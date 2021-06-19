package org.zbi.server.model.config;

import org.zbi.server.model.core.AggType;
import org.zbi.server.model.core.ColumnBase;

public class ConfigColumn extends ColumnBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tableID;

	/**
	 * 强制维度，一定要显示的，保证在去重的情况下数据准确
	 */
	private boolean forceDim = false;

	private AggType aggType;

	public String getTableID() {
		return tableID;
	}

	public void setTableID(String tableID) {
		this.tableID = tableID;
	}

	public boolean isForceDim() {
		return forceDim;
	}

	public void setForceDim(boolean forceDim) {
		this.forceDim = forceDim;
	}

	public AggType getAggType() {
		return aggType;
	}

	public void setAggType(AggType aggType) {
		this.aggType = aggType;
	}

}
