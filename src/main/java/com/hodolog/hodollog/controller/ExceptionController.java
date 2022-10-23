package com.hodolog.hodollog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 응답에 보낼 것  -> 나중에는 실제 번호를 맞게 넣어야 한다.
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> invalidRequestHandler(MethodArgumentNotValidException e){
        FieldError fieldError = e.getFieldError();
        String field = fieldError.getField();
        String message = fieldError.getDefaultMessage();

        Map<String, String> response  = new HashMap<>();
        response.put(field,message);
        return response;

    }
}
