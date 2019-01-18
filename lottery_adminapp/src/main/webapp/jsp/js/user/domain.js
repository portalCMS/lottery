function comdelUrl(id,url) {
	$("#del_domain_info").text(url);
	$("#del_domain").modal("show");
	var delid = id;
	$("#delUrl").click(function() {
		location.href = "deleteCustomerUrl.do?id="+delid;
	});
}