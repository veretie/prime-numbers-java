package uk.co.mits4u.primes.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uk.co.mits4u.primes.api.ExceptionData;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleGenericException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionData(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
