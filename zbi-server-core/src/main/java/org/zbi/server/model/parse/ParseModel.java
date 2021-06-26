package org.zbi.server.model.parse;

import java.util.List;

import org.zbi.server.model.config.ConfigJoin;
import org.zbi.server.model.config.ConfigTable;

public class ParseModel {

	private List<ParseColumn> dimensions;

	private List<ParseColumn> measures;

	private List<ConfigJoin> joins;

	private ConfigTable mainTable;

	private List<ParseFilter> filters;

	/***
	 * 起始页
	 */
	private int offSet;

	/**
	 * 页大小
	 */
	private int pageSize;

	public List<ParseColumn> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<ParseColumn> dimensions) {
		this.dimensions = dimensions;
	}

	public List<ParseColumn> getMeasures() {
		return measures;
	}

	public void setMeasures(List<ParseColumn> measures) {
		this.measures = measures;
	}

	public List<ConfigJoin> getJoins() {
		return joins;
	}

	public void setJoins(List<ConfigJoin> joins) {
		this.joins = joins;
	}

	public ConfigTable getMainTable() {
		return mainTable;
	}

	public void setMainTable(ConfigTable mainTable) {
		this.mainTable = mainTable;
	}

	public List<ParseFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<ParseFilter> filters) {
		this.filters = filters;
	}

	public int getOffSet() {
		return offSet;
	}

	public void setOffSet(int offSet) {
		this.offSet = offSet;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
