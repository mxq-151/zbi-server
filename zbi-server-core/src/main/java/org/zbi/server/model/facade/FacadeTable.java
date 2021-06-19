package org.zbi.server.model.facade;

import java.util.List;

import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigTable;

public class FacadeTable extends ConfigTable{
	
	private List<ConfigColumn> columns;

	public List<ConfigColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<ConfigColumn> columns) {
		this.columns = columns;
	}

}
