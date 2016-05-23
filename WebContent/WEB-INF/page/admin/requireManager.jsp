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
						<table class="table">
						<tr>
							<td width=8%>用户名称</td><td width=20%><input class="span10" type="text" id="username" value="${param.username }" name="username" maxlength="20" placeholder="请输入用户名称"/></td>
							<td width=8% >用户Id</td><td width=20%><input class="span10" type="text" id="userId" value="${param.userId }" name="userId" maxlength="20" placeholder="请输入用户ID"/></td>
						<c:choose>
							<c:when test="${param.acceptNum!=1}">
								<td width=8%>状态</td><td width=18%>
								<select style="width:80%;" name="status" id="chonsenstatus" rel="chosen">
									<option value="-1" ${param.status=="-1"?"selected='selected'":"" }>全部</option>
								<!--	<option value="0" ${param.status=="0"?"selected='selected'":"" }>发起中</option>
									<option value="1" ${param.status=="1"?"selected='selected'":"" }>短信中</option>
									<option value="2" ${param.status=="2"?"selected='selected'":"" }>客户打开连接</option>
									<option value="3" ${param.status=="3"?"selected='selected'":"" }>客户修改提交</option>
									<option value="4" ${param.status=="4"?"selected='selected'":"" }>待发布</option>  -->
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
							<ad:power uri="../requireManager/loadAll.html">
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
							<td>分单时间</td>
							<td>
							<input type="text" name="serviceStartDate" id="serviceStartDate" value='${param.serviceStartDate }' class="datepicker span5" />
									-
							<input type="text" name="serviceEndDate" id="serviceEndDate" value='${param.serviceEndDate }' class="datepicker span5"/>
							</td><td colspan="3"><span class="add-on">
							<button type="button" class="btn btn-small btn-search">
							<i class="halflings-icon search white"></i> 查询</button>
							</span></td>
							
						</tr>
						
						<tr class="other-info">
						
							<td >退单/入库 时间</td>
							<td>
							<input type="text" name="startFileTime" id="startFileTime"value='${param.startFileTime }' class="datepicker span5" />
									-
							<input type="text" name="endFileTime" id="endFileTime" value='${param.endFileTime }' class="datepicker span5"/>
							</td>
							<td >退单/入库 原因</td>
							<td><input type="text" name="callbackTips" id="callbackTips" value='${param.callbackTips }' class="span12" maxlength="200" /> </td>
							
							<td >下次回访时间</td>
							<td>
							<input type="text" name="startNextCallTime" id="startNextCallTime"value='${param.startNextCallTime }' class="datepicker span5" />
									-
							<input type="text" name="endNextCallTime" id="endNextCallTime" value='${param.endNextCallTime }' class="datepicker span5"/>
							</td>
							<td>&nbsp;
							<input type="hidden" name="acceptNum" id="acceptNum" value='${param.acceptNum }' />
							</td>
						</tr>
						
						</table>
						</form>
						
						<table class="table table-striped table-bordered bootstrap-datatable">
						<caption style="text-align:left;">
						条件筛选：
						<c:choose>
							<c:when test="${param.acceptNum==1 }">
								<a href="#" class="chooseOrder" order="0" style="color:red;"><i class="icon icon-search"></i> 查看全部</a>&nbsp;
								<span>只看已接单</span>
							</c:when>
							<c:otherwise>
								<span>查看全部</span>&nbsp;
								<a href="#" class="chooseOrder" order="1" style="color:red; "><i class="icon icon-search"></i>  只看已接单</a>
							</c:otherwise>
						</c:choose>
						
						</caption>
						  <thead>
						  
							  <tr>
								  <th width=5% rowspan="2">序列</th>
								  <th width=11% rowspan="2">录入时间</th>
								  <th width=8% rowspan="2">地区</th>
								  <th width=5% rowspan="2">用户ID </th>
								  <th width=8% rowspan="2">用户名称</th>
								  <th width=8% rowspan="2">用户手机</th>
								  <th width=11% rowspan="2">分单时间</th>
								  <th width=10% colspan="2" style="text-align:center;">订单统计</th>
								  <th width=10% colspan="2" style="text-align:center;">礼包配送</th>
								  <th width=8% rowspan="2">状态</th>
								  <th rowspan="2">操作</th>
							  </tr>
						 	 <tr>
						 	 	<td>已派数</td><td>已接数</td>
						 	 	<td>成功数</td><td>礼包</td>
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
						  		<ad:phone phone="${temp.userphone}" roleId="<%=new int[]{1,2} %>"/>
						  		
						  		</td>
						  		<td><fmt:formatDate value="${temp.serviceDate }" pattern="yyyy-MM-dd HH:mm"/></td>
						  		
						  		<td><span style="color:${temp.orderCount==0?'red':'' }">${temp.orderCount }</span></td><!-- 已派单数 -->
						  		<td><span style="color:${temp.acceptNum==0?'red':'' }">${temp.acceptNum }</span></td><!-- 已接单数 -->
						  		<td><span style="color:${temp.successNum==0?'red':'' }">${temp.successNum }</span></td><!-- 成功数 -->
						  		<td>
						  		<c:choose>
									<c:when test="${empty temp.gift.giftId }">
										未配送
									</c:when>	
									<c:otherwise>
										<span style="color:green;">已配送</span>
									</c:otherwise>
						  		</c:choose>
						  		</td>
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
						  		<td>
						  		<ad:power uri="../requireManager/list.html">
						  		<a class="btn btn-mini" href="list.html?requiredId=${temp.requiredId }">详情</a>
						  		</ad:power>
						  		<c:choose>
						  		
						  			<c:when test="${temp.status==6 }">
						  			<ad:power uri="../requireManager/toService.html">
							  			<a class="btn btn-mini green" href="toService.html?operator=to_service&requiredId=${temp.requiredId }">
							  			去分单</a>
							  			</ad:power>
						  			</c:when>
						  			
						  		</c:choose>
						  		
						  		<c:if test="${(temp.status==6||temp.status==7||temp.status==8)&&(!empty temp.callbackTips) }">
						  		<ad:power uri="../requireManager/toStore.html">
							  			<a class="btn btn-mini yellow" href="toStore.html?operator=to_store&requiredId=${temp.requiredId }">
							  			去派单</a>
							  			</ad:power>
							  		</c:if>
							  		
							  		<c:if test="${temp.successNum>0&&empty temp.gift.giftId}"><!-- 没有配送的可以配送 -->
						  			<ad:power uri="../requireManager/sendGift.html">
							  			<a class="btn btn-mini blue sendGift" href="#" requiredId="${temp.requiredId }">
							  			配送礼包</a>
							  			</ad:power>
							  		</c:if>
							  		<ad:power uri="../requireManager/setPrice.html">
							  		<a class="btn btn-mini green setPrice" href="#" requiredId="${temp.requiredId }">
							  			特殊定价</a>
							  			</ad:power>
							  			
							  		<ad:power uri="../requireManager/update.html">
							  		<a class="btn btn-mini yellow confirmRequire" href="#" requiredId="${temp.requiredId }">
							  			修正需求</a>
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
									 
						<c:if test="${param.acceptNum!=1 }">			 
					    <div class="count">
					    数据统计:&nbsp;
					    <c:choose>
					    	<c:when test="${empty param.status || param.status=='-1' }">
					    		<span class="title">发布需求总数:</span>
					    		<span class="info ${status6 + status7 + status8 + status40 +status41==0?'cred':''}">${status6 + status7 + status8 + status40 +status41 }</span>
					    	
					    		
					    		<span class="title">待分单数:</span>
					    		<span class="info ${status6==0?'cred':''}">${status6}</span>
					    		<span class="title">待派单数:</span>
					    		<span class="info ${status7==0?'cred':''}">${status7}</span>
					    		<span class="title">已派单数:</span>
					    		<span class="info ${status8==0?'cred':''}">${status8}</span>
					    		<span class="title">待跟进库数:</span>
					    		<span class="info ${status41==0?'cred':''}">${status41}</span>
					    		
					    		<span class="title">退单总数:</span>
					    		<span class="info ${status40==0?'cred':''}">${status40}</span>
					    	</c:when>
					    	
					    	<c:when test="${param.status=='6' }">
					    		<span class="title">待分单数:</span>
					    		<span class="info ${status6==0?'cred':''}">${status6}</span>
					    	</c:when>
					    	<c:when test="${param.status=='7' }">
					    		<span class="title">待派单数:</span>
					    		<span class="info ${status7==0?'cred':''}">${status7}</span>
					    	</c:when>
					    	<c:when test="${param.status=='8' }">
					    		<span class="title">已派单数:</span>
					    		<span class="info ${status8==0?'cred':''}">${status8}</span>
					    	</c:when>
					    	<c:when test="${param.status=='41' }">
					    		<span class="title">待跟进总数:</span>
					    		<span class="info ${status41==0?'cred':''}">${status41}</span>
					    	</c:when>
					    	<c:when test="${param.status=='40' }">
					    		<span class="title">退单总数:</span>
					    		<span class="info ${status40==0?'cred':''}">${status40}</span>
					    	</c:when>
					    	
					    </c:choose>
					    
					    </div>    
					    </c:if>  
					     
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
		//删除
		$(".changeStatu").click(function(){
			var status=$(this).attr("status");
			var storeId=$(this).attr("storeId");
			var param={};
			param.storeId=storeId;
			param.operator="edit";
			var tip="";
			if(status=="0"){//激活
				param.status="1";
				tip="确认激活吗?";
			}else if(status=="1"){//冻结
				param.status="0";
				tip="确认冻结吗";
			}
			layer.confirm(tip, {
				title:"状态切换",
			    btn: ["确定","返回"], //按钮
			    shade: false //不显示遮罩
			}, function(){
				$.post("status.html",param,function(data){
				if(data.status==1){
					layer.msg(data.message,{ icon: 1,time: 1000},function(){
						window.location.reload();
					});
				}else{
					layer.msg(data.message);
				}
			},"json");
			
			}, function(){
				
			});
		});
		
		$(".btn-search").on("click",function(){
			$("#pageIndex").val(1);
			$(this).parents("form").submit();
		});
		
		$(".temp-info").on("click",function(){
			var _this=this;
			layer.msg($(_this).attr("title").replace(/#/g,"<br/>"), {time: 5000, icon:6});
		});
		
		$("#chonsenstatus").on("change",function(){
			var value=$(this).val();
			if(value=="40"||value=="41"){
				$(".other-info").show();
			}else{
				$(".other-info").hide();
				$("#callbackTips").val("");
				$("#startFileTime").val("");
				$("#endFileTime").val("");
			}
		});
		$("#chonsenstatus").change();
		
		$(".chooseOrder").on("click",function(){
			var order=$(this).attr("order");
			if(order){
				$("#pageIndex").val(1);
				$("#chonsenstatus option[value='-1']").attr("selected","selected");
				$("#acceptNum").val(order);
				$("#myform").submit();
			}
		});
		
		//接单
		$(".loadAll").on("click",function(){
			$("#myform").attr("action","loadAll.html");
			$("#myform").submit();
			$("#myform").attr("action","index.html");
		});
		
		//配送礼物
		$(".sendGift").on("click",function(){
			var requiredId=$(this).attr("requiredId");
			if(!requiredId){layer.msg("数据缺失！");return;}
				layer.open({
				    type: 1,
				    scrollbar: false,
				    title:"礼物配送",
				    shadeClose:true,
				    skin: 'layui-layer-rim', //加上边框
				    area: ['520px', 'auto'], //宽高
				    content: '<input type="hidden" id="requiredId"/>'+ 
						' <p class="stLine">配送地址：</p><textarea maxlength="500" class="statuInfo"></textarea>'+
						'<div class="modal-footer"><a href="#" class="btn btn-sm layui-layer-close" data-dismiss="modal">返回</a> <a href="#" class="btn btn-primary btn-sm status-submit">确定</a>'+
						'</div>',
					success:function(){
						$("div.status a.btn[status='"+status+"']").addClass("btn-success");
						$("#requiredId").val(requiredId);
					}
				});
		});
		
		$("body").on("click","a.status-submit",function(){
			var requiredId=$("#requiredId").val();
			var info=$(".statuInfo").val();
			if(!info){
				layer.msg("请填写配送地址");
				return;
			}
			var param={};
			param.requiredId=requiredId;
			if(info){param.info=info;}
			$.post("sendGift.html",param,function(data){
				if(data.status=="1"){
					layer.msg(data.message);
					$("#myform").submit();
				}else{
					layer.msg(data.message);
				}
				
			},"json");
		});
		layer.config({
			  extend: 'extend/layer.ext.js'
			});     
			    
		
		//设置价格
		$("body").on("click","a.setPrice",function(){
			var requiredId=$(this).attr("requiredId");
			if(!requiredId){return;}
			layer.prompt({
				  title: '输入定价（整数）',
				  formType: 0,
				  maxlength:10				  
				}, function(pass,index){
				  if(!pass.match(/\d+/)){
					  alert("请输入整数");return;
				  }
				  var data={};
				  data.requiredId=requiredId;
				  data.price=pass;
				  $.post("setPrice.html",data,function(json){
						  layer.msg(json.message);
				  },"json")
				});
		});
		//修正需求
		$("body").on("click","a.confirmRequire",function(){
			var requiredId=$(this).attr("requiredId");
			var $form=$("<form>");
			$form.attr("method","post").attr("action","update.html");
			$form.append($("<input name='requiredId'/>").attr("value",requiredId));
			$form.append($("<input name='operator'/>").attr("value","toUpdate"));
			$form.submit();
			
		});
	});
	</script>
</body>
</html>
