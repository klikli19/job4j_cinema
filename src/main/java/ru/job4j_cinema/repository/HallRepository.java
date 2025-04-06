package ru.job4j_cinema.repository;

import ru.job4j_cinema.model.Hall;

public interface HallRepository {

    Hall getHallById(int id);

    Hall getHallBySessionId(int id);
}
