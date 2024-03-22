package io.sfinias.punk.exceptions;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String STATUS = "status";
    private static final String ERROR = "error";

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {

        Map<String, Object> body = Map.of(
                STATUS, ex.getStatusCode().toString(),
                ERROR, ex.getMessage()
        );

        return new ResponseEntity<>(body, ex.getStatusCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {

        Map<String, Object> body = Map.of(
                STATUS, HttpStatus.BAD_REQUEST,
                ERROR, ex.getMessage()
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoEntitiesPresent.class)
    public ResponseEntity<Object> handleNoEntitiesException(NoEntitiesPresent ex) {

        Map<String, Object> body = Map.of(
                STATUS, HttpStatus.NOT_FOUND,
                ERROR, "There are no beers stored"
        );

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {

        Map<String, Object> body = Map.of(
                STATUS, HttpStatus.INTERNAL_SERVER_ERROR,
                ERROR, ex.getMessage()
        );

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
