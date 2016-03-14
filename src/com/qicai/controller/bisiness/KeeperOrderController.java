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
import com.qicai.bean.bisiness.Require;
import com.qicai.bean.bisiness.Store;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.OrderDTO;
import com.qicai.dto.bisiness.RequireDTO;
import com.qicai.dto.bisiness.StoreDTO;
import com.qicai.util.JSONUtil;
import com.qicai.util.MessageSender;

/**
 * �Ҹ���ĵ��̵Ķ���
 * 
 * @author qzm
 * @since 2015-8-31
 */
@Controller
@RequestMapping(value = "order")
@LimitTag(uri = true)
public class KeeperOrderController extends BaseController {
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// ��ҳ��ѯ���и���Ķ���
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		// ��ѯ
		String pageIndex = request.getParameter("pageIndex");// ҳ��
		String pageSize = request.getParameter("pageSize");// ҳ����

		String status = request.getParameter("status");

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
			PageDTO<Order> page = new PageDTO<Order>();
			if (pageIndexInt != 0 && pageSizeInt != 0) {
				page.setPageIndex(pageIndexInt);
				page.setPageSize(pageSizeInt);
				Order selectParam = new Order();
				if (status != null && status.matches("[01]")) {
					selectParam.setStatus(Integer.parseInt(status));
				}
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

				PageDTO<List<OrderDTO>> pageDate = orderService.getListByKeeperId(page,
						getLoginAdminUser(request).getAdminUserId());

				model.addAttribute("pageResult", pageDate);
				return "admin/store_order";
			}
		}

		return "admin/store_order";
	}

	// ��������
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		String orderId = request.getParameter("orderId");
		if (orderId != null && orderId.matches("\\d+")) {

			Order param = new Order();
			param.setOrderId(Integer.parseInt(orderId));
			// �Ե곤ID �Ͷ���ID����ƥ��
			OrderDTO order = orderService.getByKeeperId(param, getLoginAdminUser(request).getAdminUserId());
			if (order != null) {
				model.addAttribute("order", order);
				RequireDTO requireDTO = requireService.getByParam(new Require(order.getRequire().getRequiredId()));
				model.addAttribute("require", requireDTO);
			}

		}
		return "admin/store_order_list";
	}

	// ��������
	@RequestMapping(value = "/getOrder")
	public String getOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
		String orderId = request.getParameter("orderId");
		JsonDTO json = new JsonDTO();
		if (orderId != null && orderId.matches("\\d+")) {

			Order param = new Order();
			param.setOrderId(Integer.parseInt(orderId));
			// �Ե곤ID �Ͷ���ID����ƥ��
			OrderDTO order = orderService.getByKeeperId(param, getLoginAdminUser(request).getAdminUserId());
			if (order != null) {
				RequireDTO require=requireService.getByParam(new Require(order.getRequire().getRequiredId()));
				if(require==null){return null;}//����
				
				model.addAttribute("order", order);
				if (order.getStatus() == 0) {// ����Ƿ����С�
					Store store = new Store(order.getStore().getStoreId());
					StoreDTO data = storeService.getByParam(store);
					int newbanace = data.getBalance() - order.getPrice();
					store.setBalance(newbanace);
					if (newbanace < 0&&false) {// ����
							json.setStatus(0).setMessage("�������ֻ��" + data.getBalance() + "������ϵ����Ա��ֵ");
							model.addAttribute(JSON, JSONUtil.object2json(json));
							return JSON;
					} 
					// ���¶���
					param.setStatus(1);// ��Ϊ�ѽ���״̬
					param.setOperatorLog(order.getOperatorLog() + " <br/> "
							+ new SimpleDateFormat("yyyy-MM-dd HH:ss").format(new Date()) + " " + "��"
							+ getLoginAdminUser(request).getNickname() + "���ܶ���");
					try {
						orderService.update(param);
						BalanceHistory history = new BalanceHistory();
						history.setCreateDate(new Date());
						history.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
						history.setOrderId(Integer.parseInt(orderId));
						history.setType(order.getType());// 1���ѡ�2����
						history.setStoreId(order.getStore().getStoreId());
						history.setValue(order.getPrice());
						history.setMessage("���̶������ѿۿ�");
						balanceHistoryService.save(history);
						json.setStatus(1).setMessage("�ӵ��ɹ�");
						//����֪ͨ
						String content="���ã������������ԤԼ�ѳɹ���"+data.getStoreName()+"װ�޹�˾����������ԤԼ�������뱣���ֻ���ͨ��лл�� �ظ�TD�˶���";
						System.out.println(content);
						MessageSender.sendMsg(require.getUserphone(), content);
						
					} catch (Exception e) {
						json.setStatus(0).setMessage("�ӵ�ʱ��ϵͳ�����쳣��");
						e.printStackTrace();
					}
					// ���̿ۿ�
					
					model.addAttribute(JSON, JSONUtil.object2json(json));
					return JSON;
				}
			}

		}
		return "admin/store_order_list";
	}

}
