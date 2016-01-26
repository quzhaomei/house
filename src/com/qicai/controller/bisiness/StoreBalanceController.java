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
import com.qicai.bean.bisiness.BalanceHistory;
import com.qicai.bean.bisiness.Store;
import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.StoreDTO;
import com.qicai.dto.bisiness.ZoneSetDTO;
import com.qicai.util.JSONUtil;

/**
 * 商家充值管理
 * @author qzm
 * @since 2015-8-31
 */
@Controller
@RequestMapping(value = "balance")
@LimitTag(uri = true)
public class StoreBalanceController extends BaseController {
	protected  SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	// 首页查询所有店铺
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//查询
		String pageIndex = request.getParameter("pageIndex");// 页码
		String pageSize = request.getParameter("pageSize");// 页容量
		
		if (pageIndex == null) {
			pageIndex = "1";
		}
		if (pageSize == null) {
			pageSize = "10";
		}
		String zoneId=request.getParameter("zoneId");
		String status=request.getParameter("status");
		
		String storeName=request.getParameter("storeName");
		String keeperName=request.getParameter("keeperName");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		
		if (pageIndex.matches("\\d+") && pageSize.matches("\\d+")) {
			Integer pageIndexInt = Integer.parseInt(pageIndex);
			Integer pageSizeInt = Integer.parseInt(pageSize);
			PageDTO<Store> page = new PageDTO<Store>();
			if (pageIndexInt != 0 && pageSizeInt != 0) {
				page.setPageIndex(pageIndexInt);
				page.setPageSize(pageSizeInt);
				Store selectParam = new Store();
				if(zoneId!=null&&zoneId.matches("\\d+")){
					selectParam.setZoneId(Integer.parseInt(zoneId));
				}
				if(status!=null&&status.matches("[01]")){
					selectParam.setStatus(Integer.parseInt(status));
				}
				selectParam.setStoreName(storeName);
				selectParam.setKeeperName(keeperName);
				if(startDate!=null&&startDate.length()==10){
					try {
						selectParam.setStartDate(format.parse(startDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if(endDate!=null&&endDate.length()==10){
					try {
						selectParam.setEndDate(format.parse(endDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				page.setParam(selectParam);
				
				PageDTO<List<StoreDTO>> pageDate = 
						storeService.getListByParam(page);
				
				//查找所有父级区域
				ZoneSet zone=new ZoneSet();
				zone.setParentId(0);
				List<ZoneSetDTO> zones=zoneSetService.getZoneSetByParam(zone);
				model.addAttribute("zones", zones);
				model.addAttribute("params", selectParam);
				
				model.addAttribute("pageResult", pageDate);
				return "admin/store_balance";
			}
		}

		return "admin/store_balance";
	}
	/**
	 * 充值
	 */
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String operator=request.getParameter("operator");
		String storeId = request.getParameter("storeId");
		if (FIND_BY_ID.equals(operator)) {// 获取当个
			if (storeId != null && storeId.matches("\\d+")) {
				Store param=new Store();
				param.setStoreId(Integer.parseInt(storeId));
				StoreDTO store=storeService.getByParam(param);
				model.addAttribute("store", store);
				
				//查询充值历史记录
				String pageIndex = request.getParameter("pageIndex");// 页码
				String pageSize = request.getParameter("pageSize");// 页容量
				
				if (pageIndex == null) {
					pageIndex = "1";
				}
				if (pageSize == null) {
					pageSize = "10";
				}
				BalanceHistory history=new BalanceHistory();
				history.setStoreId(Integer.parseInt(storeId));
				PageDTO<BalanceHistory> page=new PageDTO<BalanceHistory>();
				page.setParam(history);
				page.setPageIndex(Integer.parseInt(pageIndex));
				page.setPageSize(Integer.parseInt(pageSize));
				
				model.addAttribute("pageResult",balanceHistoryService.getListByPage(page));
				return "admin/store_balance_update";
			}
		}else if("update".equals(operator)){//进行充值记录
			JsonDTO json=new JsonDTO();
			String value=request.getParameter("value");
			if (storeId != null && storeId.matches("\\d+")
					&&value!=null&&value.matches("-?\\d+")) {
				
				String message=request.getParameter("message");
				BalanceHistory saveParam=new BalanceHistory();
				saveParam.setMessage(message);
				saveParam.setStoreId(Integer.parseInt(storeId));
				saveParam.setValue(Integer.parseInt(value));
				saveParam.setCreateDate(new Date());
				saveParam.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
				saveParam.setType(0);//0为充值
				try {
					balanceHistoryService.save(saveParam);
					json.setStatus(1).setMessage("充值成功！");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("充值过程中，系统出现异常");
				}
			}else{
				json.setStatus(0).setMessage("数据异常！");
			}
			model.addAttribute("json", JSONUtil.object2json(json));
			return JSON;
		}
		return null;
	}

	// 切换状态
	@RequestMapping(value = "/status")
	public String status(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		JsonDTO json = new JsonDTO();
		String storeId = request.getParameter("storeId");
		String status = request.getParameter("status");
		if (storeId != null && storeId.matches("\\d+") && status != null
				&& status.matches("[01]")) {
			// 检测
			Integer storeIdInt = Integer.parseInt(storeId);
			Integer statusInt = null;
			if (status != null) {
				statusInt = Integer.parseInt(status);
			}
			Store updateParam = new Store();
			updateParam.setStoreId(storeIdInt);
			updateParam.setStatus(statusInt);
			updateParam.setUpdateDate(new Date());
			updateParam.setUpdateUserId(getLoginAdminUser(request).getAdminUserId());
			try {
				storeService.update(updateParam, null,null);
				json.setStatus(1).setMessage("更新店铺成功");
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
