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
<title>后台管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="public/p-css.jsp"></c:import>
<!-- end: Favicon -->
<style type="text/css">
.modal-body{max-height: 580px;}
label.error{
	display: inline-block;
	*display: inline;
	*zoom:1;
	margin-left:15px;
	color:#2D89EF;
	background: #e2e2e2;
	padding:5px 10px;
	}
label.error:before{
  content:"×";
  color:#2D89EF;
}
.errorDiv{
display: inline-block;
	*display: inline;
	*zoom:1;
}
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

			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>
						You need to have <a href="http://en.wikipedia.org/wiki/JavaScript"
							target="_blank">JavaScript</a> enabled to use this site.
					</p>
				</div>
			</noscript>

			<!-- start: Content -->
			<div id="content" class="span10">
				<ul class="breadcrumb">
					<li><i class="icon-home"></i> <a href="index.html">首页</a> <i
						class="icon-angle-right"></i></li>
				<!-- 	<li><a href="#">系统设置<i class="icon-angle-right"></i> </a> -->
					<li><a href="#">量房申请</a>
					</li>
				</ul>

				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header" data-original-title>
							<h2>
								<i class="halflings-icon user"></i><span class="break"></span>量房申请
							</h2>
							<div class="box-icon">
									<a href="#"
									class="btn-minimize"><i class="halflings-icon chevron-up"></i>
								</a> <a href="#" class="btn-close"><i
									class="halflings-icon remove"></i> </a>
							</div>
						</div>
						
						<!-- 数据展示 -->
						<div class="box-content">
						<!-- table框架 -->
						<form action="index.html" method="post" id="myform" rel="admin-form">
						<input name="pageIndex" id="pageIndex" type="hidden" value="${pageResult.pageIndex}"/>
						<input name="pageSize" type="hidden" value="${pageResult.pageSize}"/>
						</form>
							<table class="table table-striped table-bordered">
								<thead>
									<tr>
										<th >序列</th>
										<th >姓名</th>
										<th >电话号码</th>
										<th style="width: 70px;">所在区域</th>
										<th>报名时间</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${pageResult.param }" var="apply" varStatus="status">
									<tr id="apply${apply.applyId }">
										<td>${pageResult.pageSize*(pageResult.pageIndex-1)+status.count}</td>
										<td>${fn:escapeXml(apply.username) }</td>
										<td>${apply.phone }</td>
										<td>${apply.location }</td>
										<td><fmt:formatDate value="${apply.createDate }"
										 pattern="yyyy-MM-dd HH:mm"/> </td>
									</tr>
								</c:forEach>
								</tbody>
							</table>

							<!--分页控制  -->
								<ad:page pageIndex="${pageResult.pageIndex }"
									 pageSize="${pageResult.pageSize}" 
									 totalPage="${pageResult.totalPage}"></ad:page>
								
						</div>
						<!-- 数据展示结束 -->
						
					</div>
					<!--/span-->
				</div>
				
				
			</div>
			
		</div>
	</div>
	
	<div class="clearfix"></div>
	
	<c:import url="public/p-footer.jsp"></c:import>
	
	<!-- start: JavaScript-->
	<c:import url="public/p-javascript.jsp"></c:import>>
	<!-- end: JavaScript-->
	<script type="text/javascript">
		$(function(){
		});
	</script>
</body>
</html>