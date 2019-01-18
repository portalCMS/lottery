/**
* @author Barret Lee(barret.china@gmail.com)
* @date 2014/08
* 
* MIT LICENSE
*/

;(function(window, undefined){

var curNum = 1               // 当前选择的球的个数
  , curDoubling = 1          // 当前的倍率
  , curRes = []              // 当前选中的所有球
  , curType = "standard"     // 当前算法方式
  , curFullName = "直选"     // 当前的玩法名字
  , curId = "SSC_3_1"        // 当前选中的 input 位置
  , CHECK_PASS = 1           // 状态检测 OK, 投注的 button 可以点击
  , CHECK_UNPASS = -1        // 状态检测不通过，投注的 button 不可以点击
  , textMsg = ""             // curType 为 Text 时，错误信息(不会显示，可作为强检测)
  , textErr=[]
  ,textErrInfo=""
  
  // 设置倍率最大值
  , MAX_DOUBLING = $(".ssc-resluts-doubling em b").text() || 1000
  
  // 引入进来，减少作用域链的查询，达到优化程序的作用
  , ConfigParam = window.ConfigParam
  , ConfigDOM = window.ConfigDOM
  , ConfigClose = window.ConfigClose || []

  // 和值中需要用到的两个常量
  , TABLE_2 = (function(){
        var table2 = []
          , tableHash2 = {};

        for(var i = 0; i < ConfigParam['total']; i++){
            for(var j = i; j < ConfigParam['total']; j++){
                table2.push(i + j);  
            }
        }


        for(i = 0; i < table2.length; i++){
            if(!!!tableHash2[table2[i]]){
                tableHash2[table2[i]] = 1;
            } else {
                tableHash2[table2[i]] += 1;
            }
        }

        return tableHash2;
    })()
   , TABLE_2_1 = {0:1,1:2,2:3,3:4,4:5,5:6,6:7,7:8,8:9,9:10,10:9,11:8,12:7,13:6,
        14:5,15:4,16:3,17:2,18:1}
  , TABLE_3_1 = {0:1,1:3,2:6,3:10,4:15,5:21,6:28,7:36,8:45,9:55,10:63,11:69,12:73,13:75,
        14:75,15:73,16:69,17:63,18:55,19:45,20:36,21:28,22:21,23:15,24:10,25:6,26:3,27:1}
  , TABLE_3_2 = {1:1,2:2,3:1,4:3,5:3,6:3,7:4,8:5,9:4,10:5,11:5,12:4,13:5,
        14:5,15:4,16:5,17:5,18:4,19:5,20:4,21:3,22:3,23:3,24:1,25:2,26:1}
  , TABLE_3_3 = {3:1,4:1,5:2,6:3,7:4,8:5,9:7,10:8,11:9,12:10,13:10,
        14:10,15:10,16:9,17:8,18:7,19:5,20:4,21:3,22:2,23:1,24:1};


//=========================================================== SSC ==//

/**
 * SSC Class
 * 时时彩代码类，封装时时彩所需的各种函数
 *
 * @param {String|Object} con container
 */
var SSC = window.SSC = function(con,newConfigDOM){
    this.init(con,newConfigDOM);
};

$.extend(SSC.prototype, {
    init: function(con,newConfigDOM){
    	if(newConfigDOM!=undefined)ConfigDOM = newConfigDOM;
        // container
        this.$con = $(con).size() > 0 ? $(con) : $("#ssc");
        // 初始化 Tab
        this.tabSwitch();
        // 绑定点击
        this.bindUI();
        // 绑定多选 selector
        this.bindMultiSelector();
        
        //fix bug 
        $("#doubling").val(1);
        
    },
    getCurNum: function(){
        return curNum;
    },
    /**
     * @return {Number} 当前的倍率
     */
    getCurDoubling: function(){
        return Number(curDoubling);
    },
    /**
     * @return {String} 当前算法方式
     */
    getCurType: function(){
        return curType;
    },
    /**
     * @return {Array} 当前选中的所有球
     */
    getCurRes: function(){
        return curRes;
    },
    /**
     * 手工录入，错误信息提示
     */
    getTextMsg: function(){
        return textMsg;
    },
    refreshBetModel : function(){
    	this.refresh().init();
    },
    /**
     * 玩法配置，关闭某些玩法，开始前的预处理
     */
    preprocessor: function(pos){
        if(pos.length == 0) return;

        var POSITION = [];
        for(var i = 0; i < pos.length; i++){
            POSITION.push(pos[i].join());
        }

        // 预处理配置文件
        for(var tab in ConfigDOM){
            for(i = 0; i < ConfigDOM[tab].length; i++){
                var sub = ConfigDOM[tab][i];
                if(!!!sub["group"]){
                    if($.inArray(sub["pos"].join(), POSITION) > -1){
                        ConfigDOM[tab].splice(i, 1);
                        i--;
                    }
                } else {
                    for(var j = 0; j < sub["data"].length; j++){
                        if($.inArray(sub["data"][j]["pos"].join(), POSITION) > -1){
                            ConfigDOM[tab][i]["data"].splice(j, 1);
                            j--;
                        }
                    }
                }
            }
        }

        // 遍历数组是否为空
        for(var key in ConfigDOM){
            if(ConfigDOM[key].length == 0){
                delete ConfigDOM[key];
                continue;
            }
            if(ConfigDOM[key][0]["data"]){
                for(j = 0; j < ConfigDOM[key].length; j++){
                    if(ConfigDOM[key][j]["data"].length == 0){
                        ConfigDOM[key].splice(j, 1);
                        j--;
                    }
                }
                if(ConfigDOM[key].length == 0){
                    delete ConfigDOM[key];
                }
            }
        }
    },
    /**
     * [tabSwitch 绑定顶部的 Tab 和联动的 SubTab]
     */
    tabSwitch: function(){
        var self = this;

        // 预处理
        self.preprocessor(ConfigClose);

        // 插入顶部 Tab
        $(".ssc-num").html(DOMHelper.createTab());

        // subTab bind
        self.$con.on("change", ".ssc-type input", function(){
            self.$con.find(".ssc-tip").find("i").html($(this).attr("data-tip"));

            curId = $(this).attr("id");
            curFullName = $(this).attr("data-group").replace("：","");
            self.bollLinkage();
            
            var isChecked = false;
            $(".ssc-type-choose:visible").find("input[type='radio']").each(function(){
            	if($(this).prop("checked")){
            		isChecked = true;
            	}
            });
            
            if(!isChecked){
            	$(".ssc-type-choose:visible").find("input[type='radio']").eq(0).trigger("click");
            }
        	//如果是0返点中奖金额需要重新计算
            betUtil.winAmountChange2();
        	$("._amount").formatCurrency();
        	//冷热号查询显示
    		betUtil.hotMissNumCount();
    		
    		//选号样式修改
    		$(".tit").removeClass("tok");
        	$(this).closest(".tit").addClass("tok");
        });

        // 绑定顶部 Tab 的点击事件
        self.$con.on("click", ".ssc-num a", function(evt){
            evt.preventDefault();
            // 添加选中样式
            $(".ssc-num a").removeClass("ssc-num_on");
            $(this).addClass("ssc-num_on");

            var item = $(this).text();

            /** 
             * 联动的中间处理，设置各种 Cur 状态值
             */
            self.setCurAttr(item);

            // 获取需要渲染的数据
            for(var key in ConfigDOM){
                if(key === item){
                    item = ConfigDOM[key];
                    break;
                }
            }
            // 默认不存在分组
            var tplTag = 0; 

            // 标记是否存在分组
            if(!!item[0].data) {
                tplTag = 1;
            }

            // 使用模板引擎生成 SubTab
            item = Utils.tplEngine(DOMHelper.createSubTab(tplTag), item);
            // 插入 SubTab
            $(".ssc-type").html(item).find("input")
                .eq(0).trigger("change")
                .get(0).checked = "checked";
            //切换玩法时，根据返奖率计算中奖金额。
        	betUtil.winAmountChange2();            
            //冷热号查询显示
    		betUtil.hotMissNumCount();
            $("._amount").formatCurrency();
            
            //绑定玩法div点击事件触发input radio的触发
            $(".ssc-type").find(".tit").unbind();
            $(".ssc-type").find(".tit").on('click',function(){
            	$(this).find("input[type='radio']").eq(0).prop("checked",true);
            	$(this).find("input[type='radio']").eq(0).trigger("change");
            });
        });
    },

    /**
     * setCurAttr 根据 TAB 确定当前的球数
     * @param {String} text Tab中的文案
     */
    setCurAttr: function(text){
        // 设置当前球的个数
        var table = "一二三四五六七八九".split("");

        if(text.match(new RegExp("(" + table.join("|") + ")"))){
            // 球数设置
            curNum = $.inArray(RegExp.$1, table) + 1;
        } else {
            // 大小单双
            curNum = -1;
        }
    },

    /**
     * bollLinkage 根据 SubTab 中的 id 属性来判断当前的算法
     * 并渲染各行球的展示
     */
    bollLinkage: function(){
        var self = this;
        var index = curId.split("_")[1];
        lock_number.init(0,"g-play-limit",0,betForm.callBackFuc);
        if(curId=="SSC_1_1"||curId=="SSC_2_1"||curId=="SSC_3_1"||curId=="SSC_4_1"||curId=="SSC_5_1"
    		||curId=="SSC_6_1"||curId=="SSC_6_3"||curId=="SSC_11_1"||curId=="SSC_12_1"||curId=="SSC_13_1"
    			||curId=="SSC_15_1"||curId=="SSC_18_1"||curId=="SSC_5_3"){
		        // 标准选号, 6_3为通选
		        curType = "standard";
		    }else if(curId=="SSC_2_4"||curId=="SSC_2_6"||curId=="SSC_3_2"||curId=="SSC_4_3"||curId=="SSC_4_6"
		    			||curId=="SSC_11_2"||curId=="SSC_18_5"||curId=="SSC_12_2"||curId=="SSC_13_3"||curId=="SSC_15_3"
		    				||curId=="SSC_14_2"||curId=="SSC_16_2"||curId=="SSC_18_2"||curId=="SSC_18_5"){
		    	// 和值
		    	curType = "sum";
		    }else if(curId=="SSC_2_3"||curId=="SSC_2_8"||curId=="SSC_18_7"||curId=="SSC_4_2"||curId=="SSC_4_5"
		    			||curId=="SSC_10_1"||curId=="SSC_10_2"||curId=="SSC_10_3"||curId=="SSC_10_4"
		    				||curId=="SSC_10_5"||curId=="SSC_10_6"||curId=="SSC_10_7"||curId=="SSC_10_8"
		    					||curId=="SSC_18_4"||curId=="SSC_13_2"||curId=="SSC_15_2"||curId=="SSC_14_1"
		    						||curId=="SSC_16_1"){
		    	  // 包号
		    	curType = "bh";
		    }else if(curId=="SSC_2_5"||curId=="SSC_3_3"||curId=="SSC_4_7"||curId=="SSC_11_3"||curId=="SSC_12_3"
		    			||curId=="SSC_14_3"||curId=="SSC_16_3"||curId=="SSC_18_6"){
		    	 // 胆拖
		    	curType = "dt";
		    }
		    else if(curId=="SSC_3_4"||curId=="SSC_11_4"||curId=="SSC_12_4"){
		        // 跨度
		        curType = "kd";
		    }
		    else if(/5_4|5_2|2_2|3_5|4_(4|8)|6_(2|4)|11_5|12_5|(13|14|15|16)_4|18_3/.test(curId)){
		        // 手工录入
		        curType = "text";
		    }
		    else if(/7_1/.test(curId)){
		        // 大小单双
		        curType = "dxds";
		    }
		    else if(/(8|9)_1/.test(curId)){
		        // 任选一、二
		        curType = "rx";
		    }
        // console.log(curType, curId);

        // 重置DOM
        $(".CSR_clear").trigger("click");

        // 创建球区块
        var str = DOMHelper.createBollLine();
        // 插入到 DOM 中
        $(".ssc-main-choose").html(str);

        // 绑定 selector
        self.bindUISelector();

        // 三星组选特殊处理，第二行置灰，不可点击，并与第一行联动
        if(/4_1/.test(curId)){
            // 去除十位机选
            $(".ssc-selector-item").eq(0).find("select, a").hide();
            $(".ssc-selector-item").eq(1).find("select, a").hide();

            $(".ssc-selector-item").eq(1).find("span strong").addClass("ssc-selector-item_gray")
            .off().on("click", function(evt){
                evt.stopPropagation();
            });

            $(".ssc-selector-item").eq(0).find("span strong").on("click", function(){
                var index = $(this).parents("span").index();
                $(".ssc-selector-item").eq(1).find("span strong").eq(index - 1).toggleClass("ssc-selector-item_on");
            });
        }
        
        //限制组选包号最大选择6个
		if (/2_3/.test(curId)) {
			$(".ssc-selector-item span strong").on("click", function(evt) {
				if (!$(this).hasClass("ssc-selector-item_on")
		                 && $(".ssc-selector-item").eq(0).find(".ssc-selector-item_on").size() >= 6) {
					self.alertBox("选号超出个数限制");
					evt.stopImmediatePropagation();
					return;
				}
			});
		}
        
        // 胆拖特殊处理，胆码数量不超过 curNum - 1 个，并且胆码和拖码不重复
        if(curType == "dt"){
            $(".ssc-selector-item span strong").on("click", function(evt){
                var $p = $(this).parents(".ssc-selector-item")
                  , index = $p.index()
                  , $sib = $p.siblings(".ssc-selector-item")
                  , curObjIndex = $(this).parents("span").index();

                if((index == 0) && !$(this).hasClass("ssc-selector-item_on")
                 && $(".ssc-selector-item").eq(0).find(".ssc-selector-item_on").size() >= curNum - 1){
                    self.alertBox("亲，胆码数量不能超过 " + (curNum - 1) + " 个~");

                    evt.stopImmediatePropagation();
                    return;
                }

                $sib.find("span strong").eq(curObjIndex - 1).removeClass("ssc-selector-item_on");

            });
        }
    },

    /**
     * UI 绑定中心，几乎所有的 DOM 绑定都在这里
     */
    bindUI: function(){
        var self = this;

        $(".ssc-resluts-doubling em b").text(MAX_DOUBLING);

        // 绑定球的点击
        $(".ssc-main-choose").on("click", "span strong", function(){
            $(this).toggleClass("ssc-selector-item_on");

            // 刷新当前状态
            self.refresh().init();
        });

        // 当 Text 内容发生变化的时候，刷新当前状态
        $(".ssc-main-choose-text textarea").on("change keyup focus propertychange", function(){
        	$(this).val($(this).val().replace(/\s|\n/g,","));
        	$(this).val($(this).val().replace(/，+|,+|;+|；+/g,","));
        	if($(this).val().substring(0,1)==","){
        		$(this).val($(this).val().substring(1));
        	}
            // 刷新当前状态
            self.refresh().init(); 
        });

        // 清空选择
        $(".CSR_clear").on("click", function(evt){
        	$(".ssc-selector-item a.btn").removeClass("btn_ok");
            evt.preventDefault();

            // 重置
            self.refresh().resetDom();
        });

        // 删除  
        $(".ssc-resluts-list").on("click", "a", function(evt){
            evt.preventDefault();

            $(this).parent().hide(0, function(){
                var $this = $(this);
                setTimeout(function(){
                    $this.remove();
                    self.refresh().setFinal();
                }, 16);
            });
        });

        // 点击添加样式
        $(".ssc-resluts-list").on("click", "div", function(evt){
            evt.preventDefault();

            $(this).addClass("ssc-resluts-list_active")
                .siblings().removeClass("ssc-resluts-list_active");
        });

     // 点击清空内容
        $("#clearResult").on("click", function(evt){
            evt.preventDefault();
            $("#doubling").val(1);
            $(".ssc-resluts-list").html("");
            self.refresh().setFinal();
            $("#totalPeriods").text(1);
            $("#totalAmount").text(0.00);
        });


        // 点击改变倍率
        $(".ssc-resluts-doubling i").on("click", function(){
            var index = $(this).index();
            var $val = $("#doubling");

            var val = Number($val.val());
            if(index == 0){
                $val.val( val - 1 > 0 ? val - 1 : 1);
            } else {
                $val.val(val + 1);
            }

            curDoubling = $val.val();
            $(".ssc-resluts-doubling input").triggerHandler("change");
        });

        // 倍率检测
        $(".ssc-resluts-doubling input").on("change keyup blur", function(){
            if(!/^\d+$/.test($(this).val())){
                $(this).val(curDoubling);
                return;
            }
            if(parseInt($(this).val()) >= MAX_DOUBLING){
                $(".ssc-resluts-doubling em").show();
                $(this).val(MAX_DOUBLING);
                //return;到最大倍数后需要更新金额，所以不能return
            } else {
                $(".ssc-resluts-doubling em").hide();
            }
            self.refresh().setFinal();
        });
    },

    /**
     * 绑定 select 变化时的触发动作
     */
    bindUISelector: function(){
        var total = ConfigParam['total']
          , half = total%2 == 1 ? Math.ceil(total / 2) - 1 : total / 2
          , rnum = []
          , funcPool = {   // 用于操作球的选中状态
                "all": function(index, me){
                    $(me).addClass("ssc-selector-item_on");
                },
                "big": function(index, me){
                    index >= half
                        && $(me).addClass("ssc-selector-item_on");
                },
                "small": function(index, me){
                    index < half
                        && $(me).addClass("ssc-selector-item_on");
                },
                "odd": function(index, me){
                    index % 2 == 0
                        && $(me).addClass("ssc-selector-item_on");
                },
                "even": function(index, me){
                    index % 2 == 1
                        && $(me).addClass("ssc-selector-item_on");
                },
                "other": function(index, me){
                    $.inArray(index, rnum) > -1
                        && $(me).addClass("ssc-selector-item_on");
                }
            }
        , self = this;

       // 当 select 的值发生变化的时候，修改球的选中状态
      $(".ssc-selector-item a.btn").on("change click", function(){
          var $this = $(this)
            , $items = $this.parents(".ssc-selector-item").find("span strong")
            , val = $this.attr("value");

          $this.closest(".ssc-selector-item").find("a.btn").removeClass("btn_ok");
          $this.addClass("btn_ok");
          
          self.refresh().resetDom("don't remove all selector status, just a line");
          $items.removeClass("ssc-selector-item_on");

          if(!isNaN(parseInt(val))){
              rnum = Utils.getSomeRandom(parseInt(val));
              val = "other";
          }

          $items.map(funcPool[val]);
              
          // 刷新当前状态
          self.refresh().init();
      });


        // select 旁边 button 点击选择
        $(".ssc-selector-item select").next("a").on("click", function(evt){
            evt.preventDefault();

            $(this).prev("select").trigger("change");
        });
    },

    /**
     * 选择球 -> 刷新变量 -> 判断 button 是否可以点击
     *
     * @return {Objet} 这是比较关键的一块，主要用于状态判断
     * 
     *      init: function(){            // 刷新当前选中球,并判断是否置灰button
     *          setCurRes();
     *          unDisableBtn();
     *      },
     *      setCurRes: setCurRes,        // 刷新当前选中球
     *      unDisableBtn: unDisableBtn,  // 判断是否置灰button
     *      resetDom: resetDom,          // 重置DOM
     *      setFinal: setFinal           // 设置底部的注数和金额
     */
    refresh: function(){
        var self = this;

        /**
         * 刷新当前选中球
         */
        var setCurRes = function(){
            var res = [];
            $(".ssc-selector-item").each(function(){
                var arr = [];
                $(this).find(".ssc-selector-item_on").map(function(index, me){
                    arr.push($(me).text());
                });
                if(curType=="rx"&&arr.length==0){
                	arr.push("*");
                }
                if(arr.length!=0){
                	 res.push(arr);
                }
            });

            curRes = res;
        };

        /**
         * 判断是否置灰button
         */
        var unDisableBtn = function(){
            var count = self.countor();
            
            if(count == CHECK_UNPASS){
            	// 数字归零
                $("#oneBetCount").text("0");
                $("#oneBetAmount").text("0.00");
                $("#oneAwardAmount").text("0.00");
                $("#oneWinAmount").text("0.00");

                // 置灰 button
                $(".CSR_btn").off().removeClass("CSR_btn_OK");
                $(".CSR_btn").removeClass("btn_xhl");
                $(".CSR_btn").addClass("btn_xhl_op");
                
                return;
            }
            
            var betMoney = count * 2 * Utils.getMoneyModel();
            $("#oneBetCount").text(count); 
            $("#oneBetAmount").text(betMoney).formatCurrency();


            var money = self.calcMoney(count)
              , award = betMoney;
             
            //如果中奖金额有多级则有个中奖金额及盈利区间。
            var awardArea="";
            var profitArea="";
            if(money.indexOf("~")!=-1){
//            	for(var i=0;i<money.split("~").length;i++){
//        		if(awardArea==""){
          		  awardArea = NumberFormat.formatNumber(
          				  (money.split("~")[money.split("~").length-1]*Utils.getMoneyModel()),2,1);
          		  profitArea = NumberFormat.formatNumber(
          				  (money.split("~")[money.split("~").length-1]*Utils.getMoneyModel() - award),2,1);
//          	  }else{
//          		  awardArea += "~"+NumberFormat.formatNumber(
//          				  (money.split("~")[i]*Utils.getMoneyModel()),2,1);
//          		  profitArea += "~"+NumberFormat.formatNumber(
//          				  (money.split("~")[i]*Utils.getMoneyModel()- award),2,1);
//          	  }
//        	}
            }else{
            	awardArea=NumberFormat.formatNumber((money*Utils.getMoneyModel()),2,1);
            	profitArea = parseFloat(money)* Utils.getMoneyModel() - award;
            	profitArea = NumberFormat.formatNumber(profitArea,2,1);
            }
            
            $("#oneAwardAmount").text(awardArea);
            $("#oneWinAmount").text(profitArea);
            // 绑定点击
            $(".CSR_btn").removeClass("btn_xhl_op");
            $(".CSR_btn").addClass("btn_xhl");
            $(".CSR_btn").addClass("CSR_btn_OK").off().one('click', function(evt){
            	 $(this).removeClass("btn_xhl");
            	 $(this).addClass("btn_xhl_op");
            	 $(".ssc-selector-item a.btn").removeClass("btn_ok");
            	 
                evt.preventDefault();

                if(curType == "text"){
	               	//先删除掉错误的单式号码
	       			var textVal = $(".ssc-main-choose-text textarea").val();
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
	       			$(".ssc-main-choose-text textarea").val(textVal);
	       			
	       			$("#textErrMsg").text("");
	       			curRes = $(".ssc-main-choose-text textarea").val().split(/\,/m);
               }else{
               		textErrInfo="";
               }
              

                // 添加一条数据到下方的展示框中
                self.addResItem();
                // 重置DOM
                $(".CSR_clear").trigger("click");
                // 修改
                setFinal();
                if(textErrInfo.trim()!=""){
    				self.alertBox("以下号码错误，已进行自动过滤 <br/> "+textErrInfo);
    			}
            });
        };
        // 重置DOM
        var resetDom = function(tag){
            // 数字归零
        	 $("#oneBetCount").text("0");
             $("#oneBetAmount").text("0.00");
             $("#oneAwardAmount").text("0.00");
             $("#oneWinAmount").text("0.00");

            // 选中状态取消
            !tag && $(".ssc-selector-item span strong").removeClass("ssc-selector-item_on");

            // 选择 button 置灰
            $(".CSR_btn").off().removeClass("CSR_btn_OK");
            $(".CSR_btn").removeClass("btn_xhl");
            $(".CSR_btn").addClass("btn_xhl_op");
            
            // 只有标准选号才支持机选
            if(curType == "standard"){
                $("#randomResult").show();
            } else {
                $("#randomResult").hide();
            }

            // 如果是 text，清空内容
            if(curType == "text"){
                $(".ssc-main-choose-text textarea").val("");
                $("#textErrMsg").text("");
            } 

//            $(".CSR_select option").get(0).selected = "selected";
        };

        // 设置底部的注数和金额
        var setFinal = function(){
            var val = 0;
            var money = 0;
            curDoubling = $("#doubling").val();

            $(".ssc-resluts-list div").each(function(index, me){
                val += Number($(me).find("i").eq(0).text());
                money += Number($(me).find("i").eq(1).text().replace(/,/g, ""));
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
            $(".ssc-resluts-doubling strong").eq(0).text(val)
                .end().eq(1).text(money || "0.00").formatCurrency();
            _updateTotalMoney();
        };
        
        var _updateTotalMoney = function(N) {
			var $num = $(".ssc-resluts-doubling strong").eq(0);
			var $money = $(".ssc-resluts-doubling strong").eq(1);

			var total = 0;
			$(".ssc-resluts-list > div").map(function(index, me) {
				total += Number($(me).attr("data-num"));
			});

			
			//判断是否为追期，联动修改追期信息
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
			
			var N = N || curDoubling;

			$num.text(total);
			var summoney = 0;
			$(".ssc-resluts-list div").each(function(){
				summoney+=parseFloat($(this).find("i").eq(1).text().replace(/,/g, ""));
			});
			if(summoney==0)return;
			var $totalMonery = $("#totalAmount");
			if(!$("#traceBet").prop("checked")){
				$("#totalPeriods").text(1);
				$totalMonery.text(summoney*curDoubling);
				$totalMonery.formatCurrency();
			}else{
				$money.text(summoney);
				$money.formatCurrency();
			}
			
		};
        return {
            init: function(){
                setCurRes();
                unDisableBtn();
            },
            setCurRes: setCurRes,
            unDisableBtn: unDisableBtn,
            resetDom: resetDom,
            setFinal: setFinal,
            _updateTotalMoney:_updateTotalMoney
        };
    },

    /**
     * 算法核心
     * @return {Number} 注数
     */
    countor: function(){
        var self = this
          , ret = 1
          , sum = 0
          , a = curRes[0] && curRes[0].length || 0
          , b = curRes[1] && curRes[1].length || 0;

        switch(curType){
            case "standard":  // 标准
                for(var i = 0; i < curNum; i++){
                    ret *= (curRes[i] && curRes[i].length) || 0;
                }

                if(/4_1/.test(curId)){
                    var c = curRes[2] && curRes[2].length || 0;
                    if(a == 0 || c == 0) return CHECK_UNPASS;
                    ret = a * c - Utils.arrayIntersection(curRes[0], curRes[2]).length;
                }

                return ret == 0 ? CHECK_UNPASS : ret;

            case "rx":        // 任选
            	if(/8_1/.test(curId)){
            		curNum = 1;
            	}else if(/9_1/.test(curId)){
            		curNum = 2;
            	}
            	
                if(curNum == 1){
                    for(var i = 0; i < ConfigParam['stars']; i++){
                    	if(curRes[i]=="*")continue;
                        sum += curRes[i] && curRes[i].length || 0;
                    }
                    return sum == 0 ? CHECK_UNPASS : sum;

                } else if(curNum == 2){
                	var rx2Arr =[];
                	for(var k=0;k<curRes.length;k++){
                		var result =curRes[k];
                		if(result!=undefined&&result!=""&&$.trim(result)!="*"){
                			rx2Arr.push(result);
                		}
                	}
                    for(var i = 0; i < ConfigParam['stars'] - 1; i++){
                        for(var j = i + 1; j < ConfigParam['stars']; j++){
                            sum += (rx2Arr[i] && rx2Arr[i].length || 0) * (rx2Arr[j] && rx2Arr[j].length || 0)
                        }
                    }
                    return sum == 0 ? CHECK_UNPASS : sum;
                }


            case "text":      //  直接上传
                return this._getTextPatternContent();

            case "dxds":      // 大小单双
                ret = a * b;
                return ret == 0 ? CHECK_UNPASS : ret;

            case "bh":        // 包号
                // 二星
                if(/18_7|2_8|2_3|18_4/.test(curId)){
                	//重庆时时彩的2星组选包号
                	if(/2_8|18_7/.test(curId)||a==2){
                		sum = Utils.combination(a, 2);
                		return a < 2 || sum == 0 ? CHECK_UNPASS : sum;
                	}else{
                		if(a>=2){
                			for(var i = 1; i <= a; i++){
                                sum += i;
                            }
                		}else{
                			sum=1;
                		}
                    	//console.info(Utils.combination(a, 2));
                        return a < 2 || sum == 0 ? CHECK_UNPASS : sum;
                	}
                }
                // 三星组三
                if(/4_2/.test(curId)){
                    return a < 2 ? CHECK_UNPASS : a * (a - 1);
                }
                // 三星组六
                if(/4_5/.test(curId)){
                    return a < 3 ? CHECK_UNPASS : Utils.combination(a, 3);
                }
                // 后三一码包号
                if(/10_(1|2|3|7)/.test(curId)){
                    return a < 1 ? CHECK_UNPASS : Utils.combination(a, 1);
                }
                // 后三二码包号
                if(/10_(4|5|6|8)/.test(curId)){
                    return a < 2 ? CHECK_UNPASS : Utils.combination(a, 2);
                }
            case "sum":        // 和值
            	var ret = [], TB;
                for(var i = 0; i < curRes.length; i++){
                    ret = ret.concat(curRes[i]);
                }
                if(/2_4|18_5/.test(curId)){
                    TB = TABLE_2;
                }
                if(/2_6|18_2/.test(curId)){
                    TB = TABLE_2_1;
                }
                if(/3_2|11_2|12_2/.test(curId)){
                    TB = TABLE_3_1;
                }
                if(/4_3|13_3|15_3/.test(curId)){
                    TB = TABLE_3_2;
                }
                if(/4_6|14_2|16_2/.test(curId)){
                    TB = TABLE_3_3;
                }

                $.each(ret, function(index, item){
                    sum += TB[Number(item)];
                });

                return sum == 0 ? CHECK_UNPASS : sum;

            case "kd":        // 跨度，不知道算法，硬编码
                var table = [10, 54, 96, 126, 144, 150, 144, 126, 96, 54];
                $.each(curRes[0], function(index, item){
                    sum += table[Number(item)];
                });

                return sum == 0 ? CHECK_UNPASS : sum;

            case "dt":        // 胆拖
                if(a > 0 && b > 0 && a + b >= curNum){
                    if(/4_7|14_3|16_3/.test(curId) && a + b == curNum){
                        return CHECK_UNPASS;
                    }
                    // 直选，采用排列，组选，使用组合
                    ret = Utils.permutation(b, curNum - a) * Utils.permutation(curNum, a);

                    if(/4_7|2_5|14_3|16_3|18_6/.test(curId)){
                        ret = Utils.combination(b, curNum - a);;
                    }

                    return ret;
                } else {
                    return CHECK_UNPASS;
                }


            default:
                return CHECK_PASS;

        }
    },

    /**
     * 计算金额，可能是一个区间值
     * @return {[type]} [description]
     */
    calcMoney: function(count){
    	$(".ssc-tip").find("i span").toNumber();
        var text = $(".ssc-tip").text()
          , ret = text.match(/([\d+?\.?\d+?万]+)(?=元)/g);
        $(".ssc-tip").find("i span").formatCurrency();
        if(!!!ret){
            /**! 
             * 请规范化玩法介绍中文案的呈现，奖金后面加上"元"作为单位，
             * 并使用阿拉伯数字以及万来表示金额
             */
            self.alertBox("程序出错！");
        }

        $.each(ret, function(index, item){
            !/^[\d\.]+$/.test(item) && (ret[index] = Number(item.slice(0, -1)) * 10000);
        });

        // 五星通选时返回一个总的中奖区间就可以了。
       if(/6_3/.test(curId)){
         return ret[ret.length-1]+"~"+ret[0];
       }
       //私彩玩法 后三一码不定位
       if(/10_/.test(curId)){
    	   if(count<2){
    		   if(/10_(1|2|3)/.test(curId)){
    			   return ret[0]*2+"~"+ret[0];
    		   }else{
    			   return ret[0];
    		   }
    	   }else if(count>3){
    		   return ret[0]*3+"~"+ret[0];
    	   }else{
    		   return ret[0]*count+"~"+ret[0];
    	   }
       }
       
       var retVal="";
	   for(var i=0;i<ret.length;i++){
	   		if(i==0){
	   			retVal = ret[i];
	   		}else{
	   			retVal = ret[i]+"~"+retVal;
	   		}
	   }
      return retVal;
    },
    
    /**
     * 添加一条选择记录
     */
    addResItem: function(data){
        var count = 0, content;
        var betCount = 0;
        betCount = $("#oneBetCount").text();
        switch(curType){
            case "standard": // 标准选球
            case "rx":       // 任选
                for(var i = 0; i < ConfigParam['stars']; i++){
                    if(curRes[i] == undefined || (curRes[i] == "*"&&curType=="standard")) {
                        count++;
                    } else {
                        curRes[i] = curRes[i].join(",");
                    }
                }
                while(count--){
                	if(/11_1|13_1/.test(curId)){
                		//前三在后面加*
                		curRes.push("*");
                	}else if(/12_1|15_1/.test(curId)){
                		var count2 = count/2;
                		//中三在前后加*
                		for(var j=0;j<count2;j++){
                			curRes.push("*");
                			 curRes.unshift("*");
                		}
                		break;
                	}else if(/18_1/.test(curId)){
                		//前二在前面加*
               		 	curRes.push("*");
                	}else{
                		//后三在前面加*
                		 curRes.unshift("*");
                	}
                   
                }
                content = curRes.join("|");
                break;
            case "text":
            	var code = "";
                $.map(curRes, function(ctt){
                    if($.trim(ctt) != "") {
                        content = ctt.replace(/\s+|,/g, ",");
                        content = content.trim();
                        if(code==""){
                        	code+=content.trim();
                        }else{
                        	code+=","+content.trim();
                        }
                    }
                });
            	content = code;
                content = content.trim();
                 var betCode = content;
                 var val =  code.split(/,/m);
				 betCount = val.length;
				 var textShowContent = "";
				 if(content.length>50){
            		 textShowContent = content.substring(0,50)+"...详情";
            	 }else{
            		 textShowContent = content;
            	 }
                 $(".ssc-resluts-list").append(
                     DOMHelper.createResItemTpl(
                         $.extend({type:curFullName+"单式",num:betCount,money:NumberFormat.formatNumber(
                        		 betCount*2*$("#moneyModel").val(),4,0),betCode:" "+betCode.trim()+" ",
                        		 betCount:" "+betCount+" ",content: " " + content + " "
                        		 ,textShowContent: " " + textShowContent + " "}, data || {})
                     )
                 );
                return;
            case "dxds":     // 大小单双
                content = curRes[0].join(",") + "|" + curRes[1].join(",");
                break;
            case "sum":      // 和值
            case "kd":       // 跨度
                content = curRes.join("|").replace("|", ",");
                break;
            case "bh":       // 包号
                content = curRes[0].join(",");
                break;
            case "dt":       // 胆拖
                content = curRes[0].join(",") + "#" + curRes[1].join(",");
                break;
            default:         // 默认
                content = " 『模板错误』 ";
        }
        var betCode = content.trim();
        var textShowContent = "";
		 if(content.length>50){
	   		 textShowContent = content.substring(0,50)+"...详情";
	   	 }else{
	   		textShowContent = content;
	   	 }
        $(".ssc-resluts-list").append(
            DOMHelper.createResItemTpl($.extend({betCode:" "+betCode.replace(/0([1-9])/g, "$1").trim()+" "
            	,betCount:" "+betCount+" ",content: " " + content + " "
            	,textShowContent: " " + textShowContent + " "}, data || {}))
        );
        
        // 结果列表的滚动条滚到底部
        $(".ssc-resluts-list").scrollTop($(".ssc-resluts-list").children().size()*24);
    },

    /**
     * 当 curType 为 text 时，获取 text 中每一行的球信息
     * 检测无误则返回数据
     * 
     * @return {Array} 球信息数组
     */
    _getTextPatternContent: function(){
    	 var val = $(".ssc-main-choose-text textarea").val().split(/\,/m)
         , arrText = []
         , self = this;

       if(!!!$(".ssc-main-choose-text textarea").val()){
    	   textMsg = "";
           return CHECK_UNPASS;
       }

       textMsg = "";
       textErr=[];
	   textErrInfo="";
	   var passText=0;
       for(var i = 0; i < val.length; i++){
           if($.trim(val[i]) == "") {
        	   textMsg = "您好，您输入的第 " + (i + 1) + " 注号码的格式有误 [号码为空，前后多了逗号？] ，请检查！";
        	   textErr.push(val[i]);
				 if(textErrInfo==""){
					 textErrInfo += val[i];
				 }else{
					 textErrInfo += ","+val[i];
				 }
				 continue;
           }
    	  
           var ttmp = "";
           var temp = 1;
           for(var m=0;m<val[i].length;m++){
           	if(ttmp==""){
           		ttmp+=val[i].substring(m,temp);
           	}else{
           		ttmp+=","+val[i].substring(m,temp);
           	}
           	temp++;
           }
           var arrTmp = ttmp.split(/\s+|,/);
           var hashTable = {"length": 0};

           var count = 0;
           for(var k = 0; k < arrTmp.length; k++){
               if(arrTmp[0] == arrTmp[k]) count++;
           }
           if(count==1&&arrTmp[1]==arrTmp[2])count++;
           //组三组六单式豹子检查
           if(arrTmp[0]==arrTmp[1] && arrTmp[1]==arrTmp[2] 
           		&& ("SSC_4_8"==curId||"SSC_14_4"==curId||"SSC_16_4"==curId
        		   ||"SSC_4_4"==curId||"SSC_13_4"==curId||"SSC_15_4"==curId)){
               textMsg = "您好，您输入的第 " + (i + 1) + " 注号码 \""+val[i]+"\" 的格式有误 [号码不能为豹子] ，请检查！";
               textErr.push(val[i]);
				 if(textErrInfo==""){
					 textErrInfo += val[i];
				 }else{
					 textErrInfo += ","+val[i];
				 }
				 continue;
           }
           //组六单式检查
           if(count == arrTmp.length-1 && ("SSC_4_8"==curId||"SSC_14_4"==curId||"SSC_16_4"==curId)){
               textMsg = "您好，您输入的第 " + (i + 1) + " 注号码  \""+val[i]+"\" 的格式有误 [输入的数字必须都不相同] ，请检查！";
               textErr.push(val[i]);
				 if(textErrInfo==""){
					 textErrInfo += val[i];
				 }else{
					 textErrInfo += ","+val[i];
				 }
				 continue;
           }
           //组三单式检查
           if(count != arrTmp.length-1&&("SSC_4_4"==curId||"SSC_13_4"==curId||"SSC_15_4"==curId)){
               textMsg = "您好，您输入的第 " + (i + 1) + " 注号码  \""+val[i]+"\" 的格式有误 [输入的数字必须含有对子] ，请检查！";
               textErr.push(val[i]);
				 if(textErrInfo==""){
					 textErrInfo += val[i];
				 }else{
					 textErrInfo += ","+val[i];
				 }
				 continue;
           }
           // 输入数值错误，数值区间应该是 1 - totalNum
           var isContinue = false;
           for(var j = 0; j < arrTmp.length; j++){
               var valTmp = parseInt(arrTmp[j]);
               if(valTmp > ConfigParam['total'] - 1 ||valTmp < 0){
                   textMsg = "您好，您输入的第 " + (i + 1) + " 注号码  \""+val[i]+"\" 的格式有误 [输入数值错误，数值区间应该是 1 - "+
                       (ConfigParam["total"] - 1) +"] ，请检查！";
                   isContinue = true;
                   textErr.push(val[i]);
    				 if(textErrInfo==""){
    					 textErrInfo += val[i];
    				 }else{
    					 textErrInfo += ","+val[i];
    				 }
    				 break;
               }
               if(hashTable[arrTmp[j]] == null){
                   hashTable[arrTmp[j]] = 1;
                   hashTable.length += 1;
               }
               if(!!!valTmp&&valTmp!=0){
                   textMsg = "您好，您输入的第 " + (i + 1) + " 注号码  \""+val[i]+"\" 的格式有误 [输入了字母？] ，请检查！";
                   isContinue = true;
                   textErr.push(val[i]);
    				 if(textErrInfo==""){
    					 textErrInfo += val[i];
    				 }else{
    					 textErrInfo += ","+val[i];
    				 }
    				 break;
               }
           }

           // 个数错误，应该是 curNum 个
           if(arrTmp.length != curNum){
               textMsg = "您好，您输入的第 " + (i + 1) + " 注号码  \""+val[i]+"\" 的格式有误 [个数错误，应该是" + 
                   curNum + "个] ，请检查！";
               textErr.push(val[i]);
               if(textErrInfo==""){
	   				textErrInfo += val[i];
	   			 }
	   			 continue;
           }
           
           if(!isContinue){
				passText++;
			}
       }

       $("#textErrMsg").text(textMsg);
       return passText;
    },

    /**
     * 弹出层，可扩展
     */
    alertBox: function(msg){
    	$("body").sMsg({
			content : msg,
			autoDisplay : true,
			actionEvent : ""
		});
    },

    /**
     * 机选多注的 select 绑定
     */
    bindMultiSelector: function(){
        var self = this;

        $("#randomResult").on("click", function(){
            var val = 1;

            for(var s = 0; s < val; s++){

                curRes.length = 0;

                if(curType == "standard" && curFullName.indexOf("组三") > -1) {
                    curRes[0] = curRes[1] = [Utils.getRandom()];
                    curRes[2] = [Utils.getRandom()];
                    //组选机选要剔除豹子(组六无机选)
                    var cr0 = parseInt(curRes[0]);
                	var cr1 = parseInt(curRes[1]);
                	var cr2 = parseInt(curRes[2]);
                	if(cr0==cr2&&cr1==cr2){
                    	s=s-1;
                    	continue;
                    }
                } else{
                    for(var i = 0; i < curNum; i++){
                        curRes.push([Utils.getRandom()]);
                    }
                }
                $("#oneBetCount").text(val); 
                self.addResItem({
                    "num": 1,
                    "money": 2*$("#moneyModel").val()
                });
            }

            // 刷新当前状态
            self.refresh().setFinal();
            self.refresh().resetDom();
        });
    },
    setConfigDOM : function(ConfigDOM){
    	ConfigDOM = ConfigDOM;
    }
});


//================================================= SSC/DOMHelper ==//

/**
 * DOMHelper Class
 * 生成 DOM 元素
 *
 * @method init 初始化函数 DOMHelper的函数入口
 * @method createTab 生成顶部 TAB
 */
var DOMHelper = $.extend({}, {

    /**
     * 生成顶部 TAB
     * @return {[type]} [description]
     */
    createTab: function(){
        var str = []
          , i = 0;

        // 遍历配置文件
        for(var key in ConfigDOM){
            str.push('<a href="#" ' + (i++ == 0 ? 'class="ssc-num_on"' : '') + 
                '>' + key + '</a>');
        }
        return str.join("");
    },
    /**
     * [createSubTab 创建 SubTab，input 框和 tip 信息]
     * @param  {Boolean} tplTag 表示是否存在分组
     * @return {String}        生成拼接好的字符串
     */
    createSubTab: function(tplTag){
        // 在注释前加入 "!" 可以防止压缩工具过滤注释
        // 下面用到的模板引擎是 Utils 函数中的 tplEngine。
        return tplTag ? (function(){
            return [
			'<div class="ssc-type-choose g-play-with">',
			'<div class="fl g-play-quick"> <ul>',
			'    <%for(var i = 0; i < this.length; i++){%>',
			'<li>',
			'<div class="h_tit"><%this[i]["group"]%> </div>',
			//'        <strong><%this[i]["group"]%>：</strong>',
			'        <%for(var j = 0; j < this[i]["data"].length; j++){',
			'            var tmp = this[i]["data"][j];',
			'            var id = "SSC_" + tmp["pos"][0] + "_" + tmp["pos"][1];',
			'        %>',
			'<div class="tit">&nbsp;&nbsp;',
			'        <input type="radio" data-ora ="<%tmp["winAmount"]%>" id="<%id%>" '
					+'data-type="<%tmp["type"]%>" data-tip="<%tmp["intro"]%>" data-code="<%tmp["dataCode"]%>" '
					+' data-select="<%tmp["dataSelect"]%>" data-group="<%this[i]["group"]%>" name="ctype" style="display:none;"/>',
			'        <label for="<%id%>"><%tmp["m"]%></label>',
			'</div>',
			'        <%}%>',
			'    </li>',
			'    <%}%>',
			'</div>',
			'<div class="fl g-play-limit" style="line-height: 43px;"></div><div style="clear:both;"></div>',
			'<div class="ssc-tip"><b class="indexIco iPrompt"></b><i>[玩法介绍]：<% this[0]["data"][0]["intro"]%></i>。',
			'<input class="_oriWinAmount" type="hidden" value="<%tmp["winAmount"]%>"/>',
			'<input class="_oriPayoutRatio" type="hidden" value="<%tmp["payoutRatio"]%>"/>',
			'</div>'
            ].join("\n");
        })() : (function(){
            return [
			'<div class="ssc-type-choose g-play-with">',
			'<div class="fl g-play-quick"> <ul>',
			'    <%for(var i = 0; i < this.length; i++){%>',
			'<li>',
			'<div class="h_tit"><%this[i]["group"]%> </div>',
			//'        <strong><%this[i]["group"]%>：</strong>',
			'        <%for(var j = 0; j < this[i]["data"].length; j++){',
			'            var tmp = this[i]["data"][j];',
			'            var id = "SSC_" + tmp["pos"][0] + "_" + tmp["pos"][1];',
			'        %>',
			'<div class="tit">&nbsp;&nbsp;',
			'        <input type="radio" data-ora ="<%tmp["winAmount"]%>" id="<%id%>" '
					+'data-type="<%tmp["type"]%>" data-tip="<%tmp["intro"]%>" data-code="<%tmp["dataCode"]%>" '
					+' data-select="<%tmp["dataSelect"]%>" data-group="<%this[i]["group"]%>" name="ctype" style="display:none;"/>',
			'        <label for="<%id%>"><%tmp["m"]%></label>',
			'</div>',
			'        <%}%>',
			'    </li>',
			'    <%}%>',
			'</div>',
			'<div class="fl g-play-limit" style="line-height: 43px;"></div><div style="clear:both;"></div>',
			'<div class="ssc-tip"><i>[玩法介绍]：<% this[0]["data"][0]["intro"]%></i>。',
			'<input class="_oriWinAmount" type="hidden" value="<%tmp["winAmount"]%>"/>',
			'<input class="_oriPayoutRatio" type="hidden" value="<%tmp["payoutRatio"]%>"/>',
			'</div>'
            ].join("\n");
        })();
    },

    /**
     * 创建球区块右侧的 select
     * @param  {Number} num 开始数
     * @return {String}     DOMString
     */
    ceateItemListSelect: function(num){
//      var ret = ["<select class='_ballSelect' style='display:none;'>"]
//        , opts = {
//              "all": "全部",
//              "big": "大",
//              "small": "小",
//              "odd": "偶数",
//              "even": "奇数"
//          };
//      for(i in opts){
//          ret.push("<option value='" + i + "'>机选" + opts[i] + "</option>");
//      }
//      
//      for(var i = num; i <= ConfigParam['total'] - 1; i++){
//          ret.push("<option value='" + i + "'>机选" + i + "个</option>");
//      }
//
//      ret.push("</select>");
      var ret = [];
      ret.push("<a href='javascript:;' class='btn' style='margin-top:2px;' value='all'>全</a>");
      ret.push("<a href='javascript:;' class='btn' style='margin-top:2px;' value='big'>大</a>");
      ret.push("<a href='javascript:;' class='btn' style='margin-top:2px;' value='small'>小</a>");
      ret.push("<a href='javascript:;' class='btn' style='margin-top:2px;' value='even'>奇</a>");
      ret.push("<a href='javascript:;' class='btn' style='margin-top:2px;' value='odd'>偶</a>");
      ret.push("<a href='javascript:;' class='btn' style='margin-top:2px;' value='6'>六</a>");
      ret.push("<a href='javascript:;' class='btn' style='margin-top:2px;' value='0'>清</a>");

      return ret.join("");
  },
    /**
     * 创建球区块
     * @return {String} DOMString
     */
    createBollLine: function(){
        var self = this
          , ret = ""
          , leftText = [];

        /**
        * @description 多行球的展示
        * @param {Number} line 行数，默认为1
        * @param {Array} table 左侧文案
        * @param {Boolean} tag 是否显示右侧select
        * @return {String} 返回拼接好的字符串
        */
        var chooseLineTpl = function(line, table, tag){  // standard
            var N = ConfigParam['total'] || 10
              , table = table || []
              , str = [];

            str = str.concat([
    		                    '<p class="mt15"></p>'
    		                ]);
            for(var s = 0; s < line; s++){
            	//胆拖的胆码不需要显示冷热遗漏，因为胆码和拖码的数据时想同的。
            	if(curType.indexOf("dt")!=-1&&s==0){
            		str = str.concat([
            		                    '<div class="ssc-selector-item">',
            		                    '<span class="CSI_label"><i>' + (table[s] || '选择号码') + '</i></span>'
            		                ]);
            		for(var i = 0; i < N; i++){
                        str.push("<span><strong>" + i + "</strong></span>");
                    }
            	}else{
            		str = str.concat([
            		                    '<div class="ssc-selector-item">',
            		                    '<span class="CSI_label"><i>' + (table[s] || '选择号码') + '</i><em class="_mTitle">遗漏</em><em class="_hTitle">冷热</em></span>'
            		                ]);
            		for(var i = 0; i < N; i++){
                        str.push("<span><strong>" + i + "</strong><em class='_mNum'>0</em><em class='_hNum'>0</em></span>");
                    }
            	}

            	str = str.concat([
                    (typeof tag == "undefined" || !!tag) ? DOMHelper.ceateItemListSelect(1): "",'</div>'
                ]);
            }

            return str.join("\n");
        };

        /**
        * @description 多行球的和值展示
        * @param {Number} a 和值的最小值
        * @param {Number} b 和值的最大值
        * @param {Number} line 需要拼接的行数
        * @return {String} 返回拼接好的字符串
        */
        var chooseSumTpl = function(a, b, line){  // sum
            var len = b - a + 1
              , perLine = Math.floor(len / line)
              , ret = ""
              ,mod = len%line;

            for(var s = 0; s < line; s++){
                var str = [
                    '<div class="ssc-selector-item">',
                    '<span class="CSI_label"><i>和值数</i><em class="_mTitle">遗漏</em><em class="_hTitle">冷热</em></span>'
                ];

                for(var i = a + perLine * s; i < a + perLine * (s + 1); i++){
                    str.push("<span><strong>" + i + "</strong><em class='_mNum'>0</em><em class='_hNum'>0</em></span>");
                }
                if(mod>0&&s==1){
                		str.push("<span><strong>" + perLine*line + "</strong><em class='_mNum'>0</em><em class='_hNum'>0</em></span>");
                }
                
                str = str.concat([
                    '</div>'
                ]);

                ret += str.join("\n\t");
            }

            return ret;
        };

        /**
        * @description 多行球的大小单双展示
        * @return {String} 返回拼接好的字符串
        */
        var chooseDXDSTpl = function(){  // dxds
            var text = ["十位", "个位"];
            var table = "大小单双".split("");

            for(var s = 0; s < 2; s++){
                var str = [
                    '<div class="ssc-selector-item">',
                    '<span class="CSI_label"><i>' + text[s] + '</i><em class="_mTitle">遗漏</em><em class="_hTitle">冷热</em></span>'
                ];

                for(var i = 0; i < 4; i++){
                    str.push("<span><strong>" + table[i] + "</strong><em class='_mNum'>0</em><em class='_hNum'>0</em></span>");
                }

                str.push('</div>');

                ret += str.join("\n\t");
            }

            return ret;
        };


        $(".ssc-main-choose").show();
        $(".ssc-main-choose-text").hide();

        var table = "个位,十位,百位,千位,万位,十万位,百万位,千万位".split(",");
        switch(curType){
            case "standard":
            	if(curId=="SSC_12_1"||curId=="SSC_15_1"||curId=="SSC_16_1"){
            		table = table.slice(1, curNum+1).reverse();
            	}else if(curId=="SSC_11_1"||curId=="SSC_13_1"||curId=="SSC_14_1"){
            		table = table.slice(2, curNum+2).reverse();
            	}else if(curId=="SSC_18_1"){
            		table = table.slice(1, curNum+1).reverse();
            	}else{
            		table = table.slice(0, curNum).reverse();
            	}
            	
                ret = chooseLineTpl(curNum, table);
                break;
            case "sum":  // 和值
            	var start = 0
                , end = 18
                , line = 1;

              if(curNum == 3 && !!~curId.indexOf("4_3")||curNum == 3 && !!~curId.indexOf("13_3")
              		||curNum == 3 && !!~curId.indexOf("15_3")){
                  start = 1, end = 26, line = 2;
              }
              if((curNum == 3 && !!~curId.indexOf("3_2"))||(curNum == 3 && !!~curId.indexOf("11_2"))
              		||(curNum == 3 && !!~curId.indexOf("12_2"))){
                  start = 0, end = 27, line = 2;
              }
              if(curNum == 3 && !!~curId.indexOf("4_6")||curNum == 3 && !!~curId.indexOf("14_2")
      				||curNum == 3 && !!~curId.indexOf("16_2")){
                  start = 3, end = 24, line = 2;
              }
              if(curNum == 2 && (!!~curId.indexOf("2_4")||!!~curId.indexOf("2_6")
              		||!!~curId.indexOf("18_2")||!!~curId.indexOf("18_5"))){
              	start = 0,end = 18,line = 2;
              }
              ret = chooseSumTpl(start, end, line);
              break;
            case "bh":   // 包号
                table = ['号码'];
                ret = chooseLineTpl(1, table);
                break;
            case "dt":   // 胆拖
                table = ['胆码', '拖码'];
                ret = chooseLineTpl(2, table, false);
                break;
            case "kd":   // 跨度
                table = ['跨度'];
                ret = chooseLineTpl(1, table);
                break;
            case "text": // 手工录入
                $(".ssc-main-choose, .ssc-main-choose-text").toggle();
                break;
            case "dxds": // 大小单双
                ret = chooseDXDSTpl();
                break;
            case "rx":   // 任选一、二
            	if((curId=="SSC_8_1"||curId=="SSC_9_1")&&ConfigParam['stars']==3){
            		//如果是3d排三的任选只有三行号码球
            		table = table.slice(0, 3).reverse();
            	}else{
            		table = table.slice(0, 5).reverse();
            	}
                ret = chooseLineTpl(ConfigParam['stars'], table);
                break;
            default:
                // 默认文案
                ret = "<strong>[ERROR]</strong> 配置参数有误，请注意查看 curType 参数是否设置";
        }

        return ret;
    },

    /**
     * 创建生成一条内容的模板
     * @param  {Object} data 参数
     * @return {String}      DOMString
     */
    createResItemTpl: function(data){
    	var awardAmount = $("#sscType").find("span._amount").asNumber();
        var tpl = ['<div data-num="<%num%>" data-type="<%type%>">',
                    '<a href="#">删除</a>',
                    '<span>[<%type%>]</span>',
                    '<input type="hidden" value="<%code%>"/>',//玩法code
					'<input type="hidden" value="<%content%>"/>',
					'<input type="hidden" value="<%betModel%>"/>',
					'<input type="hidden" value="<%betSelect%>"/>',
					'<input type="hidden" value="<%betCount%>"/>',
					'<input type="hidden" value="<%formToken%>"/>',
					'<input class="_betAward" type="hidden" value="<%awardAmount%>"/>',
                    '<span><%textShowContent%></span>',
                    '(<i><%num%></i>注<i><%money%></i>元)',
                '</div>'].join("");

        // 模板数据
        var data = $.extend({
        	"num": $("#oneBetCount").text(),
            "money": $("#oneBetAmount").text(),
            'type': curFullName,
            'code':$("#" + curId).attr("data-code").trim(),
            'betModel':$("#moneyModel").val().trim(),
			'betSelect':$("#" + curId).attr("data-select").trim(),
            "content": " 『模板错误』 "
        }, data || {});
        
        data["awardAmount"] = awardAmount;
        
        var formToken = MD5.hex_md5((data["money"]+"").replace(",", "")+data["betModel"]+data["betCode"]+data["betCount"]);
		data["formToken"] = formToken;
        // 替换内容的模板
        return tpl.replace(/<%([^%>]+)?%>/g, function(m, a){
            return data[a];
        });             
    }
});


//================================================= SSC/Utils ==//

/**
* Utils Class
* 对ssc类的扩展，添加辅助工具
* 
* @method getRandom 获取随机数
* @method getSomeRandom 获取多个随机数
* @method tplEngine 模板引擎
* @method repeat 字符串重复
* @method arrayIntersection 数组交集
*/
var Utils = $.extend({}, {
    /**
    * @description 获取随机数
    * @param [Array] except 剔除的数
    * @return [Array] 返回一个数组
    */
    getRandom: function(except){

        var total = ConfigParam['total'] || 10;

        if(!except || except.length == 0){
            return Math.floor(Math.random()*total);
        }

        var res, tag = true;
        while(1 && tag){
            res = Math.floor(Math.random()*total);
            ($.inArray(res, except) == -1) && (tag = false);
        }
        return res == total ? res - 1 : res;
    },

    /**
    * @description 获取随机数
    * @param [Number] num 个数
    * @param [Array] except 获取多个随机数
    * @return [Array] 返回一个数组
    */
    getSomeRandom: function(num, except){
        var res = [], args;

        while(num--){
            if(except && res.length == 0){
                args = except;
            } else {
                args = Utils.unique((except || []).concat(res));
            }
            var tmp = this.getRandom(args);
            res.push(tmp);
        }

        return res;
    },

    /**
     * 清空重复数据
     * @param  {[type]} arr [description]
     * @return {[type]}     [description]
     */
    unique: function(arr){
        var hash = {}, i = 0, ret = [];

        for(; i < arr.length; i++ ) !hash[arr[i]] && (hash[arr[i]] = 1)
        for(i in hash) ret.push(Number(i));

        return ret;
    },

    // 模板引擎，详情可以异步我写的一篇博客
    // http://www.cnblogs.com/hustskyking/p/principle-of-javascript-template.html
    tplEngine: function(tpl, data) {
        var reg = /<%([^%>]+)?%>/g, 
            regOut = /(^( )?(if|for|else|switch|case|break|{|}))(.*)?/g, 
            code = 'var r=[];\n', 
            cursor = 0;

        var add = function(line, js) {
            js? (code += line.match(regOut) ? line + '\n' : 'r.push(' + line + ');\n') :
                (code += line != '' ? 'r.push("' + line.replace(/"/g, '\\"') + '");\n' : '');
            return add;
        }
        while(match = reg.exec(tpl)) {
            add(tpl.slice(cursor, match.index))(match[1], true);
            cursor = match.index + match[0].length;
        }
        add(tpl.substr(cursor, tpl.length - cursor));
        code += 'return r.join("");';
        return new Function(code.replace(/[\r\t\n]/g, '')).apply(data);
    },

    /**
     * 重复显示
     * @param  {[type]} str [description]
     * @param  {[type]} N   [description]
     * @return {[type]}     [description]
     */
    repeat: function(str, N){
        var res = ""
        while(N--){
            res += str;
        }
        return res;
    },

    // 组合
    combination: function(a, b){
        var resX = 1, resY = 1, tmp = b;
        while(tmp){
            resX *= a;
            a--;tmp--;
        }
        while(b){
            resY *= b;
            b--;
        }
        return resX / resY;
    },

    // 排列
    permutation : function(a, b){
        var ret = 1;
        while(b){
            ret *= a--;
            b--;
        }
        return ret;
    },

    /** 
    * @description 数据去重
    * @param {Array} a 要比较的数组
    * @param {Array} b 要比较的数组
    * @return {Array} a 和 b 的交集
    */
    arrayIntersection: function (a, b) {
      var ai = 0, bi = 0;
      var result = new Array();

      while( ai < a.length && bi < b.length ){
         if (a[ai] < b[bi] ){ 
            ai++; 
         } else if (a[ai] > b[bi] ){ 
            bi++; 
         } else {
            result.push(a[ai]);
            ai++;
            bi++;
         }
      }
      return result;
    },
    //获取当前圆角分模式
	getMoneyModel:function(){
		var model = $("#moneyModel").val();
		return parseFloat(model);
	}
});

})(window, undefined);

var betForm = function(){
	var callback = {
			"initPage":function(data){
				formPost.showErrorMessage(data.success);
				//Cathectic.init();
				$(".CSR_clear").click();
				$("#clearResult").click();
				$("#doubling").val(1);
				$("._betbtn button").text("立即投注");
				$("#token").val(data.token);
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
			var playCode = $(".ssc-type-choose:visible").find("input[type='radio']:checked").attr("data-code");
			var key = $("#lotCode").val()+","+playCode+","+$("#currentIssno").text();
			values.push(key);
			formPost.submitAjaxForm("getLimits.shtml",attrs,values,callFuc["limits"]);
		},
		packageData:function(attrs,values){
			attrs.push("issueNos");
			var issueNos = $("#currentIssno").text()+":"+$("#doubling").val();
			values.push(issueNos);
			var $reultsDiv = $(".ssc-resluts-list div");
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
				}else if($(this).find("input").eq(1).val().indexOf("#")>-1){
					var betCode = $(this).find("input").eq(1).val().split("#");
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
			var $reultsDiv = $(".ssc-resluts-list div");
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
				}else if($(this).find("input").eq(1).val().indexOf("#")>-1){
					var betCode = $(this).find("input").eq(1).val().split("#");
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
			var $reultsDiv = $(".ssc-resluts-list");
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
					$(".ssc-resluts-list div").each(function(){
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
			formPost.submitAjaxForm1("bet3D.shtml",attrs,values,callback["initPage"],null);
		}
	};
}();