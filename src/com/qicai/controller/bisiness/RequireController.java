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
import com.qicai.bean.admin.AdminUser;
import com.qicai.bean.bisiness.Require;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.PageDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.bisiness.RequireDTO;
import com.qicai.dto.bisiness.ServiceUserDTO;
import com.qicai.util.JSONUtil;

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
	protected  SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	// ��ҳ��ѯ�������󣬰�������״̬��
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		//��ѯ
				String pageIndex = request.getParameter("pageIndex");// ҳ��
				String pageSize = request.getParameter("pageSize");// ҳ����
				
				String startDate=request.getParameter("startDate");
				String endDate=request.getParameter("endDate");
				String status=request.getParameter("status");
				String username=request.getParameter("username");
				String userId=request.getParameter("userId");
				String serviceStartDate=request.getParameter("serviceStartDate");
				String serviceEndDate=request.getParameter("serviceEndDate");
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
						if(userId!=null&&userId.matches("\\d+")){
							selectParam.setUserId(Integer.parseInt(userId));
						}
						if(status!=null&&status.matches("\\d+")){
							selectParam.setStatus(Integer.parseInt(status));
						}
						if(startDate!=null&&startDate.length()==10){
							try {
								selectParam.setStartDate(format.parse(startDate));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						if(endDate!=null&&endDate.length()==10){
							try {
								Date endTime=format.parse(endDate);
								endTime.setDate(endTime.getDate()+1);
								selectParam.setEndDate(endTime);
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						if(serviceStartDate!=null&&serviceStartDate.length()==10){
							try {
								selectParam.setServiceStartDate(format.parse(serviceStartDate));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						if(serviceEndDate!=null&&serviceEndDate.length()==10){
							try {
								Date serviceEndTime=format.parse(serviceEndDate);
								serviceEndTime.setDate(serviceEndTime.getDate()+1);
								selectParam.setServiceEndDate(serviceEndTime);
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						
						page.setParam(selectParam);
						
						PageDTO<List<RequireDTO>> pageDate = 
								requireService.findListByPage(page);
						
						
						model.addAttribute("pageResult", pageDate);
						return "admin/requireManager";
					}
				}

				return "admin/requireManager";

	}
	//�鿴��ϸ
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requiredId=request.getParameter("requiredId");
		if(requiredId!=null&&requiredId.matches("\\d+")){
			RequireDTO require=requireService.getByParam(new Require(Integer.parseInt(requiredId)));
			model.addAttribute("require", require);
		}
		return "admin/requireManager_list";
	}
	
	@RequestMapping(value = "/toService")//�ɵ�
	public String toService(HttpServletRequest request, HttpServletResponse response, Model model) {
		String operator=request.getParameter("operator");
		String requiredId=request.getParameter("requiredId");
		if("to_service".equals(operator)){//��ѯָ������Ա
			if(requiredId!=null&&requiredId.matches("\\d+")){
				RequireDTO require=requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				if(require!=null&&require.getStatus()==6){//���ֵ�״̬
					model.addAttribute("require", require);
					String username=request.getParameter("username");
					String userphone=request.getParameter("userphone");
					
					String pageIndex = request.getParameter("pageIndex");// ҳ��
					String pageSize = request.getParameter("pageSize");// ҳ����
					//��ѯ���е�ҵ��Ա
					if (pageIndex == null) {
						pageIndex = "1";
					}
					if (pageSize == null) {
					//	pageSize = "2";
						pageSize = "10";
					}
					AdminUser param=new AdminUser();
					param.setPhone(userphone);
					param.setNickname(username);
					
					PageDTO<AdminUser> page=new PageDTO<AdminUser>();
					page.setPageSize(Integer.parseInt(pageSize));
					page.setPageIndex(Integer.parseInt(pageIndex));
					page.setParam(param);
					
					PageDTO<List<ServiceUserDTO>> pageDate=
							adminUserService.getServiceByParam(page, SERVICE_ROLE_ID);
					
					model.addAttribute("pageResult", pageDate);
				}
				
				
			}
		}else if("doService".equals(operator)){
			String userId=request.getParameter("userId");
			if(requiredId!=null&&requiredId.matches("\\d+")&&
					userId!=null&&userId.matches("\\d+")){//������֤
				RequireDTO require=requireService.getByParam(new Require(Integer.parseInt(requiredId)));
				AdminUserDTO user=adminUserService.getById(Integer.parseInt(userId));
				JsonDTO json=new JsonDTO();
				json.setStatus(0);
				if(require!=null&&require.getStatus()==6&&user!=null){//���ֵ�״̬
					Require updateParam=new Require();
					updateParam.setRequiredId(Integer.parseInt(requiredId));
					updateParam.setStatus(7);
					
					updateParam.setServiceDate(new Date());
					updateParam.setServiceUserId(Integer.parseInt(userId));
					updateParam.setOperatorLog(require.getOperatorLog()+" <br/> "+
							new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) +" �� "+getLoginAdminUser(request).getNickname()+" ����� "+
					user.getNickname());
					try {
						requireService.update(updateParam);
						json.setStatus(1).setMessage("�ֵ��ɹ���");
					} catch (Exception e) {
						json.setStatus(0).setMessage("�ֵ������У�ϵͳ�����쳣");
						e.printStackTrace();
					}
				}else{
					json.setMessage("�����쳣");
				}
				model.addAttribute(JSON, JSONUtil.object2json(json));
			}
			return JSON;
		}
		return "admin/requireManager_to_service";
	}
}
