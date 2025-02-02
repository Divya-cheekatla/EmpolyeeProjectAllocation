package com.employeeproject.Allocation.Exception;

import com.employeeproject.Allocation.Model.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmployeeProjectNotAllocatedException.class)
    public ResponseEntity<APIResponse> resourceNotFoundException(EmployeeProjectNotAllocatedException e) {
        APIResponse apiResponse = new APIResponse(e.getMessage(), "FAILED");
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse> resourceNotFoundException(HttpMessageNotReadableException e) {
        APIResponse apiResponse = new APIResponse("Required request body is missing", "FAILED");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(APIExceptions.class)
    public ResponseEntity<APIResponse> resourceNotFoundException(APIExceptions e) {
        String message = e.getMessage();
        APIResponse apiResponse = new APIResponse(message, "FAILED");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Missing required header: " + ex.getHeaderName());
    }
}
