package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.config.QueryColumn;
import org.zbi.server.model.facade.FacadeTable;
import org.zbi.server.model.response.ModelDescResp;

public interface ConfigDaoService {

	public List<ModelDescResp> getModelDscriptions(String key);

	public void saveTable(FacadeTable table);

	public void deleteTable(String tableID);

	public List<ConfigTable> queryTables();

	public List<ConfigColumn> getConfigColumns(String tableID);

	public List<QueryColumn> getQueryColumns(String modelTag);

	public List<String> getColumnLimits(String colID);

}
