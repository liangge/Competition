package com.egov.secrecysystem.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.egov.secrecysystem.model.CpQuestion;
import com.egov.secrecysystem.model.CpUser;

public class CpQuestionDao extends AbstractHibernateDao<CpQuestion> {
	CpQuestionDao() {
		super(CpQuestion.class);
	}
	public Session getSession() {
		return this.getCurrentSession();
	}
	
	/**
	 * @author LiangGe
	 * @return
	 * 获取所有题目信息
	 */
	public List<CpQuestion> getAllQuestionRecordList() {
		String hql = "from com.egov.secrecysystem.model.CpQuestion t";
		hql += " order by t.number ";
		return this.getCurrentSession().createQuery(hql).list();
	}
	
	/**
	 * @author yangfan
	 * @return
	 * 获取题目
	 */
	public List<Map> getQuestionRecordList() {
		String hql = "select new map(t.id as id,t.number as number,t.question as question) "
				+ "from com.egov.secrecysystem.model.CpQuestion t";
		hql += " order by t.number ";
		return this.getCurrentSession().createQuery(hql).list();
	}
	
	public List<Map> getPageInstructors(String whereString,
			Map<String, Object> valuesMap, int pageNum, int pageSize) {
//		String hql = "select new map(t.id as id, t.problemlevel as problemlevel, t.mustright as mustright, t.keyproblem as keyproblem,"
//				+ "t.question as question, t.option1 as option1, t.option2 as option2, t.option3 as option3, t.option4 as option4,"
//				+ "t.answer as answer,t.problemnum as problemnum) from com.egov.secrecysystem.model.SeProblemDanXuan t";
		String hql = "select new map(t.id as id,t.number as number,t.question as question) "
				+ "from com.egov.secrecysystem.model.CpQuestion t";

		hql += whereString;
		hql += " order by t.number ";
		Query query = this.getCurrentSession().createQuery(hql)
				.setFirstResult(pageNum).setMaxResults(pageSize);
		String[] perchs = valuesMap.keySet().toArray(new String[0]);
		for (int i = 0; i < perchs.length; i++) {
					
			query.setParameter(perchs[i], valuesMap.get(perchs[i]));
		}
		return query.list();
	}
	
	public long getInstructorsTotalCount(String whereString,
			Map<String, Object> valuesMap) {
		String hql = "select t from com.egov.secrecysystem.model.CpQuestion t ";
		hql += whereString;
		hql += " and t is not null";
		Query query = this.getCurrentSession().createQuery(hql);
		String[] perchs = valuesMap.keySet().toArray(new String[0]);
		for (int i = 0; i < perchs.length; i++) {
			if(perchs[i].equals("number"))
				query.setParameter(perchs[i], Integer.parseInt(valuesMap.get(perchs[i]).toString()));
			else
				query.setParameter(perchs[i], valuesMap.get(perchs[i]));
		}
		return query.list().size();
	}
	/**
	 * 根据id取出CpQuestion
	 * @param id
	 * @return
	 */
	public CpQuestion findCpQuestionById(String id) {
		String hql = "select e from com.egov.secrecysystem.model.CpQuestion e where e.id=:id";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		List<CpQuestion> result = query.list();
		if (result.size() == 1)
			return result.get(0);
		return null;
	}
	
	public List<Map> pageInit(String whereString,
			Map<String, Object> valuesMap, int pageNum, int pageSize) {
		String hql = "select new map(t.id as id, t.number as number, t.question as question, t.rightanswer as rightanswer,"
				+ "t.scorevalue as scorevalue) from com.egov.secrecysystem.model.CpQuestion t";
		hql += whereString;
		hql += " order by t.number ";
		Query query = this.getCurrentSession().createQuery(hql)
				.setFirstResult(pageNum).setMaxResults(pageSize);
		String[] perchs = valuesMap.keySet().toArray(new String[0]);

		for (int i = 0; i < perchs.length; i++) {
			if(perchs[i].equals("number"))
				query.setParameter(perchs[i], Integer.parseInt(valuesMap.get(perchs[i]).toString()));
			else
				query.setParameter(perchs[i], valuesMap.get(perchs[i]));

		}
		List<Map> list = query.list();
		return list;
	}
	
	public List<Map> getInstructorsInfoExcel() {

		String hql = "select new map(t.id as id, t.number as number, t.question as question, t.rightanswer as rightanswer,"
				+ "t.scorevalue as scorevalue, t.remark as remark) from com.egov.secrecysystem.model.CpQuestion t";
		Query query = this.getCurrentSession().createQuery(hql);
		return query.list();

	}
	
	
	/**
	 * @author LiangGe
	 * @return
	 * 获取题目数量
	 */
	public int getQuestionCount(){
		String hql = "select t from com.egov.secrecysystem.model.CpQuestion t ";
		return this.getCurrentSession().createQuery(hql).list().size();
	}
	
	/**
	 * @author LiangGe
	 * 清楚题目历史数据
	 */
	public void clearHistoryData() {
        String hql = "delete from com.egov.secrecysystem.model.CpQuestion e ";
        Query query = this.getCurrentSession().createQuery(hql);
        query.executeUpdate();
	}
	
	/**@author LiangGe
	 * @param number
	 * @return
	 * 根据题目编号获取具体某道题目
	 */
	public CpQuestion getQuestionByNumber(Integer number) {
		String hql = "select e from com.egov.secrecysystem.model.CpQuestion e where e.number=:number";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("number", number);
		List<CpQuestion> result = query.list();
		if (result.size() == 1)
			return result.get(0);
		return null;
	}
}
