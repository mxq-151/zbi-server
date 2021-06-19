package org.zbi.server.model.nested;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NestedColumn implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 字段名
	 * */
	private String column;
	
	/**
	 * 是否为度量
	 * */
	private boolean isMeasure;
	
	/**
	 * 别名
	 * */
	private String alias;
	
	/**
	 * 涉及到的字段 规范：表别名.列别名
	 * */
	private List<String> involeColumns;
	
	
	/**
	 * 对外映射的字段
	 * */
	private String outerColumn;
	
	

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public boolean isMeasure() {
		return isMeasure;
	}

	public String getOuterColumn() {
		return outerColumn;
	}

	public void setMeasure(boolean isMeasure) {
		this.isMeasure = isMeasure;
	}

	public void setOuterColumn(String outerColumn) {
		this.outerColumn = outerColumn;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<String> getInvoleColumns() {
		return involeColumns;
	}

	public void setInvoleColumns(List<String> involeColumns) {
		this.involeColumns = involeColumns;
	}
	
	public void addInvoleSelect(String select)
	{
		if(this.involeColumns==null)
		{
			this.involeColumns=new ArrayList<>();
		}
		
		this.involeColumns.add(select);
	}

	

}
