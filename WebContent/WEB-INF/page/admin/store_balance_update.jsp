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
					<li><i class="icon-angle-right"></i> <a href="#">商户充值 </a> 
				</ul>

				<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2>
								<i class="halflings-icon user"></i><span class="break"></span>店铺充值管理
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
						<input name="operator" id="operator" type="hidden" value="findById"/>
						<input name="storeId" id="storeId" type="hidden" value="${store.storeId}"/>
						<table class="table ">
						<tr>
							<td class="info-title" width="10%;">店铺名称</td><td width="15%;">${store.storeName }</td>
							<td class="info-title" width="5%;">负责人</td><td width="15%;">${store.keeper.nickname }</td>
							<td class="info-title" width="5%;">地区</td><td width="15%;">${store.zone.name }</td>
							<td class="info-title" width="5%;">余额</td><td width="15%;">￥ ${store.balance }</td>
							
						</tr>
						<tr>
							<td class="info-title-high" >充值</td>
							<td class="info-title-high">+/-<select style="width:100px;float:right;" id="type">
									<option value="">增加</option>
									<option value="-">减少</option></select></td>
							<td class="info-title-high">金额</td><td><input type="text" id="value" class="span11" placeholder="请输入整数充值金额" maxlength="10"/></td>
							<td class="info-title-high">备注</td><td><input type="text" id="message" class="span11" placeholder="请输入备注,20字以内" maxlength="20"/></td>
							<td colspan="2"><button type="button" id="send-add" class="btn btn-middle btn-danger">确定充值</button></td>
						</tr>
						
						</table>
							  
						</form>
						
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
		$("#send-add").on("click",function(){
			var storeId=$("#storeId").val();
			var type=$("#type").val();
			var value=$("#value").val();
			var message=$("#message").val();
			if(!value){
				layer.msg("请输入充值金额，且不要带空格！")
				return;
			}else if(!value.match(/^\d+$/)){
				layer.msg("请输入正确的充值金额，且不要带空格！")
				return;
			}
			var param={};
			param.operator="update";
			param.storeId=storeId;
			param.message=message;
			param.value=type+value;
			//进行提示
			layer.confirm("请确认，将要充值的金额为 "+param.value+" 元！", {
				title:"充值提醒",
			    btn: ["确定","返回"], //按钮
			    shade: false //不显示遮罩
			}, function(){
				$("#send-add").attr("disable","disabled");
				$.post("update.html",param,function(data){
					$("#send-add").removeAttr("disable");
				if(data.status==1){
					layer.msg(data.message,{ icon: 1,time: 1000 },function(){
						window.location.reload();
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
