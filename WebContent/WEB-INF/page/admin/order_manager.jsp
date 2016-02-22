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
					<li><i class="icon-home"></i> <a href="index.html">首页</a></li>
				<!-- 	<li><a href="#">系统设置<i class="icon-angle-right"></i> </a> -->
				</ul>

				<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2>
								<i class="halflings-icon user"></i><span class="break"></span>订单管理
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
						<table class="table">
						<tr>
							
							<td width="8%;">状态</td>
							<td width="10%">
								<select rel="chosen" name="status">
								<option value="-1">全部</option>
								<option value="0" ${param.status==0?"selected='selected'":"" }>未查看</option>
								<option value="1" ${param.status==1?"selected='selected'":"" }>已接受</option>
							</select>
							</td>
							<td width="8%">订单时间</td>
							<td width="35%">
							<input type="text" name="startDate" value='${param.startDate }' class="datepicker span5" />
									-
							<input type="text" name="endDate" value="${ param.endDate}" class="datepicker span5"/></td>
							<td>&nbsp;
							<ad:power uri="../orderManager/loadAll.html">
							<a href="#" class="btn btn-mini red loadAll">下载数据</a>
							</ad:power>
							</td>
						</tr>
						<tr>
							<td>客服名称</td>
							<td><input type="text" value="${param.username }" name="username" maxlength="20" placeholder="请输入客服名称"/></td>
							<td>店铺名称</td>
							<td><input type="text" value="${param.storeName }" name="storeName" maxlength="20" placeholder="请输入店铺名称"/></td>
							<td>
							<button type="button" class="btn btn-small btn-search">
							<i class="halflings-icon search white"></i> 查询</button>
							</td>
						</tr>
						</table>
							  
						</form>
						<table class="table table-striped table-bordered bootstrap-datatable">
						  <thead>
							  <tr>
								  <th width=7%>订单号</th>
								  <th width=5%>类型</th>
								  <th width=15%>店铺 </th>
								  <th width=8%>状态</th>
								  <th width=10%>创建时间</th>
								  <th width=10%>客服</th>
								  <th width=10%>需求ID</th>
								  <th width=10%>房型面积</th>
								  <th width=10%>费用</th>
								  <th width=20%>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  <c:if test="${empty pageResult.param}">
						  	<tr><td colspan="8" style="text-align:center;color:red;font-size:15px;">暂无数据</td></tr>
						  </c:if>
						  <c:forEach items="${pageResult.param }" var="temp" varStatus="status">
						  	<tr>
								<td>${temp.orderId }</td>
								<td>
									<c:choose>
										<c:when test="${temp.type==1 }">
											<span class="label label-success">消费</span>
										</c:when>
										<c:when test="${temp.type==2 }">
											<span class="label label-important">赠送</span>
										</c:when>
									</c:choose>
								</td>
								<td class="center">${fn:escapeXml(temp.store.storeName) }</td>
								<td class="center">
									<c:choose>
										<c:when test="${temp.status==0 }">
											<span class="label label-success">未查看</span>
										</c:when>
										<c:when test="${temp.status==1 }">
											<span class="label">已接受</span>
										</c:when>
									</c:choose>
								</td>
								<td class="center">
									<fmt:formatDate value="${temp.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>${temp.createUser.nickname }</td>
								<td>${temp.require.requiredId }</td>
								<td>${temp.typename }</td>
								<td>￥${temp.type==2?"1":temp.value }</td>
								<td class="center">
								<ad:power uri="../orderManager/list.html">
									<a class="btn  btn-mini"  href="list.html?orderId=${temp.orderId }">
										<i class="halflings-icon white edit " ></i>  详细
									</a>
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
		
		$(".btn-search").on("click",function(){
			$("#pageIndex").val(1);
			$(this).parents("form").submit();
		});
		//接单
		$(".loadAll").on("click",function(){
			$("#myform").attr("action","loadAll.html");
			$("#myform").submit();
			$("#myform").attr("action","index.html");
		});
	});
	</script>
</body>
</html>
