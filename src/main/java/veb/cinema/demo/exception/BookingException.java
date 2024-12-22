
package veb.cinema.demo.exception;


public class BookingException extends RuntimeException {

    public BookingException(){
        super("Ticket is already boocked");
    }
}
