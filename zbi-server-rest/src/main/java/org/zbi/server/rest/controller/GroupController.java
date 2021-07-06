package org.zbi.server.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.dao.service.FolderDaoService;
import org.zbi.server.dao.service.GroupDaoService;
import org.zbi.server.entity.mysql.GroupUser;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeBoard;
import org.zbi.server.model.facade.FacadeFolder;
import org.zbi.server.model.facade.FacadeGroup;
import org.zbi.server.model.facade.FacadeUser;
import org.zbi.server.model.response.ModelDescResp;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/admin/v1")
public class GroupController extends BaseController {

	@Autowired
	GroupDaoService groupDaoService;
	
	@Autowired
	private ConfigDaoService daoService;
	
	@Autowired
	FolderDaoService folderDaoService;

	@RequestMapping(value = "/create/group", method = RequestMethod.GET)
	@ApiOperation(value = "获取看板列表接口", code = 200, httpMethod = "GET", response = FacadeBoard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public FacadeGroup createGroup(
			@Valid @RequestParam(required = true) @ApiParam(value = "用户组名", required = true) String groupName)
			throws AdminException {
		return this.groupDaoService.createGroup(groupName, null);

	}

	@RequestMapping(value = "/get/group", method = RequestMethod.GET)
	@ApiOperation(value = "获取用户组详细", code = 200, httpMethod = "GET", response = FacadeBoard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public FacadeGroup getGroup(
			@Valid @RequestParam(required = true) @ApiParam(value = "用户组ID", required = true) String groupID) {
		return this.groupDaoService.getGroupByGroupID(groupID);
	}
	
	@RequestMapping(value = "/list/group/model", method = RequestMethod.GET)
	@ApiOperation(value = "获取模型信息接口", code = 200, httpMethod = "GET", response = ModelDescResp.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<QueryModel> getModelInfo(
			@Valid @RequestParam(required = false) @ApiParam(value = "用户组ID", required = false) String groupID) {
		return this.daoService.listQueryModelByGroup(groupID);
	}
	
	@RequestMapping(value = "/list/folders", method = RequestMethod.GET)
	@ApiOperation(value = "获取模型信息接口", code = 200, httpMethod = "GET", response = ModelDescResp.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<FacadeFolder> listFolders(
			@Valid @RequestParam(required = false) @ApiParam(value = "用户组ID", required = false) String groupID) {
		return this.folderDaoService.getFolders();
	}

	@RequestMapping(value = "/list/user/group", method = RequestMethod.GET)
	@ApiOperation(value = "列出当前登录用户管理的用户组", code = 200, httpMethod = "GET", response = FacadeBoard.class, responseContainer = "List")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<FacadeGroup> listGroups() throws AdminException {
		return this.groupDaoService.getAdminGroup();
	}

	@RequestMapping(value = "/list/group/user", method = RequestMethod.GET)
	@ApiOperation(value = "列出当前登录用户组下的用户", code = 200, httpMethod = "GET", response = FacadeBoard.class, responseContainer = "List")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<FacadeUser> listGroupUsers(
			@Valid @RequestParam(required = true) @ApiParam(value = "用户组ID", required = true) String groupID)
			throws AdminException {
		return this.groupDaoService.getGroupUsers(groupID);
	}

	@RequestMapping(value = "/add/user", method = RequestMethod.POST)
	@ApiOperation(value = "添加用户到用户组", code = 200, httpMethod = "POST")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public int addUserToGroup(@RequestBody List<GroupUser> users,
			@Valid @RequestParam(required = true) @ApiParam(value = "用户组ID", required = true) String groupID)
			throws AdminException {
		this.groupDaoService.addUserToGroup(users, groupID);
		return users.size();
	}

	@RequestMapping(value = "/delete/group", method = RequestMethod.GET)
	@ApiOperation(value = "删除用户组", code = 200, httpMethod = "GET", response = FacadeBoard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public boolean updateBoard(
			@Valid @RequestParam(required = true) @ApiParam(value = "用户组ID", required = true) String groupID)
			throws AdminException {
		this.groupDaoService.deleteGroup(groupID);
		return true;
	}

}
