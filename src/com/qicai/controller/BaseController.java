package com.qicai.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.qicai.service.AdminUserService;
import com.qicai.service.DesignApplyService;
import com.qicai.service.MenuManagerService;
import com.qicai.service.RoleManagerService;
import com.qicai.service.RoleToMenusService;
import com.qicai.service.WechatUserService;
import com.qicai.util.OpenIdUtil;
import com.qicai.util.SqlTimeTest;

/**
 * @author qzm
 * @since 2015-5-13
 */
@Controller
public class BaseController {
	
	@Resource
	protected SystemInit init;//ϵͳ��ʼ���趨
	//sql���ܲ���
	protected SqlTimeTest sqlTime;
	@Resource
	protected OpenIdUtil openIdUtil;//�˵�����service
	
	/*** ������ʼ*/
	public static final String  TO_ADD="toAdd";
	public static final String  ADD="add";
	public static final String TO_EDIT="toEdit";
	public static final String  EDIT="edit";
	public static final String  DEL="del";
	public static final String  JSON="json";
	public static final String  LIST="list";
	public static final String  CHECK="check";
	public static final String  RESET="reset";
	public static final String  FIND_BY_ID="findById";
	
	
	public static final String  ADMIN_USER_SESSION="adminUserSession";
	public static final String  USER_SESSION="userSession";
	public static final String  LOGIN_TAG="loginTag";//��½״̬cookie��
	public static final String  AJAX="ajax";//ajax���ʡ�
	
	/*** ��������*/
	
	
	@Resource
	protected MenuManagerService menuManagerService;//�˵�����
	
	@Resource
	protected RoleManagerService roleManagerService;//��ɫ����
	
	@Resource
	protected RoleToMenusService roleToMenusService;//Ȩ�޹���
	
	@Resource
	protected AdminUserService adminUserService;//��̨�û�����
	
	@Resource
	protected WechatUserService wechatUserService;//΢���û�
	
	@Resource
	protected DesignApplyService applyService;//��������
	
	
}
