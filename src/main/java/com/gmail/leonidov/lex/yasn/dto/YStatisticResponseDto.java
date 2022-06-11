package com.gmail.leonidov.lex.yasn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YStatisticResponseDto {
    private String min;
    private String max;
    private String count;

    public String toString() {
        return String.format("{\"min\" : %s, \"max\" : %s, \"count\" : %s}", min, max, count);
    }
}
