var teams = function(){
	var ids = [];
	var initUser = function(userInfo,team){
		$("._teamName").html(userInfo.customerName+"的团队");
		$("._userRebates").html(userInfo.rebates*1000/10+"%");
		if(parseInt(team[0])>0){
			$("._directSub").html(team[0]+"人（<a href='javascript:void(0);' class='color_blue underline _directshow'>数据分析 </a>）");
		}else{
			$("._directSub").html(team[0]+"人");
		}
		if(parseInt(team[1])>0){
			$("._allTeam").html(team[1]+"人（<a href='javascript:void(0);' class='color_blue underline _allshow'>数据分析 </a>）");
		}else{
			$("._allTeam").html(team[1]+"人");
		}
	};
	var initBreads = function(breads){
		var html = "";
		ids.length = 0;
		for(var i=0;i<breads.length;i++){
			var bread = breads[i];
			ids.push(bread[0]);
			if(breads.length == 1)break;  //第一级不显示
			if(i==0){
				html+="<li><a href=\"javaScript:void(0);\" class=\"color_blue underline _breaduser\" id='"+i+"'>总销量</a> <span class=\"color_8d _amount\">"+bread[4]+"</span></li>";
			}else if(i+1==breads.length){
				html+="<li><label>&gt;</label></li>";
				html+="<li><span>"+bread[2]+"</span></li>";
			}else{
				html+="<li><label>&gt;</label></li>";
				html+="<li><a href=\"javaScript:void(0);\" class=\"color_blue underline _breaduser\" id='"+i+"' >"+bread[2]+"</a> <span class=\"color_8d _amount\">"+bread[4]+"</span></li>";
			}
		}
		$(".breadList").show();
		$("._bread").html(html);
		$("._breaduser").click(function(){
			var length = parseInt($(this).attr("id"))+1;
			var temp = ids.concat();
			ids.length = 0;
			for(var i=0;i<length;i++){
				ids.push(temp[i]);
			}
			teams.queryTeamInfo(1);
		});
		$("._directshow").unbind();
		$("._directshow").click(function(){
			teams.showDirectAgent();
		});
		$("._allshow").unbind();
		$("._allshow").click(function(){
			teams.showAllTeam();
		});
		$("._amount").formatCurrency();
	};
	var initTable = function(page){
		formPost.setTbodyObj(null,null);
		$("#data").html("");
		var tp = page.pageCount;
		var pn= page.pageNum;
		var vos = page.pagelist;
		var smap = new Map();
		$("#customerOnlineStatus").find("option").each(function(){
			smap.put($(this).val(), $(this).text());
		});
		//如果没有数据则页面不显示分页信息。
		if(vos==undefined||vos==null||vos.length==0){
			$("#data").html("<tr><td  height='39' align='center' " +
						" colspan='8' align='middle' class='f12 color_8d' >暂无记录<td></tr>");
			$("#_myPageDiv").html("");
			return;
		}
		var trObj = $("._tr").clone(true);
		for(var i=0;i<vos.length;i++){
			var vo = vos[i];
			$(trObj).find("td").eq(0).html(vo.customerName);
			if(vo.customerType == 12001){
				$(trObj).find("td").eq(1).html("代理");
			}else{
				$(trObj).find("td").eq(1).html("会员");
			}
			$(trObj).find("td").eq(2).html(vo.customerLower);
			$(trObj).find("td").eq(3).html(vo.rebates*1000/10);
			//$(trObj).find("td").eq(4).addClass("_userAmount color_red");
			//$(trObj).find("td").eq(4).html(vo.userBetMoney);
			$(trObj).find("td").eq(4).html(vo.createTime);
			$(trObj).find("td").eq(5).addClass("_amount color_red").html(vo.cash);
			$(trObj).find("td").eq(6).html(smap.get(vo.customerOnlineStatus));
			if(vo.customerOnlineStatus =='11002'){
				$(trObj).find("td").eq(6).addClass("color_green");
			}
			$(trObj).find("td").eq(7).find("input").val(vo.id);
			$("#data").append($(trObj).clone(true).show());
		}
		// 单行表格加背景色
		$('.accountTab tbody tr:odd').find("td").css({
			'background-color' : '#FFF'
		});
		$('.accountTab tbody tr:even').find("td").css({
			'background-color' : '#f6f6f6'
		});
		$("._userAmount").formatCurrency();
		$("._amount").formatCurrency();
		//显示分页选择显示栏
		pageBarBuid.showPageBar("teams.queryTeamInfo",pn,tp);
	};
	var callBackFuc = {
	  "teamInfo":function(data){
		  var breads = data.breads;
		  initUser(data.user,data.team);
		  initBreads(breads);
		  initTable(data.page);
	  },
	  "pageTable":function(data){
		  initTable(data.page);
	  },
	  "showDirectAgent":function(data){
		  data = data.DirectAgent;
		  $("#title").text("团队发现情况");
		  var yes = "昨日新增：<span>"+data.dayNum+"人</span>";
		  if(parseInt(data.dayNum)==0){
			  yes += "<span>0.00%</span>"
		  }else if(parseInt(data.dayNum)>0 && parseInt(data.YesterdayNum)==0){
			  yes += "<span>100.00%</span>"
		  }else{
			  yes +="<span>"+NumberFormat.formatNumber(((parseInt(data.dayNum)-parseInt(data.YesterdayNum))/parseInt(data.YesterdayNum))*100,2,0)+"%</span>";
		  }
	      var week = "本周新增：<span>"+data.weekNum+"人</span>";
	      if(parseInt(data.weekNum)==0){
	    	  week += "<span>0.00%</span>"
		  }else if(parseInt(data.weekNum)>0 && parseInt(data.lastWeekNum)==0){
			  week += "<span>100.00%</span>"
		  }else{
			  week +="<span>"+NumberFormat.formatNumber(((parseInt(data.weekNum)-parseInt(data.lastWeekNum))/parseInt(data.lastWeekNum))*100,2,0)+"%</span>";
		  }
	      
	      var month = "本月新增：<span>"+data.monthNum+"人</span>";
	      if(parseInt(data.monthNum)==0){
	    	  month += "<span>0.00%</span>"
		  }else if(parseInt(data.monthNum)>0 && parseInt(data.lastMonthNum)==0){
			  month += "<span>100.00%</span>"
		  }else{
			  month +="<span>"+NumberFormat.formatNumber(((parseInt(data.monthNum)-parseInt(data.lastMonthNum))/parseInt(data.lastMonthNum))*100,2,0)+"%</span>";
		  }
	      $("#yesterday").html(yes);
	      $("#week").html(week);
	      $("#month").html(month);
	      $("#box").show();
	      
	      $("#close").click(function(){
	    	  $("#box").hide();
	      });
	  },
	  "showAllTeam":function(data){
		  data = data.allTeam;
		  $("#title").text("团队发现情况");
		  var yes = "昨日新增：<span>"+data.dayNum+"人</span>";
		  if(parseInt(data.dayNum)==0){
			  yes += "<span>0.00%</span>"
		  }else if(parseInt(data.dayNum)>0 && parseInt(data.YesterdayNum)==0){
			  yes += "<span>100.00%</span>"
		  }else{
			  yes +="<span>"+NumberFormat.formatNumber(((parseInt(data.dayNum)-parseInt(data.YesterdayNum))/parseInt(data.YesterdayNum))*100,2,0)+"%</span>";
		  }
	      var week = "本周新增：<span>"+data.weekNum+"人</span>";
	      if(parseInt(data.weekNum)==0){
	    	  week += "<span>0.00%</span>"
		  }else if(parseInt(data.weekNum)>0 && parseInt(data.lastWeekNum)==0){
			  week += "<span>100.00%</span>"
		  }else{
			  week +="<span>"+NumberFormat.formatNumber(((parseInt(data.weekNum)-parseInt(data.lastWeekNum))/parseInt(data.lastWeekNum))*100,2,0)+"%</span>";
		  }
	      
	      var month = "本月新增：<span>"+data.monthNum+"人</span>";
	      if(parseInt(data.monthNum)==0){
	    	  month += "<span>0.00%</span>"
		  }else if(parseInt(data.monthNum)>0 && parseInt(data.lastMonthNum)==0){
			  month += "<span>100.00%</span>"
		  }else{
			  month +="<span>"+NumberFormat.formatNumber(((parseInt(data.monthNum)-parseInt(data.lastMonthNum))/parseInt(data.lastMonthNum))*100,2,0)+"%</span>";
		  }
	      $("#yesterday").html(yes);
	      $("#week").html(week);
	      $("#month").html(month);
	      $("#box").show();
	      $("#close").click(function(){
	    	  $("#box").hide();
	      });
	  }
	};
	var packTeamInfoData = function(attrs,values){
		var idsStr = "";
		for(var i=0;i<ids.length;i++){
			if(idsStr == ""){
				idsStr += ids[i];
			}else{
				idsStr += ","+ids[i];
			}
		}
		attrs.push("idsTree");
		values.push(idsStr);
	};
	return{
		showDirectAgent:function(){
			var attrs = new Array();
			attrs.push("type");
			var values = new Array();
			values.push("1");
			formPost.submitAjaxForm1("team/staTeamInfo.shtml",attrs,values,callBackFuc["showDirectAgent"],null);
		},
		showAllTeam:function(){
			var attrs = new Array();
			attrs.push("type");
			var values = new Array();
			values.push("2");
			formPost.submitAjaxForm1("team/staTeamInfo.shtml",attrs,values,callBackFuc["showAllTeam"],null);
		},
		queryTeamInfo:function(pageNum,id){
			var attrs = new Array();
			var values = new Array();
			
			attrs.push("pageNum");
			if(undefined==pageNum){
				values.push("1");
			}else{
				values.push(pageNum);
			}
			if(undefined != id){
				attrs.push("id");
				values.push(id);
			}
			packTeamInfoData(attrs,values);
			formPost.setTbodyObj($("#data"),$("._tr"));
			formPost.submitAjaxForm1("team/showTeamInfo.shtml",attrs,values,callBackFuc["teamInfo"],null);
		},
		queryTeamUserByKey:function(pageNum){
			var check = this.checkInput("userCash1");
			var check2 = this.checkInput("userCash2");
			if(!check||!check2){
				return;
			}
			var attrs = new Array();
			var values = new Array();
			attrs.push("pageNum");
			if(undefined==pageNum){
				values.push("1");
			}else{
				values.push(pageNum);
			}
			attrs.push("rsvst1");
			values.push($("#customerOnlineStatus").val());
			attrs.push("rebates");
			attrs.push("customerName");
			attrs.push("cashMoney");
			attrs.push("isDesc");
			
		
			values.push($("#rebates").val().trim());
			values.push($("#customerName").val().trim());
			var cash1 = 0;
			var cash2 = 999999999;
			if($("#userCash1").val()!=undefined && $("#userCash1").val().trim()!=""){
				cash1 = $("#userCash1").val().trim();
			}
			if($("#userCash2").val()!=undefined && $("#userCash2").val().trim()!=""){
				cash2 = $("#userCash2").val().trim();
			}
			var cashArea = cash1 + "-" + cash2; 
			values.push(cashArea);
			//排序依据
			values.push($("#sortKey").val());
			formPost.setTbodyObj($("#data"),$("._tr"));
			formPost.submitAjaxForm1("team/queryTeamUserByKey.shtml",attrs,values,callBackFuc["pageTable"],null);
		},
		checkInput:function(id){
			var amountVal = $("#"+id).val();
			if(isNaN(amountVal)){
				formPost.showErrorMessage("余额金额必须为数字");
				$("#"+id).select();
				return false;
			}
			return true;
		}
	};
}();

$(document).ready(function(){
	$(".breadList").hide();
	$("._detailInfo").on("click",function(){
		var id = $(this).next().val();
		teams.queryTeamInfo(1,id);
	});
	$(".paging").hide();
	$("#userMenu").addClass("current");
	teams.queryTeamInfo(1);
	
	$("#queryTeamUserByKey").on('click',function(){
		teams.queryTeamUserByKey(1);
	});
});