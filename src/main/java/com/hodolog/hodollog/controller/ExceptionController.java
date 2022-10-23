package com.hodolog.hodollog.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hodolog.hodollog.dto.ErrorResponse;
import com.hodolog.hodollog.exception.BlogException;
import com.hodolog.hodollog.exception.InvalidRequest;
import com.hodolog.hodollog.exception.PostNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ExceptionController {

//    @ResponseStatus(HttpStatus.BAD_REQUEST) // 응답에 보낼 것  -> 나중에는 실제 번호를 맞게 넣어야 한다.
//    @ResponseBody
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
//        // TODO : 이렇게 처리를 하면 에러 내용의 일관성이 부족해짐. 이것에 대해서는 별도의 처리가 필요함. -> 그리고 서비스 기획시 프론트와 백엔드간 협의가 필요한 사항임
//        ErrorResponse response =  new ErrorResponse("400", "잘못된 요청입니다.");
//        for(FieldError fieldError : e.getFieldErrors()) {
//            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
//        }
//        return response;
//    }

    @ResponseBody
    @ExceptionHandler(BlogException.class)
    public ResponseEntity<ErrorResponse> ErrorResponse (BlogException e){

        int statusCode = e.statusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();


        return ResponseEntity.status(statusCode)
                .body(body);
    }
}
