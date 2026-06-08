package com.project.MockInterviewScheduler.exceptions;

import com.project.MockInterviewScheduler.dtos.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException e){
        return ResponseEntity.status(NOT_FOUND)
                .body(new ApiResponse(e.getMessage(),null));
    }

    @ExceptionHandler(ResourceAlreadyException.class)
    public ResponseEntity<?> handleAlreadyExists(ResourceAlreadyException e){
        return ResponseEntity.status(CONFLICT)
                .body(new ApiResponse(e.getMessage(),null));
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<?> handleUnauthorized(UnauthorizedActionException e){
        return ResponseEntity.status(FORBIDDEN)
                .body(new ApiResponse(e.getMessage(),null));
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> handleInvalid(InvalidRequestException e){
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(e.getMessage(),null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOthers(Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(e.getMessage(),null));
    }
}
