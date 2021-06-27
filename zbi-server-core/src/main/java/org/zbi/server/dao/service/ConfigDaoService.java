package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.entity.mysql.GroupColLimit;
import org.zbi.server.entity.mysql.UserColLimit;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.facade.FacadeTable;
import org.zbi.server.model.response.ModelDescResp;

public interface ConfigDaoService {

	public List<ModelDescResp> getModelDscriptions(String key);

	public void saveConfigTable(FacadeTable table);

	public void deleteConfigTable(String tableID);

	public List<ConfigTable> queryConfigTables();

	public List<ConfigColumn> getConfigColumns(String tableID);

	public List<String> queryUserTotalColLimit(String modelTag);

	public List<GroupColLimit> queryGroupColLimit(String groupID, String modelTag);

	public List<UserColLimit> queryUserColLimit(String userID, String modelTag);

	public List<String> getDataLimit(String colID);

	public void insertUserColLimit(List<String> cols,String userID,String modelTag);

	public void insertGroupColLimit(List<String> cols,String groupID,String modelTag);

	public void insertUserDataLimit(List<String> words,String userID,String colID);

	public List<String> queryUserDataLimit(String userID, String colID);

	public void insertGroupDataLimit(List<String> words,String groupID,String colID);

	public List<String> queryGroupDataLimit(String groupID, String colID);

	public List<String> queryUserTotalDataLimit(String userID, String colID);

}
