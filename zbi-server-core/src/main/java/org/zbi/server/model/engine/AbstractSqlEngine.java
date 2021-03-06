package org.zbi.server.model.engine;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.core.ColumnType;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.parse.ParseModel;
import org.zbi.server.model.response.ColumnMetaResp;
import org.zbi.server.model.response.QueryResultResp;
import org.zbi.server.model.service.ISqlEncoder;

public class AbstractSqlEngine implements IQueryEngine {

	protected Connection connection;

	private ISqlEncoder sqlEncoder;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public AbstractSqlEngine(Connection connection, ISqlEncoder sqlEncoder) {
		// TODO Auto-generated constructor stub
		this.connection = connection;
		this.sqlEncoder = sqlEncoder;
	}

	@Override
	public QueryResultResp query(ParseModel model) throws IOException, ParseException {
		// TODO Auto-generated method stub

		String sql = this.sqlEncoder.encodeSql(model);
		logger.info("query sql:{}", sql);
		try {
			ResultSet rs = this.connection.createStatement().executeQuery(sql);
			return this.castToResult(rs);
		} catch (SQLException ex) {
			throw new IOException(ex);
		}

	}

	public void closeConnection(ResultSet rs) throws IOException {
		ConnectionFactory.closeConnection(this.connection, rs, null);
	}

	public QueryResultResp castToResult(ResultSet rs) throws SQLException, IOException {

		QueryResultResp resp = new QueryResultResp();
		ResultSetMetaData metas = rs.getMetaData();
		int count = metas.getColumnCount();

		List<List<String>> results = new ArrayList<>();
		List<ColumnMetaResp> columnMetas = new ArrayList<>();

		resp.setColumnMetas(columnMetas);
		resp.setResults(results);

		for (int i = 1; i <= count; i++) {
			ColumnMetaResp selectedColumnMeta = new ColumnMetaResp();
			String originalColumnName = metas.getColumnName(i);
			String alias = metas.getColumnLabel(i);
			selectedColumnMeta.setLabel(alias);
			selectedColumnMeta.setName(originalColumnName);
			columnMetas.add(selectedColumnMeta);
		}

		while (rs.next()) {
			List<String> oneRowData = new ArrayList<>();
			columnMetas.forEach(oneColMeta -> {
				try {
					String value = rs.getString(oneColMeta.getLabel());
					oneRowData.add(value);
				} catch (SQLException e) {
					oneRowData.add("");
				}
			});
			results.add(oneRowData);
		}
		this.closeConnection(rs);
		return resp;
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	public List<ConfigColumn> fetchMeta(String tableName, String dbName) throws IOException {
		// TODO Auto-generated method stub
		String sql = "select * from information_schema.columns where table_schema ='zbi_db' and table_name = 'order'";

		List<ConfigColumn> columns = new ArrayList<>();
		try {
			ResultSet rs = this.connection.createStatement().executeQuery(sql);

			while (rs.next()) {
				String column = rs.getString("column_name");
				ConfigColumn cc = new ConfigColumn();
				cc.setColName(column);
				String type = rs.getString("data_type");
				ColumnType ct = this.toColumnType(type);
				String comment = rs.getString("column_comment");
				if (comment != null) {
					cc.setChinese(comment);
				}
				cc.setAdvanced(false);
				cc.setAlias(column);
				cc.setColumnType(ct);
				columns.add(cc);
			}
			this.closeConnection(rs);
			return columns;
		} catch (SQLException ex) {
			throw new IOException(ex);
		}
	}

	private ColumnType toColumnType(String type) throws IOException {
		type = type.toLowerCase();
		if (type.contains("varchar") || type.contains("text")) {
			return ColumnType.STRING;
		} else if (type.contains("date") || type.contains("time") || type.contains("year")) {
			return ColumnType.DATE;
		} else if (type.contains("int") || type.contains("float") || type.contains("double") || type.contains("num")) {
			return ColumnType.NUMBER;
		} else {
			throw new IOException("does not the type:" + type);
		}
	}

}
