package org.zbi.server.model.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.model.engine.EngineFactory;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.request.RequestParam;
import org.zbi.server.model.response.QueryResultResp;

@Component
public class DefaultQueryServiceImpl extends AbstractQueryService {

	@Autowired
	private EngineFactory engineFactory;

	@Autowired
	private ConfigDaoService daoService;

	@PostConstruct
	public void mockData() {

		try {
			engineFactory.loadDefaultConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public QueryResultResp query(RequestParam param) throws ParseException, SQLException, QueryException, IOException {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public QueryResultResp search(String keyword, String indexPath, int offset, int pageSize)
			throws ParseException, SQLException, IOException {
		// TODO Auto-generated method stub

		return null;
	}

}
