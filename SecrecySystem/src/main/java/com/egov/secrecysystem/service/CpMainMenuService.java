package com.egov.secrecysystem.service;

import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egov.secrecysystem.dao.CpMainMenuDao;
import com.egov.secrecysystem.model.CpMainmenu;

@Service
public class CpMainMenuService {
	@Autowired
	private CpMainMenuDao cpMainMenuDao;
	
	static int TOPMENU_MASK =100;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getAuthorisedMenu(Integer roleNum){
		List<CpMainmenu> authorisedMenu= this.cpMainMenuDao.getAuthorisedMenu(roleNum);
		JSONArray topArray = new JSONArray();
	    for (CpMainmenu topMenu:authorisedMenu) {
	    	if(topMenu.getCpMainmenus().size()==0)
	    		continue;
	        JSONObject topJson = new JSONObject();
	        topJson.put("name",topMenu.getShowname());
	        topJson.put("index",topMenu.getSortindex()/TOPMENU_MASK);
			
			Set<CpMainmenu> childMenu = topMenu.getCpMainmenus();
			JSONArray childArray = new JSONArray();
			for(CpMainmenu childNode:childMenu)
			{
				JSONObject childJson=new JSONObject();
				childJson.put("tabId", childNode.getMenuid());
				childJson.put("name", childNode.getShowname());
				childJson.put("index",childNode.getSortindex()%TOPMENU_MASK);
				childJson.put("script", childNode.getOnclickscript());
				childArray.add(childJson);
			}
			topJson.put("child",childArray);
			topArray.add(topJson);
			System.out.println(topArray.toString());
	    }
	    System.out.println(topArray);
		return topArray.toString();
	}
}
