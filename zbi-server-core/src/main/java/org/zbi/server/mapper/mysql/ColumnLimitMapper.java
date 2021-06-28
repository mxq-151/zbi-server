package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.GroupColLimit;
import org.zbi.server.entity.mysql.UserColLimit;

public interface ColumnLimitMapper {

	//用于权限控制加载
	public List<String> queryUserTotalColLimit(String userID, String modelTag);

	//用于管理查看组字段限制
	public List<GroupColLimit> queryGroupColLimit(String groupID, String modelTag);

	//用于管理查看个人字段限制
	public List<UserColLimit> queryUserColLimit(String userID, String modelTag);

	public void insertUserColLimit(List<UserColLimit> list);

	public void deleteUserColLimit(String userID, String modelTag);
	
	public void insertGroupColLimit(List<GroupColLimit> list);

	public void deleteGroupColLimit(String groupID, String modelTag);

}
