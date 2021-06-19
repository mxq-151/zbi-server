package org.zbi.server.model.engine;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zbi.server.model.response.ColumnMetaResp;
import org.zbi.server.model.response.QueryResultResp;
/**
 * JDBC方试访问
 * **/
public abstract class QueryEngine implements IQueryEngine{
	
	protected Connection connection;
	
	public QueryEngine(Connection connection)
	{
		this.connection=connection;
	}
	
	public void closeConnection(ResultSet rs) throws IOException
	{
		ConnectionFactory.closeConnection(this.connection, rs, null);
	}
	
	public  QueryResultResp castToResult(ResultSet rs) throws SQLException
	{
		
		QueryResultResp resp=new QueryResultResp();
		ResultSetMetaData metas = rs.getMetaData();
        int count = metas.getColumnCount();
        
        
        List<List<String>> results = new ArrayList<>();
        List<ColumnMetaResp> columnMetas=new ArrayList<>();
        
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
       
        return resp;
	}

	public Connection getConnection() {
		return connection;
	}

}
