package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.model.config.ConfigTable;

public interface ConfigTableMapper {

	public void saveTable(ConfigTable table);

	public void deleteByID(String tableID);

	public ConfigTable selectByID(String tableID);
	
	public List<ConfigTable> loadTable();

}
