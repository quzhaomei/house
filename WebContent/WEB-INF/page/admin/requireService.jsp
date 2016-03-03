<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="ad" uri="/adtag" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>系统后台管理</title>
<c:import url="public/p-css.jsp"></c:import>
<style type="text/css">
.modal-body{max-height: 580px;}
</style>
</head>

<body>
	<!-- start: Header -->
		<c:import url="public/p-header.jsp"></c:import>
	<!-- start: Header -->

	<div class="container-fluid-full">
		<div class="row-fluid">

			<!-- start: Main Menu -->
			<c:import url="/welcome/menus.html"></c:import>
			<!-- end: Main Menu -->

			<!-- start: Content -->
			<div id="content" class="span10">
				<ul class="breadcrumb">
					<li><a href="#">需求管理 </a>
					<li><i class="icon-angle-right"></i> <a href="index.html">我的需求列表 </a></li>
				</ul>

				<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2>
								<i class="halflings-icon user"></i><span class="break"></span>我的任务
							</h2>
							<div class="box-icon">
							
									 <a href="#"
									class="btn-minimize"><i class="halflings-icon chevron-up"></i>
								</a> <a href="#" class="btn-close"><i
									class="halflings-icon remove"></i> </a>
							</div>
					</div>
					<div class="box-content">
					<form action="index.html" method="post" id="myform" rel="admin-form">
						<input name="pageIndex" id="pageIndex" type="hidden" value="${pageResult.pageIndex}"/>
						<input name="pageSize" type="hidden" value="${pageResult.pageSize}"/>
						<input name="operator" value="to_service" type="hidden"/>
						<table class="table">
						<tr>
							<td width=7%>用户名称</td><td width=18%><input class="span10" type="text" id="username" value="${param.username }" name="username" maxlength="20" placeholder="请输入用户名称"/></td>
							<td width=7% >用户Id</td><td width=18%><input class="span10" type="text" id="userId" value="${param.userId }" name="userId" maxlength="20" placeholder="请输入用户号码"/></td>
							<td width=5%>状态</td><td width=18%><select style="width:100px;" name="status" rel="chosen">
								<option value="-1" ${param.status=="-1"?"selected='selected'":"" }>全部</option>
								<option value="7" ${param.status=="7"?"selected='selected'":"" }>待派单</option>
								<option value="8" ${param.status=="8"?"selected='selected'":"" }>已派单</option>
							</select></td>
						</tr>
						<tr>
							<td>录入时间</td>
							<td>
							<input type="text" name="startDate" id="startDate"value='${param.startDate }' class="datepicker span5" />
									-
							<input type="text" name="endDate" id="endDate" value='${param.endDate }' class="datepicker span5"/></td>
							<td>分单时间</td>
							<td>
							<input type="text" name="serviceStartDate" id="serviceStartDate" value='${param.serviceStartDate }' class="datepicker span5" />
									-
							<input type="text" name="serviceEndDate" id="serviceEndDate" value='${param.serviceEndDate }' class="datepicker span5"/>
							</td>
							<td colspan="2">
							<span class="add-on">
							<button type="button" class="btn btn-small btn-search">
							<i class="halflings-icon search white"></i> 查询</button>
							</span>
							</td>
						</tr>
						</table>
						</form>
						
						<table class="table table-striped table-bordered bootstrap-datatable">
						  <thead>
							  <tr>
								  <th width=5%>序列</th>
								  <th width=15%>录入时间</th>
								  <th width=5%>用户ID </th>
								  <th width=10%>用户名称</th>
								  <th width=15%>分单时间</th>
								  <th width=8%>派单数</th>
								  <th width=8%>状态</th>
								  <th >操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  	<c:forEach items="${pageResult.param }" var="temp" varStatus="status">
						  		<tr>
						  		<td>${status.count+(pageResult.pageIndex-1)*(pageResult.pageSize) }</td>
						  		<td><fmt:formatDate value="${temp.createDate }" pattern="yyyy-MM-dd HH:mm"/> </td>
						  		<td>${temp.userId }</td>
						  		<td>${temp.username }</td>
						  		<td><fmt:formatDate value="${temp.serviceDate }" pattern="yyyy-MM-dd HH:mm"/></td>
						  		<td>${temp.orderCount }</td>
						  		<td>
						  			<c:choose>
						  			<c:when test="${temp.status==7}">
						  				<span class="label">待派单</span>
						  				</c:when>
						  				<c:when test="${temp.status==8}">
						  				<span class="label label-success">已派单</span>
						  				</c:when>
						  			</c:choose>
						  		</td>
						  		<td>
						  		<ad:power uri="../requireService/list.html">
						  		<a class="btn btn-mini" href="list.html?requiredId=${temp.requiredId }">详情</a>
						  		</ad:power>
						  			<ad:power uri="../requireService/toStore.html">
							  			<a class="btn btn-mini green" href="toStore.html?operator=to_store&requiredId=${temp.requiredId }">
							  			去派单</a>
							  			</ad:power>
							  		<ad:power uri="../requireService/message.html">	
							  			<a class="btn btn-mini yellow" href="message.html?operator=to_message&requiredId=${temp.requiredId }">
							  			备注-更新</a>
							  			</ad:power>
							  			<ad:power uri="../requireService/call.html">	
							  			<a class="btn btn-mini yellow call-customer" href="#"
							  			 data-href="call.html?operator=to_store&requiredId=${temp.requiredId }">
							  			拨通电话</a>
							  			</ad:power>
							  			
							  			<ad:power uri="../requireService/getOrder.html">	
							  			<c:if test="${temp.status==8 }">
							  			<a class="btn btn-mini yellow" href="getOrder.html?requiredId=${temp.requiredId }">
							  			查看订单</a>
							  			</c:if>
							  			</ad:power>
							  			
						  		 </td>
						  		</tr>
						  	</c:forEach>
						  </tbody>
					  </table>   
					  
					  <!--分页控制  -->
								<ad:page pageIndex="${pageResult.pageIndex }"
									 pageSize="${pageResult.pageSize}" 
									 totalPage="${pageResult.totalPage}"></ad:page>
									 
					           
					</div>
				</div><!--/span-->
			
			</div><!--/row-->
			</div>
			
		</div>
	</div>
	
	<div class="clearfix"></div>
	<c:import url="public/p-footer.jsp"></c:import>
	<c:import url="public/p-javascript.jsp"></c:import>
	<script type="text/javascript">
	var bak;
	$(function(){
		//发送短信
		$(".sendMsg").click(function(){
			var requiredId=$(this).attr("requiredId");
			var param={};
			param.requiredId=requiredId;
			var tip="确定向用户发送确认短信吗？";
			layer.confirm(tip, {
				title:"状态切换",
			    btn: ["确定","返回"], //按钮
			    shade: false //不显示遮罩
			}, function(){
				$.post("sendMsg.html",param,function(data){
				if(data.status==1){
					layer.msg(data.message,{ icon: 1,time: 1000},function(){
						window.location.reload();
					});
				}else{
					layer.msg(data.message);
				}
			},"json");
			
			}, function(){
				
			});
		});
		
		$(".btn-search").on("click",function(){
			$("#pageIndex").val(1);
			$(this).parents("form").submit();
		});
		
		$(".call-customer").on("click",function(){
			var url=$(this).attr("data-href");
			$.post(url,function(json){
				layer.msg(json.message);
			},"json")
		})
		
	});
	</script>
</body>
</html>
