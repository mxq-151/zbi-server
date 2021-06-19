package org.zbi.server.model.core;

import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.ParseException.ExceptionType;

public class StringUtils {
	
	public static String getCol(String columnName) throws ParseException
	{
		if(columnName.split("\\.").length!=3)
		{
			throw new ParseException("配置错误",ExceptionType.CONFIGERROR);
		}
		
		return columnName.substring(columnName.indexOf(".")+1);
	}
	
	public static String getTableByWhole(String columnName) throws ParseException
	{
		if(columnName.split("\\.").length!=3)
		{
			throw new ParseException("配置错误",ExceptionType.CONFIGERROR);
		}
		
		return columnName.substring(0,columnName.lastIndexOf("."));
	}
	

	public static String getTable(String tableName) throws ParseException
	{
		if(tableName.split("\\.").length!=2)
		{
			throw new ParseException("配置错误",ExceptionType.CONFIGERROR);
		}
		
		return tableName.substring(tableName.indexOf(".")+1);
	}

}
