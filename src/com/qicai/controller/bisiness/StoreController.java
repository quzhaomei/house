package com.qicai.controller.bisiness;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qicai.annotation.LimitTag;
import com.qicai.bean.admin.AdminUser;
import com.qicai.bean.bisiness.Store;
import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.PageDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.admin.RoleManagerDTO;
import com.qicai.dto.bisiness.StoreDTO;
import com.qicai.dto.bisiness.ZoneSetDTO;
import com.qicai.util.JSONUtil;

/**
 * 商家管理
 * 
 * @author qzm
 * @since 2015-8-31
 */
@Controller
@RequestMapping(value = "store")
@LimitTag(uri = true)
public class StoreController extends BaseController {
	protected  SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	// 首页查询所有店铺
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//查询
		String pageIndex = request.getParameter("pageIndex");// 页码
		String pageSize = request.getParameter("pageSize");// 页容量
		
		String zoneId=request.getParameter("zoneId");
		String status=request.getParameter("status");
		
		String storeName=request.getParameter("storeName");
		String keeperName=request.getParameter("keeperName");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		
		if (pageIndex == null) {
			pageIndex = "1";
		}
		if (pageSize == null) {
			pageSize = "10";
		}
		if (pageIndex.matches("\\d+") && pageSize.matches("\\d+")) {
			Integer pageIndexInt = Integer.parseInt(pageIndex);
			Integer pageSizeInt = Integer.parseInt(pageSize);
			PageDTO<Store> page = new PageDTO<Store>();
			if (pageIndexInt != 0 && pageSizeInt != 0) {
				page.setPageIndex(pageIndexInt);
				page.setPageSize(pageSizeInt);
				Store selectParam = new Store();
				if(zoneId!=null&&zoneId.matches("\\d+")){
					selectParam.setZoneId(Integer.parseInt(zoneId));
				}
				if(status!=null&&status.matches("[01]")){
					selectParam.setStatus(Integer.parseInt(status));
				}
				selectParam.setStoreName(storeName);
				selectParam.setKeeperName(keeperName);
				if(startDate!=null&&startDate.length()==10){
					try {
						selectParam.setStartDate(format.parse(startDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if(endDate!=null&&endDate.length()==10){
					try {
						selectParam.setEndDate(format.parse(endDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				page.setParam(selectParam);
				
				PageDTO<List<StoreDTO>> pageDate = 
						storeService.getListByParam(page);
				
				//查找所有父级区域
				ZoneSet zone=new ZoneSet();
				zone.setParentId(0);
				List<ZoneSetDTO> zones=zoneSetService.getZoneSetByParam(zone);
				model.addAttribute("zones", zones);
				model.addAttribute("params", selectParam);
				
				model.addAttribute("pageResult", pageDate);
				return "admin/store";
			}
		}

		return "admin/store";
	}
	
	
	// 负责所有的查询逻辑以及ajax
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		JsonDTO json = new JsonDTO();
		if (FIND_BY_ID.equals(operator)) {// 获取当个
			String roleId = request.getParameter("roleId");
			if (roleId != null && roleId.matches("\\d+")) {

				Integer id = Integer.parseInt(roleId);
				RoleManagerDTO role = roleManagerService.getById(id);
				json.setStatus(1).setData(role);
				model.addAttribute("json", JSONUtil.object2json(json));
			}
		} else {
			json.setStatus(0).setMessage("参数异常");
			model.addAttribute("json", JSONUtil.object2json(json));
		}
		return JSON;
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		String operator=request.getParameter("operator");
		if(TO_ADD.equals(operator)){//添加页面
			//查找所有父级区域
			ZoneSet zone=new ZoneSet();
			zone.setParentId(0);
			List<ZoneSetDTO> zones=zoneSetService.getZoneSetByParam(zone);
			model.addAttribute("zones", zones);
			//查找所有商铺负责人角色
			AdminUser user=new AdminUser();
			user.setStatus(1);
			List<AdminUserDTO> keepers=adminUserService.getListByParamAndRole(user,KEEPER_ROLE_ID);
			model.addAttribute("keepers", keepers);
			return "admin/store_add";
		}else if("findOrderZone".equals(operator)){//查找
			//查找所有父级区域对应的子区域
			String zoneId=request.getParameter("zoneId");
			if(zoneId!=null&&zoneId.matches("\\d+")){
				ZoneSet zone=new ZoneSet();
				zone.setParentId(Integer.parseInt(zoneId));
				List<ZoneSetDTO> zones=zoneSetService.getZoneSetByParam(zone);
				model.addAttribute("json", JSONUtil.object2json(zones));
				return JSON;
			}
		}else if(ADD.equals(operator)){
			JsonDTO json = new JsonDTO();
			String zoneId = request.getParameter("zoneId");//属于区域
			String keeperId = request.getParameter("keeperId");//负责人
			String logo = request.getParameter("logo");//logo
			
			String[] orderZoneIds = request.getParameterValues("orderZoneIds[]");//接单区域
			
			String storePhone = request.getParameter("storePhone");//店铺电话
			String storeName = request.getParameter("storeName");//名字
			String storeAddress = request.getParameter("storeAddress");//店铺地址
			String callPhone = request.getParameter("callPhone");//对接电话
			String msgPhone = request.getParameter("msgPhone");////短信电话
			String size = request.getParameter("size");//每月接单量
			
			String companyName = request.getParameter("companyName");//公司名字
			String ruleUserName = request.getParameter("ruleUserName");////法人姓名
			String ruleUserPhone = request.getParameter("ruleUserPhone");//法人电话
			
			String status = request.getParameter("status");//1-接单，0-暂停
			
			if(zoneId!=null&&zoneId.matches("\\d+")&&(keeperId==null||keeperId.matches("\\d+"))
					&&logo!=null&&storePhone!=null&&storePhone.matches("\\d+")
					&&storeName!=null&&storeAddress!=null&&callPhone!=null&&callPhone.matches("\\d+")
					&&msgPhone!=null&&msgPhone.matches("\\d+")&&size!=null&&size.matches("\\d+")
					&&(ruleUserPhone==null||ruleUserPhone.matches("\\d+"))
					&&(status==null||status.matches("\\d+"))){
				List<Integer> zoneIds=new ArrayList<Integer>();
				if(orderZoneIds!=null){
					for(String temp:orderZoneIds){
						if(temp!=null&&temp.matches("\\d+")){
							zoneIds.add(Integer.parseInt(temp));
						}
					}
				}
				Store saveParam= new Store();
				saveParam.setZoneId(Integer.parseInt(zoneId));
				if(keeperId!=null){
					saveParam.setKeeperId(Integer.parseInt(keeperId));
				}
				saveParam.setLogo(logo);
				saveParam.setStorePhone(storePhone);
				saveParam.setStoreName(storeName);
				saveParam.setStoreAddress(storeAddress);
				saveParam.setCallPhone(callPhone);
				saveParam.setMsgPhone(msgPhone);
				saveParam.setSize(Integer.parseInt(size));
				saveParam.setCompanyName(companyName);
				saveParam.setRuleUserName(ruleUserName);
				saveParam.setRuleUserPhone(ruleUserPhone);
				saveParam.setStatus(Integer.parseInt(status));
				saveParam.setCreateDate(new Date());
				saveParam.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
				try {
					storeService.save(saveParam, zoneIds);
					json.setStatus(1).setMessage("保存店铺成功！");
				} catch (Exception e) {
					json.setStatus(0).setMessage("保存数据中，系统出现异常！");
					e.printStackTrace();
				}
				
			}else{
				json.setStatus(0).setMessage("数据异常！");
			}
			model.addAttribute(JSON, JSONUtil.object2json(json));
			return JSON;
			
		}

		return JSON;
	}

	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		AdminUserDTO user=(AdminUserDTO) request.getSession().getAttribute(ADMIN_USER_SESSION);
		JsonDTO json = new JsonDTO();
		String operator=request.getParameter("operator");
		String name = request.getParameter("name");
		String zoneId = request.getParameter("zoneId");
		String status = request.getParameter("status");
		if (FIND_BY_ID.equals(operator)) {// 获取当个
			if (zoneId != null && zoneId.matches("\\d+")) {

				Integer id = Integer.parseInt(zoneId);
				ZoneSetDTO role = zoneSetService.getById(id);
				json.setStatus(1).setData(role);
				model.addAttribute("json", JSONUtil.object2json(json));
				return JSON;
			}
		} else if (zoneId != null && zoneId.matches("\\d+")
				&& (status != null || name != null)) {
			// 检测
			if ((name == null || name.length() <= 50)
					&& (status == null || status.matches("\\d"))) {
				Integer zoneIdInt = Integer.parseInt(zoneId);
				Integer statusInt = null;
				if (status != null) {
					statusInt = Integer.parseInt(status);
				}
				ZoneSet updateParam = new ZoneSet();
				updateParam.setZoneId(zoneIdInt);
				updateParam.setStatus(statusInt);
				updateParam.setUpdateDate(new Date());
				updateParam.setUpdateUserId(user.getAdminUserId());
				updateParam.setName(name);
				try {
					zoneSetService.saveOrUpdate(updateParam);
					json.setStatus(1).setMessage("更新区域成功");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("更新区域失败:" + e.getMessage());
				}
				model.addAttribute("json", JSONUtil.object2json(json));
				return JSON;
			}
		}
		return null;
	}

	// 切换状态
	@RequestMapping(value = "/status")
	public String status(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		JsonDTO json = new JsonDTO();
		String zoneId = request.getParameter("zoneId");
		String status = request.getParameter("status");
		if (zoneId != null && zoneId.matches("\\d+") && status != null
				&& status.matches("[01]")) {
			// 检测
			Integer zoneIdInt = Integer.parseInt(zoneId);
			Integer statusInt = null;
			if (status != null) {
				statusInt = Integer.parseInt(status);
			}
			ZoneSet updateParam = new ZoneSet();
			updateParam.setZoneId(zoneIdInt);
			updateParam.setStatus(statusInt);
			try {
				zoneSetService.saveOrUpdate(updateParam);
				json.setStatus(1).setMessage("更新区域成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.setStatus(0).setMessage("更新区域失败:" + e.getMessage());
			}
			model.addAttribute("json", JSONUtil.object2json(json));
			return "json";
		}
		return null;
	}

}
