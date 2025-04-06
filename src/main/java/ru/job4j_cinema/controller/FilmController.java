package ru.job4j_cinema.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j_cinema.service.FilmService;

@Controller
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public String getAllFilms(Model model) {
        model.addAttribute("film",
                filmService.findAll());
        return "films/list";
    }

    @GetMapping("/{id}")
    public String showOneFilmInfo(Model model,
                                  @PathVariable int id) {
        var foundFilm = filmService.findById(id);

        if (foundFilm == null) {
            model.addAttribute("message",
                    "Фильм отсутствует");
            return "errors/404";
        }

        model.addAttribute("filmdto", foundFilm);

        return "films/one";
    }
}
