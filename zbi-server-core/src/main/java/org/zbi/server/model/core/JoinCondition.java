package org.zbi.server.model.core;

public class JoinCondition {

	/**
	 * 事实表：数据库名.表名
	 * */
    private String factTable;

    /**
     * 维度表：数据库名.表名
     * */
    private String lookUpTable;

    /**
     *连表方式
     * */
    private JoinType joinType;

    /**
     * 事实连表条件: 库名.表名.字段名
     * */
    private String [] factTableCols;

    /**
     * 连表条件: 库名.表名.字段名
     * */
    private String [] lookUpTableCols;

	public String getFactTable() {
		return factTable;
	}

	public void setFactTable(String factTable) {
		this.factTable = factTable;
	}

	public String getLookUpTable() {
		return lookUpTable;
	}

	public void setLookUpTable(String lookUpTable) {
		this.lookUpTable = lookUpTable;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}

	public String[] getFactTableCols() {
		return factTableCols;
	}

	public void setFactTableCols(String[] factTableCols) {
		this.factTableCols = factTableCols;
	}

	public String[] getLookUpTableCols() {
		return lookUpTableCols;
	}

	public void setLookUpTableCols(String[] lookUpTableCols) {
		this.lookUpTableCols = lookUpTableCols;
	}

}
