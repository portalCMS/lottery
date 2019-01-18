$(document).ready(
		function() {
			$('#adminRoleType').change(function() {
				var roleId = $("#adminRoleType").val();
				$.ajax({
					type : 'post',
					url : 'showPermissions.do',
					data:{roleId:roleId},
					success : function(data) {
						$("#adminGroup").html(data);
					},
					error : function() {
					}
				});
			});
			$("._savepermissionsBtn").click(
					function() {
						var slength = $("input[type='checkbox']:checked").size();
						if (slength == 0) {
							alert("请选择权限");
							return false;
						}
						var s = "";
						$("input[type='checkbox']:checked").each(
								function() {
									s += $(this).val() + '#';
								});
							var attrs = new Array();
							var values = new Array();
							var permissionsIds = s;
							var roleId = $("#adminRoleType").val();
							attrs.push("roleId");
							attrs.push("permissionsIds");
							values.push(roleId);
							values.push(permissionsIds);
							formPost.submitForm("addAdminRolePermissions.do",attrs,values);
					});
			

		});

function oncheckclick(num) {
//	//$("input[id='" + num + "']").removeAttr("checked");disabled
	var h="";
	var c="";
	var istrue=0;
	h=$("input[id='"+num+"']").val();
	var s=h.split(",");
	c=s[1];
	$("input[id='"+c+"']").get(0).checked=true;
	
	$("input[type='checkbox']:checked").each(
			function() {
				var strs=$(this).val();
				var ss=strs.split(",");
				if(ss[1]==c){
					if($("input[id='"+ss[0]+"']").get(0).checked==true){
						istrue+=1;
					}
				}
	});
	if(istrue>1){
		$("input[id='"+c+"']").get(0).checked=true;
	}else{
		$("input[id='"+c+"']").get(0).checked=false;
	}
//	if($("input[id='"+s[0]+"']").get(0).checked==false){
//		$("input[id='"+c+"']").get(0).checked=false;
//	}
}


function onbiatclick(num) {
	if($("input[id='"+num+"']").get(0).checked==false){
		$("input[type='checkbox']:checked").each(
				function() {
					var strs=$(this).val();
					var ss=strs.split(",");
					if(ss[1]==num){
						$("input[id='"+ss[0]+"']").get(0).checked=false;
					}
		});
	}else{
		$("input[type='checkbox']").each(
				function() {
					var strs=$(this).val();
					var ss=strs.split(",");
					if(ss[1]==num){
						$("input[id='"+ss[0]+"']").get(0).checked=true;
					}
		});
		
	}
}