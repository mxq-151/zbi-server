package org.zbi.server.model.exception;

public class ParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ExceptionType type;
	
	public ParseException(String msg,ExceptionType type)
	{
		super(msg);
		this.type=type;
	}
	
	
	public enum ExceptionType {

		CONFIGERROR("配置错误"),REQUESTERROR("传参错误"),PARSEERROR("解析错误");
		
		private String type;

		ExceptionType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}


	public ExceptionType getType() {
		return type;
	}


}
