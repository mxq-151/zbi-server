package org.zbi.server.model.engine;

import java.sql.Connection;

import org.zbi.server.model.service.DefaultSqlEncoder;

public class MysqlSqlEncoder extends AbstractSqlEngine {

	public MysqlSqlEncoder(Connection connection) {
		super(connection,new DefaultSqlEncoder());
		// TODO Auto-generated constructor stub
	}

}
