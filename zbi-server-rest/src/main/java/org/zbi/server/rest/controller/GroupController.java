package org.zbi.server.rest.controller;

import java.sql.SQLException;
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
import org.zbi.server.entity.mysql.ConnInfo;
import org.zbi.server.entity.mysql.ConnParam;
import org.zbi.server.entity.mysql.GroupBoard;
import org.zbi.server.entity.mysql.GroupUser;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.core.AggType;
import org.zbi.server.model.core.ColumnType;
import org.zbi.server.model.core.EngineType;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.facade.FacadeBoard;
import org.zbi.server.model.facade.FacadeFolder;
import org.zbi.server.model.facade.FacadeGroup;
import org.zbi.server.model.facade.FacadeTable;
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
		return this.folderDaoService.listAdminFolders();
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

	@RequestMapping(value = "/add/board", method = RequestMethod.POST)
	@ApiOperation(value = "添加用户到用户组", code = 200, httpMethod = "POST")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public int addBoardToGroup(@RequestBody List<GroupBoard> boards) throws AdminException {
		return this.groupDaoService.addBoardToGroup(boards);
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

	@RequestMapping(value = "/save/conn", method = RequestMethod.POST)
	@ApiOperation(value = "保存连接", code = 200, httpMethod = "POST")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public boolean saveConnect(@RequestBody ConnInfo connInfo) throws AdminException {
		this.daoService.saveConnect(connInfo);
		return true;
	}

	@RequestMapping(value = "/save/param", method = RequestMethod.POST)
	@ApiOperation(value = "保存连接", code = 200, httpMethod = "POST")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public boolean saveParam(@RequestBody List<ConnParam> params) throws AdminException, SQLException, ParseException {
		this.daoService.inserParam(params);
		return true;
	}

	@RequestMapping(value = "/load/conn", method = RequestMethod.GET)
	@ApiOperation(value = "加载连接", code = 200, httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<ConnInfo> loadConnect() throws AdminException {
		return this.daoService.loadConn();
	}

	@RequestMapping(value = "/load/param", method = RequestMethod.GET)
	@ApiOperation(value = "加载连接参数", code = 200, httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<ConnParam> loadParam(
			@Valid @RequestParam(required = true) @ApiParam(value = "连接ID", required = true) String connID)
			throws AdminException {
		return this.daoService.getParams(connID);
	}

	@RequestMapping(value = "/load/engine", method = RequestMethod.GET)
	@ApiOperation(value = "加载引擎类型", code = 200, httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public EngineType[] loadEngine() throws AdminException {
		return EngineType.values();
	}

	@RequestMapping(value = "/column/type", method = RequestMethod.GET)
	@ApiOperation(value = "加载引擎类型", code = 200, httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public ColumnType[] columnType() throws AdminException {
		return ColumnType.values();
	}

	@RequestMapping(value = "/agg/type", method = RequestMethod.GET)
	@ApiOperation(value = "加载引擎类型", code = 200, httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public AggType[] aggType() throws AdminException {
		return AggType.values();
	}

	@RequestMapping(value = "/load/source/table", method = RequestMethod.GET)
	@ApiOperation(value = "加载源表", code = 200, httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<ConfigTable> loadSourceTable() throws AdminException {
		return this.daoService.queryConfigTables(true);
	}

	@RequestMapping(value = "/load/columns", method = RequestMethod.GET)
	@ApiOperation(value = "加载字段", code = 200, httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<ConfigColumn> loadColumns(
			@Valid @RequestParam(required = true) @ApiParam(value = "表ID", required = true) String tableID)
			throws AdminException {
		return this.daoService.getConfigColumns(tableID);
	}

	@RequestMapping(value = "/save/table", method = RequestMethod.POST)
	@ApiOperation(value = "保存表", code = 200, httpMethod = "POST")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public boolean saveTable(@RequestBody FacadeTable table) throws AdminException {
		this.daoService.saveConfigTable(table);
		return true;
	}

}
