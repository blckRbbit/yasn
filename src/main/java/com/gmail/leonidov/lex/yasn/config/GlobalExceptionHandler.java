package com.gmail.leonidov.lex.yasn.config;

import com.gmail.leonidov.lex.yasn.exceptions.YDataSetNotEnoughDataException;
import com.gmail.leonidov.lex.yasn.exceptions.YParameterNotFoundException;
import com.gmail.leonidov.lex.yasn.dto.YAppError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<YAppError> catchParameterNotFoundException(YParameterNotFoundException e) {
        log.error(String.valueOf(e));
        return new ResponseEntity<>(new YAppError(
                YAppError.YAppErrors.PARAMETER_NOT_FOUND.name(),
                e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<YAppError> catchDataSetNotEnoughDataException(YDataSetNotEnoughDataException e) {
        log.error(String.valueOf(e));
        return new ResponseEntity<>(new YAppError(
                YAppError.YAppErrors.NOT_ENOUGH_DATA.name(),
                e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
