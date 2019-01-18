<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- footer -->
<div style="clear: both"></div>
<div class="footer fl">
		<p>
			<a href="${contextPath }/index.html">网站首页</a>
			<span class="color_8d plr10">|</span>
			<a href="${contextPath }/user/showAccountSafe.html">修改密码</a><span
				class="color_8d plr10">|</span>
			<a href="${contextPath }/user/showBindCard.html">银行卡绑定</a><span
				class="color_8d plr10">|</span>
			<a href="${contextPath }/user/showUserRecharge.html">账户充值</a><span
				class="color_8d plr10">|</span>
			<a href="${contextPath }/showfeedback.html">问题反馈</a><span
				class="color_8d plr10">|</span>
			<a href="${contextPath }/showhcenter.html">帮助中心</a>
				<span class="color_8d plr10">|</span>
			<a href="${contextPath }/showNotices.html">网站公告</a>
		</p>
		<p class="mt20">夺金提醒您：！理性购彩，量力而行 不向未满18周岁的青少年出售彩票！</p>
		<p class="mt10">Copyright (C) 2014-2018 dj365.cc All rights Reserved.&nbsp;&nbsp;&nbsp;&nbsp;客服邮箱：djyl.member@gmail.com
&nbsp;&nbsp;&nbsp;&nbsp;<span class="color_8d">Version 1.0</span></p>
		<p class="mt25">
			<img src="${contextPath }/jsp/img/img_1.jpg" height="37" /><img
				src="${contextPath }/jsp/img/img_2.jpg" height="37" /><img
				src="${contextPath }/jsp/img/img_3.jpg" height="37" /><img
				src="${contextPath }/jsp/img/img_4.jpg" height="37" /><img
				src="${contextPath }/jsp/img/img_5.jpg" height="37" /><img
				src="${contextPath }/jsp/img/img_6.jpg" height="37" />
		</p>
</div>
<div style="clear:both;"></div>
<script>
	var _gaq = _gaq || [];
	_gaq.push(['_setAccount', 'UA-58453594-1']);
	_gaq.push(['_setDomainName', 'none']);
	_gaq.push(['_setAllowLinker', true]);
	_gaq.push(['_trackPageview']);
	(function () {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	})();
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-58453594-1', 'auto');
  ga('send', 'pageview');

</script>
<script>
	$(document).ready(function() {
		if ('${errorMsg }' != "" && '${noShowError}'!='true') {
			$("body").sMsg({
				content : '${errorMsg }',
				autoDisplay : true,
				actionEvent : ""
			});
		}
		if ('${success}' != "" ) {
			$("body").sMsg({
				content : '${success }',
				autoDisplay : true,
				actionEvent : ""
			});
		}
	});
</script>
