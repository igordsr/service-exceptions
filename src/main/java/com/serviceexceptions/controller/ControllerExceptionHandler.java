package com.serviceexceptions.controller;

import com.serviceexceptions.modal.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<CustomException> validationRegistroNaoEncontradoException(RegistroNaoEncontradoException err, HttpServletRequest httpServletRequest) {
        return this.toCustomException(err, httpServletRequest);
    }

    @ExceptionHandler(RegistroJaExisteException.class)
    public ResponseEntity<CustomException> validationRegistroJaExisteException(RegistroJaExisteException err, HttpServletRequest httpServletRequest) {
        return this.toCustomException(err, httpServletRequest);
    }

    @ExceptionHandler(EntidadeNaoProcessavelException.class)
    public ResponseEntity<CustomException> validationEntidadeNaoProcessavelException(EntidadeNaoProcessavelException err, HttpServletRequest httpServletRequest) {
        return this.toCustomException(err, httpServletRequest);
    }

    @ExceptionHandler(SolicitacaoInvalidaException.class)
    public ResponseEntity<CustomException> validationSolicitacaoInvalidaException(SolicitacaoInvalidaException err, HttpServletRequest httpServletRequest) {
        return this.toCustomException(err, httpServletRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomException> validationMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest) {
        final List<String> mensagens = new ArrayList<>();
        final SolicitacaoInvalidaException err = new SolicitacaoInvalidaException();
        for (FieldError f : exception.getBindingResult().getFieldErrors()) {
            String format = String.format("[%s] - %s", f.getField(), f.getDefaultMessage());
            mensagens.add(format);
        }
        err.setDetails(mensagens);
        return this.toCustomException(err, httpServletRequest);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomException> validationDataIntegrityViolationException(DataIntegrityViolationException exception, WebRequest request) {
        EntidadeNaoProcessavelException err = new EntidadeNaoProcessavelException();
        return ResponseEntity.status(err.getCode()).body(err);
    }


    private ResponseEntity<CustomException> toCustomException(CustomException err, HttpServletRequest httpServletRequest) {
        err.setPath(httpServletRequest.getRequestURI());
        return ResponseEntity.status(err.getCode()).body(err);
    }

}