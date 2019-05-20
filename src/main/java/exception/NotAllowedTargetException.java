package exception;

public class NotAllowedTargetException extends Exception{
	public NotAllowedTargetException() {
		super();
	}

	public NotAllowedTargetException(String message){
		super(message);
	}
}
