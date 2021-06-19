package com.zbi.commons.server.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zbi.server.model.facade.FacadeUser;

import com.zbi.commons.server.constant.WebConstant;

/**
 * 用户登入拦截器
 */
public class UserSessionInterceptor implements HandlerInterceptor {
	
	 private static final Logger logger = LoggerFactory.getLogger(UserSessionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		FacadeUser user = (FacadeUser) httpServletRequest.getSession()
				.getAttribute(WebConstant.USER_SESSION_KEY);
		if (user == null) {
			httpServletResponse.setCharacterEncoding("utf-8");
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return true;
		}
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {

	}
}
