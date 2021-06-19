package org.zbi.server.mapper.mysql;

import org.zbi.server.entity.mysql.UserInfo;


public interface UserInfoMapper {

	public boolean createUser(UserInfo user);

	public boolean checkEmail(String email);

	public UserInfo getUserByUserID(String userID);

	public UserInfo getUserByEmail(String email);

}
