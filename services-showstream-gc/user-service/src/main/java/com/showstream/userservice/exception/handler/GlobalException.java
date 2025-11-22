package com.showstream.userservice.exception.handler;


import com.showstream.userservice.dto.CustomError;
import com.showstream.userservice.dto.ErrorResponse;
import com.showstream.userservice.exception.PasswordNotValidException;
import com.showstream.userservice.exception.RoleNotFoundException;
import com.showstream.userservice.exception.UserAlreadyExistsException;
import com.showstream.userservice.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

/**
 * Centralized exception handler for mapping custom business exceptions
 * (thrown from the Service layer) to appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalException {


    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex,
                                                                             HttpServletRequest request)
    {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.getReasonPhrase(),
                status.value(),
                "Role Not Found",
                ex.getMessage(),
                request.getRequestURI() // UPDATED PATH RETRIEVAL
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * Handles UserAlreadyExistsException. Maps to HTTP 409 Conflict
     * (unique constraint violation on username/email).
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex,
            HttpServletRequest request) { // UPDATED PARAMETER

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.getReasonPhrase(),
                status.value(),
                "User Already register",
                ex.getMessage(),
                request.getRequestURI() // UPDATED PATH RETRIEVAL
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Sets the status code to 400
    public ErrorResponse handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        // You can iterate over ex.getBindingResult().getAllErrors()
        // to collect all validation failure messages for a detailed response.
        String detailedMessage = "Validation failed for one or more fields.";

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ErrorResponse(
                LocalDateTime.now(),
                status.getReasonPhrase(),
                status.value(),
                "Bad Request",
                detailedMessage,
                request.getRequestURI()
        );
    }

    /**
     * Generic handler for any unexpected exceptions not specifically handled above.
     * Maps to HTTP 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(
            Exception ex,
            HttpServletRequest request) { // UPDATED PARAMETER

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.getReasonPhrase(),
                status.value(),
                "Internal Server Error",
                "An unexpected error occurred: " + ex.getMessage(),
                request.getRequestURI() // UPDATED PATH RETRIEVAL
        );
        return new ResponseEntity<>(errorResponse, status);
    }

        @ExceptionHandler(UserNotFoundException.class)
        protected ResponseEntity<Object> handleUserNotFoundException(final UserNotFoundException ex) {
            CustomError customError = CustomError.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .header(CustomError.Header.API_ERROR.getName())
                    .message(ex.getMessage())
                    .build();

            return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
        }

    @ExceptionHandler(PasswordNotValidException.class)
    protected ResponseEntity<CustomError> handlePasswordNotValidException(final PasswordNotValidException ex) {
        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(CustomError.Header.VALIDATION_ERROR.getName())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }

}
