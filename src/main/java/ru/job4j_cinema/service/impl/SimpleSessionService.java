package ru.job4j_cinema.service.impl;

import org.springframework.stereotype.Service;
import ru.job4j_cinema.dto.SessionDto;
import ru.job4j_cinema.repository.SessionRepository;
import ru.job4j_cinema.service.SessionService;

import java.util.Collection;

@Service
public class SimpleSessionService implements SessionService {

    private final SessionRepository sessionRepository;


    public SimpleSessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Collection<SessionDto> getAllSessions() {
        return sessionRepository.getAllSessions();
    }

    public SessionDto findSessionById(int id) {
        return sessionRepository.getSessionById(id);
    }

    public boolean deleteById(int id) {
        return sessionRepository.deleteById(id);
    }

}
