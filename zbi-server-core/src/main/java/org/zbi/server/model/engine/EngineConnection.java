package org.zbi.server.model.engine;

import java.util.Map;

import org.zbi.server.model.core.EngineType;

public class EngineConnection {

	/**
	 * 引擎类型
	 */
	EngineType engineType;

	/**
	 * 配置demo
	 */
	private String configDemo;

	/**
	 * 链接名称
	 */
	private String connName;

	/**
	 * 参数据信息
	 */
	private Map<String, ConnectConfig> configMap;

	public EngineType getEngineType() {
		return engineType;
	}

	public void setEngineType(EngineType engineType) {
		this.engineType = engineType;
	}

	public String getConfigDemo() {
		return configDemo;
	}

	public void setConfigDemo(String configDemo) {
		this.configDemo = configDemo;
	}

	public String getConnName() {
		return connName;
	}

	public void setConnName(String connName) {
		this.connName = connName;
	}

	public Map<String, ConnectConfig> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, ConnectConfig> configMap) {
		this.configMap = configMap;
	}

}
