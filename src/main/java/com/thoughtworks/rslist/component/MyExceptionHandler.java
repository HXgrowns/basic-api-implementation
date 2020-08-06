package com.thoughtworks.rslist.component;

import com.thoughtworks.rslist.exception.CommonError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity exceptionHandler(RuntimeException e) {
        CommonError commonError = new CommonError();
        commonError.setError(e.getMessage());

        return ResponseEntity.badRequest().body(commonError);
    }

}
