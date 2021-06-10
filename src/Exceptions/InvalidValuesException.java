package Exceptions;

public class InvalidValuesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	
	public InvalidValuesException() {
		this.message = "Por favor llene todos los espacios";
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
