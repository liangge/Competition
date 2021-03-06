package com.egov.secrecysystem.controller;

import java.util.List;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.beans.factory.annotation.Autowired;

import com.egov.secrecysystem.service.CpAnswerRecordService;

@RemoteProxy(creator = SpringCreator.class)
public class CpAnswerRecordController {
	@Autowired
	private CpAnswerRecordService cpAnswerRecordService;
	
	/**
	 * 将考生答案插入数据库
	 * @param userAnswer
	 * @param userLoginName
	 * @return
	 */
	@RemoteMethod
	public boolean commitRecordByPerson(String userAnswer, String userLoginName,String questionId) {
		return cpAnswerRecordService.commitRecordByPerson(userAnswer,userLoginName,questionId);
	}
	/**
	 * 根据loginname查询提交量
	 */
	@RemoteMethod
	public int getCommitNumber(String userLoginname){
		return cpAnswerRecordService.getCommitNumber(userLoginname);
	}
	
	/**
	 * @author LiangGe
	 * @return 
	 * 获取所有选手当前答题以及得分信息
	 */
	@RemoteMethod
	public List<Map> getSummaryScore() {
		return cpAnswerRecordService.getSummaryScore();
	}

}
