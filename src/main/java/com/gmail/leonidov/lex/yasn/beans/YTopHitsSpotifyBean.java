package com.gmail.leonidov.lex.yasn.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "statistic")
public class YTopHitsSpotifyBean {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "duration_ms")
    private Long duration;

    @Column(name = "year")
    private Integer year;

    @Column(name = "popularity")
    private Short popularity;

    @Column(name = "danceability")
    private Float danceability;

    @Column(name = "energy")
    private Float energy;

    @Column(name = "key")
    private Short key;

    @Override
    public String toString() {
        return String.format
                ("YTopHitsSpotifyBean: {id: %s, duration: %s, year: %s, popularity: %s, " +
                        "danceability: %s, energy: %s, key: %s}",
                        id, duration, year, popularity, danceability, energy, key
                );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        YTopHitsSpotifyBean bean = (YTopHitsSpotifyBean) obj;
        return duration.equals(bean.getDuration())
                && year.equals(bean.getYear())
                && popularity.equals(bean.getPopularity())
                && danceability.equals(bean.getDanceability())
                && energy.equals(bean.getEnergy())
                && key.equals(bean.getKey());
    }

    @Override
    public int hashCode()
    {
        final int PRIME = 31;
        int result = 1;
        result = (PRIME * (
                result + (year == null ? 0: year.hashCode())
                        + (key == null ? 0 : key.hashCode())
                        + (popularity == null ? 0 : popularity.hashCode())
                        + (danceability == null ? 0 : danceability.hashCode())
                        + (duration == null ? 0 : duration.hashCode())
                        + (energy == null ? 0 : energy.hashCode())
            )
        );
        return result;
    }
}
