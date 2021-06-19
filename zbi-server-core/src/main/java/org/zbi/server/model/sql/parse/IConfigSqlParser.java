package org.zbi.server.model.sql.parse;

/**
 * 用mysql语法来表达SQL
 * */
public interface IConfigSqlParser {
	
	public SqlParseResult parseToConfig(String configSQL);

}
