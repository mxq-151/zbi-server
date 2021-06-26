package org.zbi.server.dao.mysql;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.mapper.mysql.ConfigColumnMapper;
import org.zbi.server.mapper.mysql.ConfigTableMapper;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.config.QueryColumn;
import org.zbi.server.model.facade.FacadeTable;
import org.zbi.server.model.response.ModelDescResp;

@Component
public class MysqlConfigDaoService implements ConfigDaoService {

	@Autowired
	private ConfigTableMapper configTableMapper;

	@Autowired
	private ConfigColumnMapper configColumnMapper;

	@Override
	public List<ModelDescResp> getModelDscriptions(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveTable(FacadeTable table) {
		// TODO Auto-generated method stub
		if (table.getTableID() != null) {
			this.configTableMapper.deleteByPrimaryKey(table.getTableID());
			this.configColumnMapper.deleteByTablelId(table.getTableID());
		}
		ConfigTable ct = new ConfigTable();
		BeanUtils.copyProperties(table, ct);
		this.configTableMapper.insertTable(ct);
		this.configColumnMapper.batchInsert(table.getColumns());

	}

	@Override
	public void deleteTable(String tableID) {
		// TODO Auto-generated method stub
		this.configTableMapper.deleteByPrimaryKey(tableID);
		this.configColumnMapper.deleteByTablelId(tableID);
	}

	@Override
	public List<ConfigTable> queryTables() {
		// TODO Auto-generated method stub
		return this.configTableMapper.loadTables();
	}

	@Override
	public List<ConfigColumn> getConfigColumns(String tableID) {
		// TODO Auto-generated method stub
		return this.configColumnMapper.listColumnsByTableID(tableID);
	}

	@Override
	public List<QueryColumn> getQueryColumns(String modelTag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getColumnLimits(String colID) {
		// TODO Auto-generated method stub
		return null;
	}

}
