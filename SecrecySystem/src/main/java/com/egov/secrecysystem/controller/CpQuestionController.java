package com.egov.secrecysystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.egov.secrecysystem.model.Function;
import com.egov.secrecysystem.service.CpQuestionService;

@RemoteProxy(creator = SpringCreator.class)
public class CpQuestionController {
	@Autowired
	private CpQuestionService cpQuestionService;

	// 显示所有题目
	@RemoteMethod
	public Map getQuestionRecordList(String loginName) {
/*		String loginName = (String) WebContextFactory.get().getSession()
				.getAttribute("name");*/
		List<Map> result = cpQuestionService.getQuestionRecordList(loginName);

		Map map = new HashMap();
		map.put("totalProperty", result.size());
		map.put("root", result);
		return map;
	}

	// 初始填空题页面
	@RemoteMethod
	public Map pageInit(String filterString, String pageNum, String pageSize) {

		Map map = new HashMap();
		String whereString = Function.initSearchHql_whereString_(filterString);
		Map<String, Object> paramsMap = Function.initSearchValues(filterString);
		List<Map> result = cpQuestionService.pageInit(whereString, paramsMap,
				pageNum, pageSize);
		map.put("totalProperty", cpQuestionService.getInstructorsTotalCount(
				whereString, paramsMap));
		map.put("root", result);
		return map;
	}

	// 更新题目信息
	@RemoteMethod
	public String updateInstructorAllInfo(JSONObject instructor) {
		return cpQuestionService.updateInstructorAllInfo(instructor);
	}

	// 刪除题目信息
	@RemoteMethod
	public String deleteInstructors(List<String> ids) {
		return cpQuestionService.deleteInstructors(ids);
	}
}
