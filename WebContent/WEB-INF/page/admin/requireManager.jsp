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
tr.other-info{display:none;border-top:1px solid red;}
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
					<li><i class="icon-angle-right"></i> <a href="index.html">预约需求列表 </a></li>
				</ul>

				<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2>
								<i class="halflings-icon user"></i><span class="break"></span>需求预约管理
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
						<input name="operator" value="to_service" type="hidden"/>
						<table class="table">
						<tr>
							<td width=7%>用户名称</td><td width=15%><input class="span10" type="text" id="username" value="${param.username }" name="username" maxlength="20" placeholder="请输入用户名称"/></td>
							<td width=7% >用户Id</td><td width=15%><input class="span10" type="text" id="userId" value="${param.userId }" name="userId" maxlength="20" placeholder="请输入用户ID"/></td>
							<td width=5%>状态</td><td width=18%><select style="width:100px;" name="status" id="chonsenstatus" rel="chosen">
								<option value="-1" ${param.status=="-1"?"selected='selected'":"" }>全部</option>
								<option value="0" ${param.status=="0"?"selected='selected'":"" }>发起中</option>
								<option value="1" ${param.status=="1"?"selected='selected'":"" }>短信中</option>
								<option value="2" ${param.status=="2"?"selected='selected'":"" }>客户打开连接</option>
								<option value="3" ${param.status=="3"?"selected='selected'":"" }>客户修改提交</option>
								<option value="4" ${param.status=="4"?"selected='selected'":"" }>待发布</option>
								<option value="6" ${param.status=="6"?"selected='selected'":"" }>待分单</option>
								<option value="7" ${param.status=="7"?"selected='selected'":"" }>待派单</option>
								<option value="8" ${param.status=="8"?"selected='selected'":"" }>已派单</option>
								<option value="40" ${param.status=="40"?"selected='selected'":"" }>退单</option>
								<option value="41" ${param.status=="41"?"selected='selected'":"" }>待跟进库</option>
							</select></td>
						</tr>
						<tr>
							<td>录入时间</td>
							<td>
							<input type="text" name="startDate" id="startDate"value='${param.startDate }' class="datepicker span5" />
									-
							<input type="text" name="endDate" id="endDate" value='${param.endDate }' class="datepicker span5"/></td>
							<td>分单时间</td>
							<td>
							<input type="text" name="serviceStartDate" id="serviceStartDate" value='${param.serviceStartDate }' class="datepicker span5" />
									-
							<input type="text" name="serviceEndDate" id="serviceEndDate" value='${param.serviceEndDate }' class="datepicker span5"/>
							</td><td colspan="2"><span class="add-on">
							<button type="button" class="btn btn-small btn-search">
							<i class="halflings-icon search white"></i> 查询</button>
							</span></td>
						</tr>
						
						<tr class="other-info">
						
							<td >退单/入库 时间</td>
							<td>
							<input type="text" name="startFileTime" id="startFileTime"value='${param.startFileTime }' class="datepicker span5" />
									-
							<input type="text" name="endFileTime" id="endFileTime" value='${param.endFileTime }' class="datepicker span5"/>
							</td>
							<td >退单/入库 原因</td>
							<td><input type="text" name="callbackTips" id="callbackTips" value='${param.callbackTips }' class="span12" maxlength="200" /> </td>
							
							<td >下次回访时间</td>
							<td>
							<input type="text" name="startNextCallTime" id="startNextCallTime"value='${param.startNextCallTime }' class="datepicker span5" />
									-
							<input type="text" name="endNextCallTime" id="endNextCallTime" value='${param.endNextCallTime }' class="datepicker span5"/>
							</td>
						</tr>
						
						</table>
						</form>
						
						<table class="table table-striped table-bordered bootstrap-datatable">
						  <thead>
							  <tr>
								  <th width=5%>序列</th>
								  <th width=15%>录入时间</th>
								  <th width=10%>地区</th>
								  <th width=5%>用户ID </th>
								  <th width=10%>用户名称</th>
								  <th width=10%>用户手机</th>
								  <th width=15%>分单时间</th>
								  <th width=8%>状态</th>
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
						  		<td>${fn:substring(temp.userphone,0,3) }********</td>
						  		<td><fmt:formatDate value="${temp.serviceDate }" pattern="yyyy-MM-dd HH:mm"/></td>
						  		<td>
						  			<c:choose>
						  				<c:when test="${temp.status==0 }">
						  				<span class="label">发起中</span>
						  				</c:when>
						  				
						  				<c:when test="${temp.status==1}">
						  				<span class="label">短信中</span>
						  				</c:when>
						  				<c:when test="${temp.status==2}">
						  				<span class="label">客户打开连接</span>
						  				</c:when>
						  				<c:when test="${temp.status==3}">
						  				<span class="label">客户修改提交</span>
						  				</c:when>
						  				<c:when test="${temp.status==4}">
						  				<span class="label">待发布</span>
						  				</c:when>
						  				
						  				<c:when test="${temp.status==6}">
						  				<span class="label">待分单</span>
						  				</c:when>
						  				<c:when test="${temp.status==7}">
						  				<span class="label">待派单</span>
						  				</c:when>
						  				<c:when test="${temp.status==8}">
						  				<span class="label">已派单</span>
						  				</c:when>
						  				
						  				
						  				<c:when test="${temp.status==40}">
						  				<span class="label label-important">退单</span>
						  				</c:when>
						  				<c:when test="${temp.status==41}">
						  				<span class="label">待跟进库</span>
						  				</c:when>
						  			</c:choose>
						  		</td>
						  		<td>
						  		<ad:power uri="../requireManager/list.html">
						  		<a class="btn btn-mini" href="list.html?requiredId=${temp.requiredId }">详情</a>
						  		</ad:power>
						  		<c:choose>
						  		
						  			<c:when test="${temp.status==6 }">
						  			<ad:power uri="../requireManager/toService.html">
							  			<a class="btn btn-mini green" href="toService.html?operator=to_service&requiredId=${temp.requiredId }">
							  			去分单</a>
							  			</ad:power>
						  			</c:when>
						  			
						  		</c:choose>
						  		
						  		<c:if test="${(temp.status==6||temp.status==7||temp.status==8)&&(!empty temp.callbackTips) }">
						  		<ad:power uri="../requireManager/toStore.html">
							  			<a class="btn btn-mini yellow" href="toStore.html?operator=to_store&requiredId=${temp.requiredId }">
							  			去派单</a>
							  			</ad:power>
							  		</c:if>
							  		
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
		//删除
		$(".changeStatu").click(function(){
			var status=$(this).attr("status");
			var storeId=$(this).attr("storeId");
			var param={};
			param.storeId=storeId;
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
		
		$(".btn-search").on("click",function(){
			$("#pageIndex").val(1);
			$(this).parents("form").submit();
		});
		
		$(".temp-info").on("click",function(){
			var _this=this;
			layer.msg($(_this).attr("title").replace(/#/g,"<br/>"), {time: 5000, icon:6});
		});
		
		$("#chonsenstatus").on("change",function(){
			var value=$(this).val();
			if(value=="40"||value=="41"){
				$(".other-info").show();
			}else{
				$(".other-info").hide();
				$("#callbackTips").val("");
				$("#startFileTime").val("");
				$("#endFileTime").val("");
			}o
		});
		$("#chonsenstatus").change();
	});
	</script>
</body>
</html>
