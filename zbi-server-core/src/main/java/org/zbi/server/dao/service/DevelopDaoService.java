package org.zbi.server.dao.service;

import java.sql.SQLException;
import java.util.List;

import org.zbi.server.entity.mysql.ConnInfo;
import org.zbi.server.entity.mysql.ConnParam;
import org.zbi.server.entity.mysql.QueryColumn;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigJoin;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.facade.FacadeJoin;
import org.zbi.server.model.facade.FacadeTable;

public interface DevelopDaoService {

	/**
	 * 保存连接信息
	 */
	public void saveConnect(ConnInfo conn) throws AdminException;

	/**
	 * 保存连接信息
	 */
	public void deleteConnect(String connID) throws AdminException;

	/**
	 * 加载连接信息
	 */
	public List<ConnInfo> loadConn();

	/**
	 * 保存连接参数
	 */
	public void saveParam(List<ConnParam> list) throws AdminException, SQLException, ParseException;

	/**
	 * 加载连接参数
	 */
	public List<ConnParam> loadParam(String connID);

	/**
	 * 保存连接信息
	 */
	public void saveJoin(List<ConfigJoin> joins);

	/**
	 * 删除连表信息
	 */
	public boolean deleteJoin(String joinID);

	/**
	 * 加载连表信息
	 */
	public List<FacadeJoin> loadJoin();

	/**
	 * 删除模型
	 */
	public void deleteModel(String modelID) throws AdminException;

	/**
	 * 创建模型
	 */
	public void saveQueryModel(String modelName) throws AdminException;
	
	/**
	 * 加载模型
	 */
	public List<QueryModel> loadQueryModel();

	/**
	 * 保存模型字段
	 */
	public void saveQueryColumn(String modelID, List<QueryColumn> queryColumns) throws AdminException;

	/**
	 * 加载模型字段
	 */
	public List<QueryColumn> loadQueryColumn(String modelID);

	/**
	 * 保存配置表字段
	 */
	public List<ConfigColumn> loadConfigColumn(String tableID) throws AdminException;

	
	/**
	 * 保存多配置表字段
	 */
	public List<ConfigColumn> loadConfigColumn(List<String> tables) throws AdminException;

	
	/**
	 * 加载配置表
	 */
	public List<ConfigTable> loadConfigTable(boolean source) throws AdminException;

	/**
	 * 删除配置表
	 */
	public void deleteConfigTable(String tableID) throws AdminException;

	/**
	 * 保存配置表
	 */
	public void saveConfigTable(FacadeTable table) throws AdminException;


}
