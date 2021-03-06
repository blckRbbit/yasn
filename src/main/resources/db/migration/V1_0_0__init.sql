DROP TABLE IF EXISTS statistic

CREATE TABLE IF NOT EXISTS statistic
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    duration_ms BIGINT,
    year INT,
    popularity SMALLINT,
    danceability REAL,
    energy REAL,
    key SMALLINT
);