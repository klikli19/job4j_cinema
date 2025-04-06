package ru.job4j_cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import ru.job4j_cinema.model.Ticket;
import ru.job4j_cinema.service.TicketService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    private TicketController ticketController;
    @Mock
    private TicketService mockTicketService;

    @BeforeEach
    public void initServices() {
        ticketController = new TicketController(
                mockTicketService
        );
    }

    @Test
    void whenBuyATicketThenGetIt() {

        var ticket = new Ticket(1, 1, 1, 1, 1);

        when(mockTicketService.save(ticket)).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var view = ticketController.buyTicket(ticket, model);

        assertThat(view).isEqualTo("tickets/one");
    }

    @Test
    void whenUserIsNotLoggedInGetsRedirectedToLoginPage() {
        var ticket = new Ticket(1, 1, 1, 1, 1);

        when(mockTicketService.save(ticket)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = ticketController.buyTicket(ticket, model);

        assertThat(view).isEqualTo("errors/404");
        assertThat(model.getAttribute("message"))
                .isEqualTo("Неверно. Попробуйте заново");
    }
}