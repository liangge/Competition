package com.egov.secrecysystem.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.egov.secrecysystem.model.CpTestpaperRecord;

public class CpTestpaperRecordDao extends AbstractHibernateDao<CpTestpaperRecord>{
	public CpTestpaperRecordDao() {
		super(CpTestpaperRecord.class);
	}
	/**获取出卷记录
	 * @author yangfan
	 * @return
	 */
	public List<Map> getTestPaperRecordList() {
		String hql = "select new map(t.id as id,t.starttime as starttime,t.endtime as endtime,t.examname as examname,t.remark as remark) from com.egov.secrecysystem.model.CpTestpaperRecord t";
		return this.getCurrentSession().createQuery(hql).list();
	}
	
	/**
	 * @author LiangGe
	 * 清楚竞赛时间历史数据
	 */
	public void clearHistoryData() {
        String hql = "delete from com.egov.secrecysystem.model.CpTestpaperRecord e ";
        Query query = this.getCurrentSession().createQuery(hql);
        query.executeUpdate();
	}
}
