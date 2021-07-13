package org.zbi.server.dao.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.entity.mysql.ConnInfo;
import org.zbi.server.entity.mysql.ConnParam;
import org.zbi.server.entity.mysql.GroupColLimit;
import org.zbi.server.entity.mysql.GroupDataLimit;
import org.zbi.server.entity.mysql.QueryColumn;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.entity.mysql.UserColLimit;
import org.zbi.server.entity.mysql.UserDataLimit;
import org.zbi.server.entity.mysql.UserInfo;
import org.zbi.server.mapper.mysql.ColumnLimitMapper;
import org.zbi.server.mapper.mysql.ConfigColumnMapper;
import org.zbi.server.mapper.mysql.ConfigJoinMapper;
import org.zbi.server.mapper.mysql.ConfigTableMapper;
import org.zbi.server.mapper.mysql.ConnInfoMapper;
import org.zbi.server.mapper.mysql.DataLimitMapper;
import org.zbi.server.mapper.mysql.QueryModelMapper;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigJoin;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.engine.EngineFactory;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.facade.FacadeColumn;
import org.zbi.server.model.facade.FacadeJoin;
import org.zbi.server.model.facade.FacadeTable;
import org.zbi.server.model.facade.FacadeUser;
import org.zbi.server.model.response.ColumnInfoResp;
import org.zbi.server.model.response.ModelInfoResp;
import org.zbi.server.model.service.LoginUserService;
import org.zbi.server.model.service.ModelService;

@Component
public class MysqlConfigDaoService extends DaoServiceBase implements ConfigDaoService {

	@Autowired
	private ConfigTableMapper configTableMapper;

	@Autowired
	private ConfigColumnMapper configColumnMapper;

	@Autowired
	private DataLimitMapper dataLimitMapper;

	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	private QueryModelMapper queryModelMapper;

	@Autowired
	private ConnInfoMapper connInfoMapper;

	@Autowired
	ColumnLimitMapper columnLimitMapper;

	@Autowired
	private ModelService modelService;

	@Autowired
	private EngineFactory engineFactory;

	@Autowired
	private ConfigJoinMapper configJoinMapper;

	@PostConstruct
	public void init() throws SQLException, ParseException {
		List<ConnInfo> conns = this.connInfoMapper.loadConn();
		for (ConnInfo conn : conns) {
			List<ConnParam> params = this.connInfoMapper.getParams(conn.getConnID());
			if (!params.isEmpty()) {
				this.engineFactory.putConnection(params, conn);
			}

		}
	}

	@Override
	public List<QueryModel> getModelDscriptions(String key) {
		// TODO Auto-generated method stub
		FacadeUser user = this.loginUserService.getLoginUser();
		if (user.getRoleType() == UserInfo.SUPERADMIN || user.getRoleType() == UserInfo.DEVELOPER) {
			return this.queryModelMapper.listAllModel();
		}
		String userID = this.loginUserService.getLoginUser().getUserID();
		return this.queryModelMapper.listQueryModel(userID);
	}

	@Override
	public void saveConfigTable(FacadeTable table) throws AdminException {
		// TODO Auto-generated method stub

		this.preHandle(table);
		this.developConfigCheck();

		if (table.getTableID() != null) {
			this.configTableMapper.deleteByPrimaryKey(table.getTableID());
			this.configColumnMapper.deleteByTablelId(table.getTableID());
		} else {
			table.setTableID(java.util.UUID.randomUUID().toString());
		}

		List<ConfigColumn> columns = table.getColumns();
		for (ConfigColumn col : columns) {
			if (col.getUuid() == null) {
				col.setUuid(java.util.UUID.randomUUID().toString());
			}
			this.preHandle(col);
		}
		ConfigTable ct = new ConfigTable();
		BeanUtils.copyProperties(table, ct);
		this.preHandle(ct);
		this.configTableMapper.insertTable(ct);
		if (columns != null && !columns.isEmpty()) {
			this.configColumnMapper.batchInsert(table.getColumns());
		}

	}

	@Override
	public void deleteConfigTable(String tableID) throws AdminException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		this.configTableMapper.deleteByPrimaryKey(tableID);
		this.configColumnMapper.deleteByTablelId(tableID);
	}

	@Override
	public List<ConfigTable> queryConfigTables(boolean source) throws AdminException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		return this.configTableMapper.loadTables();
	}

	@Override
	public List<ConfigColumn> getConfigColumns(String tableID) throws AdminException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		return this.configColumnMapper.listColumnsByTableID(tableID);
	}

	@Override
	public List<String> queryUserTotalColLimit(String modelID) {
		// TODO Auto-generated method stub
		String userID = loginUserService.getLoginUser().getUserID();
		List<String> limits = this.columnLimitMapper.queryUserTotalColLimit(userID, modelID);
		if (limits.isEmpty()) {
			List<QueryColumn> cols = this.queryModelMapper.listQueryColumn(modelID);
			List<String> res = new ArrayList<>(cols.size());
			for (QueryColumn qc : cols) {
				res.add(qc.getColID());
			}
			return res;
		} else {
			return limits;
		}
	}

	@Override
	public List<String> getDataLimit(String colID) {
		// TODO Auto-generated method stub
		String userID = loginUserService.getLoginUser().getUserID();
		return this.dataLimitMapper.queryUserTotalDataLimit(userID, colID);
	}

	@Override
	public List<GroupColLimit> queryGroupColLimit(String groupID, String modelID) {
		// TODO Auto-generated method stub
		return this.columnLimitMapper.queryGroupColLimit(groupID, modelID);
	}

	@Override
	public List<UserColLimit> queryUserColLimit(String userID, String modelID) {
		// TODO Auto-generated method stub
		return this.columnLimitMapper.queryUserColLimit(userID, modelID);
	}

	@Override
	public void insertUserColLimit(List<String> cols, String userID, String modelID) {
		// TODO Auto-generated method stub
		this.columnLimitMapper.deleteUserColLimit(userID, modelID);
		List<UserColLimit> queryColumns = new ArrayList<>(cols.size());
		for (String col : cols) {
			UserColLimit limit = new UserColLimit();
			limit.setColID(col);
			limit.setModelID(modelID);
			limit.setUserID(userID);
			queryColumns.add(limit);
		}
		this.preHandle(queryColumns);
		this.columnLimitMapper.insertUserColLimit(queryColumns);
	}

	@Override
	public void insertGroupColLimit(List<String> cols, String groupID, String modelID) {
		// TODO Auto-generated method stub
		this.columnLimitMapper.deleteGroupColLimit(groupID, modelID);
		List<GroupColLimit> queryColumns = new ArrayList<>(cols.size());
		for (String col : cols) {
			GroupColLimit limit = new GroupColLimit();
			limit.setColID(col);
			limit.setModelTag(modelID);
			limit.setGroupID(groupID);
			queryColumns.add(limit);
		}

		this.preHandle(queryColumns);
		this.columnLimitMapper.insertGroupColLimit(queryColumns);

	}

	@Override
	public void insertUserDataLimit(List<String> words, String userID, String colID) {
		// TODO Auto-generated method stub
		this.dataLimitMapper.deleteUserDataLimit(userID, colID);
		List<UserDataLimit> dataLimits = new ArrayList<>();

		for (String word : words) {
			UserDataLimit limit = new UserDataLimit();
			limit.setColID(colID);
			limit.setWord(word);
			limit.setUserID(userID);
			dataLimits.add(limit);
		}
		this.preHandle(dataLimits);
		this.dataLimitMapper.insertUserDataLimit(dataLimits);

	}

	@Override
	public List<String> queryUserDataLimit(String userID, String colID) {
		// TODO Auto-generated method stub
		return this.dataLimitMapper.queryUserDataLimit(userID, colID);
	}

	@Override
	public void insertGroupDataLimit(List<String> words, String groupID, String colID) {
		// TODO Auto-generated method stub
		this.dataLimitMapper.deleteGroupDataLimit(groupID, colID);
		List<GroupDataLimit> dataLimits = new ArrayList<>();

		for (String word : words) {
			GroupDataLimit limit = new GroupDataLimit();
			limit.setColID(colID);
			limit.setWord(word);
			limit.setGroupID(groupID);
			this.preHandle(limit);
			dataLimits.add(limit);
		}
		this.dataLimitMapper.insertGroupDataLimit(dataLimits);
	}

	@Override
	public List<String> queryGroupDataLimit(String groupID, String colID) {
		// TODO Auto-generated method stub
		return this.dataLimitMapper.queryGroupDataLimit(groupID, colID);
	}

	@Override
	public List<String> queryUserTotalDataLimit(String userID, String colID) {
		// TODO Auto-generated method stub
		return this.dataLimitMapper.queryUserTotalDataLimit(userID, colID);
	}

	@Override
	public void saveModel(String modelName) throws AdminException {
		// TODO Auto-generated method stub

		this.developConfigCheck();
		String modelID = java.util.UUID.randomUUID().toString();
		QueryModel model = new QueryModel();
		model.setModelID(modelID);
		model.setModelName(modelName);
		this.preHandle(model);
		this.queryModelMapper.createModel(model);
	}

	@Override
	public List<QueryColumn> listQueryColumn(String modelID) {
		// TODO Auto-generated method stub
		List<QueryColumn> columns= this.queryModelMapper.listQueryColumn(modelID);
		List<QueryColumn> fcs=new ArrayList<>();
		
		for(QueryColumn column:columns)
		{
			FacadeColumn fc=new FacadeColumn();
			BeanUtils.copyProperties(column, fc);
			ConfigColumn cc=this.modelService.getColumn(column.getColID());
			fc.setChinese(cc.getChinese());
			fc.setColName(cc.getColName());
			fcs.add(fc);
		}
		return fcs;
	}

	@Override
	public List<QueryModel> listQueryModel() {
		// TODO Auto-generated method stub

		FacadeUser user = this.loginUserService.getLoginUser();
		if (user.getRoleType() != UserInfo.SUPERADMIN && user.getRoleType() != UserInfo.DEVELOPER) {
			return this.queryModelMapper.listQueryModel(user.getUserID());
		} else {
			return this.queryModelMapper.listAllModel();
		}

	}

	@Override
	public List<QueryModel> listQueryModelByGroup(String groupID) {
		// TODO Auto-generated method stub
		FacadeUser user = this.loginUserService.getLoginUser();
		if (user.getRoleType() != UserInfo.SUPERADMIN && user.getRoleType() != UserInfo.DEVELOPER) {
			return this.queryModelMapper.listQueryModel(user.getUserID());
		} else {
			return this.queryModelMapper.listAllModel();
		}
	}

	private void developConfigCheck() throws AdminException {
		FacadeUser user = this.loginUserService.getLoginUser();
		if (user.getRoleType() != UserInfo.SUPERADMIN && user.getRoleType() != UserInfo.DEVELOPER) {
			throw new AdminException("没有权限执行操作");
		}
	}

	@Override
	public ModelInfoResp getModelInfo(String modelID) {
		// TODO Auto-generated method stub
		List<QueryColumn> columns = this.queryModelMapper.listQueryColumn(modelID);
		List<ColumnInfoResp> dims = new ArrayList<>();
		List<ColumnInfoResp> meas = new ArrayList<>();
		for (QueryColumn col : columns) {
			ConfigColumn cc = this.modelService.getColumn(col.getColID());
			ColumnInfoResp ci = new ColumnInfoResp();
			BeanUtils.copyProperties(cc, ci);
			if (cc.isMeasure()) {
				meas.add(ci);
			} else {
				dims.add(ci);
			}
		}

		ModelInfoResp model = new ModelInfoResp();
		model.setDimensions(dims);
		model.setMeasures(meas);
		return model;
	}

	@Override
	public void saveConnect(ConnInfo conn) throws AdminException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		if (StringUtils.isBlank(conn.getConnID())) {
			String connID = java.util.UUID.randomUUID().toString();
			conn.setConnID(connID);
		}
		this.preHandle(conn);
		this.connInfoMapper.createConnect(conn);
	}

	@Override
	public void inserParam(List<ConnParam> list) throws AdminException, SQLException, ParseException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		ConnInfo connInfo = this.connInfoMapper.loadConnInfo(list.get(0).getConnID());
		this.connInfoMapper.deleteParam(list.get(0).getConnID());
		this.connInfoMapper.inserParam(list);
		this.engineFactory.putConnection(list, connInfo);
	}

	@Override
	public List<ConnInfo> loadConn() {
		// TODO Auto-generated method stub
		return this.connInfoMapper.loadConn();
	}

	@Override
	public List<ConnParam> getParams(String connID) {
		// TODO Auto-generated method stub
		return this.connInfoMapper.getParams(connID);
	}

	@Override
	public void saveJoins(List<ConfigJoin> joins) {
		// TODO Auto-generated method stub
		for (ConfigJoin join : joins) {
			if(StringUtils.isBlank(join.getJoinID()))
			{
				join.setJoinID(java.util.UUID.randomUUID().toString());
			}
			this.configJoinMapper.insertJoin(join);
		}

	}

	@Override
	public boolean deleteJoin(String joinID) {
		// TODO Auto-generated method stub
		this.configJoinMapper.deleteByPrimaryKey(joinID);
		return true;
	}

	@Override
	public List<FacadeJoin> loadJoins() {
		// TODO Auto-generated method stub
		List<ConfigJoin> joins=this.configJoinMapper.loadAllJoins();
		List<FacadeJoin> list=new ArrayList<>();
		for(ConfigJoin join:joins)
		{
			FacadeJoin fj=new FacadeJoin();
			BeanUtils.copyProperties(join, fj);
			
			ConfigColumn cc=this.modelService.getColumn(join.getColID1());
			fj.setColName1(cc.getColName());
			cc=this.modelService.getColumn(join.getColID2());
			fj.setColName2(cc.getColName());
			
			ConfigTable table=this.modelService.getTable(join.getTblID1());
			fj.setTableName1(table.getTableName());
			table=this.modelService.getTable(join.getTblID1());
			fj.setTableName2(table.getTableName());
			list.add(fj);
		}
		return list;
	}

	@Override
	public void saveQueryColumn(String modelID, List<QueryColumn> queryColumns) throws AdminException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		this.preHandle(queryColumns);
		this.queryModelMapper.deleteQueryColumn(modelID);
		this.queryModelMapper.batchInsert(queryColumns);
		
	}

	@Override
	public void deleteModel(String modelID) throws AdminException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		this.queryModelMapper.deleteModel(modelID);
		
	}

}
