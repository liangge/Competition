package com.egov.secrecysystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.egov.secrecysystem.service.CpTestpaperRecordService;

@RemoteProxy(creator = SpringCreator.class)
public class CpTestpaperRecordController {
	@Autowired
	private CpTestpaperRecordService cpTestpaperRecordService;
	//获取最新的出卷记录
		@RemoteMethod
		public Map getTestPaperRecordList() {
			List<Map> result = cpTestpaperRecordService.getTestPaperRecordList();
			
			Map map = new HashMap();
			map.put("totalProperty",1);
			map.put("root", result);
			return map;
		}
		
		/**
		 * @author LiangGe
		 * @param jsonObject
		 * @return
		 * 保存竞赛时间基本信息
		 */
		@RemoteMethod
		public boolean saveCompetitionTime(JSONObject jsonObject) {
			return cpTestpaperRecordService.modifyCompetitionTime(jsonObject);
		}
}
