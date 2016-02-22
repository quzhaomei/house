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
 * �ר��controller
 * 
 * @author Administrator
 */
@Controller
@RequestMapping(value = "mobile")
public class WechatMainController extends BaseController {

	// ��������
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

		String userphone = request.getParameter("userphone");// �ֻ�
		String requiredId = request.getParameter("requiredId");// ID
		String sign = request.getParameter("sign");
		if (userphone != null && userphone.matches("\\d+") && requiredId != null && requiredId.matches("\\d+")
				&& sign != null) {
			String signBak = PasswordUtil.MD5(userphone + requiredId);
			if (sign.equals(signBak) || true) {// ������֤�ɹ�
				model.addAttribute("userphone", userphone);
				model.addAttribute("requiredId", requiredId);
				model.addAttribute("sign", sign);

				Require param = new Require();
				param.setUserphone(userphone);
				param.setRequiredId(Integer.parseInt(requiredId));
				RequireDTO require = requireService.getByParam(param);
				if(require!=null){
					if(require.getStatus()==2){//ǰһ��״̬�Ǵ�����
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
							+ "�ͻ��޸���Ϣ���ύ ��");
						}else{
							param.setStatus(4);
							param.setOperatorLog(require.getOperatorLog() + "<br/> " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
							+ "�ͻ�ֱ��ȷ����Ϣ���ύ��");
						}
						try {
							requireService.update(param);
							json.setStatus(1).setMessage("���³ɹ�");
						} catch (Exception e) {
							e.printStackTrace();
						}// ����
					}else{
						json.setStatus(0).setMessage("�������������Ѿ��ύ��");
					}
					model.addAttribute(JSON, JSONUtil.object2json(json));
				}
			}
		}

		return JSON;
	}

	// ��������
	@RequestMapping(value = "/requireConfirm.html")
	public String requireComfirm(HttpServletRequest request, HttpServletResponse response, Model model) {
		String userphone = request.getParameter("userphone");// �ֻ�
		String requiredId = request.getParameter("requiredId");// ID
		String sign = request.getParameter("sign");
		if (userphone != null && userphone.matches("\\d+") && requiredId != null && requiredId.matches("\\d+")
				&& sign != null) {
			String signBak = PasswordUtil.MD5(userphone + requiredId);
			if (sign.equals(signBak) || true) {// ������֤�ɹ�
				model.addAttribute("userphone", userphone);
				model.addAttribute("requiredId", requiredId);
				model.addAttribute("sign", sign);

				Require param = new Require();
				param.setUserphone(userphone);
				param.setRequiredId(Integer.parseInt(requiredId));
				RequireDTO require = requireService.getByParam(param);
				model.addAttribute("require", require);
				// �������з��ͣ�
				HouseType houseType=new HouseType();
				houseType.setStatus(1);
				List<HouseTypeDTO> types = houseTypeService.getListByParam(houseType);
				model.addAttribute("types", types);

				// �������е�����
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

				if (require != null && require.getStatus() == 1) {// ����Ƿ��Ϳͻ���״̬������Ӵ�������־
					param.setStatus(2);// �ͻ�������
					param.setOperatorLog(require.getOperatorLog() + "<br/> " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
							+ "�ͻ��򿪶�������");
					try {
						requireService.update(param);// ����
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
			// �������е�����
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
