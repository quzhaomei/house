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
				 	<li><i class="icon-angle-right"></i><a href="#">预约需求分单</a></li>
				</ul>

				<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon edit"></i><span class="break"></span>预约需求分单</h2>
						<div class="box-icon">
							<a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
							<a href="#" class="btn-close"><i class="halflings-icon remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						
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
							<td class="basic-title">房型面积</td><td>${require.houseType.name }</td>
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
							<fmt:formatDate value="${require.readyDate }" pattern="yyyy-MM-DD"/>
							</td>
							</tr>
							<tr>
							<td class="basic-title">区域</td><td>${require.zone.name }</td>
							<td class="basic-title">楼盘信息：</td><td>${require.houseInfo }</td>
							<td colspan="6">&nbsp;</td>
							</tr>
							<tr>
							<td class="basic-title">房屋地址：</td><td colspan="9">${require.houseLocation }</td>
							</tr><tr>
							<td class="basic-title">量房时间约定：</td><td colspan="9">${require.designTime }</td>
							</tr><tr>
							<td class="basic-title">电话时间约定</td><td colspan="9">${require.phoneTime }</td>
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
							<div>
							<h2>业务员分单</h2>
							<form class="form-horizontal" action="toService.html#myform" method="post" id="myform" rel="admin-form">
							<input name="operator" value="to_service" type="hidden"/>
							<input name="pageIndex" id="pageIndex" type="hidden" value="${pageResult.pageIndex}"/>
							<input name="pageSize" type="hidden" value="${pageResult.pageSize}"/>
							<input id="requiredId" name="requiredId" type="hidden" value="${require.requiredId}"/>
						
								<table class="table border" style="width:80%;">
								<tr>
								<td>姓名：</td><td><input type="text" class="span8" name="username" value="${param.username }"></td>
								<td>号码：</td><td><input type="text" class="span8" name="userphone" value="${param.userphone }"></td>
								<td><span class="btn btn-mini" id="data-search">搜索</span></td></tr>
								</table>
							<h2>搜索结果</h2>	
								<!-- 数据展示 -->
								<table class="table">
								<tr>
								<td width="15%">姓名</td><td width="15%">电话</td>
								<td width="15%">上次分单时间</td><td width="15%">本月分单数</td>
								<td width="15%">总分单数</td><td>操作</td>
								</tr>
								<c:forEach items="${pageResult.param }" var="temp">
								<tr>
								<td>${temp.nickname }</td>
								<td>${temp.userphone }</td>
								<td><fmt:formatDate value="${temp.lastDate }" pattern="yyyy-MM-dd HH:mm"/></td>
								<td>${temp.monthCount }</td>
								<td>${temp.totalCount }</td><td><span userId="${temp.userId }" class="to_service btn btn-mini btn-success">分单</span></td>
								</tr>
								</c:forEach>
								</table>
								  <!--分页控制  -->
								<ad:page pageIndex="${pageResult.pageIndex }"
									 pageSize="${pageResult.pageSize}" 
									 totalPage="${pageResult.totalPage}"></ad:page>
						</form>
							</div>
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
		$(".to_service").on("click",function(){
			var userId=$(this).attr("userId");//用户ID
			var requiredId=$("#requiredId").val();
			if(!userId||!requiredId){
				layer.msg("数据缺失");
				return;
			}
			var param={};
			param.userId=userId;
			param.requiredId=requiredId;
			param.operator="doService";
			layer.confirm("确认分给此人吗？", {
				title:"分单",
			    btn: ["确定","返回"], //按钮
			    shade: false //不显示遮罩
			}, function(){
				$.post("toService.html",param,function(data){
				if(data.status==1){
					layer.msg(data.message,{ icon: 1,time: 1000 },function(){
						window.location.href="index.html";
					});
				}else{
					layer.msg(data.message);
				}
			},"json");
			
			}, function(){
				
			});
		});
		
		//开始搜索
		$("#data-search").on("click",function(){
			$("#pageIndex").val(1);
			$("#myform").submit();
		});
	});
	</script>
</body>
</html>
