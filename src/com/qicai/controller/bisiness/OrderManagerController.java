package com.qicai.controller.bisiness;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qicai.annotation.LimitTag;
import com.qicai.bean.bisiness.Order;
import com.qicai.bean.bisiness.Require;
import com.qicai.controller.BaseController;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.OrderDTO;
import com.qicai.dto.bisiness.RequireDTO;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * ��������
 * 
 * @author qzm
 * @since 2015-8-31
 */
@Controller
@RequestMapping(value = "orderManager")
@LimitTag(uri = true)
public class OrderManagerController extends BaseController {
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
		String username = request.getParameter("username");
		String storeName = request.getParameter("storeName");

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
				selectParam.setUsername(username);
				selectParam.setStoreName(storeName);
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

				PageDTO<List<OrderDTO>> pageDate = orderService.getListByParam(page);

				model.addAttribute("pageResult", pageDate);
				return "admin/order_manager";
			}
		}

		return "admin/order_manager";
	}
	private String getFileName(String startDate,String endDate,String username,String storeName){
		String name=null;
		if(startDate!=null&&!"".equals(startDate.trim())){
			name=(name==null)?startDate:(name+"-"+startDate);
		}
		if(endDate!=null&&!"".equals(endDate.trim())){
			name=(name==null)?endDate:(name+"-"+endDate);
		}
		if(username!=null&&!"".equals(username.trim())){
			name=(name==null)?username:(name+"-"+username);
		}
		if(storeName!=null&&!"".equals(storeName.trim())){
			name=(name==null)?storeName:(name+"-"+storeName);
		}
		if(name==null){
			name="����ͳ��";
		}
		name+=".xls";
		return name;
	}
	// ���ض���
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/loadAll")
	public String load(HttpServletRequest request, HttpServletResponse response, Model model) {
		String status = request.getParameter("status");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String username = request.getParameter("username");
		String storeName = request.getParameter("storeName");
		
		Order selectParam = new Order();
		selectParam.setUsername(username);
		selectParam.setStoreName(storeName);
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
		List<OrderDTO> result = orderService.list(selectParam);
		WritableWorkbook wwb = null;
		String fileName =getFileName(startDate, endDate, username, storeName);
		try {
			wwb = Workbook.createWorkbook(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			WritableSheet ws = wwb.createSheet("�б�һ", 0);// ����������
			// д��ͷ
			jxl.write.Label label1 = new jxl.write.Label(0, 0, "����");
			jxl.write.Label label2 = new jxl.write.Label(1, 0, "������");
			jxl.write.Label label3 = new jxl.write.Label(2, 0, "����");
			jxl.write.Label label4 = new jxl.write.Label(3, 0, "����");
			jxl.write.Label label5 = new jxl.write.Label(4, 0, "״̬");
			jxl.write.Label label6 = new jxl.write.Label(5, 0, "��������ʱ��");
			jxl.write.Label label7 = new jxl.write.Label(6, 0, "�ͷ�");
			jxl.write.Label label8 = new jxl.write.Label(7, 0, "�۸�");
			jxl.write.Label label9 = new jxl.write.Label(8, 0, "�����");
			jxl.write.Label label10 = new jxl.write.Label(9, 0, "�ͻ�����");
			jxl.write.Label label11 = new jxl.write.Label(10, 0, "�ͻ��绰");
			jxl.write.Label label12 = new jxl.write.Label(11, 0, "��������");
			jxl.write.Label label13 = new jxl.write.Label(12, 0, "���󴴽�ʱ��");
			jxl.write.Label label14 = new jxl.write.Label(13, 0, "��������");
			jxl.write.Label label15 = new jxl.write.Label(14, 0, "���ݵ�ַ");

			ws.addCell(label1);// ���빤����
			ws.addCell(label2);
			ws.addCell(label3);
			ws.addCell(label4);
			ws.addCell(label5);
			ws.addCell(label6);
			ws.addCell(label7);
			ws.addCell(label8);
			ws.addCell(label9);
			ws.addCell(label10);
			ws.addCell(label11);
			ws.addCell(label12);
			ws.addCell(label13);
			ws.addCell(label14);
			ws.addCell(label15);
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < result.size(); i++) {
				OrderDTO temp = result.get(i);
				// д��ͷ
				jxl.write.Label temp1 = new jxl.write.Label(0, i + 1, ""
						+ (i + 1));
				jxl.write.Label temp2 = new jxl.write.Label(1, i + 1, temp.getOrderId()+"");//������
				jxl.write.Label temp3 = new jxl.write.Label(2, i + 1, temp.getType()==1?"����":"����"
						);//����
				jxl.write.Label temp4 = new jxl.write.Label(3, i + 1, 
						 temp.getStore().getStoreName());//������
				jxl.write.Label temp5 = new jxl.write.Label(4, i + 1,
						temp.getStatus()==1?"����":"δ����");//״̬
				jxl.write.Label temp6 = new jxl.write.Label(5, i + 1, 
						dateFormat.format(temp.getCreateDate()));//��������ʱ��
				jxl.write.Label temp7 = new jxl.write.Label(6, i + 1, 
						temp.getCreateUser().getNickname());//�ͷ�
				jxl.write.Label temp8 = new jxl.write.Label(7, i + 1, 
						temp.getValue()+"");//�۸�
				jxl.write.Label temp9 = new jxl.write.Label(8, i + 1, 
						temp.getRequire().getRequiredId()+"");//�����
				jxl.write.Label temp10 = new jxl.write.Label(9, i + 1, 
						temp.getRequire().getUsername());//�ͻ�����
				jxl.write.Label temp11 = new jxl.write.Label(10, i + 1, 
						temp.getRequire().getUserphone());//�绰
				jxl.write.Label temp12 = new jxl.write.Label(11, i + 1, 
						temp.getZonename());//��������
				jxl.write.Label temp13 = new jxl.write.Label(12, i + 1, 
						dateFormat.format(temp.getRequire().getCreateDate()));//���󴴽�ʱ��
				jxl.write.Label temp14 = new jxl.write.Label(13, i + 1, 
						temp.getTypename());//�������
				jxl.write.Label temp15 = new jxl.write.Label(14, i + 1, 
						temp.getRequire().getHouseLocation());//���ݵ�ַ
				
				ws.addCell(temp1);// ���빤����
				ws.addCell(temp2);// ���빤����
				ws.addCell(temp3);// ���빤����
				ws.addCell(temp4);// ���빤����
				ws.addCell(temp5);// ���빤����
				ws.addCell(temp6);// ���빤����
				ws.addCell(temp7);// ���빤����
				ws.addCell(temp8);// ���빤����
				ws.addCell(temp9);// ���빤����
				ws.addCell(temp10);// ���빤����
				
				ws.addCell(temp11);// ���빤����
				ws.addCell(temp12);// ���빤����
				ws.addCell(temp13);// ���빤����
				ws.addCell(temp14);// ���빤����
				ws.addCell(temp15);// ���빤����
			}
			// д��Exel������
			wwb.write();
			// �ر�Excel����������
			wwb.close();

			// ����
			String contentType = "application/x-download";
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "ISO8859-1"));

			ServletOutputStream out = response.getOutputStream();

			byte[] bytes = new byte[0xffff];
			InputStream is = new FileInputStream(new File(fileName));
			int b = 0;
			while ((b = is.read(bytes, 0, 0xffff)) > 0) {
				out.write(bytes, 0, b);
			}
			is.close();
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

		
	}

	// ��������
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		String orderId = request.getParameter("orderId");
		if (orderId != null && orderId.matches("\\d+")) {

			Order param = new Order();
			param.setOrderId(Integer.parseInt(orderId));
			// �Ե곤ID �Ͷ���ID����ƥ��
			OrderDTO order = orderService.getByParam(param);
			if (order != null) {
				model.addAttribute("order", order);
				RequireDTO requireDTO = requireService.getByParam(new Require(order.getRequire().getRequiredId()));
				model.addAttribute("require", requireDTO);
			}
		}

		return "admin/order_manager_list";
	}

}
