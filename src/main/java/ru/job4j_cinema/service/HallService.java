package ru.job4j_cinema.service;

import ru.job4j_cinema.model.Hall;
import ru.job4j_cinema.model.Hall;

public interface HallService {

    Hall getHallById(int id);

    Hall getHallBySessionId(int id);
}
