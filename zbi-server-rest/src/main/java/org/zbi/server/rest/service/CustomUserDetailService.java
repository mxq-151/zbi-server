package org.zbi.server.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.zbi.server.entity.mysql.UserInfo;
import org.zbi.server.mapper.mysql.UserInfoMapper;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserInfoMapper UserInfoMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		UserInfo user = UserInfoMapper.getUserByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("user not exsist:" + username);
		}

		CustomUserDetails cud = new CustomUserDetails(user.getUserName(), user.getRoleType(), user.getPassword(),
				user.getUserID());
		return cud;
	}

}
