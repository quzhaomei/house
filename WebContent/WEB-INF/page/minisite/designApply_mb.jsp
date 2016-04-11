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

	}

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
	.formarea {
		position: fixed;
		bottom: 0px;
		left: 0px;
		right: 0px;
		background-color: rgba(0,0,0,0.85);
		min-height: 30px;
		padding: 15px;
	}

	input[type=text] {
		background-color: #B81335;
		border: 0px;
		padding: 8px;
		font-size: 16px;
		color: white;
		border-radius: 4px;
		margin-bottom: 6px;
			
		display: block;
		width: 95%;

	}
	input[type=text]:last-child {
		margin-bottom: 0px;
	}

	.inputs {
		width: 73%;
		display: inline-block;
		vertical-align: middle;
	}
	button {
		width: 26%;
		display: inline-block;
		border: 0px;
		height: 75px;
		background: #B81335;
		font-size: 20px;
		vertical-align: middle;
		color: #F8AC00;
		font-weight: 500;
		border-radius: 4px;
		padding: 5px 15px;
		float: right;
		margin: 0px;
	}

	::-webkit-input-placeholder {
		color: white;

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
		<img src="../images/mobile_01.png">
		<img src="../images/mobile_02.png">
		<img src="../images/mobile_03.png">
		<img src="../images/mobile_04.jpg">
		<img src="../images/mobile_05.jpg">
		<img src="../images/mobile_06.png">
		<form class="formarea">
			<div class="inputs">
				<input type="text" placeholder="您的姓名" id="username" maxlength="30">
				<input type="text" placeholder="您的手机号" id="userphone" maxlength="30">
			</div>
			<button type="submit" id="subBtn">申请<br/>报名</button>

		</form>
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
	if(!username){
		alert("请输入您的姓名");
		return;
	}else if(!userphone){
		alert("请输入您的联系电话");
		return;
	}else if(!userphone.match(/^\d{11}$/)){
		alert("请输入正确的联系电话,只包含数字");
		return;
	}
	
	var param={};
	param.username=username;
	param.userphone=userphone;
	$(this).attr("disabled","disabled");
	$.post("designApply.html",param,function(json){
		if(json.status==1){
			$(".success").show();
		}else{
			alert(json.message);
		}
			$(".subBtn").removeAttr("disabled");
	},"json");
});
</script>
</html>