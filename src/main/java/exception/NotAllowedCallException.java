package exception;


public class NotAllowedCallException extends Exception{
    public NotAllowedCallException(){
        super();
    }

    public NotAllowedCallException(String message){
        super(message);
    }
}
