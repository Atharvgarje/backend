package com.learning.backend.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.learning.backend.BackendApplication;
import com.learning.backend.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final BackendApplication backendApplication;

    private final UserRepository userRepository;

    GlobalExceptionHandler(UserRepository userRepository, BackendApplication backendApplication) {
        this.userRepository = userRepository;
        this.backendApplication = backendApplication;
    }
	
    @ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntime(RuntimeException ex)
	{
		Map<String,Object> error = new HashMap<>();
		
		error.put("timestamp", LocalDateTime.now());
		error.put("Status", HttpStatus.BAD_REQUEST.value());
		error.put("error", ex.getMessage());
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, Object> error = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err -> {
            fieldErrors.put(err.getField(), err.getDefaultMessage());
        });

        error.put("timestamp", LocalDateTime.now());
        error.put("status", 400);
        error.put("errors", fieldErrors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex)
    {
    	Map<String, Object> error = new HashMap<String, Object>();
    	error.put("timestamp", LocalDateTime.now());
    	error.put("status", 404);
    	error.put("error", ex.getMessage());
    	
    	return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", 500);
        error.put("error", "Something went wrong");
        error.put("details", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
