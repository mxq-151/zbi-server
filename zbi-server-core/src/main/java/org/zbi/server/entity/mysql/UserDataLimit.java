package org.zbi.server.entity.mysql;

public class UserDataLimit {
	String userID;
	String word;
	String colID;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getColID() {
		return colID;
	}
	public void setColID(String colID) {
		this.colID = colID;
	}
}
