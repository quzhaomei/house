package com.qicai.controller.bisiness;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qicai.annotation.LimitTag;
import com.qicai.bean.bisiness.HouseType;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.admin.RoleManagerDTO;
import com.qicai.dto.bisiness.HouseTypeDTO;
import com.qicai.util.JSONUtil;

/**
 * 房型管理
 * 
 * @author qzm
 * @since 2015-8-31
 */
@Controller
@RequestMapping(value = "houseType")
@LimitTag(uri = true)
public class HouseTypeController extends BaseController {
	// 首页查询所有房型
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<HouseTypeDTO> houses = houseTypeService.getAllHouseType();
		model.addAttribute("datas", houses);
		return "admin/houseType";
	}

	// 负责所有的查询逻辑以及ajax
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String operator = request.getParameter("operator");
		JsonDTO json = new JsonDTO();
		if (FIND_BY_ID.equals(operator)) {// 获取当个
			String roleId = request.getParameter("roleId");
			if (roleId != null && roleId.matches("\\d+")) {

				Integer id = Integer.parseInt(roleId);
				RoleManagerDTO role = roleManagerService.getById(id);
				json.setStatus(1).setData(role);
				model.addAttribute("json", JSONUtil.object2json(json));
			}
		} else {
			json.setStatus(0).setMessage("参数异常");
			model.addAttribute("json", JSONUtil.object2json(json));
		}
		return JSON;
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		AdminUserDTO user=(AdminUserDTO) request.getSession().getAttribute(ADMIN_USER_SESSION);
		JsonDTO json = new JsonDTO();
		String name = request.getParameter("name");
		String price = request.getParameter("price");
		if (name != null && name.length() <= 50&& price!=null&&price.matches("\\d+")) {
			HouseType saveParam = new HouseType();
			saveParam.setName(name);
			saveParam.setPrice(Integer.parseInt(price));
			saveParam.setStatus(1);
			saveParam.setCreateUserId(user.getAdminUserId());
			saveParam.setCreateDate(new Date());
			try {
				houseTypeService.saveOrUpdate(saveParam);
				json.setStatus(1).setMessage("新建房型成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.setStatus(0).setMessage("保存房型时，发生错误:" + e.getMessage());
			}
			model.addAttribute("json", JSONUtil.object2json(json));
			return JSON;
		}else{
			json.setStatus(0).setMessage("数据异常！");
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
		String typeId = request.getParameter("typeId");
		String price = request.getParameter("price");
		String status = request.getParameter("status");
		
		if (FIND_BY_ID.equals(operator)) {// 获取当个
			if (typeId != null && typeId.matches("\\d+")) {

				Integer id = Integer.parseInt(typeId);
				HouseTypeDTO role = houseTypeService.getById(id);
				json.setStatus(1).setData(role);
				model.addAttribute("json", JSONUtil.object2json(json));
				return JSON;
			}
		} else if (typeId != null && typeId.matches("\\d+")
				&& (status != null || name != null||typeId!=null||price!=null)) {
			// 检测
			if ((name == null || name.length() <= 50)
					&& (status == null || status.matches("\\d+"))
					&& (price == null || price.matches("\\d+"))) {
				Integer typeIdInt = Integer.parseInt(typeId);
				Integer statusInt = null;
				if (status != null) {
					statusInt = Integer.parseInt(status);
				}
				HouseType updateParam = new HouseType();
				updateParam.setTypeId(typeIdInt);
				updateParam.setStatus(statusInt);
				updateParam.setUpdateDate(new Date());
				updateParam.setUpdateUserId(user.getAdminUserId());
				updateParam.setName(name);
				if (price != null) {
					updateParam.setPrice(Integer.parseInt(price));
				}
				try {
					houseTypeService.saveOrUpdate(updateParam);
					json.setStatus(1).setMessage("更新房型成功");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("更新房型失败:" + e.getMessage());
				}
				model.addAttribute("json", JSONUtil.object2json(json));
				return JSON;
			}
		}
		return null;
	}

	// 切换状态
	@RequestMapping(value = "/status")
	public String status(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		JsonDTO json = new JsonDTO();
		String typeId = request.getParameter("typeId");
		String status = request.getParameter("status");
		if (typeId != null && typeId.matches("\\d+") && status != null
				&& status.matches("[01]")) {
			// 检测
			Integer typeIdInt = Integer.parseInt(typeId);
			Integer statusInt = null;
			if (status != null) {
				statusInt = Integer.parseInt(status);
			}
			HouseType updateParam = new HouseType();
			updateParam.setTypeId(typeIdInt);
			updateParam.setStatus(statusInt);
			try {
				houseTypeService.saveOrUpdate(updateParam);
				json.setStatus(1).setMessage("更新房型成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.setStatus(0).setMessage("更新房型失败:" + e.getMessage());
			}
			model.addAttribute("json", JSONUtil.object2json(json));
			return "json";
		}
		return null;
	}

}
