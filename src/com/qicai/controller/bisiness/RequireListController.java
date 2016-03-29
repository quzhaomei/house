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
import com.qicai.bean.admin.AdminUser;
import com.qicai.bean.bisiness.HouseType;
import com.qicai.bean.bisiness.HouseTypeToStore;
import com.qicai.bean.bisiness.Order;
import com.qicai.bean.bisiness.Require;
import com.qicai.bean.bisiness.Store;
import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.PageDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.bisiness.HouseTypeDTO;
import com.qicai.dto.bisiness.HouseTypeToStoreDTO;
import com.qicai.dto.bisiness.OrderDTO;
import com.qicai.dto.bisiness.RequireDTO;
import com.qicai.dto.bisiness.RequireGift;
import com.qicai.dto.bisiness.ServiceStoreDTO;
import com.qicai.dto.bisiness.ServiceUserDTO;
import com.qicai.dto.bisiness.StoreDTO;
import com.qicai.dto.bisiness.ZoneSetDTO;
import com.qicai.util.JSONUtil;
import com.qicai.util.MessageSender;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 需求统计
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "requireList")
@LimitTag(uri = true)
public class RequireListController extends BaseController {
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// 首页查询所有需求，包括所有状态的
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 查询
		String pageIndex = request.getParameter("pageIndex");// 页码
		String pageSize = request.getParameter("pageSize");// 页容量

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String status = request.getParameter("status");
		String username = request.getParameter("username");
		String userId = request.getParameter("userId");
		String createUserId=request.getParameter("createUserId");

		String specialStatus=request.getParameter("specialStatus");//特殊状态 0 关闭，1待跟进

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
				if (userId != null && userId.matches("\\d+")) {
					selectParam.setUserId(Integer.parseInt(userId));
				}
				if (status != null && status.matches("\\d+")) {
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
				if(createUserId!=null&&createUserId.matches("\\d+")){
					selectParam.setCreateUserId(Integer.parseInt(createUserId));
				}
				if(specialStatus!=null&&specialStatus.matches("\\d+")){
					selectParam.setSpecialStatus(Integer.parseInt(specialStatus));
				}
				
				page.setParam(selectParam);
				PageDTO<List<RequireDTO>> pageDate = requireService.findListByPage(page);
				
				model.addAttribute("pageResult", pageDate);
				
				//获取所有人
				model.addAttribute("users", requireService.getAllPublishUser());
				return "admin/requireList";
			}
		}

		return "admin/requireList";

	}

	// 查看详细
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requiredId = request.getParameter("requiredId");
		String operator=request.getParameter("operator");
		if (requiredId != null && requiredId.matches("\\d+")) {
			RequireDTO require = requireService.getByParam(new Require(Integer.parseInt(requiredId)));
			model.addAttribute("require", require);
			if("json".equals(operator)){
				model.addAttribute("json", JSONUtil.object2json(require));
				return JSON;
			}
		}
		return "admin/requireManager_list";
	}
	
	

	
	
	// 下载订单
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/loadAll")
	public String load(HttpServletRequest request, HttpServletResponse response, Model model) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String status = request.getParameter("status");
		String username = request.getParameter("username");
		String userId = request.getParameter("userId");
		String createUserId=request.getParameter("createUserId");
		String specialStatus=request.getParameter("specialStatus");//特殊状态 0 关闭，1待跟进
		
		Require selectParam = new Require();
		selectParam.setUsername(username);
		if (userId != null && userId.matches("\\d+")) {
			selectParam.setUserId(Integer.parseInt(userId));
		}
		if (status != null && status.matches("\\d+")) {
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
		
		if(createUserId!=null&&createUserId.matches("\\d+")){
			selectParam.setCreateUserId(Integer.parseInt(createUserId));
		}
		if(specialStatus!=null&&specialStatus.matches("\\d+")){
			selectParam.setSpecialStatus(Integer.parseInt(specialStatus));
		}
		List<RequireDTO> result=requireService.list(selectParam);
		WritableWorkbook wwb = null;
		String fileName = "需求统计.xls";
		try {
			wwb = Workbook.createWorkbook(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			WritableSheet ws = wwb.createSheet("列表一", 0);// 建立工作簿
			// 写表头
			jxl.write.Label label1 = new jxl.write.Label(0, 0, "序列");
			jxl.write.Label label2 = new jxl.write.Label(1, 0, "需求号");
			jxl.write.Label label3 = new jxl.write.Label(2, 0, "地区");
			jxl.write.Label label4 = new jxl.write.Label(3, 0, "用户id");
			jxl.write.Label label5 = new jxl.write.Label(4, 0, "用户名称");
			jxl.write.Label label6 = new jxl.write.Label(5, 0, "用户手机");
			jxl.write.Label label7 = new jxl.write.Label(6, 0, "创建时间");
			jxl.write.Label label8 = new jxl.write.Label(7, 0, "创建人");
			jxl.write.Label label9 = new jxl.write.Label(8, 0, "状态");
			jxl.write.Label label10 = new jxl.write.Label(9, 0, "特殊状态");
			jxl.write.Label label11 = new jxl.write.Label(10, 0, "下次回访时间");
			jxl.write.Label label12 = new jxl.write.Label(11, 0, "备注");
			
			
			ws.addCell(label1);// 放入工作簿
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
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < result.size(); i++) {
				
				RequireDTO temp = result.get(i);
				String statusStr="";
				switch (temp.getStatus()) {
				case 0:statusStr="发起中"; break;
				case 1:statusStr="短信中"; break;
				case 2:statusStr="客户打开连接"; break;
				case 3:statusStr="客户修改提交"; break;
				case 4:statusStr="确认完毕待发布"; break;
				case 6:statusStr="待分单"; break;
				case 7:statusStr="待派单"; break;
				case 8:statusStr="已派单"; break;
				case 40:statusStr="关闭"; break;
				case 41:statusStr="待跟进库"; break;
				}
				
				// 写表头
				jxl.write.Label temp1 = new jxl.write.Label(0, i + 1, "" + (i + 1));
				jxl.write.Label temp2 = new jxl.write.Label(1, i + 1, temp.getRequiredId() + "");
				jxl.write.Label temp3 = new jxl.write.Label(2, i + 1, temp.getZone().getName());
				jxl.write.Label temp4 = new jxl.write.Label(3, i + 1, temp.getUserId()+"");// 
				jxl.write.Label temp5 = new jxl.write.Label(4, i + 1, temp.getUsername());//
				jxl.write.Label temp6 = new jxl.write.Label(5, i + 1, temp.getUserphone().substring(0,5)+"******");//
				jxl.write.Label temp7 = new jxl.write.Label(6, i + 1,dateFormat.format( temp.getCreateDate()));// 客服
				jxl.write.Label temp8 = new jxl.write.Label(7, i + 1,temp.getCreateUser().getNickname());// 电话
				jxl.write.Label temp9 = new jxl.write.Label(8, i + 1,statusStr);// 电话
				
				String special="";
				if(temp.getRemarks()!=null&&temp.getRemarks().getStatus()==0){
					special="关闭";
				}else if(temp.getRemarks()!=null&&temp.getRemarks().getStatus()==1){
					special="待跟进库";
				}
				jxl.write.Label temp10 = new jxl.write.Label(9, i + 1,special);// 电话
				String nextTime="";
				if(temp.getRemarks()!=null&&temp.getRemarks().getNextTime()!=null){
					nextTime=new SimpleDateFormat("yyyy-MM-DD").format(temp.getRemarks().getNextTime());
				}
				jxl.write.Label temp11 = new jxl.write.Label(10, i + 1,nextTime);// 电话
				jxl.write.Label temp12 = new jxl.write.Label(11, i + 1,temp.getRemarks()!=null?temp.getRemarks().getRemark():"");// 电话
				

				ws.addCell(temp1);// 放入工作簿
				ws.addCell(temp2);// 放入工作簿
				ws.addCell(temp3);// 放入工作簿
				ws.addCell(temp4);// 放入工作簿
				ws.addCell(temp5);// 放入工作簿
				ws.addCell(temp6);// 放入工作簿
				ws.addCell(temp7);// 放入工作簿
				ws.addCell(temp8);// 放入工作簿
				ws.addCell(temp9);// 放入工作簿
				ws.addCell(temp10);// 放入工作簿
				ws.addCell(temp11);// 放入工作簿
				ws.addCell(temp12);// 放入工作簿
			}
			// 写入Exel工作表
			wwb.write();
			// 关闭Excel工作薄对象
			wwb.close();

			// 下载
			String contentType = "application/x-download";
			response.setContentType(contentType);
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));

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
	
}
