package org.zbi.server.rest.service;

import java.util.Date;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

public class ClientDetailsInfo implements ClientDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Set<String> resourceIds;

	private String ClientSecret;

	private String clientId;

	private Set<String> authorizedGrantTypes;

	private Set<String> scope;

	private String loginIp;

	private Date Expiredate;

	private Date Takeeffectdate;

	private Set<String> registeredRedirectUri;

	private Integer AccessTokenValiditySeconds;

	private Integer refreshTokenValiditySeconds;

	private Map<String, Object> additionalInformation;

	private Collection<GrantedAuthority> authorities;

	@Override
	public String getClientId() {
		// TODO Auto-generated method stub
		return this.clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		// TODO Auto-generated method stub
		return this.resourceIds;
	}

	@Override
	public boolean isSecretRequired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getClientSecret() {
		// TODO Auto-generated method stub
		return this.ClientSecret;
	}

	@Override
	public boolean isScoped() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> getScope() {
		// TODO Auto-generated method stub
		return this.scope;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		// TODO Auto-generated method stub
		return this.authorizedGrantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		// TODO Auto-generated method stub
		return this.registeredRedirectUri;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		// TODO Auto-generated method stub
		return this.AccessTokenValiditySeconds;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		// TODO Auto-generated method stub
		return this.refreshTokenValiditySeconds;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		// TODO Auto-generated method stub
		return this.additionalInformation;
	}

	public void setResourceIds(Set<String> resourceIds) {
		this.resourceIds = resourceIds;
	}

	public void setClientSecret(String clientSecret) {
		ClientSecret = clientSecret;
	}

	public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
		this.registeredRedirectUri = registeredRedirectUri;
	}

	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		AccessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public Date getExpiredate() {
		return Expiredate;
	}

	public Date getTakeeffectdate() {
		return Takeeffectdate;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public void setExpiredate(Date expiredate) {
		Expiredate = expiredate;
	}

	public void setTakeeffectdate(Date takeeffectdate) {
		Takeeffectdate = takeeffectdate;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

}