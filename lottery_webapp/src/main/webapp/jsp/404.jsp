<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>404</title>
<%@ include file="include/css_asset.jsp"%>
<%@ include file="include/js_asset.jsp"%>

</head>
<body>
	<%@ include file="include/simple-head.jsp"%>
	<!-- content -->
	<div class="k990 mt25 clearfix">
		<div class="k310 fl relative">
			<div class="l404Bg">
				<p class="l404BgP">
					<span>404</span>
				</p>
				<p class="l404Line"></p>
				<p class="tc color_fff f14 mt10">很遗憾，页面没有找到。您可以去：</p>
			</div>
			<div class="menu404">
				<a href="${contextPath }/index.html">首页</a><span class="plr15">|</span>
				<a href="${contextPath }/user/showUserRecharge.html">充值</a><span
					class="plr15">|</span>
				<a href="${contextPath }/user/showDrawing.html">提款</a><span class="plr15">|</span>
				<a href="${contextPath }/user/showUserData.html">个人中心</a>
			</div>
			<p class="boxShadow"></p>
		</div>
		<div class="k668 fr relative">
			<div class="clearfix">
				<div class="luckyN404 fl">
					<p class="luckyTitle404">页面没找到，但是您找到了一组幸运数字</p>
					<p class="luckyNStrong mt20 color_yellow">
						<script>
							document.write($.cookie(getNowDate(0,0)+":"+"luckayNumber"));
						</script>
						<p class="line1 mt20"></p>
					</p>
					<p class="luckyBtn _bet">
						前往投注：<a href="javaScript:;" id="70001" name="SSC">重庆时时彩</a>
							<a href="javaScript:;" id="80001" name="SYXW">广东11选5</a>
							<a href="javaScript:;" id="70002" name="SSC">江西时时彩</a>
							<a href="javaScript:;" id="80006" name="SYXW">上海11选5</a>
					</p>
				</div>
				<div class="luckyR404 fr">
					<p class="luckyTitle404">有何建议</p>
					<p class="mt15 ml20">
						<a href="${contextPath }/showfeedback.html" class="btn3 big f18"><span>请提交反馈</span></a>
					</p>
					<p class="mt20 ml20">帮助我们完成的更好。</p>
				</div>
			</div>
			<p class="boxShadow"></p>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="include/simple-footer.jsp"%>
</body>
<script>
	$(document).ready(function(){
		$("._bet > a").click(function(){
			var code = $(this).attr("id");
			var group = $(this).attr("name");
			var attrs = new Array();
			attrs.push("lotteryCode");
			var values = new Array();
			values.push(code);
			
			var action = "";
			if(group=="SSC"){
				 action = "${contextPath }/showssc.html";
			}else if(group=="SYXW"){
				 action = "${contextPath }/show11x5.html";
			}
			 formPost.submitForm(action,attrs,values,true);
		});
	});
</script>
</html>