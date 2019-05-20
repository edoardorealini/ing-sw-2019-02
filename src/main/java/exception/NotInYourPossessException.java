package exception;

public class NotInYourPossessException extends Exception{
    public NotInYourPossessException(){
        super();
    }

    public NotInYourPossessException(String message){
        super(message);
    }
}
