package org.zbi.server.model.engine;

import java.io.IOException;
import java.util.List;

import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.parse.ParseModel;
import org.zbi.server.model.response.QueryResultResp;

/**
 * 所有查询引擎接口
 */
public interface IQueryEngine {

	public abstract QueryResultResp query(ParseModel model) throws IOException, IOException, ParseException;

	public List<ConfigColumn> fetchMeta(String tableName, String dbName) throws IOException;
}
