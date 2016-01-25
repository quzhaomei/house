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
 * �̼ҹ���
 * 
 * @author qzm
 * @since 2015-8-31
 */
@Controller
@RequestMapping(value = "store")
@LimitTag(uri = true)
public class StoreController extends BaseController {
	protected  SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	// ��ҳ��ѯ���е���
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//��ѯ
		String pageIndex = request.getParameter("pageIndex");// ҳ��
		String pageSize = request.getParameter("pageSize");// ҳ����
		
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
				
				//�������и�������
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
	
	
	// �������еĲ�ѯ�߼��Լ�ajax
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		JsonDTO json = new JsonDTO();
		if (FIND_BY_ID.equals(operator)) {// ��ȡ����
			String roleId = request.getParameter("roleId");
			if (roleId != null && roleId.matches("\\d+")) {

				Integer id = Integer.parseInt(roleId);
				RoleManagerDTO role = roleManagerService.getById(id);
				json.setStatus(1).setData(role);
				model.addAttribute("json", JSONUtil.object2json(json));
			}
		} else {
			json.setStatus(0).setMessage("�����쳣");
			model.addAttribute("json", JSONUtil.object2json(json));
		}
		return JSON;
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		String operator=request.getParameter("operator");
		if(TO_ADD.equals(operator)){//���ҳ��
			//�������и�������
			ZoneSet zone=new ZoneSet();
			zone.setParentId(0);
			List<ZoneSetDTO> zones=zoneSetService.getZoneSetByParam(zone);
			model.addAttribute("zones", zones);
			//�����������̸����˽�ɫ
			AdminUser user=new AdminUser();
			user.setStatus(1);
			List<AdminUserDTO> keepers=adminUserService.getListByParamAndRole(user,KEEPER_ROLE_ID);
			model.addAttribute("keepers", keepers);
			return "admin/store_add";
		}else if("findOrderZone".equals(operator)){//����
			//�������и��������Ӧ��������
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
			String zoneId = request.getParameter("zoneId");//��������
			String keeperId = request.getParameter("keeperId");//������
			String logo = request.getParameter("logo");//logo
			
			String[] orderZoneIds = request.getParameterValues("orderZoneIds[]");//�ӵ�����
			
			String storePhone = request.getParameter("storePhone");//���̵绰
			String storeName = request.getParameter("storeName");//����
			String storeAddress = request.getParameter("storeAddress");//���̵�ַ
			String callPhone = request.getParameter("callPhone");//�Խӵ绰
			String msgPhone = request.getParameter("msgPhone");////���ŵ绰
			String size = request.getParameter("size");//ÿ�½ӵ���
			
			String companyName = request.getParameter("companyName");//��˾����
			String ruleUserName = request.getParameter("ruleUserName");////��������
			String ruleUserPhone = request.getParameter("ruleUserPhone");//���˵绰
			
			String status = request.getParameter("status");//1-�ӵ���0-��ͣ
			
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
					json.setStatus(1).setMessage("������̳ɹ���");
				} catch (Exception e) {
					json.setStatus(0).setMessage("���������У�ϵͳ�����쳣��");
					e.printStackTrace();
				}
				
			}else{
				json.setStatus(0).setMessage("�����쳣��");
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
		if (FIND_BY_ID.equals(operator)) {// ��ȡ����
			if (zoneId != null && zoneId.matches("\\d+")) {

				Integer id = Integer.parseInt(zoneId);
				ZoneSetDTO role = zoneSetService.getById(id);
				json.setStatus(1).setData(role);
				model.addAttribute("json", JSONUtil.object2json(json));
				return JSON;
			}
		} else if (zoneId != null && zoneId.matches("\\d+")
				&& (status != null || name != null)) {
			// ���
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
					json.setStatus(1).setMessage("��������ɹ�");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("��������ʧ��:" + e.getMessage());
				}
				model.addAttribute("json", JSONUtil.object2json(json));
				return JSON;
			}
		}
		return null;
	}

	// �л�״̬
	@RequestMapping(value = "/status")
	public String status(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		JsonDTO json = new JsonDTO();
		String zoneId = request.getParameter("zoneId");
		String status = request.getParameter("status");
		if (zoneId != null && zoneId.matches("\\d+") && status != null
				&& status.matches("[01]")) {
			// ���
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
				json.setStatus(1).setMessage("��������ɹ�");
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
