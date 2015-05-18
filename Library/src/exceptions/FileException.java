package exceptions;
/**
 * Exception for error with file system
 * 
 * @author Mikalai_Churakou
 */
public class FileException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Throwable cause;
	private String message;
	private String localizedMessag;
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

	public FileException (Exception e) {
		super();
		this.setCause(e.getCause());
		this.setMessage(e.getMessage());
		this.setLocalizedMessag(e.getLocalizedMessage());
		
		
	}

}
