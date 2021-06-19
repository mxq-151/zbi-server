package org.zbi.server.model.encoder;

import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.parse.ParseSqlParam;

public interface ISqlEncoder< T extends ParseSqlParam> {
	
	public String encodeSql(T param) throws ParseException;

}
