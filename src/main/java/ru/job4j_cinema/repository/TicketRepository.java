package ru.job4j_cinema.repository;

import ru.job4j_cinema.model.Ticket;
import ru.job4j_cinema.model.Ticket;

import java.util.Optional;

public interface TicketRepository {

    Optional<Ticket> save(Ticket ticket);
}
