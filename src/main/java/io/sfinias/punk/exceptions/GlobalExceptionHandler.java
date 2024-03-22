package io.sfinias.punk.exceptions;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {

        Map<String, Object> body = Map.of(
                "status", ex.getStatusCode().toString(),
                "error", ex.getMessage()
        );

        return new ResponseEntity<>(body, ex.getStatusCode());
    }
}
