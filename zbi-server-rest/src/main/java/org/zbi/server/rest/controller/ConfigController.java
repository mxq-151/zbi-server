package org.zbi.server.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.FolderDaoService;
import org.zbi.server.dao.service.GroupDaoService;
import org.zbi.server.entity.mysql.GroupBoard;
import org.zbi.server.entity.mysql.GroupUser;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeBoard;
import org.zbi.server.model.facade.FacadeFolder;
import org.zbi.server.model.facade.FacadeGroup;
import org.zbi.server.model.facade.FacadeUser;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/admin/v1")
public class ConfigController extends BaseController {

	@Autowired
	GroupDaoService groupDaoService;

	@Autowired
	FolderDaoService folderDaoService;

	@RequestMapping(value = "/create/group", method = RequestMethod.GET)
	@ApiOperation(value = "获取看板列表接口", code = 200, httpMethod = "GET", response = FacadeBoard.class)
	public FacadeGroup createGroup( @ApiParam(value = "用户组名", required = true) String groupName) throws AdminException {
		return this.groupDaoService.createGroup(groupName, null);

	}

	@RequestMapping(value = "/get/group", method = RequestMethod.GET)
	@ApiOperation(value = "获取用户组详细", code = 200, httpMethod = "GET", response = FacadeBoard.class)
	public FacadeGroup getGroup(@ApiParam(value = "用户组ID", required = true) String groupID) {
		return this.groupDaoService.getGroupByGroupID(groupID);
	}


	@RequestMapping(value = "/list/folders", method = RequestMethod.GET)
	@ApiOperation(value = "获取模型信息接口", code = 200, httpMethod = "GET")
	public List<FacadeFolder> listFolders(@ApiParam(value = "用户组ID", required = false) String groupID) {
		return this.folderDaoService.listAdminFolders();
	}

	@RequestMapping(value = "/list/group/user", method = RequestMethod.GET)
	@ApiOperation(value = "列出当前登录用户组下的用户", code = 200, httpMethod = "GET", response = FacadeUser.class, responseContainer = "List")
	public List<FacadeUser> listGroupUsers(@ApiParam(value = "用户组ID", required = true) String groupID)
			throws AdminException {
		return this.groupDaoService.getGroupUsers(groupID);
	}

	@RequestMapping(value = "/add/user", method = RequestMethod.POST)
	@ApiOperation(value = "添加用户到用户组", code = 200, httpMethod = "POST")
	public int addUserToGroup(@RequestBody List<GroupUser> users,
			@ApiParam(value = "用户组ID", required = true) String groupID) throws AdminException {
		this.groupDaoService.addUserToGroup(users, groupID);
		return users.size();
	}

	@RequestMapping(value = "/add/board", method = RequestMethod.POST)
	@ApiOperation(value = "添加用户到用户组", code = 200, httpMethod = "POST")
	public int addBoardToGroup(@RequestBody List<GroupBoard> boards) throws AdminException {
		return this.groupDaoService.addBoardToGroup(boards);
	}

	@RequestMapping(value = "/delete/group", method = RequestMethod.GET)
	@ApiOperation(value = "删除用户组", code = 200, httpMethod = "GET")
	public boolean updateBoard(@ApiParam(value = "用户组ID", required = true) String groupID) throws AdminException {
		this.groupDaoService.deleteGroup(groupID);
		return true;
	}

}
