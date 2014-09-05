package com.egov.secrecysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egov.secrecysystem.dao.CpAnswerRecordDao;
import com.egov.secrecysystem.dao.CpQuestionDao;
import com.egov.secrecysystem.dao.CpTestpaperRecordDao;
import com.egov.secrecysystem.dao.CpUserDao;

@Service
public class ClearHistoryDataService {
	@Autowired
	private CpUserDao cpUserDao;
	@Autowired
	private CpQuestionDao cpQuestionDao;
	@Autowired
	private CpAnswerRecordDao cpAnswerRecordDao;
	@Autowired
	private CpTestpaperRecordDao cpTestpaperRecordDao;
	
	/**
	 * @author LiangGe
	 * @return
	 * 清除历史数据
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String clearHistoryData() {
		try {
			cpUserDao.clearHistoryData();
			cpAnswerRecordDao.clearHistoryData();
			cpQuestionDao.clearHistoryData();
			cpTestpaperRecordDao.clearHistoryData();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "{success:false ,errors: {info: '操作失败！'}}";
		}
		return "{success:true ,errors: {info:'清除历史数据成功！'}}";
	}
}
