<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="${pageContext.request.contextPath}\jsp\js\jquery-format-currence.js" type="text/javascript" ></script>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script>
	$(document).ready(function() {
		//配置
		Messenger.options = {
			extraClasses : 'messenger-fixed messenger-on-top',
			theme : 'future'
		};
		if ('${errorMsg}' != "") {
			Messenger().post({
				message : '${errorMsg }',
				type : 'error',
				showCloseButton : true
			});
		}
		if ('${param.errorMsg}' != "") {
			Messenger().post({
				message : '${param.errorMsg}',
				type : 'error',
				showCloseButton : true
			});
		}
		if ('${success}' != "") {
			Messenger().post({
				message : "${success}",
				showCloseButton : true
			});
		}
		if ('${param.success}' != "") {
			Messenger().post({
				message : "${param.success}",
				showCloseButton : true
			});
		}
	});
</script>
<%

	//String isAction = (String) session.getAttribute("isAction");
	//if (isAction == null || !isAction.equals("true")) {
	//	session.invalidate();
	//	response.sendRedirect(request.getContextPath() + "/gohome.do");
	//} else {
	//	session.removeAttribute("isAction");
	//}

%>