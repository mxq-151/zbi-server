package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.model.config.ConfigColumn;

public interface ConfigColumnMapper {

	public List<ConfigColumn> listColumnsByTableID(String tableID);
	
	public void deleteByTablelId(String tableID);

	public void insertColumn(ConfigColumn column);
}
