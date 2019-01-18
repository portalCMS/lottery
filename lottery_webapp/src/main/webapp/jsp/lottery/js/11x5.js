// 默认关闭 debug 模式
var debug = false;

// 关闭 debug 后，这里的 log 就不能用了
var log = function(msg) {
	if (!debug)
		return;
	console && console.log(msg);
};

// 选法的配置，x 选 x
var config = {
	"totalNum" : 11,
	"selectNum" : 5
};

/**
 * @description Cathectic为投注实现的一个类
 * 
 */
var Cathectic = function() {
	// 当前选球的个数
	var curNum = 1;
	// 当前选球方式，默认为“定位”
	var chooseType = "dingwei";
	
	var isrenxuan = false;
	
	var textMsg="", textErr=[],//单式错误注数号码的下标
	textErrInfo="",
	   CHECK_PASS = 1           // 状态检测 OK, 投注的 button 可以点击
	  , CHECK_UNPASS = -1  ;      // 状态检测不通过，投注的 button 不可以点击
	

	/**
	 * @description 构造 select 模板的自执行函数
	 * @param {Number}
	 *            config['totalNum'] 选择的最大个数
	 * @return {String} 返回拼接好的字符串
	 */
	var chooseTpl = function(N) {
		var str = [ '<div class="cathectic-selector-item">',
				'<span class="CSI_label"><i>号码</i><em class="_mTitle">遗漏</em><em class="_hTitle">冷热</em></span>' ];

		for (var i = 1; i <= N; i++) {
			if(i<10){
				str.push("<span><strong>0" + i + "</strong><em class='_mNum'>0</em><em class='_hNum'>0</em></span>");
			}else{
				str.push("<span><strong>" + i + "</strong><em class='_mNum'>0</em><em class='_hNum'>0</em></span>");
			}
		}

		str = str
				.concat([ '<select><option>机选1个</option></select>', '</div>' ]);
		return str.join("\n\t");
	}(config['totalNum']);

	// 选择球的一个字典，后续用于对应相关的函数
	var selectOpts = {
		"all" : "全部",
		"big" : "大",
		"small" : "小",
		"odd" : "偶数",
		"even" : "奇数"
	};
	// 当前选中的 input[type=radio] 的 id
	var curId = "CTC_1_1";
	// 中奖的金额，默认是直选一中奖奖金，为 13
	var basic = 13;
	// 一个二维数组，记录多行选中的球的数字
	var curRes = [];
	// 当前倍率
	var curDoubling = 1;
	//投注记录
	var betOrder = new Array();
	
	
	return {
		/**
		 * 初始化函数，该种写法有利于分离功能块，让层次更加清晰
		 */
		init : function() {
			this.errorHandler();
			this.numSelector();
			this.bindResultBox();
			this.bindSelector();
		},
		/**
		 * 获取当前的选球数
		 */
		getCurNum : function() {
			return curNum;
		},
		/**
		 * 获取当前选球的方式
		 */
		chooseType : function() {
			return chooseType;
		},
		/**
		 * 获取当前倍率
		 */
		getCurDoubling : function() {
			return curDoubling;
		},
		/**
		 * 获取当前选择的球号
		 */
		getCurRes : function() {
			return curRes;
		},
		/**
		 * 获取当前的 input[type=radio] 的 ID
		 */
		getCurId : function() {
			return curId;
		},
		/**
		 * 顶部 tab 的一个绑定，用于处理各种情况的数据切换
		 */
		numSelector : function() {
			var self = this;

			$(".cathectic-num a").on(
					"click",
					function(evt) {
						evt.preventDefault();

						var $this = $(this), index = $this.index();
						lock_number.init(index,"g-play-limit",index,betForm.callBackFuc);
						// 记录当前选择的球数
						curNum = $(this).attr('name');
						// 修改状态
						$(".cathectic-num a").removeClass("cathectic-num_on");
						$this.addClass("cathectic-num_on");

						// 初始化
						$(".cathectic-type").hide().eq(index).show().find(
								"input[type=radio]").eq(0).trigger("change")
								.get(0).checked = "checked";

						$(".cathectic-type").eq(index).find(".cathectic-tip")
								.hide().eq(0).show();

						self._setMoney();
						self.resetDOM();
						//更新冷热遗漏
						//betUtil.hotMissNumCount();
						
						//中奖金额变化
						betUtil.winAmountChange();
						
						//绑定玩法div点击事件触发input radio的触发
			            $(".cathectic-type").find(".tit").unbind();
			            $(".cathectic-type").find(".tit").on('click',function(){
			            	$(this).find("input[type='radio']").eq(0).prop("checked",true);
			            	$(this).find("input[type='radio']").eq(0).trigger("change");
			            });
					}).eq(0).trigger("click");

			// 当 Text 内容发生变化的时候，刷新当前状态
	        $(".cathectic-main-choose-text textarea").on("change keyup focus propertychange", function(){
	        	$(this).val($(this).val().replace(/\n/g,","));
	        	$(this).val($(this).val().replace(/，+|,+|;+|；+/g,","));
	        	$(this).val($(this).val().replace(/\s+/g," "));
	        	if($(this).val().substring(0,1)==","){
	        		$(this).val($(this).val().substring(1));
	        	}
	            // 刷新当前状态
	            self._setMoney(); 
	        });
	        
			$(".cathectic-type-choose input[type=radio]").on(
					"change",
					function() {
						var $this = $(this), id = $this.attr('id'), 
									dc = id.substring(4,id.length);

						// 记录了当前 input[type=radio] 的 ID
						curId = id;
						//先隐藏掉所有玩法提示（update by swim 20140808 ）
						$this.parents(".cathectic-type").find(".cathectic-tip").hide();
						$this.parents("#cathectic").find("span._winAmount").each(function(index){
							$(this).removeAttr("id");
						});
						// 拿到当前中奖的金额值
						basic =$this.parents(".cathectic-type").find(".d_"+dc).show()
						.find("span").last().attr('id','awardAmount').text();
						
						// 投注方式 bool / text
						if (/3_2|5_3/.test(id)) {
							$(".cathectic-main-choose").hide();
							$(".cathectic-main-choose-text").show();
							//$(".CSR_get").hide();
							chooseType = "text";
						} else {
							$(".cathectic-main-choose").show();
							$(".cathectic-main-choose-text").hide();
							//$(".CSR_get").show();
						}

						if (/(1|2|3)_1/.test(id)) {
							chooseType = "dingwei";
							// 定位
							self._showItemList(RegExp.$1);
						}
						if (/2_2|2_4|3_3|3_5|(4|5|6|7|8)_1/.test(id)) {
							chooseType = "normal";
							if(/2_4|3_5|(4|5|6|7|8)_1/.test(id)){
								isrenxuan = true;
							}
							// 普通
							self._showItemList(1);
						}
						if (/2_3|2_5|3_4|3_6|(4|5|6|7|8)_2/.test(id)) {
							chooseType = "dantuo";
							// 胆拖
							self._showItemList(2, true);
						}
						
						//中奖金额变化
						betUtil.winAmountChange();
						
						self._setMoney();
						self.resetDOM();
						
						//选号样式修改
			    		$(".tit").removeClass("tok");
			        	$(this).closest(".tit").addClass("tok");
			    		
						//更新冷热遗漏数据
						if(chooseType!="text"){
							$("#hotMissDiv").show();
							$(this).prop('checked',true);
							betUtil.hotMissNumCount();
						}else{
							$("#hotMissDiv").hide();
						}
					}).eq(0).trigger("change");
		},
		/**
		 * @description 不同的定位方式，球的行数不一样，左侧的文案也不一样， 这个函数用于处理文案和行数
		 * @param {Number}
		 *            num 行数
		 * @param {Boolean}
		 *            tag 标记是否为胆拖投注
		 */
		_showItemList : function(num, tag) {
			var table, str = "", i = 0, self = this;

			if (num == 1) {
				table = [ '号码' ];
			} else if (num == 2 && !!tag == true) {
				table = [ '胆码', '拖码' ];
			} else {
				table = [ '第一个', '第二个', '第三个' ];
			}

			for (; i < num; i++) {
				
				str += chooseTpl.replace(/号码/, function(m) {
					return table[i];
				}).replace(/<select>[\s\S]+?<\/select>/i, function() {
					var num = curNum;
					if (chooseType == "dingwei") {
						num = 1;
					}
					if (!!tag == true) {
						num = 2;
					}
					return self._ceateItemListSelect(num,table[i]);
				});
			}

			// 填入上述生成的字符串
			$(".cathectic-main-choose").html(str);

			// 设置绑定
			self._bindSelect();

			// 胆码隐藏”遗漏“二字 remove first select
			if (num == 2 && !!tag == true) {
				$(".cathectic-selector-item").eq(0).find("em").remove();
				$(".cathectic-selector-item select").eq(0).remove();
			}
		},
		/**
		 * @description 辅助上面函数
		 */
		_ceateItemListSelect : function(num,name) {
//			var arr = [ "<select>" ];
//
//			for (var i = num; i <= config['totalNum'] - 1; i++) {
//				arr.push("<option value='" + i + "'>机选" + i + "个</option>");
//			}
//
//			for (i in selectOpts) {
//				arr.push("<option value='" + i + "'>机选" + selectOpts[i]
//						+ "</option>");
//			}
//			arr.push("</select>");
			
			var ret = [];
			if(name!="胆码"){
				 ret.push("<a href='javascript:;' class='btn'  value='all'>全</a>");
			     ret.push("<a href='javascript:;' class='btn'  value='big'>大</a>");
			     ret.push("<a href='javascript:;' class='btn'  value='small'>小</a>");
			     ret.push("<a href='javascript:;' class='btn'  value='even'>奇</a>");
			     ret.push("<a href='javascript:;' class='btn'  value='odd'>偶</a>");
			     ret.push("<a href='javascript:;' class='btn'  value='0'>清</a>");
			}

	        return ret.join("");
		},
		/**
		 * @description 选择球的绑定函数
		 */
		_bindSelect : function() {
			var total = config['totalNum'];
			var half = total % 2 == 1 ? Math.ceil(total / 2) : total / 2 + 1;
			// 映射前面提到的字典 selectOpts，每个单词对应一个函数，
			// 用于操作球的选中状态
			var funcPool = {
				"all" : function(index, me) {
					for(var x=0;x<repeat_self.length;x++){
						if(index==repeat_self[x]-1)return;
					}
					$(me).addClass("cathectic-selector-item_on");
				},
				"big" : function(index, me) {
					for(var x=0;x<repeat_self.length;x++){
						if(index==repeat_self[x]-1)return;
					}
					index + 1 >= half
							&& $(me).addClass("cathectic-selector-item_on");
				},
				"small" : function(index, me) {
					for(var x=0;x<repeat_self.length;x++){
						if(index==repeat_self[x]-1)return;
					}
					index + 1 < half
							&& $(me).addClass("cathectic-selector-item_on");
				},
				"odd" : function(index, me) {
					for(var x=0;x<repeat_self.length;x++){
						if(index==repeat_self[x]-1)return;
					}
					(index + 1) % 2 == 0
							&& $(me).addClass("cathectic-selector-item_on");
				},
				"even" : function(index, me) {
					for(var x=0;x<repeat_self.length;x++){
						if(index==repeat_self[x]-1)return;
					}
					(index + 1) % 2 == 1
							&& $(me).addClass("cathectic-selector-item_on");
				},
				"other" : function(index, me) {
					$.inArray(index + 1, rnum) > -1
							&& $(me).addClass("cathectic-selector-item_on");
				}
			};

			var rnum, self= this;
			var repeat_self = "";
			// 当 select 的值发生变化的时候，修改球的选中状态
			$(".cathectic-selector-item a.btn").on(
					"click",
					function() {
						var $this = $(this), val = $this.attr("value");

						var cb = /\d/.test(val) ? funcPool["other"]
								: funcPool[val];

						var except = [];

						$(".cathectic-selector-item").eq(0).find(
								".cathectic-selector-item_on").map(
								function(index, me) {
									except.push(Number($(me).text()));
								});
						
						var total = config['totalNum'];

						// 对胆拖投注的特殊处理，随机选择多个球
						if (chooseType == "dantuo" && except.length > 0) {
							val = val > total - except.length ? total
									- except.length : val;
							repeat_self = except;
							rnum = Cathectic.util.getSomeRandom(val, except);
						} else {
							rnum = Cathectic.util.getSomeRandom(val);
						}
						$this.parents(".cathectic-selector-item").find(
								"span strong").removeClass(
								"cathectic-selector-item_on").map(cb);

						self._setMoney();
						
						$this.closest(".cathectic-selector-item").find("a.btn").removeClass("btn_ok");
				        $this.addClass("btn_ok");
					});

			$(".cathectic-selector-item strong")
					.off()
					.on(
							"click",
							function() {
								var $obj = $(this).parents(
										".cathectic-selector-item");

								// 胆拖的处理，控制球数
								if (chooseType == "dantuo"
										&& $obj.index() == 0
										&& !$(this).hasClass(
												"cathectic-selector-item_on")
										&& $obj.eq(0).find(
												".cathectic-selector-item_on")
												.size() == curNum - 1) {

									self.alertBox("您好, 胆码的选择个数不能超过"
											+ (curNum - 1) + "个！");
									return;
								}
								var text = $(this).text();
								if (chooseType == "dantuo") {
									$obj
											.siblings()
											.find(".cathectic-selector-item_on")
											.map(
													function(index, me) {
														$(me).text() == text
																&& $(me)
																		.removeClass(
																				"cathectic-selector-item_on");
													});
								}
								$(this).toggleClass(
										"cathectic-selector-item_on");
								self._setMoney();
							});
		},

		/**
		 * @description 核心算法，计算注数
		 * @return {Number} 注数
		 */
		_calcMoney : function() {
			var res = [];
			$(".cathectic-selector-item").each(
					function() {
						var arr = [];
						$(this).find(".cathectic-selector-item_on").map(
								function(index, me) {
									arr.push($(me).text());
								});
						res.push(arr);
					});

			curRes = res;

			// 定位投注
			if (chooseType == "dingwei") {
				if (curNum == 1) {
					return res[0].length;
				}
				if (curNum == 2) {
					var a = res[0].length, b = res[1].length;
					if (a * b > 0) {
						return a
								* b
								- (a + b - $.unique(res[0].concat(res[1])
										.sort()).length);
					}
				}
				if (curNum == 3) {
					var a = res[0].length, b = res[1].length, c = res[2].length;

					var ab = a + b
							- $.unique(res[0].concat(res[1]).sort()).length;
					var ac = a + c
							- $.unique(res[0].concat(res[2]).sort()).length;
					var bc = b + c
							- $.unique(res[1].concat(res[2]).sort()).length;

					var abc = 0;
					var inter = Cathectic.util
							.arrayIntersection(res[0], res[1]);
					if (inter.length > 0) {
						abc = inter.length + c
								- $.unique(inter.concat(res[2]).sort()).length;
					}
					// 只是改变[选好了]这个按钮的状态，这里做判断
					if (a * b * c > 0
							&& $.unique(res[0].concat(res[1]).concat(res[2])
									.sort()).length >= 3
							&& !((a == b && b == abc || a == c && c == abc || b == c
									&& c == abc) && abc == 1)
							|| (a == b && a == c && b == c && abc >= 3)) {
						return a * b * c - ab * c - ac * b - bc * a + abc * 2;
					}
				}
			}
			// 胆拖
			if (chooseType == "dantuo") {
				if (res[0].length >= 1
						&& res[0].length + res[1].length > curNum) {

					return this.permutation(res[1].length, curNum
							- res[0].length);
				}
			}
			// 普通
			if (chooseType == "normal") {
				if (res[0].length < curNum)
					return;

				return this.permutation(res[0].length, curNum);
			}
			// 单式上传
			if (chooseType == "text") {
				return this._checkCountText();
			}
		},
		// 检查单式上传号码是否正确
		_checkCountText : function() {
			var val = $(".cathectic-main-choose-text textarea").val().split(/\,/m), arrText = [], self = this;

			if(!!!$(".cathectic-main-choose-text textarea").val()){
		    	   textMsg = "";
		           return  CHECK_UNPASS;
		    }
			var passText = 0;
			textErr=[];
			textErrInfo="";
			for (var i = 0; i < val.length; i++) {
				if ($.trim(val[i]) == ""){
	        	   textMsg = "您好，您输入的第 " + (i + 1) + " 注号码的格式有误 [号码为空，前后多了逗号？] ，请检查！";
	        	   continue;
		        }

		        var arrTmp = val[i].split(/\s+|,/);
				var hashTable = {
					"length" : 0
				};

				// 输入数值错误，数值区间应该是 1 - totalNum
				var isContinue=false;
				for (var j = 0; j < arrTmp.length; j++) {
					// 个数错误，应该是 curNum 个
					if (arrTmp.length != curNum) {
						textMsg = "您好，您输入的第  " + (i + 1) + " 注号码  \""+val[i]+"\" 的格式有误 [号码个数错误，应该是"
								+ curNum + "个] ，请检查！";
						isContinue=true;
						textErr.push(val[i]);
						if(textErrInfo==""){
							 textErrInfo += val[i];
						 }else{
							 textErrInfo += ","+val[i];
						 }
						break;
					}
					
					var valTmp = parseInt(arrTmp[j]);
					
					if(isNaN(valTmp)||valTmp>config['totalNum']||valTmp<1){
						 textMsg = "您好，您输入的第" + (i + 1) +" 注号码  \""+val[i]
						 +"\" 的格式有误 [数值区间应该是 01-"+config['totalNum']+",输入了字母？] ，请检查！";
						 isContinue=true;
						 textErr.push(val[i]);
						 if(textErrInfo==""){
							 textErrInfo += val[i];
						 }else{
							 textErrInfo += ","+val[i];
						 }
						 
						 break;
					}
					if(arrTmp[j].length!=2){
		                   textMsg = "您好，您输入的第 " + (i + 1) + " 注号码  \""+val[i]+"\" 的格式有误 [小于10的号码未输入0？] ，请检查！";
		                   isContinue=true;
		                   textErr.push(val[i]);
		                   if(textErrInfo==""){
								 textErrInfo += val[i];
							 }else{
								 textErrInfo += ","+val[i];
							 }
		                   break;
		              }
					if (hashTable[arrTmp[j]] == null) {
						hashTable[arrTmp[j]] = 1;
						hashTable.length += 1;
					}
				}
				
				if(hashTable.length<arrTmp.length){
					 textMsg = "您好，您输入的第 " + (i + 1) + " 注号码  \""+val[i]+"\" 的格式有误 [存在重复号码？] ，请检查！";
					 if(textErrInfo==""){
						textErrInfo += val[i];
					 }
					 continue;
				}
				
				if(!isContinue){
					passText++;
				}
			}
			
			return passText;
		},
		// 处理单式上传
		_dealText : function() {
			//先删除掉错误的单式号码
			var textVal = $(".cathectic-main-choose-text textarea").val();
			for(var i=0;i<textErr.length;i++){
				textVal = textVal.replace(textErr[i],"");
				textVal = textVal.replace(","+textErr[i],"");
				textVal = textVal.replace(textErr[i]+",","");
				textVal = textVal.replace(",,",",");
			}
			//剔除最后的逗号
			if(textVal.substring(textVal.length-1)==","){
				textVal = textVal.substring(0,textVal.length-1);
			}
			$(".cathectic-main-choose-text textarea").val(textVal);
			
			var val = $(".cathectic-main-choose-text textarea").val().split(/\,/m), arrText = [], self = this;
			curRes = val;
			var str = self._getItemTpl();
			$(".cathectic-resluts-list").append(str);

			self._updateTotalMoney();
			self.resetDOM();

			var $num = $("#oneBetCount"), $money = $("#oneBetAmount");

			$(".CSR_btn").off().removeClass("CSR_btn_OK");
			$(".CSR_btn").removeClass("btn_xhl");
            $(".CSR_btn").addClass("btn_xhl_op");
			
			$num.text(0);
			$money.text("0.00");
			//最后清空错误信息
			$("#textErrMsg").text("");
			if(textErrInfo.trim()!=""){
				self.alertBox("以下号码错误，已进行自动过滤 <br/> "+textErrInfo);
			}
			
		},
		
		/**
		 * @description 主要是对 DOM 的操作，修改一些数字。显示当前的一些参数
		 * 
		 */
		_setMoney : function() {
			var res = this._calcMoney(), $num = $("#oneBetCount"), $money = $("#oneBetAmount"),
			$win = $("#oneAwardAmount"), $get = $("#oneWinAmount");

			var self = this;
			
			$("#textErrMsg").text(textMsg);
			textMsg="";
			if (!res|| res == CHECK_UNPASS || (chooseType == "text"
					&& $(".cathectic-main-choose-text textarea").val() == "")) {
				
				$(".CSR_btn").off().removeClass("CSR_btn_OK");
				$(".CSR_btn").removeClass("btn_xhl");
	            $(".CSR_btn").addClass("btn_xhl_op");
	            
				$num.text(0);
				$money.text("0.00");
				$win.text(0);
				$get.text("0.00");
			} else {
				// if(chooseType == "text" && $(".cathectic-main-choose-text
				// textarea").val() == ""){
				// return;
				// }
				var CSRBtnCB = null;
				// 单次绑定，点击选好了之后，不能再点击，并且清空选中内容
				$(".CSR_btn").removeClass("btn_xhl_op");
	            $(".CSR_btn").addClass("btn_xhl");
				$(".CSR_btn").off("click").addClass("CSR_btn_OK").one("click",CSRBtnCB = function(evt) {
					$(this).removeClass("btn_xhl");
	            	$(this).addClass("btn_xhl_op");
	            	$(".cathectic-selector-item a.btn").removeClass("btn_ok");
	            	
					evt.preventDefault();

					if (chooseType == "text") {
						self._dealText();
						return;
					}

					var str = self._getItemTpl();
					var tmp = self._checkIsUse();

					if (tmp.length > 0 && chooseType == "dingwei") {
						if (!confirm("无效信息将会被剔除：\n" + tmp.join("\n")
								+ "\n点击确定，剔除信息并投注.")) {
							$(".CSR_btn").one("click", CSRBtnCB);
							return;
						} else {
							$(".cathectic-resluts-list").append(str);
							self._updateTotalMoney();
							self.resetBtn();
						}
					} else {
						$(".cathectic-resluts-list").append(str);
						self._updateTotalMoney();
						self.resetBtn();
					}

				});
				$num.text(res);
				$money.text(res * 2 * this.getMoneyModel());

				var selectNum = config['selectNum'];

				// 修改数据
				// 拿到当前中奖的金额值
				basic =$("#awardAmount").text();
				
				if (chooseType == "normal") {
					var doubling = 1;
					if (curNum >= selectNum + 1) {
						doubling = this.permutation(curRes[0].length - 5,
								curNum - 5);
					}
					$win.text(basic * doubling);
				}
				if (chooseType == "dingwei") {
					$win.text(basic );
				}
				if (chooseType == "dantuo") {
					var doubling = 1, len;

					len = $.unique(curRes[0].concat(curRes[1]).sort()).length;
					if (curNum >= selectNum + 1) {
						doubling = this.permutation(len - 5, curNum - 5);
					}
					$win.text(basic * this.getMoneyModel());
				}
				if(chooseType=="text"){
					$win.text(basic.replace(",", "") * this.getMoneyModel());
				}
				
				$get.text(parseFloat($win.text().replace(",", "")* this.getMoneyModel())
						- parseFloat($money.text()));
				if(chooseType == "normal"&&curNum>1&&curNum<5&&isrenxuan){
					var tjj = parseFloat($win.text().replace(",", "")* this.getMoneyModel())
					- parseFloat($money.text());
					var tjj1 = parseFloat($win.text().replace(",", "")* this.getMoneyModel())*$num.text()
					- parseFloat($money.text());
					$get.text(NumberFormat.formatNumber(tjj,2,1)+"至"+NumberFormat.formatNumber(tjj1,2,1));
					var yl = parseFloat($win.text())*this.getMoneyModel();
					var yl1 = parseFloat($win.text())*this.getMoneyModel()*$num.text();
					$win.html(NumberFormat.formatNumber(yl,2,1)+"至"+NumberFormat.formatNumber(yl1,2,1));
				}else{
					$get.text(parseFloat($win.text().replace(",", "")* this.getMoneyModel())
							- parseFloat($money.text()));
					$win.text(parseFloat($win.text())*this.getMoneyModel());
					$win.formatCurrency();
					$get.formatCurrency();
				}
				
				//$money.text(parseFloat($money.text())*this.getMoneyModel());
				//$get.text(parseFloat($get.text())*this.getMoneyModel());
			}
			$money.formatCurrency();
			
		},

		// 重置 button 的状态，并修改文案（数字）
		resetBtn : function() {
			$(".CSR_btn").removeClass("CSR_btn_OK");
			$(".cathectic-selector-item_on").removeClass(
					"cathectic-selector-item_on");
			this.resetDOM();
		},

		/**
		 * @description 点击选好了之后，下面的 box 会添加一个记录， 本函数是用来创建一个模板
		 * @param [Object]
		 *            传过来需要记录的信息
		 * @return {String} 模板字符串
		 */
		_getItemTpl : function(data1) {
			var tpl = [ '<div data-num="<%num%>" data-type="<%type%>">',
					'<a href="#">删除</a>', '<span>[<%type%>]</span>',
					'<input type="hidden" value="<%code%>"/>',//玩法code
					'<input type="hidden" value="<%betcode%>"/>',
					'<input type="hidden" value="<%betModel%>"/>',
					'<input type="hidden" value="<%betSelect%>"/>',
					'<input type="hidden" value="<%betCount%>"/>',
					'<input type="hidden" value="<%formToken%>"/>',
					'<span><%textShowContent%></span>', '(<i><%num%></i>注<i><%money%></i>元)',
					'</div>' ].join("");

			var content = " ";
			var betcode = "";
			if (chooseType == "dantuo") {
				content = " 胆：";
				content += curRes[0].join(",");
				content += " 拖：";
				content += curRes[1].join(",");
				betcode = curRes[0].join(",")+"&"+curRes[1].join(",");
			}
			if (chooseType == "dingwei") {
				var tmp = [];
				for (var i = 0, len = curRes.length; i < len; i++) {
					tmp.push(curRes[i].join(","));
				}

				content += tmp.join("|")
						+ Cathectic.util.repeat("|-", 5 - curNum);
				betcode = content;
			}
			if (chooseType == "normal" || chooseType == "text") {
				if(chooseType == "text"){
					for(var i=0;i<curRes.length;i++){
						if(i!=curRes.length-1){
							content += curRes[i]+",";
						}else{
							content += curRes[i];
						}
						betcode = content;
					}
				}else{
					content += curRes[0].join(",");
					betcode = content;
				}
			}
			// 模板数据
			var data = "";
			if(data1==undefined){
				var textShowContent="";
				if(content.length>50){
					textShowContent = content.substring(0,50)+"...详情";
				}else{
					textShowContent = content;
				}
				data = $.extend({
					"num" : $("#oneBetCount").text(),
					"money" : $("#oneBetAmount").text(),
					'type' : $("#" + curId).attr("data-type"),
					'code' : $("#" + curId).attr("data-code"),
					'betModel':$("#moneyModel").val(),
					"betcode" : betcode,
					'betSelect':$("#" + curId).attr("data-select"),
					"betCount" : $("#oneBetCount").text(),
					"content" : content + " ",
					"textShowContent" : textShowContent + " "
				}, data || {});
			}else{
				data = data1;
			}
			var formToken = MD5.hex_md5(data["money"].replace(",", "")+data["betModel"]+data["betcode"]+data["betCount"]);
			data["formToken"] = formToken;
			// 替换内容的模板
			return tpl.replace(/<%([^%>]+)?%>/g, function(m, a) {
				return data[a];
			});

			// return Cathectic.util.tplEngine(tpl, data);
		},

		// 一些数字文案的还原处理
		resetDOM : function() {
			if (chooseType == "dingwei" || chooseType == "normal") {
				$("#randomResult").show();
			} else {
				$("#randomResult").hide();
			}

			$("#oneAwardAmount").text("0");
			$("#oneWinAmount").text("0.00");
			$("#oneBetCount").text("0");
			$("#oneBetAmount").text("0.00");

			$(".cathectic-main-choose-text textarea").val("");
		},

		// 计算排列数值
		permutation : function(a, b) {
			var resX = 1, resY = 1, tmp = b;
			while (tmp) {
				resX *= a;
				a--;
				tmp--;
			}
			while (b) {
				resY *= b;
				b--;
			}
			return resX / resY;
		},
		// 该函数可扩展，弹出提醒
		alertBox : function(msg) {
			$("body").sMsg({
				content : msg,
				autoDisplay : true,
				actionEvent : ""
			});
		},

		/**
		 * @description 在选好了附近和下方有很多可以点击的按钮和链接， 这个函数内部主要是对上述按钮、链接做绑定
		 */
		bindResultBox : function() {
			var self = this;

			$(".CSR_clear").on(
					"click",
					function(evt) {
						evt.preventDefault();

						$(".cathectic-selector-item_on").removeClass(
								"cathectic-selector-item_on");
						//清空单式录入
						$(".cathectic-main-choose-text textarea").val("");

						$("#oneAwardAmount").text("0");
						$("#oneWinAmount").text("0.00");

						self._setMoney();
						$(".cathectic-selector-item a.btn").removeClass("btn_ok");
					});

			var keydownTimer = null;
			$(".cathectic-main-choose-text textarea").on("keydown", function() {
				clearTimeout(keydownTimer);
				setTimeout(function() {
					self._setMoney();
				}, 100);
			});

			$(".cathectic-resluts-list").on("click", "a", function(evt) {
				evt.preventDefault();

				$(this).parent().remove();
				self._updateTotalMoney();
			});

			$(".cathectic-resluts-list").on(
					"click",
					"div",
					function(evt) {
						evt.preventDefault();

						$(this).addClass("cathectic-resluts-list_active")
								.siblings().removeClass(
										"cathectic-resluts-list_active");
					});

			$("#clearResult").on("click", function(evt) {
				evt.preventDefault();
				$("#doubling").val(1);
				$(".cathectic-resluts-list").html("");
				self._updateTotalMoney();
			});

			$(".cathectic-resluts-doubling i").on("click", function() {
				var index = $(this).index();
				var $val = $("#doubling");

				var val = Number($val.val());
				if (index == 0) {
					$val.val(val - 1 > 0 ? val - 1 : 1);
				} else {
					$val.val(val + 1);
				}

				curDoubling = $val.val();
				$(".cathectic-resluts-doubling input").trigger("change");
			});

			$(".cathectic-resluts-doubling input").on("change keyup blur",
					function() {
						if(parseInt($(this).val())>1000){
							$(this).val(1000);
							$("#boubWarning").show();
							//return;
						}else{
							$("#boubWarning").hide();
						}
						self._updateTotalMoney($(this).val());
					});
		},
		// 更新注数和钱数
		_updateTotalMoney : function(N) {
			var $num = $(".cathectic-resluts-doubling strong").eq(0);
			var $money = $(".cathectic-resluts-doubling strong").eq(1);

			var total = 0;
			$(".cathectic-resluts-list > div").map(function(index, me) {
				total += Number($(me).attr("data-num"));
			});
			
			var N = N || curDoubling;
			
			$num.text(total);
			var summoney = 0;
			$(".cathectic-resluts-list div").each(function(){
				summoney+=parseFloat($(this).find("i").eq(1).text().replace(",", ""));
				var $self = $(this);
                $(this).find("span:last").click(function(){
                	$("body").sMsg({
                		title:"投注号码明细",
                		content : $self.find("input[type='hidden']").eq(1).val(),
            			autoDisplay : true,
            			actionEvent : ""
            		});
                });
			});
			var $totalMonery = $(".xuanHaoBtn p").find("span").eq(1);
			if($("#lunch_t1").attr("checked")=="checked"){
				$totalMonery.text("￥"+summoney);
				$totalMonery.formatCurrency();
			}
			$money.text(summoney);
			$money.formatCurrency();
			
			//add by swim 20140812 添加或删除了新的投注后联动修改追期数据。
			//var num = $(".zhuiQiUl .current").attr('name');
			//判断是否为追期，联动修改追期信息
			var isZq = $("#traceSelect").find(".ok").find("input").val();
			if(isZq=="Z"||isZq=="L"){
				betUtil.chasePeriodHelper($("#zqNum").val());
			}else{
				var betAmount = $("#betAmount").text().replace(",", "");
				var doubling = $("#doubling").val();
				var totalAmount = betAmount*doubling;
				$("#totalAmount").html(totalAmount);
				$("._amount").formatCurrency();
			}
			if($("#totalAmount").asNumber()>0){
				$("._betbtn").addClass("Bet_btn_OK");
			}else{
				$("._betbtn").removeClass("Bet_btn_OK");
			}
		},

		// 去重函数
		_checkIsUse : function() {
			var err = [], a1, a2, a3;
			if (chooseType == "dingwei" && curNum == 3) {
				a1 = this._checkIsUseHelper(curRes[0], curRes[1], curRes[2]);
				a2 = this._checkIsUseHelper(curRes[1], curRes[2], curRes[0]);
				a3 = this._checkIsUseHelper(curRes[0], curRes[2], curRes[1]);
				err = err.concat(a1).concat(a2).concat(a3);
			}
			if (chooseType == "dingwei" && curNum == 2) {
				a1 = this._checkIsUseHelper(curRes[0], curRes[1]);
				a2 = this._checkIsUseHelper(curRes[1], curRes[0]);
				err = err.concat(a1).concat(a2);
			}
			return $.unique(err);
		},
		// 辅助上述去重
		_checkIsUseHelper : function(a, b, c) {
			var res = [];
			var inter = Cathectic.util.arrayIntersection(a, b);
			if (inter.length > 0) {
				for (var i = 0; i < inter.length; i++) {
					if (curNum == 3) {
						res.push([ inter[i], inter[i], c.join(",") ].join("|"));
					} else {
						res.push([ inter[i], inter[i] ].join("|"));
					}
				}
			}
			return res;
		},
		// 错误处理
		errorHandler : function() {
			window.onerror = function(info) {
				if (/^script error.$/i.test(info)) {
					return true;
				}
				return true;
			};
		},
		// 选择多注的 select 绑定
		bindSelector : function() {
			var self = this;
			$("#randomResult").on("click",function() {
				var val = 1;
				for (var s = 0; s < val; s++) {

					var tmp = [];
					curRes = [];

					if (chooseType == "dingwei") {
						for (var i = 0; i < curNum; i++) {
							var ls = Cathectic.util.getRandom(tmp);
							tmp.push(ls);
							curRes.push([ ls ]);
						}
						//alert(tmp);return;
					}
					if (chooseType == "normal") {
						curRes.push(Cathectic.util
								.getSomeRandom(curNum));
					}

					var content = " ";
					var betcode = "";
					if (chooseType == "dantuo") {
						content = " 胆：";
						content += curRes[0].join(",");
						content += " 拖：";
						content += curRes[1].join(",");
						betcode = curRes[0].join(",")+"&"+curRes[1].join(",");
					}
					if (chooseType == "dingwei") {
						var tmp = [];
						for (var i = 0, len = curRes.length; i < len; i++) {
							tmp.push(curRes[i].join(","));
						}

						content += tmp.join("|")
								+ Cathectic.util.repeat("|-", 5 - curNum);
						betcode = content;
					}
					if (chooseType == "normal" || chooseType == "text") {
						content += curRes[0].join(",");
						betcode = content;
					}
					var money = "";
					if($("#moneyModel").val()==1){
						money = 2*$("#moneyModel").val()+".00";
					}else if($("#moneyModel").val() == 0.1){
						money = 2*$("#moneyModel").val()+"0";
					}else{
						money = 2*$("#moneyModel").val();
					}
					
					var textShowContent="";
					if(content.length>50){
						textShowContent = content.substring(0,50)+"...详情";
					}else{
						textShowContent = content;
					}
					var str = self._getItemTpl({
						"num" : 1,
						"money" : money,
						'type' : $("#" + curId).attr("data-type"),
						'code' : $("#" + curId).attr("data-code"),
						'betModel':$("#moneyModel").val(),
						"betcode" :  betcode.replace(/\b0?([1-9])\b[^1-9,|]*/g, "0$1"),
						'betSelect':$("#" + curId).attr("data-select"),
						'betCount':1,
						"content" : content.replace(/\b0?([1-9])\b[^1-9,|]*/g, "0$1") + " ",
						"textShowContent":textShowContent
						
					});

					$(".cathectic-resluts-list").append(str);
				}

				self._updateTotalMoney();
				$(".cathectic-resluts-list")
						.scrollTop(
								$(".cathectic-resluts-list").children()
										.size() * 24);
			});
		},
		//获取当前圆角分模式
		getMoneyModel:function(){
			var model = $("#moneyModel").val();
			return parseFloat(model);
		}
	};
}();

/**
 * @description 对Cathectic类的扩展，添加辅助工具
 * @method getRandom 获取随机数
 * @method getSomeRandom 获取多个随机数
 * @method tplEngine 模板引擎
 * @method repeat 字符串重复
 * @method arrayIntersection 数组交集
 */
Cathectic.util = function() {
	return {
		/**
		 * @description 获取随机数
		 * @param [Array]
		 *            except 剔除的数
		 * @return [Array] 返回一个数组
		 */
		getRandom : function(except) {

			var total = config['totalNum'];

			if (!except || except.length == 0) {
				return Math.floor(Math.random() * total) + 1;
			}

			var res, tag = true;
			while (1 && tag) {
				res = Math.floor(Math.random() * total) + 1;
				($.inArray(res, except) == -1) && (tag = false);
			}
			return res == total + 1 ? res - 1 : res;
		},
		/**
		 * @description 获取随机数
		 * @param [Number]
		 *            num 个数
		 * @param [Array]
		 *            except 获取多个随机数
		 * @return [Array] 返回一个数组
		 */
		getSomeRandom : function(num, except) {
			var res = [], args;

			while (num--) {
				if (except && res.length == 0) {
					args = except;
				} else {
					args = $.unique((except || []).concat(res).sort());
				}
				var tmp = this.getRandom(args);
				res.push(tmp);
			}

			return res.sort(function(a, b) {
				return a > b;
			});
		},
		// 模板引擎，详情可以异步我写的一篇博客
		// http://www.cnblogs.com/hustskyking/p/principle-of-javascript-template.html
		tplEngine : function(tpl, data) {
			var reg = /<%([^%>]+)?%>/g, regOut = /(^( )?(if|for|else|switch|case|break|{|}))(.*)?/g, code = 'var r=[];\n', cursor = 0;

			var add = function(line, js) {
				js ? (code += line.match(regOut) ? line + '\n' : 'r.push('
						+ line + ');\n') : (code += line != '' ? 'r.push("'
						+ line.replace(/"/g, '\\"') + '");\n' : '');
				return add;
			};
			while (match = reg.exec(tpl)) {
				add(tpl.slice(cursor, match.index))(match[1], true);
				cursor = match.index + match[0].length;
			}
			add(tpl.substr(cursor, tpl.length - cursor));
			code += 'return r.join("");';
			return new Function(code.replace(/[\r\t\n]/g, '')).apply(data);
		},

		// 简单易懂，不多说
		repeat : function(str, N) {
			var res = "";
			while (N--) {
				res += str;
			}
			return res;
		},
		/**
		 * @description 数据去重
		 * @param {Array}
		 *            a 要比较的数组
		 * @param {Array}
		 *            b 要比较的数组
		 * @return {Array} a 和 b 的交集
		 */
		arrayIntersection : function(a, b) {
			var ai = 0, bi = 0;
			var result = new Array();

			while (ai < a.length && bi < b.length) {
				if (a[ai] < b[bi]) {
					ai++;
				} else if (a[ai] > b[bi]) {
					bi++;
				} else {
					result.push(a[ai]);
					ai++;
					bi++;
				}
			}
			return result;
		}
	};
}();

var betForm = function(){
	var callback = {
		"initPage":function(data){
			formPost.showErrorMessage(data.success);
			$(".CSR_clear").click();
			$("#clearResult").click();
			$("#token").val(data.token);
			$("#doubling").val(1);
			$("._betbtn button").text("立即投注");
			formPost.submitAjaxForm1("getCashAmount.shtml",null,null,head.refreshCash,null);
			$("#refreshBet").click();
		},
		"betErrorFunc":function(){
			$("._betbtn").addClass("Bet_btn_OK");
			$("._betbtn button").text("立即投注");
		}
	};
	return{
		callBackFuc:function(fuc){
			var callFuc = {
				"limits":function(data){
					var array = data.limit;
					if(array==null)array=new Array();
					var date = new Date();
					fuc(date.getHours()+":"+date.getMinutes()+":"+date.getSeconds(),array);
				}
			};
			var attrs = new Array();
			var values = new Array();
			attrs.push("key");
			var playCode = $(".cathectic-type-choose:visible").find("input[type='radio']:checked").attr("data-code");
			var key = $("#lotCode").val()+","+playCode+","+$("#currentIssno").text();
			values.push(key);
			formPost.submitAjaxForm("getLimits.shtml",attrs,values,callFuc["limits"]);
		},
		packageData:function(attrs,values){
			attrs.push("issueNos");
			var issueNos = $("#currentIssno").text()+":"+$("#doubling").val();
			values.push(issueNos);
			var $reultsDiv = $(".cathectic-resluts-list div");
			var i = 0;
			var flag = true;
			$reultsDiv.each(function(){
				attrs.push("volist["+i+"].lotteryCode");
				attrs.push("volist["+i+"].playCode");
				attrs.push("volist["+i+"].betModel");
				attrs.push("volist["+i+"].betMoney");
				attrs.push("volist["+i+"].betType");
				attrs.push("volist["+i+"].bileNum");
				attrs.push("volist["+i+"].betNum");
				attrs.push("volist["+i+"].rebates");
				attrs.push("volist["+i+"].selectCode");
				attrs.push("volist["+i+"].betCount");
				values.push($("#lotCode").val());
				values.push($(this).find("input").eq(0).val());
				values.push($(this).find("input").eq(2).val());
				values.push($(this).find("i").eq(1).text().replace(",", ""));
				values.push(20000);
				if($(this).find("input").eq(1).val().indexOf("&")>-1){
					var betCode = $(this).find("input").eq(1).val().split("&");
					values.push(betCode[0]);
					values.push(betCode[1]);
				}else{
					values.push("");
					values.push($(this).find("input").eq(1).val());
				}
				values.push($("#userBonus").find("option:selected").attr("name"));
				values.push($(this).find("input").eq(3).val());
				values.push($(this).find("input").eq(4).val());
				var formcode = MD5.hex_md5($(this).find("i").eq(1).text().replace(",", "")+$(this).find("input").eq(2).val()+$(this).find("input").eq(1).val()+$(this).find("input").eq(4).val());
				if($(this).find("input").eq(5).val()!=formcode){
					flag = false;
					return ;
				}
				i++;
			});
			return flag;
		},
		trackPackageData:function(attrs,values){
			var i = 0; 
			attrs.push("issueNos");
			var issueNos = "";
			$("#zqtb input[type='checkbox']").each(function(index ) {
				if($(this).prop('checked')){
					if(issueNos==""){
						issueNos += $(this).val()+":"+$(this).closest("tr").find("input[type='text']").val();
					}else{
						issueNos += ","+$(this).val()+":"+$(this).closest("tr").find("input[type='text']").val();
					}
				}
			});
			values.push(issueNos);
			var flag = true;
			var $reultsDiv = $(".cathectic-resluts-list div");
				$reultsDiv.each(function(){
				attrs.push("volist["+i+"].lotteryCode");
				attrs.push("volist["+i+"].playCode");
				attrs.push("volist["+i+"].betModel");
				attrs.push("volist["+i+"].betMoney");
				attrs.push("volist["+i+"].betType");
				attrs.push("volist["+i+"].bileNum");
				attrs.push("volist["+i+"].betNum");
				attrs.push("volist["+i+"].rebates");
				attrs.push("volist["+i+"].selectCode");
				attrs.push("volist["+i+"].betCount");
				values.push($("#lotCode").val());
				values.push($(this).find("input").eq(0).val());
				values.push($(this).find("input").eq(2).val());
				values.push($(this).find("i").eq(1).text().replace(",", ""));
				values.push(20000);
				if($(this).find("input").eq(1).val().indexOf("&")>-1){
					var betCode = $(this).find("input").eq(1).val().split("&");
					values.push(betCode[0]);
					values.push(betCode[1]);
				}else{
					values.push("");
					values.push($(this).find("input").eq(1).val());
				}
				values.push($("#userBonus").find("option:selected").attr("name"));
				values.push($(this).find("input").eq(3).val());
				values.push($(this).find("input").eq(4).val());
				var formcode = MD5.hex_md5($(this).find("i").eq(1).text().replace(",", "")+$(this).find("input").eq(2).val()+$(this).find("input").eq(1).val()+$(this).find("input").eq(4).val());
				if($(this).find("input").eq(5).val()!=formcode){
					flag = false;
					return;
				}
				i++;
			});
			return flag;
		},
		submitForm:function(){
			if($("#pauseSale").text()!=undefined&&$("#pauseSale").text().trim()!=""){
				$("._betbtn").removeClass("Bet_btn_OK");
				formPost.showErrorMessage("亲，当前彩种已暂停销售！");
				return;
			}
			var $reultsDiv = $(".cathectic-resluts-list");
			if($reultsDiv.html()==""){
				formPost.showErrorMessage("亲,别忘记选择你的投注号码哦!");
				return;
			}
			
			//投注之后立刻清空点击事件。
			$("._betbtn").removeClass("Bet_btn_OK");
			$("._betbtn button").text("投注中，请稍候..");
			
			var attrs = new Array();
			var values = new Array();
			attrs.push("orderAmount");
			values.push($("#totalAmount").text().replace(/,/g, ""));
			if($("#totalAmount").text().replace(/,/g, "")<0){
				formPost.showErrorMessage("注单异常");
				$("._betbtn").addClass("Bet_btn_OK");
				$("._betbtn button").text("立即投注");
				return;
			}
			attrs.push("trackNo");
			values.push($("#totalPeriods").text());
			attrs.push("token");
			values.push($("#token").val());
			if(undefined!=$("input[name='awardStop']:checked").val()&&""!=$("input[name='awardStop']:checked").val()){
				attrs.push("awardStop");
				values.push($("input[name='awardStop']:checked").val());
			}else{
				attrs.push("awardStop");
				values.push("0");
			}
			var flag = true;
			var pfErr = false;
			//追号
			if($("#traceSelect").find(".ok").text()!=undefined
					&&$("#traceSelect").find(".ok").text()!=""){
				var ttype = $("#traceSelect").find(".ok").find("input").val();
				if(ttype=="L"&& $(".ssc-resluts-list div").size()>1){
					var mcMap = new Map();
					$(".cathectic-resluts-list div").each(function(){
						var mc = $(this).find("input").eq(0).val();
						if(mcMap.get(mc)==undefined){
							mcMap.put(mc, mc);
						}
					});
					if(mcMap.keySet().length>1){
						pfErr = true;
						return;
					}
					
				}
				attrs.push("isTrack");
				values.push(ttype);
				flag = betForm.trackPackageData(attrs,values);
			}else{
				//普通投注
				attrs.push("isTrack");
				values.push("P");
				flag = betForm.packageData(attrs,values);
			}
			
			if(pfErr){
				formPost.showErrorMessage("亲，利润率追号只能适用于一种玩法哦！");
				$("._betbtn").addClass("Bet_btn_OK");
				$("._betbtn button").text("立即投注");
				return;
			}
			if(!flag){
				formPost.showErrorMessage("注单错误");
				$("._betbtn").addClass("Bet_btn_OK");
				$("._betbtn button").text("立即投注");
				return;
			}
			formPost.setErrorFunc(callback["betErrorFunc"]);
			formPost.submitAjaxForm1("bet11x5.shtml",attrs,values,callback["initPage"],null);
		}
		
	};
}();
// 整个函数启动的入口，调用 Cathectic 的初始化函数。
$(function() {
	Cathectic.init();
	betUtil.init();
});


