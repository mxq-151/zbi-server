package org.zbi.server.rest.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.dao.service.FolderDaoService;
import org.zbi.server.dao.service.GroupDaoService;
import org.zbi.server.entity.mysql.ConnInfo;
import org.zbi.server.entity.mysql.ConnParam;
import org.zbi.server.entity.mysql.GroupBoard;
import org.zbi.server.entity.mysql.GroupUser;
import org.zbi.server.entity.mysql.QueryColumn;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigJoin;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.core.AggType;
import org.zbi.server.model.core.ColumnType;
import org.zbi.server.model.core.EngineType;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.facade.FacadeBoard;
import org.zbi.server.model.facade.FacadeFolder;
import org.zbi.server.model.facade.FacadeGroup;
import org.zbi.server.model.facade.FacadeJoin;
import org.zbi.server.model.facade.FacadeTable;
import org.zbi.server.model.facade.FacadeUser;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	public FacadeGroup createGroup(@ApiParam(value = "用户组名", required = true) String groupName) throws AdminException {
		return this.groupDaoService.createGroup(groupName, null);

	}

	@RequestMapping(value = "/get/group", method = RequestMethod.GET)
	@ApiOperation(value = "获取用户组详细", code = 200, httpMethod = "GET", response = FacadeBoard.class)
	public FacadeGroup getGroup(@ApiParam(value = "用户组ID", required = true) String groupID) {
		return this.groupDaoService.getGroupByGroupID(groupID);
	}

	@RequestMapping(value = "/list/group/model", method = RequestMethod.GET)
	@ApiOperation(value = "获取模型信息接口", code = 200, httpMethod = "GET")
	public List<QueryModel> getModelInfo(@ApiParam(value = "用户组ID", required = false) String groupID) {
		return this.daoService.listQueryModelByGroup(groupID);
	}

	@RequestMapping(value = "/list/folders", method = RequestMethod.GET)
	@ApiOperation(value = "获取模型信息接口", code = 200, httpMethod = "GET")
	public List<FacadeFolder> listFolders(@ApiParam(value = "用户组ID", required = false) String groupID) {
		return this.folderDaoService.listAdminFolders();
	}

	@RequestMapping(value = "/list/user/group", method = RequestMethod.GET)
	@ApiOperation(value = "列出当前登录用户管理的用户组", code = 200, httpMethod = "GET", response = FacadeBoard.class, responseContainer = "List")
	public List<FacadeGroup> listGroups() throws AdminException {
		return this.groupDaoService.getAdminGroup();
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

	@RequestMapping(value = "/save/conn", method = RequestMethod.POST)
	@ApiOperation(value = "保存连接", code = 200, httpMethod = "POST")
	public boolean saveConnect(@RequestBody ConnInfo connInfo) throws AdminException {
		this.daoService.saveConnect(connInfo);
		return true;
	}

	@RequestMapping(value = "/save/param", method = RequestMethod.POST)
	@ApiOperation(value = "保存连接", code = 200, httpMethod = "POST")
	public boolean saveParam(@RequestBody List<ConnParam> params) throws AdminException, SQLException, ParseException {
		this.daoService.inserParam(params);
		return true;
	}

	@RequestMapping(value = "/load/conn", method = RequestMethod.GET)
	@ApiOperation(value = "加载连接", code = 200, httpMethod = "GET")
	public List<ConnInfo> loadConnect() throws AdminException {
		return this.daoService.loadConn();
	}

	@RequestMapping(value = "/load/param", method = RequestMethod.GET)
	@ApiOperation(value = "加载连接参数", code = 200, httpMethod = "GET")
	public List<ConnParam> loadParam(@ApiParam(value = "连接ID", required = true) String connID) throws AdminException {
		return this.daoService.getParams(connID);
	}

	@RequestMapping(value = "/load/engine", method = RequestMethod.GET)
	@ApiOperation(value = "加载引擎类型", code = 200, httpMethod = "GET")
	public EngineType[] loadEngine() throws AdminException {
		return EngineType.values();
	}

	@RequestMapping(value = "/column/type", method = RequestMethod.GET)
	@ApiOperation(value = "加载引擎类型", code = 200, httpMethod = "GET")
	public ColumnType[] columnType() throws AdminException {
		return ColumnType.values();
	}

	@RequestMapping(value = "/agg/type", method = RequestMethod.GET)
	@ApiOperation(value = "加载引擎类型", code = 200, httpMethod = "GET")
	public AggType[] aggType() throws AdminException {
		return AggType.values();
	}

	@RequestMapping(value = "/load/source/table", method = RequestMethod.GET)
	@ApiOperation(value = "加载源表", code = 200, httpMethod = "GET")
	public List<ConfigTable> loadSourceTable() throws AdminException {
		return this.daoService.queryConfigTables(true);
	}

	@RequestMapping(value = "/load/columns", method = RequestMethod.GET)
	@ApiOperation(value = "加载字段", code = 200, httpMethod = "GET")
	public List<ConfigColumn> loadColumns(@ApiParam(value = "表ID", required = true) String tableID)
			throws AdminException {
		return this.daoService.getConfigColumns(tableID);
	}

	@RequestMapping(value = "/save/table", method = RequestMethod.POST)
	@ApiOperation(value = "保存表", code = 200, httpMethod = "POST")
	public boolean saveTable(@RequestBody FacadeTable table) throws AdminException {
		this.daoService.saveConfigTable(table);
		return true;
	}

	@RequestMapping(value = "/save/join", method = RequestMethod.POST)
	@ApiOperation(value = "保存连表", code = 200, httpMethod = "POST")
	public boolean saveJoin(@RequestBody List<ConfigJoin> joins) throws AdminException {
		this.daoService.saveJoins(joins);
		return true;
	}

	@RequestMapping(value = "/load/join", method = RequestMethod.GET)
	@ApiOperation(value = "加载连表", code = 200, httpMethod = "GET")
	public List<FacadeJoin> loadJoins() throws AdminException {
		return this.daoService.loadJoins();
	}

	@RequestMapping(value = "/del/join", method = RequestMethod.GET)
	@ApiOperation(value = "删除连表", code = 200, httpMethod = "GET")
	public boolean delJoin(@ApiParam(value = "表ID", required = true) String tableID) throws AdminException {
		return this.daoService.deleteJoin(tableID);
	}

	@RequestMapping(value = "/create/model", method = RequestMethod.GET)
	@ApiOperation(value = "创建模型", code = 200, httpMethod = "GET")
	public boolean createModel(@ApiParam(value = "模型名称", required = true) String modelName) throws AdminException {
		this.daoService.saveModel(modelName);
		return true;
	}

	@RequestMapping(value = "/del/model", method = RequestMethod.GET)
	@ApiOperation(value = "删除模型", code = 200, httpMethod = "GET")
	public boolean deleteModel(@ApiParam(value = "模型ID", required = true) String modelID) throws AdminException {
		this.daoService.deleteModel(modelID);
		return true;
	}

	@RequestMapping(value = "/save/query/column", method = RequestMethod.POST)
	@ApiOperation(value = "保存模型", code = 200, httpMethod = "POST")
	public boolean saveModel(@RequestBody List<QueryColumn> queryColumns,
			@ApiParam(value = "模型ID", required = true) String modelID) throws AdminException {
		this.daoService.saveQueryColumn(modelID, queryColumns);
		return true;
	}

	@RequestMapping(value = "/load/model", method = RequestMethod.GET)
	@ApiOperation(value = "加载模型", code = 200, httpMethod = "GET")
	public List<QueryModel> loadModel() throws AdminException {
		return this.daoService.listQueryModel();
	}

	@RequestMapping(value = "/load/query/column", method = RequestMethod.GET)
	@ApiOperation(value = "加载模型", code = 200, httpMethod = "GET")
	public List<QueryColumn> loadQueryColumn(@ApiParam(value = "模型ID", required = true) String modelID)
			throws AdminException {
		return this.daoService.listQueryColumn(modelID);
	}

}
