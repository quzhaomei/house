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
				 	<li><i class="icon-angle-right"></i><a href="#">需求派单</a></li>
				</ul>

				<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon edit"></i><span class="break"></span>需求派单</h2>
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
								<c:when test="${require.status==7  }">
									需求提交中
								</c:when>
								<c:when test="${require.status==1  }">
									已分配
								</c:when>
								<c:when test="${require.status==2  }">
									短信中
								</c:when>
								<c:when test="${require.status==3  }">
									客户打开连接
								</c:when>
								<c:when test="${require.status==4  }">
									客户修改并提交
								</c:when>
								<c:when test="${require.status==5  }">
									待派单
								</c:when>
								<c:otherwise>
									已派单
								</c:otherwise>
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
							<td class="basic-title">预算：</td><td>￥<fmt:formatNumber value="${require.budget }"></fmt:formatNumber> </td>
							<td colspan="4">&nbsp;</td>
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
							
							<c:if test="${!empty orderHistory.param }">
								<legend>派单历史</legend>
								<c:forEach items="${orderHistory.param }" var="temp">
									<p> <fmt:formatDate value="${temp.createDate }" pattern="yyyy-MM-dd HH:mm"/>
									由 ${temp.createUser.nickname } 派单给 ${temp.store.storeName }
									<c:if test="${temp.type==2 }">
										<span class="label label-important">赠送</span>
									</c:if>
									 </p>
								
								</c:forEach>
							</c:if>
							
							
							
							</fieldset>
							<div>
							<h2>店铺派单</h2>
							订单单价：￥ ${require.houseType.price }
							<form class="form-horizontal" action="toStore.html#myform" method="post" id="myform" rel="admin-form">
							<input name="operator" value="to_store" type="hidden"/>
							<input name="pageIndex" id="pageIndex" type="hidden" value="${pageResult.pageIndex}"/>
							<input name="pageSize" type="hidden" value="${pageResult.pageSize}"/>
							<input id="requiredId" name="requiredId" type="hidden" value="${require.requiredId}"/>
						
								<table class="table border" style="width:90%;">
								<tr>
								<td>地区</td><td>
								<select style="width:120px;" id="citys">
								<option>市</option> 
								<c:forEach items="${zones }" var="zone">
									<option value="${zone.zoneId }" ${curZone.parent.zoneId==zone.zoneId?"selected='selected'":"" }>${zone.name }</option>
								</c:forEach>
								</select>
								<select style="width:120px;" name="zoneId" id="zoneId">
								<option>区</option> 
								<c:forEach items="${curZones }" var="zone">
									<option value="${zone.zoneId }" ${curZone.zoneId==zone.zoneId?"selected='selected'":"" }>${zone.name }</option>
								</c:forEach>
								</select>
								</td>
								<td>房型</td>
								<td>
								<select name="houseTypeId">
									<option value="-1">按房型搜索</option>
									<c:forEach items="${houses }" var="house">
									<option value="${house.typeId }"
										<c:choose>
											<c:when test="${! empty param.houseTypeId && house.typeId==param.houseTypeId  }">
												selected="selected"
											</c:when>
											<c:when test="${empty param.houseTypeId &&require.houseType.typeId==house.typeId }">
												selected="selected"
											</c:when>
										</c:choose>
										  > ${house.name }</option>
									</c:forEach>
								</select> 
								</td>
								<td>店铺名称</td><td><input type="text" class="span8" name="storename" value="${param.storename }"></td>
								<td><span class="btn btn-mini" id="data-search">搜索</span></td></tr>
								</table>
							<h2>店铺推荐</h2>	
								<!-- 数据展示 -->
								<table class="table">
								<tr>
								<td width="15%">商家名称</td><td width="15%">账户余额</td>
								<td >每月订单控量</td>
								<td width="20%">本月发单情况</td>
								<td>操作</td>
								</tr>
								<c:forEach items="${pageResult.param }" var="temp" >
								<tr>
								<td>${temp.storeName }</td><td>${temp.balance }</td>
								<td>${temp.size }</td>
								<td>派单：${temp.monthCount } 份 ，赠送:${temp.monthFreeCount } 份</td>
								<td>
								<span class="btn btn-mini btn-success send-to-store" storeId="${temp.storeId}" type="1">派单</span>
								<span class="btn btn-mini btn-important send-to-store" storeId="${temp.storeId}" type="2">赠送</span>
								</td>
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
		//市区级联
		$("#citys").on("change",function(){
			var zoneId=this.value;
			$("#zoneId").empty();
			$("#zoneId").append($("<option>区</option>"))
			if(zoneId&&zoneId.match(/^\d+$/)){
				var param={};
				param.operator="findOrderZone";
				param.zoneId=zoneId;
				$.post("toStore.html",param,function(json){
					
					if(json){
						$("#orderZoneIdsParent").css("visibility","visible");
						$(json).each(function(){
							$("#zoneId").append($("<option value='"+this.zoneId+"'>"+this.name+"</option>"))
						});
					}
				},"json");
			}
			
		});
		
		//开始搜索
		$("#data-search").on("click",function(){
			$("#pageIndex").val(1);
			$("#myform").submit();
		});
		
		//派单
		$(".send-to-store").on("click",function(){
			var storeId=$(this).attr("storeId");
			var type=$(this).attr("type");
			var requiredId=$("#requiredId").val();
			if(!storeId||!type||!requiredId){return;}
			var _this=this;
			var tips="";
			if(type=="1"){
				tips="确定派送此单吗？";
			}else{
				tips="确定赠送此单吗？"
			}
			layer.confirm(tips, {
				title:"派单确认",
			    btn: ["确定","返回"], //按钮
			    shade: false //不显示遮罩
			}, function(){
				var param={};
				param.requiredId=requiredId;
				param.type=type;
				param.storeId=storeId;
				param.operator="store";
				$(_this).attr("disabled","disabled");
				$.post("toStore.html",param,function(json){
					$(_this).removeAttr("disabled");
					
					if(json.status==1){
						layer.msg(json.message);
						$("#myform").submit();
					}else{
						layer.msg(json.message);
					}
					
				},"json");
			
			}, function(){
				
			});
			
		});
	});
	</script>
</body>
</html>
