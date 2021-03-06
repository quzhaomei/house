package com.qicai.controller.bisiness;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.qicai.annotation.LimitTag;
import com.qicai.bean.bisiness.HouseType;
import com.qicai.bean.bisiness.Require;
import com.qicai.bean.bisiness.RequireRemark;
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
import com.qicai.util.ShortUrlUtil;
import com.qicai.util.UuidUtils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

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
		
		
		String specialStartDate = request.getParameter("specialStartDate");
		String specialEndDate = request.getParameter("specialEndDate");
		
		String specialStatus=request.getParameter("specialStatus");//特殊状态 0 关闭，1待跟进
		
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
				
				if (specialStartDate != null && specialStartDate.length() == 10) {
					try {
						selectParam.setSpecialStartDate(format.parse(specialStartDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (specialEndDate != null && specialEndDate.length() == 10) {
					try {
						Date specialEndDateTime = format.parse(specialEndDate);
						specialEndDateTime.setDate(specialEndDateTime.getDate() + 1);
						selectParam.setSpecialEndDate(specialEndDateTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				selectParam.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
				if(specialStatus!=null&&specialStatus.matches("\\d+")){
					selectParam.setSpecialStatus(Integer.parseInt(specialStatus));
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
		String operator=request.getParameter("operator");
		
		if (requiredId != null && requiredId.matches("\\d+")) {
			RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
			model.addAttribute("require", require);
			if("json".equals(operator)){
				model.addAttribute("json", JSONUtil.object2json(require));
				return JSON;
			}
		}
		return "admin/requirePublish_list";
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

			String designType = request.getParameter("designType");// 装修方式
			String designStyle = request.getParameter("designStyle");// 装修风格
			String houseStatus = request.getParameter("houseStatus");// 房屋状态
			// 数据校验
			if (username != null && username.length() <= 20 && userphone != null && userphone.matches("\\d+")
					&& zoneId != null && zoneId.matches("\\d+") && typeId != null && typeId.matches("\\d+")
					&& houseDes != null && isNew != null && isNew.matches("\\d+") && budget != null && houseInfo != null
					&& houseLocation != null && designTime != null && phoneTime != null && isReady.matches("[01]")
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
				saveParam.setBudget(budget);
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
				saveParam.setStatus(1);// 后台新建预约中->发送短信
				saveParam.setCreateDate(new Date());
				saveParam.setCreateUserId(getLoginAdminUser(request).getAdminUserId());

				saveParam.setOperatorLog(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
						+ getLoginAdminUser(request).getNickname() + " 创建预约");

				saveParam.setDesignType(designType);
				saveParam.setDesignStyle(designStyle);
				saveParam.setHouseStatus(houseStatus);
				try {
					requireService.save(saveParam);

					// 发送短信
					String sign = PasswordUtil.MD5(userphone + saveParam.getRequiredId());
					String host = request.getRequestURL().toString();
					host = host.substring(0, host.lastIndexOf("/"));
					host = host.substring(0, host.lastIndexOf("/"));
					host += "/mobile/requireConfirm.html?requiredId=" + saveParam.getRequiredId() + "&userphone="
							+ userphone + "&sign=" + sign;
					String url = host;
					url = ShortUrlUtil.getShotUrl(url);
					// TODO 创建时 发送链接
					String content = "尊敬的会员您好：感谢您预约免费量房，注册还可以领取装修礼包 " + url + " ,回复TD退订。";
					MessageSender.sendMsg(userphone, content);

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
				if (require.getStatus() == 0 || require.getStatus() == 1 || require.getStatus() == 2
						|| require.getStatus() == 3 || require.getStatus() == 4) {// 未发布的才能更改
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

			String designType = request.getParameter("designType");
			String designStyle = request.getParameter("designStyle");
			String houseStatus = request.getParameter("houseStatus");
			// 数据校验
			if (requiredId != null && requiredId.matches("\\d+") && budget != null && username != null
					&& username.length() <= 20 && userphone != null && userphone.matches("\\d+")
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
				updateParam.setBudget(budget);
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

				updateParam.setDesignStyle(designStyle);
				updateParam.setDesignType(designType);
				updateParam.setHouseStatus(houseStatus);
				try {
					requireService.update(updateParam);
					json.setStatus(1).setMessage("预约更新成功");

				} catch (Exception e) {
					json.setStatus(0).setMessage("更新过程中。系统出现异常");
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
			if (old != null && (old.getStatus() == 0 || old.getStatus() == 1 || old.getStatus() == 2
					|| old.getStatus() == 3 || old.getStatus() == 4)) {
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
		JsonDTO json = new JsonDTO();
		if (requiredId != null && requiredId.matches("\\d+")) {
			RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
			if (require != null && (require.getStatus() == 1 || require.getStatus() == 0)) {
				Require updateParam = new Require();
				updateParam.setRequiredId(Integer.parseInt(requiredId));
				updateParam.setStatus(1);// 重置为发送短信的状态
				updateParam.setOperatorLog(require.getOperatorLog() + " <br/> "
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
						+ getLoginAdminUser(request).getNickname() + " 发送短信给客户");
				try {
					String sign = PasswordUtil.MD5(require.getUserphone() + requiredId);
					String host = request.getRequestURL().toString();
					host = host.substring(0, host.lastIndexOf("/"));
					host = host.substring(0, host.lastIndexOf("/"));
					host += "/mobile/requireConfirm.html?requiredId=" + Integer.parseInt(requiredId) + "&userphone="
							+ require.getUserphone() + "&sign=" + sign;
					String url = host;
					url = ShortUrlUtil.getShotUrl(url);
					String content = "尊敬的会员您好：感谢您预约免费量房，注册还可以领取装修礼包 " + url + " ,回复TD退订。";
					HttpSendResult result = MessageSender.sendMsg(require.getUserphone(), content);
					if (result.getIsSuccess()) {
						requireService.update(updateParam);
						json.setStatus(1).setMessage("发送短信成功");
					} else {
						json.setStatus(0).setMessage(result.getInfo());
					}
				} catch (Exception e) {
					json.setStatus(0).setMessage("发送短信失败");
					e.printStackTrace();
				}
			} else {
				json.setStatus(0).setMessage("数据异常！");
			}
		} else {
			json.setStatus(0).setMessage("数据丢失，请刷新页面");
		}
		model.addAttribute(JSON, JSONUtil.object2json(json));
		return JSON;
	}

	// 确认
	@RequestMapping(value = "/confirm")
	public String confirm(HttpServletRequest request, HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		String requiredId = request.getParameter("requiredId");
		if ("to_confirm".equals(operator)) {// 跳转到确认页面
			if (requiredId != null && requiredId.matches("\\d+")) {
				RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				// 判断状态
				if (require != null && require.getStatus() == 3) {
					// 查找所有房屋类型
					HouseType houseType = new HouseType();
					houseType.setStatus(1);
					List<HouseTypeDTO> houses = houseTypeService.getListByParam(houseType);
					model.addAttribute("houses", houses);

					// 查找所有的区域
					ZoneSet zone = new ZoneSet();
					zone.setParentId(0);
					zone.setStatus(1);
					List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zone);
					model.addAttribute("zones", zones);

					model.addAttribute("require", require);
				}
			}

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
		} else if ("confirm".equals(operator)) {
			JsonDTO json = new JsonDTO();

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

			String callbackTips = request.getParameter("callbackTips");
			String serviceTips = request.getParameter("serviceTips");

			String designType = request.getParameter("designType");
			String designStyle = request.getParameter("designStyle");
			String houseStatus = request.getParameter("houseStatus");
			// 数据校验
			if (requiredId != null && requiredId.matches("\\d+") && username != null && username.length() <= 20
					&& userphone != null && userphone.matches("\\d+")
					&& (zoneId == null || zoneId != null && zoneId.matches("\\d+")) && typeId != null
					&& typeId.matches("\\d+") && houseDes != null && isNew != null && isNew.matches("\\d+")
					&& houseInfo != null && houseLocation != null && designTime != null && phoneTime != null
					&& isReady.matches("[01]")

					&& (("1".equals(isReady) || readyDate != null && readyDate.length() == 10))) {
				RequireDTO old = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if (old == null || old.getStatus() != 3) {
					return "";// 非法数据拦截
				}

				Require updateParam = new Require();
				updateParam.setRequiredId(Integer.parseInt(requiredId));
				updateParam.setUsername(username);
				updateParam.setUserphone(userphone);
				if (zoneId != null && zoneId.matches("\\d+")) {
					updateParam.setZoneId(Integer.parseInt(zoneId));
				}
				if (budget != null) {
					updateParam.setBudget(budget);
				}
				updateParam.setHouseTypeId(Integer.parseInt(typeId));
				updateParam.setHouseDes(houseDes);
				updateParam.setHouseInfo(houseInfo);
				updateParam.setIsNew(Integer.parseInt(isNew));
				updateParam.setIsReady(Integer.parseInt(isReady));

				updateParam.setHouseStatus(houseStatus);
				updateParam.setDesignStyle(designStyle);
				updateParam.setDesignType(designType);
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

				updateParam.setStatus(4);// 变为待分单状态

				updateParam.setOperatorLog(
						old.getOperatorLog() + " <br/> " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
								+ " 由 " + getLoginAdminUser(request).getNickname() + " 确认客户修改的订单消息，并提交");
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

	// 批量上传
	@RequestMapping(value = "/batchUpload")
	public String batchUpload(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "excell", required = false) MultipartFile excell) {
		// 上传文档
		if (excell != null) {
			try {
				Workbook rwb = Workbook.getWorkbook(excell.getInputStream());
				Sheet rst = rwb.getSheet(0);
				// 获取Sheet表中所包含的总列数
				List<Require> datas = new ArrayList<Require>();
				// 获取Sheet表中所包含的总行数
				int rsRows = rst.getRows();
				for (int row = 1; row < rsRows; row++) {
					Require temp = new Require();
					temp.setUsername(rst.getCell(0, row).getContents());
					temp.setUserphone(rst.getCell(1, row).getContents());
					datas.add(temp);
				}
				JsonDTO json = new JsonDTO();
				json.setStatus(1).setData(datas);
				model.addAttribute("json", JSONUtil.object2json(json));
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return JSON;
		}
		String operator = request.getParameter("operator");
		if ("toUpload".equals(operator)) {
			return "admin/requirePublish_batchUpload";
		} else if ("upload".equals(operator)) {
			JsonDTO json = new JsonDTO();

			String[] usernames = request.getParameterValues("username");
			String[] userphones = request.getParameterValues("userphone");
			for (int index = 0; index < usernames.length; index++) {

				Require require = new Require();
				require.setUserphone(userphones[index]);

				// 处理userId存在的问题
				List<RequireDTO> old = requireService.list(require);

				if (old != null && old.size() > 0) {
					require.setUserId(old.get(0).getUserId());
				} else {
					require.setUserId(UuidUtils.getUserId());
				}

				require.setUsername(usernames[index]);
				require.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
				require.setStatus(0);
				require.setCreateDate(new Date());

				require.setOperatorLog(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
						+ getLoginAdminUser(request).getNickname() + " 创建预约");

				try {
					requireService.save(require);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			json.setStatus(1).setMessage("导入成功");
			model.addAttribute("json", JSONUtil.object2json(json));
		}
		return JSON;
	}

	// 拨打电话
	@RequestMapping(value = "/call")
	public String call(HttpServletRequest request, HttpServletResponse response, Model model) {
		JsonDTO json = new JsonDTO();
		json.setStatus(0).setMessage("数据异常");
		String requiredId = request.getParameter("requiredId");
		if (requiredId != null && requiredId.matches("\\d+")) {
			RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
			if (require != null) {
				json.setStatus(1).setMessage("外呼中，请等待");
				json.setData(require.getUserphone());
			}
		}
		model.addAttribute("json", JSONUtil.object2json(json));
		return JSON;
	}
	
	// otherStatus 状态切换
	@RequestMapping(value = "/otherStatus")
	public String otherStatus(HttpServletRequest request, HttpServletResponse response, Model model) {
		String operator=request.getParameter("operator");
		String requiredId=request.getParameter("requiredId");
		JsonDTO json=new JsonDTO();
		if("toOtherStatus".equals(operator)){
			if(requiredId!=null&&requiredId.matches("\\d+")){
				RequireDTO require=requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if(require.getRemarks()==null&&require.getStatus()<=4){
					json.setStatus(1);
				}else{
					json.setStatus(0).setMessage("数据异常");
				}
				
			}
		}else if("otherStatus".equals(operator)){
			if(requiredId!=null&&requiredId.matches("\\d+")){
				RequireDTO require=requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if(require.getRemarks()==null&&require.getStatus()<=4){
					String remark=request.getParameter("remark");
					String nextcallTime=request.getParameter("nextcallTime");
					String status=request.getParameter("status");
					if(status!=null&&status.matches("\\d+")){
						RequireRemark saveRemark=new RequireRemark();
						saveRemark.setRequiredId(Integer.parseInt(requiredId));
						saveRemark.setStatus(Integer.parseInt(status));
						saveRemark.setRemark(remark);
						saveRemark.setCreateDate(new Date());
						if(nextcallTime!=null&&nextcallTime.length()==10){
							try {
								saveRemark.setNextTime(new SimpleDateFormat("yyyy-MM-dd")
										.parse(nextcallTime));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
							try {
								requireService.saveRemark(saveRemark);
								Require updateParam=new Require();
								updateParam.setRequiredId(Integer.parseInt(requiredId));
								updateParam.setOperatorLog(require.getOperatorLog()+" <br/> "+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
						+ getLoginAdminUser(request).getNickname() + "在发布前对需求进行了"+(Integer.parseInt(status)==0?"关闭":"待跟进")+"操作");
								
								requireService.update(updateParam);
								json.setStatus(1).setMessage("更新成功");
							} catch (Exception e) {
								json.setStatus(0).setMessage("更新过程中，数据出现异常");
								e.printStackTrace();
							}
						
						
					}else{
						json.setStatus(0).setMessage("数据异常");
					}
				}else{
						json.setStatus(0).setMessage("数据异常");
				}
				
			}
		}else if("openStatus".equals(operator)){
			if(requiredId!=null&&requiredId.matches("\\d+")){
				RequireDTO require=requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if(require.getRemarks()!=null&&require.getRemarks().getStatus()==1){
					json.setStatus(1);
					try {
						requireService.clearRemark(require.getRequiredId());
						Require updateParam=new Require();
						updateParam.setCreateDate(new Date());
						updateParam.setRequiredId(Integer.parseInt(requiredId));
						updateParam.setOperatorLog(require.getOperatorLog()+" <br/> "+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
				+ getLoginAdminUser(request).getNickname() + "在发布前对需求进行了启用操作");
						
						requireService.update(updateParam);
						json.setStatus(1).setMessage("更新成功");
					} catch (Exception e) {
						json.setStatus(0).setMessage("更新过程中，数据出现异常");
						e.printStackTrace();
					}
				}else{
					json.setStatus(0).setMessage("数据异常");
				}
				
				
			}
		}
		model.addAttribute("json", JSONUtil.object2json(json));
		
		return JSON;
	}
}
