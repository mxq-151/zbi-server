package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.GroupInfo;
import org.zbi.server.entity.mysql.GroupUser;
import org.zbi.server.model.facade.FacadeUser;

public interface GroupInfoMapper {

	public boolean saveGroup(GroupInfo groupInfo);

	public GroupInfo loadGroupInfoById(String groupID);

	public List<FacadeUser> loadGroupUsers(String groupID);

	public boolean checkGroupByName(String groupName, String adminID);

	public List<GroupInfo> getAdminGroup(String adminID);

	public int saveUserToGroup(List<GroupUser> users);

	public int deleteUserInGroup(String groupID);

	public void updateGroupAdmin(String adminID, String groupID);

	public void deleteGroup(String groupID);

}
