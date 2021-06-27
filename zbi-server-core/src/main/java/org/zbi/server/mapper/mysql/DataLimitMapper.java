package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.GroupDataLimit;
import org.zbi.server.entity.mysql.UserDataLimit;

public interface DataLimitMapper {

	public void insertUserDataLimit(List<UserDataLimit> canSees);

	public void deleteUserDataLimit(String userID, String colID);

	public List<String> queryUserDataLimit(String userID, String colID);
	
	public void insertGroupDataLimit(List<GroupDataLimit> canSees);

	public void deleteGroupDataLimit(String groupID, String colID);

	public List<String> queryGroupDataLimit(String groupID, String colID);
	
	public List<String> queryUserTotalDataLimit(String userID, String colID);
}
