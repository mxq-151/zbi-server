package org.zbi.server.model.facade;

import org.zbi.server.entity.mysql.UserInfo;

public class FacadeUser extends UserInfo{

	private int GroupNum;

	public int getGroupNum() {
		return GroupNum;
	}

	public void setGroupNum(int groupNum) {
		GroupNum = groupNum;
	}

}
