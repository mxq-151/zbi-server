package org.zbi.server.model.parse;

import org.zbi.server.model.core.AggType;

public class ParseMeasure extends ParseDimension {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AggType aggType;

	public AggType getAggType() {
		return aggType;
	}

	public void setAggType(AggType aggType) {
		this.aggType = aggType;
	}

}
