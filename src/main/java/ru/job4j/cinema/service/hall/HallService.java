package ru.job4j.cinema.service.hall;

import ru.job4j.cinema.model.Hall;

public interface HallService {

    Hall getHallById(int id);

    Hall getHallBySessionId(int id);
}
