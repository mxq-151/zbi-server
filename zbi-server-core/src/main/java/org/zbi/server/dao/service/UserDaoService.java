package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.model.facade.FacadeUser;

public interface UserDaoService {
	
	public FacadeUser getUserWrapperInfoByEmail(String email);
	
	public FacadeUser getUserWrapperInfoByID(long userID);

	public void createUser(FacadeUser user);
	
	public List<FacadeUser> loadAllUser();

}
