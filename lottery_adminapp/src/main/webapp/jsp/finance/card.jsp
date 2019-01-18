<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>index</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/finance/card.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#error_msg").hide();
	});

	function disableBankCard() {
		location.href = "disableBankCard.do?cardId=" + '${cardId}';
	}

	function enableBankCard() {
		location.href = "enableBankCard.do?cardId=" + '${cardId}';
	}

	function deleteBankCard() {
		location.href = "deleteBankCard.do?cardId=" + '${cardId}';
	}
</script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/showBankManage.do">银行卡管理</a></li>
			<li class="active">银行卡详情</li>
		</ol>
		<div class="alert alert-danger alert-dismissable" id='error_msg'>
			<button type="button" class="close alert_close_btn">&times;
			</button>
			<div id="error_msg_val"></div>
		</div>
		<div class="panel panel-default">
			<!--  
			<div class="panel-heading">
				<label class="label label-danger">已分配：200人</label>
			</div>
			-->
			<div class="panel-body">
				<div class="pull-left">
					<c:if test="${cardStatus == 10001 }">
						<a href="#" onclick="enableBankCard()" id="enable_card_modal_btn"
							class="btn btn-default ">启用此卡</a>
					</c:if>
					<c:if test="${cardStatus != 10001 }">
						<a href="#" id="lock_card_modal_btn" class="btn btn-default ">停用此卡</a>
					</c:if>


					<a href="initUpdateCard.do?cardId=${cardId}"
						class="btn btn-default ">修改信息</a> <input type="hidden" id="cardId"
						name="cardId" value="${cardId}" />

				</div>
				<div class="pull-right">
					<a href="#" id="del_card_modal_btn" class="btn btn-default ">删除此卡</a>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
		<!-- 
		<div class="panel panel-success">
			<div class="panel-heading">充值记录</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-3 control-label">今日充值次数</label>
								<div class="col-sm-9">
									<p class="form-control-static">1次</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">今日充值金额</label>
								<div class="col-sm-9">
									<p class="form-control-static">123456.0000</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">累计充值次数</label>
								<div class="col-sm-9">
									<p class="form-control-static">1次</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">累计充值金额</label>
								<div class="col-sm-9">
									<p class="form-control-static">123456.0000</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		-->
		<div class="panel panel-success">
			<div class="panel-heading">卡信息</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-3 control-label">银行名称</label>
								<div class="col-sm-9">
									<p class="form-control-static">${bankCard.bankName }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">支行名称</label>
								<div class="col-sm-9">
									<p class="form-control-static">${bankCard.branceBankName }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">账户名</label>
								<div class="col-sm-9">
									<p class="form-control-static">${bankCard.opencardName }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">银行卡号</label>
								<div class="col-sm-9">
									<p class="form-control-static">${bankCard.cardNo }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">附言</label>
								<div class="col-sm-9">
									<p class="form-control-static">
										<c:if test="${bankManage.psStatus==1 }">
											开启${bankManage.psNum}位
											<c:if test="${bankManage.ps==1 }">纯数字</c:if>
											<c:if test="${bankManage.ps==2 }">纯字母</c:if>
											<c:if test="${bankManage.ps==3 }">混合</c:if>
										</c:if>
										<c:if test="${bankManage.psStatus==0 }">关闭</c:if>
									</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">绑定时间</label>
								<div class="col-sm-9">
									<p class="form-control-static">
										<fmt:formatDate value="${bankCard.createTime}"
											pattern="yyyy-MM-dd hh:mm:ss" />
									</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">地址</label>
								<div class="col-sm-9">
									<p class="form-control-static">${bankManage.url}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">备注</label>
								<div class="col-sm-9">
									<p class="form-control-static">${bankCard.remark}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="panel panel-success">
			<div class="panel-heading">此卡使用者情况</div>
			<div class="panel-body">
				<table
					class="table table-hover table-bordered table-striped table-condensed clear_margin_bottom">
					<thead>
						<tr class="success text-center">
							<td>#</td>
							<td>用户名</td>
							<td>用户类型</td>
							<td>绑定属性</td>
							<td>使用此卡次数</td>
							<td>使用此卡充值金额</td>
							<td>卡状态</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody id="tbody">

					</tbody>
					<tr style="display: none;" id="ctr">
						<td>1</td>
						<td>王五</td>
						<td>一级代理</td>
						<td class="text-danger">继承绑定</td>
						<td><label class="label label-danger">20次</label></td>
						<td class="text-danger">6558.0000</td>
						<td class="text-success">正常</td>
						<td><a href="#" class="btn btn-link btn-sm">查看用户详情</a></td>
					</tr>
				</table>
				<ul class="pager _page">
					<li><a href="#">上一页</a></li>
					<li><a href="#">下一页</a></li>
				</ul>
			</div>
		</div>

	</div>

	<!-- /.modal -->

	<div class="modal fade " id="del_card_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">删除该卡</h4>
				</div>
				<div class="modal-body">
					<p class="text-danger">
						删除该卡后，此卡分配的用户将自动解除绑定，有可能导致用户无充值分配银行卡，建议在删除前先转移银行卡内用户至其他卡。</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						关闭</button>
					<button type="button" class="btn btn-primary"
						onclick="deleteBankCard()">确定删除</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<div class="modal fade " id="lock_card_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">停用该卡</h4>
				</div>
				<div class="modal-body">
					<p class="text-danger">停用此卡后将，已分配该卡的用户将无法使用，确定要停用？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						关闭</button>
					<button type="button" class="btn btn-primary"
						onclick="disableBankCard()">确定停用</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<script>
		$(document).ready(function() {
			$("#del_card_modal_btn").click(function() {
				$("#del_card_modal").modal("show");
			});
			$("#lock_card_modal_btn").click(function() {
				$("#lock_card_modal").modal("show");
			});
		});
	</script>
</body>
</html>
