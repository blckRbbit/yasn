package com.gmail.leonidov.lex.yasn.services;

import au.com.bytecode.opencsv.CSVReader;
import com.gmail.leonidov.lex.yasn.beans.YTopHitsSpotifyBean;
import com.gmail.leonidov.lex.yasn.dto.YStatisticResponseDto;
import com.gmail.leonidov.lex.yasn.exceptions.YDataSetNotEnoughDataException;
import com.gmail.leonidov.lex.yasn.exceptions.YParameterNotFoundException;
import com.gmail.leonidov.lex.yasn.repositories.YTopHitsSpotifyRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YSpotifyStatisticService {

    private final YTopHitsSpotifyRepository repository;

    @Value("${yasn.http.request.path}")
    private  String path;

    public List<YStatisticResponseDto> show(String columnName, Integer year) {
        return prepared(columnName, year);
    }

    private List<YStatisticResponseDto> prepared(String columnName, Integer year) {
        fillDB();
        List<YStatisticResponseDto> result = new ArrayList<>();
        Collection<Set<?>> data = divideByDeciles(columnName, year);
        for (Set<?> decile: data) {
           result.add(
                   new YStatisticResponseDto(
                           decile.stream().sorted().collect(Collectors.toList()).get(0).toString(),
                           decile.stream().sorted().collect(Collectors.toList()).get(decile.size() - 1).toString(),
                           String.valueOf(decile.size())
                   )
           );
        }
        return result;
    }

    @SneakyThrows
    private void fillDB() {
        CSVReader reader = new CSVReader(new FileReader(path));
        List<YTopHitsSpotifyBean> models = new ArrayList<>();

        String[] line;
        long i = 0;
        while ((line = reader.readNext()) != null) {
            if (i == 0) {
                i++;
                continue;
            }
            i++;
            var duration = Long.parseLong(line[2]);
            var year = Integer.parseInt(line[4]);
            var popularity = Short.parseShort(line[5]);
            var danceability = Float.parseFloat(line[6]);
            var energy = Float.parseFloat(line[7]);
            var key = Short.parseShort(line[8]);
            models.add(
                    new YTopHitsSpotifyBean(
                            i, duration, year, popularity, danceability, energy, key
                    )
            );
        }
        repository.saveAll(models);
    }

    private Collection<Set<?>> divideByDeciles(String columnName, Integer year) {
        List<?> data = selectColumn(columnName, year);
        return findDeciles(data);
    }

    private List<Set<?>> findDeciles(List<?> data) {
        List<Set<?>> result = new ArrayList<>();
        if (data.size() > 9) {
            var decileDigit = 0;
            for (int i = 0; i < 10; i++) {
                List<?> decile =
                        Objects.requireNonNull(data)
                                .subList(decileDigit, calculateDecile(data.size(), i + 1));
                result.add(new HashSet<>(decile));
                decileDigit += decile.size();
            }
        } else {
            throw new YDataSetNotEnoughDataException("Длина выборки недостаточна для предоставления статистики");
        }
        return result;
    }

    private int calculateDecile(int size, double numberOfDecile) {
        return (int) Math.round((size * numberOfDecile) / 10);
    }

    private List<?> selectColumn(String columnName, Integer year) {
        switch (columnName) {
            case "duration_ms":
                return year != null ?
                        repository.getAllDurationFromYear(columnName, year) : repository.getAllDuration(columnName);

            case "year":
                return year != null ?
                        repository.getAllYearFromYear(columnName, year) : repository.getAllYear(columnName);

            case "popularity":
                return year != null ?
                        repository.getAllPopularityFromYear(columnName, year) : repository.getAllPopularity(columnName);

            case "danceability":
                return year != null ?
                        repository.getAllDanceabilityFromYear(columnName, year) : repository.getAllDanceability(columnName);

            case "energy":
                return year != null ?
                        repository.getAllEnergyFromYear(columnName, year) : repository.getAllEnergy(columnName);

            case "key":
                return year != null ?
                        repository.getAllKeyFromYear(columnName, year): repository.getAllKey(columnName);

            default: throw new YParameterNotFoundException("Невозможно найти статистику по указанному параметру!");
        }
    }

}
