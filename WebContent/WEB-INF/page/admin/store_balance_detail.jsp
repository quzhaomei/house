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
.info-title{background-color:#eee;}
.info-title-high{color:red;font-weight:bold;}
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
					<li><i class="icon-angle-right"></i> <a href="#">消费记录</a> 
				</ul>

				<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2>
								<i class="halflings-icon user"></i><span class="break"></span>消费记录
							</h2>
							<div class="box-icon">
							
									 <a href="#"
									class="btn-minimize"><i class="halflings-icon chevron-up"></i>
								</a> <a href="#" class="btn-close"><i
									class="halflings-icon remove"></i> </a>
							</div>
					</div>
					<div class="box-content">
					<form action="update.html" method="post" id="myform" rel="admin-form">
						<input name="pageIndex" id="pageIndex" type="hidden" value="${pageResult.pageIndex}"/>
						<input name="pageSize" type="hidden" value="${pageResult.pageSize}"/>
						<input name="storeId" id="storeId" type="hidden" value="${store.storeId}"/>
						</form>
						<fieldset>
						<legend style="color:red;">账户余额：￥ ${store.balance }</legend>
						</fieldset>
						<h2>账户历史记录</h2>
						<table class="table table-striped table-bordered bootstrap-datatable">
						  <thead>
							  <tr>
								  <th width=20%>时间</th>
								  <th width=10%>类型</th>
								  <th width=10%>金额</th>
								  <th width=15%>订单号 </th>
								  <th width=10%>操作人</th>
								  <th width=35%>操作人备注</th>
							  </tr>
						  </thead>   
						  <tbody>
							  <c:forEach items="${pageResult.param}" var="temp">
							  <tr>
							  <td><fmt:formatDate value="${temp.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
							  <td><c:choose>
							  	<c:when test="${temp.type==0 }">
							  		<span class="label label-success">充值</span>
							  	</c:when>
							  	<c:when test="${temp.type==1 }">
							  		<span class="label label-danger">消费</span>
							  	</c:when>
							  </c:choose></td>
							  <td>￥ ${temp.value }</td>
							  <td>${temp.orderId }</td>
							   <td>${temp.createUser.nickname }</td>
							     <td>${temp.message }</td>
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
	$(function(){
		
	});
	</script>
</body>
</html>
