package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.QueryColumn;
import org.zbi.server.entity.mysql.QueryModel;

public interface QueryModelMapper {

	public void createModel(QueryModel model);

	public List<QueryModel> listQueryModel(String userID);
	
	public List<QueryModel> listQueryModelByGroup(String groupID);

	public List<QueryModel> listAllModel();

	public void deleteModel(String modelTag);

	public List<QueryColumn> listQueryColumn(String modelTag);

	public void batchInsert(List<QueryColumn> queryColumns);

	public void deleteQueryColumn(String modelTag);

}
