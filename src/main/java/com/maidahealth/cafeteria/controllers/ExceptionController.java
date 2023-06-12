package com.maidahealth.cafeteria.controllers;

import com.maidahealth.cafeteria.dtos.StandardErrorDto;
import com.maidahealth.cafeteria.exceptions.InvalidCategoryException;
import com.maidahealth.cafeteria.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(InvalidCategoryException.class)
    public ResponseEntity<StandardErrorDto> invalidCategoryException(InvalidCategoryException invalidCategoryException) {
        StandardErrorDto standardErrorDto = new StandardErrorDto();
        standardErrorDto.setCode(HttpStatus.CONFLICT.value());
        standardErrorDto.setStatus(HttpStatus.CONFLICT.name());
        standardErrorDto.setMessage(invalidCategoryException.getMessage());
        standardErrorDto.setLocalDateTime(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(standardErrorDto);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<StandardErrorDto> productNotFoundException(ProductNotFoundException productNotFoundException) {
        StandardErrorDto standardErrorDto = new StandardErrorDto();
        standardErrorDto.setCode(HttpStatus.NOT_FOUND.value());
        standardErrorDto.setStatus(HttpStatus.NOT_FOUND.name());
        standardErrorDto.setMessage(productNotFoundException.getMessage());
        standardErrorDto.setLocalDateTime(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardErrorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorDto> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        StandardErrorDto standardErrorDto = new StandardErrorDto();
        standardErrorDto.setCode(HttpStatus.BAD_REQUEST.value());
        standardErrorDto.setStatus(HttpStatus.BAD_REQUEST.name());
        standardErrorDto.setMessage(methodArgumentNotValidException.getBindingResult().getAllErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.toList()).toString());
        standardErrorDto.setLocalDateTime(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardErrorDto);
    }

}
