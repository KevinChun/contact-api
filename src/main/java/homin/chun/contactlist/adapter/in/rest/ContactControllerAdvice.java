package homin.chun.contactlist.adapter.in.rest;

import homin.chun.contactlist.exception.ContactNotFoundException;
import homin.chun.contactlist.exception.InvalidParameterException;
import homin.chun.contactlist.exception.NotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ContactControllerAdvice {

    @ExceptionHandler(InvalidParameterException.class)

    public ResponseEntity<?> InvalidParameterExceptionHandler(InvalidParameterException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.create(
                        exception,
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage()
                )
        );
    }

    @ExceptionHandler(NotSupportedException.class)
    public ResponseEntity<?> NotSupportedExceptionHandler(NotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.create(
                        exception,
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage()
                )
        );
    }

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<?> ContactNotFoundExceptionHandler(ContactNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.create(
                        exception,
                        HttpStatus.NOT_FOUND,
                        exception.getMessage()
                )
        );
    }
}
