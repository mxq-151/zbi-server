package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.model.config.ConfigColumn;

public interface ConfigColumnMapper {

	public List<ConfigColumn> loadColumnsByTableID(String tableID);
	
	public void deleteByTablelId(String tableID);
	
	public void saveColumn(List<ConfigColumn> list);
}
