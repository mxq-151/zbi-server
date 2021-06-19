package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.model.core.CanSeeWord;

public interface CanSeesMapper {

	public void insertWords(List<CanSeeWord> canSees);

	public void deleteCanSees(String groupID, String colID);

	public List<CanSeeWord> listWords(String groupID, String colID);
}
