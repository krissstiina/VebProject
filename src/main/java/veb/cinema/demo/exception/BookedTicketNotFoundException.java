package veb.cinema.demo.exception;
public class BookedTicketNotFoundException extends RuntimeException {

    public BookedTicketNotFoundException() {
        super("Бронирование не найдено.");
    }

    public BookedTicketNotFoundException(String message) {
        super(message);
    }

    public BookedTicketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookedTicketNotFoundException(Throwable cause) {
        super(cause);
    }
}

