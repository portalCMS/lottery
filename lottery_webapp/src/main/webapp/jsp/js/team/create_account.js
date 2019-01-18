var createAccount = function() {

	return {
		proxypackagedata : function(attrs, values) {
			var flag = true;
			$(".proxyadd").each(
					function() {
						if($(this).attr("name") == "quotaId"&&!isNaN($(this).val())&&$(this).val()<1){
							attrs.push($(this).attr("name"));
							attrs.push("minRebates");
						}else{
							attrs.push($(this).attr("name"));
						}
						if ($(this).attr("name") == "customerName"
								&& $(this).val() == "") {
							formPost.showErrorMessage("用户名不能为空");
							flag = false;
							return flag;
						}
						if ($(this).attr("name") == "customerName"
								&& $(this).val().length < 6) {
							formPost.showErrorMessage("用户名长度不能低于6位");
							flag = false;
							return flag;
						}
						if ($(this).attr("name") == "minRebates"
								&& $(this).val() == "") {
							formPost.showErrorMessage("请输入返点");
							flag = false;
							return flag;
						}
						if ($(this).attr("name") == "minRebates"
								&& isNaN($(this).val())) {
							formPost.showErrorMessage("返点必须是数字");
							flag = false;
							return flag;
						}
						if($(this).attr("name") == "quotaId"&&!isNaN($(this).val())&&$(this).val()<1){
							values.push(0);
							values.push($(this).val());
						}else{
							values.push($(this).val());
						}
					});
			return flag;
		},
		proxypackagesubmit : function() {
			var attrs = new Array();
			var values = new Array();
			var flag = createAccount.proxypackagedata(attrs, values);
			if (flag)
				formPost.submitAjaxForm1("team/savecreate.shtml", attrs,
						values, createAccount.proxyajaxdata, "#proxy");
		},
		proxyajaxdata : function(data, div) {
			if (div != undefined && div != "") {
				$("#" + div).addClass("ml15 color_red");
				$("#" + div).html("保存成功(随机密码:" + data.pwd + ")");
			} else {
				$("body").sMsg({
					content : "保存成功(随机密码:" + data.pwd + ")",
					autoDisplay : true,
					actionEvent : ""
				});
				$("#copyBtn").zclip({
					path:ctx+'/jsp/js/ZeroClipboard.swf',
					copy:data.pwd,
					afterCopy:function(){
						alert("密码已复制到剪切板");
					}
				});
			}
			$("#proxy #customerName").val("");
			// $("#proxy #customerAlias").val("");
			$("#proxy #token").val(data.token);
			formPost.submitAjaxForm1("team/getSelfQuotas.shtml", null, null,
					createAccount.quotaproxyhtml);
			formPost.submitAjaxForm1("team/getSelfQuotas.shtml", null, null,
					createAccount.quotamemberhtml);
		},
		memberpackagedata : function(attrs, values) {
			var flag = true;
			$(".memberadd").each(
					function() {
						if($(this).attr("name") == "quotaId"&&!isNaN($(this).val())&&$(this).val()<1){
							attrs.push($(this).attr("name"));
							attrs.push("minRebates");
						}else{
							attrs.push($(this).attr("name"));
						}
						if ($(this).attr("name") == "customerName"
								&& $(this).val() == "") {
							formPost.showErrorMessage("用户名不能为空");
							flag = false;
							return flag;
						}
						if ($(this).attr("name") == "customerName"
								&& $(this).val().length < 6) {
							formPost.showErrorMessage("用户名长度不能低于6位");
							flag = false;
							return flag;
						}
						if ($(this).attr("name") == "minRebates"
								&& $(this).val() == "") {
							formPost.showErrorMessage("请输入返点");
							flag = false;
							return flag;
						}
						if ($(this).attr("name") == "minRebates"
								&& isNaN($(this).val())) {
							formPost.showErrorMessage("返点必须是数字");
							flag = false;
							return flag;
						}
						if($(this).attr("name") == "quotaId"&&!isNaN($(this).val())&&$(this).val()<1){
							values.push(0);
							values.push($(this).val());
						}else{
							values.push($(this).val());
						}
					});
			return flag;
		},
		membersubmit : function() {
			var attrs = new Array();
			var values = new Array();
			var flag = createAccount.memberpackagedata(attrs, values);
			if (flag)
				formPost.submitAjaxForm1("team/savecreate.shtml", attrs,
						values, createAccount.memberajaxdata, "#member");
		},
		memberajaxdata : function(data, div) {
			if (div != undefined && div != "") {
				$("#" + div).addClass("ml15 color_red");
				$("#" + div).html("保存成功(随机密码:" + data.pwd + ")");
			} else {
				$("body").sMsg({
					content : "保存成功(随机密码:" + data.pwd + ")",
					autoDisplay : true,
					actionEvent : ""
				});
				$("#copyBtn").zclip({
					path:ctx+'/jsp/js/ZeroClipboard.swf',
					copy:data.pwd,
					afterCopy:function(){
						alert("密码已复制到剪切板");
					}
				});
			}
			$("#member #customerName").val("");
			// $("#member #customerAlias").val("");
			$("#proxy #token").val(data.token);
			formPost.submitAjaxForm1("team/getSelfQuotas.shtml", null, null,
					createAccount.quotaproxyhtml);
			formPost.submitAjaxForm1("team/getSelfQuotas.shtml", null, null,
					createAccount.quotamemberhtml);
		},
		quotaproxyhtml : function(data) {
			var quotas = data.quotalist;
			var html = "";
			for (var i = 0; i < quotas.length; i++) {
				html += "<option value='" + quotas[i].id + "'>"
						+ quotas[i].proportion * 1000 / 10 + "%--剩余"
						+ quotas[i].quota_count + "</option>";
			}
			var qtQuota = data.qtQuota;
			for (var i = 0; i < qtQuota.length; i++) {
				html += "<option value='" + qtQuota[i]/1000 + "'>"
						+  qtQuota[i] / 10 + "%</option>";
			}
			$("#proxy #token").val(data.token);
			$("#proxy #quotaId").html(html);
		},
		quotamemberhtml : function(data) {
			var quotas = data.quotalist;
			var html = "";
			for (var i = 0; i < quotas.length; i++) {
				html += "<option value='" + quotas[i].id + "'>"
						+ quotas[i].proportion * 1000 / 10 + "%--剩余"
						+ quotas[i].quota_count + "</option>";
			}
			var qtQuota = data.qtQuota;
			for (var i = 0; i < qtQuota.length; i++) {
				html += "<option value='" + qtQuota[i]/1000 + "'>"
						+  qtQuota[i] / 10 + "%</option>";
			}
			$("#member #token").val(data.token);
			$("#member #quotaId").html(html);
		},
		linkOpenAccount : function(){
			var quotaId = 0;
			var host = window.location.host;
			if(isNaN($("#linkInput").val())){
				formPost.showErrorMessage("返点必须是数字");
				return;
			}
			if($("#linkInput").val()>=12.0){
				formPost.showErrorMessage("连接开户返点必须小于等于12%");
				return;
			}
			if($("._showlink").val()==0){
				quotaId = $("#linkInput").val();
				quotaId = quotaId*0.01;
			}else{
				quotaId = $("._showlink").val();
			}
			$("._links").find("a").text("http://"+host+ctx+"/reg/linkreguser.html?links="
					+BASE64.encoder($("#userId").val()+","+quotaId+","+$("#memberType").val()));
			
			if($(".zclip").html()==undefined){
				$("._links").closest("p").append($("<a/>").addClass("miniBtn ml70 _copyUrl mt10")
						.append($("<span/>").text("复制地址")));
			}
			
			$("._copyUrl").zclip({
				path:ctx+'/jsp/js/ZeroClipboard.swf',
				copy:$("._links").find("a").text(),
				afterCopy:function(){
					formPost.showErrorMessage("地址已复制到剪切板");
				}
			});
		}
	};
}();

$(document).ready(function() {
	$("._linkBtn").click(function() {
		createAccount.linkOpenAccount();
	});
	$(".proxyBtn").click(function() {
		createAccount.proxypackagesubmit();
	});
	$.checkFrom.initBind(".proxyadd", true, true, true);
	$(".memberBtn").click(function() {
		createAccount.membersubmit();
	});
	$("#createAccountMenu").addClass("current");
	$.checkFrom.initBind(".memberadd", true, true, true);
	$("._showProxyInput").on("change click", function() {
		if ($(this).find("option:selected").val() == "0") {
			if ($("._pinput").is(":hidden")) {
				$("._pinput").show();
				$("#proxyInput").addClass("proxyadd");
			}
		} else {
			$("._pinput").hide();
			$("#proxyInput").val("");
			$("#proxyInput").removeClass("proxyadd");
		}
	});
	$("._showMemberInput").on("change click", function() {
		if ($(this).find("option:selected").val() == "0") {
			if ($("._minput").is(":hidden")) {
				$("._minput").show();
				$("#memberInput").addClass("memberadd");
			}
		} else {
			$("._minput").hide();
			$("#memberInput").val("");
			$("#memberInput").removeClass("memberadd");
		}
	});
	$("._showlink").on("change click", function() {
		if ($(this).find("option:selected").val() == "0") {
			if ($("._linput").is(":hidden")) {
				$("._linput").show();
				$("#linkInput").addClass("linkadd");
			}
		} else {
			$("._linput").hide();
			$("#linkInput").val("");
			$("#linkInput").removeClass("linkadd");
		}
	});
	$("select").trigger("click");
});