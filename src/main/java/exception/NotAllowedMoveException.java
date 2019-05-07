package exception;

public class NotAllowedMoveException extends Exception{
    public NotAllowedMoveException(){
        super();
    }

    public NotAllowedMoveException(String message){
        super(message);
    }

}