<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帮助详情</title>
<%@ include file="../include/css_asset.jsp"%>
<%@ include file="../include/js_asset.jsp"%>
<script src="${contextPath }/jsp/js/helper/helpDetail.js"></script>
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
				<input type="hidden" id="hid" value="${vo.id }" >
			</div>
			<div class="w740 fr">
				<div class="relative helpBorder _showam">
					<h3 class="helpDTitle">问题描述假字请勿阅读</h3>
					<div class="helpDContent">
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
							Aenean euismod bibendum laoreet. Proin gravida dolor sit amet
							lacus accumsan et viverra justo commodo. Proin sodales pulvinar
							tempor. Cum sociis natoque penatibus et magnis dis parturient
							montes, nascetur ridiculus mus. Nam fermentum, nulla luctus
							pharetra vulputate, felis tellus mollis orci, sed rhoncus sapien
							nunc eget odio.</p>
						<p>&nbsp;</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
							Aenean euismod bibendum laoreet. Proin gravida dolor sit amet
							lacus accumsan et viverra justo commodo. Proin sodales pulvinar
							tempor. Cum sociis natoque penatibus et magnis dis parturient
							montes, nascetur ridiculus mus. Nam fermentum, nulla luctus
							pharetra vulputate, felis tellus mollis orci, sed rhoncus sapien
							nunc eget odio.</p>
						<p>&nbsp;</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
							Aenean euismod bibendum laoreet. Proin gravida dolor sit amet
							lacus accumsan et viverra justo commodo. Proin sodales pulvinar
							tempor. Cum sociis natoque penatibus et magnis dis parturient
							montes, nascetur ridiculus mus. Nam fermentum, nulla luctus
							pharetra vulputate, felis tellus mollis orci, sed rhoncus sapien
							nunc eget odio.</p>
					</div>
					<p class="helpDBtn">
						<a href="javascript:;" class="i3GreyBtn _isApplaud"><span>有用&nbsp;<i>(0)</i></span></a>&nbsp;&nbsp;<a
							href="javascript:;" class="i3GreyBtn _isOppose"><span>没用&nbsp;<i>(0)</i></span></a>
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