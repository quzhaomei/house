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
div.count {margin:10px 0px;font-size:15px;color:red;}
div.count .title{font-size:15px;font-weight:bold;color:#888}
div.count .info{font-size:15px;color:#444;margin:0 30px 0 10px;}
div.count .info.cred{color:red;}

.layui-layer-content{
padding:5px 5px;
}
.status{
text-align: center;
margin:5px 0;
}
.status a{
margin:3px 8px;
}
.statuInfo{
margin:0px 20px;
width:90%;
height:95px;
resize:none;
}
.stLine{
margin:10px 20px;
padding-bottom:5px;
border-bottom:1px solid #aaa;
font-weight: bold;
}
.modal-footer a.btn{
position: relative !important;
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
						<input type="hidden" name="specialStatus" id="specialStatus" value='${empty param.specialStatus?"-1":param.specialStatus }' />
						
						<table class="table">
						<tr>
							<td width=8%>用户名称</td><td width=20%><input class="span10" type="text" id="username" value="${param.username }" name="username" maxlength="20" placeholder="请输入用户名称"/></td>
							<td width=8% >用户Id</td><td width=20%><input class="span10" type="text" id="userId" value="${param.userId }" name="userId" maxlength="20" placeholder="请输入用户ID"/></td>
						<c:choose>
							<c:when test="${param.acceptNum!=1}">
								<td width=8%>状态</td><td width=18%>
								<select style="width:80%;" name="status" id="chonsenstatus" rel="chosen">
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
								</select>
							</td>
							</c:when>
							<c:otherwise>
							<td colspan="2"></td>
							</c:otherwise>
						</c:choose>
							
							<td>&nbsp;
							<ad:power uri="../requireList/loadAll.html">
							<a href="#" class="btn btn-mini red loadAll">下载数据</a>
							</ad:power>
							</td>
						</tr>
						<tr>
							<td>录入时间</td>
							<td>
							<input type="text" name="startDate" id="startDate"value='${param.startDate }' class="datepicker span5" />
									-
							<input type="text" name="endDate" id="endDate" value='${param.endDate }' class="datepicker span5"/></td>
							
							<td>创建人</td>
							<td>
							<select style="width:80%;" name="createUserId" rel="chosen">
								<option value="-1">－全部－</option>
								<c:forEach items="${users }" var="user">
									<option value="${user.adminUserId }" ${param.createUserId==user.adminUserId?"selected='selected'":"" }>${user.nickname }</option>
								</c:forEach>
							</select>
							</td>
							<td colspan="3"><span class="add-on">
							<button type="button" class="btn btn-small btn-search">
							<i class="halflings-icon search white"></i> 查询</button>
							</span></td>
							
						</tr>
						
			
						
						</table>
						</form>
						
						<table class="table table-striped table-bordered bootstrap-datatable">
						 <caption style="text-align:left;">
						特殊状态筛选：
						<c:choose>
							<c:when test="${param.specialStatus==0 }">
								<a href="#" class="chooseOrder" order="-1" style="color:red;"><i class="icon icon-search"></i> 查看全部</a>&nbsp;
								<span>只看关闭</span>
								<a href="#" class="chooseOrder" order="1" style="color:red;"><i class="icon icon-search"></i> 只看待跟进库</a>&nbsp;
							</c:when><c:when test="${param.specialStatus==1 }">
								<a href="#" class="chooseOrder" order="-1" style="color:red;"><i class="icon icon-search"></i> 查看全部</a>&nbsp;
								<a href="#" class="chooseOrder" order="0" style="color:red;"><i class="icon icon-search"></i> 只看关闭</a>&nbsp;
								<span>只看待跟进库</span>
							</c:when>
							<c:otherwise>
								<span>查看全部</span>
								<a href="#" class="chooseOrder" order="0" style="color:red;"><i class="icon icon-search"></i> 只看关闭</a>&nbsp;
								<a href="#" class="chooseOrder" order="1" style="color:red;"><i class="icon icon-search"></i> 只看待跟进库</a>&nbsp;
							</c:otherwise>
						</c:choose>
						
						</caption>
						  <thead>
						  
							  <tr>
								  <th width=5% >序列</th>
								  <th width=12% >录入时间</th>
								  <th width=8% >地区</th>
								  <th width=10% >用户ID </th>
								  <th width=10%>用户名称</th>
								  <th width=10% >用户手机</th>
								  <th width=13% >创建人</th>
								  <th width=8% >状态</th>
								   <th width=13% >特殊状态</th>
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
						  		<td>
						  		<c:choose>
						  			<c:when test="${fn:length(temp.userphone)>3&&fn:length(temp.userphone)<11 }">
						  				${fn:substring(temp.userphone,0,3) }********
						  			</c:when>
						  			<c:when test="${fn:length(temp.userphone)>=11}">
						  			${fn:substring(temp.userphone,0,3) }****${fn:substring(temp.userphone,7,11) }
						  			</c:when>
						  		</c:choose>
						  		
						  		</td>
						  		<td>${temp.createUser.nickname }</td>
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
						  				
						  				
						  				<c:when test="${temp.status==41}">
						  				<span class="label">待跟进库</span>
						  				</c:when>
						  				<c:when test="${temp.status==40}">
						  				<span class="label label-important">退单</span>
						  				</c:when>
						  			</c:choose>
						  		</td>
						  		<td>	<c:choose>
								  		<c:when test="${temp.remarks.status==0 }">
								  			<span class="label">已关闭</span><a href="#" class="status-remarks"  requiredId="${temp.requiredId }" >[原因]</a>
								  		</c:when>
								  		<c:when test="${temp.remarks.status==1 }">
								  			<span class="label label-important">待跟进库</span><a href="#" class="status-remarks"  requiredId="${temp.requiredId }" >[原因]</a>
								  			<br/>下次回访:<fmt:formatDate value="${temp.remarks.nextTime }" pattern="yyyy-MM-dd"/>
								  		</c:when>
								  	</c:choose>
								  	&nbsp;
								  	 </td>
						  		<td>
						  		<ad:power uri="../requireList/list.html">
						  		<a class="btn btn-mini" href="list.html?requiredId=${temp.requiredId }">详情</a>
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
	
		$(".status-remarks").on("click",function(){
			var requiredId=$(this).attr("requiredId");
			if(requiredId){
				var param={};
				param.requiredId=requiredId;
				param.operator="json";
				$.post("list.html",param,function(json){
					if(json.status){
						layer.msg(json.remarks.remark);
					}
				},"json");
			}
		});
		
		$(".chooseOrder").on("click",function(){
			var order=$(this).attr("order");
			if(order){
				$("#pageIndex").val(1);
				$("#chonsenstatus option[value='-1']").attr("selected","selected");
				$("#specialStatus").val(order);
				$("#myform").submit();
			}
		});
		
		//接单
		$(".loadAll").on("click",function(){
			$("#myform").attr("action","loadAll.html");
			$("#myform").submit();
			$("#myform").attr("action","index.html");
		});
		
	});
	</script>
</body>
</html>
