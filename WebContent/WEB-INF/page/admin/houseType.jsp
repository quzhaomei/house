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
								  <th width=10%>房型名称</th>
								  <th width=5%>佣金</th>
								  <th width=10%>创建用户</th>
								  <th width=15%>创建时间</th>
								  <th width=10%>更新者</th>
								  <th width=15%>更新时间</th>
								  <th width=10%>状态</th>
								  <th width=20%>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  <c:forEach items="${datas }" var="temp" varStatus="status">
						  	<tr>
								<td>${status.count }</td>
								<td class="center">${fn:escapeXml(temp.name) }</td>
								<td class="center">${temp.price }</td>
								<td>
									${temp.createUser.nickname }
								</td>
								<td class="center">
									<fmt:formatDate value="${temp.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
									${temp.updateUser.nickname }
								</td>
								<td class="center">
									<fmt:formatDate value="${temp.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								
								<td class="center">
								<c:if test="${temp.status==0 }">
									<span class="label label-important">冻结 0</span>
								</c:if>
								<c:if test="${temp.status==1 }">
									<span class="label label-success">激活 1</span>
								</c:if>
								</td>
								
								<td class="center">
									<ad:power uri="../houseType/update.html">
									<a class="btn btn-info btn-mini editHouse" href="#" typeId="${temp.typeId }">
										<i class="halflings-icon white edit "></i>  修改
									</a>
									</ad:power>
									
									<ad:power uri="../houseType/status.html">
									<a class="btn btn-danger btn-mini changeStatu" href="#" typeId="${temp.typeId }" status="${temp.status }" >
										<i class="halflings-icon white refresh"></i> 切换状态
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
	var bak;
	$(function(){
		//增加
		$(".addHouse").click(function(){
			$("#add_name").val("");//重置名字
			$("#addModal").modal("show");//展示modal
		});
		//提交新增
		$(".addSubmit").on("click",
		function(){
			var param={};
			param.name=$("#add_name").val();
			param.price=$("#add_price").val();
			if(!param.name||$.trim(param.name)==""){
				layer.msg("房型名称不能为空！");
				return;
			}
			param.price=$("#add_price").val();
			if(!param.price||$.trim(param.price)==""||!param.price.match(/^\d+$/)){
				layer.msg("请输入正确的房型佣金");
				return;
			}
			$.post("add.html",param,function(data){
				if(data.status==1){
					layer.msg(data.message);
					window.location.reload();
				}else{
					layer.msg(data.message);
				}
			},"json");
			}
		);
		
		var bak;//修改数据备份
		//修改
		$("#content").on("click",".editHouse",function(){
			var typeId=$(this).attr("typeId");
			var param={};
			param.operator="findById";
			param.typeId=typeId;
			if(typeId){
			$.post("update.html",param,function(data){
				if(data.status==1){
					bak=data.data;
				$("#update_name").val(bak.name);
				$("#update_typeId").val(bak.typeId);
				$("#update_price").val(bak.price);
				
				$(".houseStatus").removeAttr("checked").parent("span").removeClass("checked")
				$(".houseStatus").each(function(){
					if($(this).val()==bak.status){
						$(this).attr("checked","checked").parent("span").addClass("checked");
					}
				});
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
			param.operator="edit";
			
			param.typeId=$("#update_typeId").val();
			
			var isChange=false;
			if($("#update_name").val()!=bak.name){
				param.name=$("#update_name").val();
				isChange=true;
			}
			if($("#update_price").val()!=bak.price){
				param.price=$("#update_price").val();
				isChange=true;
			}
			if($(".houseStatus:checked").val()!=bak.status){
				param.status=$(".houseStatus:checked").val();
				isChange=true;
			}
			if(!isChange){
				layer.msg("数据没有任何变化");
				return;
			}
			
			if(param.price&&!param.price.match(/^\d+$/)){
				layer.msg("请输入正确的房型佣金");
				return;
			}
			$.post("update.html",param,function(data){
				if(data.status==1){
					layer.msg(data.message,window.location.reload());
				}else{
					layer.msg(data.message);
				}
			},"json");
		}
		);
		
		//删除
		$("#content").on("click",".changeStatu",function(){
			var status=$(this).attr("status");
			var typeId=$(this).attr("typeId");
			var param={};
			param.typeId=typeId;
			param.operator="edit";
			var tip="";
			if(status=="0"){//激活
				param.status="1";
				tip="确认激活吗?";
			}else if(status=="1"){//冻结
				param.status="0";
				tip="确认冻结吗";
			}
			layer.confirm(tip, {
				title:"状态切换",
			    btn: ["确定","返回"], //按钮
			    shade: false //不显示遮罩
			}, function(){
				$.post("status.html",param,function(data){
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
<!-- 新增modal -->
	<div class="modal hide fade" id="addModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>量房房型设置</h3>
		</div>
		<div class="modal-body">
			<p><small class="text-success">新建房型</small></p>
				<div class="input-prepend" title="房型名称">
				<span class="add-on"><i class="halflings-icon user"></i> 房型 名 称 </span>
				<input class="input-large span5" id="add_name"  type="text" placeholder="请输入新建房型名称" maxlength="50" >
				</div>	
				<div class="input-prepend" title="房型名称">
				<span class="add-on"><i class="halflings-icon user"></i> 佣金 </span>
				<input class="input-large span5" id="add_price"  type="text" placeholder="请输入房型佣金" maxlength="10" >
				</div>	
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">返回</a> <a href="#"
				class="btn btn-primary addSubmit">保存</a>
		</div>
	</div>
	
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
						<span class="add-on"><i class="halflings-icon user"></i> 房型 名 称 </span>
						<input type="hidden" id="update_typeId" >
					</td>
					<td>
						<input class="input-large span5" id="update_name"  type="text" placeholder="请输入新建房型名称" maxlength="50" >
					</td>
					</tr>
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i> 房型 佣 金 </span>
					</td>
					<td>
						<input class="input-large span5" id="update_price"  type="text" placeholder="请输入房型佣金" maxlength="10" >
					</td>
					</tr>
					<tr>
						<td>
							<span class="add-on"><i class="halflings-icon user"></i> 状态 </span>
						</td>
						<td>
						 <label class="radio" style="padding:0 0;" for="jihuo"> <input type="radio"
							name="status" class="type_radio houseStatus" id="jihuo" value="1"> <span class="label label-success">激活 </span>
						</label>		
						<label class="radio" style="padding:0 0;" for="dongjie"> <input type="radio" name="status"
							class="type_radio houseStatus" id="dongjie" value="0" checked="checked">
							 <span class="label label-important">冻结 </span>
						</label>
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
