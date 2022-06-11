package com.gmail.leonidov.lex.yasn.controllers;

import com.gmail.leonidov.lex.yasn.dto.YStatisticResponseDto;
import com.gmail.leonidov.lex.yasn.services.YSpotifyStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class YSpotifyStatisticController {

    private final YSpotifyStatisticService service;

    @GetMapping
    public List<YStatisticResponseDto> get(@RequestParam("colname") String columnName,
                                           @RequestParam(name = "year", required = false) Integer year) {
        return service.show(columnName, year);
    }
}
