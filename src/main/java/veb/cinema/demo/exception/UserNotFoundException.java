package veb.cinema.demo.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("Hall not found");
    }
    
}
