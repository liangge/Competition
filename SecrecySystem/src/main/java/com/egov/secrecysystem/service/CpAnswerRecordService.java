package com.egov.secrecysystem.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.directwebremoting.WebContextFactory;
import org.json.simple.JSONObject;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egov.secrecysystem.dao.CpAnswerRecordDao;
import com.egov.secrecysystem.model.CpAnswerRecord;
import com.egov.secrecysystem.model.CpQuestion;
import com.egov.secrecysystem.model.CpUser;
import com.egov.secrecysystem.dao.CpTestpaperRecordDao;
import com.egov.secrecysystem.dao.CpUserDao;

@Service
public class CpAnswerRecordService {
	@Autowired
	private CpAnswerRecordDao cpAnswerRecordDao;
	@Autowired
	private CpUserService cpUserService;
	@Autowired
	private CpQuestionService cpQuestionService;
	
	/**
	 * 将每个考生的答题记录存入数据库
	 * @author yangfan
	 * @param userAnswer
	 * @param userLoginName
	 * @param questionId
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean commitRecordByPerson(String userAnswer,
			String userLoginName,String questionId) {
		String id;
		//根据userLoginName取出CpUser
		CpUser cpUser = cpUserService.getRoleByLoginName(userLoginName);
		//根据questionId取出CpQuestion
		CpQuestion cpQuestion= cpQuestionService.findCpQuestionById(questionId);
		
		// 判断该考生是否已经提交过本题
		List<Map> list = cpAnswerRecordDao.judgeRecordExist(cpUser.getId(), cpQuestion.getId());
		//根据userAnswer判断是否答对
		int isright = judgeAnswer(userAnswer, cpQuestion);
		// 已经提交本题
		if(list.size() != 0) {
			Map map = list.get(0);
			id  = map.get("id").toString();
			return modifyRecordByPerson(userAnswer, userLoginName, questionId , isright, id);
		}
		
		try{
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String dateString = dateFormat.format(date);
			date = dateFormat.parse(dateString);
			System.out.println("dateString = " + dateString);
			System.out.println("shijian = " + dateString.toString());
			
			
			CpAnswerRecord answerRecord = new CpAnswerRecord();
			answerRecord.setCpUser(cpUser);
			answerRecord.setCpQuestion(cpQuestion);
			answerRecord.setTime(date);
			answerRecord.setName(cpUser.getName());
			answerRecord.setLoginname(cpUser.getLoginname());
			answerRecord.setNumber(cpQuestion.getNumber());
			answerRecord.setAnswer(cpQuestion.getRightanswer());
			answerRecord.setUseranswer(userAnswer);
			answerRecord.setIsright(isright);
			cpAnswerRecordDao.save(answerRecord);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return true; 
	}
	
	/**
	 * @return
	 * 修改提交答案
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean modifyRecordByPerson(String userAnswer,
			String userLoginName,String questionId, int isright, String id) {
		try {
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String dateString = dateFormat.format(date);
			date = dateFormat.parse(dateString);
			System.out.println("dateString = " + dateString);
			System.out.println("shijian = " + dateString.toString());
			
			
			CpAnswerRecord answerRecord = cpAnswerRecordDao.findById(id);
			answerRecord.setTime(date);
			answerRecord.setUseranswer(userAnswer);
			answerRecord.setIsright(isright);
			
			cpAnswerRecordDao.update(answerRecord);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 根据loginname查询提交量
	 * @param userLoginname
	 * @return
	 */
	public int getCommitNumber(String userLoginname) {
		CpUser cpUser = cpUserService.getRoleByLoginName(userLoginname);
		List<CpAnswerRecord> record = cpAnswerRecordDao.findByProperty("cpUser", cpUser);
		return record.size();
	}
	
	/**
	 * 判断用户答案是否正确
	 * @author yangfan
	 * @param userAnswer
	 * @param cpQuestion
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public int judgeAnswer(String userAnswer,CpQuestion cpQuestion){
		if(userAnswer.equals(""))
			return 0;
		//去空格处理
		String answer = "";
		for(int i = 0;i < userAnswer.length();i++){
			if(userAnswer.charAt(i) != ' ')
				answer += userAnswer.charAt(i);
		}
		if(answer.equals(cpQuestion.getRightanswer()))
			// 0代表错误，2代表待定，1表示核对后的正确
			return 1;
		else
			return 0;
	}
	@Autowired
	private CpUserDao cpUserDao;
	@Autowired
	private CpTestpaperRecordDao cpTestpaperRecordDao;

	/**
	 * @author LiangGe
	 * @return 
	 * 获取所有选手当前答题以及得分信息
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Map> getSummaryScore() {
		int scoreCount = 0;
		int questionNum;
		int isRight;
		String isRightString;
		int scoreValue;
		int questionCount = 1;
		List<Map> scoreSummaryInfoList = new ArrayList<Map>();

		questionCount = cpQuestionService.getQuestionCount();
		System.out.println("题目总数：" + questionCount);
		
		// 获取当前所有选手信息
		List<CpUser> userInfoList = cpUserDao.getUserInfo();
		System.out.println("选手信息 :" + userInfoList.toString());
		// 获取当前所有选手的答题信息
		List<CpAnswerRecord> answerInfoList = cpAnswerRecordDao.getAnswerInfo();
		System.out.println("答题信息:" + answerInfoList.toString());
		for(CpUser cpUser: userInfoList) {
			scoreCount = 0;
			Map userScoreMap = new JSONObject();
			userScoreMap.put("name", cpUser.getName());
			
			for(CpAnswerRecord cpAnswerRecord:answerInfoList) {
				// 当前用户
				if(cpUser.getId().equals(cpAnswerRecord.getCpUser().getId())) {
					// 获取题目编号和题目分值
					questionNum = cpAnswerRecord.getCpQuestion().getNumber();
					scoreValue = cpAnswerRecord.getCpQuestion().getScorevalue();
					isRight = cpAnswerRecord.getIsright();
					if(isRight == 0) {
						isRightString = "错误";
					} else if(isRight == 2){
						isRightString = "待定";
					}else {
						isRightString = "正确";
						scoreCount += scoreValue;
					}
					userScoreMap.put("" + questionNum, isRightString);
				}
			}
			userScoreMap.put("score", scoreCount);
			
			if(scoreCount == 0) {
				for(int i=1; i<=questionCount; i++) {
					userScoreMap.put(""+i, "未答");
				}
			}
			
			scoreSummaryInfoList.add(userScoreMap);
		}
		
		for(Map tmp:scoreSummaryInfoList) {
			for(int i=1; i<=questionCount; i++) {
				if(!tmp.containsKey(""+i)) {
					tmp.put(""+i, "未答");
				}
			}
		}
		System.out.println("比赛实时信息 ：");
		System.out.println(scoreSummaryInfoList.toString());
		return scoreSummaryInfoList;
	}
}
