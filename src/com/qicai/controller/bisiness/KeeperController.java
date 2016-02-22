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
import com.qicai.bean.bisiness.Order;
import com.qicai.bean.bisiness.Store;
import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.controller.BaseController;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.OrderDTO;
import com.qicai.dto.bisiness.StoreDTO;
import com.qicai.dto.bisiness.ZoneSetDTO;

/**
 * 商家负责人管理
 * 
 * @author qzm
 * @since 2015-8-31
 */
@Controller
@RequestMapping(value = "keeper")
@LimitTag(uri = true)
public class KeeperController extends BaseController {
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// 首页查询所有负责的店铺
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 查询
		String pageIndex = request.getParameter("pageIndex");// 页码
		String pageSize = request.getParameter("pageSize");// 页容量

		String zoneId = request.getParameter("zoneId");
		String status = request.getParameter("status");

		String storeName = request.getParameter("storeName");
		String keeperName = request.getParameter("keeperName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		if (pageIndex == null) {
			pageIndex = "1";
		}
		if (pageSize == null) {
			pageSize = "10";
		}
		if (pageIndex.matches("\\d+") && pageSize.matches("\\d+")) {
			Integer pageIndexInt = Integer.parseInt(pageIndex);
			Integer pageSizeInt = Integer.parseInt(pageSize);
			PageDTO<Store> page = new PageDTO<Store>();
			if (pageIndexInt != 0 && pageSizeInt != 0) {
				page.setPageIndex(pageIndexInt);
				page.setPageSize(pageSizeInt);
				Store selectParam = new Store();
				if (zoneId != null && zoneId.matches("\\d+")) {
					selectParam.setZoneId(Integer.parseInt(zoneId));
				}
				if (status != null && status.matches("[01]")) {
					selectParam.setStatus(Integer.parseInt(status));
				}
				selectParam.setStoreName(storeName);
				selectParam.setKeeperName(keeperName);
				selectParam.setKeeperId(getLoginAdminUser(request).getAdminUserId());
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

				page.setParam(selectParam);

				PageDTO<List<StoreDTO>> pageDate = storeService.getListByParam(page);

				// 查找所有父级区域
				ZoneSet zone = new ZoneSet();
				zone.setParentId(0);
				List<ZoneSetDTO> zones = zoneSetService.getZoneSetByParam(zone);
				model.addAttribute("zones", zones);

				model.addAttribute("pageResult", pageDate);
				return "admin/store_keeper";
			}
		}

		return "admin/store_keeper";
	}

	// 查看店铺详细，包括订单信息
	@RequestMapping(value = "/balance")
	public String balance(HttpServletRequest request, HttpServletResponse response, Model model) {
		String storeId = request.getParameter("storeId");
		if (storeId != null && storeId.matches("\\d+")) {
			Store param = new Store();
			param.setStoreId(Integer.parseInt(storeId));
			StoreDTO store = storeService.getByParam(param);
			model.addAttribute("store", store);

			// 查询充值历史记录
			String pageIndex = request.getParameter("pageIndex");// 页码
			String pageSize = request.getParameter("pageSize");// 页容量

			if (pageIndex == null) {
				pageIndex = "1";
			}
			if (pageSize == null) {
				pageSize = "10";
			}
			BalanceHistory history = new BalanceHistory();
			history.setStoreId(Integer.parseInt(storeId));
			PageDTO<BalanceHistory> page = new PageDTO<BalanceHistory>();
			page.setParam(history);
			page.setPageIndex(Integer.parseInt(pageIndex));
			page.setPageSize(Integer.parseInt(pageSize));

			model.addAttribute("pageResult", balanceHistoryService.getListByPage(page));
			return "admin/store_balance_detail";
		}
		return "";
	}

	// 查看店铺详细，包括订单信息
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		String storeId = request.getParameter("storeId");// 店铺ID
		if (storeId != null && storeId.matches("\\d+")) {
			StoreDTO store = storeService.getByParam(new Store(Integer.parseInt(storeId)));
			if (store != null) {

				String pageIndex = request.getParameter("pageIndex");// 页码
				String pageSize = request.getParameter("pageSize");// 页容量
				if (pageIndex == null) {
					pageIndex = "1";
				}
				if (pageSize == null) {
					pageSize = "10";
				}
				// 查询订单记录
				if (pageIndex.matches("\\d+") && pageSize.matches("\\d+")) {
					Integer pageIndexInt = Integer.parseInt(pageIndex);
					Integer pageSizeInt = Integer.parseInt(pageSize);
					PageDTO<Order> page = new PageDTO<Order>();
					if (pageIndexInt != 0 && pageSizeInt != 0) {
						page.setPageIndex(pageIndexInt);
						page.setPageSize(pageSizeInt);
						Order selectParam = new Order();
						selectParam.setStoreId(Integer.parseInt(storeId));
						;
						page.setParam(selectParam);

						PageDTO<List<OrderDTO>> pageDate = orderService.getListByParam(page);

						model.addAttribute("pageResult", pageDate);
					}
				}

				model.addAttribute("store", store);
			}
		}
		return "admin/store_list";
	}

}
