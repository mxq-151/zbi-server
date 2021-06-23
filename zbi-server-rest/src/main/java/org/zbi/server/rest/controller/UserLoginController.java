package org.zbi.server.rest.controller;

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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
public class UserLoginController extends BaseController {

	@Autowired
	UserDaoService UserDaoService;



	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ApiOperation(value = "密码登录", code = 200, httpMethod = "GET", response = FacadeUser.class)
	public FacadeUser login(@Valid @RequestParam @ApiParam(value = "用户名", required = true) String userName,
			@Valid @RequestParam @ApiParam(value = "密码", required = true) String password,
			HttpServletRequest httpServletRequest) throws LoginException {
		throw new LoginException("找不到用户或者密码不对");
	}

}
