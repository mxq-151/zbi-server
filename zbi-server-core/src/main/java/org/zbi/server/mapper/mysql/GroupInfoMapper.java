package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.GroupInfo;
import org.zbi.server.entity.mysql.GroupUser;
import org.zbi.server.model.facade.FacadeUser;

public interface GroupInfoMapper {

	public boolean createGroup(String groupID, String groupName, String groupDesc, String adminID);

	public GroupInfo getGroupInfoById(String groupID);

	public List<FacadeUser> getGroupUsers(String groupID);

	public boolean checkGroupByName(String groupName, String adminID);

	public List<GroupInfo> getAdminGroup(String adminID);

	public int addUserToGroup(List<GroupUser> users);

	public int deleteUserInGroup(String groupID);

	public void updateGroupAdmin(String adminID, String groupID);

	public void deleteGroup(String groupID);

}
