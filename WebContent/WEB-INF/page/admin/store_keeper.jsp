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
								<i class="halflings-icon user"></i><span class="break"></span>店铺管理
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
							<td width="10%;">地区</td>
							<td width="25%;">
							<select rel="chosen" name="zoneId">
								<option value="-1">所有</option>
								<c:forEach items="${zones }" var="temp">
									<option value="${temp.zoneId }"
									 ${param.zoneId==temp.zoneId?"selected='selected'":"" }>${temp.name }</option>
								</c:forEach>
							</select>
							 </td>
							<td width="10%;">状态</td>
							<td width="40%;">
								<select rel="chosen" name="status">
								<option value="-1">全部</option>
								<option value="0" ${param.status==0?"selected='selected'":"" }>暂停</option>
								<option value="1" ${param.status==1?"selected='selected'":"" }>接单</option>
							</select>
							</td>
						</tr>
						<tr>
							<td>店铺名称</td><td><input type="text" value="${param.storeName }" name="storeName" maxlength="20" placeholder="请输入店铺名称"/></td>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td>开店时间</td><td>
							<input type="text" name="startDate" value='${param.startDate }' class="datepicker span4" />
									-
							<input type="text" name="endDate" value="${param.endDate }" class="datepicker span4"/></td>
							<td>&nbsp;</td><td>
							<button type="button" class="btn btn-small btn-search">
							<i class="halflings-icon search white"></i> 查询</button>
							</td>
						</tr>
						</table>
							  
						</form>
						<table class="table table-striped table-bordered bootstrap-datatable">
						  <thead>
							  <tr>
								  <th width=5%>序列</th>
								  <th width=10%>地区</th>
								  <th width=10%>店铺名称</th>
								  <th width=15%>开店时间 </th>
								  <th width=15%>更新时间</th>
								  <th width=8%>店铺状态</th>
								  <th width=10%>负责人</th>
								   <th width=10%>余额</th>
								  <th width=20%>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  <c:forEach items="${pageResult.param }" var="temp" varStatus="status">
						  	<tr>
								<td>${status.count+(pageResult.pageIndex-1)*(pageResult.pageSize) }</td>
								<td>${temp.zone.name }</td>
								<td class="center">${fn:escapeXml(temp.storeName) }</td>
								<td class="center">
									<fmt:formatDate value="${temp.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td class="center">
									<fmt:formatDate value="${temp.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td class="center">
									<c:choose>
										<c:when test="${temp.status==0 }">
											<span class="label">暂停</span>
										</c:when>
										<c:when test="${temp.status==1 }">
											<span class="label label-success">接单中</span>
										</c:when>
									</c:choose>
								</td>
								<td>
									${temp.keeper.nickname }
								</td>
								<td>
									￥ ${temp.balance }
								</td>
								
								<td class="center">
								<ad:power uri="../keeper/list.html">
									<a class="btn btn-info btn-mini " href="list.html?storeId=${temp.storeId }">
										<i class="halflings-icon white edit "></i>  明细
									</a>
									</ad:power>
									
								<ad:power uri="../keeper/balance.html">	
									<a class="btn btn-info btn-mini " href="balance.html?storeId=${temp.storeId }">
										<i class="halflings-icon white edit "></i>  消费记录
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
		
		
	});
	</script>
</body>
</html>
