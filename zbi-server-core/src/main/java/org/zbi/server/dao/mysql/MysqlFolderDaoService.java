package org.zbi.server.dao.mysql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.FolderDaoService;
import org.zbi.server.entity.mysql.BoardInfo;
import org.zbi.server.entity.mysql.FolderInfo;
import org.zbi.server.entity.mysql.UserInfo;
import org.zbi.server.mapper.mysql.BoardInfoMapper;
import org.zbi.server.mapper.mysql.FolderInfoMapper;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeFolder;
import org.zbi.server.model.service.LoginUserService;

@Component
public class MysqlFolderDaoService implements FolderDaoService {

	@Autowired
	FolderInfoMapper folderInfoMapper;

	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	BoardInfoMapper boardInfoMapper;

	@Override
	public List<FacadeFolder> getQueryFolders() {
		// TODO Auto-generated method stub
		String userID = this.loginUserService.getLoginUser().getUserID();
		List<FolderInfo> folders = folderInfoMapper.loadFolders(userID);
		return this.merge(folders);

	}

	private List<FacadeFolder> merge(List<FolderInfo> folders) {
		List<String> fs = new ArrayList<>(folders.size());
		for (FolderInfo folder : folders) {
			fs.add(folder.getFolderID());
		}
		List<BoardInfo> boards = this.boardInfoMapper.loadBoardByFolders(fs);

		List<FacadeFolder> array = new ArrayList<FacadeFolder>();
		for (FolderInfo folder : folders) {
			FacadeFolder ff = new FacadeFolder();
			BeanUtils.copyProperties(folder, ff);
			List<BoardInfo> list = new ArrayList<>();
			for (BoardInfo board : boards) {
				if (board.getFolderID().equals(folder.getFolderID())) {
					BoardInfo br = new BoardInfo();
					BeanUtils.copyProperties(board, br);
					list.add(br);
				}
			}

			if (!list.isEmpty()) {
				ff.setBoards(list);
				ff.setBoardNum(list.size());
				array.add(ff);
			}else
			{
				int roleType=this.loginUserService.getLoginUser().getRoleType();
				if(roleType==UserInfo.SUPERADMIN || roleType==UserInfo.DEVELOPER)
				{
					ff.setBoards(Collections.EMPTY_LIST);
					ff.setBoardNum(0);
					array.add(ff);
				}
			}
		}
		return array;
	}

	@Override
	public FacadeFolder createFolder(String folderName, String folderDesc, int folderType) throws AdminException {
		// TODO Auto-generated method stub

		if (this.loginUserService.getLoginUser().getRoleType() > UserInfo.DEVELOPER) {
			throw new AdminException("无权限进行此操作");
		}

		if (this.folderInfoMapper.checkFolderByName(folderName, folderType,
				this.loginUserService.getLoginUser().getUserID())) {
			throw new AdminException("存在相同的文件夹名称");
		}
		String folderID = java.util.UUID.randomUUID().toString();
		FolderInfo folder = new FolderInfo();
		folder.setAdminID(this.loginUserService.getLoginUser().getUserID());
		folder.setFolderDesc(folderDesc);
		folder.setFolderName(folderName);
		folder.setFolderType(folderType);
		folder.setFolderID(folderID);
		this.folderInfoMapper.saveFolder(folder);

		FacadeFolder ff = new FacadeFolder();
		BeanUtils.copyProperties(folder, ff);
		ff.setBoards(Collections.emptyList());

		return ff;

	}

	@Override
	public boolean deleteFolder(String folderID) throws AdminException {
		// TODO Auto-generated method stub
		this.checkPermission(folderID);
		this.folderInfoMapper.deleteFolderByID(folderID);
		return true;
	}

	@Override
	public boolean updateFolder(String folderID, String folderName, String folderDesc) throws AdminException {
		// TODO Auto-generated method stub
		this.checkPermission(folderID);
		return this.folderInfoMapper.updateFolder(folderID, folderName, folderDesc);
	}

	private void checkPermission(String folderID) throws AdminException {
		FolderInfo folder = this.folderInfoMapper.loadFolder(folderID);
		// 谁创建谁管理
		if (!folder.getAdminID().equals(this.loginUserService.getLoginUser().getUserID())) {
			throw new AdminException("无权操作此文件夹");
		}
		if (folder != null) {
			throw new AdminException("无此文件夹");
		}
	}

	@Override
	public List<FacadeFolder> listAdminFolders() {
		// TODO Auto-generated method stub
		List<FolderInfo> folders = this.folderInfoMapper
				.loadAdminFolder(this.loginUserService.getLoginUser().getUserID());
		return this.merge(folders);
	}

}
