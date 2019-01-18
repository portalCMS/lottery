$(document).ready(function() {
	$("._saveUserRoleBtn").click(function() {
		var s='';
		$("input[type='checkbox']:checked").each(function() {
			s += $(this).val() + ',';
		});
		//alert(s);
		var attrs = new Array();
		var values = new Array();
		var roleIds = s;
		var userId = $("#userId").val();
		attrs.push("roleIds");
		attrs.push("userId");
		values.push(roleIds);
		values.push(userId);
		formPost.submitForm("saveadminuserrole.do",attrs,values);
	});

});