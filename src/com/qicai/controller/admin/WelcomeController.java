package com.qicai.controller.admin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.qicai.annotation.LimitTag;
import com.qicai.bean.admin.AdminUser;
import com.qicai.controller.BaseController;
import com.qicai.dto.ImgUploadResult;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.admin.MenuManagerDTO;
import com.qicai.util.ImgUtil;
import com.qicai.util.JSONUtil;
import com.qicai.util.PasswordUtil;
import com.qicai.util.UuidUtils;

@Controller
@RequestMapping(value = "welcome")
@LimitTag
public class WelcomeController extends BaseController {
	// ��½����ת��ҳ��
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		return "admin/welcome";
	}

	// ��������
	// ��½����ת��ҳ��
	@RequestMapping(value = "/updatePsw")
	public String updatePsw(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		AdminUserDTO loginUser = (AdminUserDTO) request.getSession()
				.getAttribute(ADMIN_USER_SESSION);
		String oldPsw = request.getParameter("oldPsw");
		String newPsw = request.getParameter("newPsw");
		JsonDTO json = new JsonDTO();
		if (oldPsw != null && newPsw != null && oldPsw.length() <= 50
				&& newPsw.length() <= 50) {
			AdminUser adminUser = new AdminUser();
			adminUser.setAdminUserId(loginUser.getAdminUserId());
			adminUser.setPassword(PasswordUtil.MD5(oldPsw));
			loginUser = adminUserService.getUserByParam(adminUser);
			if (loginUser != null) {// ��֤�ɹ����޸�����
				adminUser.setPassword(PasswordUtil.MD5(newPsw));
				try {
					adminUserService.updateUserAndRole(adminUser, null);
					json.setStatus(1).setMessage("������³ɹ���");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("�������ʧ�ܣ���" + e.getMessage());
				}
				model.addAttribute("json", JSONUtil.object2json(json));
				return JSON;
			}
		}
		json.setStatus(0).setMessage("�������");
		model.addAttribute("json", JSONUtil.object2json(json));
		return "json";
	}

	// ����˵��ļ���
	@RequestMapping(value = "/menus")
	public String getMenus(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// ��session�л�ȡ���еĽ�ɫ��Ϣ
		AdminUserDTO userDTO = (AdminUserDTO) request.getSession()
				.getAttribute(ADMIN_USER_SESSION);
		Map<String, MenuManagerDTO> menusMap = userDTO.getMenuList();
		if (menusMap != null) {
			List<MenuManagerDTO> menus = null;

			String godPhone = request.getSession().getServletContext()
					.getInitParameter("godPhone");
			if (userDTO.getPhone()!=null&&userDTO.getPhone().equals(godPhone)) {
				menus = menuManagerService.getNavMenu();// ��ȡ���еĲ˵������ҳ��˵���
			} else {
				menus = new ArrayList<MenuManagerDTO>();
				menus.addAll(menusMap.values());
			}
			// ����ID����
			Collections.sort(menus, new Comparator<MenuManagerDTO>() {
				@Override
				public int compare(MenuManagerDTO menu1, MenuManagerDTO menu2) {
					return menu1.getMenuId() - menu2.getMenuId();
				}
			});

			model.addAttribute("pageMenus", menus);
		}
		return "admin/menu";
	}

	// �ϴ�ͼƬ
	@RequestMapping(value = "/uploadImg")
	public String uploadImg(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "imgFile") MultipartFile file, Model model) {
		String imgPath = init.getImgPath();
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		String title = request.getParameter("title");
		String align = request.getParameter("align");

		// ��֤�ϴ����Ƿ�ΪͼƬ
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (bi == null) { // ��ΪͼƬ
			ImgUploadResult imgUploadResult = new ImgUploadResult();
			imgUploadResult.setError(1);
			imgUploadResult.setMessage("��������ȷ��ʽ��ͼƬ");
			model.addAttribute("json", JSONUtil.object2json(imgUploadResult));
			return JSON;
		}
		if(width==null){
			width=bi.getWidth()+"";
		}
		if(height==null){
			height=bi.getHeight()+"";
		}
		File parent = new File(imgPath + "newImgs/");
		if (!parent.exists()) {
			parent.mkdirs();
		}
		String uuid=UuidUtils.getImgUUID();
		String filename = uuid + ".jpg";
		File target = new File(parent, filename);
		
		File mintarget = new File(parent, filename+"-mini");
		// ѹ��ͼƬ�ĵ�ַ
		ImgUtil.compressPic(bi, mintarget.getPath());
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), target);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// �ش����
		ImgUploadResult imgUploadResult = new ImgUploadResult();
		imgUploadResult.setError(0);
		imgUploadResult.setHeight(height);
		imgUploadResult.setWidth(width);
		imgUploadResult.setAlign(align);
		imgUploadResult.setTitle(title);
		imgUploadResult.setUrl("/imgStore/newImgs/" + filename);
		model.addAttribute("json", JSONUtil.object2json(imgUploadResult));

		return JSON;
	}

	// �ϴ���Դ
	@RequestMapping(value = "/uploadSource")
	public String uploadSource(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "sourceFile") MultipartFile file, Model model) {
		String orName=file.getOriginalFilename();
		String houzui="";
		if(orName.lastIndexOf(".")!=-1){
			houzui=orName.substring(orName.lastIndexOf("."));
		}
		String imgPath = init.getImgPath();
		File parent = new File(imgPath + "newSource/");
		if (!parent.exists()) {
			parent.mkdirs();
		}
		String filename = UuidUtils.getImgUUID()+houzui;
		File target = new File(parent, filename);
		// �������200K�������ѹ��
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// �ش����
		ImgUploadResult imgUploadResult = new ImgUploadResult();
		imgUploadResult.setError(0);
		imgUploadResult.setUrl("/imgStore/newSource/" + filename);
		model.addAttribute("json", JSONUtil.object2json(imgUploadResult));
		return JSON;
	}
}
