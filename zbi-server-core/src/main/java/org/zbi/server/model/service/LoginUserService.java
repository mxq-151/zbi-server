package org.zbi.server.model.service;

import org.zbi.server.model.facade.FacadeUser;

public interface LoginUserService {
	
	
	public FacadeUser getLoginUser();
	
	public String encodePassword(String pwd);

}
