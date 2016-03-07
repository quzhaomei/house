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
					<li><i class="icon-home"></i> <a href="index.html">首页</a><i class="icon-angle-right"></i></li>
				<li><a href="#">${houseType.name }房型佣金设置 </a> 
				</ul>

				<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2>
								<i class="halflings-icon user"></i><span class="break"></span>房型管理
							</h2>
							<div class="box-icon">
							
							<ad:power uri="../houseType/add.html">
								<a href="#" class="addHouse"><i
									class="halflings-icon plus"></i>
									 </a> 
								</ad:power>
									 
									 <a href="#"
									class="btn-minimize"><i class="halflings-icon chevron-up"></i>
								</a> <a href="#" class="btn-close"><i
									class="halflings-icon remove"></i> </a>
							</div>
					</div>
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
								  <th width=5%>序列</th>
								  <th width=10%>店铺名称</th>
								  <th width=10%>房型名称</th>
								  <th width=10%>店铺佣金</th>
								  <th width=10%>更新者</th>
								  <th width=15%>更新时间</th>
								  <th width=20%>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  <c:forEach items="${datas }" var="temp" varStatus="status">
						  	<tr>
								<td>${status.count }</td>
								<td>${temp.storeName }</td>
								<td>${houseType.name }</td>
								<td>
									<c:choose>
										<c:when test="${empty temp.price }">
											默认值(${houseType.price})
										</c:when>
										<c:otherwise>
											${temp.price }
										</c:otherwise>
									</c:choose>
								</td>
								<td>${temp.updateUser.nickname }</td>
								<td><fmt:formatDate value="${temp.updateDate }" pattern="yyyy-MM-dd HH:mm"/>
								</td>
								<td class="center">
									<ad:power uri="../houseType/update.html">
									<a class="btn btn-info btn-mini addStoreSet" href="#" 
									storeId="${temp.storeId }">
										  ${houseType.name }价格设置
									</a>
									</ad:power>
								</td>
							</tr>
						  </c:forEach>
						  </tbody>
					  </table>            
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
	var typeId="${houseType.typeId }";
	$(function(){
		var bak;//修改数据备份
		//修改
		$("#content").on("click",".addStoreSet",function(){
			var storeId=$(this).attr("storeId");

			var param={};
			param.operator="toStoreSetById";
			param.storeId=storeId;
			param.typeId=typeId;
			if(typeId){
			$.post("update.html",param,function(data){
				if(data.status==1){
					bak=data.data;
				$("#store_id").val(bak.storeId);
				$("#update_price").val(bak.price);
				$("#updateModal").modal("show");
				}else{
					layer.msg(data.message);
				}
			},"json");		
			}
		});
		
		//提交更新
		$(".updateSubmit").click(
		function(){
			var param={};
			param.operator="storeSetById";
			
			param.typeId=typeId;
			param.storeId=$("#store_id").val();
			var isChange=false;
			if($("#update_price").val()!=bak.price){
				param.price=$("#update_price").val();
				isChange=true;
			}
			if(param.price&&!param.price.match(/^\d+$/)){
				layer.msg("请输入正确的房型佣金");
				return;
			}
			if(!isChange){
				layer.msg("数据没有任何变化");
				return;
			}
			
			$.post("update.html",param,function(data){
				if(data.status==1){
					layer.msg(data.message);
					window.location.reload();
				}else{
					layer.msg(data.message);
				}
			},"json");
		})
	});
		
	</script>
</body>
	
	<!-- 修改modal -->
	<div class="modal hide fade" id="updateModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>房型设置</h3>
		</div>
		<div class="modal-body">
			<p><small class="text-success">更新房型</small></p>			
					<table>
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i>  佣 金 </span>
					</td>
					<td>
						<input class="input-large span5" id="update_price"  type="text" placeholder="请输入 ${houseType.name } 佣金" maxlength="10" >
						<input class="input-large span5" id="store_id"  type="hidden"  >
					</td>
					</tr>
					</table>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">返回</a> <a href="#"
				class="btn btn-primary updateSubmit">保存</a>
		</div>
	</div>
</html>
