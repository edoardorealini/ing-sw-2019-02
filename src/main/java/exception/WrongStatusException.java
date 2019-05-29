package exception;

//this exception is used when a player tries to do something but he is not in the correct status of his turn.

public class WrongStatusException extends Exception {

    public WrongStatusException(){
        super();
    }

    public WrongStatusException(String message){
        super(message);
    }

}
