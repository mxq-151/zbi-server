package org.zbi.server.model.engine;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zbi.server.model.response.QueryResultResp;

public class CalciteEngine extends QueryEngine {
	
	private final static Logger logger = LoggerFactory.getLogger(CalciteEngine.class);

	public CalciteEngine(Connection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}

	@Override
	public QueryResultResp query(String sql,int offset,int pageSize) throws SQLException, IOException {
		// TODO Auto-generated method stub

		logger.info("query sql:"+sql);
		ResultSet rs = this.connection.prepareStatement(sql).executeQuery();

		QueryResultResp qr= this.castToResult(rs);
		this.closeConnection(rs);
		return qr;

	}

}
