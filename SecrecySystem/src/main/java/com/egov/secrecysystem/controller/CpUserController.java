package com.egov.secrecysystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.egov.secrecysystem.model.Function;
import com.egov.secrecysystem.service.CpUserService;

@RemoteProxy(creator = SpringCreator.class)
public class CpUserController {
	@Autowired
	private CpUserService cpUserService;
	
	/**
	 * @author LiangGe
	 * @param filterString
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * 分页获取所有用户信息
	 */
	@RemoteMethod
	public Map getAllUserInfo(String filterString, String pageNum, String pageSize) {
		Map map = new HashMap();
		String whereString = Function.initSearchHql_whereString_(filterString);
		Map<String, Object> paramsMap = Function.initSearchValues(filterString);
		List<Map> result = cpUserService.getAllUserInfo(whereString, paramsMap, pageNum, pageSize);
		map.put("totalProperty", cpUserService.getUserTotalCount(whereString, paramsMap));
		map.put("root", result);
		return map;
	}
	
	/**
	 * @author LiangGe
	 * @param jsonObject
	 * @return
	 * 修改用户信息
	 */
	@RemoteMethod
	public String updateUserInfo(JSONObject jsonObject) {
		return cpUserService.updateUserInfo(jsonObject);
	}
	
	/**
	 * @author LiangGe
	 * @param ids
	 * @return
	 * 删除用户信息
	 */
	@RemoteMethod
	public boolean deleteUserInfo(List<String> ids) {
		return cpUserService.deleteUserInfo(ids);
	}
}
