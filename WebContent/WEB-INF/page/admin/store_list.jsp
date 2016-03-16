<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="ad" uri="/adtag"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>系统后台管理</title>
<c:import url="public/p-css.jsp"></c:import>
<style type="text/css">
.logo {
	width: 120px;
	height: 120px;
}

td.title {
	font-weight: bold;
	background-color: #ddd;
	width: 10%;
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

			<!-- start: Content -->
			<div id="content" class="span10">
				<ul class="breadcrumb">
					<li><i class="icon-home"></i> <a href="index.html">首页</a></li>
					<li><i class="icon-angle-right"></i><a href="index.html">店铺列表
					</a></li>
					<li><i class="icon-angle-right"></i><a href="#">店铺详细</a></li>
				</ul>

				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header" data-original-title>
							<h2>
								<i class="halflings-icon edit"></i><span class="break"></span>店铺详细
							</h2>
							<div class="box-icon">
								<a href="#" class="btn-minimize"><i
									class="halflings-icon chevron-up"></i></a> <a href="#"
									class="btn-close"><i class="halflings-icon remove"></i></a>
							</div>
						</div>
						<div class="box-content">
							<fieldset>
								


								<div class="control-group" style="height:150px;">
									<div class="span2 text-center" style="margin:5px 0;">
										<img src="${store.logo }" class="logo" />
									</div>
									<div class="span10">
										<div class="control-group">
										<b>地区：</b> ${store.zone.name} &nbsp; 
										<b>店铺名称：</b> ${store.storeName }
										</div>
										<div class="control-group">
											<b>接单区域</b> &nbsp;
											<c:forEach items="${store.orderZones }" var="type">
												<span class="label label-success" style="margin: 0 5px;">
													${type.name } </span>
											</c:forEach>

										</div>
									

									<div class="control-group">
										<b>接单房型</b> &nbsp;
										<c:forEach items="${store.houseTypes }" var="type">
											<span class="label label-success"> ${type.name } </span>
										</c:forEach>
									</div>
									</div>

								</div>




								<legend style="">基本信息</legend>
								<table class="table table-border" style="width: 95%;">
									<tr>
										<td class="title">接单开关</td>
										<td>${store.status==0?"关":"开" }</td>
										<td class="title">店铺电话</td>
										<td>${store.storePhone }</td>
										<td class="title">店铺负责人</td>
										<td>${store.keeper.nickname }</td>
									</tr>
									<tr>
										<td class="title">每月订单控量</td>
										<td>${store.size }</td>
										<td class="title">对接电话</td>
										<td>${store.callPhone }</td>
										<td class="title">短信电话</td>
										<td>${store.msgPhone }</td>
									</tr>
									<tr>
										<td class="title">公司名称</td>
										<td>${store.companyName }</td>
										<td class="title">法人姓名</td>
										<td>${store.ruleUserName }</td>
										<td class="title">法人电话</td>
										<td>${store.ruleUserPhone }</td>
									</tr>
									<tr>
										<td class="title">店铺详细地址</td>
										<td colspan="5">${store.storeAddress }</td>
									</tr>
									<tr>
										<td class="title">店铺备注</td>
										<td colspan="5">${store.remarks }</td>
									</tr>
								</table>

								<form action="list.html#myform" method="post" id="myform"
									rel="admin-form">
									<input name="pageIndex" id="pageIndex" type="hidden"
										value="${pageResult.pageIndex}" /> <input name="pageSize"
										type="hidden" value="${pageResult.pageSize}" /> <input
										name="storeId" type="hidden" value="${store.storeId }"
										id="storeId" />
								</form>
								<c:if test="${!empty pageResult.param }">
								<legend>近期订单</legend>
								<table class="table table-border" style="width: 95%;">
									<tr>
										<th width=5%>序列</th>
										<th width=5%>类型</th>
										<th width=8%>状态</th>
										<th width=10%>创建时间</th>
										<th width=10%>创建人</th>
										<th width=10%>区域</th>
										<th width=10%>房型</th>
										<th width=10%>费用</th>
									</tr>
									<c:forEach items="${pageResult.param }" var="temp"
										varStatus="status">
										<tr>
											<td>${status.count+(pageResult.pageIndex-1)*(pageResult.pageSize) }</td>
											<td><c:choose>
													<c:when test="${temp.type==1 }">
														<span class="label label-success">消费</span>
													</c:when>
													<c:when test="${temp.type==2 }">
														<span class="label label-important">赠送</span>
													</c:when>
												</c:choose></td>
											<td class="center"><c:choose>
													<c:when test="${temp.status==0 }">
														<span class="label label-success">未查看</span>
													</c:when>
													<c:when test="${temp.status==1 }">
														<span class="label">已接受</span>
													</c:when>
												</c:choose></td>
											<td class="center"><fmt:formatDate
													value="${temp.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td>${temp.createUser.nickname }</td>
											<td>${temp.zonename }</td>
											<td>${temp.typename }</td>
											<td>${temp.price}</td>
										</tr>
									</c:forEach>
								</table>
								<!--分页控制  -->
								<ad:page pageIndex="${pageResult.pageIndex }"
									pageSize="${pageResult.pageSize}"
									totalPage="${pageResult.totalPage}"></ad:page>
							</c:if>
							</fieldset>
						</div>

					</div>
				</div>
				<!--/span-->

			</div>
			<!--/row-->


		</div>
		<!--/.fluid-container-->




	</div>


	<div class="clearfix"></div>
	<c:import url="public/p-footer.jsp"></c:import>
	<c:import url="public/p-javascript.jsp"></c:import>
	<script type="text/javascript">
		$(function() {

			$(".btn-cancel").on("click", function() {
				window.location.href = "index.html";
			});

		});
	</script>
</body>
</html>
