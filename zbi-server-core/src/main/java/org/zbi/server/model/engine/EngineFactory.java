package org.zbi.server.model.engine;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zbi.server.model.core.EngineType;
import org.zbi.server.model.decoder.DefaultRequestDecoder;
import org.zbi.server.model.decoder.IRequestDecoder;
import org.zbi.server.model.encoder.DefaultSqlEncoder;
import org.zbi.server.model.encoder.ISqlEncoder;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.ParseException.ExceptionType;

@Component
public class EngineFactory {

	/**
	 * 连接池
	 */
	private ConnectionFactory connectionFactory=new ConnectionFactory();

	@Value("${calcite.model.path:#{null}}")
	private String modelPath;
	/**
	 * 
	 * */
	private Map<String,String> urls=new HashMap<>();
	
	public void addConConfig(EngineConnection config) throws SQLException {
		this.connectionFactory.initConnnection(config);
	}

	public void loadDefaultConnection() throws SQLException {
		EngineConnection connection = new EngineConnection();

		Map<String, ConnectConfig> configMap = new HashMap<>();

		URL path=this.getClass().getResource(this.modelPath);
		configMap.put("datasource.driver-class-name", new ConnectConfig(true, "org.apache.calcite.jdbc.Driver"));
		configMap.put("datasource.url",
				new ConnectConfig(true, "jdbc:calcite:caseSensitive=false;model=" + path.getPath()));

		configMap.put("datasource.initial.size", new ConnectConfig(true, "5"));

		configMap.put("datasource.min.idle", new ConnectConfig(true, "1"));

		configMap.put("datasource.max.active", new ConnectConfig(true, "10"));

		configMap.put("datasource.username", new ConnectConfig(true, ""));

		configMap.put("datasource.password", new ConnectConfig(true, ""));

		connection.setConfigMap(configMap);
		connection.setConnName("calcite-1");
		connection.setEngineType(EngineType.CALCITE);
		addConConfig(connection);
	}

	public IQueryEngine getQueryEngine(String conName, EngineType engineType) throws ParseException, SQLException {

		Connection connection = this.connectionFactory.getConnection(conName);

		switch (engineType) {
		case CALCITE:

			if (connection == null) {
				throw new ParseException("找不到链接:" + conName, ExceptionType.CONFIGERROR);
			}

			return new CalciteEngine(connection);

		case KYLIN:
			String url=this.urls.get(conName);
			if(url==null)
			{
				throw new ParseException("找不到链接:" + conName, ExceptionType.CONFIGERROR);
			}
			return new KylinEngine();
		default:
			break;

		}

		throw new ParseException("没有引擎:" + engineType, ExceptionType.CONFIGERROR);
	}

	public ISqlEncoder<?> getSqlEncoder(EngineType engineType) throws ParseException {
		switch (engineType) {
		case CALCITE:
			return new DefaultSqlEncoder();
		default:
			break;

		}

		throw new ParseException("没有引擎SQL编码器:" + engineType, ExceptionType.CONFIGERROR);
	}

	public IRequestDecoder<?> getParamDecoder(EngineType engineType) throws ParseException {
		switch (engineType) {
		case CALCITE:
			return new DefaultRequestDecoder();
		default:
			break;

		}

		throw new ParseException("没有引擎:" + engineType, ExceptionType.CONFIGERROR);
	}

}
