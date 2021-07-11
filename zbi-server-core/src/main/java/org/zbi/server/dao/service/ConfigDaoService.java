package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.entity.mysql.ConnInfo;
import org.zbi.server.entity.mysql.ConnParam;
import org.zbi.server.entity.mysql.GroupColLimit;
import org.zbi.server.entity.mysql.QueryColumn;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.entity.mysql.UserColLimit;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeTable;
import org.zbi.server.model.response.ModelInfoResp;

public interface ConfigDaoService {

	public List<QueryModel> getModelDscriptions(String key);

	public void saveConfigTable(FacadeTable table) throws AdminException;

	public void deleteConfigTable(String tableID) throws AdminException;

	public List<ConfigTable> queryConfigTables() throws AdminException;

	public List<ConfigColumn> getConfigColumns(String tableID) throws AdminException;

	public List<String> queryUserTotalColLimit(String modelID);

	public List<GroupColLimit> queryGroupColLimit(String groupID, String modelID);

	public List<UserColLimit> queryUserColLimit(String userID, String modelID);

	public List<String> getDataLimit(String colID);

	public void insertUserColLimit(List<String> cols, String userID, String modelID);

	public void insertGroupColLimit(List<String> cols, String groupID, String modelID);

	public void insertUserDataLimit(List<String> words, String userID, String colID);

	public List<String> queryUserDataLimit(String userID, String colID);

	public void insertGroupDataLimit(List<String> words, String groupID, String colID);

	public List<String> queryGroupDataLimit(String groupID, String colID);

	public List<String> queryUserTotalDataLimit(String userID, String colID);

	public void saveModel(String modelID, String modelName, List<QueryColumn> queryColumn) throws AdminException;

	public List<QueryColumn> listQueryColumn(String modelID);

	public List<QueryModel> listQueryModel();

	public ModelInfoResp getModelInfo(String modelID);

	List<QueryModel> listQueryModelByGroup(String groupID);

	public void saveConnect(ConnInfo conn) throws AdminException;

	public void inserParam(List<ConnParam> list)throws AdminException;

	public List<ConnInfo> loadConn();
	
	public List<ConnParam> getParams(String connID);

}
