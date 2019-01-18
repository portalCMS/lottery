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
		/**
		 * Usage: format_number(12345.678, 2); result: 12345.68
		 */
		 format_number1 : function(pnumber, decimals) {
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
			return NumberFormat._commaFormatted(result);
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
			return msg;
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