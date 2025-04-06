package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.SessionDto;

import java.util.Collection;

public interface SessionService {
    Collection<SessionDto> getAllSessions();

    SessionDto findSessionById(int id);

    boolean deleteById(int id);

 }
