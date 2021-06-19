package org.zbi.server.model.nested;

import java.util.List;

import org.zbi.server.model.parse.ParseCondition;

public interface INestedSqlEncoder {
	
	
	public String encoderSql(NestedSqlParam param,List<ParseCondition> conditions,List<String> selectedCols);

}
