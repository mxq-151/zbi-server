package org.zbi.server.model.engine;

import java.sql.Connection;
import java.sql.SQLException;

import org.zbi.server.model.response.QueryResultResp;

public class MysqlEngine extends QueryEngine {

	public MysqlEngine(Connection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}

	@Override
	public QueryResultResp query(String sql, int offset, int pageSize) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
