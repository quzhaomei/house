package com.qicai.controller.bisiness;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qicai.annotation.LimitTag;
import com.qicai.bean.admin.AdminUser;
import com.qicai.bean.bisiness.HouseType;
import com.qicai.bean.bisiness.HouseTypeToStore;
import com.qicai.bean.bisiness.Order;
import com.qicai.bean.bisiness.Require;
import com.qicai.bean.bisiness.Store;
import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.PageDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.bisiness.HouseTypeDTO;
import com.qicai.dto.bisiness.HouseTypeToStoreDTO;
import com.qicai.dto.bisiness.OrderDTO;
import com.qicai.dto.bisiness.RequireDTO;
import com.qicai.dto.bisiness.RequireGift;
import com.qicai.dto.bisiness.ServiceStoreDTO;
import com.qicai.dto.bisiness.ServiceUserDTO;
import com.qicai.dto.bisiness.StoreDTO;
import com.qicai.dto.bisiness.ZoneSetDTO;
import com.qicai.util.JSONUtil;
import com.qicai.util.MessageSender;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * �������
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "requireManager")
@LimitTag(uri = true)
public class RequireController extends BaseController {
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// ��ҳ��ѯ�������󣬰�������״̬��
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		// ��ѯ
		String pageIndex = request.getParameter("pageIndex");// ҳ��
		String pageSize = request.getParameter("pageSize");// ҳ����

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String status = request.getParameter("status");
		String username = request.getParameter("username");
		String userId = request.getParameter("userId");
		String serviceStartDate = request.getParameter("serviceStartDate");
		String serviceEndDate = request.getParameter("serviceEndDate");

		String callbackTips = request.getParameter("callbackTips");
		String startFileTime = request.getParameter("startFileTime");
		String endFileTime = request.getParameter("endFileTime");

		String startNextCallTime = request.getParameter("startNextCallTime");
		String endNextCallTime = request.getParameter("endNextCallTime");
		
		String acceptNum = request.getParameter("acceptNum");
		if (pageIndex == null) {
			pageIndex = "1";
		}
		if (pageSize == null) {
			pageSize = "10";
		}
	
		if (pageIndex.matches("\\d+") && pageSize.matches("\\d+")) {
			Integer pageIndexInt = Integer.parseInt(pageIndex);
			Integer pageSizeInt = Integer.parseInt(pageSize);
			PageDTO<Require> page = new PageDTO<Require>();
			if (pageIndexInt != 0 && pageSizeInt != 0) {
				page.setPageIndex(pageIndexInt);
				page.setPageSize(pageSizeInt);
				Require selectParam = new Require();
				selectParam.setUsername(username);
				if (userId != null && userId.matches("\\d+")) {
					selectParam.setUserId(Integer.parseInt(userId));
				}
				if (status != null && status.matches("\\d+")) {
					selectParam.setStatus(Integer.parseInt(status));
				}
				if (startDate != null && startDate.length() == 10) {
					try {
						selectParam.setStartDate(format.parse(startDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (endDate != null && endDate.length() == 10) {
					try {
						Date endTime = format.parse(endDate);
						endTime.setDate(endTime.getDate() + 1);
						selectParam.setEndDate(endTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (serviceStartDate != null && serviceStartDate.length() == 10) {
					try {
						selectParam.setServiceStartDate(format.parse(serviceStartDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (serviceEndDate != null && serviceEndDate.length() == 10) {
					try {
						Date serviceEndTime = format.parse(serviceEndDate);
						serviceEndTime.setDate(serviceEndTime.getDate() + 1);
						selectParam.setServiceEndDate(serviceEndTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (callbackTips != null && !callbackTips.equals(""))
					selectParam.setCallbackTips(callbackTips);
				if (startFileTime != null && startFileTime.length() == 10) {
					try {
						selectParam.setStartFileTime(format.parse(startFileTime));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (endFileTime != null && endFileTime.length() == 10) {
					try {
						Date serviceEndTime = format.parse(endFileTime);
						serviceEndTime.setDate(serviceEndTime.getDate() + 1);
						selectParam.setEndFileTime(serviceEndTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (startNextCallTime != null && startNextCallTime.length() == 10) {
					try {
						selectParam.setStartNextCallTime(format.parse(startNextCallTime));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (endNextCallTime != null && endNextCallTime.length() == 10) {
					try {
						Date endNextCallTime_ = format.parse(endNextCallTime);
						endNextCallTime_.setDate(endNextCallTime_.getDate() + 1);
						selectParam.setEndNextCallTime(endNextCallTime_);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if(acceptNum!=null&&"1".equals(acceptNum)){
					selectParam.setAcceptNum(1);
				}
				page.setParam(selectParam);

				PageDTO<List<RequireDTO>> pageDate = requireService.findPublishByPage(page);
				
				model.addAttribute("pageResult", pageDate);
				//ͳ��
				 /** 6-���ֵ�
				 * 7-���ɵ�
				 * 40�رգ�41��������
				 * 8-���ɵ�
				 */
				
				if (status!=null&&status.matches("\\d+")) {
					selectParam.setStatus(Integer.parseInt(status));
					model.addAttribute("status"+Integer.parseInt(status), requireService.getPublishCountByParam(selectParam));
				}else{//ȫ�� 
					selectParam.setStatus(6);
					model.addAttribute("status6", requireService.getPublishCountByParam(selectParam));
					selectParam.setStatus(7);
					model.addAttribute("status7", requireService.getPublishCountByParam(selectParam));
					selectParam.setStatus(8);
					model.addAttribute("status8", requireService.getPublishCountByParam(selectParam));
					selectParam.setStatus(40);
					model.addAttribute("status40", requireService.getPublishCountByParam(selectParam));
					selectParam.setStatus(41);
					model.addAttribute("status41", requireService.getPublishCountByParam(selectParam));
				}
				
				return "admin/requireManager";
			}
		}

		return "admin/requireManager";

	}

	// �鿴��ϸ
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requiredId = request.getParameter("requiredId");
		if (requiredId != null && requiredId.matches("\\d+")) {
			RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
			model.addAttribute("require", require);
		}
		return "admin/requireManager_list";
	}
	
	// ��������
	@RequestMapping(value = "/sendGift")
	public String sendGift(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requiredId = request.getParameter("requiredId");
		String address=request.getParameter("info");
		JsonDTO json=new JsonDTO();
		if (requiredId != null && requiredId.matches("\\d+")) {
			RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
			if(require!=null){
				RequireGift gift=new RequireGift();
				gift.setRequiredId(Integer.parseInt(requiredId));
				gift.setStatus(0);
				gift.setAddress(address);
				gift.setCreateDate(new Date());
				try {
					requireService.saveGift(gift);
					json.setStatus(1).setMessage("���ͳɹ�");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("����ʧ��");
				}
			}else{
				json.setStatus(0).setMessage("���ݴ���");
			}
		}else{
			json.setStatus(0).setMessage("���ݴ���");
		}
		model.addAttribute("json", JSONUtil.object2json(json));
		return JSON;
	}

	@RequestMapping(value = "/toService") // �ɵ�
	public String toService(HttpServletRequest request, HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		String requiredId = request.getParameter("requiredId");
		if ("to_service".equals(operator)) {// ��ѯָ������Ա
			if (requiredId != null && requiredId.matches("\\d+")) {
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if (require != null && require.getStatus() == 6) {// ���ֵ�״̬
					model.addAttribute("require", require);
					String username = request.getParameter("username");
					String userphone = request.getParameter("userphone");

					String pageIndex = request.getParameter("pageIndex");// ҳ��
					String pageSize = request.getParameter("pageSize");// ҳ����
					// ��ѯ���е�ҵ��Ա
					if (pageIndex == null) {
						pageIndex = "1";
					}
					if (pageSize == null) {
						// pageSize = "2";
						pageSize = "10";
					}
					AdminUser param = new AdminUser();
					param.setPhone(userphone);
					param.setNickname(username);

					PageDTO<AdminUser> page = new PageDTO<AdminUser>();
					page.setPageSize(Integer.parseInt(pageSize));
					page.setPageIndex(Integer.parseInt(pageIndex));
					page.setParam(param);

					PageDTO<List<ServiceUserDTO>> pageDate = adminUserService.getServiceByParam(page, SERVICE_ROLE_ID);

					model.addAttribute("pageResult", pageDate);
				}

			}
		} else if ("doService".equals(operator)) {
			String userId = request.getParameter("userId");
			if (requiredId != null && requiredId.matches("\\d+") && userId != null && userId.matches("\\d+")) {// ������֤
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				AdminUserDTO user = adminUserService.getById(Integer.parseInt(userId));
				JsonDTO json = new JsonDTO();
				json.setStatus(0);
				if (require != null && require.getStatus() == 6 && user != null) {// ���ֵ�״̬
					Require updateParam = new Require();
					updateParam.setRequiredId(Integer.parseInt(requiredId));
					updateParam.setStatus(7);

					updateParam.setServiceDate(new Date());
					updateParam.setServiceUserId(Integer.parseInt(userId));
					updateParam.setOperatorLog(require.getOperatorLog() + " <br/> "
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " �� "
							+ getLoginAdminUser(request).getNickname() + " ����� " + user.getNickname());
					try {
						requireService.update(updateParam);
						json.setStatus(1).setMessage("�ֵ��ɹ���");
					} catch (Exception e) {
						json.setStatus(0).setMessage("�ֵ������У�ϵͳ�����쳣");
						e.printStackTrace();
					}
				} else {
					json.setMessage("�����쳣");
				}
				model.addAttribute(JSON, JSONUtil.object2json(json));
			}
			return JSON;
		}
		return "admin/requireManager_to_service";
	}

	@RequestMapping(value = "/toStore") // �ɵ�
	public String toStore(HttpServletRequest request, HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		String requiredId = request.getParameter("requiredId");
		if ("to_store".equals(operator)) {// ��ѯ����
			if (requiredId != null && requiredId.matches("\\d+")) {
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if (require != null
						&& (require.getStatus() == 6 || require.getStatus() == 8 || require.getStatus() == 7)) {// ���ɵ�״̬
					model.addAttribute("require", require);
					// ��������
					String storename = request.getParameter("storename");
					String houseTypeId = request.getParameter("houseTypeId");
					String zoneId = request.getParameter("zoneId");
					// �������з�������
					HouseType type = new HouseType();
					type.setStatus(1);
					List<HouseTypeDTO> houses = houseTypeService.getListByParam(type);
					model.addAttribute("houses", houses);

					// �������е�����
					ZoneSet zone = new ZoneSet();
					zone.setParentId(0);
					zone.setStatus(1);
					List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zone);
					model.addAttribute("zones", zones);

					Store selectParam = new Store();
					if (houseTypeId != null && houseTypeId.matches("\\d+")) {
						selectParam.setServiceTypeId(Integer.parseInt(houseTypeId));
					} else {
						selectParam.setServiceTypeId(require.getHouseType().getTypeId());
					}
					if (storename != null && storename.trim().length() > 0) {
						selectParam.setStoreName(storename);
					}
					selectParam.setServiceZoneId(require.getZone().getZoneId());
					if (zoneId != null && zoneId.matches("\\d+")) {
						selectParam.setServiceZoneId(Integer.parseInt(zoneId));
						// ��ȡ��ǰѡ��λ��
						ZoneSetDTO curZone = zoneSetService.getById(Integer.parseInt(zoneId));
						model.addAttribute("curZone", curZone);
						// ��ȡ����
						ZoneSet childParam = new ZoneSet();
						childParam.setParentId(curZone.getParent().getZoneId());
						List<ZoneSetDTO> curZones = zoneSetService.getZoneSetByParam(childParam);
						model.addAttribute("curZones", curZones);
					} else {// Ĭ��Ϊ�����λ��
							// ��ȡ��ǰλ��
						ZoneSetDTO curZone = zoneSetService.getById(require.getZone().getZoneId());
						model.addAttribute("curZone", curZone);
						// ��ȡ����
						ZoneSet childParam = new ZoneSet();
						childParam.setParentId(curZone.getParent().getZoneId());
						List<ZoneSetDTO> curZones = zoneSetService.getZoneSetByParam(childParam);
						model.addAttribute("curZones", curZones);
					}

					String pageIndex = request.getParameter("pageIndex");// ҳ��
					String pageSize = request.getParameter("pageSize");// ҳ����
					// ��ѯ���е�ҵ��Ա
					if (pageIndex == null) {
						pageIndex = "1";
					}
					if (pageSize == null) {
						// pageSize = "2";
						pageSize = "10";
					}
					selectParam.setStatus(1);
					PageDTO<Store> page = new PageDTO<>();
					page.setParam(selectParam);
					page.setPageSize(Integer.parseInt(pageSize));
					page.setPageIndex(Integer.parseInt(pageIndex));
					PageDTO<List<ServiceStoreDTO>> pageDate = storeService.getServiceListByParam(page);

					List<ServiceStoreDTO> mainDate = pageDate.getParam();
					for (ServiceStoreDTO temp : mainDate) {
						HouseTypeToStore param = new HouseTypeToStore();
						param.setHouseTypeId(require.getHouseType().getTypeId());
						param.setStoreId(temp.getStoreId());
						HouseTypeToStoreDTO store = houseTypeToStoreService.getByParam(param);
						if (store.getPrice() != null) {
							temp.setPrice(store.getPrice());
						} else {
							temp.setPrice(require.getHouseType().getPrice());
						}
					}

					model.addAttribute("pageResult", pageDate);

					// ��ѯǰ100���ɵ���ʷ
					PageDTO<Order> page_ = new PageDTO<>();
					page_.setPageIndex(1);
					page_.setPageSize(100);
					Order param = new Order();
					param.setRequiredId(Integer.parseInt(requiredId));
					page_.setParam(param);
					PageDTO<List<OrderDTO>> orders = orderService.getListByParam(page_);
					model.addAttribute("orderHistory", orders);

				}

			}
		} else if ("store".equals(operator)) {
			String storeId = request.getParameter("storeId");
			String type = request.getParameter("type");// 1-�ɵ���2-����
			if (requiredId != null && requiredId.matches("\\d+") && storeId != null && storeId.matches("\\d+")
					&& type.matches("[12]")) {// ������֤
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				StoreDTO store = storeService.getByParam(new Store(Integer.parseInt(storeId)));

				JsonDTO json = new JsonDTO();
				json.setStatus(0);
				if (require != null
						&& (require.getStatus() == 6 || require.getStatus() == 7 || require.getStatus() == 8)
						&& store != null) {// ���ɵ�״̬

					// ���������
					int price = require.getHouseType().getPrice();
					if (store.getBalance() < price && false) {
						json.setStatus(0).setMessage("�������㣬�ɵ�ʧ��");
						model.addAttribute(JSON, JSONUtil.object2json(json));
						return JSON;
					}

					// ��⵱�¶�����Ŀ��û�г���
					Order countParam = new Order();
					countParam.setStoreId(Integer.parseInt(storeId));
					int count = orderService.getCountByParam(countParam);
					if (store.getSize() <= count) {
						json.setStatus(0).setMessage("�õ��̶����Ѵﱾ������");
						model.addAttribute(JSON, JSONUtil.object2json(json));
						return JSON;
					}

					// ������״̬
					if (store.getStatus() == 0) {
						json.setStatus(0).setMessage("�õ����ݲ��ӵ�");
						model.addAttribute(JSON, JSONUtil.object2json(json));
						return JSON;
					}
					// ����Ƿ��ɹ���
					Order param = new Order();
					param.setRequiredId(Integer.parseInt(requiredId));
					param.setStoreId(Integer.parseInt(storeId));
					OrderDTO order = orderService.getByParam(param);
					if (order != null) {
						json.setStatus(0).setMessage("�õ����Ѿ��ɹ��˵��ˣ�");
						model.addAttribute(JSON, JSONUtil.object2json(json));
						return JSON;
					}
					Order saveParam = new Order();
					saveParam.setCreateDate(new Date());
					saveParam.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
					saveParam.setType(Integer.parseInt(type));
					saveParam.setStatus(0);// 0������

					saveParam.setStoreId(Integer.parseInt(storeId));
					saveParam.setRequiredId(Integer.parseInt(requiredId));

					// ���ü۸�
					HouseTypeToStore priceParam = new HouseTypeToStore();
					priceParam.setHouseTypeId(require.getHouseType().getTypeId());
					priceParam.setStoreId(Integer.parseInt(storeId));
					HouseTypeToStoreDTO priceTemp = houseTypeToStoreService.getByParam(priceParam);
					if (Integer.parseInt(type) == 2) {
						saveParam.setPrice(1);// 1��Ǯ����
					} else if (priceTemp.getPrice() == null) {
						saveParam.setPrice(require.getHouseType().getPrice());
					} else {
						saveParam.setPrice(priceTemp.getPrice());
					}

					saveParam.setOperatorLog(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " �� "
							+ getLoginAdminUser(request).getNickname() + " �ɵ��� " + store.getStoreName());

					try {
						orderService.save(saveParam);
						json.setStatus(1).setMessage("�ɵ��ɹ���");

						// ��������״̬
						Require updateParam = new Require();
						updateParam.setRequiredId(Integer.parseInt(requiredId));
						updateParam.setOperatorLog(require.getOperatorLog() + " <br/> "
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " �� "
								+ getLoginAdminUser(request).getNickname() + " �����ɵ��� " + store.getStoreName());
						updateParam.setStatus(8);
						requireService.update(updateParam);
						// TODO ������ʾ���̽ӵ�
						// ���Ͷ���
						String content = "����,����è��װ��̨�е�,��ע����գ��ظ�TD�˶���";
						MessageSender.sendMsg(store.getMsgPhone(), content);

					} catch (Exception e) {
						json.setStatus(0).setMessage("�ɵ������У�ϵͳ�����쳣");
						e.printStackTrace();
					}
				} else {
					json.setMessage("�����쳣");
				}
				model.addAttribute(JSON, JSONUtil.object2json(json));
			}
			return JSON;
		} else if ("findOrderZone".equals(operator)) {
			// �������и��������Ӧ��������
			String zoneId = request.getParameter("zoneId");
			if (zoneId != null && zoneId.matches("\\d+")) {
				ZoneSet zone = new ZoneSet();
				zone.setParentId(Integer.parseInt(zoneId));
				zone.setStatus(1);
				List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zone);

				model.addAttribute("json", JSONUtil.object2json(zones));
				return JSON;
			}
		}
		return "admin/requireService_to_store";
	}

	
	// ���ض���
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/loadAll")
	public String load(HttpServletRequest request, HttpServletResponse response, Model model) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String status = request.getParameter("status");
		String username = request.getParameter("username");
		String userId = request.getParameter("userId");
		String serviceStartDate = request.getParameter("serviceStartDate");
		String serviceEndDate = request.getParameter("serviceEndDate");

		String callbackTips = request.getParameter("callbackTips");
		String startFileTime = request.getParameter("startFileTime");
		String endFileTime = request.getParameter("endFileTime");

		String startNextCallTime = request.getParameter("startNextCallTime");
		String endNextCallTime = request.getParameter("endNextCallTime");
		
		String acceptNum = request.getParameter("acceptNum");
		Require selectParam = new Require();
		selectParam.setUsername(username);
		if (userId != null && userId.matches("\\d+")) {
			selectParam.setUserId(Integer.parseInt(userId));
		}
		if (status != null && status.matches("\\d+")) {
			selectParam.setStatus(Integer.parseInt(status));
		}
		if (startDate != null && startDate.length() == 10) {
			try {
				selectParam.setStartDate(format.parse(startDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (endDate != null && endDate.length() == 10) {
			try {
				Date endTime = format.parse(endDate);
				endTime.setDate(endTime.getDate() + 1);
				selectParam.setEndDate(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (serviceStartDate != null && serviceStartDate.length() == 10) {
			try {
				selectParam.setServiceStartDate(format.parse(serviceStartDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (serviceEndDate != null && serviceEndDate.length() == 10) {
			try {
				Date serviceEndTime = format.parse(serviceEndDate);
				serviceEndTime.setDate(serviceEndTime.getDate() + 1);
				selectParam.setServiceEndDate(serviceEndTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (callbackTips != null && !callbackTips.equals(""))
			selectParam.setCallbackTips(callbackTips);
		if (startFileTime != null && startFileTime.length() == 10) {
			try {
				selectParam.setStartFileTime(format.parse(startFileTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (endFileTime != null && endFileTime.length() == 10) {
			try {
				Date serviceEndTime = format.parse(endFileTime);
				serviceEndTime.setDate(serviceEndTime.getDate() + 1);
				selectParam.setEndFileTime(serviceEndTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (startNextCallTime != null && startNextCallTime.length() == 10) {
			try {
				selectParam.setStartNextCallTime(format.parse(startNextCallTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (endNextCallTime != null && endNextCallTime.length() == 10) {
			try {
				Date endNextCallTime_ = format.parse(endNextCallTime);
				endNextCallTime_.setDate(endNextCallTime_.getDate() + 1);
				selectParam.setEndNextCallTime(endNextCallTime_);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(acceptNum!=null&&"1".equals(acceptNum)){
			selectParam.setAcceptNum(1);
		}
		List<RequireDTO> result=requireService.publishList(selectParam);
		WritableWorkbook wwb = null;
		String fileName = "���������б�ͳ��.xls";
		try {
			wwb = Workbook.createWorkbook(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			WritableSheet ws = wwb.createSheet("�б�һ", 0);// ����������
			// д��ͷ
			jxl.write.Label label1 = new jxl.write.Label(0, 0, "����");
			jxl.write.Label label2 = new jxl.write.Label(1, 0, "�����");
			jxl.write.Label label3 = new jxl.write.Label(2, 0, "����");
			jxl.write.Label label4 = new jxl.write.Label(3, 0, "�û�id");
			jxl.write.Label label5 = new jxl.write.Label(4, 0, "�û�����");
			jxl.write.Label label6 = new jxl.write.Label(5, 0, "�û��ֻ�");
			jxl.write.Label label7 = new jxl.write.Label(6, 0, "����ʱ��");
			jxl.write.Label label8 = new jxl.write.Label(7, 0, "�ֵ�ʱ��");
			jxl.write.Label label9 = new jxl.write.Label(8, 0, "���ɵ���");
			jxl.write.Label label10 = new jxl.write.Label(9, 0, "�ѽӵ���");
			jxl.write.Label label11 = new jxl.write.Label(10, 0, "״̬");
			
			jxl.write.Label label12 = new jxl.write.Label(11, 0, "�ͻ�Ҫ��");
			jxl.write.Label label13 = new jxl.write.Label(12, 0, "�طü�¼");
			jxl.write.Label label14 = new jxl.write.Label(13, 0, "�̻���ʾ");
			jxl.write.Label label15 = new jxl.write.Label(14, 0, "�鵵ʱ��");
			jxl.write.Label label16 = new jxl.write.Label(15, 0, "�´λط�ʱ��");
			
			jxl.write.Label label17 = new jxl.write.Label(16, 0, "�ɹ�������");
			jxl.write.Label label18 = new jxl.write.Label(17, 0, "���״̬");
			jxl.write.Label label19 = new jxl.write.Label(18, 0, "�����ַ");
			
			ws.addCell(label1);// ���빤����
			ws.addCell(label2);
			ws.addCell(label3);
			ws.addCell(label4);
			ws.addCell(label5);
			ws.addCell(label6);
			ws.addCell(label7);
			ws.addCell(label8);
			ws.addCell(label9);
			ws.addCell(label10);
			ws.addCell(label11);
			
			ws.addCell(label12);
			ws.addCell(label13);
			ws.addCell(label14);
			ws.addCell(label15);
			ws.addCell(label16);
			ws.addCell(label17);
			ws.addCell(label18);
			ws.addCell(label19);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < result.size(); i++) {
				
				RequireDTO temp = result.get(i);
				String statusStr="";
				if(temp.getStatus()<=6){
					statusStr="���ֵ�";
				}else if(temp.getStatus()==7){
					statusStr="���ɵ�";
				}else if(temp.getStatus()==8){
					statusStr="���ɵ�";
				}else if(temp.getStatus()==40){
					statusStr="�˵�";
				}else if(temp.getStatus()==41){
					statusStr="��������";
				}
				// д��ͷ
				jxl.write.Label temp1 = new jxl.write.Label(0, i + 1, "" + (i + 1));
				jxl.write.Label temp2 = new jxl.write.Label(1, i + 1, temp.getRequiredId() + "");
				jxl.write.Label temp3 = new jxl.write.Label(2, i + 1, temp.getZone().getName());
				jxl.write.Label temp4 = new jxl.write.Label(3, i + 1, temp.getUserId()+"");// 
				jxl.write.Label temp5 = new jxl.write.Label(4, i + 1, temp.getUsername());//
				String userphone="";
				if( temp.getUserphone().length()>5){
					userphone+=temp.getUserphone().substring(0, 3)+"****";
				}
				if(temp.getUserphone().length()>7){
					userphone+=temp.getUserphone().substring(7, temp.getUserphone().length());
				}
				jxl.write.Label temp6 = new jxl.write.Label(5, i + 1, userphone);//
				jxl.write.Label temp7 = new jxl.write.Label(6, i + 1,dateFormat.format( temp.getCreateDate()));// �ͷ�
				jxl.write.Label temp8 = new jxl.write.Label(7, i + 1,temp.getServiceDate()!=null?dateFormat.format( temp.getServiceDate()):"");// �۸�
				jxl.write.Label temp9 = new jxl.write.Label(8, i + 1, temp.getOrderCount()+"");// �����
				jxl.write.Label temp10 = new jxl.write.Label(9, i + 1, temp.getAcceptNum()+"");// �ͻ�����
				jxl.write.Label temp11 = new jxl.write.Label(10, i + 1,statusStr);// �绰
				
				jxl.write.Label temp12 = new jxl.write.Label(11, i + 1,temp.getCustomerTips());
				jxl.write.Label temp13 = new jxl.write.Label(12, i + 1,temp.getCallbackTips());
				jxl.write.Label temp14 = new jxl.write.Label(13, i + 1,temp.getServiceTips());
				
				jxl.write.Label temp15 = new jxl.write.Label(14, i + 1,temp.getFileTime()!=null?dateFormat.format(temp.getFileTime()):"");
				jxl.write.Label temp16 = new jxl.write.Label(15, i + 1,temp.getNextCallTime()!=null?dateFormat.format(temp.getFileTime()):"");
				jxl.write.Label temp17 = new jxl.write.Label(16, i + 1,temp.getSuccessNum()+"");
				String giftStatus="";
				if(temp.getGift()!=null&&temp.getGift().getStatus()==0){
					giftStatus="������";
				}else{
					giftStatus="δ����";
				}
				jxl.write.Label temp18 = new jxl.write.Label(17, i + 1,giftStatus);
				jxl.write.Label temp19 = new jxl.write.Label(18, i + 1,temp.getGift()!=null?temp.getGift().getAddress():"");

				ws.addCell(temp1);// ���빤����
				ws.addCell(temp2);// ���빤����
				ws.addCell(temp3);// ���빤����
				ws.addCell(temp4);// ���빤����
				ws.addCell(temp5);// ���빤����
				ws.addCell(temp6);// ���빤����
				ws.addCell(temp7);// ���빤����
				ws.addCell(temp8);// ���빤����
				ws.addCell(temp9);// ���빤����
				ws.addCell(temp10);// ���빤����

				ws.addCell(temp11);// ���빤����
				ws.addCell(temp12);// ���빤����
				ws.addCell(temp13);// ���빤����
				ws.addCell(temp14);// ���빤����
				ws.addCell(temp15);// ���빤����
				ws.addCell(temp16);// ���빤����
				ws.addCell(temp17);// ���빤����
				ws.addCell(temp18);// ���빤����
				ws.addCell(temp19);// ���빤����
			}
			// д��Exel������
			wwb.write();
			// �ر�Excel����������
			wwb.close();

			// ����
			String contentType = "application/x-download";
			response.setContentType(contentType);
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));

			ServletOutputStream out = response.getOutputStream();

			byte[] bytes = new byte[0xffff];
			InputStream is = new FileInputStream(new File(fileName));
			int b = 0;
			while ((b = is.read(bytes, 0, 0xffff)) > 0) {
				out.write(bytes, 0, b);
			}
			is.close();
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
}
