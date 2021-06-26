package org.zbi.server.entity.mysql;

import java.util.Date;

public class SysClientInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.ID_
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_client_id
     *
     * @mbggenerated
     */
    private String clientId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_client_secret
     *
     * @mbggenerated
     */
    private String clientSecret;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_resource
     *
     * @mbggenerated
     */
    private String resource;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_scope
     *
     * @mbggenerated
     */
    private String scope;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_grant_type
     *
     * @mbggenerated
     */
    private String grantType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_redirect_uri
     *
     * @mbggenerated
     */
    private String redirectUri;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_authoritie
     *
     * @mbggenerated
     */
    private String authoritie;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_additional_Info
     *
     * @mbggenerated
     */
    private String additionalInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_access_validity
     *
     * @mbggenerated
     */
    private Integer accessValidity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_refresh_validity
     *
     * @mbggenerated
     */
    private Integer refreshValidity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_auto_approve
     *
     * @mbggenerated
     */
    private Boolean autoApprove;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_login_ip
     *
     * @mbggenerated
     */
    private String loginIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_effect_date
     *
     * @mbggenerated
     */
    private Date effectDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_client_info.F_expire_date
     *
     * @mbggenerated
     */
    private Date expireDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.ID_
     *
     * @return the value of sys_client_info.ID_
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.ID_
     *
     * @param id the value for sys_client_info.ID_
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_client_id
     *
     * @return the value of sys_client_info.F_client_id
     *
     * @mbggenerated
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_client_id
     *
     * @param clientId the value for sys_client_info.F_client_id
     *
     * @mbggenerated
     */
    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_client_secret
     *
     * @return the value of sys_client_info.F_client_secret
     *
     * @mbggenerated
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_client_secret
     *
     * @param clientSecret the value for sys_client_info.F_client_secret
     *
     * @mbggenerated
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret == null ? null : clientSecret.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_resource
     *
     * @return the value of sys_client_info.F_resource
     *
     * @mbggenerated
     */
    public String getResource() {
        return resource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_resource
     *
     * @param resource the value for sys_client_info.F_resource
     *
     * @mbggenerated
     */
    public void setResource(String resource) {
        this.resource = resource == null ? null : resource.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_scope
     *
     * @return the value of sys_client_info.F_scope
     *
     * @mbggenerated
     */
    public String getScope() {
        return scope;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_scope
     *
     * @param scope the value for sys_client_info.F_scope
     *
     * @mbggenerated
     */
    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_grant_type
     *
     * @return the value of sys_client_info.F_grant_type
     *
     * @mbggenerated
     */
    public String getGrantType() {
        return grantType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_grant_type
     *
     * @param grantType the value for sys_client_info.F_grant_type
     *
     * @mbggenerated
     */
    public void setGrantType(String grantType) {
        this.grantType = grantType == null ? null : grantType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_redirect_uri
     *
     * @return the value of sys_client_info.F_redirect_uri
     *
     * @mbggenerated
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_redirect_uri
     *
     * @param redirectUri the value for sys_client_info.F_redirect_uri
     *
     * @mbggenerated
     */
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri == null ? null : redirectUri.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_authoritie
     *
     * @return the value of sys_client_info.F_authoritie
     *
     * @mbggenerated
     */
    public String getAuthoritie() {
        return authoritie;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_authoritie
     *
     * @param authoritie the value for sys_client_info.F_authoritie
     *
     * @mbggenerated
     */
    public void setAuthoritie(String authoritie) {
        this.authoritie = authoritie == null ? null : authoritie.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_additional_Info
     *
     * @return the value of sys_client_info.F_additional_Info
     *
     * @mbggenerated
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_additional_Info
     *
     * @param additionalInfo the value for sys_client_info.F_additional_Info
     *
     * @mbggenerated
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo == null ? null : additionalInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_access_validity
     *
     * @return the value of sys_client_info.F_access_validity
     *
     * @mbggenerated
     */
    public Integer getAccessValidity() {
        return accessValidity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_access_validity
     *
     * @param accessValidity the value for sys_client_info.F_access_validity
     *
     * @mbggenerated
     */
    public void setAccessValidity(Integer accessValidity) {
        this.accessValidity = accessValidity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_refresh_validity
     *
     * @return the value of sys_client_info.F_refresh_validity
     *
     * @mbggenerated
     */
    public Integer getRefreshValidity() {
        return refreshValidity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_refresh_validity
     *
     * @param refreshValidity the value for sys_client_info.F_refresh_validity
     *
     * @mbggenerated
     */
    public void setRefreshValidity(Integer refreshValidity) {
        this.refreshValidity = refreshValidity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_auto_approve
     *
     * @return the value of sys_client_info.F_auto_approve
     *
     * @mbggenerated
     */
    public Boolean getAutoApprove() {
        return autoApprove;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_auto_approve
     *
     * @param autoApprove the value for sys_client_info.F_auto_approve
     *
     * @mbggenerated
     */
    public void setAutoApprove(Boolean autoApprove) {
        this.autoApprove = autoApprove;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_login_ip
     *
     * @return the value of sys_client_info.F_login_ip
     *
     * @mbggenerated
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_login_ip
     *
     * @param loginIp the value for sys_client_info.F_login_ip
     *
     * @mbggenerated
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_effect_date
     *
     * @return the value of sys_client_info.F_effect_date
     *
     * @mbggenerated
     */
    public Date getEffectDate() {
        return effectDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_effect_date
     *
     * @param effectDate the value for sys_client_info.F_effect_date
     *
     * @mbggenerated
     */
    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_client_info.F_expire_date
     *
     * @return the value of sys_client_info.F_expire_date
     *
     * @mbggenerated
     */
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_client_info.F_expire_date
     *
     * @param expireDate the value for sys_client_info.F_expire_date
     *
     * @mbggenerated
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}