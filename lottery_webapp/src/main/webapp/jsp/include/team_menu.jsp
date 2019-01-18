<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
</script>
<ul class="pageFlBox">
	<li><a id="userMenu" href="${contextPath }/team/show.html"><span class="ico9"></span>用户列表</a></li>
	<li><a id="createAccountMenu" href="${contextPath }/team/showcreate.html"><span class="ico8"></span>开设下级</a></li>
	<c:if test="${customerkey.rebates*100 > cminPoint }">
	<li><a id="lowerManage" href="${contextPath }/team/showlm.html"><span class="ico9"></span>下级管理</a></li>
	<li><a id="quotaManageMenu" href="${contextPath }/team/showqm.html"><span class="ico7"></span>管理下级配额</a></li>
	</c:if>
	<li><a id="ykReport" href="${contextPath }/team/queryYKReport.html"><span class="ico1"></span>团队盈亏报表</a></li>
	<li><a id="betRecordsMenu" href="../team/showbrs.html"><span
			class="ico3"></span>团队投注查询</a></li>
	<li><a id="ordersMenu" href="${contextPath }/order/showQueryOrder.html"><span class="ico3"></span>团队订单查询</a></li>
	<li><a id="revenueMenu" href="${contextPath }/order/showRevenue.html"><span class="ico1"></span>下级点差盈收</a></li>
</ul>
<p class="pageFlBoxBot"></p> 