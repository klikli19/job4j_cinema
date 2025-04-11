package ru.job4j.cinema.repository;

import org.junit.jupiter.api.*;

import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.film.Sql2oFilmRepository;
import ru.job4j.cinema.repository.session.Sql2oSessionRepository;


import java.time.LocalDateTime;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class Sql2oSessionRepositoryTest {

    private static Sql2oSessionRepository sql2oSessionRepository;

    private static Sql2oFilmRepository sql2oFilmRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oSessionRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oSessionRepository = new Sql2oSessionRepository(sql2o);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    @AfterEach
    public void clearFilmSessions() {

        var sessions = sql2oSessionRepository.getAllSessions();
        sessions.forEach(Session ->
                sql2oSessionRepository.deleteById(Session.getId()));
    }

    @Test
    public void whenSaveFilmSessionThenGetItById() {

        var savedFilm = sql2oFilmRepository.save(new Film(0,
                "name",
                "desc",
                2001,
                1,
                1,
                1,
                1));

        var filmSession = sql2oSessionRepository.save(
                new Session(
                        0,
                        savedFilm.getId(),
                        1,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        1500)
        );

        assertThat(sql2oSessionRepository
                .getSessionById(filmSession.getId()))
                .isNotNull();
    }

    @Test
    public void whenGettingNonExistingSessionIdThenReceiveNull() {

        assertThat(sql2oSessionRepository.getSessionById(1000))
                .isNull();
    }
}

