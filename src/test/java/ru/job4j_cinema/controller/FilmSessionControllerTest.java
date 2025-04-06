package ru.job4j_cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;
import ru.job4j_cinema.controller.SessionController;
import ru.job4j_cinema.dto.SessionDto;
import ru.job4j_cinema.model.Hall;
import ru.job4j_cinema.service.FilmService;
import ru.job4j_cinema.service.HallService;
import ru.job4j_cinema.service.SessionService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilmSessionControllerTest {

    private SessionController sessionController;

    @Mock
    private SessionService sessionService;

    @Mock
    private FilmService mockFilmService;

    @Mock
    private HallService mockHallService;

    @BeforeEach
    public void initServices() {
        sessionController = new SessionController(
                sessionService,
                mockFilmService,
                mockHallService
        );
    }

    @Test
    void whenRequestAllFilmSessionsThenReceiveThem() {
        var filmSessionDto1 = new SessionDto(
                1,
                1,
                "1",
                "description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "hallName",
                1000,
                1);

        var filmSessionDto2 = new SessionDto(
                2,
                2,
                "2",
                "description2",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "hallName2",
                2000,
                2);

        var expectedFilmSessions = List.of(filmSessionDto1, filmSessionDto2);

        when(sessionService.getAllSessions()).thenReturn(expectedFilmSessions);

        var model = new ConcurrentModel();
        var view = sessionController.getAll(model);
        var actualFilmSessions = model.getAttribute("sessiondtos");

        assertThat(view).isEqualTo("sessions/list");
        assertThat(expectedFilmSessions).isEqualTo(actualFilmSessions);
    }

    @Test
    void whenRequestAFilmSessionThenReceiveIt() {
        var expectedFilmSessionDto = new SessionDto(
                1,
                1,
                "1",
                "description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "hallName",
                1000,
                1);

        when(sessionService.findSessionById(expectedFilmSessionDto.getId()))
                .thenReturn(expectedFilmSessionDto);

        when(mockHallService.getHallBySessionId(1)).thenReturn(
                new Hall(1,
                        "test",
                        5,
                        5,
                        "testHall")
        );
        var request = new MockHttpServletRequest();
        var model = new ConcurrentModel();
        var view = sessionController.showOneFilmSessionInfo(
                model,
                1,
                request);
        var actualFilmSessionDto = model.getAttribute("sessiondto");

        assertThat(view).isEqualTo("sessions/one");
        assertThat(actualFilmSessionDto).isEqualTo(expectedFilmSessionDto);
    }

}