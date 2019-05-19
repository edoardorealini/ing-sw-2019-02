package exception;

public class NotAllowedShootingMode extends Exception{
    public NotAllowedShootingMode(){
        super();
    }

    public NotAllowedShootingMode(String message){
        super(message);
    }
}
