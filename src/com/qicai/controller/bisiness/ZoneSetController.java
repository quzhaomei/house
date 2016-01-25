package com.qicai.controller.bisiness;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qicai.annotation.LimitTag;
import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.admin.RoleManagerDTO;
import com.qicai.dto.bisiness.ZoneSetDTO;
import com.qicai.util.JSONUtil;

/**
 * 角色管理
 * 
 * @author qzm
 * @since 2015-8-31
 */
@Controller
@RequestMapping(value = "zoneSet")
@LimitTag(uri = true)
public class ZoneSetController extends BaseController {
	// 首页查询所有角色
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String parentId=request.getParameter("parentId");
		ZoneSet param=new ZoneSet();
		if(parentId!=null&&parentId.matches("\\d+")){
			param.setParentId(Integer.parseInt(parentId));
			model.addAttribute("parent", zoneSetService.getById(Integer.parseInt(parentId)));
		}else{//查询所有地区分类，地区分类
			param.setParentId(0);
		}
		List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(param);
		model.addAttribute("datas", zones);
		return "admin/zoneSet";
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
		String parentId=request.getParameter("parentId");
		if (name != null && name.length() <= 50) {
			ZoneSet saveParam = new ZoneSet();
			saveParam.setName(name);
			if(parentId!=null&&parentId.matches("\\d+")){
				saveParam.setParentId(Integer.parseInt(parentId));
			}
			saveParam.setStatus(1);
			saveParam.setCreateUserId(user.getAdminUserId());
			saveParam.setCreateDate(new Date());
			try {
				zoneSetService.saveOrUpdate(saveParam);
				json.setStatus(1).setMessage("新建区域成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.setStatus(0).setMessage("保存区域时，发生错误:" + e.getMessage());
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
		String zoneId = request.getParameter("zoneId");
		String status = request.getParameter("status");
		if (FIND_BY_ID.equals(operator)) {// 获取当个
			if (zoneId != null && zoneId.matches("\\d+")) {

				Integer id = Integer.parseInt(zoneId);
				ZoneSetDTO role = zoneSetService.getById(id);
				json.setStatus(1).setData(role);
				model.addAttribute("json", JSONUtil.object2json(json));
				return JSON;
			}
		} else if (zoneId != null && zoneId.matches("\\d+")
				&& (status != null || name != null)) {
			// 检测
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
					json.setStatus(1).setMessage("更新区域成功");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("更新区域失败:" + e.getMessage());
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
		String zoneId = request.getParameter("zoneId");
		String status = request.getParameter("status");
		if (zoneId != null && zoneId.matches("\\d+") && status != null
				&& status.matches("[01]")) {
			// 检测
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
				json.setStatus(1).setMessage("更新区域成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.setStatus(0).setMessage("更新区域失败:" + e.getMessage());
			}
			model.addAttribute("json", JSONUtil.object2json(json));
			return "json";
		}
		return null;
	}

}
