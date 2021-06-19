package org.zbi.server.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.FolderDaoService;
import org.zbi.server.entity.mysql.FolderInfo;
import org.zbi.server.mapper.mysql.FolderAndBoardMapper;
import org.zbi.server.mapper.mysql.FolderInfoMapper;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.facade.FacadeFolder;
import org.zbi.server.model.response.BoardInfoResp;

@Component
public class MysqlFolderDaoService implements FolderDaoService {
	
	@Autowired
	FolderInfoMapper folderInfoMapper;
	
	@Autowired
	FolderAndBoardMapper folderAndBoardMapper;

	@Override
	public List<FacadeFolder> getFolders() {
		// TODO Auto-generated method stub
		 List<FolderInfo> folders=folderInfoMapper.getFolders();
		 
		 List<FacadeFolder> allFolders=new ArrayList<>();
		 for(FolderInfo folder:folders)
		 {
			 List<BoardInfoResp> boards=folderAndBoardMapper.getBoardDescByFolderID(folder.getFolderID());
			 allFolders.add(this.transferFacadeFolder(folder, boards));
		 }
		 
		 return allFolders;
		 
	}

	@Override
	public FacadeFolder getFolder(String folderID) {
		// TODO Auto-generated method stub
		FolderInfo folder= this.folderInfoMapper.getFolder(folderID);
		
		List<BoardInfoResp> boards=folderAndBoardMapper.getBoardDescByFolderID(folder.getFolderID());
		return this.transferFacadeFolder(folder, boards);
	}

	@Override
	public FacadeFolder createFolder(String folderName, String folderDesc, String userID) throws QueryException {
		// TODO Auto-generated method stub
		
		if(this.folderInfoMapper.checkFolderByName(folderName))
		{
			throw new QueryException("存在相同的文件夹名称");
		}
		String folderID=java.util.UUID.randomUUID().toString();
		this.folderInfoMapper.createFolder(folderID,folderName, folderDesc);
		FolderInfo folder=this.folderInfoMapper.getFolder(folderID);
		
		return this.getFolder(folder.getFolderID());
	}

	@Override
	public boolean addBoardToFolder(String folderID, String boardID, String boardName) {
		// TODO Auto-generated method stub
		return folderAndBoardMapper.addBoardToFolder(boardID, folderID);
	}

	@Override
	public boolean removeBoard(String boardID,String folderID) {
		// TODO Auto-generated method stub
		return this.folderAndBoardMapper.deleteBoardInFolder(boardID, folderID);
	}

	@Override
	public boolean deleteFolder(String folderID) throws QueryException {
		// TODO Auto-generated method stub
		 this.folderInfoMapper.deleteFolderByID(folderID);
		 return true;
	}

	@Override
	public boolean updateFolder(String folderID, String folderName, String folderDesc) throws QueryException {
		// TODO Auto-generated method stub
		return this.folderInfoMapper.updateFolder(folderID, folderName,folderDesc);
	}
	
	private FacadeFolder transferFacadeFolder(FolderInfo folderInfo,List<BoardInfoResp> boards)
	{
		FacadeFolder folder=new FacadeFolder();
		folder.setFolderDesc(folderInfo.getFolderDesc());
		folder.setFolderID(folderInfo.getFolderID());
		folder.setFolderName(folderInfo.getFolderName());
		folder.setBoards(boards);	
		return folder;
		
		
	}

}
