$(document).ready(function(){
	$("#userQuery").on('click',function(){
		users.queryUser();
	});
	
	$("#restPwd").on('click',function(){
		users.restPwd();
	});
});

var users = function(){
	return{
		restPwd:function(){
			if(!confirm("亲,是否确认重置用户登录密码?")){
				return;
			}
			var attrs=[];
			var vals=[];
			
			attrs.push("customerName");
			vals.push($("#userName").val());
			
			var action = "user/restPwd.do";
			formPost.submitAjaxForm(action,attrs,vals,this.restPwdBack);
		},
		restPwdBack:function(data){
			formPost.showErrorMessage(data.info);
		},
		queryUser:function(){
			if($("#userName").val().trim()==""){
				formPost.showErrorMessage("请填写用户名");
				return;
			}
			var attrs=[];
			var vals=[];
			
			attrs.push("customerName");
			vals.push($("#userName").val());
			
			var action = "user/queryUser.do";
			formPost.submitAjaxForm(action,attrs,vals,this.queryUserBack);
			$("#myTb").html("");
		},
		queryUserBack:function(data){
			$("#myTb").html("");
			var user = data.user;
			var $tr = $("._hidTr:last").clone(true);
			$tr.show();
			$tr.find("td").eq(0).html(user.id);
			$tr.find("td").eq(1).html(user.customerName);
			$tr.find("td").eq(2).html(user.customerAlias);
			$tr.find("td").eq(3).html(user.rebates);
			if(user.customerStatus==10002){
				$tr.find("td").eq(4).html("<label class=\"label label-success\">正常</label>");
			}else{
				$tr.find("td").eq(4).html("<label class=\"label label-danger\">冻结</label>");
			}
			$tr.find("td").eq(5).html(user.email);
			$tr.find("td").eq(6).html(user.qq);
			
			$tr.find("td").eq(7).find("a").unbind();
			$tr.find("td").eq(7).find("a").on('click',function(){
				var action =ctx+"/user/showUserInfo/"+user.id+".do";
				window.open(action);
			});
			$("#myTb").append($tr.clone(true));
			
			$('#myTb').find("td").css({
				'background-color' : '#FFF'
			});
		}
	};
}();

