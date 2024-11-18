package com.multiversa.usuario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Capturar erros de validação
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>>
  handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> erros = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      erros.put(error.getField(), error.getDefaultMessage());
    }
    return ResponseEntity.badRequest().body(erros);
  }
  // Capturar erros de usuário não encontrado
  @ExceptionHandler(UsuarioNaoEncontradoException.class)
  public ResponseEntity<String>
  handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
  // Capturar outras exceções genéricas
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception ex) {
    return
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + ex.getMessage());
  }
}