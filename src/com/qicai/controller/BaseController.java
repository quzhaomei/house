package com.qicai.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.service.AdminUserService;
import com.qicai.service.BalanceHistoryService;
import com.qicai.service.DesignApplyService;
import com.qicai.service.HouseTypeService;
import com.qicai.service.MenuManagerService;
import com.qicai.service.RoleManagerService;
import com.qicai.service.RoleToMenusService;
import com.qicai.service.StoreService;
import com.qicai.service.WechatUserService;
import com.qicai.service.ZoneSetService;
import com.qicai.util.OpenIdUtil;
import com.qicai.util.SqlTimeTest;

/**
 * @author qzm
 * @since 2015-5-13
 */
@Controller
public class BaseController {
	
	@Resource
	protected SystemInit init;//系统初始化设定
	//sql性能测试
	protected SqlTimeTest sqlTime;
	@Resource
	protected OpenIdUtil openIdUtil;//菜单管理service
	
	/*** 常量开始*/
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
	public static final String  LOGIN_TAG="loginTag";//登陆状态cookie。
	public static final String  AJAX="ajax";//ajax访问。
	
	public static final Integer  KEEPER_ROLE_ID=3;//商户负责人ID
	/**工具方法,获取当前登陆的管理员信息**/
	protected AdminUserDTO getLoginAdminUser(HttpServletRequest request){
		return (AdminUserDTO) request.getSession().getAttribute(ADMIN_USER_SESSION);
	}
	/*** 常量结束*/
	
	@Resource
	protected MenuManagerService menuManagerService;//菜单管理
	
	@Resource
	protected RoleManagerService roleManagerService;//角色管理
	
	@Resource
	protected RoleToMenusService roleToMenusService;//权限管理
	
	@Resource
	protected AdminUserService adminUserService;//后台用户管理
	
	@Resource
	protected WechatUserService wechatUserService;//微信用户
	
	@Resource
	protected DesignApplyService applyService;//量房申请
	
	@Resource
	protected ZoneSetService zoneSetService;//区域设置
	
	@Resource
	protected HouseTypeService houseTypeService;//房型设置
	
	@Resource
	protected StoreService storeService;//房型设置
	
	@Resource
	protected BalanceHistoryService balanceHistoryService;//账户消费记录
}
