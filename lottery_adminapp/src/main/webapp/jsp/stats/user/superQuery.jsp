<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>超级查询</title>
<%@ include file='../../include/asset_css.jsp'%>
<%@ include file='../../include/asset_js.jsp'%>
<script src="${contextPath }/jsp/js/statistic/superquery.js"></script>
</head>
<body>
	<%@ include file="../../include/top.jsp"%>
	<div class="container ">
		<div class="row">
			<%@ include file="../../include/stats_menu_user.jsp"%>
			<div class="col-sm-10">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="form-horizontal">
							<div class="form-group">
								<div class="col-sm-3">
									<div class="row">
										<div class="col-sm-6">
											<input type="text" class="form-control _param" placeholder="用户昵称" name="customerAlias"/>
										</div>
										<div class="col-sm-6">
											<input type="text" class="form-control _param" placeholder="用户账号" name="customerName"/>
										</div>
									</div>
								</div>
								<div class="col-sm-3">
									<input type="text" class="form-control _param _startTime"  placeholder="起始时间" name="strartTime"/>
								</div>
								<div class="col-sm-3">
									<input type="text" class="form-control _param _endTime" placeholder="结束时间" name="endTime"/>
								</div>
								<div class="col-sm-3">
									<input type="text" class="form-control _param" placeholder="配额剩余量  0-100" name="quotaCount">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-3">
									<select class="form-control _param" id="types" name="customerLevel">
										<option value="-1">所有类型</option>
										<option value="0">会员用户</option>
										<option value="1">一级代理</option>
										<option value="2">二级代理</option>
										<option value="3">三级代理</option>
										<option value="4">四级代理</option>
										<option value="5">五级代理</option>
									</select>
								</div>
								<div class="col-sm-3">
									<input type="text" class="form-control _param" name="quotaStr" placeholder="返点区间  0.056-0.057" name="quotaStr"/>
								</div>
								<div class="col-sm-3">
									<input type="text" class="form-control _param" name="betMoney" placeholder="投注量区间  100000-200000">
								</div>
								<div class="col-sm-3">
									<input type="text" class="form-control _param" name="cashMoney" placeholder="账户余额区间  100-200">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-3">
									<label class="">  <input class="_param" type="checkbox" name="isUserCard"  value="1" />
										绑卡
									</label>
								    <label class="">  <input class="_param" type="checkbox" name="isBindCard"  value="1" />
								                     分配卡
									</label>
								</div>
								<div class="col-sm-3">
									<label class="">  
										<input type="checkbox" class="_param" name="isBetMoney" value="1"  data-toggle="tooltip" data-placement="top" title="有过投注记录" />
										投注
									</label> 
									<label class="">  
										<input type="checkbox" class="_param" name="isWithdrawals" value="1"  data-toggle="tooltip" data-placement="top" title="有过充值记录" />
										提款
									</label> 
									<label class=" ">  
									<input type="checkbox" class="_param" name="isRecharge" value="1"  data-toggle="tooltip" data-placement="top" title="有过充值记录" />
										充值
									</label>
								</div>
								<div class="col-sm-3">
									<label class="">  
										<input type="checkbox" class="_param" value="1" name="isDesc" checked="checked" />倒序结果
									</label> 
									<label class="">  
										<input type="checkbox" class="_param"  value="1" name="noLike" name="accountDone" />精确查询
									</label>
								</div>
								<div class="col-sm-3 ">
									<!-- Single button -->
									<div class="btn-group">
									  <button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown">
									   	立即查询 <span class="caret"></span>
									  </button>
									  <ul class="dropdown-menu" role="menu">
									    <li><a href="javascript:;" id="secBtn">查询数据</a></li>
									    <li><a href="javascript:;" id="downLoadBtn">查询数据并下载报表</a></li>
									  </ul>
									</div>
								</div>
							</div>
							<p class="split"></p>
							共查到：<span class="text-danger _total">0 </span>人&nbsp; 
							投注总量：<span class="text-danger _total _money">0 </span>&nbsp; 
							提款总量：<span class="text-danger _total _money">0</span>&nbsp; 
							充值总量：<span class="text-danger _total _money">0</span>&nbsp; 
							余额总量 ：<span class="text-danger _total _money">0</span>&nbsp; 
							配额总量：<span class="text-danger _total ">0</span>
						</div>
					</div>
				</div>

				<table
					class="table table-condensed table-striped table-hover table-responsive text-center">
					<thead>
						<tr>
							<td>#</td>
							<td>注册时间</td>
							<td>昵称</td>
							<td>账号</td>
							<td>父级</td>
							<td>类型</td>
							<td><span data-toggle="tooltip" data-placement="top"
								title="已开户的下级用户与账户剩余的配额总数">下级与配额</span></td>
							<td>账户完善</td>
							<td>投注量</td>
							<td>充值量</td>
							<td>提款量</td>
							<td>账户余额</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody class="f12 _tbody">
					</tbody>
					<tr id="ctr" style="display: none;">
							<td>1111</td>
							<td class="text-muted">2014-10-31 15:42:19</td>
							<td>张三</td>
							<td>zhangsan</td>
							<td><a href="#">lisi123</a></td>
							<td>一级代理[6.2%]</td>
							<td>100人/100个</td>
							<td><span class="text-success">绑卡</span>&nbsp;<span
								class="text-success">分配卡</span></td>
							<td class="text-danger">1000.00</td>
							<td class="text-danger">10000.00</td>
							<td class="text-danger">10000.00</td>
							<td class="text-danger">10000.00</td>
							<td>
								<div class="btn-group">
									<button class="btn btn-link btn-xs dropdown-toggle"
										type="button" data-toggle="dropdown">
										查询用户 <span class="caret"></span>
									</button>
									<ul class="dropdown-menu text-left " role="menu">
										<li><a href="#">用户详情</a></li>
										<li><a href="javascript:;">投注查询</a></li>
										<li><a href="javascript:;">订单查询</a></li>
										<li><a href="javascript:;">个人盈亏</a></li>
									</ul>
								</div>
							</td>
						</tr>
				</table>
				<nav>
				<ul class="pager"  id="pageInfo">
				</ul>
				</nav>
				<div class="panel panel-default">
					<div class="panel-heading">参数描述</div>
					<div class="panel-body f12">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-sm-2 ">账户完善:</label>
								<div class="form-control-static col-sm-10 ">
									<span class="text-danger">绑卡</span>与<span class="text-danger">分配卡</span>勾选后，则只查询已绑定或分配银行卡的用户。未选择状态下则全部查询。<span
										class="text-danger">查询当前用户状态，不受时间限制</span>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2 ">活跃度:</label>
								<div class="form-control-static col-sm-10 ">
									在指定的时间内查询进行过<span class="text-danger">投注</span>、<span
										class="text-danger">提款</span>、<span class="text-danger">充值</span>等行为操作的用户。
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2 ">精确查询:</label>
								<div class="form-control-static col-sm-10 ">
									根据输入的<span class="text-danger">用户名</span>或<span
										class="text-danger">昵称</span>进行查询比对，默认使用模糊查询。
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2 ">配额:</label>
								<div class="form-control-static col-sm-10 ">
									表示该用户所拥有未使用的配额集合的总量。</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>