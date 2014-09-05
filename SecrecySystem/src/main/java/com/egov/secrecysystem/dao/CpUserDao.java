package com.egov.secrecysystem.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.egov.secrecysystem.model.CpUser;

public class CpUserDao extends AbstractHibernateDao<CpUser>{
	
	CpUserDao() {
		super(CpUser.class);
	}

	public Session getSession() {
		return this.getCurrentSession();
	}
	
	/**
	 * @author LiangGe
	 * @param whereString
	 * @param valuesMap
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * 分页获取用户信息
	 */
	public List<Map> getAllUserInfo(String whereString,
			Map<String, Object> valuesMap, int pageNum, int pageSize) {
		String hql = "select new map(t.id as id, t.name as name, t.loginname as loginname, "
				+ "t.remark as remark) from com.egov.secrecysystem.model.CpUser t";
		hql += whereString;
		hql += "and t.loginname!=:loginName order by t.id ";
		Query query = this.getCurrentSession().createQuery(hql)
				.setFirstResult(pageNum).setMaxResults(pageSize);

		String[] perchs = valuesMap.keySet().toArray(new String[0]);

		for (int i = 0; i < perchs.length; i++) {
			if (perchs[i].equals("score")) {
				query.setParameter(perchs[i],
						Integer.parseInt((String) valuesMap.get(perchs[i])));
			} else {
				query.setParameter(perchs[i], valuesMap.get(perchs[i]));
			}
		}
		query.setParameter("loginName", "admin");
		return query.list();
	}
	
	/**
	 * @author LiangGe
	 * @param whereString
	 * @param valuesMap
	 * @return
	 * 获取用户总数
	 */
	public long getUserTotalCount(String whereString,
			Map<String, Object> valuesMap) {
		String hql = "select t from com.egov.secrecysystem.model.CpUser t ";
		hql += whereString;
		hql += " and t.loginname!=:loginName and t is not null";
		Query query = this.getCurrentSession().createQuery(hql);
		String[] perchs = valuesMap.keySet().toArray(new String[0]);
		for (int i = 0; i < perchs.length; i++) {
			query.setParameter(perchs[i], valuesMap.get(perchs[i]));
		}
		query.setParameter("loginName", "admin");
		return query.list().size();
	}
	
	// 返回用户
	public CpUser getRoleByLoginName(String loginName) {
		String hql = "select e from com.egov.secrecysystem.model.CpUser e where e.loginname=:loginName";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("loginName", loginName);
		List<CpUser> result = query.list();
		if (result.size() == 1)
			return result.get(0);
		return null;
	}
	
	/**
	 * @author LiangGe
	 * @return
	 * 获取当前所有选手信息
	 */
	public List<CpUser> getUserInfo() {
		String hql = "select e from com.egov.secrecysystem.model.CpUser e where e.loginname!=:loginName";
		return this.getCurrentSession().createQuery(hql).setParameter("loginName", "admin").list();
	}
	

	/**
	 * @author LiangGe
	 * @param name
	 * @param password
	 * @return 用户存在返回ok
	 * 验证用户登录
	 */
	public String validateLogin(String name, String password) {
		String hql = "from com.egov.secrecysystem.model.CpUser e where e.loginname=:Name and e.passwd=:Password";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("Name", name);
		query.setParameter("Password", password);
		List<CpUser> result = query.list();
		if (result.size() != 1)
			return null;
		else
			return "ok";
	}
	
	/**
	 * @author LiangGe
	 * @param loginName
	 * @return
	 * 通过loginame获取用户密码
	 */
	public String getRolePassword(String loginName) {
		String hql = "select e.passwd from com.egov.secrecysystem.model.CpUser e where e.loginname=:loginName";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("loginName", loginName);
		List<String> result = query.list();
		if (result.size() == 1)
			return result.get(0);
		return null;
	}
	
	/**
	 * @author LiangGe
	 * @param loginName
	 * @param password
	 * @return
	 * 修改密码
	 */
	public boolean setRolePassword(String loginName, String password) {
		try {
			CpUser entity = this.getUserByLoginName(loginName);
			if (entity == null)
				return false;
			entity.setPasswd(password);
			this.update(entity);
			return true;
		} catch (Exception e) {
			System.out.println("savePassword error!" + e.getMessage());
			return false;
		}
	}
	
	/**
	 * @author LiangGe
	 * @param loginName
	 * @return
	 * 通过loginame获取用户 
	 */
	public CpUser getUserByLoginName(String loginName) {
		String hql = "select e from com.egov.secrecysystem.model.CpUser e where e.loginname=:loginName";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("loginName", loginName);
		List<CpUser> result = query.list();
		if (result.size() == 1)
			return result.get(0);
		return null;
	}
	
	/**
	 * @author LiangGe
	 * 清楚用户历史数据
	 */
	public void clearHistoryData() {
        String hql = "select e.id from com.egov.secrecysystem.model.CpUser e where " +
                "e.loginname!=:loginName";
        Query query = this.getCurrentSession().createQuery(hql).setParameter("loginName", "admin");
        List deleteList = query.list();
        if (deleteList.size() == 0) return;
        hql = "delete from com.egov.secrecysystem.model.CpUser e " +
                "where e.id in(:list)";
        query = this.getCurrentSession().createQuery(hql).setParameterList("list", deleteList);
        query.executeUpdate();
	}
	
	/**
	 * @author LiangGe
	 * @return
	 * 导出用户excel格式
	 */
	public List<Map> exportUserExcel() {

		String hql = "select new map(e.name as name,e.loginname as loginname,"
				+ "e.passwd as passwd,e.remark as remark) "
				+ "from com.egov.secrecysystem.model.CpUser e where e.loginname!=:loginName";
		Query query = this.getCurrentSession().createQuery(hql).setParameter("loginName", "admin");
		return query.list();

	}
}
