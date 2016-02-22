package com.qicai.controller.minisite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qicai.bean.bisiness.HouseType;
import com.qicai.bean.bisiness.Require;
import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.bisiness.HouseTypeDTO;
import com.qicai.dto.bisiness.RequireDTO;
import com.qicai.dto.bisiness.ZoneSetDTO;
import com.qicai.util.JSONUtil;
import com.qicai.util.PasswordUtil;

/**
 * 活动专用controller
 * 
 * @author Administrator
 */
@Controller
@RequestMapping(value = "mobile")
public class WechatMainController extends BaseController {

	// 量房申请
	@RequestMapping(value = "/requireUpdate.html")
	public String requireUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
		JsonDTO json=new JsonDTO();
		
		String status = request.getParameter("operator");
//		String username = request.getParameter("username");
		String houseInfo = request.getParameter("houseInfo");
		String zoneId = request.getParameter("zoneId");
		String houseLocation = request.getParameter("houseLocation");
		String type = request.getParameter("type");
		String houseDes = request.getParameter("houseDes");
		String budget = request.getParameter("budget");
		String isNew = request.getParameter("isNew");
		String isReady = request.getParameter("isReady");
		String readyDate = request.getParameter("readyDate");
		String designTime = request.getParameter("designTime");
		String phoneTime = request.getParameter("phoneTime");
		String customerTips = request.getParameter("customerTips");

		String userphone = request.getParameter("userphone");// 手机
		String requiredId = request.getParameter("requiredId");// ID
		String sign = request.getParameter("sign");
		if (userphone != null && userphone.matches("\\d+") && requiredId != null && requiredId.matches("\\d+")
				&& sign != null) {
			String signBak = PasswordUtil.MD5(userphone + requiredId);
			if (sign.equals(signBak) || true) {// 数据验证成功
				model.addAttribute("userphone", userphone);
				model.addAttribute("requiredId", requiredId);
				model.addAttribute("sign", sign);

				Require param = new Require();
				param.setUserphone(userphone);
				param.setRequiredId(Integer.parseInt(requiredId));
				RequireDTO require = requireService.getByParam(param);
				if(require!=null){
					if(require.getStatus()==2){//前一个状态是打开连接
						if("change".equals(status)){
							param.setStatus(3);
							param.setHouseInfoBak(houseInfo);
							param.setHouseLocationBak(houseLocation);
							param.setPhoneTimeBak(phoneTime);
							if(zoneId!=null&&zoneId.matches("\\d+"))
								param.setZoneIdBak(Integer.parseInt(zoneId));
							
							if(type!=null&&type.matches("\\d+"))
								param.setHouseTypeIdBak(Integer.parseInt(type));
							if(budget!=null&&budget.matches("\\d+"))
								param.setBudgetBak(Integer.parseInt(budget));
							if(houseDes!=null)
								param.setHouseDesBak(houseDes);
							
							if(isNew!=null&&isNew.matches("\\d+"))
								param.setIsNew(Integer.parseInt(isNew));
							
							if(isReady!=null&&isReady.matches("\\d+"))
								param.setIsReady(Integer.parseInt(isReady));
							
							if(readyDate!=null&&readyDate.length()==10){
								try {
									param.setReadyDateBak(
											new SimpleDateFormat("yyyy-MM-dd").parse(readyDate));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
								
								param.setDesignTimeBak(designTime);
								
								param.setCustomerTipsBak(customerTips);
							
							
							param.setOperatorLog(require.getOperatorLog() + "<br/> " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
							+ "客户修改信息并提交 ！");
						}else{
							param.setStatus(4);
							param.setOperatorLog(require.getOperatorLog() + "<br/> " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
							+ "客户直接确认信息并提交。");
						}
						try {
							requireService.update(param);
							json.setStatus(1).setMessage("更新成功");
						} catch (Exception e) {
							e.printStackTrace();
						}// 更新
					}else{
						json.setStatus(0).setMessage("您的请求数据已经提交！");
					}
					model.addAttribute(JSON, JSONUtil.object2json(json));
				}
			}
		}

		return JSON;
	}

	// 量房申请
	@RequestMapping(value = "/requireConfirm.html")
	public String requireComfirm(HttpServletRequest request, HttpServletResponse response, Model model) {
		String userphone = request.getParameter("userphone");// 手机
		String requiredId = request.getParameter("requiredId");// ID
		String sign = request.getParameter("sign");
		if (userphone != null && userphone.matches("\\d+") && requiredId != null && requiredId.matches("\\d+")
				&& sign != null) {
			String signBak = PasswordUtil.MD5(userphone + requiredId);
			if (sign.equals(signBak) || true) {// 数据验证成功
				model.addAttribute("userphone", userphone);
				model.addAttribute("requiredId", requiredId);
				model.addAttribute("sign", sign);

				Require param = new Require();
				param.setUserphone(userphone);
				param.setRequiredId(Integer.parseInt(requiredId));
				RequireDTO require = requireService.getByParam(param);
				model.addAttribute("require", require);
				// 查找所有房型，
				HouseType houseType=new HouseType();
				houseType.setStatus(1);
				List<HouseTypeDTO> types = houseTypeService.getListByParam(houseType);
				model.addAttribute("types", types);

				// 查找所有的区域
				ZoneSet zoneparam = new ZoneSet();
				zoneparam.setParentId(0);
				zoneparam.setStatus(1);
				List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zoneparam);
				model.addAttribute("zones", zones);

				ZoneSetDTO zone = zoneSetService.getById(require.getZone().getZoneId());
				zoneparam.setParentId(zone.getParent().getZoneId());
				model.addAttribute("parentId", zone.getParent().getZoneId());

				List<ZoneSetDTO> children = zoneSetService.getZoneSetByParam(zoneparam);
				model.addAttribute("children", children);

				if (require != null && require.getStatus() == 1) {// 如果是发送客户的状态，则添加打开链接日志
					param.setStatus(2);// 客户打开连接
					param.setOperatorLog(require.getOperatorLog() + "<br/> " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
							+ "客户打开短信链接");
					try {
						requireService.update(param);// 更新
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
		}
		return "mobile/designApply";
	}

	@RequestMapping(value = "/findZones.html")
	public String findZones(HttpServletRequest request, HttpServletResponse response, Model model) {
		String parentId = request.getParameter("parentId");
		if (parentId != null && parentId.matches("\\d+")) {
			// 查找所有的区域
			ZoneSet zoneparam = new ZoneSet();
			zoneparam.setStatus(1);
			zoneparam.setParentId(Integer.parseInt(parentId));
			List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zoneparam);
			JsonDTO json = new JsonDTO();
			json.setData(zones).setStatus(1);

			model.addAttribute(JSON, JSONUtil.object2json(json));
		}
		return JSON;
	}

}
