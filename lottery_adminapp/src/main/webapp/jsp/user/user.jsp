<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<title>index</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/user/user.js"></script>
<script>
	$(document).ready(function() {
		$("#join_bank_btn").click(function() {
			getBankAndCard();
			$("#join_bank").modal("show");
		});
	});
</script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/user/showUserManage.do">用户管理</a></li>
			<li class="active">用户详情</li>
		</ol>
		<div class="row">
			<c:forEach items="${userTree }" var="user">
				<div class="col-sm-3">
					<div class="well well-sm">
						<div class="pull-left">
							<a href="${contextPath }/user/showUserInfo/${user[0] }.do">${user[1]}</a>&nbsp;
							<span class="text-muted"> <c:if
									test="${user[2]==0 }">总代</c:if> <c:if
									test="${user[2]> 0 }">${user[2]}级代理</c:if>
							</span>
						</div>
						<div class="pull-right text-danger">
							<fmt:formatNumber value="${user[3] }" pattern="0.0%" />
						</div>
						<div class="clearfix"></div>
<!-- 						<div class="split"> -->
<%-- 							团队本月投注额：<span class="text-danger"><fmt:formatNumber value="${user[2] }" groupingUsed="true"/></span> --%>
<!-- 						</div> -->
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="panel panel-info">
			<div class="panel-heading">用户下级</div>
			<div class="panel-body">
				<table
					class="table table-hover table-bordered table-striped table-condensed">
					<thead>
						<tr class="text-center success">
							<td>#</td>
							<td>用户名</td>
							<td>类型</td>
							<td>返点</td>
<!-- 							<td>本月投注量(自己+团队)</td> -->
							<td>下线人数</td>
							<td>状态</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lowerUser }" var="user">
							<tr>
								<td>${user[0] }</td>
								<td>${user[1]}</td>
								<td>${user[2]}级代理</td>
								<td><fmt:formatNumber value="${user[3] }"
										pattern="0.0%" /></td>
<%-- 								<td class="text-danger"><fmt:formatNumber value="${user[6] }" groupingUsed="true" /></td> --%>
								<td>${user[4]}</td>
								<td>
									<c:if test="${user[5] == 10002}"><label class="label label-success">正常</label></c:if>
									<c:if test="${user[5] == 10001}"><label class="label label-success">冻结</label></c:if>
								</td>
								<td><a href="${contextPath }/user/showUserInfo/${user[0] }.do"
									class="btn btn-link btn-sm">查看详情</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 
		<div class="panel panel-info">
			<div class="panel-heading">
				<span class="pull-left">活跃度报表</span> <a href="#"
					class="pull-right btn btn-info btn-xs">订单记录查询</a>
				<div class="clearfix"></div>
			</div>
			<div class="panel-body">
				<table
					class="table table-hover table-bordered table-striped table-condensed">
					<thead>
						<tr class="success text-center">
							<td>#</td>
							<td>时间段</td>
							<td>投注金额</td>
							<td>中奖金额</td>
							<td>充值金额</td>
							<td>提款金额</td>
							<td>返点总额</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>2014-06-01 至 2014-07-01</td>
							<td class="text-danger">123456.0000</td>
							<td class="text-danger">1236.0000</td>
							<td class="text-danger">1234.0000</td>
							<td class="text-danger">123.0000</td>
							<td class="text-danger">123.0000</td>
							<td><a href="#" class="btn btn-link btn-sm">订单查询</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
-->
		<div class="panel panel-info">
			<div class="panel-heading">
				<span class="pull-left">已分配的银行卡</span> <a href="javascript:void(0);"
					class="btn btn-info btn-xs pull-right" id="join_bank_btn">添加银行卡</a>
				<div class="clearfix"></div>
			</div>
			<div class="panel-body">
				<table
					class="table table-condensed table-bordered table-striped table-hover">
					<thead>
						<tr class="success text-center">
							<td>#</td>
							<td>银行名称</td>
							<td>姓名</td>
							<td>卡号</td>
							<td>使用次数</td>
							<td>累计充值金额</td>
							<td>卡等级</td>
							<td>卡库名称</td>
							<td>属性</td>
							<td>卡状态</td>
							<td class="tip" data-toggle='tooltip' data-original-title=''>操作</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${bankCards }" var="cards">
							<tr>
								<script>bindCardIds.push('${cards.bindId }');</script>
								<td>${cards.cardId }</td>
								<td>${cards.bankName }</td>
								<td>${cards.customerName }</td>
								<td>${cards.cardNo }</td>
								<td>${cards.cardCount }</td>
								<td>${cards.cardMoney }</td>
								<td>${cards.cardLevel }</td>
								<td>${cards.cardInventoryName }</td>
								<td class="text-danger"><c:if
										test="${cards.extendsStatus == 15002 }">继承绑定</c:if> <c:if
										test="${cards.extendsStatus == 15001 }">单点绑定</c:if></td>
								<td class="text-primary"><c:if
										test="${cards.bankcardStatus == 10001 }">停用</c:if> <c:if
										test="${cards.bankcardStatus == 10002 }">正常</c:if></td>
								<td class="text-center"><label class="checkbox-inline">
										<input type="radio"
										<c:if test="${cards.status == 10002 }"> checked="true"</c:if>
										value="${cards.bindId },10002" name="cardstatus${cards.bindId }" /> 启用
								</label> <label class="radio-inline"> <input type="radio"
										<c:if test="${cards.status == 10001 }"> checked="true"</c:if>
										value="${cards.bindId },10001" name="cardstatus${cards.bindId }" /> 停用
								</label></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div class="panel panel-info">
			<div class="panel-heading">用户绑定的银行卡</div>
			<div class="panel-body">
				<table
					class="table table-condensed table-bordered table-striped table-hover">
					<thead>
						<tr class="success text-center">
							<td>#</td>
							<td>绑定时间</td>
							<td>银行名称</td>
							<td>开户姓名</td>
							<td>银行卡号</td>
							<td>开户地址</td>
							<td>支行名称</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${userCard }" var="obj">
							<tr>
								<script>cardIds.push('${obj[1].id}');</script>
								<td>${obj[1].id}</td>
								<td><fmt:formatDate value="${obj[1].createTime }"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>${obj[0]}</td>
								<td>${obj[1].openCardName}</td>
								<td>${obj[1].cardNo}</td>
								<td>${obj[1].address}</td>
								<td>${obj[1].branchBankName}</td>
								<td><label class="radio-inline"> <input
										<c:if test="${obj[1].status == 10002 }"> checked="true"</c:if>
										type="radio" value="${obj[1].id},10002" name="userCard${obj[1].id}"
										id="userCard${obj[1].id}">正常使用
								</label> <label class="radio-inline"> <input type="radio"
										value="${obj[1].id},10001" name="userCard${obj[1].id}"
										<c:if test="${obj[1].status == 10001 }"> checked="true"</c:if>
										id="userCard${obj[1].id}">暂停使用
								</label></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div class="panel panel-info">
			<div class="panel-heading">个人信息</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">昵称</label>
								<div class="col-sm-10">
									<p class="form-control-static">${user[8]}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">邮箱</label>
								<div class="col-sm-10">
									<p class="form-control-static">${user[6]}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">qq</label>
								<div class="col-sm-10">
									<p class="form-control-static">${user[7]}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">注册时间</label>
								<div class="col-sm-10">
									<p class="form-control-static">
										<fmt:formatDate value="${user[4]}"
											pattern="yyyy-MM-dd HH:mm:ss" />
									</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">账户状态</label>
								<div class="col-sm-10">
									<label class="radio-inline"> <input
										name="customerStatus1" type="radio" id="customerStatus1"
										value="10002"
										<c:if test="${user[5] == 10002 }"> checked="true"</c:if>
										checked="checked"> 正常
									</label> <label class="radio-inline"> <input
										name="customerStatus1"
										<c:if test="${user[5] == 10001 }"> checked="true"</c:if>
										type="radio" id="customerStatus2" value="10001"> 冻结
									</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">账户余额</label>
								<div class="col-sm-10">
									<p class="form-control-static text-danger">${cash.cash }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">冻结金额</label>
								<div class="col-sm-10">
									<p class="form-control-static text-danger">${cash.frozenCash}</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label"><c:if
									test="${user[2] == 0 }">总代</c:if> <c:if
									test="${user[2] > 0 }">${user[2]}级代理</c:if></label>
								<div class="col-sm-2">
									<p class="form-control-static ">
										<input class="form-control " type="text" id="rebatesStr" name="rebatesStr" value="<fmt:formatNumber value="${user[3] }" pattern="0.0%" />"></input>
									</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">&nbsp;</label>
								<div class="col-sm-10" style="padding-left: 0px;">
									<div class="form-inline ">
										<c:forEach items="${quotas }" var="quota">
											<div class=" col-sm-3 ">
												<label class="control-label "><fmt:formatNumber
														value="${quota.proportion }" pattern="0.0%" /></label> <input
													type="number" name="quota" id="quota,${quota.id}"
													value="${quota.quota_count }" class="form-control">
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
		<form action="${contextPath }/user/updateUserInfo.do" method="post" id="updateUserInfoForm">
			<input type="hidden" id="userId" name="userId" value="${user[0]}" />
			<input type="hidden" id="userbindcards" name="userbindcards" value="">
			<input type="hidden" id="usercards" name="usercards" value="">
			<input type="hidden" id="customerStatus" name="customerStatus" value="">
			<input type="hidden" id="quotas" name="quotas" value="">
			<input type="hidden" id="rebates" name="rebates" value="">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-horizontal">
								<div class="form-group">
									<label class="col-sm-2 control-label">管理密码</label>
									<div class="col-sm-4">
										<input type="password" class="form-control " id="adminPwd"
											name="adminPwd" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">验证码</label>
									<div class="col-sm-2">
										<input type="text" class="form-control " id="code"
											name="code"/> <input
											type="hidden" name="pickey" value="updateUserkey"  />
									</div>
									<div class="col-sm-2">
										<img id="pcode"
											width="100" height="35" />
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<button type="button" class="btn btn-danger" onclick="checkSubmit()">提交保存</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>

	<!-- -->
	<div class="modal fade" id="join_bank">
		<form action="../addBindCard.do" method="post" id="bindCardForm">
			<input type="hidden" id="id" name="id" value="${user[0]}" />
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">添加银行卡绑定</h4>
					</div>
					<div class="modal-body">
						<div class="form-inline">
							<div class="form-group">
								<select class="form-control" id="banks" name="banks"
									onchange="getBankCard()">
								</select>
							</div>
							<div class="form-group">
								<select class="form-control" id="bankCards" name="bankCardId">
								</select>
							</div>
						</div>
						<br />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							关闭</button>
						<button type="button" class="btn btn-primary"
							onclick="addBindCard();">确认添加</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
		</form>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

</body>
</html>
