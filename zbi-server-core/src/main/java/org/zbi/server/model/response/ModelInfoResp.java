package org.zbi.server.model.response;

import java.util.List;

import org.zbi.server.model.config.ConfigColumn;

public class ModelInfoResp {
	
	/**
	 * 维度
	 * */
	private List<ConfigColumn> dimensions;

	/**
	 * 度量
	 */
	private List<ConfigColumn> measures;

	public List<ConfigColumn> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<ConfigColumn> dimensions) {
		this.dimensions = dimensions;
	}

	public List<ConfigColumn> getMeasures() {
		return measures;
	}

	public void setMeasures(List<ConfigColumn> measures) {
		this.measures = measures;
	}

	

}
