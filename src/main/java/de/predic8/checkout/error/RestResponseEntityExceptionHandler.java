package de.predic8.checkout.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { InterruptedException.class, ExecutionException.class, TimeoutException.class})
    protected ResponseEntity<Object> handleNoEventbus(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Can not send message to eventbus!",
                new HttpHeaders(), SERVICE_UNAVAILABLE, request);
    }

    @ExceptionHandler(value = { NoPriceException.class})
    protected ResponseEntity<Object> handleNoPrice(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "{ \"error\":\"Article with no price!\" }",
                new HttpHeaders(), BAD_REQUEST, request);
    }
}