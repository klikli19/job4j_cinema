package ru.job4j.cinema.repository.film;

import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;

import java.util.Collection;

public interface FilmRepository {

    Film save(Film film);

    boolean deleteById(int id);

    FilmDto findById(int id);

    Collection<Film> findAllFilms();
}
