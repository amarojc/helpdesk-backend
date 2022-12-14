package com.amaro.helpdesk.resource.execptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amaro.helpdesk.services.exceptions.ConstraintViolationException;
import com.amaro.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.amaro.helpdesk.services.exceptions.ObjectNotFoundException;

//Classe de manipulação de recursos
@ControllerAdvice
public class ResourceExceptionHandler {
	
	//Método manipulador de excessões para objetos nullos
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex, 
			HttpServletRequest request){
		
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), 
				"Object Not Found", ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		
	}

	//Método manipulador de excessões de campos unique
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex, 
			HttpServletRequest request){
		
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), 
				"Violação de dados", ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		
	}
	
	//Método manipulador de exceções de campos obrigatórios
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex, 
			HttpServletRequest request){
		
		ValidationError errors = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), 
				"Validation error", "Erro na validação dos campos", request.getRequestURI());
		
		//Varrendo os erros e adicionando no método error da classe ValidationErrors
		for(FieldError x : ex.getBindingResult().getFieldErrors()) {
			errors.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		
	}
	
	//Método manipular de exceções do campo CPF
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> constraintViolationException(ConstraintViolationException ex, HttpServletRequest request){

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), 
				"Validation error", ex.getMessage(), request.getRequestURI());
	
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
}
