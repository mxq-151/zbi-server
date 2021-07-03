package org.zbi.server.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.UserDaoService;
import org.zbi.server.entity.mysql.GroupUser;
import org.zbi.server.entity.mysql.UserInfo;
import org.zbi.server.model.exception.LoginException;
import org.zbi.server.model.facade.FacadeUser;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	UserDaoService UserDaoService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ApiOperation(value = "加载用户", code = 200, httpMethod = "GET", response = FacadeUser.class)
	public List<FacadeUser> loadAllUser() throws LoginException {
		return this.UserDaoService.loadAllUser();
	}
	
	@RequestMapping(value = "/rights", method = RequestMethod.GET)
	@ApiOperation(value = "加载", code = 200, httpMethod = "GET", response = FacadeUser.class)
	public List<JSONObject> rights() throws LoginException {
		
		List<JSONObject> array=new ArrayList<>();
		
		array.add(this.rightToJson("编辑看板", GroupUser.EDITBOARD));
		array.add(this.rightToJson("删除看板", GroupUser.DELGROUP));
		array.add(this.rightToJson("编辑用户组", GroupUser.EDITGROUP));
		array.add(this.rightToJson("删除用户组", GroupUser.DELGROUP));
		return array;
		
	}
	
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	@ApiOperation(value = "加载", code = 200, httpMethod = "GET", response = FacadeUser.class)
	public List<JSONObject> roles() throws LoginException {
		
		List<JSONObject> array=new ArrayList<>();
		
		array.add(this.toJson("超级管理员", UserInfo.SUPERADMIN));
		array.add(this.toJson("部门管理员", UserInfo.DEPARTMENTADMIN));
		array.add(this.toJson("开发人员", UserInfo.DEVELOPER));
		array.add(this.toJson("组管理员", UserInfo.GROUPADMIN));
		array.add(this.toJson("普通用户", UserInfo.MEMBERS));
		return array;
		
	}
	
	private JSONObject rightToJson(String rightName,int rightType)
	{
		JSONObject obj=new JSONObject();
		obj.put("rightName", rightName);
		obj.put("rightType", rightType);
		
		return obj;
	}
	
	private JSONObject toJson(String roleName,int roleType)
	{
		JSONObject obj=new JSONObject();
		obj.put("roleName", roleName);
		obj.put("roleType", roleType);
		
		return obj;
	}

}