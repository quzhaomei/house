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
.controls .checkbox{margin-right:10px;}
.help-block{font-size:15px;}
#orderZoneIdsParent,#readyDateParent{visibility: hidden;color:#EC1515;}
.textarea{resize:none;width:500px;height:80px;background-color:#eee;border:1px solid red;}
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
				 	<li><i class="icon-angle-right"></i><a href="index.html">发布需求 </a></li>
				 	<li><i class="icon-angle-right"></i><a href="#">预约需求确认</a></li>
				</ul>

				<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon edit"></i><span class="break"></span>需求确认</h2>
						<div class="box-icon">
							<a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
							<a href="#" class="btn-close"><i class="halflings-icon remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<form class="form-horizontal" action="#" method="post">
							<fieldset>
							<legend class="help-block"> 基本信息</legend>
							  <div class="control-group">
								<label class="control-label" for="username">客户姓名</label>
								<div class="controls">
								  <div class="input-prepend">
								  <input id="requiredId" value="${require.requiredId }"  type="hidden">
									<input id="username" maxlength="20" value="${require.username }" readonly="readonly" placeholder="请输入客户姓名" type="text">
								  </div>
								
								</div>
							  </div>
							
							    <div class="control-group">
							  <label class="control-label" for="userphone">客户手机号</label>
							 <div class="controls">
								  <div class="input-prepend">
									<input id="userphone" maxlength="11" value="${require.userphone }" readonly="readonly" placeholder="请输入客户手机号" type="text">
								  </div>
								</div>
							</div>   

							
							 <legend class="help-block"> 房子信息</legend>
							  <div class="control-group">
								<label class="control-label" for="zoneId">区域</label>
								<div class="controls">
								 <div class="input-prepend">
									<input type="text" maxlength="10" class="span6" value="${require.zone.name }" readonly="readonly"> 
										<span class="changeZone btn btn-mini btn-success">点击更换</span>
								  </div>
								  <div style="display:none;">
								   <select id="zoneId" data-rel="chosen" >
									  <option value="0">请选择店铺区域</option>
									  <c:forEach items="${zones }" var="temp">
									  	<option value="${temp.zoneId }">${temp.name }</option>
									  </c:forEach>
								  </select> 
									  </div>
									  <c:if test="${!empty require.zoneBak }">
								  	<span class="label label-important">客户更改为：
								  	${require.zoneBak.name}</span>
								  	</c:if>
								</div>
							  </div>
							  
							   <div class="control-group" id="orderZoneIdsParent">
								<label class="control-label">具体区域</label>
								<div class="controls" id="orderZoneIds">
								 	
								</div>
							  </div>
							
							   <div class="control-group">
							  <label class="control-label" for="typeId">房型</label>
							 <div class="controls">
								  	<c:forEach items="${houses }" var="house">
								  		 <label class="radio inline">
										<input type="radio" name="typeId" ${require.houseType.typeId==house.typeId?"checked='checked'":"" } value="${house.typeId }"> ${house.name }
										  </label>
								  	</c:forEach>
								  	
								  	<c:if test="${!empty require.houseTypeBak }">
								  	<span class="label label-important">客户更改为：
								  	${require.houseTypeBak.name}</span>
								  	</c:if>
								  	
								</div>
							</div> 
							
							
							   <div class="control-group">
								  <label class="control-label" for="houseDes">房型描述</label>
								 <div class="controls">
								  <div class="input-prepend">
									<input id="houseDes" maxlength="10" value="${require.houseDes }" placeholder="请输入房子面积 / 整数" type="text"> 
								  </div>
								  
								  <c:if test="${!empty require.houseDesBak }">
								  	<span class="label label-important">客户更改为：${require.houseDesBak }</span>
								  </c:if>
								  
								</div>
							</div> 
							
							 <div class="control-group">
								  <label class="control-label" for="budget">预算</label>
								 <div class="controls">
								  <div class="input-prepend">
									<input id="budget" maxlength="10" value="${require.budget }" placeholder="预算" type="text">
									<span class="add-on">￥</span> 
								  </div>
								  
								  <c:if test="${!empty require.budgetBak }">
								  	<span class="label label-important">客户更改为：${require.budgetBak } </span>
								  </c:if>
								  
								</div>
							</div> 
							
							<div class="control-group">
							  <label class="control-label" for="isNew">是否新房</label>
							 <div class="controls">
							 <label class="checkbox inline">
										<input type="radio" name="isNew" ${require.isNew==1?"checked='checked'":"" }  value="1"> 是
										  </label>
								   <label class="checkbox inline">
										<input type="radio" name="isNew" ${require.isNew==0?"checked='checked'":"" } value="0"> 否
										  </label>
									
									<c:if test="${!empty require.isNewBak }">
								  	<span class="label label-important">客户更改为：
								  	<c:choose>
								  		<c:when test="${require.isNewBak ==0}">旧房</c:when>
								  		<c:when test="${require.isNewBak ==1}">新房</c:when>
								  	</c:choose>
								  	
								  	</span>
								  </c:if>
								</div>
							</div> 
							
							<div class="control-group">
							  <label class="control-label" for="houseInfo">楼盘信息</label>
							 <div class="controls">
								  <div class="input-prepend">
									<input id="houseInfo" maxlength="20" value="${require.houseInfo }" placeholder="请输入楼盘信息" type="text">
								  </div>
								  
								   <c:if test="${!empty require.houseInfoBak }">
								  	<span class="label label-important">客户更改为：${require.houseInfoBak }</span>
								  </c:if>
								</div>
							</div> 
							
							<div class="control-group">
							  <label class="control-label" for="isReady">交房情况</label>
							 <div class="controls">
							 <label class="checkbox inline">
										<input type="radio" name="isReady"  ${require.isReady==1?"checked='checked'":"" }  value="1"> 是
										  </label>
								   <label class="checkbox inline">
										<input type="radio" name="isReady" ${require.isReady==0?"checked='checked'":"" } value="0"> 否
										  </label>
										  <c:if test="${!empty require.isReadyBak }">
								  	<span class="label label-important">客户更改为：${require.isReadyBak=="0"?"否":"是" }</span>
								  </c:if>
									
								</div>
							</div> 
							
							<div class="control-group" id="readyDateParent" style="${require.isReady==0?"visibility:visible":"" }">
							  <label class="control-label" for="readyDate">交房时间</label>
							 <div class="controls">
									<input id="readyDate" class="datepicker" value='<fmt:formatDate value="${require.readyDate }" pattern="yyyy-MM-dd"/>' placeholder="请选择交房时间" type="text">
									 <c:if test="${!empty require.readyDateBak }">
								  	<span class="label label-important">客户更改为：<fmt:formatDate value="${require.readyDateBak }" pattern="yyyy-MM-dd"/></span>
								  </c:if>
								</div>
							</div>
							
							<div class="control-group">
							  <label class="control-label" for="houseLocation">房屋地址</label>
							 <div class="controls"> 
								  <div class="input-prepend">
									<input id="houseLocation" maxlength="50" value="${require.houseLocation }" placeholder="请输入房屋具体地址"  type="text">
								  </div>
								   <c:if test="${!empty require.houseLocationBak }">
								  	<span class="label label-important">客户更改为：${require.houseLocationBak }</span>
								  </c:if>
								</div>
							</div>
							  
							  <div class="control-group">
								<label class="control-label" for="designTime">量房时间约定</label>
								<div class="controls">
								  <div class="input-prepend">
									<input id="designTime" maxlength="50" value="${require.designTime }" type="text" placeholder="请输入量房时间">
								  </div>
								  
								  <c:if test="${!empty require.designTimeBak }">
								  	<span class="label label-important">客户更改为：${require.designTimeBak }</span>
								  </c:if>
								  
								</div>
							  </div>
							  
							    <div class="control-group">
								<label class="control-label" for="phoneTime">电话预约约定</label>
								<div class="controls">
								  <div class="input-prepend">
									<input id="phoneTime" maxlength="50"  value="${require.phoneTime }" type="text" placeholder="请输入合适的电话预约时间">
								  </div>
								  
								   <c:if test="${!empty require.phoneTimeBak }">
								  	<span class="label label-important">客户更改为：${require.phoneTimeBak }</span>
								  </c:if>
								  
								</div>
							  </div>
							  
							  <div class="control-group">
								<label class="control-label" for="designType">装修方式</label>
								<div class="controls">
								  <div class="input-prepend">
									<input id="designType" maxlength="200"  value="${require.designType }" type="text" placeholder="请输入装修方式">
								  </div>
								  
								   <c:if test="${!empty require.designTypeBak }">
								  	<span class="label label-important">客户更改为：${require.designTypeBak }</span>
								  </c:if>
								  
								</div>
							  </div>
							  <div class="control-group">
								<label class="control-label" for="designStyle">装修风格</label>
								<div class="controls">
								  <div class="input-prepend">
									<input id="designStyle" maxlength="200"  value="${require.designStyle }" type="text" placeholder="请输入装修风格">
								  </div>
								  
								   <c:if test="${!empty require.designStyleBak }">
								  	<span class="label label-important">客户更改为：${require.designStyleBak }</span>
								  </c:if>
								  
								</div>
							  </div>
							  
							   <div class="control-group">
								<label class="control-label" for="houseStatus">房屋状态</label>
								<div class="controls">
								  <select name="houseStatus" id="houseStatus" data-rel="houseStatus" bak="${require.houseStatus }">
									  	<option value="毛坯房" ${require.houseStatus=='毛坯房'?"selected='selected'":"" }>毛坯房</option>
									  	<option value="老房翻新" ${require.houseStatus=='老房翻新'?"selected='selected'":"" }>老房翻新</option>
									  	<option value="局部装修" ${require.houseStatus=='局部装修'?"selected='selected'":"" }>局部装修</option>
									  	<option value="工装" ${require.houseStatus=='工装'?"selected='selected'":"" }>工装</option>
								</select>
								   <c:if test="${!empty require.houseStatusBak }">
								  	<span class="label label-important">客户更改为：${require.houseStatusBak }</span>
								  </c:if>
								  
								</div>
							  </div>
							 
							<legend class="help-block"> 客户附加信息</legend> 
							  
							  <div class="control-group">
								<label class="control-label" for="customerTips">客户备注</label>
								<div class="controls">
								  <div class="input-prepend input-append">
									<textarea id="customerTips" class="textarea" maxlength="100" 
									>${require.customerTips }</textarea>
									
									<c:if test="${!empty require.customerTipsBak }">
								  	<p class="help-block" style="color:#EB3C00;">客户更改为：${require.customerTipsBak }</p>
								  </c:if>
								  
								  </div>
								</div>
							  </div>
							  
						<!-- 	  	<legend class="help-block"> 客服提示</legend>
							  	
							  	<div class="control-group">
								<label class="control-label" for="callbackTips">回访备注</label>
								<div class="controls">
								  <div class="input-prepend input-append">
									<textarea id="callbackTips" class="textarea" maxlength="100" 
									></textarea>
									
								  
								  </div>
								</div>
							  </div>
							  
							  <div class="control-group">
								<label class="control-label" for="serviceTips">商户提示</label>
								<div class="controls">
								  <div class="input-prepend input-append">
									<textarea id="serviceTips" class="textarea" maxlength="100" 
									></textarea>
									
								  
								  </div>
								</div>
							  </div>
							   -->
							  <div class="form-actions">
								<button type="button" class="btn btn-primary btn-add-require">确认</button>
								<button class="btn" onclick="history.go(-1)">取消</button>
							  </div>
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
		//更换区域
		$(".changeZone").on("click",function(){
			$("#zoneId").parent("div").show();
			$(this).parents(".input-prepend").remove();
		});
		
		//需求确认操作
		$(".btn-add-require").on("click",function(){
			var requiredId=$("#requiredId").val();
			
			var username=$("#username").val();//客户名字
			var userphone=$("#userphone").val();//客户手机号
			
			var zoneId=$("#orderZoneIds input[name='orderZoneId']:checked").val();//店铺所在区域
			var typeId=$("input[name='typeId']:checked").val();//房型
			var houseDes=$("#houseDes").val();//面积
			var houseInfo=$("#houseInfo").val();//楼盘信息
			var isReady=$("input[name='isReady']:checked").val();//是否交房
			var isNew=$("input[name='isNew']:checked").val();//是否新房
			var readyDate=$("#readyDate").val();//如是否，交房时间。
			var houseLocation=$("#houseLocation").val();//房屋位置
			var designTime=$("#designTime").val();//两房时间约定
			var phoneTime=$("#phoneTime").val();//电话时间约定
			var budget=$("#budget").val();//预算
			
			var customerTips=$("#customerTips").val();//客户备注
			
			var callbackTips=$("#callbackTips").val();//回访备注
			var serviceTips=$("#serviceTips").val();//商户备注
			
			var designType=$("#designType").val();
			var designStyle=$("#designStyle").val();
			var houseStatus=$("#houseStatus").val();
			//数据判断
			if(!username){
				layer.msg("客户名字不能为空");
				return;
			}else if(!userphone){
				layer.msg("客户手机号不能为空");
				return;
			}else if(!$(".changeZone")[0]&&!zoneId){
				layer.msg("请选择房子所在的区域");
				return;
			}else if(!typeId){
				layer.msg("请选择房型");
				return;
			}else if(!houseDes){
				layer.msg("请输入房子面积");
				return;
			}else if(!houseInfo){
				layer.msg("请输入楼盘信息");
				return;
			}else if(!budget){
				layer.msg("请输入预算");
				return;
			}else if(isReady==0&&!readyDate){
				layer.msg("请选择交房时间");
				return;
			}else if(!houseLocation){
				layer.msg("请输入房子具体地址");
				return;
			}else if(!designTime){
				layer.msg("请输入量房时间约定信息");
				return;
			}else if(!phoneTime){
				layer.msg("请输入电话预约信息");
				return;
			}else if(!designType){
				layer.msg("请输入量房类型");
				return;
			}else if(!designStyle){
				layer.msg("请输入量房风格");
				return;
			}else if(!houseStatus){
				layer.msg("请输入房屋状态");
				return;
			}
			
			var param={};
			param.operator="confirm";
			param.requiredId=requiredId;
			param.username=username;
			param.userphone=userphone;
			if(zoneId){
				param.zoneId=zoneId;
			}
			param.typeId=typeId;
			param.houseDes=houseDes;
			
			param.houseInfo=houseInfo;
			param.isReady=isReady;
			
			if(isReady==0){//没有 才填入准备时间
				param.readyDate=readyDate;
			}
			
			param.isNew=isNew;
			param.houseLocation=houseLocation;
			param.designTime=designTime;
			param.phoneTime=phoneTime;
			param.customerTips=customerTips;
			
			param.callbackTips=callbackTips;
			param.serviceTips=serviceTips;
			param.budget=budget;
			
			param.designType=designType;
			param.designStyle=designStyle;
			param.houseStatus=houseStatus;
			
			$(this).attr("disabled","disabled");
			$.post("confirm.html",param,function(json){
				if(json.status==1){
					layer.msg(json.message);
					window.location.href="index.html";
				}else{
					layer.msg(json.message);
				}
				
			},"json");
			
		});	
		
		//区域切换，接单区域级联更换
		$("#zoneId").on("change",function(){
			var zoneId=$(this).val();
			$("#orderZoneIds").empty();
			$("#orderZoneIdsParent").css("visibility","hidden");
			if(zoneId&&zoneId!=0){
				var param={};
				param.operator="findOrderZone";
				param.zoneId=zoneId;
				$.post("confirm.html",param,function(json){
					if(json){
						$("#orderZoneIdsParent").css("visibility","visible");
						$(json).each(function(){
								var $span=$("<span>").append(	$("<input>").attr("type","radio")
										.attr("name","orderZoneId").val(this.zoneId));
								
								var $div=$("<div>").append($span);
								var $label=$("<label>").addClass("radio inline").append($div)
								.append(this.name)	
								$("#orderZoneIds").append($label);
						});
					}
				},"json");
			}
		});
		
		$("input[type='radio'][name='isReady']").on("click",function(){
			var isReady=$("input[name='isReady']:checked").val();
			if(isReady==1){
				$("#readyDateParent").css("visibility","hidden");
			}else{
				$("#readyDateParent").css("visibility","visible");
			}
		});
		
	});
	</script>
</body>
</html>
