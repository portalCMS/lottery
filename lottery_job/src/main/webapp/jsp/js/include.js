$(document).ready(function() {
	$.ajax({
		url : "/job/include/top.html",
		success : function(data) {
			$(".top_include").html(data);
		}
	});

	$('.tip').tooltip('hide');
	$('.tip').hover(function() {
		$(this).tooltip('show');
	}, function() {
		$(this).tooltip('hide');
	});
});
