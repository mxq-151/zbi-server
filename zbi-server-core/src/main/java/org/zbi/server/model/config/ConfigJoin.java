package org.zbi.server.model.config;

import org.zbi.server.entity.mysql.EntityBase;

public class ConfigJoin extends EntityBase{
	
	private String joinID;

	private String tblID1;

	private String colID1;

	private String tblID2;

	private String colID2;

	public String getTblID1() {
		return tblID1;
	}

	public void setTblID1(String tblID1) {
		this.tblID1 = tblID1;
	}

	public String getColID1() {
		return colID1;
	}

	public void setColID1(String colID1) {
		this.colID1 = colID1;
	}

	public String getTblID2() {
		return tblID2;
	}

	public void setTblID2(String tblID2) {
		this.tblID2 = tblID2;
	}

	public String getColID2() {
		return colID2;
	}

	public void setColID2(String colID2) {
		this.colID2 = colID2;
	}

	public String getJoinID() {
		return joinID;
	}

	public void setJoinID(String joinID) {
		this.joinID = joinID;
	}


}
