package org.zbi.server.entity.mysql;

import java.sql.Timestamp;

public class EntityBase {
	
	private Timestamp lastUpdate;
	
	private String lastUpdater;

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdater() {
		return lastUpdater;
	}

	public void setLastUpdater(String lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

}
