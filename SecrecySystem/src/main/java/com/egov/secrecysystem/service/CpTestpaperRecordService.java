package com.egov.secrecysystem.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.directwebremoting.WebContextFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egov.secrecysystem.dao.CpTestpaperRecordDao;
import com.egov.secrecysystem.model.CpTestpaperRecord;

@Service
public class CpTestpaperRecordService {
	@Autowired
	private CpTestpaperRecordDao cpTestpaperRecordDao;
	@Autowired
	private CpQuestionService cpQuestionService;
	/**
	 * @author yangfan
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Map> getTestPaperRecordList() {
		try {
			List<Map> paperNumList = cpTestpaperRecordDao.getTestPaperRecordList();
			for(Map element : paperNumList) {
				Date starttime = (Date)element.get("starttime");
				Date endtime = (Date)element.get("endtime");
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				element.put("starttime", df.format(starttime));
				element.put("endtime", df.format(endtime));
				element.put("remark", cpQuestionService.getQuestionCount());
				System.out.println("测试数据显示：" +element);
				System.out.println("题目数量remark： = " +element.get("remark") );
			}
			return paperNumList;
		} catch (Exception e) {
			System.out.println("getpaperNumList error!" + e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * @author LiangGe
	 * @param jsonObject
	 * @return
	 * 修改竞赛基本时间信息
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean modifyCompetitionTime(JSONObject jsonObject) {
		if(jsonObject.get("id").toString().equals("")) {
			return this.saveCompetitionTime(jsonObject);
		}
		
		CpTestpaperRecord entity = cpTestpaperRecordDao.findById(jsonObject
				.get("id").toString());
		if(entity == null) {
			return this.saveCompetitionTime(jsonObject);
		}
		
		String examdate = jsonObject.get("examdate").toString();
		String start = jsonObject.get("starttime").toString() + ":00";
		String end = jsonObject.get("endtime").toString() + ":00";
		
		Date starttime = new Date();
		Date endtime = new Date();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		try {
			starttime = sdf.parse(examdate + " "+ start);
			endtime = sdf.parse(examdate + " " + end); 
			
			entity.setExamname(jsonObject.get("examname").toString());
			entity.setStarttime(starttime);
			entity.setEndtime(endtime);
			
			cpTestpaperRecordDao.update(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("保存竞赛时间信息错误");
			return false;
		}
		return true;
	}
	
	/**
	 * @author LiangGe
	 * @param jsonObject
	 * @return
	 * 保存竞赛基本时间信息
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean saveCompetitionTime(JSONObject jsonObject) {
		String examdate = jsonObject.get("examdate").toString();
		String start = jsonObject.get("starttime").toString() + ":00";
		String end = jsonObject.get("endtime").toString() + ":00";
		
		Date starttime = new Date();
		Date endtime = new Date();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		try {
			starttime = sdf.parse(examdate + " "+ start);
			endtime = sdf.parse(examdate + " " + end); 
			
			CpTestpaperRecord entity = new CpTestpaperRecord();
			entity.setExamname(jsonObject.get("examname").toString());
			entity.setStarttime(starttime);
			entity.setEndtime(endtime);
			
			cpTestpaperRecordDao.save(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("保存竞赛时间信息错误");
			return false;
		}
		return true;
	}
}
