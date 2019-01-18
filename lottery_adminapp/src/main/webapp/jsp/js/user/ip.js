$(document).ready(function(){
	$("._queryBtn").click(function(){
		ip.showIps(1);
	});
	ip.showIps(1);
});

var ip = function(){
	
	var bckfun = {
		showbck : function(data){
			var $orderTbody = $("#showbody");
			$orderTbody.html("");
			var $tr = $("._hidTr").clone(true);
			var objs = data.page.pagelist;
			$tr.show();
			for(var i=0;i<objs.length;i++){
				var obj = objs[i];
				$tr.find("td").eq(0).text(i+1);
				$tr.find("td").eq(1).html("");
				$tr.find("td").eq(1).append($("<a>").attr("href",ctx+"/user/showUserInfo/"+obj[0]+".do").text(obj[1]));
				$tr.find("td").eq(2).text(obj[2]);
				$tr.find("td").eq(3).text(obj[5]);
				$tr.find("td").eq(4).text(obj[4]);
				if(obj[6] ==null){
					$tr.find("td").eq(5).text("");
				}else{
					$tr.find("td").eq(5).text(obj[6]);
				}
				if(obj[7] ==null){
					$tr.find("td").eq(6).text("");
				}else{
					$tr.find("td").eq(6).text(obj[7]);
				}
				
				$tr.find("td").eq(7).text(obj[8]);
				$tr.find("td").eq(8).text(obj[9]);
				$tr.find("td").eq(9).find(".userId").val(obj[1]);
				$tr.find("td").eq(9).find(".rsvst3").val(obj[10]);
				$orderTbody.append($tr.clone(true));
			}
			
			$("._killUser").unbind();
			$("._killUser").on('click',function(){
				ip.killUser($(this).find(".userId").val());
			});
			
			$(".showFileName").unbind();
			$(".showFileName").on("click",function(){
				$("#view_model .modal-body").text($(this).find("input").val());
				$("#view_model").modal("show");
			});
			// 单行表格加背景色
			$('.accountTab tbody tr:odd').find("td").css({
				'background-color' : '#FFF'
			});
			$('.accountTab tbody tr:even').find("td").css({
				'background-color' : '#f6f6f6'
			});
			pageInfo("_pageInfo",data.page.pageNum,data.page.maxCount,data.page.pageCount);
		}	
	};
	var pageInfo = function(divId,pageNum,maxCount,pageCount){
		var html = "";
		if(parseInt(pageNum)-1<0){
			pageNum=1;
		}
		if(parseInt(pageNum)-parseInt(pageCount)>=0){
			pageNum = pageCount;
		}
		html += "<li><a href='javaScript:;' onClick='ip.showIps("+(pageNum-1)+")'>上一页</a></li><li>第"
					+pageNum+"页/共"+pageCount+"页</li><li><a href='javaScript:;' onClick='ip.showIps("+(pageNum+1)+")'>下一页</a></li>";
		$("."+divId).html(html);
	};
	return{
		killUser : function(userName){
			var attrs = [];
			var vals = [];
			
			attrs.push("uname");
			attrs.push("ipAddress");
			
			vals.push(userName);
			vals.push(webctx);
			
			formPost.submitAjaxForm("user/killUser.do",attrs,vals,this.showKillRs);
		},
		showIps : function(page){
			var attrs = new Array();
			var vals = new Array();
			attrs.push("pageNum");
			attrs.push("uname");
			attrs.push("ip");
			attrs.push("cpuid");
			attrs.push("diskid");
			attrs.push("openCardName");
			attrs.push("cardNo");
			vals.push(page);
			vals.push($("#uname").val());
			vals.push($("#ip").val());
			vals.push($("#cpuid").val());
			vals.push($("#diskid").val());
			vals.push($("#openCardName").val());
			vals.push($("#cardNo").val());
			
			var $orderTbody = $("#showbody");
			$orderTbody.html("<tr><td colspan='10' align='center'>查询中，请稍候......</td></tr>");
			formPost.submitAjaxForm("user/queryips.do",attrs,vals,bckfun["showbck"]);
		},
		showKillRs : function(data){
			formPost.showErrorMessage(data.info);
			return;
		}
	};
}();