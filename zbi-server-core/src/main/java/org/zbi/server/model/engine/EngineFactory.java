package org.zbi.server.model.engine;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.zbi.server.entity.mysql.ConnInfo;
import org.zbi.server.entity.mysql.ConnParam;
import org.zbi.server.model.core.EngineType;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.ParseException.ExceptionType;

@Component
public class EngineFactory {

	/**
	 * 连接池
	 */
	private ConnectionFactory connectionFactory = new ConnectionFactory();

	private Map<String, ConnInfo> connections = new HashMap<>();

	private Map<String, List<ConnParam>> params = new HashMap<>();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void putConnection(List<ConnParam> params, ConnInfo connInfo) throws SQLException, ParseException {

		EngineType engine = connInfo.getEngineType();
		switch (engine) {
		case CALCITE:
		case MYSQL:
		case DORIS:
		case KUDU:
			for (String param : ConnectionFactory.array) {
				boolean find = false;
				for (ConnParam p : params) {
					if (p.getParamKey().equals(param)) {
						find = true;
						break;
					}
				}
				if (!find) {
					throw new ParseException("can not find param for:" + param, ExceptionType.CONFIGERROR);
				}
			}
		default:
			break;
		}

		logger.info("init connection for {}", connInfo.getConnName());
		this.connections.put(connInfo.getConnID(), connInfo);
		this.params.put(connInfo.getConnID(), params);
		connectionFactory.initConnnection(params, connInfo.getUrl(), connInfo.getConnID());
	}

	public IQueryEngine getQueryEngine(String connID) throws ParseException, SQLException {

		ConnInfo connInfo = this.connections.get(connID);
		switch (connInfo.getEngineType()) {
		case CALCITE:
		case MYSQL:
		case DORIS:
		case KUDU:
			Connection conn = this.connectionFactory.getConnection(connID);
			return new DefaultSqlEngine(conn);
		default:
			throw new ParseException("can not find connection for this engine", ExceptionType.CONFIGERROR);
		}

	}

}
