package org.zbi.server.model.core;

/**
 * 可见值
 */
public class CanSeeWord {

	private int gcID;

	private String colID;

	private String groupId;

	/**
	 * 可见值
	 */
	private String word;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getGcID() {
		return gcID;
	}

	public void setGcID(int gcID) {
		this.gcID = gcID;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getColID() {
		return colID;
	}

	public void setColID(String colID) {
		this.colID = colID;
	}

}
