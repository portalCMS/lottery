var details = function(){
	return{
		queryDetail: function(action,order_number){
			var attrs = new Array();
			attrs.push("orderNumber");
			var values = new Array();
			values.push(order_number);
			formPost.submitForm(action,attrs,values);
		}
	};
}();