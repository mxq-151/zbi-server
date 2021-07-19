package org.zbi.server.dao.mysql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.DevelopDaoService;
import org.zbi.server.entity.mysql.ConnInfo;
import org.zbi.server.entity.mysql.ConnParam;
import org.zbi.server.entity.mysql.QueryColumn;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.entity.mysql.UserInfo;
import org.zbi.server.mapper.mysql.ColumnLimitMapper;
import org.zbi.server.mapper.mysql.ConfigColumnMapper;
import org.zbi.server.mapper.mysql.ConfigJoinMapper;
import org.zbi.server.mapper.mysql.ConfigTableMapper;
import org.zbi.server.mapper.mysql.ConnInfoMapper;
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
import org.zbi.server.model.service.ModelService;

@Component
public class MysqlDevelopDaoService extends DaoServiceBase implements DevelopDaoService {

	@Autowired
	private QueryModelMapper queryModelMapper;

	@Autowired
	private ConnInfoMapper connInfoMapper;

	@Autowired
	ColumnLimitMapper columnLimitMapper;

	@Autowired
	private ModelService modelService;

	@Autowired
	private ConfigTableMapper configTableMapper;

	@Autowired
	private ConfigColumnMapper configColumnMapper;

	@Autowired
	private EngineFactory engineFactory;

	@Autowired
	private ConfigJoinMapper configJoinMapper;

	@PostConstruct
	public void init() throws SQLException, ParseException {
		List<ConnInfo> conns = this.connInfoMapper.loadConn();
		for (ConnInfo conn : conns) {
			List<ConnParam> params = this.connInfoMapper.loadParam(conn.getConnID());
			if (!params.isEmpty()) {
				this.engineFactory.putConnection(params, conn);
			}

		}
	}

	@Override
	public void saveConfigTable(FacadeTable table) throws AdminException {
		// TODO Auto-generated method stub

		this.preHandle(table);
		this.developConfigCheck();

		if (table.getTableID() != null) {
			this.configTableMapper.deleteByID(table.getTableID());
			this.configColumnMapper.deleteByTablelId(table.getTableID());
		} else {
			table.setTableID(java.util.UUID.randomUUID().toString());
		}

		List<ConfigColumn> columns = table.getColumns();
		for (ConfigColumn col : columns) {
			if (col.getUuid() == null) {
				col.setUuid(java.util.UUID.randomUUID().toString());
			}
			col.setTableID(table.getTableID());
			this.preHandle(col);
		}
		ConfigTable ct = new ConfigTable();
		BeanUtils.copyProperties(table, ct);
		this.preHandle(ct);
		this.configTableMapper.saveTable(ct);
		if (columns != null && !columns.isEmpty()) {
			this.configColumnMapper.saveColumn(table.getColumns());
		}

	}

	@Override
	public void deleteConfigTable(String tableID) throws AdminException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		this.configTableMapper.deleteByID(tableID);
		this.configColumnMapper.deleteByTablelId(tableID);
	}

	@Override
	public List<ConfigTable> loadConfigTable(boolean source) throws AdminException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		return this.configTableMapper.loadTable();
	}

	@Override
	public List<ConfigColumn> loadConfigColumn(String tableID) throws AdminException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		List<ConfigColumn> columns = this.configColumnMapper.loadColumnsByTableID(tableID);
		if (columns.isEmpty()) {
			ConfigTable table = this.configTableMapper.selectByID(tableID);
			try {
				return this.engineFactory.getQueryEngine(table.getConnID()).fetchMeta(table.getTableName(),
						table.getProject());
			} catch (IOException | ParseException | SQLException e) {
				// TODO Auto-generated catch block
				throw new AdminException(e.getMessage());
			}
		} else {
			return columns;
		}
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
		this.connInfoMapper.saveConnect(conn);
	}

	@Override
	public void saveParam(List<ConnParam> list) throws AdminException, ParseException, SQLException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		ConnInfo connInfo = this.connInfoMapper.loadConnInfo(list.get(0).getConnID());
		this.connInfoMapper.deleteParam(list.get(0).getConnID());
		this.connInfoMapper.saveParam(list);
		this.engineFactory.putConnection(list, connInfo);
	}

	@Override
	public List<ConnInfo> loadConn() {
		// TODO Auto-generated method stub
		return this.connInfoMapper.loadConn();
	}

	@Override
	public List<ConnParam> loadParam(String connID) {
		// TODO Auto-generated method stub
		return this.connInfoMapper.loadParam(connID);
	}

	@Override
	public void saveJoin(List<ConfigJoin> joins) {
		// TODO Auto-generated method stub
		for (ConfigJoin join : joins) {
			if (StringUtils.isBlank(join.getJoinID())) {
				join.setJoinID(java.util.UUID.randomUUID().toString());
			}
			this.configJoinMapper.saveJoin(join);
		}

	}

	@Override
	public boolean deleteJoin(String joinID) {
		// TODO Auto-generated method stub
		this.configJoinMapper.deleteByJoinID(joinID);
		return true;
	}

	@Override
	public List<FacadeJoin> loadJoin() {
		// TODO Auto-generated method stub
		List<ConfigJoin> joins = this.configJoinMapper.loadAllJoin();
		List<FacadeJoin> list = new ArrayList<>();
		for (ConfigJoin join : joins) {
			FacadeJoin fj = new FacadeJoin();
			BeanUtils.copyProperties(join, fj);

			ConfigColumn cc = this.modelService.getColumn(join.getColID1());
			fj.setColName1(cc.getColName());
			cc = this.modelService.getColumn(join.getColID2());
			fj.setColName2(cc.getColName());

			ConfigTable table = this.modelService.getTable(join.getTblID1());
			fj.setTableName1(table.getTableName());
			table = this.modelService.getTable(join.getTblID1());
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
		this.queryModelMapper.saveQueryColumn(queryColumns);

	}

	@Override
	public void deleteModel(String modelID) throws AdminException {
		// TODO Auto-generated method stub
		this.developConfigCheck();
		this.queryModelMapper.deleteModel(modelID);

	}

	@Override
	public void saveQueryModel(String modelName) throws AdminException {
		// TODO Auto-generated method stub

		this.developConfigCheck();
		String modelID = java.util.UUID.randomUUID().toString();
		QueryModel model = new QueryModel();
		model.setModelID(modelID);
		model.setModelName(modelName);
		this.preHandle(model);
		this.queryModelMapper.saveModel(model);
	}

	@Override
	public List<QueryColumn> loadQueryColumn(String modelID) {
		// TODO Auto-generated method stub
		List<QueryColumn> columns = this.queryModelMapper.loadQueryColumn(modelID);
		List<QueryColumn> fcs = new ArrayList<>();

		for (QueryColumn column : columns) {
			FacadeColumn fc = new FacadeColumn();
			BeanUtils.copyProperties(column, fc);
			ConfigColumn cc = this.modelService.getColumn(column.getColID());
			fc.setChinese(cc.getChinese());
			fc.setColName(cc.getColName());
			fcs.add(fc);
		}
		return fcs;
	}

	@Override
	public List<QueryModel> loadQueryModel() {
		// TODO Auto-generated method stub

		FacadeUser user = this.loginUserService.getLoginUser();
		if (user.getRoleType() != UserInfo.SUPERADMIN && user.getRoleType() != UserInfo.DEVELOPER) {
			return this.queryModelMapper.loadQueryModel(user.getUserID());
		} else {
			return this.queryModelMapper.loadAllModel();
		}

	}

	private void developConfigCheck() throws AdminException {
		FacadeUser user = this.loginUserService.getLoginUser();
		if (user.getRoleType() != UserInfo.SUPERADMIN && user.getRoleType() != UserInfo.DEVELOPER) {
			throw new AdminException("没有权限执行操作");
		}
	}

	@Override
	public void deleteConnect(String connID) throws AdminException {
		// TODO Auto-generated method stub
		this.connInfoMapper.deleteConn(connID);
	}

	@Override
	public List<ConfigColumn> loadConfigColumn(List<String> tables) throws AdminException {
		// TODO Auto-generated method stub
		List<ConfigColumn> columns = this.configColumnMapper.loadColumnsByTables(tables);
		return columns;
	}

}
