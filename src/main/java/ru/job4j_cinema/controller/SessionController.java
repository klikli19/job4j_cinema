package ru.job4j_cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j_cinema.model.Ticket;
import ru.job4j_cinema.model.User;
import ru.job4j_cinema.service.FilmService;
import ru.job4j_cinema.service.SessionService;
import ru.job4j_cinema.service.HallService;


import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    private final FilmService filmService;

    private final HallService hallService;

    public SessionController(SessionService sessionService,
                             FilmService filmService,
                             HallService hallService) {
        this.sessionService = sessionService;
        this.filmService = filmService;
        this.hallService = hallService;
    }

    @GetMapping
    public String getAll(Model model) {

        model.addAttribute("sessiondtos",
                sessionService.getAllSessions());

        return "sessions/list";
    }

    @GetMapping("/{id}")

    public String showOneFilmSessionInfo(Model model,
                                         @PathVariable int id,
                                         HttpServletRequest request) {

        var ticket = new Ticket();

        var foundFilmSession = sessionService.findSessionById(id);
        var foundFilm = filmService.findById(foundFilmSession.getFilmId());
        var hall = hallService.getHallBySessionId(id);

        ticket.setSessionId(id);

        model.addAttribute("filmsessiondto", foundFilmSession);
        model.addAttribute("filmdto", foundFilm);
        model.addAttribute("rowCount", hall.getRowCount());
        model.addAttribute("placeCount", hall.getPlaceCount());
        model.addAttribute("ticket", ticket);

        var httpSession = request.getSession();

        var user = Optional.ofNullable((User) httpSession.getAttribute("user"));

        user.ifPresent(value -> ticket.setUserId(value.getId()));

        return "sessions/one";
    }
}
