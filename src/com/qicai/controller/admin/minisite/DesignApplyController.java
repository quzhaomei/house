package com.qicai.controller.admin.minisite;

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
import com.qicai.bean.minisite.DesignApply;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.PageDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.util.HttpSendResult;
import com.qicai.util.JSONUtil;
import com.qicai.util.MessageSender;
import com.qicai.util.ShortUrlUtil;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping(value = "designApply")
@LimitTag(uri = true)
public class DesignApplyController extends BaseController {
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	// 负责跳转菜单页面,并查出用户列表
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//查询所有的推广员
		List<AdminUserDTO> users=adminUserService.getListByParamAndRole(new AdminUser(), TUIGUANG_ROLE_ID);
		model.addAttribute("users", users);
		model.addAttribute("rootUrl", getRootUrl(request));
		String pageIndex = request.getParameter("pageIndex");// 页码
		String pageSize = request.getParameter("pageSize");// 页容量
		
		String source=request.getParameter("source");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		if (pageIndex == null) {
			pageIndex = "1";
		}
		if (pageSize == null) {
			pageSize = "50";
		}
		if (pageIndex.matches("\\d+") && pageSize.matches("\\d+")) {
			Integer pageIndexInt = Integer.parseInt(pageIndex);
			Integer pageSizeInt = Integer.parseInt(pageSize);
			PageDTO<DesignApply> page = new PageDTO<DesignApply>();
			if (pageIndexInt != 0 && pageSizeInt != 0) {
				page.setPageIndex(pageIndexInt);
				page.setPageSize(pageSizeInt);
				DesignApply selectParam = new DesignApply();
				if(source!=null&&source.matches("\\d+")){
					selectParam.setSource(Integer.parseInt(source));
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

				PageDTO<List<DesignApply>> pageDate = applyService
						.findByPage(page);
				model.addAttribute("pageResult", pageDate);
				return "admin/designApply";
			}
		}
		return "admin/designApply";
	}
	
	// 下载订单
		@SuppressWarnings("deprecation")
		@RequestMapping(value = "/load")
		public String load(HttpServletRequest request,HttpServletResponse response) {
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String source=request.getParameter("source");
			DesignApply designApply=new DesignApply();
			if(source!=null&&source.matches("\\d+")){
				designApply.setSource(Integer.parseInt(source));
			}
			if (startDate != null && startDate.length() == 10) {
				try {
					designApply.setStartDate(format.parse(startDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (endDate != null && endDate.length() == 10) {
				try {
					Date endTime = format.parse(endDate);
					endTime.setDate(endTime.getDate() + 1);
					designApply.setEndDate(endTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(designApply.getEndDate()!=null){
				designApply.getEndDate().setDate(designApply.getEndDate().getDate()+1);
			}
			List<DesignApply> result=applyService.getListByParam(designApply);
			WritableWorkbook wwb = null;
			String fileName = "活动报名.xls";
			try {
				wwb = Workbook.createWorkbook(new File(fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {

				WritableSheet ws = wwb.createSheet("列表一", 0);// 建立工作簿
				// 写表头
				jxl.write.Label label1 = new jxl.write.Label(0, 0, "序列");
				jxl.write.Label label2 = new jxl.write.Label(1, 0, "姓名");
				jxl.write.Label label3 = new jxl.write.Label(2, 0, "电话");
				jxl.write.Label label4 = new jxl.write.Label(3, 0, "地区");
				jxl.write.Label label5 = new jxl.write.Label(4, 0, "渠道编号");
				jxl.write.Label label6 = new jxl.write.Label(5, 0, "报名时间");
				
				
				ws.addCell(label1);// 放入工作簿
				ws.addCell(label2);
				ws.addCell(label3);
				ws.addCell(label4);
				ws.addCell(label5);
				ws.addCell(label6);
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (int i = 0; i < result.size(); i++) {
					
					DesignApply temp = result.get(i);
					
					// 写表头
					jxl.write.Label temp1 = new jxl.write.Label(0, i + 1, "" + (i + 1));
					jxl.write.Label temp2 = new jxl.write.Label(1, i + 1, temp.getUsername() );
					jxl.write.Label temp3 = new jxl.write.Label(2, i + 1, temp.getPhone()+"");
					jxl.write.Label temp4 = new jxl.write.Label(3, i + 1, temp.getLocation());// 
					jxl.write.Label temp5 = new jxl.write.Label(4, i + 1, temp.getSource()+"");//
					jxl.write.Label temp6 = new jxl.write.Label(5, i + 1, dateFormat.format(temp.getCreateDate()));//
					
					ws.addCell(temp1);// 放入工作簿
					ws.addCell(temp2);// 放入工作簿
					ws.addCell(temp3);// 放入工作簿
					ws.addCell(temp4);// 放入工作簿
					ws.addCell(temp5);// 放入工作簿
					ws.addCell(temp6);// 放入工作簿
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
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/sendMsg")
	public String sendMsg(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//发送短信
		String applyId=request.getParameter("applyId");
		String content=request.getParameter("content");
		
		if(applyId!=null&&applyId.matches("\\d+")){
			DesignApply apply = new DesignApply();
			apply.setApplyId(Integer.parseInt(applyId));
			apply=applyService.getByParam(apply);
			if(apply!=null){
				String toPhone=apply.getPhone();//获取电话号码
				try {
					//去掉content中的标签
					content=content.replaceAll("<[/]*url>", "");
					//为null，发送测试数据
					HttpSendResult result=MessageSender.sendMsg(toPhone, content);
					if(result.getIsSuccess()){//成功，则更新状态
						apply.setStatus(1);
						apply.setDescription(apply.getDescription()+
								" # "+dateFormat.format(new Date()) + " 发送短信");
					}
					try {
						applyService.update(apply);
					} catch (Exception e) {
					}
					model.addAttribute("json", JSONUtil.object2json(result));
				} catch (Exception e) {
					model.addAttribute("json", 
							JSONUtil.object2json(new HttpSendResult(false, "系统出现异常")));
					e.printStackTrace();
				}
			}
		} 
		return "json";
	}

	// 更新
	@RequestMapping(value = "/saveOrUpdate")
	public String saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		JsonDTO json = new JsonDTO();
		if (phone != null && phone.matches("\\d+") && name != null) {
			DesignApply apply = new DesignApply();
			apply.setPhone(phone);
			DesignApply checkDate = applyService.getByParam(apply);// 检查数据是否重复
			if (checkDate != null) {
				json.setStatus(0).setMessage("该用户已经注册过");
			} else {
				// 初始化
				apply.setUsername(name);
				apply.setCreateDate(new Date());
				apply.setStatus(0);
				apply.setDescription(dateFormat.format(new Date()) + " 经后台创建");
				String host = request.getRequestURL().toString();
				host = host.substring(0, host.lastIndexOf("/"));
				host = host.substring(0, host.lastIndexOf("/"));
				host += "/w_m/designApply.html?phone=" + phone;
				String shortUrl = ShortUrlUtil.getShotUrl(host);
				apply.setShortUrl(shortUrl);// 占位
				try {
					applyService.save(apply);
					json.setStatus(1).setMessage("添加成功！");
				} catch (Exception e) {
					json.setStatus(0).setMessage("新建失败：" + e.getMessage());
					e.printStackTrace();
				}
			}
		} else {
			json.setStatus(0).setMessage("数据异常！");
		}
		model.addAttribute("json", JSONUtil.object2json(json));
		return "json";
	}

	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/DD HH:mm");
}
