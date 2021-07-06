package org.zbi.server.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.zbi.server.model.facade.FacadeUser;
import org.zbi.server.model.service.LoginUserService;

@Component
public class LoginUserServiceImpl implements LoginUserService {
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public FacadeUser getLoginUser() {
		// TODO Auto-generated method stub

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();

		FacadeUser user = new FacadeUser();
		user.setUserID(cud.getUserID());
		user.setRoleType(cud.getRoleType());
		user.setUserName(cud.getUserName());

		return user;
	}

	@Override
	public String encodePassword(String pwd) {
		// TODO Auto-generated method stub
		return encoder.encode(pwd);
	}

}
