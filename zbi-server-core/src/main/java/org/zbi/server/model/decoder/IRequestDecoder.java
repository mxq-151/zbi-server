package org.zbi.server.model.decoder;

import org.zbi.server.model.config.ModelConfig;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.parse.ParseSqlParam;
import org.zbi.server.model.request.RequestParam;

public interface IRequestDecoder<T extends ParseSqlParam> {
	
	public T parseToConfig(RequestParam param,ModelConfig modelConfig,T parseParam ) throws ParseException;

}
