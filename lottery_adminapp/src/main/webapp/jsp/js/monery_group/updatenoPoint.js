var noPoint = function(){
	
	return{
		initData:function(){
			var _theoryBonus = parseFloat($("._rebates").val());
			var _rebates = parseFloat($("._payoutRatio").text());
			var _interval = parseFloat($("._interval").text());
			var _monery = parseFloat($("._monery").val());
			var html = "";
			html+="<tr>";
			html+="<td class=\"text-center\">"+_rebates+"<input type=\"hidden\" class=\"_addparam\" value=\""+_rebates+"\" id=\"rebates\" /></td>";
			html+="<td><input type=\"text\" class=\"form-control _addparam1\" style=\"width: 100px\" value=\""+_theoryBonus+"\" id=\"bonus\" /></td>";
			html+="</tr>";
			for(var i=_rebates;i>=0;i=i-_interval){
				_theoryBonus = _theoryBonus+_monery;
				_rebates = ((_rebates*1000) - (_interval*1000))/1000;
				html+="<tr>";
				html+="<td class=\"text-center\">"+_rebates+"<input type=\"hidden\" class=\"_addparam\" value=\""+_rebates+"\" id=\"rebates\" /></td>";
				html+="<td><input type=\"text\" class=\"form-control _addparam1\" style=\"width: 100px\" value=\""+_theoryBonus+"\" id=\"bonus\" /></td>";
				html+="</tr>";
			}
			$("._norebates").html(html);
		},
		generateData:function(){
			var _theoryBonus = parseFloat($("._rebates").val());
			var _rebates = parseFloat($("._payoutRatio").text());
			var _interval = parseFloat($("._interval").text());
			var _monery = parseFloat($("._monery").val());
			var re =new RegExp(getRex("intege1"));
			if(_monery==undefined||_monery==""){
				formPost.showErrorMessage("对应扣款不能为空");
				$("._norebates").html("");
				return;
			}
			if(!re.test(_monery)){
				formPost.showErrorMessage("对应扣款必须是正整数");
				$("._norebates").html("");
				return;
			}
			var html = "";
			html+="<tr>";
			html+="<td class=\"text-center\">"+_rebates+"<input type=\"hidden\" class=\"_addparam\" value=\""+_rebates+"\" id=\"rebates\" /></td>";
			html+="<td><input type=\"text\" class=\"form-control _addparam1\" style=\"width: 100px\" value=\""+_theoryBonus+"\" id=\"bonus\" /></td>";
			html+="</tr>";
			for(var i=_rebates;i>=0;i=i-_interval){
				_theoryBonus = _theoryBonus+_monery;
				_rebates = ((_rebates*1000) - (_interval*1000))/1000;
				html+="<tr>";
				html+="<td class=\"text-center\">"+_rebates+"<input type=\"hidden\" class=\"_addparam\" value=\""+_rebates+"\" id=\"rebates\"></td>";
				html+="<td><input type=\"text\" class=\"form-control _addparam1\" style=\"width: 100px\" value=\""+_theoryBonus+"\" id=\"bonus\"/></td>";
				html+="</tr>";
			}
			$("._norebates").html(html);
		},
		packageBonusData:function(attrs,values){
			var i = 0;
			var error = 0;
			var flag = true;
			$("._addparam").each(function(){
				attrs.push("vos["+i+"]."+$(this).attr("id"));
				values.push($(this).val());
				i++;
			});
			i = 0;
			$("._addparam1").each(function(){
				attrs.push("vos["+i+"]."+$(this).attr("id"));
				var re =new RegExp(getRex("num"));
				if(!re.test($(this).val())){
					formPost.showErrorMessage("对应奖金必须是数字");
					$(this).focus();
					error++;
					return;
				}
				values.push($(this).val());
				i++;
			});
			if(error>0)flag = false;
			return flag;
		},
		nextDone:function(){
			var attrs = new Array();
			var values = new Array();
			//var flag = false;
			var flag = noPoint.packageBonusData(attrs, values);
			if(flag)formPost.submitForm("updatenextDone.do",attrs,values);
		}
	};
}();

$(document).ready(function(){
	$("._generate").click(function(){
		noPoint.generateData();
	});
	$("._nextDone").click(function(){
		noPoint.nextDone();
	});
});