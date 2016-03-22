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
.tip-title{margin:5px 5px;color:#888;}
.tip-title label{margin:5px 5px;pading:0 20px;color:#F14500;}
.tr-footer span.btn{margin:5px 30px;}
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
					<li><i class="icon-angle-right"></i> <a href="index.html">发布需求</a></li>
					<li><i class="icon-angle-right"></i> <a href="#">批量上传 </a></li>
				</ul>

				<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2>
								<i class="halflings-icon user"></i><span class="break"></span>批量上传
							</h2>
							<div class="box-icon">
									 <a href="#"
									class="btn-minimize"><i class="halflings-icon chevron-up"></i>
								</a> <a href="#" class="btn-close"><i
									class="halflings-icon remove"></i> </a>
							</div>
					</div>
					<div class="box-content">
					<div class="tip-title">＊ 请先 <a href="../mini_2/xxx.xls" ><i class="icon icon-download"></i>  下载模版</a>，然后把数据填入模版中，再进行导入操作！ </div>
					<div class="tip-title">
					
					<form ></form>
					<label for="required-xls">
						<i class="icon icon-upload"></i> 上传数据
						<input id="required-xls" type="file" name="excell" style="display:none;"/>
					</label>
					</div>
					<div style="text-align:center;">
						<table class="table table-striped table-bordered bootstrap-datatable" style="width:70%;margin:0 0px 0 50px;">
						  <thead>
							  <tr>
								  <th width=10%>序列</th>
								  <th >用户姓名</th>
								  <th >用户号码</th>
							  </tr>
						  </thead>   
						  <tbody id="filedate">
						  	
						  </tbody>
					  </table>   
					  </div>
					  
									 
					           
					</div>
				</div><!--/span-->
			
			</div><!--/row-->
			</div>
			
		</div>
	</div>
	
	<div class="clearfix"></div>
	<c:import url="public/p-footer.jsp"></c:import>
	<c:import url="public/p-javascript.jsp"></c:import>
		<script type="text/javascript" src="../js/ajaxfileupload.js" charset="utf-8"></script>
	<script type="text/javascript">
	$(function(){
		$("#content").on("change", "#required-xls", function() {
			var file = this.files[0];
			if (!file) {// 如果支持type，验证图片
				layer.msg("请选择xls文件");
				return;
			}
			$.ajaxFileUpload({
				url : 'batchUpload.html',// 处理图片脚本
				secureuri : false,
				fileElementId : 'required-xls',// file控件id
				dataType : 'json',
				success : function(data, status) {
					var index=0;
					if(data.status==1){
						$(data.data).each(function(){
							index++;
							var $tr=$("<tr class='table-data'>");
							var $tdindex=$("<td>");
							var $tdname=$("<td>");
							var $tdphone=$("<td>");
							$tdname.append($tdindex.text(index));
							$tdname.append($("<input>").attr("type","text").attr("name","username").val(this.username));
							$tdphone.append($("<input>").attr("type","text").attr("name","userphone").val(this.userphone).attr("maxLength","11"));
							$tr.append($tdindex).append($tdname).append($tdphone);
							$("#filedate").append($tr);
						});
						$("#filedate").append("<tr class='tr-footer'><td>操作</td><td colspan='2'><span class='btn btn-success btn-submit'>确定</span><span class='btn btn-danger btn-cancel'>清空</span></td></tr>");
					}else{
						layer.msg(data.message);
						window.location.href="index.html";
					}
				},
				error : function(data, status, e) {
					alert(e);
				}
			});
		});
		
		$("#content").on("click",".btn-cancel",function(){
			$("#filedate").empty();
		});
		
		$("#content").on("click",".btn-submit",function(){
			var myForm = new FormData();
			myForm.append("operator","upload");
			if($("tr.table-data")[0]){
				$("tr.table-data").each(function(){
					$(this).find("input").each(function(){
						myForm.append($(this).attr("name"),$(this).val());
					});
			});
				
			$.ajax({
					url: "batchUpload.html",
					type: "POST",
					data: myForm,
					processData: false,  // 告诉jQuery不要去处理发送的数据
					contentType: false ,  // 告诉jQuery不要去设置Content-Type请求头
					dataType:"json",
					success:function(json){
						  if(json.status=="1"){
							  layer.msg(json.message);
							  window.location.href="index.html";
						  }else{
							  layer.msg(json.message);
						  }
					}
				});
				
			}else{
				layer.msg("请先导入数据");
				return;
			}
			
		});
	});
	</script>
</body>
</html>
