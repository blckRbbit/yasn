package com.gmail.leonidov.lex.yasn;

import com.gmail.leonidov.lex.yasn.exceptions.YDataSetNotEnoughDataException;
import com.gmail.leonidov.lex.yasn.exceptions.YParameterNotFoundException;
import com.gmail.leonidov.lex.yasn.repositories.YTopHitsSpotifyRepository;
import com.gmail.leonidov.lex.yasn.services.YSpotifyStatisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class YSpotifyStatisticServiceTest {

    @Autowired
    private YSpotifyStatisticService service;

    @MockBean
    private YTopHitsSpotifyRepository repository;

    @Test
    void testShowDurationStatistic() {
        String columnName = "duration_ms";
        String result = "[{\"min\" : 1, \"max\" : 1, \"count\" : 1}, {\"min\" : 2, \"max\" : 2, \"count\" : 1}, " +
                "{\"min\" : 3, \"max\" : 3, \"count\" : 1}, {\"min\" : 4, \"max\" : 4, \"count\" : 1}, " +
                "{\"min\" : 5, \"max\" : 5, \"count\" : 1}, {\"min\" : 6, \"max\" : 6, \"count\" : 1}, " +
                "{\"min\" : 7, \"max\" : 7, \"count\" : 1}, {\"min\" : 8, \"max\" : 8, \"count\" : 1}, " +
                "{\"min\" : 9, \"max\" : 9, \"count\" : 1}, {\"min\" : 10, \"max\" : 10, \"count\" : 1}]";
        List<Long> data = fillResultData(11);
        doReturn(data).when(repository).getAllDuration(columnName);
        assertEquals(String.valueOf(service.show(columnName, null)), result);
    }

    @Test
    void testShowDurationStatisticFromYear() {
        String columnName = "duration_ms";
        int year = 1;
        String result = "[{\"min\" : 1, \"max\" : 1, \"count\" : 1}, {\"min\" : 2, \"max\" : 2, \"count\" : 1}, " +
                "{\"min\" : 3, \"max\" : 3, \"count\" : 1}, {\"min\" : 4, \"max\" : 4, \"count\" : 1}, " +
                "{\"min\" : 5, \"max\" : 5, \"count\" : 1}, {\"min\" : 6, \"max\" : 6, \"count\" : 1}, " +
                "{\"min\" : 7, \"max\" : 7, \"count\" : 1}, {\"min\" : 8, \"max\" : 8, \"count\" : 1}, " +
                "{\"min\" : 9, \"max\" : 9, \"count\" : 1}, {\"min\" : 10, \"max\" : 10, \"count\" : 1}]";
        List<Long> data = fillResultData(11);
        doReturn(data).when(repository).getAllDurationFromYear(columnName, year);
        assertEquals(String.valueOf(service.show(columnName, year)), result);
    }

    @Test
    void testNegativeShowDurationStatistic() {
        String columnName = "duration_ms";
        List<Long> data = fillResultData(10);
        doReturn(data).when(repository).getAllDuration(columnName);
        assertThrows(YDataSetNotEnoughDataException.class, () -> service.show(columnName, null));
    }

    @Test
    void testNegativeShowUndefinedColumnStatistic() {
        String columnName = "abc";
        assertThrows(YParameterNotFoundException.class, () -> service.show(columnName, null));
    }

    private List<Long> fillResultData(int count) {
        List<Long> data = new ArrayList<>();
        for (int i = 1; i < count; i++) {
            data.add((long) i);
        }
        return data;
    }

}
