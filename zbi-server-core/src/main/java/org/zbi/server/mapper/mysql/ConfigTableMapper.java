package org.zbi.server.mapper.mysql;

import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.core.EngineType;

public interface ConfigTableMapper {

	public void insertTable(String tblAlias, String project, String tableName, EngineType engingType, int tableType,
			String tableID);

	public void deleteByPrimaryKey(String tableID);

	public ConfigTable selectByPrimaryKey(String tableID);

}
