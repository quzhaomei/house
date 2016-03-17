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
					<li><i class="icon-home"></i> <a href="index.html">首页</a></li>
				<!-- 	<li><a href="#">系统设置<i class="icon-angle-right"></i> </a> -->
				</ul>

				<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2>
								<i class="halflings-icon user"></i><span class="break"></span>店铺订单管理
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
							
							<td width="8%;">状态</td>
							<td width="10%">
								<select rel="chosen" name="status">
								<option value="-1">全部</option>
								<option value="0" ${param.status==0?"selected='selected'":"" }>未接单</option>
								<option value="1" ${param.status==1?"selected='selected'":"" }>已接单</option>
							</select>
							</td>
							<td width="8%">订单时间</td>
							<td width="35%">
							<input type="text" name="startDate" value='${param.startDate }' class="datepicker span5" />
									-
							<input type="text" name="endDate" value="${ param.endDate}" class="datepicker span5"/></td>
							<td>
							<button type="button" class="btn btn-small btn-search">
							<i class="halflings-icon search white"></i> 查询</button>
							</td>
						</tr>
						</table>
							  
						</form>
						<table class="table table-striped table-bordered bootstrap-datatable">
						  <thead>
							  <tr>
								  <th width=7%>订单号</th>
								  <th width=5%>类型</th>
								  <th width=12%>店铺 </th>
								  <th width=8%>状态</th>
								  <th width=10%>创建时间</th>
								  <th width=10%>创建人</th>
								  <th width=8%>区域</th>
								  <th width=10%>房型面积</th>
								  <th width=7%>费用</th>
								  <th width=7%>量房确认</th>
								  <th width=20%>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  <c:if test="${empty pageResult.param}">
						  	<tr><td colspan="8" style="text-align:center;color:red;font-size:15px;">暂无数据</td></tr>
						  </c:if>
						  <c:forEach items="${pageResult.param }" var="temp" varStatus="status">
						  	<tr>
								<td>${temp.orderId }</td>
								<td>
									<c:choose>
										<c:when test="${temp.type==1 }">
											<span class="label label-success">消费</span>
										</c:when>
										<c:when test="${temp.type==2 }">
											<span class="label label-important">赠送</span>
										</c:when>
									</c:choose>
								</td>
								<td class="center">${fn:escapeXml(temp.store.storeName) }</td>
								<td class="center">
									<c:choose>
										<c:when test="${temp.status==0 }">
											<span class="label label-success">未接单</span>
										</c:when>
										<c:when test="${temp.status==1 }">
											<span class="label">已接单</span>
										</c:when>
									</c:choose>
								</td>
								<td class="center">
									<fmt:formatDate value="${temp.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>${temp.createUser.nickname }</td>
								<td>${temp.zonename }</td>
								<td>${temp.typename }</td>
								<td>${temp.price }</td>
								<td>
								<c:choose>
									<c:when test="${temp.isSuccess==0 }">
										<span class="label">失败</span>
									</c:when>
									<c:when test="${temp.isSuccess==1 }">
										<span class="label label-success">成功</span>
									</c:when>
								</c:choose>
								</td>
								<td class="center">
								<ad:power uri="../order/list.html">
										<a class="btn  btn-mini"  href="list.html?orderId=${temp.orderId }">
											<i class="halflings-icon white search " ></i>  查看
										</a>
									</ad:power>
									
								<ad:power uri="../order/getOrder.html">
								<c:if test="${temp.status==0 }">
									<a class="btn btn-success btn-mini getOrder" orderId="${temp.orderId }" href="#">
										<i class="halflings-icon white edit " ></i>  接单
									</a>
									</c:if>
									<c:if test="${temp.status==1&&empty temp.isSuccess }">
										<a class="btn btn-danger btn-mini orderConfirm" orderId="${temp.orderId }" href="#">
										<i class="halflings-icon white edit " ></i>  量房结果确认
									</a>
									</c:if>
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
		
		$(".getOrder").on("click",function(){
			var orderId=$(this).attr("orderId");
			layer.confirm("确定接单吗？", {
				title:"接单确认",
			    btn: ["确定","返回"], //按钮
			    shade: false //不显示遮罩
			}, function(){
				var param={};
				param.orderId=orderId;
				param.operator="order";
				//先检测余额
				$.post("getOrder.html",param,function(json){
					if(json.status==1){
						layer.msg("接单成功");
						$("#myform").submit();
					}else{
						layer.msg(json.message);
					}
				},"json")
				//跳转到页面
				
			}, function(){
				
			});
			
		});
		
		$(".orderConfirm").on("click",function(){
			var orderId=$(this).attr("orderId");
			layer.open({
			    type: 1,
			    scrollbar: false,
			    title:"量房结果确认",
			    shadeClose:true,
			    skin: 'layui-layer-rim', //加上边框
			    area: ['540px', 'auto'], //宽高
			    content: '<input type="hidden" id="orderIdTemp" /><div class="status"><a class="btn  status btn-mini " status="1" href="javascript:;">'+ 
					' 成功</a><a class="btn  status btn-mini" status="0" href="javascript:;">'+ 
					' 失败</a></div>'+ 
					' <p class="stLine">商家备注：<small>失败时必须填写商户备注，成功则选填</small></p><textarea style="resize:none;"maxlength="1000" class="statuInfo"></textarea>'+
					'<div class="modal-footer"><a href="#" class="btn btn-sm layui-layer-close" data-dismiss="modal">返回</a> <a href="#" class="btn btn-primary btn-sm status-submit">更新</a>'+
					'</div>',
				success:function(){
					$("#orderIdTemp").val(orderId);
				}
			});
		});
		
		
		$("body").on("click","div.status a.btn",function(){
			var status=$(this).attr("status");
			
			if($(this).hasClass("btn-success")){
				return;
			}
			$("div.status a.btn").removeClass("btn-success");
			$(this).addClass("btn-success");
		});
		
		$("body").on("click","a.status-submit",function(){
			var orderIdTemp=$("#orderIdTemp").val();
			var isSuccess=$("div.status a.btn[status].btn-success").attr("status");
			var info=$(".statuInfo").val();
			var nextcallTime=$("#nextcallTime").val();
			if(!isSuccess){
				layer.msg("请选择是否成功");
				return;
			}
			if(isSuccess=="0"&&!info){
				layer.msg("请填写商户备注");
				return;
			}
			
			var param={};
			param.isSuccess=isSuccess;
			if(info){param.info=info;}
			param.orderId=orderIdTemp;
			param.operator="isSuccess";
			
			$.post("getOrder.html",param,function(data){
				if(data.status=="1"){
					layer.msg(data.message);
					$("#myform").submit();
				}else{
					layer.msg(data.message);
				}
				
			},"json");
		});
		
	});
	</script>
</body>
</html>
