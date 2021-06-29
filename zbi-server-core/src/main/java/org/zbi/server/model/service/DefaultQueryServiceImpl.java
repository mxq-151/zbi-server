package org.zbi.server.model.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.model.engine.EngineFactory;
import org.zbi.server.model.engine.IQueryEngine;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.parse.ParseModel;
import org.zbi.server.model.request.RequestParam;
import org.zbi.server.model.response.QueryResultResp;

@Component
public class DefaultQueryServiceImpl extends AbstractQueryService {

	@Autowired
	private EngineFactory engineFactory;

	@Autowired
	private RequestDecodeService requestDecodeService;

	@Autowired
	private ModelService modelService;
	
	private final Logger logger = LoggerFactory.getLogger(DefaultQueryServiceImpl.class);

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
		RequestParam newParam = this.requestDecodeService.parseRquest(param);
		ParseModel model = this.modelService.getModel(newParam);
		
		IQueryEngine queryEngine = this.engineFactory.getQueryEngine(model.getConnID(), model.getEngineType());
		long start = System.currentTimeMillis();
		QueryResultResp resp = queryEngine.query(model);
		long end = System.currentTimeMillis();
		resp.setDuration(end - start);
		return resp;

	}

	@Override
	public QueryResultResp search(String keyword, String indexPath, int offset, int pageSize)
			throws ParseException, SQLException, IOException {
		// TODO Auto-generated method stub

		return null;
	}

}
