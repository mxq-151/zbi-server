package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.GroupColLimit;

public interface ColumnLimitMapper {

	// 用于权限控制加载
	public List<String> loadColLimit(String userID, String modelID);

	// 用于管理查看组字段限制
	public List<GroupColLimit> loadGroupColLimit(String groupID, String modelID);

	public void saveGroupColLimit(List<GroupColLimit> list);

	public void deleteGroupColLimit(String groupID, String modelID);

}
