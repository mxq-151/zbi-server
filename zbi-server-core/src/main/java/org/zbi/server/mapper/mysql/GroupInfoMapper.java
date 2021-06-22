package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.GroupInfo;

public interface GroupInfoMapper {

	public boolean createGroup(String groupName, String groupDesc, String groupID, String createID);

	public GroupInfo getGroupInfoById(String groupID);

	public boolean checkGroupByName(String groupName);

	public List<GroupInfo> getAdminGroup(String userID);

	public int addUserToGroup(List<String> users, String groupID);

	public int deleteUserInGroup(List<String> users, String groupID);

	public void updateGroupAdmin(String adminID, String groupID);
	
	public void deleteGroup(String groupID);

}
