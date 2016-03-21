<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>凯特猫免费量房 家装设计节</title>
</head>
<style>
	body {
		padding: 0px;
		margin: 0px;
		padding-bottom: 105px;
		background-color: #B81335;
	}

	* {font-family:"Lantinghei SC","Open Sans",Arial,"Hiragino Sans GB","Microsoft YaHei","微软雅黑","STHeiti","WenQuanYi Micro Hei",SimSun,sans-serif;}

	*:focus{
		outline: none;
	}
	img {
		width: 100%;
		display: block;
	}

	* {
		box-sizing: border-box;
	}

	.container {
		width: 900px;
		margin: auto;
		position: relative;
	}
	.formarea {
		position: absolute;
		top: 505px;
		right: 90px;
		width: 270px;
	}

	input[type=text] {
		border: 2px solid #B81335;
		padding: 8px;
		font-size: 16px;
		color: black;
		margin-bottom: 10px;
			
		display: block;
		width: 100%;

	}
	input[type=text]:last-child {
		margin-bottom: 0px;
	}

	.inputs {
		display: block;
		width: 100%;
		margin-bottom: 20px;
	}
	button {
		display: block;
		border: 0px;
		background: #BA0631;
		font-size: 18px;
		vertical-align: middle;
		color: white;
		width: 100%;
		font-weight: 500;
		padding: 10px 15px;
		margin: 0px;
		cursor: pointer;

	}

	button:active {
		background-color: #D35049;
	}
	button span {
		font-size: 14px;
	}

	.select label {
		width: 60px;
		display: inline-block;
		height: 38px;
		font-size: 20px;
		color: #BA0631;
		font-weight: bold;
		letter-spacing: 2px;
		padding-top: 5px;
	}

	select {
		display: inline-block;
		width: 200px;
		border-radius: 0px;
		background-color: white;
		border: 2px solid #BA0631;
		float: right;
		height: 38px;
		line-height: 16px;
		font-size: 16px;
		padding: 9px 15px;
		background-image: url(../images/adown.png);
		background-position: right center;
  		background-size: auto 100%;
  		background-repeat: no-repeat;
  		-webkit-appearance: none;
	}

	::-webkit-input-placeholder {
		color: black;

	}

	.hide {
		display: none;
	}

	.success {
		position: fixed;
		bottom: 0px;
		right: 0px;
		top: 0px;
		left: 0px;
		padding-top: 100px;
		background-image: url(../images/geometry.png);
		background-repeat: repeat;
		background-size: 412px;
		text-align: center;
		line-height: 1.4em;
	}

	.success img {
		width: 100px;
		height: auto;
		margin: auto;
		display: inline-block;
	}
	
	.logo {
		text-align: center;
	}
	.success .info {
		width: 220px;
		display: inline-block;
		margin: auto;

	}
</style>
<body>
	<div class="container">

		<img src="../images/web_01.png">
		<img src="../images/web_02.jpg">
		<img src="../images/web_03.png">
		<img src="../images/web_04.jpg">
		<div class="formarea">
		<div class="inputs">
			<input type="text" placeholder="您的姓名" id="username" maxlength="20">
			<input type="text" placeholder="您的手机号" id="userphone" maxlength="11">
			<div class="select">
				<label for="area">上海</label>
				<select name="area" id="address">
					<option value="0">区域</option>
					<option value="徐汇">徐汇</option>
					<option value="静安">静安</option>
					<option value="黄埔">黄埔</option>
					<option value="卢湾">卢湾</option>
					<option value="长宁">长宁</option>
					<option value="闸北">闸北</option>
					<option value="普陀">普陀</option>
					<option value="虹口">虹口</option>
					<option value="闵行">闵行</option>
					<option value="杨浦">杨浦</option>
					<option value="宝山">宝山</option>
					<option value="浦东">浦东</option>
					<option value="南汇(惠南镇)">南汇(惠南镇)</option>
					<option value="松江">松江</option>
					<option value="嘉定">嘉定</option>
					<option value="青浦(徐泾)">青浦(徐泾)</option>
				</select>
			</div>
		</div>
		<button type="submit" id="subBtn">申请报名<span>（免费获得各项福利）</span></button>

		</div>
	</div>

	<div class="success hide">
		<div class="logo">
			<img src="../images/logo.png">
		</div>
		
		<div class="info">
			<p>您的量房报名信息已成功提交</p>
			<p>我们的工作人员会尽快与您电话联系确认具体量房信息</p>
		</div>
	</div>
</body>
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
$("#subBtn").on("click",function(){
	var username=$("#username").val();
	var userphone=$("#userphone").val();
	var address=$("#address").val();
	if(!username){
		alert("请输入您的姓名");
		return;
	}else if(!userphone){
		alert("请输入您的联系电话");
		return;
	}else if(userphone.length!=11){
		alert("请输入正确的联系电话");
		return;
	}else if(address==0){
		alert("请选择区域!");
		return;
	}
	var param={};
	param.username=username;
	param.userphone=userphone;
	param.address=address;
	$(this).attr("disabled","disabled");
	$.post("designApply.html",param,function(json){
		if(json.status==1){
			$(".success").show();
		}else{
			alert(json.message);
			$("#subBtn").removeAttr("disabled");
		}
	},"json");
});
</script>
</html>