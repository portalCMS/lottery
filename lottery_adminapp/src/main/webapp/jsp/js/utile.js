function getNowTime(offset_month, offset_day) {
	var now = new Date();
	now = now.addDay(offset_day);
	var nowTime = now.format("yyyy-MM-dd hh:mm:ss");
	return nowTime;
}

function contrastTime(time1, time2) {
	var b = Date.parse(time1.replace(/-/g, "/")) > Date.parse(time2.replace(/-/g, "/"));
 	return b;
}


Date.prototype.format = function(format) {  
    /* 
     * eg:format="yyyy-MM-dd hh:mm:ss"; 
     */  
    var o = {  
        "M+" : this.getMonth() + 1, // month  
        "d+" : this.getDate(), // day  
        "h+" : this.getHours(), // hour  
        "m+" : this.getMinutes(), // minute  
        "s+" : this.getSeconds(), // second  
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S" : this.getMilliseconds()  
        // millisecond  
    };  
  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
                        - RegExp.$1.length));  
    }  
  
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1  
                            ? o[k]  
                            : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
};

Date.prototype.addDay = function(days){
	var dateTimes = this.getTime()+days*24*60*60*1000;
	var newTime = new Date(dateTimes);
	return newTime;
};