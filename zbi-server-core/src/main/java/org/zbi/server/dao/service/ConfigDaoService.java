package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.entity.mysql.GroupColLimit;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.model.response.ModelInfoResp;

public interface ConfigDaoService {

	/**
	 * 保存用户组字段权限
	 * */
	public List<GroupColLimit> loadGroupColLimit(String groupID, String modelID);
	
	/**
	 * 获取用户字段权限
	 * */
	public List<String> loadColLimit(String colID);

	/**
	 * 保存用户组字段权限
	 * */
	public void saveGroupColLimit(List<String> cols, String groupID, String modelID);

	/**
	 * 保存用户组数据权限
	 * */
	public void saveGroupDataLimit(List<String> words, String groupID, String colID);

	/**
	 * 获取用户组数据权限
	 * */
	public List<String> loadGroupDataLimit(String groupID, String colID);

	/**
	 * 获取用户数据权限
	 * */
	public List<String> loadDataLimit(String colID);
	
	/**
	 * 获取用户模型列表
	 * */
	public List<QueryModel> loadModel(String key);
	
	/**
	 * 获取用户用户模型列表
	 * */
	public List<QueryModel> loadGroupModel(String groupID);
	
	/**
	 * 保存用户组的模型权限
	 * */
	public void saveGroupModel(String groupID,String modelID);

	/**
	 * 获取模型详情
	 * */
	public ModelInfoResp loadModelInfo(String modelID);

}
