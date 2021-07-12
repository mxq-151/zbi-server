
package org.zbi.server.model.engine;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.PooledConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zbi.server.entity.mysql.ConnParam;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 连接池
 */
public class ConnectionFactory {

	public static final String URL = "datasource.url";

	public static final String CLASS = "datasource.driver-class-name";

	public static final String USER = "datasource.username";

	public static final String PASSWORD = "datasource.password";

	public static final String INITSIZE = "datasource.initial.size";

	public static final String IDLSIZE = "datasource.min.idle";

	public static final String MAXSIZE = "datasource.max.active";

	public static final String[] array = {URL,CLASS,USER,PASSWORD,INITSIZE,IDLSIZE,MAXSIZE};

	private final static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
	private Map<String, PooledConnection> pools = new HashMap<>();
	private Map<String, DruidDataSource> dataSourceMap = new HashMap<>();

	public void initConnnection(List<ConnParam> params, String url, String connID) throws SQLException {
		Map<String, String> maps = new HashMap<>(6);

		for (ConnParam param : params) {
			maps.put(param.getParamKey(), param.getParamVal());
		}
		DruidDataSource dataSource = createDataSource(maps);
		PooledConnection pooledConnection = dataSource.getPooledConnection();
		dataSourceMap.put(connID, dataSource);
		pools.put(connID, pooledConnection);

	}

	public DruidDataSource createDataSource(Map<String, String> info) throws SQLException {
		logger.info("try to connect to server:" + info.get(URL));
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(info.get(CLASS));
		dataSource.setUsername(info.get(USER));
		dataSource.setPassword(info.get(PASSWORD));
		dataSource.setUrl(info.get(URL));
		dataSource.setInitialSize(Integer.parseInt(info.get(INITSIZE)));
		dataSource.setMinIdle(Integer.parseInt(info.get(IDLSIZE)));
		dataSource.setMaxActive(Integer.parseInt(info.get(MAXSIZE)));
		/**
		 * 设置断开重连机制
		 */
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setValidationQuery("select 1");
		dataSource.init();
		logger.info("success to connect to server:" + info.get(URL));
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
