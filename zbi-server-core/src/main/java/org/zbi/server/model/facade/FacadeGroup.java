package org.zbi.server.model.facade;

import org.zbi.server.entity.mysql.GroupInfo;

public class FacadeGroup extends GroupInfo {

	/**
	 * 组内用户数
	 */
	private int userNum;

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

}
