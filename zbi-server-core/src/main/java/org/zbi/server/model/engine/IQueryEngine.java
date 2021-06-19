package org.zbi.server.model.engine;

import java.io.IOException;
import java.sql.SQLException;

import org.zbi.server.model.response.QueryResultResp;

/**
 * 所有查询引擎接口
 * */
public interface IQueryEngine {

	public abstract QueryResultResp query(String sql,int offset,int pageSize) throws SQLException, IOException;
}
