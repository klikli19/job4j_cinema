package ru.job4j.cinema.service.user;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.user.UserRepository;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    private final UserRepository sql2oUserRepository;

    public SimpleUserService(UserRepository sql2oUserRepository) {
        this.sql2oUserRepository = sql2oUserRepository;
    }

    @Override
    public Optional<User> save(User user) {
        return sql2oUserRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return sql2oUserRepository.findByEmailAndPassword(email, password);
    }
}
