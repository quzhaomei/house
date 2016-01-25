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
.logo-pre{border:1px solid #888;width:100px;height:100px;
position:absolute;right:150px;top:50px;}
.controls .checkbox{margin-right:10px;}
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
				 	<li><i class="icon-angle-right"></i><a href="index.html">店铺列表 </a></li>
				 	<li><i class="icon-angle-right"></i><a href="#">添加店铺</a></li>
				</ul>

				<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2><i class="halflings-icon edit"></i><span class="break"></span>新增店铺</h2>
						<div class="box-icon">
							<a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
							<a href="#" class="btn-close"><i class="halflings-icon remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<form class="form-horizontal" action="#" method="post">
						<div class="logo-pre" id="logo-pre"></div>
							<fieldset>
							  <div class="control-group">
								<label class="control-label" for="storeName">店铺名称</label>
								<div class="controls">
								  <div class="input-prepend">
									<input id="storeName" maxlength="20" placeholder="请输入店铺名称" type="text">
								  </div>
								</div>
							  </div>
							    <div class="control-group">
							  <label class="control-label" for="logo">店铺logo</label>
							  <div class="controls" id="logo">
								<input class="input-file uniform_on" name="imgFile" id="logo_file" type="file" accept="image/*">
							  	<input type="hidden" id="logo_path"> 
							  </div>
							</div>   
							 
							  <div class="control-group">
								<label class="control-label" for="zoneId">店铺所处地区</label>
								<div class="controls">
								  <select id="zoneId" data-rel="chosen">
									  <option value="0">请选择店铺区域</option>
									<c:forEach items="${zones }" var="temp">
										<option value="${temp.zoneId }">${temp.name }</option>
									</c:forEach>
								  </select>
								</div>
							  </div>
							  
							   <div class="control-group">
								<label class="control-label">接单区域</label>
								
								<div class="controls" id="orderZoneIds">
								 
								 
								</div>
								
							  </div>
							  
							   <div class="control-group">
								<label class="control-label">接单开关</label>
								<div class="controls">
								  <label class="checkbox inline">
									<input type="radio" name="ch" class="status" value="0" checked="checked"> 关
								  </label>
								  <label class="checkbox inline">
									<input type="radio" name="ch" class="status" value="1"> 开
								  </label>
								</div>
							  </div>
							  
							    <div class="control-group">
								<label class="control-label" for="storePhone">店铺电话</label>
								<div class="controls">
								  <div class="input-prepend">
									<input id="storePhone" maxlength="11"  type="text" placeholder="请输入店铺电话">
								  </div>
								</div>
							  </div>
							 
							 
							    <div class="control-group">
								<label class="control-label" for="keeperId">店铺负责人</label>
								<div class="controls">
								  <select data-rel="chosen" id="keeperId">
									<option value="0">请选择店铺负责人</option>
									<c:forEach items="${keepers }" var="temp">
										<option value="${temp.adminUserId }">${temp.nickname }</option>
									</c:forEach>
								  </select>
								</div>
							  </div>
							  
							  <div class="control-group">
								<label class="control-label" for="storeAddress">店铺详细地址</label>
								<div class="controls">
								  <div class="input-prepend">
									<input id="storeAddress" maxlength="50" type="text" placeholder="请输入店铺详细地址">
								  </div>
								</div>
							  </div>
							   
							
							  <div class="control-group">
								<label class="control-label" for="size">订单控量</label>
								<div class="controls">
								  <div class="input-prepend input-append">
									<input id="size" maxlength="10" placeholder="请输入店铺每月订单控量" type="text">
								  </div>
								</div>
							  </div>
							  
							   <div class="control-group">
								<label class="control-label" for="companyName">公司名称</label>
								<div class="controls">
								  <div class="input-prepend input-append">
									<input id="companyName" maxlength="20" placeholder="请输入公司名字" type="text">
								  </div>
								</div>
							  </div>
							  
							  <div class="control-group">
								<label class="control-label" for="ruleUserName">法人姓名</label>
								<div class="controls">
								  <div class="input-prepend input-append">
									<input id="ruleUserName" maxlength="10" placeholder="请输入公司法人姓名" type="text">
								  </div>
								</div>
							  </div>
							  
							  <div class="control-group">
								<label class="control-label" for="ruleUserPhone">法人电话</label>
								<div class="controls">
								  <div class="input-prepend input-append">
									<input id="ruleUserPhone" maxlength="11" placeholder="请输入公司法人电话" type="text">
								  </div>
								</div>
							  </div>
							  
							  <div class="control-group">
								<label class="control-label" for="callPhone">对接电话</label>
								<div class="controls">
								  <div class="input-prepend input-append">
									<input id="callPhone" maxlength="11" placeholder="请输入对接电话" type="text">
								  </div>
								</div>
							  </div>
							  
							  <div class="control-group">
								<label class="control-label" for="msgPhone">短信电话</label>
								<div class="controls">
								  <div class="input-prepend input-append">
									<input id="msgPhone" maxlength="11" placeholder="请输入短信电话" type="text">
								  </div>
								</div>
							  </div>
							  
							  <div class="form-actions">
								<button type="button" class="btn btn-primary btn-add-store">确认</button>
								<button class="btn">取消</button>
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
		//添加店铺操作
		$(".btn-add-store").on("click",function(){
			var storeName=$("#storeName").val();//店铺名字
			var logo=$("#logo_path").val();//logo
			var zoneId=$("#zoneId").val();//店铺所在区域
			var orderZoneIds=[];//接单区域
			
			$("#orderZoneIds input[name='orderZoneId']:checked").each(function(){
				orderZoneIds.push(this.value);
			});
			var status=$(".status:checked").val();//接单状态
			var storePhone=$("#storePhone").val();//店铺电话
			var keeperId=$("#keeperId").val();
			var storeAddress=$("#storeAddress").val();
			var size=$("#size").val();
			var companyName=$("#companyName").val();
			var ruleUserName=$("#ruleUserName").val();
			var ruleUserPhone=$("#ruleUserPhone").val();
			var callPhone=$("#callPhone").val();
			var msgPhone=$("#msgPhone").val();
			//数据判断
			if(!storeName){
				layer.msg("店铺名字不能为空");
				return;
			}else if(!logo){
				layer.msg("请上传店铺logo");
				return;
			}else if(!zoneId||zoneId==0){
				layer.msg("请选择店铺所在区域");
				return;
			}else if(!orderZoneIds){
				layer.msg("请选择店铺的接单区域状态");
				return;
			}else if(!status){
				layer.msg("请选择接单状态");
				return;
			}else if(!storePhone||!storePhone.match(/^\d+$/)){
				layer.msg("请输入店铺电话");
				return;
			}else if(!keeperId||keeperId==0){
				layer.msg("请选择店铺负责人！");
				return;
			}else if(!storeAddress){
				layer.msg("请输入店铺详细地址！");
				return;
			}else if(!size){
				layer.msg("请输入店铺每月接单量");
				return;
			}else if(!companyName){
				layer.msg("请输入公司名字");
				return;
			}else if(!ruleUserPhone){
				layer.msg("请输入公司法人电话");
				return;
			}else if(!ruleUserName){
				layer.msg("请输入公司法人名字");
				return;
			}else if(!callPhone){
				layer.msg("请输入对接电话");
				return;
			}else if(!msgPhone){
				layer.msg("请输入短信电话");
				return;
			}
			var param={};
			param.operator="add";
			param.storeName=storeName;
			param.logo=logo;
			param.zoneId=zoneId;
			param.orderZoneIds=orderZoneIds;
			param.status=status;
			param.storePhone=storePhone;
			param.keeperId=keeperId;
			param.storeAddress=storeAddress;
			param.size=size;
			param.companyName=companyName;
			param.ruleUserName=ruleUserName;
			param.ruleUserPhone=ruleUserPhone;
			param.msgPhone=msgPhone;
			param.callPhone=callPhone;
			
			$(this).attr("disabled","disabled");
			$.post("add.html",param,function(json){
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
			
			if(zoneId&&zoneId!=0){
				var param={};
				param.operator="findOrderZone";
				param.zoneId=zoneId;
				$.post("add.html",param,function(json){
					if(json){
						$("#orderZoneIds").css("color","green");
						$(json).each(function(){
							
								var $span=$("<span>").append(	$("<input>").attr("type","checkbox")
										.attr("name","orderZoneId").val(this.zoneId));
								
								var $div=$("<div>").append($span);
								var $label=$("<label>").addClass("checkbox inline").append($div)
								.append(this.name)	
								$("#orderZoneIds").append($label);
						});
					}
				},"json");
			}
		});
		
		
		$("#logo").on("change", "#logo_file", function() {
			var file = this.files[0];
			if (file.type && !file.type.match(/image.*/)) {// 如果支持type，验证图片
				layer.msg("请选择图片文件");
				return;
			}
			if (!(file.type)) {// 不支持type，则进行后缀名匹配
				if (!checkName(_this.value)) {
					layer.msg("请选择图片文件");
					return;
				}
			}
			$.ajaxFileUpload({
				url : '../welcome/uploadImg.html',// 处理图片脚本
				secureuri : false,
				fileElementId : 'logo_file',// file控件id
				dataType : 'json',
				success : function(data, status) {
					if (data.error == 1) {
						layer.msg(data.message);
						$("#logo_path").value = "";
						$("#logo_file").files = "";
						$("#logo-pre img").remove();// 移除预览图片
					} else {
						if($("#logo-pre img")[0]){
							$("#logo-pre img").attr("src",data.url);
						}else{
							$("#logo-pre").append(
									$("<img>").attr("src",data.url).
									css({"width":"100px","height":"100px"})
							);
						}
						$("#logo_path").val(data.url);
					}
				},
				error : function(data, status, e) {
					alert(e);
				}
			});
		});
		
	});
	</script>
</body>
</html>
