package org.zbi.server.core.test;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zbi.server.rest.service.CustomUserDetails;

public class ServiceTestBase {

	public void superAdminLogin() {
		CustomUserDetails user = new CustomUserDetails("moxuqiang", 1, "", "1");
		this.setAuth(user);
	}

	public void departmentLogin() {
		CustomUserDetails user = new CustomUserDetails("department", 2, "", "2");
		this.setAuth(user);
	}

	public void developerLogin() {
		CustomUserDetails user = new CustomUserDetails("developer", 4, "", "3");
		this.setAuth(user);
	}

	public void groupLogin() {
		CustomUserDetails user = new CustomUserDetails("group", 3, "", "4");
		this.setAuth(user);
	}

	public void test1Login() {
		CustomUserDetails user = new CustomUserDetails("test1", 5, "", "5");
		this.setAuth(user);
	}

	private void setAuth(CustomUserDetails user) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
