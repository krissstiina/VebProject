package veb.cinema.demo.exception;

public class HallNotFoundException extends RuntimeException{

    public HallNotFoundException(){
        super("Hall not found");
    }
}