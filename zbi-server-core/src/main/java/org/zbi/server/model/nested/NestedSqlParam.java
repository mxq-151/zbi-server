package org.zbi.server.model.nested;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NestedSqlParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 所有表
	 */
	private List<NestedTable> nestedTables;

	/**
	 * 头表
	 */
	private String topTable;

	/**
	 * 连表信息，表别名必须是全局唯一（表别名:连表信息）
	 */
	private Map<String, NestedMainTable> nestedMainTable;

	public void addNestedMainTable(String tableName, NestedMainTable nestedMainTable) {
		if (this.nestedMainTable == null) {
			this.nestedMainTable = new HashMap<>();
		}

		this.nestedMainTable.put(tableName, nestedMainTable);

	}

	public void addNestedTable(NestedTable table) {
		if (this.nestedTables == null) {
			this.nestedTables = new ArrayList<>();
		}

		this.nestedTables.add(table);
	}

	public List<NestedTable> getNestedTables() {
		return nestedTables;
	}

	public void setNestedTables(List<NestedTable> nestedTables) {
		this.nestedTables = nestedTables;
	}

	public String getTopTable() {
		return topTable;
	}

	public void setTopTable(String topTable) {
		this.topTable = topTable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Map<String, NestedMainTable> getNestedMainTable() {
		return nestedMainTable;
	}

	public void setNestedMainTable(Map<String, NestedMainTable> nestedMainTable) {
		this.nestedMainTable = nestedMainTable;
	}

}
