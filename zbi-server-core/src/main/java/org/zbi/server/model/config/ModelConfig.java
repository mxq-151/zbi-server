package org.zbi.server.model.config;

import org.zbi.server.model.core.EngineType;

public class ModelConfig {
	
	
	/**
	 * 模型ID
	 * */
	private String modelID;
	
	
	/**
	 * 使用的链接的名称
	 * */
	private String connName;
	
	
	/**
	 * 模型标识
	 * */
	private String modelTag;
	
	/**
	 * 模型名称
	 * */
	private String modelName;
	
	/**
	 * 引擎类型
	 * */
	private EngineType engineType;
	
	/**
	 * sql配置
	 * */
	private QuerySqlModel querySqlModel;


	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelID() {
		return modelID;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

	public String getModelTag() {
		return modelTag;
	}

	public void setModelTag(String modelTag) {
		this.modelTag = modelTag;
	}

	public QuerySqlModel getQuerySqlModel() {
		return querySqlModel;
	}

	public void setQuerySqlModel(QuerySqlModel querySqlModel) {
		this.querySqlModel = querySqlModel;
	}

	public String getConnName() {
		return connName;
	}

	public void setConnName(String connName) {
		this.connName = connName;
	}

	public EngineType getEngineType() {
		return engineType;
	}

	public void setEngineType(EngineType engineType) {
		this.engineType = engineType;
	}

}
