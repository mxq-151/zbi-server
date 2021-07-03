package org.zbi.server.dao.mysql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.GroupDaoService;
import org.zbi.server.entity.mysql.GroupInfo;
import org.zbi.server.entity.mysql.GroupUser;
import org.zbi.server.entity.mysql.UserInfo;
import org.zbi.server.mapper.mysql.GroupInfoMapper;
import org.zbi.server.mapper.mysql.UserInfoMapper;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeGroup;
import org.zbi.server.model.facade.FacadeUser;
import org.zbi.server.model.service.LoginUserService;

@Component
public class MysqlGroupDaoService implements GroupDaoService {

	@Autowired
	private GroupInfoMapper groupInfoMapper;

	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public FacadeGroup getGroupByGroupID(String groupID) {
		// TODO Auto-generated method stub
		GroupInfo group = this.groupInfoMapper.getGroupInfoById(groupID);
		return this.toFacadeGroup(group, Collections.emptyList());
	}

	@Override
	public FacadeGroup createGroup(String groupName, String groupDesc) throws AdminException {
		// TODO Auto-generated method stub

		int roleType = this.loginUserService.getLoginUser().getRoleType();
		if (roleType > UserInfo.DEVELOPER) {
			throw new AdminException("无权限进行此操作");
		}

		boolean exsist = this.groupInfoMapper.checkGroupByName(groupName,
				this.loginUserService.getLoginUser().getUserID());
		if (exsist) {
			throw new AdminException("用户组已经存在" + groupName);
		}

		String groupID = java.util.UUID.randomUUID().toString();
		String userID = loginUserService.getLoginUser().getUserID();
		this.groupInfoMapper.createGroup(groupID, groupName, groupDesc, userID);

		FacadeGroup group = new FacadeGroup();
		group.setGroupDesc(groupDesc);
		group.setGroupName(groupName);
		group.setGroupID(groupID);
		group.setUsers(Collections.emptyList());
		return group;

	}

	private FacadeGroup toFacadeGroup(GroupInfo group, List<String> users) {
		FacadeGroup fgroup = new FacadeGroup();
		BeanUtils.copyProperties(group, fgroup);
		fgroup.setUsers(users);
		return fgroup;
	}

	@Override
	public List<FacadeGroup> getAdminGroup() {
		// TODO Auto-generated method stub

		List<GroupInfo> groups = this.groupInfoMapper.getAdminGroup(this.loginUserService.getLoginUser().getUserID());

		List<FacadeGroup> array = new ArrayList<>();
		for (GroupInfo group : groups) {
			FacadeGroup fgroup = this.toFacadeGroup(group, Collections.emptyList());
			array.add(fgroup);
		}
		return array;
	}

	@Override
	public int addUserToGroup(List<GroupUser> users, String groupID) throws AdminException {
		// TODO Auto-generated method stub
		this.checkPermission(groupID);
		this.groupInfoMapper.deleteUserInGroup(groupID);
		return this.groupInfoMapper.addUserToGroup(users);
	}

	@Override
	public void updateGroupAdmin(String adminID, List<String> groupIDS) throws AdminException {
		// TODO Auto-generated method stub
		int roleType = this.loginUserService.getLoginUser().getRoleType();
		if (roleType > UserInfo.MEMBERS) {
			throw new AdminException("无权限进行此操作");
		}

		UserInfo user = this.userInfoMapper.getUserByUserID(adminID);

		if (user.getRoleType() > 3) {
			throw new AdminException("用户:" + user.getUserName() + "不能被赋予组管理权限");
		}
		for (String groupID : groupIDS) {
			GroupInfo gi = this.groupInfoMapper.getGroupInfoById(groupID);
			this.groupInfoMapper.updateGroupAdmin(adminID, groupID);
		}

	}

	@Override
	public void deleteGroup(String groupID) throws AdminException {
		// TODO Auto-generated method stub
		this.checkPermission(groupID);
		this.groupInfoMapper.deleteGroup(groupID);
	}

	private void checkPermission(String groupID) throws AdminException {
		GroupInfo group = this.groupInfoMapper.getGroupInfoById(groupID);
		FacadeUser user = this.loginUserService.getLoginUser();
		if (group.getAdminID().equals(user.getUserID()) || (user.getRoleType() == UserInfo.SUPERADMIN)
				|| (user.getRoleType() == UserInfo.DEVELOPER)) {
			return;
		} else {
			throw new AdminException("用户:" + this.loginUserService.getLoginUser().getUserName() + "没有删除权限");
		}
	}

	@Override
	public List<FacadeUser> getGroupUsers(String groupID) {
		// TODO Auto-generated method stub
		return this.groupInfoMapper.getGroupUsers(groupID);
	}

}
