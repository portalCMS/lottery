<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<p class="mt10 line1"></p>
<div class="k990 clearfix">
	<span class="f12 mt10 fl color_8d mb10">Copyright (C) 2014-2018 dj365.cc All
		rights Reserved.</span>
	<span class="color_8d fr mt10 mb10">
		 版权所有：夺金娱乐 Version 1.0
	</span>
</div>
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