<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="ad" uri="/adtag" %>
						  		<td><span class="label label-important">[临时]</span></td>
						  		<td><fmt:formatDate value="${temp.createDate }" pattern="yyyy-MM-dd HH:mm"/> </td>
						  		<td>${temp.userId }</td>
						  		<td>${temp.username }</td>
						  		<td><fmt:formatDate value="${temp.serviceDate }" pattern="yyyy-MM-dd HH:mm"/></td>
						  		<td>${temp.orderCount }</td>
						  		<td>
						  			<c:choose>
						  			<c:when test="${temp.status==7}">
						  				<span class="label">待派单</span>
						  				</c:when>
						  				<c:when test="${temp.status==8}">
						  				<span class="label label-success">已派单</span>
						  				</c:when>
						  				<c:when test="${temp.status==40}">
						  				<span class="label label-success">退单</span>
						  				</c:when>
						  				<c:when test="${temp.status==41}">
						  				<span class="label label-success">待跟进库</span>
						  				</c:when>
						  			</c:choose>
						  		</td>
						  		<td>
						  		<ad:power uri="../requireService/list.html">
						  		<a class="btn btn-mini" href="list.html?requiredId=${temp.requiredId }">详情</a>
						  		</ad:power>
						  		
						  		<c:choose>
						  			<c:when test="${temp.status!=40&&temp.status!=41 }">
						  				<ad:power uri="../requireService/toStore.html">
							  			<a class="btn btn-mini green" href="toStore.html?operator=to_store&requiredId=${temp.requiredId }">
							  			去派单</a>
							  			</ad:power>
							  		<ad:power uri="../requireService/message.html">	
							  			<a class="btn btn-mini yellow" href="message.html?operator=to_message&requiredId=${temp.requiredId }">
							  			备注-更新</a>
							  			</ad:power>
							  			<ad:power uri="../requireService/call.html">	
							  			<a class="btn btn-mini yellow call-customer" href="#"
							  			 data-href="call.html?requiredId=${temp.requiredId }">
							  			拨通电话</a>
							  			</ad:power>
							  			
							  			<ad:power uri="../requireService/getOrder.html">	
							  			<c:if test="${temp.status==8 }">
							  			<a class="btn btn-mini yellow" href="getOrder.html?requiredId=${temp.requiredId }">
							  			查看订单</a>
							  			</c:if>
							  			</ad:power>
							  			
							  			<ad:power uri="../requireService/message.html">	
							  			<a class="btn btn-mini btn-danger otherStatus" href="#" requiredId="${temp.requiredId }"
							  			status="${temp.status}">
							  			状态冻结</a>
							  			</ad:power>
						  			</c:when>
						  		</c:choose>
						  			<c:if test="${temp.status==41 }">
						  				<ad:power uri="../requireService/message.html">	
							  			<a class="btn btn-mini btn-danger useStatus" href="#" requiredId="${temp.requiredId }"
							  			status="${temp.status}">
							  			激活</a>
							  			</ad:power>
						  			</c:if>
						  		 </td>
