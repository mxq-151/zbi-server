package org.zbi.server.rest.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.zbi.server.model.facade.FacadeUser;
import org.zbi.server.model.service.LoginUserService;

@Component
public class LoginUserServiceImpl implements LoginUserService {

	@Override
	public FacadeUser getLoginUser() {
		// TODO Auto-generated method stub

		Authentication auth=SecurityContextHolder.getContext().getAuthentication();


		FacadeUser user = new FacadeUser();

		return user;
	}

}
