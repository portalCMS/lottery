$(document).ready(function() {
	$('.tipsCover').hover(function() {
		$(this).find(".tipsInfo").show();
	}, function() {
		$(this).find(".tipsInfo").hide();
	});
	$("._amount").formatCurrency();
});

var NumberFormat = function() {
	return{
		/** 下面两个函数都能对浮点数进行四舍五入，保留小数点后两位 * */
		/**
		 * Usage: CurrencyFormatted(12345.678); result: 12345.68
		 */
		_currencyFormatted:function(amount) {
			var i = parseFloat(amount);
			if (isNaN(i)) {
				i = 0.00;
			}
			var minus = '';
			if (i < 0) {
				minus = '-';
			}
			i = Math.abs(i);
			i = parseInt((i + .005) * 100);
			i = i / 100;
			s = new String(i);
			if (s.indexOf('.') < 0) {
				s += '.00';
			}
			if (s.indexOf('.') == (s.length - 2)) {
				s += '0';
			}
			s = minus + s;
			return s;
		},
		format_number1 : function(pnumber){
			pnumber = NumberFormat.formatNumber(pnumber,4,1);
			return pnumber;
		},
		/**
		 * Usage: format_number(12345.678, 2); result: 12345.68
		 */
		 format_number : function(pnumber, decimals) {
			if (isNaN(pnumber)) {
				return 0;
			}
			;
			if (pnumber == '') {
				return 0;
			}
			;

			var snum = new String(pnumber);
			var sec = snum.split('.');
			var whole = parseFloat(sec[0]);
			var result = '';

			if (sec.length > 1) {
				var dec = new String(sec[1]);
				dec = String(parseFloat(sec[1])
						/ Math.pow(10, (dec.length - decimals)));
				dec = String(whole + Math.round(parseFloat(dec))
						/ Math.pow(10, decimals));
				var dot = dec.indexOf('.');
				if (dot == -1) {
					dec += '.';
					dot = dec.indexOf('.');
				}
				while (dec.length <= dot + decimals) {
					dec += '0';
				}
				result = dec;
			} else {
				var dot;
				var dec = new String(whole);
				dec += '.';
				dot = dec.indexOf('.');
				while (dec.length <= dot + decimals) {
					dec += '0';
				}
				result = dec;
			}
			return result;
		},
		/** 每三位数字添加一个逗号，方便阅读 * */
		_commaFormatted : function(amount) {
			var delimiter = ","; // replace comma if desired
			amount = new String(amount);
			var a = amount.split('.', 2);
			var d = a[1];
			var i = parseInt(a[0]);
			if (isNaN(i)) {
				return '';
			}
			var minus = '';
			if (i < 0) {
				minus = '-';
			}
			i = Math.abs(i);
			var n = new String(i);
			var a = [];
			while (n.length > 3) {
				var nn = n.substr(n.length - 3);
				a.unshift(nn);
				n = n.substr(0, n.length - 3);
			}
			if (n.length > 0) {
				a.unshift(n);
			}
			n = a.join(delimiter);
			if (d.length < 1) {
				amount = n;
			} else {
				amount = n + '.' + d;
			}
			amount = minus + amount;
			return amount;
		},

		_addCommas : function(nStr) {
			nStr += '';
			var x = nStr.split('.');
			var x1 = x[0];
			var x2 = x.length > 1 ? '.' + x[1] : '';
			var rgx = /(d+)(d{3})/;

			while (rgx.test(x1)) {
				x1 = x1.replace(rgx, '$1' + ',' + '$2');
			}

			return x1 + x2;
		},
		/** 
	        * 将数值四舍五入后格式化. 
	        * 
	        * @param num 数值(Number或者String) 
	        * @param cent 要保留的小数位(Number) 
	        * @param isThousand 是否需要千分位 0:不需要,1:需要(数值类型); 
	        * @return 格式的字符串,如'1,234,567.45' 
	        * @type String 
	        */  
		 formatNumber : function(num,cent,isThousand) {  
	        num = num.toString().replace(/\$|\,/g,'');  
	  
	        // 检查传入数值为数值类型  
	          if(isNaN(num))  
	            num = "0";  
	  
	        // 获取符号(正/负数)  
	        sign = (num == (num = Math.abs(num)));  
	  
	        num = Math.floor(num*Math.pow(10,cent)+0.50000000001);  // 把指定的小数位先转换成整数.多余的小数位四舍五入  
	        cents = num%Math.pow(10,cent);              // 求出小数位数值  
	        num = Math.floor(num/Math.pow(10,cent)).toString();   // 求出整数位数值  
	        cents = cents.toString();               // 把小数位转换成字符串,以便求小数位长度  
	  
	        // 补足小数位到指定的位数  
	        while(cents.length<cent)  
	          cents = "0" + cents;  
	  
	        if(isThousand) {  
	          // 对整数部分进行千分位格式化.  
	          for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)  
	            num = num.substring(0,num.length-(4*i+3))+','+ num.substring(num.length-(4*i+3));  
	        }  
	  
	        if (cent > 0)  
	          return (((sign)?'':'-') + num + '.' + cents);  
	        else  
	          return (((sign)?'':'-') + num);  
	      }  
	};
}();


var pageInfoHtml = function(){
	return{
		showPageInfoById : function (divId,pageNum,maxCount,pageCount){
			var html = "";
			if(parseInt(pageNum)-1<0){
				pageNum=1;
			}
			if(parseInt(pageNum)-parseInt(pageCount)>=0){
				pageNum = pageCount;
			}
			html += "<li><a href='javaScript:queryRechargeOrders("+(pageNum-1)+")'>上一页</a></li><li>第"
						+pageNum+"页/共"+pageCount+"页</li><li><a href='javaScript:queryRechargeOrders("+(pageNum+1)+")'>下一页</a></li>";
			$("#"+divId).html(html);
		},
		showPageInfoByClass : function (divClass,pageNum,maxCount,pageCount){
			var html = "";
			if(parseInt(pageNum)-1<0){
				pageNum=1;
			}
			if(parseInt(pageNum)-parseInt(pageCount)>=0){
				pageNum = pageCount;
			}
			html += "<li><a href='javaScript:queryRechargeOrders("+(pageNum-1)+")'>上一页</a></li><li>第"
						+pageNum+"页/共"+pageCount+"页</li><li><a href='javaScript:queryRechargeOrders("+(pageNum+1)+")'>下一页</a></li>";
			$("."+divId).html(html);
		}
	};
}();


var formPost = function(){
	var method = "post";
	var flag = true;
	var defaultCheckboxName = "temp";
	var webPath = ctx;
	var tbodyObj;
	var copyTr;
	var errorFunc;
	return{
		setFlag : function(status){
			if(status==undefined)status = true;
			this.flag = status;
		},
		setTbodyObj : function(obj,trObj){
			tbodyObj = obj;
			copyTr = trObj;
		},
		setErrorFunc : function(obj){
			errorFunc = obj;
		},
		doErrorFunc : function(){
			errorFunc();
		},
		submitForm:function(action,attrs,values,noCheck){
			//提交前检查所有表单验证。
			if(noCheck!=true){
				var check = $.checkFrom.checkAllType();
				if(!check){
					return;
				}
			}
			
			
			var submitForm = document.createElement("form"); 
			submitForm.method=method;
			submitForm.action = action;
			if(undefined!=attrs&&attrs!=null&&attrs.length!=0){
				this.buildFrom(submitForm, attrs, values);
			}
			if(flag){
				if(browser.browserMatch()=="IE"){
					submitForm.setAttribute("id", "form");
					var rootElement = document.body;
					rootElement.appendChild(submitForm);
					form.submit();
				}else if(browser.browserMatch()=="firefox"){
					submitForm.setAttribute("id", "form");
					var rootElement = document.body;
					rootElement.appendChild(submitForm);
					document.getElementById("form").submit();
				}else{
					submitForm.submit();
				}
			}
		},
		submitForm1:function(action,attrs,values,div){
			//提交前检查所有表单验证。
			var check = $.checkFrom.checkAllType(div);
			if(!check){
				return;
			}
			
			var submitForm = document.createElement("form"); 
			submitForm.method=method;
			submitForm.action = action;
			if(undefined!=attrs&&attrs!=null&&attrs.length!=0){
				this.buildFrom(submitForm, attrs, values);
			}
			if(flag){
				if(browser.browserMatch()=="IE"||browser.browserMatch()=="firefox"){
					submitForm.id="form";
					var rootElement = document.body;
					rootElement.appendChild(submitForm);
					form.submit();
				}else if(browser.browserMatch()=="firefox"){
					submitForm.setAttribute("id", "form");
					var rootElement = document.body;
					rootElement.appendChild(submitForm);
					document.getElementById("form").submit();
				}else{
					submitForm.submit();
				}
			}
		},
		submitAjaxForm:function(action,attrs,values,ajaxFuc,contextPath){
			//提交前检查所有表单验证。
			var check = $.checkFrom.checkAllType();
			if(!check){
				return;
			}
			var params = "";
			if(undefined!=attrs&&attrs!=null&&attrs.length!=0){
					params = this.buildAjaxParam(attrs, values);
			}
			
			if(contextPath==undefined)contextPath = webPath;
			$.ajax({
				type : 'post',
				url : contextPath+"/"+action,
				data : params,
				dataType : 'json',
				beforeSend:function(){
					if(tbodyObj!=undefined&&tbodyObj!=null){
						$(tbodyObj).html("");
						var tdsize = 6;
						if(copyTr!=undefined&&copyTr!=null){
							tdsize = $(copyTr).find("td").length;
						}else{
							tdsize = $(tbodyObj).find("tr").eq(0).find("td").length;
						}
						var img = document.createElement("img");
						var $img = $(img);
						$img.attr("src",contextPath+"/jsp/images/l_loading.gif");
						var tr = document.createElement("tr");
						var $tr = $(tr);
						var td = document.createElement("td");
						var $td = $(td);
						$td.attr("align","center");
						$td.attr("colspan",tdsize);
						$td.append(img);
						$td.append(" 数据加载中，请稍候…");
						$tr.append($td);
						tbodyObj.append($tr);
					}
				},
				success : function(data) {
					if(data.errorMsg){
						$("#token").val(data.token);
						formPost.showErrorMessage(data.errorMsg);
						formPost.doErrorFunc();
						return;
					}
					ajaxFuc(data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(tbodyObj!=undefined&&tbodyObj!=null){
						$(tbodyObj).html("");
						var tdsize = 6;
						if(copyTr!=undefined&&copyTr!=null){
							tdsize = $(copyTr).find("td").length;
						}else{
							tdsize = $(tbodyObj).find("tr").eq(0).find("td").length;
						}
						var tr = document.createElement("tr");
						$tr = $(tr);
						var td =document.createElement("td");
						$td = $(td);
						$td.attr("colspan",tdsize);
						$td.attr("align","center");
						$td.addClass("f14 color_yellow");
						$td.text("查询异常");
						$tr.append($td);
						tbodyObj.append($tr);
					}
				}
			});
		},
		submitAjaxForm1:function(action,attrs,values,ajaxFuc,div,contextPath){
			//提交前检查所有表单验证。
			if(div!=null){
				var check = $.checkFrom.checkAllType(div);
				if(!check){
					return false;
				}
			}
			
			var params = "";
			if(undefined!=attrs&&attrs!=null&&attrs.length!=0){
					params = this.buildAjaxParam(attrs, values);
			}
			
			if(contextPath==undefined)contextPath = webPath;
			$.ajax({
				type : 'post',
				url : contextPath+"/"+action,
				data : params,
				dataType : 'json',
				beforeSend:function(){
					if(tbodyObj!=undefined&&tbodyObj!=null){
						$(tbodyObj).html("");
						var tdsize = 6;
						if(copyTr!=undefined&&copyTr!=null){
							tdsize = $(copyTr).find("td").length;
						}else{
							tdsize = $(tbodyObj).find("tr").eq(0).find("td").length;
						}
						var img = document.createElement("img");
						var $img = $(img);
						$img.attr("src",contextPath+"/jsp/images/l_loading.gif");
						var tr = document.createElement("tr");
						var $tr = $(tr);
						var td = document.createElement("td");
						var $td = $(td);
						$td.attr("align","center");
						$td.attr("colspan",tdsize);
						$td.append(img);
						$td.append(" 数据加载中，请稍候…");
						$tr.append($td);
						tbodyObj.append($tr);
					}
				},
				success : function(data) {
					if(data.errorMsg){
						$("#token").val(data.token);
						formPost.showErrorMessage(data.errorMsg);
						formPost.doErrorFunc();
						return false;
					}
					ajaxFuc(data);
					return true;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if(tbodyObj!=undefined&&tbodyObj!=null){
						$(tbodyObj).html("");
						var tdsize = 6;
						if(copyTr!=undefined&&copyTr!=null){
							tdsize = $(copyTr).find("td").length;
						}else{
							tdsize = $(tbodyObj).find("tr").eq(0).find("td").length;
						}
						var tr = document.createElement("tr");
						$tr = $(tr);
						var td =document.createElement("td");
						$td = $(td);
						$td.attr("colspan",tdsize);
						$td.attr("align","center");
						$td.addClass("f14 color_yellow");
						$td.text("查询异常");
						$tr.append($td);
						tbodyObj.append($tr);
					}
				}
			});
		},
		buildAjaxParam:function(attrs,values){
			var params = "";
			for(var i=0;i<attrs.length;i++){
				if(params == ""){
					params+=attrs[i]+"="+values[i];
				}else{
					params+="&"+attrs[i]+"="+values[i];
				}
			}
			return params;
		},
		buildFrom:function(obj,attrs,values){
			for(var i=0;i<attrs.length;i++){
				var input = document.createElement("input");
				input.setAttribute("name", attrs[i]) ; 
				input.setAttribute("value", values[i]); 
				obj.appendChild(input) ; 
			}
		},
		packageData:function(dataname,params,values,checkBoxAttr,checkBoxName){
			$("input[name='"+dataname+"']").each(function(){
				if($(this).attr("type")=='checkbox'){
					var valueStr = "";
					$(this).each(function(){
						if(this.checked){
							if(valueStr=="")valueStr=$(this).val();
							valueStr+=","+$(this).val();
						}
					});
					if(valueStr==""){this.showErrorMessage("请选择"+checkBoxName);return false;}
					if(checkBoxName == undefined)checkBoxAttr = defaultCheckboxName;
					params.push($(this).attr(checkBoxAttr));
					values.push(valueStr);
				}else if($(this).attr("type")=='radio'){
					$(this).each(function(){
						if(this.checked){
							params.push($(this).attr("id"));
							values.push($(this).val());
						}
					});
				}else{
					if($(this).attr("checkType")!=undefined){
						var status = $.checkFrom.checkTypeAttr($(this), $(this).attr("checkType"));
						if(!status){flag = false;$(this).focus();$(this).select();return false;}else{flag = true;}
					}
					params.push($(this).attr("id"));
					values.push($(this).val());
				}
				
				//flag = false;
			});
			$("textarea[name='"+dataname+"']").each(function(){
				if($(this).attr("checkType")!=undefined){
					var status = $.checkFrom.checkTypeAttr($(this), $(this).attr("checkType"));
					if(!status){flag = false;$(this).focus();return;}else{flag = true;}
				}
				params.push($(this).attr("id"));
				values.push($(this).val());
			});
		},
		showErrorMessage:function(msg,div){
			if(div!=undefined&&div!=""){
				$("#"+div).addClass("ml15 color_red");
				$("#"+div).html(msg);
			}else{
				$("body").sMsg({
					content : msg,
					autoDisplay : true,
					actionEvent : ""
				});
			}
			
		},
		clearErrorMessage:function(div){
			$("#"+div).html("");
		},
		showInfoTab:function(title,msg,div){
			if(div!=undefined&&div!=""){
				$("#"+div).addClass("ml15 color_red");
				$("#"+div).html(msg);
			}else{
				$("body").sMsg({
					title : title,
					content : msg,
					autoDisplay : true,
					actionEvent : ""
				});
			}
			
		}
	};
}();

var itemVal = function(){
	return{
		getCheckBoxVal:function (name){
			var checkBoxs = document.getElementsByName(name);
			var checkValue="";
			for(var i=0;i<checkBoxs.length;i++){
				if(checkBoxs[i].checked==true){
					if(checkValue==""){
						checkValue=checkBoxs[i].value;
					}else{
						checkValue=checkValue+","+checkBoxs[i].value;
					}
				}
			}
			return checkValue;
		},
		getRadioVal:function (name){
			var checkValue = $("input[name='"+name+"']:checked").val();
			return checkValue;
		}
	};
}();

var browser = function(){
	var userAgent = navigator.userAgent,   
    rMsie = /(msie\s|trident.*rv:)([\w.]+)/,   
    rFirefox = /(firefox)\/([\w.]+)/,   
    rOpera = /(opera).+version\/([\w.]+)/,   
    rChrome = /(chrome)\/([\w.]+)/,   
    rSafari = /version\/([\w.]+).*(safari)/;  
    var browser ="";  
    var version ="";  
    return{
    	uaMatch:function(ua) {  
            var match = rMsie.exec(ua);  
            if (match != null) {  
                return { browser : "IE", version : match[2] || "0" };  
            }  
            var match = rFirefox.exec(ua);  
            if (match != null) {  
                return { browser : match[1] || "", version : match[2] || "0" };  
            }  
            var match = rOpera.exec(ua);  
            if (match != null) {  
                return { browser : match[1] || "", version : match[2] || "0" };  
            }  
            var match = rChrome.exec(ua);  
            if (match != null) {  
                return { browser : match[1] || "", version : match[2] || "0" };  
            }  
            var match = rSafari.exec(ua);  
            if (match != null) {  
                return { browser : match[2] || "", version : match[1] || "0" };  
            }  
            if (match != null) {  
                return { browser : "", version : "0" };  
            }  
        },
	    browserMatch : function(){
	    	var browserMatch = this.uaMatch(userAgent.toLowerCase());  
	    	    if (browserMatch.browser) {  
	    	        browser = browserMatch.browser;  
	    	        version = browserMatch.version;  
	    	    }
	    	  return browser;
	    },
	    getBrowser : function(){
	    	return browser;
	    },
	    getVersion : function(){
	    	return version;
	    }
    };
}();

var jsonFormat = function(){
	return{
		/**
		 * eg: var lottery1 = "[{'key':'www'},{'key2':'ww2'}]";
		 * 根据class来构建json字符串
		 * List的class名称jsonMap.get("classNameList")
		 * List中的obj的class名称jsonMap.get("className")
		 */
		buildAjaxJsonListStr:function(jsonMap){
			var classNameList = jsonMap.get("classNameList");
			var className = jsonMap.get("className");
			var jsonListStr = "[{" ;
			var ps = $(classNameList).size();
			for(var j=0;j<ps;j++){
				var list = $(classNameList).get(j);
				var objs = $(list).find(className);
				for(var i=0;i<objs.size();i++){
					var obj = objs.get(i);
					var name = "'"+$(obj).attr('name')+"'";
					var value = ":'"+$(obj).val()+"',";
					jsonListStr += name + value;
				}
				jsonListStr = jsonListStr.substring(0, jsonListStr.length-1);
				jsonListStr +="},{";
			}
			if(jsonListStr.length==2){
				return null;
			}
			jsonListStr = jsonListStr.substring(0, jsonListStr.length-2);
			jsonListStr +="]"; 
			return jsonListStr;
		},
		/**
		 * eg: var lottery1 = "{'key':'www','key2':'ww2'}";
		 *  根据class来构建json字符串
		 * obj的class名称jsonMap.get("className")
		 */
		buildAjaxJsonStr:function(jsonMap){
			var jsonStr = "{" ;
			var ps = $(jsonMap.get("className")).size();
			for(var j=0;j<ps;j++){
				var attr = $(jsonMap.get("className")).get(j);
				var name = "'"+$(attr).attr('name')+"'";
				var value = ":'"+$(attr).val()+"',";
				jsonStr += name + value;
				jsonStr = jsonStr.substring(0, jsonStr.length-1);
				jsonStr +="}";
			}
			if(jsonListStr.length==1){
				return null;
			}
			return jsonStr;
		}
	};
}();


function Map() {
	this.container = new Object();
}

Map.prototype.put = function(key, value) {
	this.container[key] = value;
};

Map.prototype.get = function(key) {
	return this.container[key];
};

Map.prototype.keySet = function() {
	var keyset = new Array();
	var count = 0;
	for ( var key in this.container) {
		// 跳过object的extend函数
		if (key == 'extend') {
			continue;
		}
		keyset[count] = key;
		count++;
	}
	return keyset;
};

Map.prototype.size = function() {
	var count = 0;
	for ( var key in this.container) {
		// 跳过object的extend函数
		if (key == 'extend') {
			continue;
		}
		count++;
	}
	return count;
};

Map.prototype.remove = function(key) {
	delete this.container[key];
};

Map.prototype.toString = function() {
	var str = "";
	for (var i = 0, keys = this.keySet(), len = keys.length; i < len; i++) {
		str = str + keys[i] + "=" + this.container[keys[i]] + ";\n";
	}
	return str;
};
//添加字符串判断是否以什么结束开始的函数
String.prototype.endsWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
     return false;
  if(this.substring(this.length-s.length)==s)
     return true;
  else
     return false;
  return true;
 };
//添加字符串判断是否以什么开始的函数
 String.prototype.startsWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
   return false;
  if(this.substr(0,s.length)==s)
     return true;
  else
     return false;
  return true;
 };
//ie7,8不支持trim()方法的解决方式。
 String.prototype.trim = function () {
 	return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
 };
 //数组删除(数组下标)
 Array.prototype.remove=function(dx){
	 if(isNaN(dx)||dx>this.length){return false;}
	 for(var i=0,n=0;i<this.length;i++)
	 {
		 if(this[i]!=this[dx])
		 {
			 this[n++]=this[i];
		 }
	 }
	 this.length-=1;
};
 
 
//构建分页选择显示栏
var pageBarBuid = function(){
	return {
		showPageBar : function(pageAjaxFunc,pn,tp){
			$("#_myPageDiv").html("");
			//如果没有数据则页面不显示分页信息。
			if(tp==undefined||tp==0){
				return;
			}
			
			var page = $(".paging").clone(true);
			$(page).find("span").last().text(tp);
			$(page).show();
			$(page).find("._p1").attr('href','javascript:'+pageAjaxFunc+'(1)');
			if(pn>1){
				$(page).find("._pb").attr('href','javascript:'+pageAjaxFunc+'('+(pn-1)+')');
			}else{
				$(page).find("._pb").attr('href','javascript:'+pageAjaxFunc+'(1)');
			}
			if(pn<tp){
				$(page).find("._pn").attr('href','javascript:'+pageAjaxFunc+'('+(pn+1)+')');
			}else{
				$(page).find("._pn").attr('href','javascript:'+pageAjaxFunc+'('+tp+')');
			}
			$(page).find("._pl").attr('href','javascript:'+pageAjaxFunc+'('+tp+')');
			
			var sp = $("#_mypc ._pc").clone(true);
			$(sp).show();
			
			if(tp<=5){
				for(var i=1;i<=tp;i++){
					$(sp).attr('href','javascript:'+pageAjaxFunc+'('+i+')');
					$(sp).html(i);
					if(i==pn){
						$(sp).addClass("current");
					}else{
						$(sp).removeClass("current");
					}
					$(page).find("._pcs").append($(sp).clone(true));
				}
			}else if(tp>=pn+2){
				var i =1;
				if(pn>2){
					i=pn-2;
				}
				var j = i+4;
				
				for(i;i<=j;i++){
					$(sp).attr('href','javascript:'+pageAjaxFunc+'('+i+')');
					$(sp).html(i);
					if(i==pn){
						$(sp).addClass("current");
					}else{
						$(sp).removeClass("current");
					}
					$(page).find("._pcs").append($(sp).clone(true));
				}
				
			}else if(tp<pn+2){
				for(var i=tp-5;i<=tp;i++){
					$(sp).attr('href','javascript:'+pageAjaxFunc+'('+i+')');
					$(sp).html(i);
					if(i==pn){
						$(sp).addClass("current");
					}else{
						$(sp).removeClass("current");
					}
					$(page).find("._pcs").append($(sp).clone(true));
				}
			}
			
			$("#_myPageDiv").append($(page).clone(true));
		}
	};
}();

var Utils={  
	    /* 
	        单位 
	    */  
	    units:'个十百千万@#%亿^&~',  
	    /* 
	        字符 
	    */  
	    chars:'零一二三四五六七八九',  
	    /* 
	        数字转中文 
	        @number {Integer} 形如123的数字 
	        @return {String} 返回转换成的形如 一百二十三 的字符串              
	    */  
	    numberToChinese:function(number){  
	        var a=(number+'').split(''),s=[],t=this;  
	        if(a.length>12){  
	            throw new Error('too big');  
	        }else{  
	            for(var i=0,j=a.length-1;i<=j;i++){  
	                if(j==1||j==5||j==9){//两位数 处理特殊的 1*  
	                    if(i==0){  
	                        if(a[i]!='1')s.push(t.chars.charAt(a[i]));  
	                    }else{  
	                        s.push(t.chars.charAt(a[i]));  
	                    }  
	                }else{  
	                    s.push(t.chars.charAt(a[i]));  
	                }  
	                if(i!=j){  
	                    s.push(t.units.charAt(j-i));  
	                }  
	            }  
	        }  
	        //return s;  
	        return s.join('').replace(/零([十百千万亿@#%^&~])/g,function(m,d,b){//优先处理 零百 零千 等  
	            b=t.units.indexOf(d);  
	            if(b!=-1){  
	                if(d=='亿')return d;  
	                if(d=='万')return d;  
	                if(a[j-b]=='0')return '零'  
	            }  
	            return '';  
	        }).replace(/零+/g,'零').replace(/零([万亿])/g,function(m,b){// 零百 零千处理后 可能出现 零零相连的 再处理结尾为零的  
	            return b;  
	        }).replace(/亿[万千百]/g,'亿').replace(/[零]$/,'').replace(/[@#%^&~]/g,function(m){  
	            return {'@':'十','#':'百','%':'千','^':'十','&':'百','~':'千'}[m];  
	        }).replace(/([亿万])([一-九])/g,function(m,d,b,c){  
	            c=t.units.indexOf(d);  
	            if(c!=-1){  
	                if(a[j-c]=='0')return d+'零'+b  
	            }  
	            return m;  
	        });  
	    }  
	};  

/**
 *create by 2012-08-25 pm 17:48
 *@author hexinglun@gmail.com
 *BASE64 Encode and Decode By UTF-8 unicode
 *可以和java的BASE64编码和解码互相转化
 */
(function(){
	var BASE64_MAPPING = [
		'A','B','C','D','E','F','G','H',
		'I','J','K','L','M','N','O','P',
		'Q','R','S','T','U','V','W','X',
		'Y','Z','a','b','c','d','e','f',
		'g','h','i','j','k','l','m','n',
		'o','p','q','r','s','t','u','v',
		'w','x','y','z','0','1','2','3',
		'4','5','6','7','8','9','+','/'
	];

	/**
	 *ascii convert to binary
	 */
	var _toBinary = function(ascii){
		var binary = new Array();
		while(ascii > 0){
			var b = ascii%2;
			ascii = Math.floor(ascii/2);
			binary.push(b);
		}
		/*
		var len = binary.length;
		if(6-len > 0){
			for(var i = 6-len ; i > 0 ; --i){
				binary.push(0);
			}
		}*/
		binary.reverse();
		return binary;
	};

	/**
	 *binary convert to decimal
	 */
	var _toDecimal  = function(binary){
		var dec = 0;
		var p = 0;
		for(var i = binary.length-1 ; i >= 0 ; --i){
			var b = binary[i];
			if(b == 1){
				dec += Math.pow(2 , p);
			}
			++p;
		}
		return dec;
	};

	/**
	 *unicode convert to utf-8
	 */
	var _toUTF8Binary = function(c , binaryArray){
		var mustLen = (8-(c+1)) + ((c-1)*6);
		var fatLen = binaryArray.length;
		var diff = mustLen - fatLen;
		while(--diff >= 0){
			binaryArray.unshift(0);
		}
		var binary = [];
		var _c = c;
		while(--_c >= 0){
			binary.push(1);
		}
		binary.push(0);
		var i = 0 , len = 8 - (c+1);
		for(; i < len ; ++i){
			binary.push(binaryArray[i]);
		}

		for(var j = 0 ; j < c-1 ; ++j){
			binary.push(1);
			binary.push(0);
			var sum = 6;
			while(--sum >= 0){
				binary.push(binaryArray[i++]);
			}
		}
		return binary;
	};

	var __BASE64 = {
			/**
			 *BASE64 Encode
			 */
			encoder:function(str){
				var base64_Index = [];
				var binaryArray = [];
				for(var i = 0 , len = str.length ; i < len ; ++i){
					var unicode = str.charCodeAt(i);
					var _tmpBinary = _toBinary(unicode);
					if(unicode < 0x80){
						var _tmpdiff = 8 - _tmpBinary.length;
						while(--_tmpdiff >= 0){
							_tmpBinary.unshift(0);
						}
						binaryArray = binaryArray.concat(_tmpBinary);
					}else if(unicode >= 0x80 && unicode <= 0x7FF){
						binaryArray = binaryArray.concat(_toUTF8Binary(2 , _tmpBinary));
					}else if(unicode >= 0x800 && unicode <= 0xFFFF){//UTF-8 3byte
						binaryArray = binaryArray.concat(_toUTF8Binary(3 , _tmpBinary));
					}else if(unicode >= 0x10000 && unicode <= 0x1FFFFF){//UTF-8 4byte
						binaryArray = binaryArray.concat(_toUTF8Binary(4 , _tmpBinary));	
					}else if(unicode >= 0x200000 && unicode <= 0x3FFFFFF){//UTF-8 5byte
						binaryArray = binaryArray.concat(_toUTF8Binary(5 , _tmpBinary));
					}else if(unicode >= 4000000 && unicode <= 0x7FFFFFFF){//UTF-8 6byte
						binaryArray = binaryArray.concat(_toUTF8Binary(6 , _tmpBinary));
					}
				}

				var extra_Zero_Count = 0;
				for(var i = 0 , len = binaryArray.length ; i < len ; i+=6){
					var diff = (i+6)-len;
					if(diff == 2){
						extra_Zero_Count = 2;
					}else if(diff == 4){
						extra_Zero_Count = 4;
					}
					//if(extra_Zero_Count > 0){
					//	len += extra_Zero_Count+1;
					//}
					var _tmpExtra_Zero_Count = extra_Zero_Count;
					while(--_tmpExtra_Zero_Count >= 0){
						binaryArray.push(0);
					}
					base64_Index.push(_toDecimal(binaryArray.slice(i , i+6)));
				}

				var base64 = '';
				for(var i = 0 , len = base64_Index.length ; i < len ; ++i){
					base64 += BASE64_MAPPING[base64_Index[i]];
				}

				for(var i = 0 , len = extra_Zero_Count/2 ; i < len ; ++i){
					base64 += '=';
				}
				return base64;
			},
			/**
			 *BASE64  Decode for UTF-8 
			 */
			decoder : function(_base64Str){
				var _len = _base64Str.length;
				var extra_Zero_Count = 0;
				/**
				 *计算在进行BASE64编码的时候，补了几个0
				 */
				if(_base64Str.charAt(_len-1) == '='){
					//alert(_base64Str.charAt(_len-1));
					//alert(_base64Str.charAt(_len-2));
					if(_base64Str.charAt(_len-2) == '='){//两个等号说明补了4个0
						extra_Zero_Count = 4;
						_base64Str = _base64Str.substring(0 , _len-2);
					}else{//一个等号说明补了2个0
						extra_Zero_Count = 2;
						_base64Str = _base64Str.substring(0 , _len - 1);
					}
				}

				var binaryArray = [];
				for(var i = 0 , len = _base64Str.length; i < len ; ++i){
					var c = _base64Str.charAt(i);
					for(var j = 0 , size = BASE64_MAPPING.length ; j < size ; ++j){
						if(c == BASE64_MAPPING[j]){
							var _tmp = _toBinary(j);
							/*不足6位的补0*/
							var _tmpLen = _tmp.length;
							if(6-_tmpLen > 0){
								for(var k = 6-_tmpLen ; k > 0 ; --k){
									_tmp.unshift(0);
								}
							}
							binaryArray = binaryArray.concat(_tmp);
							break;
						}
					}
				}

				if(extra_Zero_Count > 0){
					binaryArray = binaryArray.slice(0 , binaryArray.length - extra_Zero_Count);
				}

				var unicode = [];
				var unicodeBinary = [];
				for(var i = 0 , len = binaryArray.length ; i < len ; ){
					if(binaryArray[i] == 0){
						unicode=unicode.concat(_toDecimal(binaryArray.slice(i,i+8)));
						i += 8;
					}else{
						var sum = 0;
						while(i < len){
							if(binaryArray[i] == 1){
								++sum;
							}else{
								break;
							}
							++i;
						}
						unicodeBinary = unicodeBinary.concat(binaryArray.slice(i+1 , i+8-sum));
						i += 8 - sum;
						while(sum > 1){
							unicodeBinary = unicodeBinary.concat(binaryArray.slice(i+2 , i+8));
							i += 8;
							--sum;
						}
						unicode = unicode.concat(_toDecimal(unicodeBinary));
						unicodeBinary = [];
					}
				}
				return unicode;
			}
	};

	window.BASE64 = __BASE64;
})();

(function(){
	var __MD5 = function(){
		var hexcase = 0;  /* hex output format. 0 - lowercase; 1 - uppercase        */
		var b64pad  = ""; /* base-64 pad character. "=" for strict RFC compliance   */
		var chrsz   = 8;  /* bits per input character. 8 - ASCII; 16 - Unicode      */


		/*
		 * Calculate the MD5 of an array of little-endian words, and a bit length
		 */
		var core_md5 = function (x, len)
		{
		  /* append padding */
		  x[len >> 5] |= 0x80 << ((len) % 32);
		  x[(((len + 64) >>> 9) << 4) + 14] = len;

		  var a =  1732584193;
		  var b = -271733879;
		  var c = -1732584194;
		  var d =  271733878;

		  for(var i = 0; i < x.length; i += 16)
		  {
		    var olda = a;
		    var oldb = b;
		    var oldc = c;
		    var oldd = d;

		    a = md5_ff(a, b, c, d, x[i+ 0], 7 , -680876936);
		    d = md5_ff(d, a, b, c, x[i+ 1], 12, -389564586);
		    c = md5_ff(c, d, a, b, x[i+ 2], 17,  606105819);
		    b = md5_ff(b, c, d, a, x[i+ 3], 22, -1044525330);
		    a = md5_ff(a, b, c, d, x[i+ 4], 7 , -176418897);
		    d = md5_ff(d, a, b, c, x[i+ 5], 12,  1200080426);
		    c = md5_ff(c, d, a, b, x[i+ 6], 17, -1473231341);
		    b = md5_ff(b, c, d, a, x[i+ 7], 22, -45705983);
		    a = md5_ff(a, b, c, d, x[i+ 8], 7 ,  1770035416);
		    d = md5_ff(d, a, b, c, x[i+ 9], 12, -1958414417);
		    c = md5_ff(c, d, a, b, x[i+10], 17, -42063);
		    b = md5_ff(b, c, d, a, x[i+11], 22, -1990404162);
		    a = md5_ff(a, b, c, d, x[i+12], 7 ,  1804603682);
		    d = md5_ff(d, a, b, c, x[i+13], 12, -40341101);
		    c = md5_ff(c, d, a, b, x[i+14], 17, -1502002290);
		    b = md5_ff(b, c, d, a, x[i+15], 22,  1236535329);

		    a = md5_gg(a, b, c, d, x[i+ 1], 5 , -165796510);
		    d = md5_gg(d, a, b, c, x[i+ 6], 9 , -1069501632);
		    c = md5_gg(c, d, a, b, x[i+11], 14,  643717713);
		    b = md5_gg(b, c, d, a, x[i+ 0], 20, -373897302);
		    a = md5_gg(a, b, c, d, x[i+ 5], 5 , -701558691);
		    d = md5_gg(d, a, b, c, x[i+10], 9 ,  38016083);
		    c = md5_gg(c, d, a, b, x[i+15], 14, -660478335);
		    b = md5_gg(b, c, d, a, x[i+ 4], 20, -405537848);
		    a = md5_gg(a, b, c, d, x[i+ 9], 5 ,  568446438);
		    d = md5_gg(d, a, b, c, x[i+14], 9 , -1019803690);
		    c = md5_gg(c, d, a, b, x[i+ 3], 14, -187363961);
		    b = md5_gg(b, c, d, a, x[i+ 8], 20,  1163531501);
		    a = md5_gg(a, b, c, d, x[i+13], 5 , -1444681467);
		    d = md5_gg(d, a, b, c, x[i+ 2], 9 , -51403784);
		    c = md5_gg(c, d, a, b, x[i+ 7], 14,  1735328473);
		    b = md5_gg(b, c, d, a, x[i+12], 20, -1926607734);

		    a = md5_hh(a, b, c, d, x[i+ 5], 4 , -378558);
		    d = md5_hh(d, a, b, c, x[i+ 8], 11, -2022574463);
		    c = md5_hh(c, d, a, b, x[i+11], 16,  1839030562);
		    b = md5_hh(b, c, d, a, x[i+14], 23, -35309556);
		    a = md5_hh(a, b, c, d, x[i+ 1], 4 , -1530992060);
		    d = md5_hh(d, a, b, c, x[i+ 4], 11,  1272893353);
		    c = md5_hh(c, d, a, b, x[i+ 7], 16, -155497632);
		    b = md5_hh(b, c, d, a, x[i+10], 23, -1094730640);
		    a = md5_hh(a, b, c, d, x[i+13], 4 ,  681279174);
		    d = md5_hh(d, a, b, c, x[i+ 0], 11, -358537222);
		    c = md5_hh(c, d, a, b, x[i+ 3], 16, -722521979);
		    b = md5_hh(b, c, d, a, x[i+ 6], 23,  76029189);
		    a = md5_hh(a, b, c, d, x[i+ 9], 4 , -640364487);
		    d = md5_hh(d, a, b, c, x[i+12], 11, -421815835);
		    c = md5_hh(c, d, a, b, x[i+15], 16,  530742520);
		    b = md5_hh(b, c, d, a, x[i+ 2], 23, -995338651);

		    a = md5_ii(a, b, c, d, x[i+ 0], 6 , -198630844);
		    d = md5_ii(d, a, b, c, x[i+ 7], 10,  1126891415);
		    c = md5_ii(c, d, a, b, x[i+14], 15, -1416354905);
		    b = md5_ii(b, c, d, a, x[i+ 5], 21, -57434055);
		    a = md5_ii(a, b, c, d, x[i+12], 6 ,  1700485571);
		    d = md5_ii(d, a, b, c, x[i+ 3], 10, -1894986606);
		    c = md5_ii(c, d, a, b, x[i+10], 15, -1051523);
		    b = md5_ii(b, c, d, a, x[i+ 1], 21, -2054922799);
		    a = md5_ii(a, b, c, d, x[i+ 8], 6 ,  1873313359);
		    d = md5_ii(d, a, b, c, x[i+15], 10, -30611744);
		    c = md5_ii(c, d, a, b, x[i+ 6], 15, -1560198380);
		    b = md5_ii(b, c, d, a, x[i+13], 21,  1309151649);
		    a = md5_ii(a, b, c, d, x[i+ 4], 6 , -145523070);
		    d = md5_ii(d, a, b, c, x[i+11], 10, -1120210379);
		    c = md5_ii(c, d, a, b, x[i+ 2], 15,  718787259);
		    b = md5_ii(b, c, d, a, x[i+ 9], 21, -343485551);

		    a = safe_add(a, olda);
		    b = safe_add(b, oldb);
		    c = safe_add(c, oldc);
		    d = safe_add(d, oldd);
		  }
		  return Array(a, b, c, d);

		};

		/*
		 * These functions implement the four basic operations the algorithm uses.
		 */
		var md5_cmn = function(q, a, b, x, s, t)
		{
		  return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s),b);
		};
		var md5_ff = function(a, b, c, d, x, s, t)
		{
		  return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
		};
		var md5_gg = function (a, b, c, d, x, s, t)
		{
		  return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
		};
		var md5_hh = function (a, b, c, d, x, s, t)
		{
		  return md5_cmn(b ^ c ^ d, a, b, x, s, t);
		};
		var md5_ii = function(a, b, c, d, x, s, t)
		{
		  return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
		};

		/*
		 * Calculate the HMAC-MD5, of a key and some data
		 */
		var core_hmac_md5 = function(key, data)
		{
		  var bkey = str2binl(key);
		  if(bkey.length > 16) bkey = core_md5(bkey, key.length * chrsz);

		  var ipad = Array(16), opad = Array(16);
		  for(var i = 0; i < 16; i++)
		  {
		    ipad[i] = bkey[i] ^ 0x36363636;
		    opad[i] = bkey[i] ^ 0x5C5C5C5C;
		  }

		  var hash = core_md5(ipad.concat(str2binl(data)), 512 + data.length * chrsz);
		  return core_md5(opad.concat(hash), 512 + 128);
		};

		/*
		 * Add integers, wrapping at 2^32. This uses 16-bit operations internally
		 * to work around bugs in some JS interpreters.
		 */
		var safe_add = function(x, y)
		{
		  var lsw = (x & 0xFFFF) + (y & 0xFFFF);
		  var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
		  return (msw << 16) | (lsw & 0xFFFF);
		};

		/*
		 * Bitwise rotate a 32-bit number to the left.
		 */
		var bit_rol = function(num, cnt)
		{
		  return (num << cnt) | (num >>> (32 - cnt));
		};

		/*
		 * Convert a string to an array of little-endian words
		 * If chrsz is ASCII, characters >255 have their hi-byte silently ignored.
		 */
		var str2binl = function(str)
		{
		  var bin = Array();
		  var mask = (1 << chrsz) - 1;
		  for(var i = 0; i < str.length * chrsz; i += chrsz)
		    bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (i%32);
		  return bin;
		};

		/*
		 * Convert an array of little-endian words to a string
		 */
		var binl2str =function(bin)
		{
		  var str = "";
		  var mask = (1 << chrsz) - 1;
		  for(var i = 0; i < bin.length * 32; i += chrsz)
		    str += String.fromCharCode((bin[i>>5] >>> (i % 32)) & mask);
		  return str;
		};

		/*
		 * Convert an array of little-endian words to a hex string.
		 */
		var binl2hex = function (binarray)
		{
		  var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
		  var str = "";
		  for(var i = 0; i < binarray.length * 4; i++)
		  {
		    str += hex_tab.charAt((binarray[i>>2] >> ((i%4)*8+4)) & 0xF) +
		           hex_tab.charAt((binarray[i>>2] >> ((i%4)*8  )) & 0xF);
		  }
		  return str;
		};

		/*
		 * Convert an array of little-endian words to a base-64 string
		 */
		var binl2b64 = function(binarray)
		{
		  var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		  var str = "";
		  for(var i = 0; i < binarray.length * 4; i += 3)
		  {
		    var triplet = (((binarray[i   >> 2] >> 8 * ( i   %4)) & 0xFF) << 16)
		                | (((binarray[i+1 >> 2] >> 8 * ((i+1)%4)) & 0xFF) << 8 )
		                |  ((binarray[i+2 >> 2] >> 8 * ((i+2)%4)) & 0xFF);
		    for(var j = 0; j < 4; j++)
		    {
		      if(i * 8 + j * 6 > binarray.length * 32) str += b64pad;
		      else str += tab.charAt((triplet >> 6*(3-j)) & 0x3F);
		    }
		  }
		  return str;
		};
		return{
			hex_md5 : function(s){ return binl2hex(core_md5(str2binl(s), s.length * chrsz));},
			b64_md5 : function(s){ return binl2b64(core_md5(str2binl(s), s.length * chrsz));},
			str_md5 : function (s){ return binl2str(core_md5(str2binl(s), s.length * chrsz));},
			hex_hmac_md5 : function(key, data) { return binl2hex(core_hmac_md5(key, data)); },
			b64_hmac_md5 : function(key, data) { return binl2b64(core_hmac_md5(key, data)); },
			str_hmac_md5 : function(key, data) { return binl2str(core_hmac_md5(key, data)); }
		};
	}();
	window.MD5 = __MD5;
})();