package org.zbi.server.model.parse;

import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.core.SortType;

public class ParseColumn extends ConfigColumn {
	
	/**
	 * 排序顺序
	 */
	private int sortOrder;

	/**
	 * 排序类型
	 */
	private SortType sortType;

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
