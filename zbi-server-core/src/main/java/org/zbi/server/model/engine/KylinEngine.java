package org.zbi.server.model.engine;

import java.io.IOException;
import java.util.List;

import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.parse.ParseModel;
import org.zbi.server.model.response.QueryResultResp;

public class KylinEngine implements IQueryEngine {

	@Override
	public QueryResultResp query(ParseModel model) throws IOException, IOException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConfigColumn> fetchMeta(String tableName, String dbName) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
