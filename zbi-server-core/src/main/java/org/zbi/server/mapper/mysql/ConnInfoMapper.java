package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.ConnInfo;
import org.zbi.server.entity.mysql.ConnParam;

public interface ConnInfoMapper {
	
	public void saveConnect(ConnInfo conn);
	
	public void saveParam(List<ConnParam> list);
	
	public void deleteParam(String connID);
	
	public List<ConnParam> loadParam(String connID);
	
	public List<ConnInfo> loadConn();
	
	public void deleteConn(String connID);
	
	public ConnInfo loadConnInfo(String connID);

}
