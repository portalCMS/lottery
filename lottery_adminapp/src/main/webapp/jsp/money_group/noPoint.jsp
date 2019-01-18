<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无返点奖金组设置</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/monery_group/noPoint.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ul class="follow-list">
			<li class="active">设置奖金组与返点</li>
			<li class="active">设置无返点奖金组</li>
			<li>验证</li>
			<div class="clearfix"></div>
		</ul>
		<div class="row">
			<div class="col-sm-6">
				<div class="panel panel-default">
					<div class="panel-heading">参数</div>
					<div class="panel-body">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-sm-3">理论奖金：</label>
								<div class="col-sm-6">
									<div class="form-control-static _theoryBonus">${bonusvokey.theoryBonus}<input type="hidden" class="_rebates" value="${bonusvokey.theoryBonus*bonusvokey.payoutRatio}"></div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3">返点率：</label>
								<div class="col-sm-6">
									<div class="form-control-static _payoutRatio">${bonusvokey.rebates}</div>
								</div>
							</div>
							<div class="split"></div>

							<div class="form-group">
								<label class="control-label col-sm-3">返点间隔：</label>
								<div class="col-sm-6">
									<div class="form-control-static _interval">0.001</div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3">对应增加：</label>
								<div class="col-sm-2">
									<c:if test="${norebatesvokey!=null and norebatesvokey.vos!=null }">
										<input type="text" class="form-control _monery" value='<fmt:formatNumber value="${norebatesvokey.vos[1].bonus-norebatesvokey.vos[0].bonus }" pattern="0.00" />'/>
									</c:if>
									<c:if test="${norebatesvokey==null and norebatesvokey.vos==null }">
										<input type="text" class="form-control _monery" value='<fmt:formatNumber value="${bonusvokey.theoryBonus*0.001}" pattern="0.00" />'/>
									</c:if>
								</div>
								<span class="help-block col-sm-1">元</span>
								<div class="col-sm-4">
									<a href="javascript:void(0);" class="btn btn-primary _generate">生成无返点奖金</a>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="panel panel-default">
					<div class="panel-heading">无返点奖金组</div>
					<div class="panel-body" style="height: 222px; overflow-y: scroll">
						<table
							class="table table-bordered table-condensed table-container table-hover table-striped">
							<thead>
								<tr class="text-center success">
									<td width="50%">返点</td>
									<td>对应奖金</td>
								</tr>
							</thead>
							<tbody class="_norebates">
								<c:forEach items="${norebatesvokey.vos }" var="vo">
									<tr>
										<td class="text-center">${vo.rebates }<input class="_addparam" type="hidden" value="${vo.rebates }" id="rebates"></td>
										<td><input type="text" class="form-control _addparam1" style="width: 100px" value="${vo.bonus }" id="bonus"/></td>
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
				<div class="col-sm-offset-1">
					<a href="javascript:void(0);" class="btn btn-primary _nextDone"> 下一步 </a> <a
						href="showcreatebonus.do?isBack=1" class="btn btn-default">返回</a>
				</div>
			</div>
		</div>
	</div>
</body>
<c:if test="${norebatesvokey==null||norebatesvokey.vos==null }">
			<script type="text/javascript">
				$(document).ready(function(){
					noPoint.initData();
				});
			</script>
</c:if>
</html>