package org.zbi.server.model.response;

import java.util.List;

public class QueryResultResp {

	/**
	 * 行头
	 * */
	protected List<ColumnMetaResp> columnMetas;
	
	/**
	 * 内容
	 * */
	protected List<List<String>> results;
	
	/**
	 * 引擎端耗时
	 * */
	protected long duration;
	
	/**
	 * 查询记录数
	 * */
	private int totalSize;

	public List<ColumnMetaResp> getColumnMetas() {
		return columnMetas;
	}

	public List<List<String>> getResults() {
		return results;
	}

	public void setColumnMetas(List<ColumnMetaResp> columnMetas) {
		this.columnMetas = columnMetas;
	}

	public void setResults(List<List<String>> results) {
		this.results = results;
	}

	public long getDuration() {
		return duration;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

}
