<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>量房信息确认</title>
     <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
     <link rel="apple-touch-icon" href="apple-touch-icon.png">
     <link rel="stylesheet" href="css/fonts.css">
     <link rel="stylesheet" href="css/flaticon.css">
     <link rel="stylesheet" href="css/main.css">
      <link rel="stylesheet" href="js/jquery-ui.css">
      <style>
      .ui-datepicker-title select{display:inline-block;}
      </style>
      
</head>
<body class="bg_white with_bottombar measure" id="apply">
	
		<header>
			<a href="http://51getmore.cn"><img src="images/measure/head.png"></a>
		</header>
	

	
		<div class="formarea pad_20  apply withicon">
			<fieldset class="b_info">
			<c:choose>
				<c:when test="${require.status==1||require.status==2 }">
					<h1>
					请核对您的量房信息
					<div class="notice">
					确认无误后请您点击“确认提交”，如信息有误，您可以在此修改后再确认，我们会更新您的信息，并在最短时间内为您安排上门量房。
					</div>
					</h1>
				</c:when>
				<c:when test="${require.status==3 }">
					<h1>
					信息正在审核中…
					<div class="notice">
					您修改的信息，正由后台人员审核中！
					</div>
					</h1>
				</c:when>
				<c:when test="${require.status>=4 }">
					<h1>
					确认成功！
					<div class="notice">
					您的需求预约已经确认成功！请等待工作人员派单。
					</div>
					</h1>
				</c:when>
			</c:choose>
			
			
			

			<div class="form_row">
				<i class="icon-user"></i>
				<input type="text" maxlength="20" readonly="readonly" 
				 data-rel="username" name="name" value="${require.username }" placeholder="您的姓名" bak="${require.username }">
			</div>
		
			
			<div class="form_row">
				<i class="icon-iphone"></i>
				<input type="text" maxlength="11" readonly="readonly" data-rel="userphone" name="tel"
				 placeholder="手机号" value="${require.userphone }" bak="${require.userphone }">
			</div>

			
			<div class="form_row" >
				<i class="icon-home"></i>
				<input type="text" name="estate"  value="${require.houseInfo }" placeholder="楼盘信息"  data-rel="houseInfo" bak="${require.houseInfo }">
			</div>

			<div class="form_row twocol">
				<i class="icon-map"></i>
				<select name="city" id="city">
					<c:forEach items="${zones }" var="zone">
						<option value="${zone.zoneId }" ${parentId==zone.zoneId?"selected='selected'":"" }>${zone.name }</option>
					</c:forEach>
				</select>
				<select name="area" id="area" data-rel="zoneId" bak="${require.zone.zoneId }"  class="right">
					<c:forEach items="${children }" var="temp">
						<option value="${temp.zoneId }" ${require.zone.zoneId==temp.zoneId?"selected='selected'":"" }>${temp.name }</option>
					</c:forEach>
				</select>
			</div>

			


			<div class="form_row">
				<input type="text" name="address"  data-rel="houseLocation" id="address" value="${require.houseLocation }"placeholder="房屋详细地址" bak="${require.houseLocation }">
			</div>

			<div class="form_row twocol">
				<i class="icon-box"></i>
				<select name="type" id="type" data-rel="type" bak="${require.houseType.typeId }">
				<c:forEach items="${types }" var="type">
					<option value="${type.typeId }" ${type.typeId==require.houseType.typeId?
					"selected='selected'":"" }>${type.name }</option>
				</c:forEach>
				</select>
				
				<i class="icon-star right"></i>
				<select class="right" name="status" id="isNew" data-rel="isNew" bak="${require.isNew }">
				<option value="-1">是否新房</option>
				<option value="0" ${require.isNew==0?"selected='selected'":"" }>旧房</option>
				<option value="1" ${require.isNew==1?"selected='selected'":"" }>新房</option>
				</select>


			</div>
			
			<div class="form_row">
				<i class="icon-calendar"></i>
				
				<input type="text"  name="square" data-rel="houseDes" placeholder="房型描述" value="${require.houseDes }" bak="${require.houseDes }">
			</div>
			


			<div class="form_row">
				<i class="icon-coin-yen2"></i>
				<input type="text" name="square" data-rel="budget" id="budget" value="${require.budget }" placeholder="预算" bak="${require.budget }">
			</div>

			<div class="form_row">
				<i class="icon-clock2"></i>
				<select name="housetime" id="isReady" data-rel="isReady" bak="${require.isReady }">
				<option value="-1">交房情况</option>
				<option value="1" ${require.isReady==1?"selected='selected'":"" }>已拿房</option>
				<option value="0" ${require.isReady==0?"selected='selected'":"" }>未拿房</option>
				</select>
			</div>

			<div class="form_row" id="readyTime" style="${require.isReady==1?"display:none;":""}">
				<i class="icon-calendar"></i>
				
				<input type="text" placeholder="交房时间" name="square" data-rel="readyDate" bak="<fmt:formatDate value="${require.readyDate }" pattern="yyyy-MM-dd"/>" class="datepicker" value="<fmt:formatDate value="${require.readyDate }" pattern="yyyy-MM-dd"/>" >
			</div>

			<div class="form_row">
				<i class="icon-paint-format"></i>
				<select name="houseStatus" id="houseStatus" data-rel="houseStatus" bak="${require.houseStatus }">
										<option value="-1" >房屋状态</option>
									  	<option value="毛坯房" ${require.houseStatus=='毛坯房'?"selected='selected'":"" }>毛坯房</option>
									  	<option value="老房翻新" ${require.houseStatus=='老房翻新'?"selected='selected'":"" }>老房翻新</option>
									  	<option value="局部装修" ${require.houseStatus=='局部装修'?"selected='selected'":"" }>局部装修</option>
									  	<option value="工装" ${require.houseStatus=='工装'?"selected='selected'":"" }>工装</option>
				</select>
			</div>
			
			<div class="form_row">
			<label for="calltime">电话预约时间: </label>
				<textarea name="calltime" id="calltime" data-rel="phoneTimeBak" bak="${fn:escapeXml(require.phoneTime) }"
				rows="3">${require.phoneTime }</textarea>
			</div>
			
			<div class="form_row">
			<label for="calltime">量房时间: </label>
				<textarea name="calltime" id="calltime" data-rel="designTime" bak="${fn:escapeXml(require.designTime) }"
				rows="3">${require.designTime }</textarea>
			</div>
			
			
			<div class="form_row">
			<label for="calltime">装修方式: </label>
				<textarea name="calltime" id="calltime" data-rel="designType" bak="${fn:escapeXml(require.designType) }"
				rows="3">${require.designType }</textarea>
			</div>
			<div class="form_row">
			<label for="calltime">装修风格: </label>
				<textarea name="calltime" id="calltime" data-rel="designStyle" bak="${fn:escapeXml(require.designStyle) }"
				rows="3">${require.designStyle }</textarea>
			</div>
			
			

			<div class="form_row">
			<label for="calltime">客户留言: </label>
				<textarea name="comments" id="comments" data-rel="customerTips" bak="${fn:escapeXml(require.customerTips) }"
				rows="7">${require.customerTips }</textarea>
			</div>

			</fieldset>


		
			

				
				<div class="notice">我们承诺：凯特猫家装提供该项免费服务，绝不产生任何费用，为了您的利益和我们的口碑，您的隐私我们将严格保密。</div>
				
		<c:if test="${require.status<3 }">
			<div class="bottombar bg_black">
				 <button type="button" id="sendResult" class="bttn_full">确认并提交量房信息</button>
			</div>
		</c:if>
		</div>



			<div class="brandlist pad_10">
			<fieldset class="b_info">

			<h1>
			上海百强装修公司上门服务
			<span class="eng">
				报价透明  设计优秀  项目全程保障 
 
			</span>
			</h1>
			<div class="contents row">
					<div class="col-3"> <img src="images/measure/01.jpg"> </div>
					<div class="col-3"><img src="images/measure/02.jpg"></div>
					<div class="col-3"><img src="images/measure/03.jpg"></div>
					<div class="col-3"><img src="images/measure/04.jpg"></div>
					<div class="col-3"><img src="images/measure/05.jpg"></div>
					<div class="col-3"><img src="images/measure/06.jpg"></div>
					<div class="col-3"><img src="images/measure/07.jpg"></div>
			</div>
			</fieldset>
		</div>



<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
 
<script type="text/javascript">
$(function(){
	$("#city").on("change",function(){
		var parentId=$(this).val();
		var param={};
		param.parentId=parentId;
		$.post("findZones.html",param,function(json){
			if(json.status==1){
				$("#area").empty();
				
				$(json.data).each(function(){
					$("#area").append(new Option(this.name,this.zoneId));
				});
			}
			
		},"json");
	});
	
	$("#isReady").on("change",function(){
		var isReady=$(this).val();
		if(isReady==1){
			$("#readyTime").hide();
		}else{
			$("#readyTime").show();
		}
	});
	
	 $.datepicker.regional['zh-CN'] ={
			 clearText: '清除', clearStatus: '清除已选日期',  
			 closeText: '关闭', closeStatus: '不改变当前选择',  
			 prevText: '<上月', prevStatus: '显示上月',  
			 nextText: '下月>', nextStatus: '显示下月',  
			 currentText: '今天', currentStatus: '显示本月',  
			 monthNames: ['一月','二月','三月','四月','五月','六月',  
			 '七月','八月','九月','十月','十一月','十二月'],  
			 monthNamesShort: ['一','二','三','四','五','六',  
			 '七','八','九','十','十一','十二'],  
			 monthStatus: '选择月份', yearStatus: '选择年份',  
			 weekHeader: '周', weekStatus: '年内周次',  
			 dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
			 dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
			 dayNamesMin: ['日','一','二','三','四','五','六'],  
			 dayStatus: '设置 DD 为一周起始', dateStatus: '选择 m月 d日, DD',  
			 dateFormat: 'yy-mm-dd', firstDay: 1,  
			 initStatus: '请选择日期', isRTL: false  }
		 $.datepicker.setDefaults($.datepicker.regional['zh-CN']);  

		$('.datepicker').datepicker({changeYear:true,changeMonth:true});
		
		
	$("#sendResult").on("click",function(){
			var param={};
		$("[data-rel]").each(function(){
			
			param.userphone="${userphone}";
			param.requiredId="${requiredId}";
			param.sign="${sign}";
			
			var name=$(this).attr("data-rel");
			var value=$(this).val();
			var bak=$(this).attr("bak");
			if(value&&value!=bak){
				param[name]=value;
				param.operator="change";//代表改变了
			}
		});
			if($("#isNew").val()=="-1"){alert("请选择是否新房");return;}
			if($("#houseStatus").val()=="-1"){alert("请选择房屋状态");return;}
			if($("#isReady").val()=="-1"){alert("请选择交房情况");return;}
			if($("#isReady").val()=="0"&&!$("#readyTime input").val()){alert("请选择交房时间");return;}
		if(confirm("是否确认提交？")){
			$.post("requireUpdate.html",param,function(data){
				if(data.status==1){
					alert("确认成功！");
					window.location.reload();
				}else{
					alert(data.message);
				}
			},"json");
		}
	})
});
</script>
</body>
</html>