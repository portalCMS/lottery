<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帮助中心</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/helper/helpCenter.js"></script>
</head>
<body>
	<%@ include file="../include/head.jsp"%>

	<!-- content -->
	<div class="k990">
		<div class="clearfix mt15">
			<div class="helpL fl">
				<p class="i3Line"></p>
				<p class="i3BoxTitle">
					<span>帮助列表</span>
				</p>
				<dl class="helpLDl _dl">
					
				</dl>
				<dd class="helpLDd _dd" style="display: none;">
						<a href="javascript:;" class="_setTitle">充值提款</a>
						<ul class="helpLUl _ul">
							<li><a href="#">问题子分类描述</a></li>
						</ul>
				</dd>
				<p class="boxShadow"></p>
			</div>
			<div class="w740 fr">
				<div class="relative helpBorder">
					<img src="${contextPath }/jsp/images/helpBan.jpg" width="738" />
					<p class="boxShadow"></p>
				</div>
				<div class="mt10 relative helpBorder">
					<ul class="clearfix helpBtn">
						<li><a href="javascript:;" class="_zh"><img
								src="${contextPath }/jsp/images/helpImg1.jpg" width="92"
								height="102" />
							<p>账户问答</p></a></li>
						<li><a href="javascript:;" class="_cz"><img
								src="${contextPath }/jsp/images/helpImg2.jpg" width="92"
								height="102" />
							<p>彩种介绍</p></a></li>
						<li><a href="javascript:;" class="_cw"><img
								src="${contextPath }/jsp/images/helpImg3.jpg" width="92"
								height="102" />
							<p>财务问答</p></a></li>
						<li><a href="${contextPath }/showfeedback.html"><img
								src="${contextPath }/jsp/images/helpImg4.jpg" width="92"
								height="102" />
							<p>意见反馈</p></a></li>
					</ul>
					<p class="helpService">
						<span>无法获取帮助？试试&nbsp;<a target="_blank" href="${contextPath }/opencs.html" class="color_blue">在线客服</a></span>
					</p>
					<p class="boxShadow"></p>
				</div>
			</div>
		</div>
	</div>
	<!-- content End -->
	<%@ include file="../include/footer.jsp"%>
	<script type="text/javascript">
		$(document).ready(function(){
			//left折叠
			$('.helpLDd > a').click(function(){
				$(this).parent('dd').toggleClass('helpLDdOver').siblings('dd').removeClass('helpLDdOver');
			});
		});
	</script>
</body>
</html>