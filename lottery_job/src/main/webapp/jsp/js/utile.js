function getNowTime(offset_month, offset_day) {
	var now = new Date();
	var year = now.getYear();
	var month = now.getMonth() + 1 + offset_month;
	var day = now.getDate() + offset_day;
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	var nowTime = year + 1900 + "-" + month + "-" + day + " " + hour + ":" + minute;
	// + ":" + second;
	return nowTime;
}

function contrastTime(time1, time2) {
	var b = Date.parse(time1.replace(/-/g, "/")) > Date.parse(time2.replace(/-/g, "/"));
 	return b;
}
