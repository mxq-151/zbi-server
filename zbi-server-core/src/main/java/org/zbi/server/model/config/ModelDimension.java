package org.zbi.server.model.config;

import org.zbi.server.model.core.ColumnBase;

public class ModelDimension extends ColumnBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 强制维度，一定要显示的，保证在去重的情况下数据准确
	 */
	private boolean forceDim = false;

	/**
	 * 是否为高级字段
	 */
	private boolean isAdvanced;

	public boolean isForceDim() {
		return forceDim;
	}

	public void setForceDim(boolean forceDim) {
		this.forceDim = forceDim;
	}

	public boolean isAdvanced() {
		return isAdvanced;
	}

	public void setAdvanced(boolean isAdvanced) {
		this.isAdvanced = isAdvanced;
	}

}
