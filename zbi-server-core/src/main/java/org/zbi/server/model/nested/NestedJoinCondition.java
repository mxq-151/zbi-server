package org.zbi.server.model.nested;

public class NestedJoinCondition {
	
	
	public NestedJoinCondition(String firstTable,String secondTable,String firstColumn,String secondColumn)
	{
		this(firstTable,secondTable,firstColumn,secondColumn,false);
	}
	
	public NestedJoinCondition(String firstTable,String secondTable,String firstColumn,String secondColumn,boolean dynamic)
	{
		this.firstTableColumn=firstColumn;
		this.secondTableColumn=secondColumn;
		this.firstTableName=firstTable;
		this.secondTableName=secondTable;
		this.dynamic=dynamic;
	}
	
	public NestedJoinCondition()
	{
		
	}
	
	/**
	 * 第一表别名
	 * */
	private String firstTableName;
	
	/**
	 * 第二表别名
	 * */
	private String secondTableName;
	
	/**
	 * 左表连表信息，规范：字段别名
	 * */
	private String firstTableColumn;
	
	/**
	 * 右表连表信息，规范：字段别名
	 * */
	private String secondTableColumn;
	
	/**
	 * 是否动态连表
	 * */
	private boolean dynamic=false;



	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public String getFirstTableName() {
		return firstTableName;
	}

	public String getSecondTableName() {
		return secondTableName;
	}

	public void setFirstTableName(String firstTableName) {
		this.firstTableName = firstTableName;
	}

	public void setSecondTableName(String secondTableName) {
		this.secondTableName = secondTableName;
	}

	public String getFirstTableColumn() {
		return firstTableColumn;
	}

	public String getSecondTableColumn() {
		return secondTableColumn;
	}

	public boolean isDynamic() {
		return dynamic;
	}

	public void setFirstTableColumn(String firstTableColumn) {
		this.firstTableColumn = firstTableColumn;
	}

	public void setSecondTableColumn(String secondTableColumn) {
		this.secondTableColumn = secondTableColumn;
	}

	

}
