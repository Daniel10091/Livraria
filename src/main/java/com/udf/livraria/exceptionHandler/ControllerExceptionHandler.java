package com.udf.livraria.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.udf.livraria.domain.dto.ErrorDto;
import com.udf.livraria.domain.exception.LivroAlreadyExistException;
import com.udf.livraria.domain.exception.LivroNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler({ LivroNotFoundException.class, LivroNotFoundException.class })
  public ResponseEntity<ErrorDto> handleLivroNotFound(RuntimeException ex, WebRequest request) {
    var errorDto = new ErrorDto(404, "Usuário não encontrado", ex.getMessage(), request.getDescription(false));
    return ResponseEntity.status(errorDto.getStatus()).body(errorDto);
  }

  @ExceptionHandler(LivroAlreadyExistException.class)
  public ResponseEntity<ErrorDto> handleLivroAlreadyExist(RuntimeException ex, WebRequest request) {
    var errorDto = new ErrorDto(409, "Usuário já existe", ex.getMessage(), request.getDescription(false));
    return ResponseEntity.status(errorDto.getStatus()).body(errorDto);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorDto> handleOtherExceptions(RuntimeException ex, WebRequest request) {
    var errorDto = new ErrorDto(500, "Erro interno do servidor", ex.getMessage(), request.getDescription(false));
    return ResponseEntity.status(errorDto.getStatus()).body(errorDto);
  }

}
