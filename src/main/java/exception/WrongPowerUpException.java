package exception;

public class WrongPowerUpException extends Exception{
    public WrongPowerUpException(){
        super();
    }

    public WrongPowerUpException(String message){
        super(message);
    }
}
