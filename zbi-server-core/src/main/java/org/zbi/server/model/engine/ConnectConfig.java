package org.zbi.server.model.engine;

public class ConnectConfig {
	
	public ConnectConfig()
	{
		
	}

	public ConnectConfig(boolean required,String defaultVal)
	{
		this.required=required;
		this.defaultVal=defaultVal;
	}
	
	/**
	 * 是否必须
	 * */
	private boolean required;
	
	/**
	 * 默认值
	 * */
	private String defaultVal;
	


	public boolean isRequired() {
		return required;
	}


	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

}
