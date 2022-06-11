package com.gmail.leonidov.lex.yasn.repositories;

import com.gmail.leonidov.lex.yasn.beans.YTopHitsSpotifyBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YTopHitsSpotifyRepository extends JpaRepository<YTopHitsSpotifyBean, Long> {

    @Query(value = "SELECT duration_ms FROM statistic ORDER BY duration_ms", nativeQuery = true)
    List<Long> getAllDuration(String columnName);

    @Query(value = "SELECT year FROM statistic ORDER BY year", nativeQuery = true)
    List<Integer> getAllYear(String columnName);

    @Query(value = "SELECT popularity FROM statistic ORDER BY popularity", nativeQuery = true)
    List<Short> getAllPopularity(String columnName);

    @Query(value = "SELECT danceability FROM statistic ORDER BY danceability", nativeQuery = true)
    List<Float> getAllDanceability(String columnName);

    @Query(value = "SELECT energy FROM statistic ORDER BY energy", nativeQuery = true)
    List<Float> getAllEnergy(String columnName);

    @Query(value = "SELECT key FROM statistic ORDER BY key", nativeQuery = true)
    List<Short> getAllKey(String columnName);

    @Query(value = "SELECT duration_ms FROM statistic WHERE statistic.year = :year ORDER BY :columnName ",
            nativeQuery = true)
    List<Long> getAllDurationFromYear(String columnName, Integer year);

    @Query(value = "SELECT year FROM statistic WHERE statistic.year = :year ORDER BY :columnName",
            nativeQuery = true)
    List<Integer> getAllYearFromYear(String columnName, Integer year);

    @Query(value = "SELECT popularity FROM statistic WHERE statistic.year = :year ORDER BY :columnName",
            nativeQuery = true)
    List<Short> getAllPopularityFromYear(String columnName, Integer year);

    @Query(value = "SELECT danceability FROM statistic WHERE statistic.year = :year ORDER BY :columnName",
            nativeQuery = true)
    List<Float> getAllDanceabilityFromYear(String columnName, Integer year);

    @Query(value = "SELECT energy FROM statistic WHERE statistic.year = :year ORDER BY :columnName",
            nativeQuery = true)
    List<Float> getAllEnergyFromYear(String columnName, Integer year);

    @Query(value = "SELECT key FROM statistic WHERE statistic.year = :year ORDER BY :columnName",
            nativeQuery = true)
    List<Short> getAllKeyFromYear(String columnName, Integer year);

}
