package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.model.config.QueryColumn;

public interface QueryModelMapper {

	public List<QueryColumn> queryQueryColumns(String userID, String modelTag);
	
	public List<QueryColumn> queryQueryColumnsByGroup(String groupID, String modelTag);

	public void insertQueryColumns(List<QueryColumn> queryColumns);

	public void deleteQueryColumns(String groupID, String modelTag);

}
