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
					<li><i class="icon-angle-right"></i> <a href="index.html">发布需求 </a></li>
				</ul>

				<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2>
								<i class="halflings-icon user"></i><span class="break"></span>发布需求
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
							<td width=7%>用户名称</td><td width=18%><input class="span10" type="text" id="username" value="${param.username }" name="username" maxlength="20" placeholder="请输入用户名称"/></td>
							<td width=7% >用户Id</td><td width=18%><input class="span10" type="text" id="userId" value="${param.userId }" name="userId" maxlength="20" placeholder="请输入用户号码"/></td>
							<td width=5%>&nbsp;</td><td width=18%>
							&nbsp;
							</td>
						</tr>
						<tr>
							<td>录入时间</td>
							<td>
							<input type="text" name="startDate" id="startDate"value='${param.startDate }' class="datepicker span5" />
									-
							<input type="text" name="endDate" id="endDate" value='${param.endDate }' class="datepicker span5"/></td>
							<td>手机号</td>
							<td>
							<input type="text" name="userphone" id="userphone" value='${param.userphone }'/></td>
							<td colspan="2">
							<span class="add-on">
							<button type="button" class="btn btn-small btn-search">
							<i class="halflings-icon search white"></i> 查询</button>
							</span>
							<ad:power uri="../requirePublish/add.html">
							<a href="add.html?operator=toAdd" class="btn btn-small btn-search addHouse btn-danger">
							<i class="halflings-icon plus white"></i> 新建预约</a>
								</ad:power>
							</td>
						</tr>
						</table>
						</form>
					
						<table class="table table-striped table-bordered bootstrap-datatable">
						  <thead>
							  <tr>
								  <th width=5%>序列</th>
								  <th width=13%>录入时间</th>
								  <th width=8%>区域</th>
								  <th width=8%>用户ID </th>
								  <th width=8%>用户名称</th>
								  <th width=10%>用户手机</th>
								  <th width=5%>状态</th>
								  <th width=12%>楼盘信息</th>
								  <th >操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  	<c:forEach items="${pageResult.param }" var="temp" varStatus="status">
						  		<tr>
						  		<td>${status.count+(pageResult.pageIndex-1)*(pageResult.pageSize) }</td>
						  		<td><fmt:formatDate value="${temp.createDate }" pattern="yyyy-MM-dd HH:mm"/> </td>
						  		<td>${temp.zone.name }</td>
						  		<td>${temp.userId }</td>
						  		<td>${temp.username }</td>
						  		<td>${temp.userphone }</td>
						  		<td>
						  			<c:choose>
						  				<c:when test="${temp.status==0 }">
						  				<span class="label label-success">发起中</span>
						  				</c:when>
						  				<c:when test="${temp.status==1 }">
						  				<span class="label label-success">短信中</span>
						  				</c:when>
						  				<c:when test="${temp.status==2 }">
						  				<span class="label label-success">客户打开短信</span>
						  				</c:when>
						  				<c:when test="${temp.status==3 }">
						  				<span class="label label-success">客户修改提交</span>
						  				</c:when>
						  				<c:when test="${temp.status==4 }">
						  				<span class="label label-success">确认完毕待发布</span>
						  				</c:when>
						  				<c:when test="${temp.status==40 }">
						  				<span class="label label-important">退单</span>
						  				</c:when>
						  				<c:otherwise>
						  				<span class="label">已发布</span>
						  				</c:otherwise>
						  			</c:choose>
						  		</td>
						  		<td>${temp.houseInfo }</td>
						  		
						  		<td>
						  		<ad:power uri="../requirePublish/list.html">
						  		<a class="btn btn-mini" href="list.html?requiredId=${temp.requiredId }">详情</a>
						  		</ad:power>
						  			<c:if test="${temp.status<=4 }">
						  				<ad:power uri="../requirePublish/update.html">
							  			<a class="btn btn-mini green" href="update.html?operator=toUpdate&requiredId=${temp.requiredId }">
							  			修正预约</a>
							  			</ad:power>
							  			<ad:power uri="../requirePublish/status.html">
							  			<a class="btn btn-mini green sendRequire"   status="${temp.status}" requiredId="${temp.requiredId }" href="#">
							  			发布</a>
							  			</ad:power>
							  			</c:if>
							  			
							  			<c:choose>
								  			<c:when test="${temp.status==0||temp.status==1 }">
								  				<ad:power uri="../requirePublish/sendMsg.html">
									  			<a class="btn btn-danger btn-mini sendMsg" status="${temp.status}" requiredId="${temp.requiredId }" href="#">
									  			发送短信</a>
									  			</ad:power>
								  			</c:when>
								  			<c:when test="${temp.status==3}">
								  				<ad:power uri="../requirePublish/confirm.html">
									  			<a class="btn btn-danger btn-mini" href="confirm.html?operator=to_confirm&requiredId=${temp.requiredId }">
									  			确认信息</a>
									  			</ad:power>
								  			</c:when>
								  		</c:choose>
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
		
		function reset(){
			$("#myform").submit();
		}
		
		//发送短信
		$(".sendMsg").click(function(){
			var requiredId=$(this).attr("requiredId");
			var status=$(this).attr("status");
			
			var param={};
			param.requiredId=requiredId;
			var tip="确定向用户发送确认短信吗？";
			if(status==1){
				tip="已经向该用户发送过确认短信了，确认再次发送吗？";
			}
			
			layer.confirm(tip, {
				title:"短信确认",
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
		
		$(".sendRequire").on("click",function(){
			var requiredId=$(this).attr("requiredId");
			var status=$(this).attr("status");
			var param={};
			param.requiredId=requiredId;
			if(!requiredId){return false;}
			var tips="确认发布吗？发布后，不可更改";
			if(status=="0"){
				tips="* 还未给客户发送过确认短信，确定直接发布吗？";
			}else if(status=="1"){
				tips="* 客户还未点开过短信，确定直接发布吗？";
			}else if(status=="2"){
				tips="* 客户访问页面但还未确认信息，确定直接发布吗？";
			}else if(status=="3"){
				tips="* 客户修改提交后，还未确认，确定直接发布吗？";
			}
			
			layer.confirm(tips, {
				title:"发布预约",
			    btn: ["确定","返回"], //按钮
			    shade: false //不显示遮罩
			}, function(){
				$.post("status.html",param,function(data){
				if(data.status==1){
					layer.msg(data.message,{ icon: 1,time: 1000 },function(){
						reset();
					});
				}else{
					layer.msg(data.message);
				}
			},"json");
			
			}, function(){
				
			});
		});
		
	});
	</script>
</body>
</html>
