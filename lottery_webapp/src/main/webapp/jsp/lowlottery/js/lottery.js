$(document).ready(function() {
	$(".lottery_win_desc").find("table").hide();
	$("._lottery_win_desc_btn").click(function() {
		var table = $(".lottery_win_desc").find("table");
		if (table.css("display") == "none") {
			table.show(300);
			$(this).text("隐藏");
		} else {
			table.hide(300);
			$(this).text("展开");
		}
	});
});