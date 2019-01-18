function orderSubmit(){
	var i=1;
	$("li[name='changeform']").each(function(){
		if($(this).attr("class")=='active'){
			return false;
		}
		i++;
	});
	if(i==1){
		if($("#userStatus1").val()=='true'){
			alert("请填写正确用户名");
			return false;
		}
		if($("#adminPwd1").val()==""){
			alert("权限密码不能为空");
			return;
		}
		singleForm.submit();
	}else if(i==2){
		if($("#userStatus2").val()=='true'||$("#userStatus2").val()==''){
			alert("请填写正确转出用户名");
			return false;
		}
		if($("#userStatus3").val()=='true'||$("#userStatus2").val()==''){
			alert("请填写正确转入用户名");
			return false;
		}
		if($("#adminPwd2").val()==""){
			alert("权限密码不能为空");
			return;
		}
		fundmove.submit();
	}
}

function checkUsetName(){
	jQuery(function(){
		var customerName = $("#customerName1").val();
		var params = "customerName="+customerName;
		$.ajax({
			type : 'post',
			url : 'checkCustomer.do',
			data : params,
			dataType : 'json',
			success : function(data) {
				if(data.entity==""){
					alert("账户不存在");
					$("#money").html("账户不存在");
					$("#userStatus1").val(true);
				}else{
					$("#uname").html(data.entity.customerName);
					if(data.entity.customerLevel == 0){
						$("#ulevel").html("总代");
					}else{
						$("#ulevel").html(data.entity.customerLevel+"级代理");
					}
					$("#ucash").html(data.cash.cash);
					$("#frozencash").html(data.cash.frozenCash);
					$("#check_and_select_user_modal").modal("show");
					$("#userStatus1").val(false);
				}
			},
			error : function() {
			}
		});
	});
}


function checkUsetNameonblur(){
	jQuery(function(){
		var customerName = $("#customerName1").val();
		var params = "customerName="+customerName;
		$.ajax({
			type : 'post',
			url : 'checkCustomer.do',
			data : params,
			dataType : 'json',
			success : function(data) {
				if(data.entity==""){
					alert("账户不存在");
					$("#money").html("账户不存在");
					$("#userStatus1").val(true);
				}else{
					$("#money").html("现金余额:"+data.cash.cash);
					$("#userStatus1").val(false);
				}
			},
			error : function() {
			}
		});
	});
}



function checkUsetNameForm2(){
	jQuery(function(){
		var customerName = $("#customerNamefrom").val();
		var params = "customerName="+customerName;
		$.ajax({
			type : 'post',
			url : 'checkCustomer.do',
			data : params,
			dataType : 'json',
			success : function(data) {
				if(data.entity==""){
					alert("账户不存在");
					$("#outmoney").html("账户不存在");
					$("#userStatus2").val(true);
				}else{
					$("#uname").html(data.entity.customerName);
					if(data.entity.customerLevel == 0){
						$("#ulevel").html("总代");
					}else{
						$("#ulevel").html(data.entity.customerLevel+"级代理");
					}
					$("#ucash").html(data.cash.cash);
					$("#frozencash").html(data.cash.frozenCash);
					$("#userMainId").val(data.entity.id);
					$("#userStatus2").val(false);
					$("#check_and_select_user_modal").modal("show");
					
					
				}
			},
			error : function() {
			}
		});
	});
}

function checkUsetNameForm2onblur(){
	jQuery(function(){
		var customerName = $("#customerNamefrom").val();
		var params = "customerName="+customerName;
		$.ajax({
			type : 'post',
			url : 'checkCustomer.do',
			data : params,
			dataType : 'json',
			success : function(data) {
				if(data.entity==""){
					alert("账户不存在");
					$("#outmoney").html("账户不存在");
					$("#userStatus2").val(true);
				}else{
					$("#userMainId").val(data.entity.id);
					$("#outmoney").html("现金余额:"+data.cash.cash);
					$("#userStatus2").val(false);
				}
			},
			error : function() {
			}
		});
	});
}


function checkLowerCustomer(){
	jQuery(function(){
		var customerName = $("#customerSunNamefrom").val();
		var userMainId = $("#userMainId").val();
		var params = "customerName="+customerName+"&userMainId="+userMainId;
		$.ajax({
			type : 'post',
			url : 'checkLowerCustomers.do',
			data : params,
			dataType : 'json',
			success : function(data) {
				if(data.lowerCustomer==null||data.lowerCustomer==""){
					alert("账户不存在");
					$("#inmoney").html("账户不存在");
					$("#userStatus3").val(true);
				}else{
					$("#uname").html(data.lowerCustomer.customerName);
					if(data.lowerCustomer.customerLevel == 0){
						$("#ulevel").html("总代");
					}else{
						$("#ulevel").html(data.lowerCustomer.customerLevel+"级代理");
					}
					$("#ucash").html(data.cash.cash);
					$("#frozencash").html(data.cash.frozenCash);
					$("#userSunId").val(data.lowerCustomer.id);
					$("#userStatus3").val(false);
					$("#check_and_select_user_modal").modal("show");
				}
			},
			error : function() {
			}
		});
	});
}


function checkLowerCustomeronblur(){
	jQuery(function(){
		var customerName = $("#customerSunNamefrom").val();
		var userMainId = $("#userMainId").val();
		var params = "customerName="+customerName+"&userMainId="+userMainId;
		$.ajax({
			type : 'post',
			url : 'checkLowerCustomers.do',
			data : params,
			dataType : 'json',
			success : function(data) {
				if(data.lowerCustomer==null||data.lowerCustomer==""){
					alert("账户不存在");
					$("#inmoney").html("账户不存在");
					$("#userStatus3").val(true);
				}else{
					$("#userSunId").val(data.lowerCustomer.id);
					$("#inmoney").html("现金余额:"+data.cash.cash);
					$("#userStatus3").val(false);
				}
			},
			error : function() {
			}
		});
	});
}
