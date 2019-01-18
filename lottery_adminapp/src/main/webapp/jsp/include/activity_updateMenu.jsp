<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="col-sm-2">
	<div class="list-group _names">
		<a href="${contextPath }/activity/updateRegTempl.do?id=${vo.id}&type=NO" class="list-group-item " id="reg_menu"> 
			注册活动模板
		</a>
		<a href="${contextPath }/activity/updatefrecTempl.do?id=${vo.id}&type=NO" class="list-group-item" id="frist_recharge_menu"> 
			首充活动模板
		</a>
		<a href="${contextPath }/activity/updateBetTempl.do?id=${vo.id}&type=NO" class="list-group-item" id="bet_menu"> 
			游戏活动模板
		</a>
<%-- 		<a href="${contextPath }/activity/showHelpTempl.do" class="list-group-item" id="help_lost_menu">  --%>
<!-- 			救援活动模板 -->
<!-- 		</a> -->
<%-- 		<a href="${contextPath }/activity/showDeadTempl.do" class="list-group-item" id="dead_user_menu">  --%>
<!-- 			沉寂活动模板 -->
<!-- 		</a> -->
	</div>
</div>