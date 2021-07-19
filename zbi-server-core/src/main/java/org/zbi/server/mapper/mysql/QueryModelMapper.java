package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.QueryColumn;
import org.zbi.server.entity.mysql.QueryModel;

public interface QueryModelMapper {

	public void saveModel(QueryModel model);

	public List<QueryModel> loadQueryModel(String userID);
	
	public List<QueryModel> loadQueryModelByGroup(String groupID);

	public List<QueryModel> listAllModel();

	public void deleteModel(String modelTag);

	public List<QueryColumn> loadQueryColumn(String modelTag);

	public void saveQueryColumn(List<QueryColumn> queryColumns);

	public void deleteQueryColumn(String modelTag);

}
