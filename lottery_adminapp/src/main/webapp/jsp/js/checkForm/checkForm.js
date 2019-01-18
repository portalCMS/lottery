$.checkFrom = function() {
	return {
		/**
		 * 表单验证绑定，notFocus为true即检查不通过后不focus到对应的出错的地方。
		 * @param attrs
		 * @param isNotBindCheckBox
		 * @param isNotBindradio
		 * @param notFocus
		 * @returns
		 */
		initBind : function(attrs, isNotBindCheckBox, isNotBindradio,notFocus) {
			var attrObj;
			if(attrs.startsWith(".")||attrs.startsWith("#")){
				attrObj=$(attrs);
			}else{
				attrObj=$("input[name='" + attrs + "']");
			}
			$(attrObj).each(
					function() {
						if (isNotBindCheckBox != undefined
								&& !isNotBindCheckBox) {
							$(this).on(
									"blur",
									function() {
										$.checkFrom.checkTypeAttr($(this), $(
												this).attr("checkType"),notFocus);
									});
						} else if (isNotBindradio != undefined
								&& !isNotBindradio) {
							$(this).on(
									"blur",
									function() {
										$.checkFrom.checkTypeAttr($(this), $(
												this).attr("checkType"),notFocus);
									});
						} else if($(this).is('input')){
							$(this).on(
									"blur",
									function() {
										$.checkFrom.checkTypeAttr($(this), $(
												this).attr("checkType"),notFocus);
									});
						}
					});
		},
		checkTypeAttr : function(obj, types,notFocus) {
			var newflag = true;
			var type = types.split(",");
			for (var i = 0; i < type.length; i++) {
				switch (type[i]) {
				case 'isNotEmpty':
					if($(obj).val()==""){
						formPost.showErrorMessage($(obj).attr('alt').split(",")[i]);
						if(notFocus!=true){
							$(obj).select();
							$(obj).focus();
						}
						newflag = false;
					}
					break;
				default:
					var re =new RegExp(getRex(type[i]));
					if(!re.test($(obj).val())){
						formPost.showErrorMessage($(obj).attr('alt').split(",")[i]);
						if(notFocus!=true){
							$(obj).select();
							$(obj).focus();
						}
						newflag = false;
					}
					break;
				}
				if (!newflag)
					break;
			}
			return newflag;
		},
		/**
		 * 检查所有已绑定的input text属性看是否检验通过。
		 * @returns
		 */
		checkAllType:function(attrs){
			if(attrs==undefined||attrs==null){
				attrs="";
			}
			var inputs = $(attrs+" input");
			for(var i=0;i<inputs.size();i++){
				var input = inputs.get(i);
				var attr = $(input).attr('checkType');
				if(attr!=null&&attr!=""){
					var isok = $.checkFrom.checkTypeAttr($(input), $(input).attr("checkType"));
					if(!isok){
						return false;
					}
				}
			}
			return true;
		}
	};
	
}();