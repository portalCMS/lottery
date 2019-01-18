$(document).ready(function() {
		var table = $("table");
		table.each(function(index, value) {
			var self = $(this);
			$(this).find("tr").hide();
			$(this).find("tr").eq(0).show();
			$(this).find("tr").eq(1).show().find("a").on("click", function() {
				$(self).find("tr").eq(2).toggle();
				$(table).eq(index).find("tbody tr").toggle();
			});
		});
		$("#_labPanle").hide();
		$("._addPanleBtn").on("click", function() {
			$("#_labPanle").toggle();
		});
		$("._cancelAddBtn").on("click", function() {
			$("#_labPanle").toggle();
		});
		
		$("._updateBtn").on("click", function() {
			cardInvConfig.showCardInv(this);
		});
	   $("._queryInv").on("click",function(){
		   cardInvConfig.queryInvCard(this);
	   });
	   $("._addCardInv").on('click',function(){
		   cardInvConfig.addCardInv(this);
	   });
	   
	   $("#_updatePanle").hide();
	   $("._updateCardInv").on('click',function(){
		   cardInvConfig.updateCardInv(this);
	   });
	   $("._cancelPanleBtn").on('click',function(){
		   $("#_updatePanle").hide();
	   });
	   $.checkFrom.initBind(".checkedAttr", true, true,true);
});

var cardInvConfig = function(){
	return {
		addCardInv:function(obj){
			//做一次所有的表单格式验证
			var check = $.checkFrom.checkAllType("#_labPanle");
			if(!check){
				return;
			}
			var attrs = new Array();
			attrs.push("cardInventoryName");
			attrs.push("remark");
			var vals = new Array();
			vals.push($("#newInvName").val());
			vals.push($("#newInvRemark").val());
			var action = "addCardInv.do";
			formPost.submitForm(action,attrs,vals);
		},
		showCardInv:function(obj){
			var invId = $(obj).closest("table").find("._invId").val();
			var invName = $(obj).closest("table").find("._invName").text();
			var invRemark = $(obj).closest("table").find("._remark").text();
			$("#_updatePanle ._updateId").val(invId);
			$("#_updatePanle #updateInvRemark").val(invRemark);
			$("#_updatePanle #updateInvName").val(invName);
			$("#_updatePanle").toggle();
		},
		updateCardInv:function(obj){
			var attrs = new Array();
			attrs.push("id");
			attrs.push("cardInventoryName");
			attrs.push("remark");
			var vals = new Array();
			vals.push($(obj).find("input").val());
			vals.push($("#updateInvName").val());
			vals.push($("#updateInvRemark").val());
			var action = "updateCardInv.do";
			formPost.submitForm(action,attrs,vals);
		},
		queryInvCard:function(obj){
			var dp = $(obj).closest("table").find("tbody tr").css('display');
			if(dp=='none'){
				return;
			}
			var attrs = new Array();
			attrs.push("cardInventoryId");
			var vals = new Array();
			vals.push($(obj).next().val());
			formPost.submitAjaxForm("queryInventoryCards.do",attrs,vals,this.showCards);
		},
		showCards:function(data){
			var invId = data.invId;
			var $aInv = $("._invId").filter(function(index) {
				if($(this).val()==invId){
					return this;
				}
			});
			var $aTbody = $aInv.closest("table").find("tbody");
			$aTbody.find("ul").html("");
			
			var $hidLi = $("._hidLi:last").clone(true);
			var $agentInfo = $("._agentInfo:last").clone(true);
			$agentInfo.show();
			$hidLi.show();
			$hidLi.hover(
				function(){
					$(this).css('background-color','#D2E9FF');	
				},function(){
				   $(this).css('background-color','#ffffff');
				}
			);
			for(var i=0;i<data.cards.length;i++){
				var card = data.cards[i];
				
				$hidLi.find("._cardName").html(card.opencardName);
				$hidLi.find("._bankName").html(card.bankName);
				if(card.bankcardStatus==10002){
					$hidLi.find("._status").html("正常");
				}else{
					$hidLi.find("._status").html("无效");
				}
				
				$hidLi.find("._cardNo").html("卡号："+card.cardNo);
				$hidLi.find("._agentList").html("");
				$hidLi.find("._agentList").append($agentInfo.clone(true));
				//绑定了该卡的总代
				if(card.bindCardUsers!=null&&card.bindCardUsers!=undefined){
					for(var j=0;j<card.bindCardUsers.length;j++){
						var $agent =$("._agent:last").clone(true);
						$agent.show();
						$agent.html(card.bindCardUsers[j].customerName+"<input type='hidden' value='"+
									card.bindCardUsers[j].id+"' />&nbsp;&nbsp;");
						$hidLi.find("._agentList").append($agent.clone(true));
					}
					 
					$hidLi.find("._agent").on('click',function(){
						cardInvConfig.showAgentUser($(this).find("input").val());
					});
				}
				$hidLi.find("._queryCardInfo").html("查看<input type='hidden' value='"+card.id+"' />");
				$hidLi.find("._queryCardInfo").on('click',function(){
					cardInvConfig.showCardInfo($(this).find("input").val());
				});
				
				$aTbody.find("._level"+card.cardLevel).append($hidLi.clone(true));
			};
		},
		showAgentUser : function(id){
			var attrs = new Array();
			attrs.push("id");
			var vals = new Array();
			vals.push(id);
			var action = "user/showUserInfo/"+id+".do";
			formPost.submitForm(action,attrs,vals);
		},
		showCardInfo : function(id){
			var attrs = new Array();
			attrs.push("id");
			attrs.push("status");
			var vals = new Array();
			vals.push(id);
			vals.push(10002);
			var action = "showCardInfo.do";
			formPost.submitForm(action,attrs,vals);
		}
	};
}();