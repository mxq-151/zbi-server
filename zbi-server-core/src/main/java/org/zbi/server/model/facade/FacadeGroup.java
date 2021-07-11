package org.zbi.server.model.facade;

import java.util.List;

import org.zbi.server.entity.mysql.GroupInfo;

public class FacadeGroup extends GroupInfo {
	
	private List<String> users;

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

}
