package Exceptions;

public class InvalidPasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	
	public InvalidPasswordException() {
		this.message = "La contrase�a es incorrecta";
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
