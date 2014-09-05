package com.egov.secrecysystem.dao;

import java.util.List;
import java.util.Map;

import com.egov.secrecysystem.model.SeMainmenu;

public class MainMenuDao extends AbstractHibernateDao<SeMainmenu> {
	
	MainMenuDao() {
		super(SeMainmenu.class);
	}
	
	public List getPageInMainmenu(int pageNum, int pageSize){
		String hql = "select t from com.egov.secrecysystem.model.SeMainmenu t";
		return this.getCurrentSession().createQuery(hql).setFirstResult(pageNum).setMaxResults(pageSize).list();
	}
	
	public long getInMainmenuCount(){
		String hql = "select count(t) from com.egov.secrecysystem.model.SeMainmenu t";
		return (Long)this.getCurrentSession().createQuery(hql).list().get(0);
	}
	
	public List parentMenuGroup(){
		String hql = "select t.parentmenu from com.egov.secrecysystem.model.SeMainmenu t group by t.parentmenu";
		return this.getCurrentSession().createQuery(hql).list();
	}
	
	public List getMainMenuForParentMenu(String parentmenu){
		String hql = "select t from com.egov.secrecysystem.model.SeMainmenu t where t.parentmenu =:parentmenu";
		return this.getCurrentSession().createQuery(hql).setParameter("parentmenu", parentmenu).list();
	}
	
	public List<SeMainmenu> getAuthorisedMenu(Integer roleNum){
		String hql ="select e from com.egov.secrecysystem.model.SeMainmenu e where e.seRole.rolenum = :roleNum";
		List<SeMainmenu> temp = this.getCurrentSession().createQuery(hql).setParameter("roleNum", roleNum).list();
	//	return this.getCurrentSession().createQuery(hql).setParameter("roleNum", roleNum).list();
		return temp;
	}

	public List<Map> findAllMenuByGrouping(){
		String hql = "select new map(e.id as id,e.menuid as menuid,e.showname as showname ,e.onclickscript as onclickscript,e.SeMainmenu.showname as parentmenu,e.seRole.remark as rolename, e.sortindex as sortindex ) from com.egov.secrecysystem.model.SeMainmenu e";
		return (List<Map>)this.getCurrentSession().createQuery(hql).list();
	}
	
	public List<Map> findParentMenu(){
		String hql = "select new map (e.id as id, e.menuid as menuid, e.showname as showname, e.seRole.remark as rolename, e.sortindex as sortindex) from com.egov.secrecysystem.model.SeMainmenu e where e.SeMainmenu = null";
		return (List<Map>)this.getCurrentSession().createQuery(hql).list();
	}
	public List<Map> loadParentMenuName() {
		String hql = "select new map (e.menuid as menuid, e.showname as showname) from com.egov.secrecysystem.model.SeMainmenu e where e.SeMainmenu = null";
		return (List<Map>)this.getCurrentSession().createQuery(hql).list();
	}
	public List<SeMainmenu> getByShowname(String showname){
		String hql = "select t from com.egov.secrecysystem.model.SeMainmenu t where t.showname =:showname";
		return (List<SeMainmenu>)this.getCurrentSession().createQuery(hql).setParameter("showname", showname).list();
	}
}
