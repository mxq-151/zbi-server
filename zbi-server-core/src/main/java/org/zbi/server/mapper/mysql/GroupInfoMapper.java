package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.GroupInfo;


public interface GroupInfoMapper {
	
	public boolean createGroup(String groupName,String groupDesc,int createrID);
	
	
	public int getGroupInfoById(int groupID);
	
	List<GroupInfo> getGroups();

}
