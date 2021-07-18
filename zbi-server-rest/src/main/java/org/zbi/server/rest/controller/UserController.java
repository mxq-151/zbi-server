package org.zbi.server.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.UserDaoService;
import org.zbi.server.entity.mysql.GroupUser;
import org.zbi.server.entity.mysql.UserInfo;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.exception.LoginException;
import org.zbi.server.model.facade.FacadeUser;
import org.zbi.server.model.service.LoginUserService;
import org.zbi.server.rest.config.AuthorizationServerConfig.DefaultTokenServices;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	UserDaoService userDaoService;

	@Autowired
	LoginUserService loginUserService;

	@Autowired
	private DefaultTokenServices defaultTokenServices;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ApiOperation(value = "加载用户", code = 200, httpMethod = "GET", response = FacadeUser.class)
	public List<FacadeUser> loadAllUser() throws LoginException {
		return this.userDaoService.loadAllUser();
	}

	@RequestMapping(value = "/manual", method = RequestMethod.POST)
	@ApiOperation(value = "添加用户", code = 200, httpMethod = "POST", response = FacadeUser.class)
	public List<FacadeUser> manual(@RequestBody List<FacadeUser> users) throws LoginException, AdminException {
		for (FacadeUser user : users) {
			this.userDaoService.createUser(user);
		}

		return this.userDaoService.loadAllUser();
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ApiOperation(value = "加载用户组权限", code = 200, httpMethod = "GET")
	public boolean logOut(HttpServletRequest request) throws LoginException {
		String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.contains("Bearer")) {
			String email = this.loginUserService.getLoginUser().getEmail();
			this.logger.info("user {} logout", email);
			String tokenId = authorization.substring("Bearer".length() + 1);
			defaultTokenServices.revokeToken(tokenId);
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/rights", method = RequestMethod.GET)
	@ApiOperation(value = "加载用户组权限", code = 200, httpMethod = "GET")
	public List<JSONObject> rights() throws LoginException {

		List<JSONObject> array = new ArrayList<>();

		array.add(this.rightToJson("编辑看板", GroupUser.EDITBOARD));
		array.add(this.rightToJson("删除看板", GroupUser.DELGROUP));
		array.add(this.rightToJson("编辑用户组", GroupUser.EDITGROUP));
		array.add(this.rightToJson("删除用户组", GroupUser.DELGROUP));
		return array;

	}

	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	@ApiOperation(value = "加载角色", code = 200, httpMethod = "GET")
	public List<JSONObject> roles() throws LoginException {

		List<JSONObject> array = new ArrayList<>();

		array.add(this.toJson("超级管理员", UserInfo.SUPERADMIN));
		array.add(this.toJson("部门管理员", UserInfo.DEPARTMENTADMIN));
		array.add(this.toJson("开发人员", UserInfo.DEVELOPER));
		array.add(this.toJson("组管理员", UserInfo.GROUPADMIN));
		array.add(this.toJson("普通用户", UserInfo.MEMBERS));
		return array;

	}

	private JSONObject rightToJson(String rightName, int rightType) {
		JSONObject obj = new JSONObject();
		obj.put("rightName", rightName);
		obj.put("rightType", rightType);

		return obj;
	}

	private JSONObject toJson(String roleName, int roleType) {
		JSONObject obj = new JSONObject();
		obj.put("roleName", roleName);
		obj.put("roleType", roleType);

		return obj;
	}

}
