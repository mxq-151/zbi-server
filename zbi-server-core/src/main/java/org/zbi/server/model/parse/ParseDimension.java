package org.zbi.server.model.parse;

import org.zbi.server.model.config.ModelDimension;
import org.zbi.server.model.core.SortType;

public class ParseDimension extends ModelDimension {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 所属表
	 * */ 
	private String fromTable;

	/**
	 * 排序顺序
	 */
	private int sortOrder;

	/**
	 * 排序类型
	 */
	private SortType sortType;

	public String getFromTable() {
		return fromTable;
	}

	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public SortType getSortType() {
		return sortType;
	}

	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}

}
