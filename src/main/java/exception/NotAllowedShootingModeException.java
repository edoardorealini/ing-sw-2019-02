package exception;

public class NotAllowedShootingModeException extends Exception{
    public NotAllowedShootingModeException(){
        super();
    }

    public NotAllowedShootingModeException(String message){
        super(message);
    }
}
