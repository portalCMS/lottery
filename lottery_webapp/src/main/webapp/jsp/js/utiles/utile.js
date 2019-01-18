Date.prototype.Format = function(fmt) {// author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

function getNowTime(offset_month, offset_day) {
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth() + 1 + offset_month;
	var day = now.getDate() + offset_day;
	var hour = now.getHours();
	var minute = now.getMinutes();
	// var second = now.getSeconds();
	var nowTime = year + "-" + month + "-" + day + " " + hour + ":"
			+ minute;
	// + ":" + second;
	return nowTime;
}

function getNowTime1(offset_month, offset_day) {
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth() + 1 + offset_month;
	var day = now.getDate() + offset_day;
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	var nowTime = year + "-" + month + "-" + day + " " + hour + ":"
			+ minute + ":" + second;
	return nowTime;
}

function getTime(offset_month, offset_day,offset_hour) {
	var now = new Date();
	now.setDate(now.getDate()+offset_day)
	var year = now.getFullYear();
	var month = now.getMonth() + 1 + offset_month;
	var day = now.getDate();
	var nowTime = year + "-" + month + "-" + day + " " + offset_hour + ":"
			+ "00" + ":" + "00";
	return nowTime;
}

function getNowDate(offset_month, offset_day) {
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth() + 1 + offset_month;
	var day = now.getDate() + offset_day;
	var nowTime = year + "-" + month + "-" + day;
	return nowTime;
}

function contrastTime(time1, time2) {
	var b = Date.parse(time1.replace(/-/g, "/")) > Date.parse(time2.replace(
			/-/g, "/"));
	return b;
}

// 获取两时间段的差值
function getDateDiff(startTime, endTime, diffType) {
    startTime = startTime.replace(/-/g, "/");
    endTime = endTime.replace(/-/g, "/");
    diffType = diffType.toLowerCase();
    var sTime = new Date(startTime); //开始时间
    var eTime = new Date(endTime); //结束时间
    var divNum = 1;
    switch (diffType) {
        case "second":
            divNum = 1000;
            break;
        case "minute":
            divNum = 1000 * 60;
            break;
        case "hour":
            divNum = 1000 * 3600;
            break;
        case "day":
            divNum = 1000 * 3600 * 24;
            break;
        default:
            break;
    }
    return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
}

