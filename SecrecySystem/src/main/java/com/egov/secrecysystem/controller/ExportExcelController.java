package com.egov.secrecysystem.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
//import cn.hdu.examsignup.model.ExSupervisor;
//import cn.hdu.examsignup.model.Function;

import com.egov.secrecysystem.service.CpQuestionService;
import com.egov.secrecysystem.service.CpUserService;

@Controller
public class ExportExcelController {

	@Autowired
	private CpQuestionService cpQuestionService;
	@Autowired
	private CpUserService cpUserService;

	/**
	 * @author LiangGe
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             导出用户数据
	 */
	@RequestMapping(value = "exportUserExcel", method = RequestMethod.POST)
	public ModelAndView exportUserExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.reset();
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "userInfo" + date.format(new Date()).toString();
		try {
			response.setContentType("application/vnd.ms-excel;charset=UTF-8"); // 改成输出excel文件
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Type", "application/force-download");
			response.setHeader("Content-disposition", "attachment;filename="
					+ filename + ".xls");
			WritableSheet sheet = wwb.createSheet("所有参赛选手信息", 0);
			sheet.setColumnView(0, 15);
			sheet.setColumnView(1, 25);
			sheet.setColumnView(2, 25);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 25);
			// sheet.setColumnView(5, 15);

			WritableFont blodFont = new WritableFont(WritableFont.TAHOMA, 10,
					WritableFont.BOLD, false);
			WritableCellFormat blodFormat = new WritableCellFormat(blodFont);
			Label label = null;
			label = new Label(0, 0, "名称");
			sheet.addCell(label);
			label = new Label(1, 0, "登录用户");
			sheet.addCell(label);

			List<Map> users = cpUserService.exportUserExcel();
			for (int i = 0; i < users.size(); i++) {
				label = new Label(0, i + 1, users.get(i).get("name").toString());
				sheet.addCell(label);
				label = new Label(1, i + 1, users.get(i).get("loginname")
						.toString());
				sheet.addCell(label);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("写入Excel文件发生错误！！！");

		} finally {
			wwb.write();
			wwb.close();
			os.flush();
			os.close();
		}
		return null;
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             批量导出题目信息
	 */
	@RequestMapping(value = "exportDanXuanInfoExcel", method = RequestMethod.POST)
	public ModelAndView exportDanXuanInfoExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.reset();
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "TianKongInfo" + date.format(new Date()).toString();
		try {
			response.setContentType("application/vnd.ms-excel;charset=UTF-8"); // 改成输出excel文件
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Type", "application/force-download");
			response.setHeader("Content-disposition", "attachment;filename="
					+ filename + ".xls");
			WritableSheet sheet = wwb.createSheet("所有题目信息", 0);
			sheet.setColumnView(0, 15);
			sheet.setColumnView(1, 50);
			sheet.setColumnView(2, 25);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 15);

			WritableFont blodFont = new WritableFont(WritableFont.TAHOMA, 10,
					WritableFont.BOLD, false);
			WritableCellFormat blodFormat = new WritableCellFormat(blodFont);
			Label label = null;
			label = new Label(0, 0, "题 号");
			sheet.addCell(label);
			label = new Label(1, 0, "题 目");
			sheet.addCell(label);
			label = new Label(2, 0, "正确答案");
			sheet.addCell(label);
			label = new Label(3, 0, "分 值");
			sheet.addCell(label);
			label = new Label(4, 0, "备 注");
			sheet.addCell(label);
			List<Map> instructors = cpQuestionService.getInstructorsInfoExcel();
			for (int i = 0; i < instructors.size(); i++) {
				label = new Label(0, i + 1, instructors.get(i).get("number")
						.toString());
				sheet.addCell(label);
				label = new Label(1, i + 1, instructors.get(i).get("question")
						.toString());
				sheet.addCell(label);
				label = new Label(2, i + 1, instructors.get(i)
						.get("rightanswer").toString());
				sheet.addCell(label);
				label = new Label(3, i + 1, instructors.get(i)
						.get("scorevalue").toString());
				sheet.addCell(label);
				Object obj = instructors.get(i).get("remark");
				if (obj == null) {
					label = new Label(4, i + 1, "");
				} else {
					label = new Label(4, i + 1, obj.toString());
				}
				sheet.addCell(label);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("写入Excel文件发生错误！！！");

		} finally {
			wwb.write();
			wwb.close();
			os.flush();
			os.close();
		}
		return null;
	}
}
