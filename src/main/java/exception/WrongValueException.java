package exception;

public class WrongValueException extends Exception{
    public WrongValueException(){
        super();
    }

    public WrongValueException(String message){
        super(message);
    }
}
