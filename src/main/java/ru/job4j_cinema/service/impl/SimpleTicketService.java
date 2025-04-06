package ru.job4j_cinema.service.impl;

import org.springframework.stereotype.Service;
import ru.job4j_cinema.model.Ticket;
import ru.job4j_cinema.repository.TicketRepository;
import ru.job4j_cinema.service.TicketService;

import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {

    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Optional<Ticket> save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}
