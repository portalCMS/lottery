<script src="${contextPath }/jsp/js/jQuery.min.js"></script>
<script src="${contextPath }/jsp/js/message/messenger.min.js"></script>
<script src="${contextPath }/jsp/js/bootstrap.min.js"></script>
<script src="${contextPath }/jsp/js/global.js"></script>
<script src="${contextPath }/jsp/js/checkForm/checkForm.js"></script>
<script src="${contextPath }/jsp/js/checkForm/formValidatorRegex.js"></script>
<%

	String isAction = (String) session.getAttribute("isAction");
	if (isAction == null || !isAction.equals("true")) {
		session.invalidate();
		response.sendRedirect("http://www.baidu.com");
	} else {
		session.removeAttribute("isAction");
	}

%>