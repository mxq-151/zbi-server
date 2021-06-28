package org.zbi.server.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.entity.mysql.GroupColLimit;
import org.zbi.server.entity.mysql.GroupDataLimit;
import org.zbi.server.entity.mysql.QueryColumn;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.entity.mysql.UserColLimit;
import org.zbi.server.entity.mysql.UserDataLimit;
import org.zbi.server.entity.mysql.UserInfo;
import org.zbi.server.mapper.mysql.ColumnLimitMapper;
import org.zbi.server.mapper.mysql.ConfigColumnMapper;
import org.zbi.server.mapper.mysql.ConfigTableMapper;
import org.zbi.server.mapper.mysql.DataLimitMapper;
import org.zbi.server.mapper.mysql.QueryModelMapper;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeTable;
import org.zbi.server.model.facade.FacadeUser;
import org.zbi.server.model.response.ModelDescResp;
import org.zbi.server.model.service.LoginUserService;

@Component
public class MysqlConfigDaoService implements ConfigDaoService {

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
	ColumnLimitMapper columnLimitMapper;

	@Override
	public List<ModelDescResp> getModelDscriptions(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveConfigTable(FacadeTable table) throws AdminException {
		// TODO Auto-generated method stub
		
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
		}
		ConfigTable ct = new ConfigTable();
		BeanUtils.copyProperties(table, ct);
		this.configTableMapper.insertTable(ct);
		this.configColumnMapper.batchInsert(table.getColumns());

	}

	@Override
	public void deleteConfigTable(String tableID) throws AdminException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		this.configTableMapper.deleteByPrimaryKey(tableID);
		this.configColumnMapper.deleteByTablelId(tableID);
	}

	@Override
	public List<ConfigTable> queryConfigTables() throws AdminException {
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
	public List<String> queryUserTotalColLimit(String modelTag) {
		// TODO Auto-generated method stub
		String userID = loginUserService.getLoginUser().getUserID();
		return this.columnLimitMapper.queryUserTotalColLimit(userID, modelTag);
	}

	@Override
	public List<String> getDataLimit(String colID) {
		// TODO Auto-generated method stub
		String userID = loginUserService.getLoginUser().getUserID();
		return this.dataLimitMapper.queryUserTotalDataLimit(userID, colID);
	}

	@Override
	public List<GroupColLimit> queryGroupColLimit(String groupID, String modelTag) {
		// TODO Auto-generated method stub
		return this.columnLimitMapper.queryGroupColLimit(groupID, modelTag);
	}

	@Override
	public List<UserColLimit> queryUserColLimit(String userID, String modelTag) {
		// TODO Auto-generated method stub
		return this.columnLimitMapper.queryUserColLimit(userID, modelTag);
	}

	@Override
	public void insertUserColLimit(List<String> cols, String userID, String modelTag) {
		// TODO Auto-generated method stub
		this.columnLimitMapper.deleteUserColLimit(userID, modelTag);
		List<UserColLimit> queryColumns = new ArrayList<>(cols.size());
		for (String col : cols) {
			UserColLimit limit = new UserColLimit();
			limit.setColID(col);
			limit.setModelTag(modelTag);
			limit.setUserID(userID);
			queryColumns.add(limit);
		}
		this.columnLimitMapper.insertUserColLimit(queryColumns);
	}

	@Override
	public void insertGroupColLimit(List<String> cols, String groupID, String modelTag) {
		// TODO Auto-generated method stub
		this.columnLimitMapper.deleteGroupColLimit(groupID, modelTag);
		List<GroupColLimit> queryColumns = new ArrayList<>(cols.size());
		for (String col : cols) {
			GroupColLimit limit = new GroupColLimit();
			limit.setColID(col);
			limit.setModelTag(modelTag);
			limit.setGroupID(groupID);
			queryColumns.add(limit);
		}

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
	public void saveModel(String modelTag, String modelName, List<QueryColumn> queryColumn) throws AdminException {
		// TODO Auto-generated method stub

		this.developConfigCheck();
		if (modelTag == null) {
			String modelID = java.util.UUID.randomUUID().toString();
			QueryModel model = new QueryModel();
			model.setModelID(modelID);
			model.setModelTag(modelID);
			model.setModelName(modelName);
			for (QueryColumn qc : queryColumn) {
				qc.setModelTag(modelID);
			}
			this.queryModelMapper.batchInsert(queryColumn);
			this.queryModelMapper.createModel(model);
		} else {
			this.queryModelMapper.deleteModel(modelTag);
			this.queryModelMapper.deleteQueryColumn(modelTag);

			QueryModel model = new QueryModel();
			model.setModelID(modelTag);
			model.setModelTag(modelTag);
			model.setModelName(modelName);

			for (QueryColumn qc : queryColumn) {
				qc.setModelTag(modelTag);
			}
			this.queryModelMapper.batchInsert(queryColumn);
			this.queryModelMapper.createModel(model);
		}

	}

	@Override
	public List<QueryColumn> listQueryColumn(String modelTag) {
		// TODO Auto-generated method stub
		return this.queryModelMapper.listQueryColumn(modelTag);
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

	private void developConfigCheck() throws AdminException {
		FacadeUser user = this.loginUserService.getLoginUser();
		if (user.getRoleType() != UserInfo.SUPERADMIN && user.getRoleType() != UserInfo.DEVELOPER) {
			throw new AdminException("没有权限执行操作");
		}
	}

}
