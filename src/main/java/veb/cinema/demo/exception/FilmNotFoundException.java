package veb.cinema.demo.exception;

public class FilmNotFoundException extends RuntimeException{

    public FilmNotFoundException(){
        super("Film not found");
    }
}
