
package com.mikalai.library.exceptions;
/**
 * Exception for error with database
 * 
 * @author Mikalai_Churakou
 */
public class DBException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Throwable cause;
	private String message;
	private String localizedMessag;

	

	
	public DBException(Exception e) {
		super();
		this.setCause(e.getCause());
		this.setMessage(e.getMessage());
		this.setLocalizedMessag(e.getLocalizedMessage());
		
		
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLocalizedMessag() {
		return localizedMessag;
	}

	public void setLocalizedMessag(String localizedMessag) {
		this.localizedMessag = localizedMessag;
	}

	
	
}
