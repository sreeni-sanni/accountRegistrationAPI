package com.assignment.accountRegistrationAPI.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.assignment.accountRegistrationAPI.model.ErrorDetails;

@RestControllerAdvice
public class GlobalExceptionAdvice {

	@ExceptionHandler(APIException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(APIException exception, WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
