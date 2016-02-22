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
 * �̼ҹ���
 * 
 * @author qzm
 * @since 2015-8-31
 */
@Controller
@RequestMapping(value = "store")
@LimitTag(uri = true)
public class StoreController extends BaseController {
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// ��ҳ��ѯ���е���
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		// ��ѯ
		String pageIndex = request.getParameter("pageIndex");// ҳ��
		String pageSize = request.getParameter("pageSize");// ҳ����

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
						endTime.setDate(endTime.getDate()+1);//������ֹʱ��
						selectParam.setEndDate(endTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				page.setParam(selectParam);

				PageDTO<List<StoreDTO>> pageDate = storeService.getListByParam(page);

				// �������и�������
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

	// ��ѯ������ϸ�Լ���ʷ������¼
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		String storeId = request.getParameter("storeId");// ����ID
		if (storeId != null && storeId.matches("\\d+")) {
			StoreDTO store = storeService.getByParam(new Store(Integer.parseInt(storeId)));
			if (store != null) {
				
				String pageIndex = request.getParameter("pageIndex");// ҳ��
				String pageSize = request.getParameter("pageSize");// ҳ����
				if (pageIndex == null) {
					pageIndex = "1";
				}
				if (pageSize == null) {
					pageSize = "10";
				}
				// ��ѯ������¼
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
		if (TO_ADD.equals(operator)) {// ���ҳ��
			// �������и�������
			ZoneSet zone = new ZoneSet();
			zone.setParentId(0);
			List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zone);
			model.addAttribute("zones", zones);
			// �������з���
			List<HouseTypeDTO> types = houseTypeService.getAllHouseType();
			model.addAttribute("types", types);
			// �����������̸����˽�ɫ
			AdminUser user = new AdminUser();
			user.setStatus(1);
			List<AdminUserDTO> keepers = adminUserService.getListByParamAndRole(user, KEEPER_ROLE_ID);
			model.addAttribute("keepers", keepers);
			return "admin/store_add";
		} else if ("findOrderZone".equals(operator)) {// ����
			// �������и��������Ӧ��������
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
			String zoneId = request.getParameter("zoneId");// ��������
			String keeperId = request.getParameter("keeperId");// ������
			String logo = request.getParameter("logo");// logo

			String[] orderZoneIds = request.getParameterValues("orderZoneIds[]");// �ӵ�����

			String[] orderTypeIds = request.getParameterValues("orderTypeIds[]");// �ӵ�����

			String storePhone = request.getParameter("storePhone");// ���̵绰
			String storeName = request.getParameter("storeName");// ����
			String storeAddress = request.getParameter("storeAddress");// ���̵�ַ
			String callPhone = request.getParameter("callPhone");// �Խӵ绰
			String msgPhone = request.getParameter("msgPhone");//// ���ŵ绰
			String size = request.getParameter("size");// ÿ�½ӵ���

			String companyName = request.getParameter("companyName");// ��˾����
			String ruleUserName = request.getParameter("ruleUserName");//// ��������
			String ruleUserPhone = request.getParameter("ruleUserPhone");// ���˵绰

			String status = request.getParameter("status");// 1-�ӵ���0-��ͣ

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
					json.setStatus(1).setMessage("������̳ɹ���");
				} catch (Exception e) {
					json.setStatus(0).setMessage("���������У�ϵͳ�����쳣��");
					e.printStackTrace();
				}

			} else {
				json.setStatus(0).setMessage("�����쳣��");
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
		if (FIND_BY_ID.equals(operator)) {// ��ȡ����
			if (storeId != null && storeId.matches("\\d+")) {

				// �������и�������
				ZoneSet zone = new ZoneSet();
				zone.setParentId(0);
				List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zone);
				model.addAttribute("zones", zones);
				// �������з���
				List<HouseTypeDTO> types = houseTypeService.getAllHouseType();
				model.addAttribute("types", types);
				// �����������̸����˽�ɫ
				AdminUser user = new AdminUser();
				user.setStatus(1);
				List<AdminUserDTO> keepers = adminUserService.getListByParamAndRole(user, KEEPER_ROLE_ID);
				model.addAttribute("keepers", keepers);

				Integer id = Integer.parseInt(storeId);
				Store param = new Store();
				param.setStoreId(id);
				StoreDTO store = storeService.getByParam(param);
				model.addAttribute("store", store);

				// ����ָ�������Ľӵ�����
				ZoneSet zone_p = new ZoneSet();
				zone_p.setParentId(store.getZone().getZoneId());
				List<ZoneSetDTO> parentZones = zoneSetService.getZoneSetByParam(zone_p);
				model.addAttribute("parentZones", parentZones);
				return "admin/store_update";
			}
		} else if ("findOrderZone".equals(operator)) {// ����
			// �������и��������Ӧ��������
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
			String zoneId = request.getParameter("zoneId");// ��������
			String keeperId = request.getParameter("keeperId");// ������
			String logo = request.getParameter("logo");// logo

			String[] orderZoneIds = request.getParameterValues("orderZoneIds[]");// �ӵ�����

			String[] orderTypeIds = request.getParameterValues("orderTypeIds[]");// �ӵ�����

			String storePhone = request.getParameter("storePhone");// ���̵绰
			String storeName = request.getParameter("storeName");// ����
			String storeAddress = request.getParameter("storeAddress");// ���̵�ַ
			String callPhone = request.getParameter("callPhone");// �Խӵ绰
			String msgPhone = request.getParameter("msgPhone");//// ���ŵ绰
			String size = request.getParameter("size");// ÿ�½ӵ���

			String companyName = request.getParameter("companyName");// ��˾����
			String ruleUserName = request.getParameter("ruleUserName");//// ��������
			String ruleUserPhone = request.getParameter("ruleUserPhone");// ���˵绰

			String status = request.getParameter("status");// 1-�ӵ���0-��ͣ

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
					json.setStatus(1).setMessage("�޸ĵ��̳ɹ���");
				} catch (Exception e) {
					json.setStatus(0).setMessage("�޸������У�ϵͳ�����쳣��");
					e.printStackTrace();
				}

			} else {
				json.setStatus(0).setMessage("�����쳣��");
			}
			model.addAttribute(JSON, JSONUtil.object2json(json));
			return JSON;
		}
		return null;
	}

	// �л�״̬
	@RequestMapping(value = "/status")
	public String status(HttpServletRequest request, HttpServletResponse response, Model model) {
		JsonDTO json = new JsonDTO();
		String storeId = request.getParameter("storeId");
		String status = request.getParameter("status");
		if (storeId != null && storeId.matches("\\d+") && status != null && status.matches("[01]")) {
			// ���
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
				json.setStatus(1).setMessage("���µ��̳ɹ�");
			} catch (Exception e) {
				e.printStackTrace();
				json.setStatus(0).setMessage("��������ʧ��:" + e.getMessage());
			}
			model.addAttribute("json", JSONUtil.object2json(json));
			return "json";
		}
		return null;
	}

}
