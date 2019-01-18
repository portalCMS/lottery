var done = function(){
	
	return{
		savedata:function(action){
			formPost.submitForm(action);
		}
	};
}();

$(document).ready(function(){
	$("._addbtn").click(function(){
		done.savedata("addBonusGroups.do");
	});
});