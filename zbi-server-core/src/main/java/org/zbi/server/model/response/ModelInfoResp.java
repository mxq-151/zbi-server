package org.zbi.server.model.response;

import java.util.List;

public class ModelInfoResp {
	
	
	private List<ColumnInfoResp> dimensions;

	/**
	 * 度量
	 */
	private List<ColumnInfoResp> measures;

	public List<ColumnInfoResp> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<ColumnInfoResp> dimensions) {
		this.dimensions = dimensions;
	}

	public List<ColumnInfoResp> getMeasures() {
		return measures;
	}

	public void setMeasures(List<ColumnInfoResp> measures) {
		this.measures = measures;
	}

	

}
