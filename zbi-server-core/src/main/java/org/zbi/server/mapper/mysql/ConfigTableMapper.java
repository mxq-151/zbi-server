package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.model.config.ConfigTable;

public interface ConfigTableMapper {

	public void insertTable(ConfigTable table);

	public void deleteByPrimaryKey(String tableID);

	public ConfigTable selectByPrimaryKey(String tableID);
	
	List<ConfigTable> loadTables();

}
