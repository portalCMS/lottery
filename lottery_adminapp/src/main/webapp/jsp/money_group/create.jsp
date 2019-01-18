<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增奖金组</title>
<%@ include file='../include/asset_css.jsp'%>
<%@ include file='../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/lottery/bonusgroup.js"></script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>

	<div class="container">
		<ul class="follow-list">
			<li class="active">设置奖金组与返点</li>
			<li>设置无返点奖金组</li>
			<li>验证</li>
			<div class="clearfix"></div>
		</ul>
		<div class="panel panel-default">
			<div class="panel-heading">奖金组基本信息</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
							<label class="control-label col-sm-1">别名</label>
							<div class="col-sm-2">
								<input type="text" class="form-control uname" checkType="isNotEmpty" alt="别名不能为空"
									name="addParam" id="name"  value="${bonusvokey.name }"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1">理论返奖</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" name="addParam" 
									id="theoryBonus" checkType="isNotEmpty,intege1" alt="理论返奖不能为空,理论返奖必须是正正数" value="${bonusvokey.theoryBonus }" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1">返奖率</label>
							<div class="col-sm-2">
								<input type="text" class="form-control amoney" name="addParam"
									id="payoutRatio" checkType="isNotEmpty,decmal6" alt="返奖率不能为空,返奖率必须是小数" value="${bonusvokey.payoutRatio }"/>
							</div>
							<span class="help-block money1">1000.00 </span>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1">返点率</label>
							<div class="col-sm-2">
								<input type="text" class="form-control amoney" name="addParam"
									id="rebates" checkType="isNotEmpty,decmal6" alt="返点率不能为空,返点率必须是小数" value="${bonusvokey.rebates }"/>
							</div>
							<span class="help-block money">1000.00 </span>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1">利润率</label>
							<div class="col-sm-2">
								<input type="text" class="form-control amoney" name="addParam"
									id="margin" checkType="isNotEmpty,decmal6" alt="利润率不能为空,利润率必须是小数" value="${bonusvokey.margin }"/>
							</div>
							<span class="help-block money2">1000.00 </span>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1">备注</label>
							<div class="col-sm-4">
								<textarea class="form-control" name="addParam" id="remark" >${bonusvokey.remark }</textarea>
							</div>
						</div>
				</div>
				<div class="split"></div>
				<a href="javascript:void(0)" class="btn btn-primary col-sm-offset-2 addbtn">下一步</a>
			</div>
		</div>
	</div>
</body>
</html>