package org.zbi.server.model.facade;

import org.zbi.server.entity.mysql.QueryColumn;

public class FacadeColumn extends QueryColumn {
	
	private String colName;
	
	private String chinese;

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

}
