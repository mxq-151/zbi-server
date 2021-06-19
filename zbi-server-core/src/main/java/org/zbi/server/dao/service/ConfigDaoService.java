package org.zbi.server.dao.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.config.ModelConfig;
import org.zbi.server.model.facade.FacadeTable;
import org.zbi.server.model.response.ModelDescResp;

@Component
public interface ConfigDaoService<T> {

	public ModelConfig getQuerySqlModel(T modelTag);

	public List<ModelDescResp> getModelDscriptions(String key);

	public void saveTable(FacadeTable table);

	public void deleteTable(String tableID);
	
	public List<ConfigTable> queryTables();
	
	public List<ConfigColumn> getColumns(String tableID);

}
