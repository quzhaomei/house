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
				 	<li><a href="index.html">预约需求列表 </a></li>
				 	<li><i class="icon-angle-right"></i><a href="#">预约需求详情</a></li>
				</ul>

				<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon edit"></i><span class="break"></span>预约需求详情</h2>
						<div class="box-icon">
							<a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
							<a href="#" class="btn-close"><i class="halflings-icon remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<form class="form-horizontal" action="#" method="post">
							<fieldset>
							<legend class="help-block"> 基本信息</legend>
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
							<td  width=9% class="basic-title">添加时间</td><td><fmt:formatDate value="${require.createDate }" pattern="yyyy-MM-dd HH:mm"/> </td>
							<td width=9%  class="basic-title">创建人员：</td><td>${require.createUser.nickname }</td>
							<td class="basic-title">业务人员：</td><td>
							<c:choose>
								<c:when test="${empty require.serviceUser.nickname }">
									暂未分配
								</c:when>
								<c:otherwise>
								${require.serviceUser.nickname} 
								</c:otherwise>
							</c:choose>
							
							</td>
							<td class="basic-title">需求状态</td><td>
							<span class="label">
							<c:choose>
								<c:when test="${require.status==0  }">
									发起中
								</c:when>
								<c:when test="${require.status==1  }">
									短信中
								</c:when>
								<c:when test="${require.status==2  }">
									客户打开短信
								</c:when>
								<c:when test="${require.status==3  }">
									客户修改提交
								</c:when>
								<c:when test="${require.status==4  }">
									确认完毕待发布
								</c:when>
								<c:when test="${require.status==6  }">
									待分单
								</c:when>
								<c:when test="${require.status==7  }">
									待派单
								</c:when>
								<c:when test="${require.status==8  }">
									已派单
								</c:when>
							</c:choose>
							</span>
							</td>
							</tr>
							<tr>
							<td class="basic-title">房型</td><td>${require.houseType.name }</td>
							<td class="basic-title">房型描述</td><td>${require.houseDes }</td>
							<td class="basic-title">是否新房：</td><td>
							<c:choose>
								<c:when test="${require.isNew==1 }">新房</c:when>
								<c:otherwise>旧房</c:otherwise>
							</c:choose>
							</td>
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
							<td class="basic-title">预算：</td><td>${require.budget } </td>
							<td colspan="4">&nbsp;</td>
							</tr>
							<tr>
							<td class="basic-title">房屋地址：</td><td colspan="9">${require.houseLocation }</td>
							</tr><tr>
							<td class="basic-title">量房时间约定：</td><td colspan="9">${require.designTime }</td>
							</tr><tr>
							<td class="basic-title">电话时间约定</td><td colspan="9">${require.phoneTime }</td>
							</tr>
							<tr>
							<td class="basic-title">装修方式：</td><td colspan="9">${require.designType }</td>
							</tr>
							<tr>
							<td class="basic-title">装修风格：</td><td colspan="9">${require.designStyle }</td>
							</tr>
							<tr>
							<td class="basic-title">房屋状态：</td><td colspan="9">${require.houseStatus }</td>
							</tr>
							</table>
							<legend class="help-block"> 客户要求</legend>
							<p class="basic-info">${require.customerTips }</p>
							<legend class="help-block"> 回访备注</legend>
							<p class="basic-info">${require.callbackTips }</p>
							<legend class="help-block"> 商家提示</legend>
							<p class="basic-info">${require.serviceTips }</p>
							<legend class="help-block">操作日志</legend>
							<p class="basic-info">${require.operatorLog }</p>
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
