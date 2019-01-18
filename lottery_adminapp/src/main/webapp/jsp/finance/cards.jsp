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
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				已绑定银行卡：<label class="label label-danger">${countCard}张</label>&nbsp;&nbsp;
				已添加银行：<label class="label label-danger">${countBank}家</label>
			</div>
			<div class="panel-body">
				<a href="initBindCard.do" class="btn btn-warning">绑定新银行卡</a> <a
					href="showBanks.do" class="btn btn-warning">银行管理</a>
				<a href="showCardInventory.do" class="btn btn-warning">卡库配置</a>
			</div>
		</div>

		<table
			class="table table-hover table-bordered table-striped table-condensed clear_margin_bottom">
			<thead>
				<tr class="success text-center">
					<td>#</td>
					<td>绑定时间</td>
					<td>银行名称</td>
					<td>卡号</td>
					<td>绑定人数</td>
					<td>充值金额</td>
					<td>充值次数</td>
					<td>状态</td>
					<td>备注</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<!-- 
				<tr>
					<td>1</td>
					<td>2014-6-18 22:46:49</td>
					<td>招商银行</td>
					<td>123456789 123456</td>
					<td><label class="label label-success">20人</label></td>
					<td class="text-danger">123456.0000</td>
					<td><label class="label label-danger">20次</label></td>
					<td class="text-danger">12345679.0000</td>
					<td><label class="label label-danger">35次</label></td>
					<td>正常</td>
					<td>--</td>
					<td><a href="card.html" class="btn btn-link btn-sm">查看详情</a></td>
				</tr>
				 -->
				<c:forEach items="${pagelist.pagelist }" var="card">
					<tr>
						<td>${card.id }</td>
						<td>${card.updateTime }</td>
						<td>${card.bankName }</td>
						<td>${card.cardNo }</td>
						<td><label class="label label-success">${card.bindCount }人</label></td>
						<td class="text-danger"><fmt:formatNumber pattern="#,##0.00" value="${card.cardMoney }"/></td>
						<td><label class="label label-danger">${card.cardCount }次</label></td>
						<c:if test="${card.bankcardStatus == 10002 }">
							<td class="text-success">正常</td>
						</c:if>
						<c:if test="${card.bankcardStatus == 10001 }">
							<td class="text-success">停用</td>
						</c:if>
						<c:if
							test="${card.bankcardStatus != 10001 && card.bankcardStatus != 10002}">
							<td class="text-success">删除</td>
						</c:if>
						<td>${card.remark }</td>
						<td><a
							href="showCardInfo.do?id=${card.id }&status=${card.bankcardStatus }"
							class="btn btn-link btn-sm">查看详情</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pager">
			<li><a
				href="showBankManage.do?pageNum=${(pagelist.pageNum-1)<=0?1:(pagelist.pageNum-1) }">上一页</a></li>
			<li>第${pagelist.pageNum }页/共${pagelist.pageCount }页</li>
			<li><a
				href="showBankManage.do?pageNum=${(pagelist.pageNum)>=pagelist.pageCount?pagelist.pageCount:(pagelist.pageNum+1) }">下一页</a></li>
		</ul>
	</div>

</body>
</html>
