package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.ConnInfo;
import org.zbi.server.entity.mysql.ConnParam;

public interface ConnInfoMapper {
	
	public void createConnect(ConnInfo conn);
	
	public void inserParam(List<ConnParam> list);
	
	public void deleteParam(String connID);
	
	public List<ConnParam> getParams(String connID);
	
	public List<ConnInfo> loadConn();
	
	public ConnInfo loadConnInfo(String connID);

}
