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
.help-block{font-weight:bold;font-size:15px;}
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
				 	<li><i class="icon-angle-right"></i><a href="#">更正预约需求</a></li>
				</ul>

				<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon edit"></i><span class="break"></span>更正预约</h2>
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
									<input id="username" maxlength="20" value="${require.username }" placeholder="请输入客户姓名" type="text">
								  </div>
								</div>
							  </div>
							
							    <div class="control-group">
							  <label class="control-label" for="userphone">客户手机号</label>
							 <div class="controls">
								  <div class="input-prepend">
									<input id="userphone" maxlength="11" value="${require.userphone }" placeholder="请输入客户手机号" type="text">
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
								</div>
							  </div>
							  
							   <div class="control-group" id="orderZoneIdsParent">
								<label class="control-label">具体区域</label>
								<div class="controls" id="orderZoneIds">
								 	
								</div>
							  </div>
							
							   <div class="control-group">
							  <label class="control-label" for="typeId">房型面积</label>
							 <div class="controls">
								  	<c:forEach items="${houses }" var="house">
								  		 <label class="radio inline">
										<input type="radio" name="typeId" ${require.houseType.typeId==house.typeId?"checked='checked'":"" } value="${house.typeId }"> ${house.name }
										  </label>
								  	</c:forEach>
								</div>
							</div> 
							
							
							   <div class="control-group">
								  <label class="control-label" for="houseDes">房型描述</label>
								 <div class="controls">
								  <div class="input-prepend">
									<input id="houseDes" maxlength="10" value="${require.houseDes }" placeholder="请输入房子面积 / 整数" type="text"> 
								  </div>
								</div>
							</div> 
							<div class="control-group">
								  <label class="control-label" for="budget">预算</label>
								 <div class="controls">
								  <div class="input-prepend">
									<input id="budget" maxlength="10" value="${require.budget }" placeholder="装修预算" type="text"> 
								  <span class="add-on">￥</span>
								  </div>
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
									
								</div>
							</div> 
							
							<div class="control-group">
							  <label class="control-label" for="houseInfo">楼盘信息</label>
							 <div class="controls">
								  <div class="input-prepend">
									<input id="houseInfo" maxlength="20" value="${require.houseInfo }" placeholder="小区名字，路口，商区等信息" type="text">
								  </div>
								</div>
							</div> 
							
							<div class="control-group">
							  <label class="control-label" for="isReady">交房情况</label>
							 <div class="controls">
							 <label class="checkbox inline">
										<input type="radio" name="isReady"  ${require.isReady==1?"checked='checked'":"" }  value="1"> 已交房
										  </label>
								   <label class="checkbox inline">
										<input type="radio" name="isReady" ${require.isReady==0?"checked='checked'":"" } value="0"> 未交房
										  </label>
									
								</div>
							</div> 
							
							<div class="control-group" id="readyDateParent" style="${require.isReady==0?"visibility:visible":"" }">
							  <label class="control-label" for="readyDate">交房时间</label>
							 <div class="controls">
									<input id="readyDate" class="datepicker" value='<fmt:formatDate value="${require.readyDate }" pattern="yyyy-MM-dd"/>' placeholder="请选择交房时间" type="text">
									
								</div>
							</div>
							
							<div class="control-group">
							  <label class="control-label" for="houseLocation">房屋地址</label>
							 <div class="controls"> 
								  <div class="input-prepend">
									<input id="houseLocation" maxlength="50" value="${require.houseLocation }" placeholder="请输入房屋具体地址"  type="text">
								  </div>
								</div>
							</div>
							  
							  <div class="control-group">
								<label class="control-label" for="designTime">量房时间约定</label>
								<div class="controls">
								  <div class="input-prepend">
									<input id="designTime" maxlength="50" value="${require.designTime }" type="text" placeholder="请输入量房时间">
								  </div>
								</div>
							  </div>
							  
							    <div class="control-group">
								<label class="control-label" for="phoneTime">电话预约约定</label>
								<div class="controls">
								  <div class="input-prepend">
									<input id="phoneTime" maxlength="50"  value="${require.phoneTime }" type="text" placeholder="请输入合适的电话预约时间">
								  </div>
								</div>
							  </div>
							 
							<legend class="help-block"> 客户附加信息</legend> 
							  
							  <div class="control-group">
								<label class="control-label" for="customerTips">客户备注</label>
								<div class="controls">
								  <div class="input-prepend input-append">
									<textarea id="customerTips" class="textarea" maxlength="100" 
									>${require.customerTips }</textarea>
								  </div>
								</div>
							  </div>
							  
							  <div class="form-actions">
								<button type="button" class="btn btn-primary btn-add-require">确认</button>
								<button type="button" class="btn" onclick="history.go(-1)">取消</button>
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
		
		//添加预约需求操作
		$(".btn-add-require").on("click",function(){
			var requiredId=$("#requiredId").val();
			
			var username=$("#username").val();//客户名字
			var userphone=$("#userphone").val();//客户手机号
			
			var zoneId=$("#orderZoneIds input[name='orderZoneId']:checked").val();//店铺所在区域
			var typeId=$("input[name='typeId']:checked").val();//房型
			var houseDes=$("#houseDes").val();//房型描述
			var houseInfo=$("#houseInfo").val();//楼盘信息
			var isReady=$("input[name='isReady']:checked").val();//是否交房
			var isNew=$("input[name='isNew']:checked").val();//是否新房
			var readyDate=$("#readyDate").val();//如是否，交房时间。
			var houseLocation=$("#houseLocation").val();//房屋位置
			var designTime=$("#designTime").val();//两房时间约定
			var phoneTime=$("#phoneTime").val();//电话时间约定
			var budget=$("#budget").val();
			
			var customerTips=$("#customerTips").val();//客户备注
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
				layer.msg("请选择房型面积");
				return;
			}else if(!houseDes){
				layer.msg("请输入房型描述");
				return;
			}else if(!budget){
				layer.msg("请输入预算");
				return;
			}else if(!budget.match(/^\d+$/)){
				layer.msg("预算为一个数字");
				return;
			}else if(!houseInfo){
				layer.msg("请输入楼盘信息");
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
			}
			
			var param={};
			param.operator="update";
			param.requiredId=requiredId;
			param.username=username;
			param.userphone=userphone;
			if(zoneId){
				param.zoneId=zoneId;
			}
			param.typeId=typeId;
			param.houseDes=houseDes;
			param.budget=budget;
			param.houseInfo=houseInfo;
			param.isReady=isReady;
			
			if(isReady==0){//没有 才填入准备时间
				param.readyDate=$.trim(readyDate);
			}
			
			param.isNew=isNew;
			param.houseLocation=houseLocation;
			param.designTime=designTime;
			param.phoneTime=phoneTime;
			param.customerTips=customerTips;
			
			$(this).attr("disabled","disabled");
			$.post("update.html",param,function(json){
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
				$.post("update.html",param,function(json){
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
