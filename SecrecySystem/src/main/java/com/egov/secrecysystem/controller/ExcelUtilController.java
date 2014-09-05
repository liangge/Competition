package com.egov.secrecysystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.egov.secrecysystem.service.CpUserService;
import com.egov.secrecysystem.service.ExcelUtilService;
import com.egov.secrecysystem.service.CpQuestionService;
@Controller
public class ExcelUtilController {

	private Logger logger = LoggerFactory.getLogger(ExcelUtilController.class);
	@Autowired
	private ExcelUtilService excelUtilService;
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
	 * 批量导入用户信息
	 */
	@RequestMapping(value = "xlsToJsonAboutInstructor", method = RequestMethod.POST)
	public ModelAndView upLoadSingleFileAboutInstructor(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		Writer out = response.getWriter();
		HttpSession curSession = request.getSession();
		// 判断是否上传了文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("file");
		if (file.getSize() == 0) {
			out.write("{success: false, errors:{info: '未选择文件！'}}");
			return null;
		}

		InputStream sampleInputStream = null;
		InputStream fromInputStream = null;
		InputStream checksampleInputStream = null;
		InputStream checkfromInputStream = null;
		JSONObject result = null;
		List<JSONObject> instructors = null;

		// String schoolnum = curSession.getAttribute("institution").toString();
		try {
			logger.debug("上传的文件为：" + file.getOriginalFilename());
			logger.debug(request.getSession().getServletContext()
					.getRealPath("/")
					+ "sample/批量导入用户信息sample.xls");
			sampleInputStream = new FileInputStream(request.getSession()
					.getServletContext().getRealPath("/")
					+ "sample/批量导入用户信息sample.xls");
			fromInputStream = file.getInputStream();
			checksampleInputStream = new FileInputStream(request.getSession()
					.getServletContext().getRealPath("/")
					+ "sample/批量导入用户信息sample.xls");
			checkfromInputStream = file.getInputStream();
			// 检查excel列
			String checkInfo = excelUtilService.checkExcelHeader(
					checkfromInputStream, checksampleInputStream);
			if (checkInfo.equals("passcheck")) {
				result = excelUtilService.convertstudentExcelToJson(
						fromInputStream, sampleInputStream);
				instructors = (List<JSONObject>) result.get("excelArray");
				String checkResult = cpUserService.checkInstructorsData(instructors);
				if (checkResult == null) {
					cpUserService.importInstructors(instructors);
					out.write("{ success: true, errors:{info: '上传成功！'}}");
				} else {
					out.write("{ success: false, errors:{info: '"
							+ checkResult
							+ "！'}}");
				};
			} else {
				out.write(checkInfo);
			}
			System.out.println(checkInfo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sampleInputStream.close();
				fromInputStream.close();
				checksampleInputStream.close();
				checkfromInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// 批量上传填空题信息
	@RequestMapping(value = "xlsToJsonAboutDanXuan", method = RequestMethod.POST)
	public ModelAndView upLoadSingleFileAboutDanXuan(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		Writer out = response.getWriter();
		HttpSession curSession = request.getSession();
		// 判断是否上传了文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("file");
		if (file.getSize() == 0) {
			out.write("{success: false, errors:{info: '未选择文件！'}}");
			return null;
		}

		InputStream sampleInputStream = null;
		InputStream fromInputStream = null;
		InputStream checksampleInputStream = null;
		InputStream checkfromInputStream = null;
		JSONObject result = null;
		List<JSONObject> instructors = null;

		// String schoolnum = curSession.getAttribute("institution").toString();
		try {
			logger.debug("上传的文件为：" + file.getOriginalFilename());
			logger.debug(request.getSession().getServletContext()
					.getRealPath("/")
					+ "sample/批量导入填空题信息sample.xls");
			sampleInputStream = new FileInputStream(request.getSession()
					.getServletContext().getRealPath("/")
					+ "sample/批量导入填空题信息sample.xls");
			fromInputStream = file.getInputStream();
			checksampleInputStream = new FileInputStream(request.getSession()
					.getServletContext().getRealPath("/")
					+ "sample/批量导入填空题信息sample.xls");
			checkfromInputStream = file.getInputStream();
			// 检查excel列
			String checkInfo = excelUtilService.checkExcelHeader(
					checkfromInputStream, checksampleInputStream);
			if (checkInfo.equals("passcheck")) {
				result = excelUtilService.convertstudentExcelToJson(
						fromInputStream, sampleInputStream);
				instructors = (List<JSONObject>) result.get("excelArray");
				String resultCheckQuestion = cpQuestionService.checkInstructorsData(instructors);
				if (resultCheckQuestion == null) {
					cpQuestionService.importInstructors(instructors);
					out.write("{ success: true, errors:{info: '上传成功！'}}");
				} else {
					out.write("{ success: false, errors:{info: '"
							+ resultCheckQuestion + "！'}}");
				}
				;
			} else {
				out.write(checkInfo);
			}
			System.out.println(checkInfo);
			// logger.debug(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sampleInputStream.close();
				fromInputStream.close();
				checksampleInputStream.close();
				checkfromInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// 上传文件
	@RequestMapping(value = "upLoadFile", method = RequestMethod.POST)
	public ModelAndView upLoadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out =response.getWriter();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("fileName");
		if (file.getSize() == 0) {
			out.write("{success: false, errors:{info: '未选择文件！'}}");
			return null;
		}
		
		String fileName = file.getOriginalFilename();
		try{
			
			int readBytes = 0;
			byte[] contentBytes=new byte[1024];
			inputStream = (FileInputStream) file.getInputStream();
			outputStream = new FileOutputStream(request.getSession().getServletContext().getRealPath("/") + "secrecy\\" + fileName);
			while((readBytes = inputStream.read(contentBytes)) != -1){
				outputStream.write(contentBytes, 0, readBytes);
			}
			out.write("{success:true}");
		}catch(IOException e){
			out.write("{success:false}");
			e.printStackTrace();
		}
		finally{
			outputStream.close();
			inputStream.close();
			
		}
		return null;
	}
}
