//组合
function C(arr, num) {
	var r = [];
	(function f(t, a, n) {
		if (n == 0)
			return r.push(t);
		for (var i = 0, l = a.length; i <= l - n; i++) {
			f(t.concat(a[i]), a.slice(i + 1), n - 1);
		}
	})([], arr, num);
	return r;
}

//排列
function A(arr, num) {
	var r = [];
	(function f(t, a, n) {
		if (n == 0)
			return r.push(t);
		for (var i = 0, l = a.length; i < l; i++) {
			f(t.concat(a[i]), a.slice(0, i).concat(a.slice(i + 1)), n - 1);
		}
	})([], arr, num);
	return r;
}

var r1 = C("0123456789".split(""), 3);
//document.write('组合：<ol><li>' + r1.join('</li><li>') + '</li></ol><br/><br/>');
var r2 = A("0123456789".split(""), 3);
//document.write('排列：<ol><li>' + r2.join('</li><li>') + '</li></ol>');