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
 * 需求管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "requireManager")
@LimitTag(uri = true)
public class RequireController extends BaseController {
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// 首页查询所有需求，包括所有状态的
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 查询
		String pageIndex = request.getParameter("pageIndex");// 页码
		String pageSize = request.getParameter("pageSize");// 页容量

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
				//统计
				 /** 6-待分单
				 * 7-待派单
				 * 40关闭，41待跟进库
				 * 8-已派单
				 */
				
				if (status!=null&&status.matches("\\d+")) {
					selectParam.setStatus(Integer.parseInt(status));
					model.addAttribute("status"+Integer.parseInt(status), requireService.getPublishCountByParam(selectParam));
				}else{//全部 
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

	// 查看详细
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requiredId = request.getParameter("requiredId");
		if (requiredId != null && requiredId.matches("\\d+")) {
			RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
			model.addAttribute("require", require);
		}
		return "admin/requireManager_list";
	}
	
	// 配送礼物
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
					json.setStatus(1).setMessage("配送成功");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("配送失败");
				}
			}else{
				json.setStatus(0).setMessage("数据错误");
			}
		}else{
			json.setStatus(0).setMessage("数据错误");
		}
		model.addAttribute("json", JSONUtil.object2json(json));
		return JSON;
	}

	@RequestMapping(value = "/toService") // 派单
	public String toService(HttpServletRequest request, HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		String requiredId = request.getParameter("requiredId");
		if ("to_service".equals(operator)) {// 查询指定的人员
			if (requiredId != null && requiredId.matches("\\d+")) {
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if (require != null && require.getStatus() == 6) {// 待分单状态
					model.addAttribute("require", require);
					String username = request.getParameter("username");
					String userphone = request.getParameter("userphone");

					String pageIndex = request.getParameter("pageIndex");// 页码
					String pageSize = request.getParameter("pageSize");// 页容量
					// 查询所有的业务员
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
			if (requiredId != null && requiredId.matches("\\d+") && userId != null && userId.matches("\\d+")) {// 数据验证
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				AdminUserDTO user = adminUserService.getById(Integer.parseInt(userId));
				JsonDTO json = new JsonDTO();
				json.setStatus(0);
				if (require != null && require.getStatus() == 6 && user != null) {// 待分单状态
					Require updateParam = new Require();
					updateParam.setRequiredId(Integer.parseInt(requiredId));
					updateParam.setStatus(7);

					updateParam.setServiceDate(new Date());
					updateParam.setServiceUserId(Integer.parseInt(userId));
					updateParam.setOperatorLog(require.getOperatorLog() + " <br/> "
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
							+ getLoginAdminUser(request).getNickname() + " 分配给 " + user.getNickname());
					try {
						requireService.update(updateParam);
						json.setStatus(1).setMessage("分单成功！");
					} catch (Exception e) {
						json.setStatus(0).setMessage("分单过程中，系统出现异常");
						e.printStackTrace();
					}
				} else {
					json.setMessage("数据异常");
				}
				model.addAttribute(JSON, JSONUtil.object2json(json));
			}
			return JSON;
		}
		return "admin/requireManager_to_service";
	}

	@RequestMapping(value = "/toStore") // 派单
	public String toStore(HttpServletRequest request, HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		String requiredId = request.getParameter("requiredId");
		if ("to_store".equals(operator)) {// 查询店铺
			if (requiredId != null && requiredId.matches("\\d+")) {
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if (require != null
						&& (require.getStatus() == 6 || require.getStatus() == 8 || require.getStatus() == 7)) {// 待派单状态
					model.addAttribute("require", require);
					// 搜索条件
					String storename = request.getParameter("storename");
					String houseTypeId = request.getParameter("houseTypeId");
					String zoneId = request.getParameter("zoneId");
					// 查找所有房屋类型
					HouseType type = new HouseType();
					type.setStatus(1);
					List<HouseTypeDTO> houses = houseTypeService.getListByParam(type);
					model.addAttribute("houses", houses);

					// 查找所有的区域
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
						// 获取当前选择位置
						ZoneSetDTO curZone = zoneSetService.getById(Integer.parseInt(zoneId));
						model.addAttribute("curZone", curZone);
						// 获取区域
						ZoneSet childParam = new ZoneSet();
						childParam.setParentId(curZone.getParent().getZoneId());
						List<ZoneSetDTO> curZones = zoneSetService.getZoneSetByParam(childParam);
						model.addAttribute("curZones", curZones);
					} else {// 默认为需求的位置
							// 获取当前位置
						ZoneSetDTO curZone = zoneSetService.getById(require.getZone().getZoneId());
						model.addAttribute("curZone", curZone);
						// 获取区域
						ZoneSet childParam = new ZoneSet();
						childParam.setParentId(curZone.getParent().getZoneId());
						List<ZoneSetDTO> curZones = zoneSetService.getZoneSetByParam(childParam);
						model.addAttribute("curZones", curZones);
					}

					String pageIndex = request.getParameter("pageIndex");// 页码
					String pageSize = request.getParameter("pageSize");// 页容量
					// 查询所有的业务员
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

					// 查询前100条派单历史
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
			String type = request.getParameter("type");// 1-派单。2-赠送
			if (requiredId != null && requiredId.matches("\\d+") && storeId != null && storeId.matches("\\d+")
					&& type.matches("[12]")) {// 数据验证
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				StoreDTO store = storeService.getByParam(new Store(Integer.parseInt(storeId)));

				JsonDTO json = new JsonDTO();
				json.setStatus(0);
				if (require != null
						&& (require.getStatus() == 6 || require.getStatus() == 7 || require.getStatus() == 8)
						&& store != null) {// 待派单状态

					// 检测余额够不够
					int price = require.getHouseType().getPrice();
					if (store.getBalance() < price && false) {
						json.setStatus(0).setMessage("店铺余额不足，派单失败");
						model.addAttribute(JSON, JSONUtil.object2json(json));
						return JSON;
					}

					// 检测当月订单数目有没有超标
					Order countParam = new Order();
					countParam.setStoreId(Integer.parseInt(storeId));
					int count = orderService.getCountByParam(countParam);
					if (store.getSize() <= count) {
						json.setStatus(0).setMessage("该店铺订单已达本月上限");
						model.addAttribute(JSON, JSONUtil.object2json(json));
						return JSON;
					}

					// 检测店铺状态
					if (store.getStatus() == 0) {
						json.setStatus(0).setMessage("该店铺暂不接单");
						model.addAttribute(JSON, JSONUtil.object2json(json));
						return JSON;
					}
					// 检测是否派过单
					Order param = new Order();
					param.setRequiredId(Integer.parseInt(requiredId));
					param.setStoreId(Integer.parseInt(storeId));
					OrderDTO order = orderService.getByParam(param);
					if (order != null) {
						json.setStatus(0).setMessage("该店铺已经派过此单了！");
						model.addAttribute(JSON, JSONUtil.object2json(json));
						return JSON;
					}
					Order saveParam = new Order();
					saveParam.setCreateDate(new Date());
					saveParam.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
					saveParam.setType(Integer.parseInt(type));
					saveParam.setStatus(0);// 0分配中

					saveParam.setStoreId(Integer.parseInt(storeId));
					saveParam.setRequiredId(Integer.parseInt(requiredId));

					// 设置价格
					HouseTypeToStore priceParam = new HouseTypeToStore();
					priceParam.setHouseTypeId(require.getHouseType().getTypeId());
					priceParam.setStoreId(Integer.parseInt(storeId));
					HouseTypeToStoreDTO priceTemp = houseTypeToStoreService.getByParam(priceParam);
					if (Integer.parseInt(type) == 2) {
						saveParam.setPrice(1);// 1块钱赠送
					} else if (priceTemp.getPrice() == null) {
						saveParam.setPrice(require.getHouseType().getPrice());
					} else {
						saveParam.setPrice(priceTemp.getPrice());
					}

					saveParam.setOperatorLog(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
							+ getLoginAdminUser(request).getNickname() + " 派单给 " + store.getStoreName());

					try {
						orderService.save(saveParam);
						json.setStatus(1).setMessage("派单成功！");

						// 更新需求状态
						Require updateParam = new Require();
						updateParam.setRequiredId(Integer.parseInt(requiredId));
						updateParam.setOperatorLog(require.getOperatorLog() + " <br/> "
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
								+ getLoginAdminUser(request).getNickname() + " 进行派单给 " + store.getStoreName());
						updateParam.setStatus(8);
						requireService.update(updateParam);
						// TODO 短信提示店铺接单
						// 发送短信
						String content = "您好,凯特猫家装后台有单,请注意查收！回复TD退订。";
						MessageSender.sendMsg(store.getMsgPhone(), content);

					} catch (Exception e) {
						json.setStatus(0).setMessage("派单过程中，系统出现异常");
						e.printStackTrace();
					}
				} else {
					json.setMessage("数据异常");
				}
				model.addAttribute(JSON, JSONUtil.object2json(json));
			}
			return JSON;
		} else if ("findOrderZone".equals(operator)) {
			// 查找所有父级区域对应的子区域
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

	
	// 下载订单
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
		String fileName = "量房需求列表统计.xls";
		try {
			wwb = Workbook.createWorkbook(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			WritableSheet ws = wwb.createSheet("列表一", 0);// 建立工作簿
			// 写表头
			jxl.write.Label label1 = new jxl.write.Label(0, 0, "序列");
			jxl.write.Label label2 = new jxl.write.Label(1, 0, "需求号");
			jxl.write.Label label3 = new jxl.write.Label(2, 0, "地区");
			jxl.write.Label label4 = new jxl.write.Label(3, 0, "用户id");
			jxl.write.Label label5 = new jxl.write.Label(4, 0, "用户名称");
			jxl.write.Label label6 = new jxl.write.Label(5, 0, "用户手机");
			jxl.write.Label label7 = new jxl.write.Label(6, 0, "创建时间");
			jxl.write.Label label8 = new jxl.write.Label(7, 0, "分单时间");
			jxl.write.Label label9 = new jxl.write.Label(8, 0, "已派单数");
			jxl.write.Label label10 = new jxl.write.Label(9, 0, "已接单数");
			jxl.write.Label label11 = new jxl.write.Label(10, 0, "状态");
			
			jxl.write.Label label12 = new jxl.write.Label(11, 0, "客户要求");
			jxl.write.Label label13 = new jxl.write.Label(12, 0, "回访记录");
			jxl.write.Label label14 = new jxl.write.Label(13, 0, "商户提示");
			jxl.write.Label label15 = new jxl.write.Label(14, 0, "归档时间");
			jxl.write.Label label16 = new jxl.write.Label(15, 0, "下次回访时间");
			
			jxl.write.Label label17 = new jxl.write.Label(16, 0, "成功量房数");
			jxl.write.Label label18 = new jxl.write.Label(17, 0, "礼包状态");
			jxl.write.Label label19 = new jxl.write.Label(18, 0, "礼包地址");
			
			ws.addCell(label1);// 放入工作簿
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
					statusStr="待分单";
				}else if(temp.getStatus()==7){
					statusStr="待派单";
				}else if(temp.getStatus()==8){
					statusStr="已派单";
				}else if(temp.getStatus()==40){
					statusStr="退单";
				}else if(temp.getStatus()==41){
					statusStr="待跟进库";
				}
				// 写表头
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
				jxl.write.Label temp7 = new jxl.write.Label(6, i + 1,dateFormat.format( temp.getCreateDate()));// 客服
				jxl.write.Label temp8 = new jxl.write.Label(7, i + 1,temp.getServiceDate()!=null?dateFormat.format( temp.getServiceDate()):"");// 价格
				jxl.write.Label temp9 = new jxl.write.Label(8, i + 1, temp.getOrderCount()+"");// 需求号
				jxl.write.Label temp10 = new jxl.write.Label(9, i + 1, temp.getAcceptNum()+"");// 客户名称
				jxl.write.Label temp11 = new jxl.write.Label(10, i + 1,statusStr);// 电话
				
				jxl.write.Label temp12 = new jxl.write.Label(11, i + 1,temp.getCustomerTips());
				jxl.write.Label temp13 = new jxl.write.Label(12, i + 1,temp.getCallbackTips());
				jxl.write.Label temp14 = new jxl.write.Label(13, i + 1,temp.getServiceTips());
				
				jxl.write.Label temp15 = new jxl.write.Label(14, i + 1,temp.getFileTime()!=null?dateFormat.format(temp.getFileTime()):"");
				jxl.write.Label temp16 = new jxl.write.Label(15, i + 1,temp.getNextCallTime()!=null?dateFormat.format(temp.getFileTime()):"");
				jxl.write.Label temp17 = new jxl.write.Label(16, i + 1,temp.getSuccessNum()+"");
				String giftStatus="";
				if(temp.getGift()!=null&&temp.getGift().getStatus()==0){
					giftStatus="已配送";
				}else{
					giftStatus="未配送";
				}
				jxl.write.Label temp18 = new jxl.write.Label(17, i + 1,giftStatus);
				jxl.write.Label temp19 = new jxl.write.Label(18, i + 1,temp.getGift()!=null?temp.getGift().getAddress():"");

				ws.addCell(temp1);// 放入工作簿
				ws.addCell(temp2);// 放入工作簿
				ws.addCell(temp3);// 放入工作簿
				ws.addCell(temp4);// 放入工作簿
				ws.addCell(temp5);// 放入工作簿
				ws.addCell(temp6);// 放入工作簿
				ws.addCell(temp7);// 放入工作簿
				ws.addCell(temp8);// 放入工作簿
				ws.addCell(temp9);// 放入工作簿
				ws.addCell(temp10);// 放入工作簿

				ws.addCell(temp11);// 放入工作簿
				ws.addCell(temp12);// 放入工作簿
				ws.addCell(temp13);// 放入工作簿
				ws.addCell(temp14);// 放入工作簿
				ws.addCell(temp15);// 放入工作簿
				ws.addCell(temp16);// 放入工作簿
				ws.addCell(temp17);// 放入工作簿
				ws.addCell(temp18);// 放入工作簿
				ws.addCell(temp19);// 放入工作簿
			}
			// 写入Exel工作表
			wwb.write();
			// 关闭Excel工作薄对象
			wwb.close();

			// 下载
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
