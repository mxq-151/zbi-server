package org.zbi.server.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.FolderDaoService;
import org.zbi.server.entity.mysql.FolderInfo;
import org.zbi.server.entity.mysql.UserInfo;
import org.zbi.server.mapper.mysql.FolderAndBoardMapper;
import org.zbi.server.mapper.mysql.FolderInfoMapper;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.facade.FacadeFolder;
import org.zbi.server.model.response.BoardInfoResp;
import org.zbi.server.model.service.LoginUserService;

@Component
public class MysqlFolderDaoService implements FolderDaoService {

	@Autowired
	FolderInfoMapper folderInfoMapper;

	@Autowired
	FolderAndBoardMapper folderAndBoardMapper;

	@Autowired
	private LoginUserService loginUserService;

	@Override
	public List<FacadeFolder> getFolders() {
		// TODO Auto-generated method stub
		List<FolderInfo> folders = folderInfoMapper.getFolders();

		List<FacadeFolder> allFolders = new ArrayList<>();
		for (FolderInfo folder : folders) {
			List<BoardInfoResp> boards = folderAndBoardMapper.getBoardDescByFolderID(folder.getFolderID());
			allFolders.add(this.transferFacadeFolder(folder, boards));
		}

		return allFolders;

	}

	@Override
	public FacadeFolder getFolder(String folderID) {
		// TODO Auto-generated method stub

		FolderInfo folder = this.folderInfoMapper.getFolder(folderID);
		List<BoardInfoResp> boards = folderAndBoardMapper.getBoardDescByFolderID(folder.getFolderID());
		return this.transferFacadeFolder(folder, boards);
	}

	@Override
	public FacadeFolder createFolder(String folderName, String folderDesc) throws AdminException {
		// TODO Auto-generated method stub

		if (this.loginUserService.getLoginUser().getRoleType() > UserInfo.DEVELOPER) {
			throw new AdminException("无权限进行此操作");
		}

		if (this.folderInfoMapper.checkFolderByName(folderName)) {
			throw new AdminException("存在相同的文件夹名称");
		}
		String folderID = java.util.UUID.randomUUID().toString();
		this.folderInfoMapper.createFolder(folderID, folderName, folderDesc,
				this.loginUserService.getLoginUser().getUserID());
		FolderInfo folder = this.folderInfoMapper.getFolder(folderID);

		return this.getFolder(folder.getFolderID());
	}

	@Override
	public boolean addBoardToFolder(String folderID, String boardID, String boardName) throws AdminException {
		// TODO Auto-generated method stub
		this.checkPermission(folderID);
		return folderAndBoardMapper.addBoardToFolder(boardID, folderID);
	}

	@Override
	public boolean removeBoard(String boardID, String folderID) throws AdminException {
		// TODO Auto-generated method stub
		this.checkPermission(folderID);
		return this.folderAndBoardMapper.deleteBoardInFolder(boardID, folderID);
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

	private FacadeFolder transferFacadeFolder(FolderInfo folderInfo, List<BoardInfoResp> boards) {
		FacadeFolder folder = new FacadeFolder();
		folder.setFolderDesc(folderInfo.getFolderDesc());
		folder.setFolderID(folderInfo.getFolderID());
		folder.setFolderName(folderInfo.getFolderName());
		folder.setBoards(boards);
		return folder;

	}

	private void checkPermission(String folderID) throws AdminException {
		FolderInfo folder = this.folderInfoMapper.getFolder(folderID);
		// 谁创建谁管理
		if (!folder.getAdminID().equals(this.loginUserService.getLoginUser().getUserID())) {
			throw new AdminException("无权操作此文件夹");
		}
	}

}
