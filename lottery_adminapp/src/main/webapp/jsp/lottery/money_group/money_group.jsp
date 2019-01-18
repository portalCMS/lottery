<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>号源监控</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/lottery/bonusgroup.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">奖金组设置</div>
			<div class="panel-body">
				<a href="#" class="btn btn-primary dis_add_money_group_btn">添加奖金组</a>
				<div id="add_money_locak_panel">
					<div class="split"></div>
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-1">别名</label>
							<div class="col-sm-2">
								<input type="text" class="form-control uname"
									checkType="isNotEmpty" alt="别名不能为空" name="addParam" id="name" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1">理论返奖</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" id="atheory"
									value="2000" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1">返奖率</label>
							<div class="col-sm-2">
								<input type="text" class="form-control amoney" name="addParam"
									id="rebates" checkType="isNotEmpty,decmal6"
									alt="返奖率不能为空,返奖率必须是小数" />
							</div>
							<span class="help-block money">1000.00 </span>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1">返点率</label>
							<div class="col-sm-2">
								<input type="text" class="form-control amoney" name="addParam"
									id="payoutRatio" checkType="isNotEmpty,decmal6"
									alt="返点率不能为空,返点率必须是小数" />
							</div>
							<span class="help-block money1">1000.00 </span>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1">利润率</label>
							<div class="col-sm-2">
								<input type="text" class="form-control amoney" name="addParam"
									id="margin" checkType="isNotEmpty,decmal6"
									alt="利润率不能为空,利润率必须是小数" />
							</div>
							<span class="help-block money2">1000.00 </span>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1">备注</label>
							<div class="col-sm-4">
								<textarea class="form-control" name="addParam" id="remark"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1">生成操作</label>
							<div class="col-sm-10 form-control-static">
								<span class="text-danger">奖金组生成后，将会自动为现有彩种初始化此奖金组参数，参数细节为此次设置的参考参数</span>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-4 col-sm-offset-1">
								<a href="#" class="btn btn-info addbtn">提交保存</a> <a href="#"
									class="btn btn-default dis_add_money_group_btn">返回</a>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
		<table class="table table-bordered table-condensed table-hover">
			<thead>
				<tr class="text-center success">
					<td>#</td>
					<td>别名</td>
					<td>添加时间</td>
					<td>参考理论返奖</td>
					<td>返奖率</td>
					<td>返点率</td>
					<td>利润率</td>
					<td>备注</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bonusGroups}" var="bonus">
					<tr>
						<td>${bonus.id }</td>
						<td>${bonus.name }</td>
						<td><fmt:formatDate value="${bonus.createTime }"
								pattern="yyyy-MM-dd hh:mm:ss" /></td>
						<td><fmt:formatNumber value="2000" pattern="0,000.00" /></td>
						<td>${bonus.rebates }</td>
						<td>${bonus.payoutRatio }</td>
						<td>${bonus.margin }</td>
						<td>${bonus.remark }</td>
						<td><a href="javaScript:void(0)" id="${bonus.id }"
							class="btn btn-xs btn-link edit_btn">修改</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="modal fade" id="edit_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">修改奖金组</h4>
				</div>
				<div class="modal-body">
					<p class="text-danger">奖金组修改不会影响到采用该奖金组配置的所有玩法具体数据</p>
					<div class="split"></div>
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-2">别名</label>
							<div class="col-sm-4">
								<input type="text" class="form-control name" name="updateParam"
									id="name" /> <input type="hidden" class="form-control id"
									name="updateParam" id="id" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">理论返奖</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="utheory"
									value="2000" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">返奖率</label>
							<div class="col-sm-4">
								<input type="text" class="form-control rebates umoney"
									name="updateParam" id="rebates" />
							</div>
							<span class="help-block umoney">1000.00 </span>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">返点率</label>
							<div class="col-sm-4">
								<input type="text" class="form-control payoutRatio umoney"
									name="updateParam" id="payoutRatio" />
							</div>
							<span class="help-block umoney2">1000.00 </span>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">利润率</label>
							<div class="col-sm-4">
								<input type="text" class="form-control margin umoney"
									name="updateParam" id="margin" />
							</div>
							<span class="help-block umoney3">1000.00 </span>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2">备注</label>
							<div class="col-sm-8">
								<textarea class="form-control remark" name="updateParam"
									id="remark"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						返回</button>
					<button type="button" class="btn btn-primary btn-update">提交保存</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>