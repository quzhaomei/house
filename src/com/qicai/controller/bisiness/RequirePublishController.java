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
import com.qicai.bean.bisiness.Require;
import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.HouseTypeDTO;
import com.qicai.dto.bisiness.RequireDTO;
import com.qicai.dto.bisiness.ZoneSetDTO;
import com.qicai.util.HttpSendResult;
import com.qicai.util.JSONUtil;
import com.qicai.util.MessageSender;
import com.qicai.util.PasswordUtil;
import com.qicai.util.UuidUtils;

/**
 * 需求发布
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "requirePublish")
@LimitTag(uri = true)
public class RequirePublishController extends BaseController {
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
		String userphone = request.getParameter("userphone");
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
				selectParam.setUsername(username);
				selectParam.setUserphone(userphone);
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
				return "admin/requirePublish";
			}
		}

		return "admin/requirePublish";

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

	// 添加新的需求预约
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		if (TO_ADD.equals(operator)) {
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
			return "admin/requireManager_add";
		} else if ("findOrderZone".equals(operator)) {// 查找
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
		} else if (ADD.equals(operator)) {
			JsonDTO json = new JsonDTO();
			String username = request.getParameter("username");
			String userphone = request.getParameter("userphone");
			String zoneId = request.getParameter("zoneId");
			String typeId = request.getParameter("typeId");
			String houseDes = request.getParameter("houseDes");

			String houseInfo = request.getParameter("houseInfo");
			String budget = request.getParameter("budget");
			String isReady = request.getParameter("isReady");
			String isNew = request.getParameter("isNew");
			String readyDate = request.getParameter("readyDate");
			String houseLocation = request.getParameter("houseLocation");
			String designTime = request.getParameter("designTime");
			String phoneTime = request.getParameter("phoneTime");
			String customerTips = request.getParameter("customerTips");
			// 数据校验
			if (username != null && username.length() <= 20 && userphone != null && userphone.matches("\\d+")
					&& zoneId != null && zoneId.matches("\\d+") && typeId != null && typeId.matches("\\d+")
					&& houseDes != null && isNew != null && isNew.matches("\\d+") && budget != null
					&& budget.matches("\\d+") && houseInfo != null && houseLocation != null && designTime != null
					&& phoneTime != null && isReady.matches("[01]")
					&& (("1".equals(isReady) || readyDate != null && readyDate.length() == 10))) {
				Require saveParam = new Require();
				saveParam.setUserphone(userphone);
				// 处理userId存在的问题
				List<RequireDTO> old = requireService.list(saveParam);

				if (old != null && old.size() > 0) {
					saveParam.setUserId(old.get(0).getUserId());
				} else {
					saveParam.setUserId(UuidUtils.getUserId());
				}

				saveParam.setUsername(username);

				saveParam.setZoneId(Integer.parseInt(zoneId));
				saveParam.setHouseTypeId(Integer.parseInt(typeId));
				saveParam.setBudget(Integer.parseInt(budget));
				saveParam.setHouseDes(houseDes);
				saveParam.setHouseInfo(houseInfo);
				saveParam.setIsNew(Integer.parseInt(isNew));
				saveParam.setIsReady(Integer.parseInt(isReady));
				if (readyDate != null && readyDate.length() == 10 && "0".equals(isReady)) {// 且没交房
					try {
						saveParam.setReadyDate(format.parse(readyDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				saveParam.setHouseLocation(houseLocation);
				saveParam.setDesignTime(designTime);
				saveParam.setPhoneTime(phoneTime);
				saveParam.setCustomerTips(customerTips);
				saveParam.setStatus(0);// 后台新建预约中
				saveParam.setCreateDate(new Date());
				saveParam.setCreateUserId(getLoginAdminUser(request).getAdminUserId());

				saveParam.setOperatorLog(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
						+ getLoginAdminUser(request).getNickname() + " 创建预约");

				try {
					requireService.save(saveParam);
					json.setStatus(1).setMessage("预约需求成功");
				} catch (Exception e) {
					json.setStatus(0).setMessage("添加过程中。系统出现异常");
					e.printStackTrace();
				}

			} else {
				json.setStatus(0).setMessage("数据异常");
			}
			model.addAttribute("json", JSONUtil.object2json(json));
		}
		return JSON;
	}

	/**
	 * 修正已建立，却暂未发布的信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		if ("toUpdate".equals(operator)) {
			String requiredId = request.getParameter("requiredId");
			if (requiredId != null && requiredId.matches("\\d+")) {
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if (require.getStatus() == 0||require.getStatus() == 1
						||require.getStatus() == 2||require.getStatus() == 3
						||require.getStatus() == 4) {// 未发布的才能更改
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

					model.addAttribute("require", require);
					return "admin/requireManager_update";
				}
			}
		} else if ("update".equals(operator)) {
			JsonDTO json = new JsonDTO();
			String requiredId = request.getParameter("requiredId");

			String username = request.getParameter("username");
			String userphone = request.getParameter("userphone");
			String zoneId = request.getParameter("zoneId");
			String typeId = request.getParameter("typeId");
			String houseDes = request.getParameter("houseDes");
			String budget = request.getParameter("budget");
			String houseInfo = request.getParameter("houseInfo");
			String isReady = request.getParameter("isReady");
			String isNew = request.getParameter("isNew");
			String readyDate = request.getParameter("readyDate");
			String houseLocation = request.getParameter("houseLocation");
			String designTime = request.getParameter("designTime");
			String phoneTime = request.getParameter("phoneTime");
			String customerTips = request.getParameter("customerTips");
			// 数据校验
			if (requiredId != null && requiredId.matches("\\d+") && budget != null && budget.matches("\\d+")
					&& username != null && username.length() <= 20 && userphone != null && userphone.matches("\\d+")
					&& (zoneId == null || zoneId != null && zoneId.matches("\\d+")) && typeId != null
					&& typeId.matches("\\d+") && houseDes != null && isNew != null && isNew.matches("\\d+")
					&& houseInfo != null && houseLocation != null && designTime != null && phoneTime != null
					&& isReady.matches("[01]")

					&& (("1".equals(isReady) || readyDate != null && readyDate.length() == 10))) {
				Require updateParam = new Require();
				updateParam.setRequiredId(Integer.parseInt(requiredId));
				updateParam.setUsername(username);
				updateParam.setUserphone(userphone);
				if (zoneId != null && zoneId.matches("\\d+")) {
					updateParam.setZoneId(Integer.parseInt(zoneId));
				}
				updateParam.setBudget(Integer.parseInt(budget));
				updateParam.setHouseTypeId(Integer.parseInt(typeId));
				updateParam.setHouseDes(houseDes);
				updateParam.setHouseInfo(houseInfo);
				updateParam.setIsNew(Integer.parseInt(isNew));
				updateParam.setIsReady(Integer.parseInt(isReady));
				if (readyDate != null && readyDate.length() == 10 && "0".equals(isReady)) {
					try {
						updateParam.setReadyDate(format.parse(readyDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				updateParam.setHouseLocation(houseLocation);
				updateParam.setDesignTime(designTime);
				updateParam.setPhoneTime(phoneTime);
				updateParam.setCustomerTips(customerTips);

				try {
					requireService.update(updateParam);
					json.setStatus(1).setMessage("预约更新成功");
				} catch (Exception e) {
					json.setStatus(0).setMessage("添加过程中。系统出现异常");
					e.printStackTrace();
				}

			} else {
				json.setStatus(0).setMessage("数据异常");
			}
			model.addAttribute("json", JSONUtil.object2json(json));
			return JSON;

		} else if ("findOrderZone".equals(operator)) {// 查找
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
		return "";
	}

	// 发布预约
	@RequestMapping(value = "/status")
	public String status(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requiredId = request.getParameter("requiredId");
		if (requiredId != null && requiredId.matches("\\d+")) {
			JsonDTO json = new JsonDTO();
			RequireDTO old = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
			if (old != null && (old.getStatus() == 0||old.getStatus() == 1
					||old.getStatus() == 2||old.getStatus() == 3||old.getStatus() == 4)) {
				Require updateParam = new Require();
				updateParam.setRequiredId(Integer.parseInt(requiredId));
				updateParam.setStatus(6);// 设置为发布状态，待分单
				updateParam.setOperatorLog(
						old.getOperatorLog() + " <br/> " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
								+ " 由 " + getLoginAdminUser(request).getNickname() + " 提交发布");
				try {
					requireService.update(updateParam);
					json.setStatus(1).setMessage("预约更新成功");
				} catch (Exception e) {
					json.setStatus(0).setMessage("更新预约过程中，系统出错，请稍后再试！");
					e.printStackTrace();
				}
				model.addAttribute(JSON, JSONUtil.object2json(json));
			} else {
				json.setStatus(0).setMessage("数据异常");
			}
		}
		return JSON;
	}
		
	// 发送短信
	@RequestMapping(value = "/sendMsg")
	public String sendMsg(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requiredId = request.getParameter("requiredId");
		JsonDTO json=new JsonDTO();
		if (requiredId != null && requiredId.matches("\\d+")) {
			RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
			if(require!=null&&(require.getStatus()==1||require.getStatus()==0)){
				Require updateParam=new Require();
				updateParam.setRequiredId(Integer.parseInt(requiredId));
				updateParam.setStatus(1);//重置为发送短信的状态
				updateParam.setOperatorLog(require.getOperatorLog()+" <br/> "+
						new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+" 由 "+getLoginAdminUser(request).getNickname()+" 发送短信给客户");
				try {
					String sign = PasswordUtil.MD5(require.getUserphone() + requiredId);
					String host = request.getRequestURL().toString();
					host = host.substring(0, host.lastIndexOf("/"));
					host = host.substring(0, host.lastIndexOf("/"));
					host += "/mobile/requireConfirm.html?requiredId=" 
					+ Integer.parseInt(requiredId)+"&userphone="+
							require.getUserphone()+"&sign="+sign;
					String url=host;
//					System.out.println(url);
					//TODO
					String content="尊敬的会员您好：感谢您预约免费量房，注册还可以领取装修礼包 "+url+" ,回复TD退订。";
					HttpSendResult result= MessageSender.sendMsg(require.getUserphone(), content);
					if(result.getIsSuccess()){
						requireService.update(updateParam);
						json.setStatus(1).setMessage("发送短信成功");
					}else{
						json.setStatus(0).setMessage(result.getInfo());
					}
				} catch (Exception e) {
					json.setStatus(0).setMessage("发送短信失败");
					e.printStackTrace();
				}
			}else{
				json.setStatus(0).setMessage("数据异常！");
			}
		}else{
			json.setStatus(0).setMessage("数据丢失，请刷新页面");
		}
		model.addAttribute(JSON, JSONUtil.object2json(json));
		return JSON;
	}
	
	//确认
	@RequestMapping(value = "/confirm")
	public String confirm(HttpServletRequest request, HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		String requiredId = request.getParameter("requiredId");
		if("to_confirm".equals(operator)){//跳转到确认页面
			if(requiredId!=null&&requiredId.matches("\\d+")){
				RequireDTO require=requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				//判断状态
				if(require!=null&&require.getStatus()==3){
					//查找所有房屋类型
					HouseType houseType=new HouseType();
					houseType.setStatus(1);
					List<HouseTypeDTO> houses=houseTypeService.getListByParam(houseType);
					model.addAttribute("houses", houses);
					
					//查找所有的区域
					ZoneSet zone=new ZoneSet();
					zone.setParentId(0);
					zone.setStatus(1);
					List<ZoneSetDTO> zones=zoneSetService.getZoneSetByParam(zone);
					model.addAttribute("zones", zones);
					
					model.addAttribute("require", require);
				}
			}
			
		}else if ("findOrderZone".equals(operator)) {// 查找
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
		}else if("confirm".equals(operator)){
			JsonDTO json = new JsonDTO();
			
			String username = request.getParameter("username");
			String userphone = request.getParameter("userphone");
			String zoneId = request.getParameter("zoneId");
			String typeId = request.getParameter("typeId");
			String houseDes = request.getParameter("houseDes");
			String budget=request.getParameter("budget");

			String houseInfo = request.getParameter("houseInfo");
			String isReady = request.getParameter("isReady");
			String isNew = request.getParameter("isNew");
			String readyDate = request.getParameter("readyDate");
			String houseLocation = request.getParameter("houseLocation");
			String designTime = request.getParameter("designTime");
			String phoneTime = request.getParameter("phoneTime");
			String customerTips = request.getParameter("customerTips");
			
			String callbackTips=request.getParameter("callbackTips");
			String serviceTips=request.getParameter("serviceTips");
			// 数据校验
			if (requiredId!=null&&requiredId.matches("\\d+")&&
					username != null && username.length() <= 20 && userphone != null && userphone.matches("\\d+")
					&& (zoneId==null||zoneId != null && zoneId.matches("\\d+")) && typeId != null && typeId.matches("\\d+")
					&& houseDes != null  && isNew != null && isNew.matches("\\d+")
					&& houseInfo != null && houseLocation != null && designTime != null && phoneTime != null
					&& isReady.matches("[01]")
					&& (budget==null||budget.matches("\\d+"))
					&& (("1".equals(isReady) || readyDate != null && readyDate.length() == 10))) {
				RequireDTO old=requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if(old==null||old.getStatus()!=3){
					return "";//非法数据拦截
				}
				
				Require updateParam = new Require();
				updateParam.setRequiredId(Integer.parseInt(requiredId));
				updateParam.setUsername(username);
				updateParam.setUserphone(userphone);
				if(zoneId!=null&&zoneId.matches("\\d+")){
					updateParam.setZoneId(Integer.parseInt(zoneId));
				}
				if(budget!=null){
					updateParam.setBudget(Integer.parseInt(budget));
				}
				updateParam.setHouseTypeId(Integer.parseInt(typeId));
				updateParam.setHouseDes(houseDes);
				updateParam.setHouseInfo(houseInfo);
				updateParam.setIsNew(Integer.parseInt(isNew));
				updateParam.setIsReady(Integer.parseInt(isReady));
				if (readyDate != null && readyDate.length() == 10) {
					try {
						updateParam.setReadyDate(format.parse(readyDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				updateParam.setHouseLocation(houseLocation);
				updateParam.setDesignTime(designTime);
				updateParam.setPhoneTime(phoneTime);
				updateParam.setCustomerTips(customerTips);
				
				updateParam.setCreateUserId(getLoginAdminUser(request).getAdminUserId());

				updateParam.setServiceTips(serviceTips);
				updateParam.setCallbackTips(callbackTips);
				
				updateParam.setStatus(4);//变为待分单状态
				
				updateParam.setOperatorLog(old.getOperatorLog()+" <br/> "+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+" 由 "
						+getLoginAdminUser(request).getNickname()+" 确认客户修改的订单消息，并提交");
				try {
					requireService.update(updateParam);
					json.setStatus(1).setMessage("需求确认成功");
				} catch (Exception e) {
					json.setStatus(0).setMessage("需求确认过程中。系统出现异常");
					e.printStackTrace();
				}

			} else {
				json.setStatus(0).setMessage("数据异常");
			}
			model.addAttribute("json", JSONUtil.object2json(json));
			return JSON;
		
		}
		return "admin/requirePublish_confirm";
	}

}
