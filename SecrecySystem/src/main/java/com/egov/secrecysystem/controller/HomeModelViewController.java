package com.egov.secrecysystem.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.egov.secrecysystem.service.CpMainMenuService;

@Controller
public class HomeModelViewController {

	@Autowired
	private CpMainMenuService cpMainMenuService;

	/**
	 * @author LiangGe
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             返回Home视图模型
	 */
	@RequestMapping(value = "Home", method = RequestMethod.GET)
	public ModelAndView getindexPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer roleNum = 0;
		ModelAndView model = new ModelAndView("Home");
		HttpSession curSession = request.getSession();

		roleNum = (Integer) curSession.getAttribute("role");
		System.out.println("rolenum = " + roleNum);
		String institution = (String) curSession.getAttribute("remark");
		String username = (String) curSession.getAttribute("name");
		model.addObject("menus", cpMainMenuService.getAuthorisedMenu(roleNum));
		model.addObject("username", username);
		model.addObject("institution", institution);
		return model;
	}
}
