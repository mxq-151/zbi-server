package org.zbi.server.model.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.memory.MemoryConfigDao;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.model.config.ModelConfig;
import org.zbi.server.model.core.EngineType;
import org.zbi.server.model.core.StringUtils;
import org.zbi.server.model.decoder.IRequestDecoder;
import org.zbi.server.model.encoder.ISqlEncoder;
import org.zbi.server.model.engine.EngineFactory;
import org.zbi.server.model.engine.IQueryEngine;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.parse.ParseSqlParam;
import org.zbi.server.model.request.RequestParam;
import org.zbi.server.model.response.QueryResultResp;

@Component
public class DefaultQueryServiceImpl extends  AbstractQueryService {

	@Autowired
	private EngineFactory engineFactory;

	@Autowired
	private ConfigDaoService<String> daoService;

	@PostConstruct
	public void mockData() {
		if (daoService instanceof MemoryConfigDao) {
			MemoryConfigDao memoryConfigDao = (MemoryConfigDao) (daoService);
			memoryConfigDao.mockModelConfigs();
		}

		try {
			engineFactory.loadDefaultConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResultResp query(RequestParam param) throws ParseException, SQLException, QueryException, IOException {
		// TODO Auto-generated method stub

		ModelConfig modelConfig = this.daoService.getQuerySqlModel(param.getModelTag());
		if (modelConfig == null) {
			throw new QueryException("无法根据模型标识：" + param.getModelTag() + " 找到模型信息");
		}
		IRequestDecoder<ParseSqlParam> decoder = (IRequestDecoder<ParseSqlParam>) engineFactory
				.getParamDecoder(modelConfig.getEngineType());
		ParseSqlParam parseSqlParam = new ParseSqlParam();
		parseSqlParam = decoder.parseToConfig(param, modelConfig, parseSqlParam);

		ISqlEncoder<ParseSqlParam> sqlEncoder = (ISqlEncoder<ParseSqlParam>) engineFactory
				.getSqlEncoder(modelConfig.getEngineType());
		String sql = sqlEncoder.encodeSql(parseSqlParam);
		IQueryEngine queryEngine = engineFactory.getQueryEngine(modelConfig.getConnName(), modelConfig.getEngineType());
		QueryResultResp qr = queryEngine.query(sql, parseSqlParam.getOffSet(), parseSqlParam.getPageSize());

		encodeChinese(parseSqlParam, qr);
		return qr;
	}



	@Override
	public QueryResultResp search(String keyword,String indexPath, int offset, int pageSize) throws ParseException, SQLException, IOException {
		// TODO Auto-generated method stub
		
		String table=StringUtils.getTableByWhole(indexPath);
		String column=StringUtils.getCol(indexPath);
		
		StringBuffer sb=new StringBuffer();
		sb.append("select ");
		sb.append(column);
		sb.append(" from ");
		sb.append(table);
		if(keyword!=null && keyword.trim().length()>0)
		{
			sb.append(" where ");
			sb.append(column);
			sb.append(" like '");
			sb.append(keyword);
			sb.append("%'");
		}

		sb.append(" group by ");
		sb.append(column);
		
		IQueryEngine queryEngine = engineFactory.getQueryEngine("calcite-1", EngineType.CALCITE);
		
		return queryEngine.query(sb.toString(), offset, pageSize);
		
	}

	

}
