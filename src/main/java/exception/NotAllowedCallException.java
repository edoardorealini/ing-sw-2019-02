package exception;

import javax.xml.stream.events.NotationDeclaration;

public class NotAllowedCallException extends Exception{
    public NotAllowedCallException(){
        super();
    }

    public NotAllowedCallException(String message){
        super(message);
    }
}
