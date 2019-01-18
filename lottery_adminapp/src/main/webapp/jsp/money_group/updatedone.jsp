<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>验证</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/monery_group/updatedone.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
	<ul class="follow-list">
			<li class="active">设置奖金组与返点</li>
			<li class="active">设置无返点奖金组</li>
			<li class="active">验证</li>
			<div class="clearfix"></div>
		</ul>
			<div class="row">
				<div class="col-sm-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							奖金组
						</div>
						<div class="panel-body">
							<div class="form-horizontal">
								<div class="form-group">
									<label class="control-label col-sm-3">别名：</label>
									<div class="col-sm-6">
										<div class="form-control-static">
											${bonusvokey.name }
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-3">理论奖金：</label>
									<div class="col-sm-6">
										<div class="form-control-static">
											${bonusvokey.theoryBonus }
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-3">返奖率：</label>
									<div class="col-sm-6">
										<div class="form-control-static">
											${bonusvokey.rebates }
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-3">返点率：</label>
									<div class="col-sm-6">
										<div class="form-control-static">
											${bonusvokey.payoutRatio }
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-3">利润率：</label>
									<div class="col-sm-6">
										<div class="form-control-static">
											${bonusvokey.margin }
										</div>
									</div>
								</div>
								<div class="split"></div>
								<div class="form-group">
									<label class="control-label col-sm-3">备注：</label>
									<div class="col-sm-9">
										<div class="form-control-static">
											${bonusvokey.remark }
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							无返点奖金组
						</div>
						<div class="panel-body" style="height: 250px; overflow-y: scroll">
							<table class="table table-bordered table-condensed table-container table-hover table-striped">
								<thead>
									<tr class="text-center success">
										<td>返点</td>
										<td>对应奖金</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="vo" items="${norebatesvokey.vos }">
										<tr>
											<td> <fmt:formatNumber value="${vo.rebates }" pattern="0.0%" />  </td>
											<td> <fmt:formatNumber value="${vo.bonus }" pattern="0,000.00" /> </td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-body">
					<p class="text-danger">
						Tip:新增奖金组的默认值将追加现有彩种，如需调节，请在彩种管理中修改奖金组细节
					</p>
					<div class="col-sm-offset-1">
						<a href="javascript:void(0)" class="btn btn-primary _addbtn">确认添加</a>
						<a href="nextUpdateNoPoint.do" class="btn btn-default">返回</a>
					</div>
				</div>
			</div>
		</div>
</body>
</html>