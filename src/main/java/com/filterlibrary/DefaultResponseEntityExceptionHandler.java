package com.filterlibrary;

import com.filterlibrary.exception.AuthorizationBearerTokenException;
import com.filterlibrary.model.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LogManager.getLogger(DefaultResponseEntityExceptionHandler.class);

    @ExceptionHandler(AuthorizationBearerTokenException.class)
    public ResponseEntity<Object> handleCustomHeaderNotFoundException(AuthorizationBearerTokenException ex) {
        logger.error(ex.getMessage());
        return sendResponse(ex.getClass().toString(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        logger.error(ex.getMessage());
        return sendResponse(ex.getClass().toString(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<Object> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        logger.error(ex.getMessage());
        return sendResponse(ex.getClass().toString(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleMalformedJwtException(MalformedJwtException ex) {
        logger.error(ex.getMessage());
        return sendResponse(ex.getClass().toString(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException(SignatureException ex) {
        logger.error(ex.getMessage());
        return sendResponse(ex.getClass().toString(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error(ex.getMessage());
        return sendResponse(ex.getClass().toString(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> sendResponse(String message, String description, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(message, description, "CurrentDateTime.defaultFormat()", status.value());
        return new ResponseEntity<>(errorResponse, status);
    }
}
