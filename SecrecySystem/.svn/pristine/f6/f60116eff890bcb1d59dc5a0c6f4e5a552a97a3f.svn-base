package com.egov.secrecysystem.filter;




import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Filter class handle the security of the application.
 * 
 * It should be configured inside the web.xml.
 * 
 * @author egov
 */
public class ResourceCheckFilter implements Filter {

	private FilterConfig filterConfig = null;
	private String loginURI = null;
	private Logger logger = LoggerFactory.getLogger(ResourceCheckFilter.class);

	// a set of restricted resources
	private Set<String> unprotectResourcesSet;
	private  SessionFactory sessionFactory;
	
	public static Map rolenumAndMenuMap =  new HashMap();
	private static int hasUpdateFlag=0;

	/**
	 * Initializes the Filter.
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public Set<String> getUnprotectResourcesSet() {
		return this.unprotectResourcesSet;
	}

	public void setUnprotectResourcesSet(Set<String> unprotectResourcesSet) {
		this.unprotectResourcesSet = unprotectResourcesSet;
	}
	
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String getLoginURI() {
		return this.loginURI;
	}

	public void setLoginURI(String loginURI) {
		this.loginURI = loginURI;
	}

	/**
	 * Standard doFilter object.
	 */
	public void doFilter(ServletRequest req, ServletResponse rsp,
			FilterChain chain) throws IOException, ServletException {
		String requestUri = ((HttpServletRequest) req).getRequestURI().trim();
		requestUri = requestUri.replaceFirst(
				"^" + ((HttpServletRequest) req).getContextPath(), "");
		if (this.isProtectedResource(requestUri)) {
			logger.debug("访问受保护资源：" + requestUri);
			if (isAuthentic(req, rsp, chain)) {
				logger.debug("您已经过授权！" + requestUri);
				chain.doFilter(req, rsp);
			} else {
				logger.debug("您未经过授权" + requestUri);
				ServletContext servletContext = req.getServletContext();
				RequestDispatcher requestDispatcher = null;
				requestDispatcher = servletContext.getRequestDispatcher("/Info.jsp"); // 定向的页�?				((HttpServletRequest) req).setAttribute("msg", "您没有权限访问此页面，或者该页面不存在！");
				requestDispatcher.forward(req, rsp);
			}
		} else {
			logger.debug("访问未受限资源：" + requestUri);
			chain.doFilter(req, rsp);
		}
	}

	public void destroy() {
	}

	public boolean isProtectedResource(String url) {
		Pattern pattern;
		Matcher matcher;
		for (String reg : this.unprotectResourcesSet) {
			pattern = Pattern.compile(reg);
			matcher = pattern.matcher(url);
			if (matcher.matches())
				return false;
		}
		return true;
	}

	public boolean isAuthentic(ServletRequest req, ServletResponse rsp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession curSession = request.getSession();
		Integer role = (Integer) curSession.getAttribute("role");
//		Integer role = 0;
		if (role == null || role.toString().isEmpty()) {
			logger.debug("尚未登录");
			return false;
		} else {
			if(hasUpdateFlag==0){
				Session session = sessionFactory.getCurrentSession();
                /** Starting the Transaction */
                String HQL_QUERY = "select r.rolenum from com.egov.secrecysystem.model.SeRole r where r.rolenum is not null group by r.rolenum";
				List<Integer> roleNum =session.createQuery(HQL_QUERY).list();
				for(Integer element:roleNum){
					HQL_QUERY="select t.onclickscript from com.egov.secrecysystem.model.SeMainmenu t where t.seMainmenu is not null and t.onclickscript like '%.jsp' and  t.seRole.rolenum="+element;
					rolenumAndMenuMap.put(element, session.createQuery(HQL_QUERY).list());
				}
				hasUpdateFlag=1;
			}
			boolean result= authenticByRoleNumAndMenu(req,rsp,chain,rolenumAndMenuMap);
			if(result==true){
				logger.debug(request.getRequestURI() +"=============>Pass!");
			}else
			{
				logger.debug(request.getRequestURI() +"#################Stop!");
			}
			return result;
		}
	}
	public boolean authenticByRoleNumAndMenu(ServletRequest req, ServletResponse rsp,
			FilterChain chain,Map rolenumAndMenuMap) throws IOException, ServletException {
		String requestUri = ((HttpServletRequest) req).getRequestURI().trim();
		requestUri = requestUri.replaceFirst(
				"^" + ((HttpServletRequest) req).getContextPath(), "");
		requestUri=requestUri.substring(1);
		if(!(requestUri.trim().endsWith(".jsp")&&requestUri.startsWith("jsp/")))
			return true;
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession curSession = request.getSession();
		Integer role = (Integer) curSession.getAttribute("role");
	//	Integer role = 0;
		List<String> relatedList= (List<String>) rolenumAndMenuMap.get(role);
		for(String element:relatedList){
			if(element.trim().endsWith(requestUri.trim().trim())){
				logger.debug("Match:==================>" +element);
				return true;
			}
		}
		return false;
	}
}
