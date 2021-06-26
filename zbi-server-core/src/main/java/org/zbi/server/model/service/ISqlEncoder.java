package org.zbi.server.model.service;

import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.parse.ParseModel;

public interface ISqlEncoder {

	public String encodeSql(ParseModel model) throws ParseException;

}
