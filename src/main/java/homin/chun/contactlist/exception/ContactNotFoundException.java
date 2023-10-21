package homin.chun.contactlist.exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String message) {
        super(message);
    }
}
