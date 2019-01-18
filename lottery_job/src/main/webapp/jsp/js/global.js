$(document).ready(function() {
	$('.tip').tooltip('hide');
	$('.tip').hover(function() {
		$(this).tooltip('show');
	}, function() {
		$(this).tooltip('hide');
	});
	$(".alert_close_btn").click(function() {
		$(this).parent().hide();
	});
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
	var webPath = "/lottery_job";
	return{
		setFlag : function(status){
			if(status==undefined)status = true;
			this.flag = status;
		},
		submitForm:function(action,attrs,values){
			var submitForm = document.createElement("form"); 
			submitForm.method=method;
			submitForm.action = action;
			if(undefined!=attrs&&attrs!=null&&attrs.length!=0){
				this.buildFrom(submitForm, attrs, values);
			}
			if(flag){
				if(browser.browserMatch()=="IE"){
					submitForm.id="form";
					var rootElement = document.body;
					rootElement.appendChild(submitForm);
					form.submit();
				}else{
					submitForm.submit();
				}
			}
		},
		submitAjaxForm:function(action,attrs,values,ajaxFuc,jsonMap,contextPath){
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
				success : function(data) {
					if(data.errorMsg){
						formPost.showErrorMessage(data.errorMsg);
						return;
					}
					ajaxFuc(data);
				},
				error : function(error) {
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
		showErrorMessage:function(msg){
			Messenger().post({
				message : msg,
				type : 'error',
				showCloseButton : true
			});
		},
		showInfoMessage:function(msg){
			Messenger().post({
				message : msg,
				type : 'info',
				showCloseButton : true
			});
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

var messenger = function(){
	return{
		cofirmMsg:function (infoMsg,confirmAction,cancelAction){
			var msg = Messenger().post({
				  message: infoMsg,
				  actions: {
					  确定: {
					      action: function(){
					    	if(undefined!=confirmAction)confirmAction();
					        msg.hide();
					    }
					  },

				    取消: {
				      action: function(){
				    	  if(undefined!=cancelAction)cancelAction();
				          msg.hide();
				      }
				    }
				  }
				});
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