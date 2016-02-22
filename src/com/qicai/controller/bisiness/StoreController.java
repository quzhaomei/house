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
import com.qicai.bean.bisiness.Order;
import com.qicai.bean.bisiness.Store;
import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.PageDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.bisiness.HouseTypeDTO;
import com.qicai.dto.bisiness.OrderDTO;
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
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// 首页查询所有店铺
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 查询
		String pageIndex = request.getParameter("pageIndex");// 页码
		String pageSize = request.getParameter("pageSize");// 页容量

		String zoneId = request.getParameter("zoneId");
		String status = request.getParameter("status");

		String storeName = request.getParameter("storeName");
		String keeperName = request.getParameter("keeperName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

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
				if (zoneId != null && zoneId.matches("\\d+")) {
					selectParam.setZoneId(Integer.parseInt(zoneId));
				}
				if (status != null && status.matches("[01]")) {
					selectParam.setStatus(Integer.parseInt(status));
				}
				selectParam.setStoreName(storeName);
				selectParam.setKeeperName(keeperName);
				if (startDate != null && startDate.length() == 10) {
					try {
						selectParam.setStartDate(format.parse(startDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (endDate != null && endDate.length() == 10) {
					try {
						Date endTime=format.parse(endDate);
						endTime.setDate(endTime.getDate()+1);//包括截止时间
						selectParam.setEndDate(endTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				page.setParam(selectParam);

				PageDTO<List<StoreDTO>> pageDate = storeService.getListByParam(page);

				// 查找所有父级区域
				ZoneSet zone = new ZoneSet();
				zone.setParentId(0);
				List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zone);
				model.addAttribute("zones", zones);

				model.addAttribute("pageResult", pageDate);
				return "admin/store";
			}
		}

		return "admin/store";
	}

	// 查询店铺详细以及历史订单记录
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		String storeId = request.getParameter("storeId");// 店铺ID
		if (storeId != null && storeId.matches("\\d+")) {
			StoreDTO store = storeService.getByParam(new Store(Integer.parseInt(storeId)));
			if (store != null) {
				
				String pageIndex = request.getParameter("pageIndex");// 页码
				String pageSize = request.getParameter("pageSize");// 页容量
				if (pageIndex == null) {
					pageIndex = "1";
				}
				if (pageSize == null) {
					pageSize = "10";
				}
				// 查询订单记录
				if (pageIndex.matches("\\d+") && pageSize.matches("\\d+")) {
					Integer pageIndexInt = Integer.parseInt(pageIndex);
					Integer pageSizeInt = Integer.parseInt(pageSize);
					PageDTO<Order> page = new PageDTO<Order>();
					if (pageIndexInt != 0 && pageSizeInt != 0) {
						page.setPageIndex(pageIndexInt);
						page.setPageSize(pageSizeInt);
						Order selectParam = new Order();
						selectParam.setStoreId(Integer.parseInt(storeId));
						;
						page.setParam(selectParam);

						PageDTO<List<OrderDTO>> pageDate = orderService.getListByParam(page);

						model.addAttribute("pageResult", pageDate);
					}
				}

				model.addAttribute("store", store);
			}
		}
		return "admin/store_list";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response, Model model) {

		String operator = request.getParameter("operator");
		if (TO_ADD.equals(operator)) {// 添加页面
			// 查找所有父级区域
			ZoneSet zone = new ZoneSet();
			zone.setParentId(0);
			List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zone);
			model.addAttribute("zones", zones);
			// 查找所有房型
			List<HouseTypeDTO> types = houseTypeService.getAllHouseType();
			model.addAttribute("types", types);
			// 查找所有商铺负责人角色
			AdminUser user = new AdminUser();
			user.setStatus(1);
			List<AdminUserDTO> keepers = adminUserService.getListByParamAndRole(user, KEEPER_ROLE_ID);
			model.addAttribute("keepers", keepers);
			return "admin/store_add";
		} else if ("findOrderZone".equals(operator)) {// 查找
			// 查找所有父级区域对应的子区域
			String zoneId = request.getParameter("zoneId");
			if (zoneId != null && zoneId.matches("\\d+")) {
				ZoneSet zone = new ZoneSet();
				zone.setParentId(Integer.parseInt(zoneId));
				List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zone);

				model.addAttribute("json", JSONUtil.object2json(zones));
				return JSON;
			}
		} else if (ADD.equals(operator)) {
			JsonDTO json = new JsonDTO();
			String zoneId = request.getParameter("zoneId");// 属于区域
			String keeperId = request.getParameter("keeperId");// 负责人
			String logo = request.getParameter("logo");// logo

			String[] orderZoneIds = request.getParameterValues("orderZoneIds[]");// 接单区域

			String[] orderTypeIds = request.getParameterValues("orderTypeIds[]");// 接单房型

			String storePhone = request.getParameter("storePhone");// 店铺电话
			String storeName = request.getParameter("storeName");// 名字
			String storeAddress = request.getParameter("storeAddress");// 店铺地址
			String callPhone = request.getParameter("callPhone");// 对接电话
			String msgPhone = request.getParameter("msgPhone");//// 短信电话
			String size = request.getParameter("size");// 每月接单量

			String companyName = request.getParameter("companyName");// 公司名字
			String ruleUserName = request.getParameter("ruleUserName");//// 法人姓名
			String ruleUserPhone = request.getParameter("ruleUserPhone");// 法人电话

			String status = request.getParameter("status");// 1-接单，0-暂停

			if (zoneId != null && zoneId.matches("\\d+") && (keeperId == null || keeperId.matches("\\d+"))
					&& logo != null && storePhone != null && storePhone.matches("\\d+") && storeName != null
					&& storeAddress != null && callPhone != null && callPhone.matches("\\d+") && msgPhone != null
					&& msgPhone.matches("\\d+") && size != null && size.matches("\\d+")
					&& (ruleUserPhone == null || ruleUserPhone.matches("\\d+"))
					&& (status == null || status.matches("\\d+"))) {
				List<Integer> zoneIds = new ArrayList<Integer>();
				if (orderZoneIds != null) {
					for (String temp : orderZoneIds) {
						if (temp != null && temp.matches("\\d+")) {
							zoneIds.add(Integer.parseInt(temp));
						}
					}
				}
				List<Integer> typeIds = new ArrayList<Integer>();
				if (orderTypeIds != null) {
					for (String temp : orderTypeIds) {
						if (temp != null && temp.matches("\\d+")) {
							typeIds.add(Integer.parseInt(temp));
						}
					}
				}
				Store saveParam = new Store();
				saveParam.setZoneId(Integer.parseInt(zoneId));
				if (keeperId != null) {
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
					storeService.save(saveParam, zoneIds, typeIds);
					json.setStatus(1).setMessage("保存店铺成功！");
				} catch (Exception e) {
					json.setStatus(0).setMessage("保存数据中，系统出现异常！");
					e.printStackTrace();
				}

			} else {
				json.setStatus(0).setMessage("数据异常！");
			}
			model.addAttribute(JSON, JSONUtil.object2json(json));
			return JSON;

		}

		return JSON;
	}

	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		String storeId = request.getParameter("storeId");
		if (FIND_BY_ID.equals(operator)) {// 获取当个
			if (storeId != null && storeId.matches("\\d+")) {

				// 查找所有父级区域
				ZoneSet zone = new ZoneSet();
				zone.setParentId(0);
				List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zone);
				model.addAttribute("zones", zones);
				// 查找所有房型
				List<HouseTypeDTO> types = houseTypeService.getAllHouseType();
				model.addAttribute("types", types);
				// 查找所有商铺负责人角色
				AdminUser user = new AdminUser();
				user.setStatus(1);
				List<AdminUserDTO> keepers = adminUserService.getListByParamAndRole(user, KEEPER_ROLE_ID);
				model.addAttribute("keepers", keepers);

				Integer id = Integer.parseInt(storeId);
				Store param = new Store();
				param.setStoreId(id);
				StoreDTO store = storeService.getByParam(param);
				model.addAttribute("store", store);

				// 查找指定地区的接单区域
				ZoneSet zone_p = new ZoneSet();
				zone_p.setParentId(store.getZone().getZoneId());
				List<ZoneSetDTO> parentZones = zoneSetService.getZoneSetByParam(zone_p);
				model.addAttribute("parentZones", parentZones);
				return "admin/store_update";
			}
		} else if ("findOrderZone".equals(operator)) {// 查找
			// 查找所有父级区域对应的子区域
			String zoneId = request.getParameter("zoneId");
			if (zoneId != null && zoneId.matches("\\d+")) {
				ZoneSet zone = new ZoneSet();
				zone.setParentId(Integer.parseInt(zoneId));
				List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zone);

				model.addAttribute("json", JSONUtil.object2json(zones));
				return JSON;
			}
		} else if ("update".equals(operator)) {
			JsonDTO json = new JsonDTO();
			String zoneId = request.getParameter("zoneId");// 属于区域
			String keeperId = request.getParameter("keeperId");// 负责人
			String logo = request.getParameter("logo");// logo

			String[] orderZoneIds = request.getParameterValues("orderZoneIds[]");// 接单区域

			String[] orderTypeIds = request.getParameterValues("orderTypeIds[]");// 接单房型

			String storePhone = request.getParameter("storePhone");// 店铺电话
			String storeName = request.getParameter("storeName");// 名字
			String storeAddress = request.getParameter("storeAddress");// 店铺地址
			String callPhone = request.getParameter("callPhone");// 对接电话
			String msgPhone = request.getParameter("msgPhone");//// 短信电话
			String size = request.getParameter("size");// 每月接单量

			String companyName = request.getParameter("companyName");// 公司名字
			String ruleUserName = request.getParameter("ruleUserName");//// 法人姓名
			String ruleUserPhone = request.getParameter("ruleUserPhone");// 法人电话

			String status = request.getParameter("status");// 1-接单，0-暂停

			if (zoneId != null && zoneId.matches("\\d+") && (keeperId == null || keeperId.matches("\\d+"))
					&& logo != null && storePhone != null && storePhone.matches("\\d+") && storeName != null
					&& storeAddress != null && callPhone != null && callPhone.matches("\\d+") && msgPhone != null
					&& msgPhone.matches("\\d+") && size != null && size.matches("\\d+")
					&& (ruleUserPhone == null || ruleUserPhone.matches("\\d+"))
					&& (status == null || status.matches("\\d+")) && (storeId != null && storeId.matches("\\d+"))) {
				List<Integer> zoneIds = new ArrayList<Integer>();
				if (orderZoneIds != null) {
					for (String temp : orderZoneIds) {
						if (temp != null && temp.matches("\\d+")) {
							zoneIds.add(Integer.parseInt(temp));
						}
					}
				}
				List<Integer> typeIds = new ArrayList<Integer>();
				if (orderTypeIds != null) {
					for (String temp : orderTypeIds) {
						if (temp != null && temp.matches("\\d+")) {
							typeIds.add(Integer.parseInt(temp));
						}
					}
				}
				Store updateParam = new Store();
				updateParam.setStoreId(Integer.parseInt(storeId));
				updateParam.setZoneId(Integer.parseInt(zoneId));
				if (keeperId != null) {
					updateParam.setKeeperId(Integer.parseInt(keeperId));
				}
				updateParam.setLogo(logo);
				updateParam.setStorePhone(storePhone);
				updateParam.setStoreName(storeName);
				updateParam.setStoreAddress(storeAddress);
				updateParam.setCallPhone(callPhone);
				updateParam.setMsgPhone(msgPhone);
				updateParam.setSize(Integer.parseInt(size));
				updateParam.setCompanyName(companyName);
				updateParam.setRuleUserName(ruleUserName);
				updateParam.setRuleUserPhone(ruleUserPhone);
				updateParam.setStatus(Integer.parseInt(status));
				updateParam.setUpdateDate(new Date());
				updateParam.setUpdateUserId(getLoginAdminUser(request).getAdminUserId());
				try {
					storeService.update(updateParam, zoneIds, typeIds);
					json.setStatus(1).setMessage("修改店铺成功！");
				} catch (Exception e) {
					json.setStatus(0).setMessage("修改数据中，系统出现异常！");
					e.printStackTrace();
				}

			} else {
				json.setStatus(0).setMessage("数据异常！");
			}
			model.addAttribute(JSON, JSONUtil.object2json(json));
			return JSON;
		}
		return null;
	}

	// 切换状态
	@RequestMapping(value = "/status")
	public String status(HttpServletRequest request, HttpServletResponse response, Model model) {
		JsonDTO json = new JsonDTO();
		String storeId = request.getParameter("storeId");
		String status = request.getParameter("status");
		if (storeId != null && storeId.matches("\\d+") && status != null && status.matches("[01]")) {
			// 检测
			Integer storeIdInt = Integer.parseInt(storeId);
			Integer statusInt = null;
			if (status != null) {
				statusInt = Integer.parseInt(status);
			}
			Store updateParam = new Store();
			updateParam.setStoreId(storeIdInt);
			updateParam.setStatus(statusInt);
			updateParam.setUpdateDate(new Date());
			updateParam.setUpdateUserId(getLoginAdminUser(request).getAdminUserId());
			try {
				storeService.update(updateParam, null, null);
				json.setStatus(1).setMessage("更新店铺成功");
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
