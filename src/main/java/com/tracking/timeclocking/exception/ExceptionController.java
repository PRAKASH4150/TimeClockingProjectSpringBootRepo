package com.tracking.timeclocking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	 @ExceptionHandler(value = NoUserFoundException.class)
	public ResponseEntity<Object> exceptionOne(NoUserFoundException exception) 
	{
		 return new ResponseEntity<>("No user found", HttpStatus.NOT_FOUND);
	}
	 
	 @ExceptionHandler(value = NoRecordFoundException.class)
		public ResponseEntity<Object> exceptionOne(NoRecordFoundException exception) 
		{
			 return new ResponseEntity<>("No records found", HttpStatus.NOT_FOUND);
		}
	
}