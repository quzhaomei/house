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
.help-block{font-weight:bold;font-size:15px;}
#orderZoneIdsParent,#readyDateParent{visibility: hidden;color:#EC1515;}
.textarea{resize:none;width:500px;height:80px;background-color:#eee;border:1px solid red;}
.basic-info{background-color:#ddd;padding:5px 10px 10px 10px;}
.basic-title{font-weight:bold;margin:0px 5px 0 0px;}
.tips{color:red;text-align:center;}
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
				 	<li><a href="index.html">我的订单 </a></li>
				 	<li><i class="icon-angle-right"></i><a href="#">订单详情</a></li>
				</ul>

				<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon edit"></i><span class="break"></span>
						订单
						</h2>
						<div class="box-icon">
							<a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
							<a href="#" class="btn-close"><i class="halflings-icon remove"></i></a>
						</div>
					</div>
					<c:if test="${order.status==0 }">
						<p class="tips">接单后才会显示隐藏的信息！</p>
					</c:if>
					<div class="box-content">
						<form class="form-horizontal" action="#" method="post">
							<fieldset>
							
							
							
							<legend class="help-block"> 订单信息</legend>
							<p class="basic-info">
								<span class="basic-title">
							订单号 : </span> ${order.orderId }
							&nbsp;
							<span class="basic-title">
							发单时间 : </span> <fmt:formatDate value="${order.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/> 
							&nbsp;
							<span class="basic-title">
							订单分配人员 : </span> ${order.createUser.nickname }
							&nbsp;
							<span class="basic-title">
							客户电话 : </span> 
							<c:if test="${order.status==0 }">
							<c:choose>
						  			<c:when test="${fn:length(require.userphone)>3&&fn:length(require.userphone)<11 }">
						  				${fn:substring(require.userphone,0,3) }********
						  			</c:when>
						  			<c:when test="${fn:length(require.userphone)>=11}">
						  			${fn:substring(require.userphone,0,3) }****${fn:substring(require.userphone,7,11) }
						  			</c:when>
						  		</c:choose>
						  		
							</c:if>
							<c:if test="${order.status==1 }">
								${require.userphone }
							</c:if>
							&nbsp;
							<c:if test="${order.status==1 }"><!-- 已接单才显示 -->
							<br/>
							<span class="basic-title">量房结果  : </span> 
							<c:choose>
								<c:when test="${empty order.isSuccess }">
									<span style="color:#888;">未确认</span>
								</c:when>
								<c:when test="${order.isSuccess==0}">
									<span style="color:red;">失败</span>
									</c:when>
								<c:when test="${order.isSuccess==1}">
									<span style="color:green;">成功</span>
								</c:when>
							</c:choose>
							</c:if>
							<c:if test="${not empty order.remarks }">
							<br/>
							<span class="basic-title">商家备注  : </span> 
							<span style="color:red;">${order.remarks }</span>
							</c:if>
							</p>
						
							
							
							<legend class="help-block"> 订单操作日志</legend>
							${order.operatorLog }
							
							<legend class="help-block"> 需求基本信息</legend>
							<p class="basic-info">
							<span class="basic-title">
							用户ID : </span> ${require.userId }
							&nbsp;
							<span class="basic-title">
							用户姓名 : </span> ${require.username }
							</p>
							<table class="table">
							<tr>
							<td width=11% class="basic-title">需求ID</td><td>${require.requiredId }</td>
							<td  width=9% class="basic-title">发布时间</td><td><fmt:formatDate value="${require.createDate }" pattern="yyyy-MM-dd HH:mm"/> </td>
							<td class="basic-title">是否新房：</td><td>
							<c:choose>
								<c:when test="${require.isNew==1 }">新房</c:when>
								<c:otherwise>旧房</c:otherwise>
							</c:choose>
							</td>
							<td class="basic-title">需求状态</td><td>
							<span class="label">
							<c:choose>
							
								<c:when test="${require.status==6  }">
									待分单
								</c:when>
								<c:when test="${require.status==7  }">
									待派单
								</c:when>
								<c:when test="${require.status==8  }">
									已派单
								</c:when>
								<c:when test="${require.status==40  }">
									已退单
								</c:when>
								<c:when test="${require.status==41 }">
									待跟进库
								</c:when>
							</c:choose>
							</span>
							</td>
							</tr>
							<tr>
							<td class="basic-title">房型面积</td><td>${require.houseType.name }</td>
							<td class="basic-title">房型描述</td><td>${require.houseDes }</td>
							
							<td class="basic-title">交房情况</td><td>
							<c:choose>
								<c:when test="${require.isReady==1 }">已交房</c:when>
								<c:otherwise>待交房</c:otherwise>
							</c:choose>
							</td>
							<td class="basic-title">交房时间</td><td>
							<c:if test="${empty require.readyDate }">已交房</c:if>
							<fmt:formatDate value="${require.readyDate }" pattern="yyyy-MM-dd"/>
							</td>
							</tr>
							<tr>
							<td class="basic-title">区域</td><td>${require.zone.name }</td>
							<td class="basic-title">楼盘信息：</td><td>${require.houseInfo }</td>
							<td class="basic-title">预算：</td>
							<td> 
							<c:if test="${order.status==0 }">
									********
								</c:if>
								<c:if test="${order.status==1 }">
									${require.budget }
								</c:if>
							</td>
							<td class="basic-title">房屋状态：</td><td>${require.houseStatus }</td>
							<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
							<td class="basic-title">房屋地址：</td>
							<td colspan="9">
								<c:if test="${order.status==0 }">
									********
								</c:if>
								<c:if test="${order.status==1 }">
									${require.houseLocation }
								</c:if>
							</td>
							</tr><tr>
							<td class="basic-title">量房时间约定：</td><td colspan="9">${require.designTime }</td>
							</tr><tr>
							<td class="basic-title">电话时间约定</td><td colspan="9">${require.phoneTime }</td>
							</tr>
							<tr>
							<td class="basic-title">装修方式</td><td colspan="9">${require.designType }</td>
							</tr>
							<tr>
							<td class="basic-title">装修风格</td><td colspan="9">${require.designStyle }</td>
							</tr>
							</table>
								<c:if test="${not empty require.serviceTips}">
									<legend class="help-block"> 商家提示</legend>
									<p class="basic-info">${require.serviceTips }</p>
								</c:if>
							</fieldset>
							
						</form>
					</div>
				</div><!--/span-->

			</div><!--/row-->
    

	</div><!--/.fluid-container-->			
			
			
			
			
		</div>
	</div>
	
	
	<div class="clearfix"></div>
	<c:import url="public/p-footer.jsp"></c:import>
	<c:import url="public/p-javascript.jsp"></c:import>
	<script type="text/javascript" src="../js/ajaxfileupload.js" charset="utf-8"></script>
	<script type="text/javascript">
	$(function(){
		
	});
	</script>
</body>
</html>
