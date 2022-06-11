package com.gmail.leonidov.lex.yasn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YAppError {
    private String code;
    private String message;

    public enum YAppErrors {
        PARAMETER_NOT_FOUND, NOT_ENOUGH_DATA
    }
}