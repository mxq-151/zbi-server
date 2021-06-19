package org.zbi.server.dao.service;

import java.util.List;
import java.util.Map;

import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.facade.FacadeBoard;

/**
 * 看板
 */
public interface BoardDaoService {

	/**
	 * 创建新看板
	 * 
	 * @throws QueryException
	 */
	public FacadeBoard createNewBoard(String boardName, String boardDesc, String folderID) throws QueryException;

	/**
	 * 删除看板
	 */
	public boolean deleteBoard(String boardID) throws QueryException;;

	/**
	 * 获取看板
	 */
	public FacadeBoard getBoardByID(String boardID) throws QueryException;

	/**
	 * 获取看板
	 */
	public List<FacadeBoard> getBoardByFolderID(String folderID) throws QueryException;

	/**
	 * 更新看板布局信息
	 */
	public FacadeBoard updateBoardStype(Map<String, Object> otherParams, String boardID) throws QueryException;

	/***
	 * 更新看板名称
	 */
	public FacadeBoard updateBoardName(String boardID, String boardName, String boardDesc) throws QueryException;

}
