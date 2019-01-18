<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>彩种配置</title>
<%@ include file='../../include/asset_css.jsp'%>
<%@ include file='../../include/asset_js.jsp'%>
<script>
$(document).ready(function() {
	
	$.checkFrom.initBind(".checkedAttr", true, true);
	
	$(".number_url_close_btn").hide();
	$("#new_btn").click(function() {
		var d = $("#number_url_panle").clone(true);
		d.find("input").val("");
		$("#number_panle").append(d);
		var panles = $(".number_url_panle_class");
		panles.find(".number_url_close_btn").show();
		panles.eq(0).find(".number_url_close_btn").hide();

	});
	$(".number_url_close_btn").on("click", function() {
		$(this).closest("#number_url_panle").remove();
		var panles = $(".number_url_panle_class");
		if (panles.length == 1) {
			panles.eq(0).find(".number_url_close_btn").hide();
		}
	});

	$("#next_btn").click(function() {
		var names = $(".link_names");
		var temp_names = "";
		for (var i = 0; i < names.length; i++) {
			temp_names += names.eq(i).val();
			temp_names += "|";
			names.eq(i).attr("name", "links[" + i+"].sourceName");
		}
		var urls = $(".link_urls");
		var temp_urls = "";
		for (var i = 0; i < urls.length; i++) {
			temp_urls += urls.eq(i).val();
			temp_urls += "|";
			urls.eq(i).attr("name", "links[" + i+"].sourceLink");
		}
		
		var levels = $(".link_levels");
		var temp_levels = "";
		for (var i = 0; i < urls.length; i++) {
			temp_levels += levels.eq(i).val();
			temp_levels += "|";
			levels.eq(i).attr("name", "links[" + i+"].sourceLevel");
		}
		lotteryForm.submit();
	});
	
	$("#chooseLotGroup").next().val($("#chooseLotGroup").val());
	$("#chooseLotGroup").change(function(){
		$(this).next().val($(this).val());
	});
	
});
</script>

</head>
<body>
	<%@ include file="../../include/top.jsp"%>
	<div class="container">
		<ul class="follow-list">
			<li class="active">彩种配置</li>
			<li>奖期配置</li>
			<li>玩法配置</li>
			<li>奖金组配置</li>
			<li>限额配置</li>
			<div class="clearfix"></div>
		</ul>
		<form action="${contextPath }/lottery/createLotteryInfo.do" method="post" name="lotteryForm">
		<div class="panel panel-default">
			<div class="panel-heading">彩种信息</div>
			<div class="panel-body">
				<div class="form-horizontal" role="form">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">彩种组</label>
						<div class="col-sm-3">
							<select id="chooseLotGroup" class="form-control">
								<c:forEach items="${groupNameList }" var="group">
									<option value="${group.lotteryGroup }">${group.lotteryGroupName }</option>
								</c:forEach>
							</select>
							<input type="hidden"  name="lotteryGroup" value="${lotteryVo.lotteryGroup }">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">彩种名称</label>
						<div class="col-sm-3">
							<input type="text" class="form-control checkedAttr" name="lotteryName" 
							checkType="isNotEmpty" alt="彩种名称不能为空！"value="${lotteryVo.lotteryName }">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">彩种代码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control checkedAttr" name="lotteryCode" 
							checkType="isNotEmpty,intege1" alt="彩种代码不能为空！,彩种代码必须为正整数！" value="${lotteryVo.lotteryCode }">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">奖期规则</label>
						<div class="col-sm-3">
							<input type="text" class="form-control checkedAttr" name="seriesRule" value="${lotteryVo.seriesRule }"
								checkType="isNotEmpty" alt="奖期规则不能为空！" placeholder="yyMMdd[nnn]">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				开奖号源
			</div>
			<div class="panel-body">
				<c:forEach items="${lotteryVo.links }" var="link" varStatus="status">
					<div class="panel panel-default number_url_panle_class" id="number_url_panle">
					<div class="panel-heading">
						<div class="pull-right">
							<a href="javascript:;" class="number_url_close_btn">关闭</a>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="panel-body">
						<div class="form-horizontal" >
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">号源别名</label>
								<div class="col-sm-3">
									<input type="text" class="form-control link_names checkedAttr" name="links[0].sourceName" 
									checkType="isNotEmpty" alt="号源别名不能为空！" value="${link.sourceName }" >
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">号源网址</label>
								<div class="col-sm-8">
									<input type="text" class="form-control link_urls checkedAttr" name="links[0].sourceLink" 
									checkType="isNotEmpty" alt="号源网址不能为空！" value="${link.sourceLink }">
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">权重数值</label>
								<div class="col-sm-3">
									<input type="text" class="form-control link_levels checkedAttr" name="sourceLevel" 
									checkType="isNotEmpty,intege1" alt="权重数值不能为空！,权重数值必须为正整数！" value="100">
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
			</div>		
			<div class="panel-body">
				<div id="number_panle"></div>
				<a href="javascript:;" id="new_btn" class="btn btn-block btn-info btn-sm">添加新号源</a>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<input type="button" class="btn btn-primary" id="next_btn" value="下一步" />
						<a href="${contextPath }/lottery/showLotteryEngine.do" id="new_btn" class="btn btn-sm">返回</a>
						
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
</body>
</html>