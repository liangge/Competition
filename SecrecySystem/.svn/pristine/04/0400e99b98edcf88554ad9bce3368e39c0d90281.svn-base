package com.egov.secrecysystem.controller;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.beans.factory.annotation.Autowired;

import com.egov.secrecysystem.service.ClearHistoryDataService;


@RemoteProxy(creator = SpringCreator.class)
public class ClearHistoryDataController {
	@Autowired
	private ClearHistoryDataService clearHistoryDataService;
	
	/**
	 * @author LiangGe
	 * @return
	 * 清除历史数据
	 */
	@RemoteMethod
	public String clearHistoryData() {
		return this.clearHistoryDataService.clearHistoryData();
	}
}
