package com.lino.dscatalog.controller.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lino.dscatalog.services.exceptions.ResourceNotFoundExceptions;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundExceptions.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundExceptions e, HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

	}

//	@ExceptionHandler(DatabaseException.class)
//	public ResponseEntity<StandardError> database(EntityNotFoundException e, HttpServletRequest request) {
//		StandardError err = new StandardError();
//		err.setTimestamp(Instant.now());
//		err.setStatus(HttpStatus.BAD_REQUEST.value());
//		err.setError("Database exception");
//		err.setMessage(e.getMessage());
//		err.setPath(request.getRequestURI());
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
//
//	}
}