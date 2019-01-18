<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>


<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>index</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
</head>
<script src="${contextPath }/jsp/js/finance/binding_bank.js"></script>
<script>
	$(document).ready(function() {
		$("#error_msg").hide();
		$("#pcode").attr("src", "${contextPath }/picode/ycode.do?key=addbankkey" );
		$("#pcode").attr("title", "点击刷新");
		$("#pcode").bind("click", function() {
			$("#pcode").attr("src", "${contextPath }/picode/ycode.do?key=addbankkey&&random="+Math.random());
		});
	});
</script>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/showBankManage.do">银行卡管理</a></li>
			<li><a href="${contextPath }/showBankManage.do">银行管理</a></li>
			<li class="active">添加新银行</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">新银行信息</div>
			<div class="panel-body">
				<div class="row">
					<div class="alert alert-danger alert-dismissable" id='error_msg' >
						<button type="button" class="close alert_close_btn">
							&times;
						</button>
						<div id="error_msg_val">用户名或密码错误</div>
					</div>
					<div class="col-sm-6">
						<form class="form-horizontal" role="form" action="addBank.do" method="post" id="bankFrom">
							<div class="form-group">
								<label for="bank_name" class="col-sm-2 control-label">银行名称</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="name" name="name" />
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-2 control-label">链接地址</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="url" name="url"
										placeholder="http://www.domain.com">
								</div>
							</div>
							<div class="form-group">
								<label for="max_money" class="col-sm-2 control-label">最大金额</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="moneyMax" name="moneyMax">
									<span class="help-block">单笔充值</span>
								</div>
							</div>
							<div class="form-group">
								<label for="mix_money" class="col-sm-2 control-label">最小金额</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="moneyMin" name="moneyMin">
									<span class="help-block">单笔充值</span>
								</div>
							</div>
							<div class="form-group">
								<label for="mix_money" class="col-sm-2 control-label" >作废时间</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" style="width: 70px"
										value="145" id="cancelTime" name="cancelTime"> <span class="help-block">生成充值订单后，在X分钟内无操作则此订单以作废方式处理</span>
								</div>
								<div class="col-sm-5 col-sm-pull-3">
									<p class="form-control-static">分钟/每订单</p>
								</div>
							</div>
							<div class="form-group">
								<label for="bz" class="col-sm-2 control-label">帮助描述</label>
								<div class="col-sm-10">
									<textarea class="form-control" name="describe"></textarea>
									<span class="help-block">支持HTML标签，充值显示的帮助文档</span>
								</div>
							</div>
							<div class="form-group">
								<label for="fy" class="col-sm-2 control-label">附言</label>
								<div class="col-sm-10">
									<label class="radio-inline"> <input type="radio"
										id="psStatus" value="1" name="psStatus"
										checked="checked"> 开启
									</label> <label class="radio-inline"> <input type="radio"
										id="psStatus" value="0" name="psStatus"> 关闭
									</label>
								</div>
							</div>
							<div class="split"></div>
							<div class="form-group">
								<div class="col-sm-2 col-sm-offset-2">
									<input type="number" class="form-control form-inline "
										value="4" name="psNum" id="psNum" />
								</div>
								<div class="col-sm-1">
									<p class="form-control-static">位</p>
								</div>
								<div class="col-sm-7 ">
									<label class="radio-inline"> <input type="radio"
										id="inlineCheckbox1" value="1" name="ps"
										checked="checked"> 纯数字
									</label> <label class="radio-inline"> <input type="radio"
										id="inlineCheckbox2" value="2" name="ps"> 纯字母
									</label> <label class="radio-inline"> <input type="radio"
										id="inlineCheckbox2" value="3" name="ps"> 数字字母组合
									</label>
								</div>

							</div>
							<div class="form-group">
								<label for="fy" class="col-sm-2 control-label">操作权限</label>
								<div class="col-sm-10">
									<label class="checkbox-inline"> <input type="checkbox"
										id="inlineCheckbox1" value="1" name="add"
										checked="checked"> 充值
									</label> <label class="checkbox-inline"> <input type="checkbox"
										id="inlineCheckbox2" value="1" name="out"> 提款
									</label> <label class="checkbox-inline"> <input type="checkbox"
										id="inlineCheckbox2" value="1" name="bind"> 绑卡
									</label> <span class="help-block">影响用户使用相关功能时的权限</span>
								</div>
							</div>

							<div class="split"></div>
							<div class="form-group">
								<label for="pwd" class="col-sm-2 control-label">财务密码</label>
								<div class="col-sm-5">
									<input type="password" class="form-control" id="pwd" name="pwd">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">验证码</label>
								<div class="col-sm-2">
									<input type="text" class="form-control " name="code"/>
									<input type="hidden" name="pickey" value="addbankkey" />
								</div>
								<div class="col-sm-2">
									<img
										src="${contextPath }/picode/ycode.do?key=addbankkey"
										width="100" height="35" id="pcode" name="pcode"/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="button" class="btn btn-danger" onclick="checkAddBank()">确定绑定</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

	</div>

</body>
</html>
