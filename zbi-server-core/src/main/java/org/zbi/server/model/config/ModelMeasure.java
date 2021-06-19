package org.zbi.server.model.config;

import org.zbi.server.model.core.AggType;

import com.alibaba.fastjson.annotation.JSONField;

public class ModelMeasure extends ModelDimension{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(serialize = false)
	private AggType aggType;

	public AggType getAggType() {
		return aggType;
	}

	public void setAggType(AggType aggType) {
		this.aggType = aggType;
	}

}
