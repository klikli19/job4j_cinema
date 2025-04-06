package ru.job4j_cinema.service;

import ru.job4j_cinema.dto.FileDto;
import ru.job4j_cinema.dto.FilmDto;
import ru.job4j_cinema.model.Film;
import ru.job4j_cinema.dto.FilmDto;
import ru.job4j_cinema.model.Film;

import java.util.Collection;

public interface FilmService {

    Film save(Film film, FileDto fileDto);

    void deleteById(int id);

    FilmDto findById(int id);

    Collection<Film> findAll();
}
