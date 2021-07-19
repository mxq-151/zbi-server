package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.GroupDataLimit;

public interface DataLimitMapper {
	
	public void saveGroupDataLimit(List<GroupDataLimit> canSees);

	public void deleteGroupDataLimit(String groupID, String colID);

	public List<String> loadGroupDataLimit(String groupID, String colID);
	
	public List<String> loadDataLimit(String userID, String colID);
}
