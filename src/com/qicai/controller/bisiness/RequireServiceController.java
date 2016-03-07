package com.qicai.controller.bisiness;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qicai.annotation.LimitTag;
import com.qicai.bean.bisiness.HouseType;
import com.qicai.bean.bisiness.HouseTypeToStore;
import com.qicai.bean.bisiness.Order;
import com.qicai.bean.bisiness.Require;
import com.qicai.bean.bisiness.Store;
import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.HouseTypeDTO;
import com.qicai.dto.bisiness.HouseTypeToStoreDTO;
import com.qicai.dto.bisiness.OrderDTO;
import com.qicai.dto.bisiness.RequireDTO;
import com.qicai.dto.bisiness.ServiceStoreDTO;
import com.qicai.dto.bisiness.StoreDTO;
import com.qicai.dto.bisiness.ZoneSetDTO;
import com.qicai.util.JSONUtil;
import com.qicai.util.MessageSender;
import com.qicai.util.ShortUrlUtil;

/**
 * 需求管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "requireService")
@LimitTag(uri = true)
public class RequireServiceController extends BaseController {
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// 首页查询所有分配给我的需求
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
				selectParam.setServiceUserId(getLoginAdminUser(request).getAdminUserId());
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

				page.setParam(selectParam);

				PageDTO<List<RequireDTO>> pageDate = requireService.findListByPage(page);

				model.addAttribute("pageResult", pageDate);
				return "admin/requireService";
			}
		}

		return "admin/requireService";

	}

	// 拨打电话
	@RequestMapping(value = "/call")
	public String call(HttpServletRequest request, HttpServletResponse response, Model model) {
		JsonDTO json=new JsonDTO();
		json.setMessage("暂未开发");
		model.addAttribute("json", JSONUtil.object2json(json));
		return JSON;
	}

	// 查看详细
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requiredId = request.getParameter("requiredId");
		if (requiredId != null && requiredId.matches("\\d+")) {
			RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
			model.addAttribute("require", require);
		}
		return "admin/requireService_list";
	}

	@RequestMapping(value = "/orderlist")
	public String orderlist(HttpServletRequest request, HttpServletResponse response, Model model) {
		String orderId = request.getParameter("orderId");
		if (orderId != null && orderId.matches("\\d+")) {

			Order param = new Order();
			param.setOrderId(Integer.parseInt(orderId));
			// 以店长ID 和订单ID进行匹配
			OrderDTO order = orderService.getByParam(param);
			if (order != null) {
				model.addAttribute("order", order);
				RequireDTO requireDTO = requireService.getByParam(new Require(order.getRequire().getRequiredId()));
				model.addAttribute("require", requireDTO);
			}
		}

		return "admin/service_order_manager_list";
	}

	// 查看订单// 备注
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/getOrder")
	public String getOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requiredId = request.getParameter("requiredId");
		// 查询
		String pageIndex = request.getParameter("pageIndex");// 页码
		String pageSize = request.getParameter("pageSize");// 页容量

		String status = request.getParameter("status");

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String username = request.getParameter("username");
		String storeName = request.getParameter("storeName");

		if (pageIndex == null) {
			pageIndex = "1";
		}
		if (pageSize == null) {
			pageSize = "10";
		}
		if (pageIndex.matches("\\d+") && pageSize.matches("\\d+")) {
			Integer pageIndexInt = Integer.parseInt(pageIndex);
			Integer pageSizeInt = Integer.parseInt(pageSize);
			PageDTO<Order> page = new PageDTO<Order>();
			if (pageIndexInt != 0 && pageSizeInt != 0) {
				page.setPageIndex(pageIndexInt);
				page.setPageSize(pageSizeInt);
				Order selectParam = new Order();
				selectParam.setUsername(username);
				selectParam.setStoreName(storeName);
				if (requiredId != null && requiredId.matches("\\d+")) {
					selectParam.setRequiredId(Integer.parseInt(requiredId));
				}
				if (status != null && status.matches("[01]")) {
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
				page.setParam(selectParam);

				PageDTO<List<OrderDTO>> pageDate = orderService.getListByParam(page);
				model.addAttribute("requiredId", requiredId);
				model.addAttribute("pageResult", pageDate);
				return "admin/server_order_manager";
			}
		}

		return "admin/server_order_manager";
	}

	// 备注
	@RequestMapping(value = "/message")
	public String message(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requiredId = request.getParameter("requiredId");
		String operator = request.getParameter("operator");
		if (requiredId != null && requiredId.matches("\\d+")) {
			if ("to_message".equals(operator)) {
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				model.addAttribute("require", require);
				return "admin/requireService_message";
			} else if ("message".equals(operator)) {
				JsonDTO json = new JsonDTO();
				String callbackTips = request.getParameter("callbackTips");
				String serviceTips = request.getParameter("serviceTips");
				String budget=request.getParameter("budget");
				String houseLocation=request.getParameter("houseLocation");
				String designTime=request.getParameter("designTime");
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if (require != null) {
					Require updateParam = new Require();
					updateParam.setRequiredId(Integer.parseInt(requiredId));
					updateParam.setBudget(budget);
					updateParam.setHouseLocation(houseLocation);
					updateParam.setCallbackTips(callbackTips);
					updateParam.setServiceTips(serviceTips);
					updateParam.setDesignTime(designTime);
					try {
						requireService.update(updateParam);
						json.setStatus(1).setMessage("更新需求成功！");
					} catch (Exception e) {
						json.setStatus(0).setMessage("更新过程中，系统出现异常！");
						e.printStackTrace();
					}
				} else {
					json.setStatus(0).setMessage("数据异常");
				}
				model.addAttribute("json", JSONUtil.object2json(json));
				return JSON;
			}else if("toOtherStatus".equals(operator)){
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if(require.getNextCallTime()!=null){
					require.setNextCallTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(require.getNextCallTime()));
				}
				model.addAttribute("json", JSONUtil.object2json(require));
				return JSON;
			}else if("otherStatus".equals(operator)){
				JsonDTO json=new JsonDTO();
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				String status=request.getParameter("status");
				String info=request.getParameter("info");
				String nextcallTime=request.getParameter("nextcallTime");
				if(status!=null&&status.matches("\\d+")&&require!=null){
					Require updateParam=new Require();
					updateParam.setRequiredId(Integer.parseInt(requiredId));
					updateParam.setCallbackTips(info);
					updateParam.setStatus(Integer.parseInt(status));
					String logOp=null;
					if(Integer.parseInt(status)==40){
						updateParam.setFileTime(new Date());
						logOp="关闭";
					}else if(Integer.parseInt(status)==41){
						updateParam.setFileTime(new Date());
						try {
							updateParam.setNextCallTime(new SimpleDateFormat("yyyy-MM-dd").parse(nextcallTime));
							
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
						logOp="待跟进库";
					}
					updateParam.setOperatorLog(require.getOperatorLog() + " <br/> "
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
							+ getLoginAdminUser(request).getNickname() + " 对订单进行了 " +logOp+" 操作");
					try {
						requireService.update(updateParam);
						json.setStatus(1).setMessage("更新成功");
					} catch (Exception e) {
						json.setStatus(0).setMessage("更新过程中，系统出现异常");
						e.printStackTrace();
					}
					
				}else{
					json.setStatus(0).setMessage("数据异常");
				}
				model.addAttribute("json", JSONUtil.object2json(json));
				return JSON;
			}else if("openStatus".equals(operator)){//激活
				JsonDTO json=new JsonDTO();
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if(require!=null){
					if(require.getStatus()==41){
						Require updateParam=new Require();
						updateParam.setRequiredId(Integer.parseInt(requiredId));
						updateParam.setStatus(7);//待派单状态
						updateParam.setOperatorLog(require.getOperatorLog() + " <br/> "
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
								+ getLoginAdminUser(request).getNickname() + " 对订单进行了 激活到待派单 操作");
						try {
							requireService.update(updateParam);
							json.setStatus(1).setMessage("更新成功");
						} catch (Exception e) {
							json.setStatus(0).setMessage("更新过程中，系统出现异常");
							e.printStackTrace();
						}
						
					}else{
						json.setStatus(0).setMessage("状态异常");
					}
				}else{
					json.setStatus(0).setMessage("该需求不存在！");
				}
				model.addAttribute("json", JSONUtil.object2json(json));
				return "json";
			}
			
		}
		return "";
	}

	@RequestMapping(value = "/toStore") // 派单
	public String toStore(HttpServletRequest request, HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		String requiredId = request.getParameter("requiredId");
		if ("to_store".equals(operator)) {// 查询店铺
			if (requiredId != null && requiredId.matches("\\d+")) {
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if (require != null && (require.getStatus() == 8 || require.getStatus() == 7)) {// 待派单状态
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
					
					List<ServiceStoreDTO> mainDate=pageDate.getParam();
					for(ServiceStoreDTO temp:mainDate){
						HouseTypeToStore param=new HouseTypeToStore();
						param.setHouseTypeId(require.getHouseType().getTypeId());
						param.setStoreId(temp.getStoreId());
						HouseTypeToStoreDTO store=houseTypeToStoreService.getByParam(param);
						if(store.getPrice()!=null){
							temp.setPrice(store.getPrice());
						}else{
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
				if (require != null && (require.getStatus() == 7 || require.getStatus() == 8) && store != null) {// 待派单状态

					// 检测余额够不够
					int price = require.getHouseType().getPrice();
					if (store.getBalance() < price) {
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
						//TODO 短信提示店铺接单
						//发送短信
						String content="您好,凯特猫家装后台有单,请注意查收！回复TD退订。";
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

	public static void main(String[] args) {
		String url = "http://51getMore.cn/house/requirePublish/index.html";
		url = ShortUrlUtil.getShotUrl(url);
		System.out.println(url);
	}
}
