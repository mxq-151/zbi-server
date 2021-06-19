
package org.zbi.server.model.engine;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.PooledConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 连接池
 */
public class ConnectionFactory {
	private final static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
	private Map<String, PooledConnection> pools = new HashMap<>();
	private Map<String, DruidDataSource> dataSourceMap = new HashMap<>();

	public void initConnnection(EngineConnection connection) throws SQLException {
		Map<String, String> maps = new HashMap<>(6);
		Map<String, ConnectConfig> connectionMap = connection.getConfigMap();

		maps.put("datasource.driver-class-name", connectionMap.get("datasource.driver-class-name").getDefaultVal());

		maps.put("datasource.url", connectionMap.get("datasource.url").getDefaultVal());

		maps.put("datasource.initial.size", connectionMap.get("datasource.initial.size").getDefaultVal());

		maps.put("datasource.min.idle", connectionMap.get("datasource.min.idle").getDefaultVal());

		maps.put("datasource.max.active", connectionMap.get("datasource.max.active").getDefaultVal());

		maps.put("datasource.username", connectionMap.get("datasource.username").getDefaultVal());

		maps.put("datasource.password", connectionMap.get("datasource.password").getDefaultVal());
		
		DruidDataSource dataSource = createDataSource(maps);
		PooledConnection pooledConnection = dataSource.getPooledConnection();
		dataSourceMap.put(connection.getConnName(), dataSource);
		pools.put(connection.getConnName(), pooledConnection);

	}



	public DruidDataSource createDataSource(Map<String, String> info) throws SQLException {
		logger.info("try to connect to server:" + info.get("datasource.url"));
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(info.get("datasource.driver-class-name"));
		dataSource.setUsername(info.get("datasource.username"));
		dataSource.setPassword(info.get("datasource.password"));
		dataSource.setUrl(info.get("datasource.url"));
		dataSource.setInitialSize(Integer.parseInt(info.get("datasource.initial.size")));
		dataSource.setMinIdle(Integer.parseInt(info.get("datasource.min.idle")));
		dataSource.setMaxActive(Integer.parseInt(info.get("datasource.max.active")));
		/**
		 * 设置断开重连机制
		 */
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setValidationQuery("select 1");
		dataSource.init();
		// 启用监控统计功能 dataSource.setFilters("stat");
		// dataSource.setPoolPreparedStatements(false);
		// PooledConnection pooledConnection = dataSource.getPooledConnection();
		// pooledConnection.getConnection().createStatement()
		logger.info("success to connect to server:" + info.get("datasource.url"));
		return dataSource;
	}

	public PooledConnection createPooledConnection(Map<String, String> info) throws SQLException {
		DruidDataSource dataSource = createDataSource(info);
		return dataSource.getPooledConnection();
	}

	public Connection getConnection(String connName) throws SQLException {
		DruidDataSource druidDataSource = this.dataSourceMap.get(connName);
		logger.info("[{}] datasource connection status: [ConnectCount:{}, ActiveCount:{}, CloseCount:{}]", connName,
				druidDataSource.getConnectCount(), druidDataSource.getActiveCount(), druidDataSource.getCloseCount());

		return druidDataSource.getConnection(30000L);
	}

	public PooledConnection getConnPool(String connectionName) throws SQLException {

		return this.pools.get(connectionName);
	}

	public DruidDataSource getDatasource(String connName) throws SQLException {

		return this.dataSourceMap.get(connName);
	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 * @param rs
	 * @param st
	 * @throws IOException
	 */
	public static void closeConnection(Connection conn, ResultSet rs, Statement st) throws IOException {
		// 从小关到大
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				logger.error("ResultSet close exception", e);
			} finally {
				if (st != null) {
					try {
						st.close();
						st = null;
					} catch (SQLException e) {
						logger.error("Statement close exception", e);
					}
				}
			}
		}

		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				logger.error("Connection close exception", e);
			}
		}
	}

	public boolean close() {
		for (String key : dataSourceMap.keySet()) {
			DruidDataSource druidDataSource = dataSourceMap.get(key);
			druidDataSource.close();
		}
		return true;
	}
}
