package com.egov.secrecysystem.dao;

import java.util.List;
import java.util.Map;

import com.egov.secrecysystem.model.CpMainmenu;

public class CpMainMenuDao extends AbstractHibernateDao<CpMainmenu>{
	
	CpMainMenuDao() {
		super(CpMainmenu.class);
	}
	
	public List getPageInMainmenu(int pageNum, int pageSize){
		String hql = "select t from com.egov.secrecysystem.model.CpMainmenu t";
		return this.getCurrentSession().createQuery(hql).setFirstResult(pageNum).setMaxResults(pageSize).list();
	}
	
	public long getInMainmenuCount(){
		String hql = "select count(t) from com.egov.secrecysystem.model.CpMainmenu t";
		return (Long)this.getCurrentSession().createQuery(hql).list().get(0);
	}
	
	public List parentMenuGroup(){
		String hql = "select t.parentmenu from com.egov.secrecysystem.model.CpMainmenu t group by t.parentmenu";
		return this.getCurrentSession().createQuery(hql).list();
	}
	
	public List getMainMenuForParentMenu(String parentmenu){
		String hql = "select t from com.egov.secrecysystem.model.CpMainmenu t where t.parentmenu =:parentmenu";
		return this.getCurrentSession().createQuery(hql).setParameter("parentmenu", parentmenu).list();
	}
	
	/**
	 * @author LiangGe
	 * @param roleNum
	 * @return
	 * 根据rolenum获取不同用户的菜单栏目,role中使用remark字段代表rolenum
	 */
	public List<CpMainmenu> getAuthorisedMenu(Integer roleNum){
		String hql ="select e from com.egov.secrecysystem.model.CpMainmenu e where e.cpRole.rolenum = :roleNum";
		List<CpMainmenu> temp = this.getCurrentSession().createQuery(hql).setParameter("roleNum", roleNum).list();
		return temp;
	}

	public List<Map> findAllMenuByGrouping(){
		String hql = "select new map(e.id as id,e.menuid as menuid,e.showname as showname ,e.onclickscript as onclickscript,e.CpMainmenu.showname as parentmenu,e.seRole.remark as rolename, e.sortindex as sortindex ) from com.egov.secrecysystem.model.CpMainmenu e";
		return (List<Map>)this.getCurrentSession().createQuery(hql).list();
	}
	
	public List<Map> findParentMenu(){
		String hql = "select new map (e.id as id, e.menuid as menuid, e.showname as showname, e.seRole.remark as rolename, e.sortindex as sortindex) from com.egov.secrecysystem.model.CpMainmenu e where e.CpMainmenu = null";
		return (List<Map>)this.getCurrentSession().createQuery(hql).list();
	}
	public List<Map> loadParentMenuName() {
		String hql = "select new map (e.menuid as menuid, e.showname as showname) from com.egov.secrecysystem.model.CpMainmenu e where e.CpMainmenu = null";
		return (List<Map>)this.getCurrentSession().createQuery(hql).list();
	}
	public List<CpMainmenu> getByShowname(String showname){
		String hql = "select t from com.egov.secrecysystem.model.CpMainmenu t where t.showname =:showname";
		return (List<CpMainmenu>)this.getCurrentSession().createQuery(hql).setParameter("showname", showname).list();
	}
}
