package org.zbi.server.mapper.mysql;

import org.zbi.server.entity.mysql.SysClientInfo;

public interface SysClientInfoMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table w_sys_client_info
	 *
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table w_sys_client_info
	 *
	 * @mbggenerated
	 */
	int insert(SysClientInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table w_sys_client_info
	 *
	 * @mbggenerated
	 */
	int insertSelective(SysClientInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table w_sys_client_info
	 *
	 * @mbggenerated
	 */
	SysClientInfo selectByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table w_sys_client_info
	 *
	 * @mbggenerated
	 */
	int updateByPrimaryKeySelective(SysClientInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table w_sys_client_info
	 *
	 * @mbggenerated
	 */
	int updateByPrimaryKey(SysClientInfo record);

	SysClientInfo selectByClientId(String clientId);
}