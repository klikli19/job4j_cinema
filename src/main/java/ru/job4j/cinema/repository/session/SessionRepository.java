package ru.job4j.cinema.repository.session;

import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.dto.SessionDto;

import java.util.Collection;

public interface SessionRepository {

    Collection<SessionDto> getAllSessions();

    SessionDto getSessionById(int id);

    boolean deleteById(int id);

    Session save(Session session);
}
