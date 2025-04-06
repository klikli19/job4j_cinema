package ru.job4j_cinema.repository;

import ru.job4j_cinema.dto.SessionDto;
import ru.job4j_cinema.model.Session;

import java.util.Collection;

public interface SessionRepository {

    Collection<SessionDto> getAllSessions();

    SessionDto getSessionById(int id);

    boolean deleteById(int id);

    Session save(Session session);
}
