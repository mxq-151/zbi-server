package org.zbi.server.rest.service;

import java.util.Collections;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;
import org.zbi.server.entity.mysql.SysClientInfo;
import org.zbi.server.mapper.mysql.SysClientInfoMapper;

import com.google.common.collect.Sets;

@Component
public class CustomClientDetailService implements ClientDetailsService {
	
	@Autowired
	private SysClientInfoMapper sysClientInfoMapper;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		// TODO Auto-generated method stub

		SysClientInfo clientInfo = this.sysClientInfoMapper.selectByClientId(clientId);
		
		if(clientInfo==null)
		{
			throw new ClientRegistrationException("客户端不存在");
		}

		return toClientDetailsInfo(clientInfo);
	}

	private ClientDetailsInfo toClientDetailsInfo(SysClientInfo clientInfo) {

		ClientDetailsInfo clientDetail = new ClientDetailsInfo();

		clientDetail.setAccessTokenValiditySeconds(clientInfo.getAccessValidity());
		clientDetail.setClientId(clientInfo.getClientId());
		clientDetail.setClientSecret(clientInfo.getClientSecret());
		clientDetail.setExpiredate(clientInfo.getExpireDate());
		clientDetail.setTakeeffectdate(clientInfo.getEffectDate());
		clientDetail.setLoginIp(clientInfo.getLoginIp());
		clientDetail.setAuthorities(Collections.emptyList());

		if (clientInfo.getGrantType() != null) {
			String[] strs = clientInfo.getGrantType().split(";");
			HashSet<String> set = Sets.newHashSet(strs);
			clientDetail.setAuthorizedGrantTypes(set);
		}

		if (clientInfo.getResource() != null) {
			String[] strs = clientInfo.getResource().split(";");
			HashSet<String> set = Sets.newHashSet(strs);
			clientDetail.setResourceIds(set);
		}
		
		if (clientInfo.getScope() != null) {
			String[] strs = clientInfo.getScope().split(";");
			HashSet<String> set = Sets.newHashSet(strs);
			clientDetail.setScope(set);
		}
		
		if (clientInfo.getScope() != null) {
			String[] strs = clientInfo.getScope().split(";");
			HashSet<String> set = Sets.newHashSet(strs);
			clientDetail.setScope(set);
		}
		
		return clientDetail;

	}

}
