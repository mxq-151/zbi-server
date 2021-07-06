package org.zbi.server.model.service;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.request.RequestParam;
import org.zbi.server.model.response.QueryResultResp;

@Component
public interface IQueryService {
	
	public QueryResultResp query(RequestParam param) throws  IOException;
	
	public QueryResultResp search(String keyword,String indexPath,int offset,int pageSize) throws ParseException, SQLException, IOException;

}
