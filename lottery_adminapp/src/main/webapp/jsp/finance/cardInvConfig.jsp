<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script type="text/javascript" src="${contextPath }/jsp/js/finance/card_inv_config.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<ol class="breadcrumb">
			<li><a href="${contextPath }/index.do">首页</a></li>
			<li><a href="${contextPath }/showBankManage.do">银行卡管理</a></li>
			<li class="active">银行卡配置</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-heading">卡库配置</div>
			<div class="panel-body">
				<p>
					<a href="javascript:;" class="btn btn-default _addPanleBtn">添加新库</a>
				</p>
				
				<div id="_labPanle">
					<p class="split"></p>
					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-1 control-label">名称</label>
							<div class="col-sm-4">
								<input type="text" class="form-control checkedAttr " id="newInvName"
								checkType="isNotEmpty" alt="名称不能为空！"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">备注</label>
							<div class="col-sm-4">
								<textarea class="form-control" id="newInvRemark"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-4 col-sm-offset-1">
								<a href="javascript:;" class="btn btn-danger _addCardInv">确定添加</a> 
								<a href="javascript:;" class="btn btn-default _cancelAddBtn">取消</a>
							</div>
						</div>
					</div>
				</div>
				
				<div id="_updatePanle">
					<p class="split"></p>
					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-1 control-label">名称</label>
							<div class="col-sm-4">
								<input type="text" class="form-control checkedAttr " id="updateInvName"
								checkType="isNotEmpty" alt="名称不能为空！"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">备注</label>
							<div class="col-sm-4">
								<textarea class="form-control" id="updateInvRemark"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-4 col-sm-offset-1">
								<a href="javascript:;" class="btn btn-danger _updateCardInv">确定修改<input class="_updateId" type="hidden"/></a> 
								<a href="javascript:;" class="btn btn-default _cancelPanleBtn">取消</a>
							</div>
						</div>
					</div>
				</div>
				<c:forEach items="${inventorys }" var="inv">
					<table class="table  table-bordered  clear_margin_bottom">
					<thead>
						<tr>
							<td colspan="5" class="clearfix">
								<span class="label label-danger pull-left _invName">${inv.inventoryName }</span>
								&nbsp;&nbsp;&nbsp;&nbsp; 
								<a href="javascript:;" class="text-muted  _updateBtn">修改</a> 
								<span class="pull-right text-muted _invTime">${inv.createTime }</span>
							</td>
						</tr>
						<tr>
							<td colspan="5 clearfix">
								<span class="pull-left _remark">${inv.remark }</span>
							 	<a href="javascript:;" class="pull-right _queryInv">查看详情</a>
							 	<input type="hidden" class="_invId" value="${inv.id }"/>
							</td>
						</tr>
						<tr class="success text-center">
							<td width="20%">1级卡</td>
							<td width="20%">2级卡</td>
							<td width="20%">3级卡</td>
							<td width="20%">4级卡</td>
							<td width="20%">5级卡</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td width="20%">
								<ul class="list-unstyled _level1">
								</ul>
							</td>
							<td width="20%">
								<ul class="list-unstyled _level2">
								</ul>
							</td>
							<td width="20%">
								<ul class="list-unstyled _level3">
								</ul>
							</td>
							<td width="20%">
								<ul class="list-unstyled _level4">
								
								</ul>
							</td>
							<td width="20%">
								<ul class="list-unstyled _level5">
								</ul>
							</td>
						</tr>
					</tbody>
				</table>	
				<br>			
				</c:forEach>
				
				<!-- 隐藏的单个银行card的clone对象 -->
				<li class="_hidLi" style="display:none;">
					<p>
						<span class="label label-default _cardName"></span>&nbsp;
						<span class="label label-success _bankName"></span>&nbsp;
						<span class="label label-success _status"></span> 
					</p>
					<p>
						<span class="label label-default _cardNo"></span>
					</p>
					<p class="_agentList">
					</p>
					<p style="margin-left: 150px;">
						<a href="javascript:;" class="_queryCardInfo">查看</a>
					</p>
					<p class="split"></p>
				</li>
				<a href="javascript:;" class="_agent" style="display:none;"></a>
				<span class="label _agentInfo" style="display:none;color:green; ">总代：</span>
			</div>
		</div>
	</div>
</body>
</html>