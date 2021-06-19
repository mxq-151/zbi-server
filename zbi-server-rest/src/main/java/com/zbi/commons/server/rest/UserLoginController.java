package com.zbi.commons.server.rest;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.UserDaoService;
import org.zbi.server.model.exception.LoginException;
import org.zbi.server.model.facade.FacadeUser;

import com.zbi.commons.server.constant.WebConstant;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
public class UserLoginController extends BaseController{
	

	@Autowired
	UserDaoService UserDaoService;
	
	@PostConstruct
	public void initUser()
	{
		FacadeUser user=new FacadeUser();
		user.setEmail("578038303@qq.com");
		user.setPassword("123456");
		user.setUserID(1);
		user.setUserName("moxuqiang");
		user.setRoleType(1);
		this.UserDaoService.createUser(user);
		
		
		user=new FacadeUser();
		user.setEmail("123456@qq.com");
		user.setPassword("123456");
		user.setUserID(1);
		user.setUserName("moxuqiang");
		user.setRoleType(1);
		this.UserDaoService.createUser(user);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ApiOperation(value = "密码登录", code = 200, httpMethod = "GET", response = FacadeUser.class)
	public FacadeUser login(@Valid @RequestParam @ApiParam(value = "用户名", required = true) String userName,@Valid @RequestParam @ApiParam(value = "密码", required = true) String password,HttpServletRequest httpServletRequest) throws LoginException
	{
		
		FacadeUser userInfo=this.UserDaoService.getUserWrapperInfoByEmail(userName);
		if(userInfo!=null && userInfo.getPassword().equals(password))
		{
			httpServletRequest.getSession().setAttribute(WebConstant.USER_SESSION_KEY, userInfo);
			return userInfo;
		}
		throw new LoginException("找不到用户或者密码不对");
	}
	

	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	@ApiOperation(value = "第三方登录", code = 200, httpMethod = "GET", response = FacadeUser.class)
	public FacadeUser loginWithoutPassword(@Valid @RequestParam @ApiParam(value = "用户名", required = true) String userName,HttpServletRequest httpServletRequest) throws LoginException
	{
		
		FacadeUser userInfo=this.UserDaoService.getUserWrapperInfoByEmail(userName);
		if(userInfo!=null)
		{
			httpServletRequest.getSession().setAttribute(WebConstant.USER_SESSION_KEY, userInfo);
			return userInfo;
		}
		throw new LoginException("找不到用户");
	}
	

}
