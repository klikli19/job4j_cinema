package ru.job4j_cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j_cinema.model.User;
import ru.job4j_cinema.controller.UserController;
import ru.job4j_cinema.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private UserController userController;
    @Mock
    private UserService mockUserService;

    @BeforeEach
    public void initServices() {
        userController = new UserController(
                mockUserService
        );
    }

    @Test
    void whenRequestRegistrationPageThenGetIt() {

        assertThat(userController.getRegistrationPage())
                .isEqualTo("users/register");
    }

    @Test
    void whenRegistrationIsOk() {

        User user = new User(1, "fullName", "email", "password");
        when(mockUserService.save(user)).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(user, model);

        assertThat(view).isEqualTo("redirect:/sessions");
    }

    @Test
    void whenRegistrationFails() {

        User user = new User(1, "fullName", "email", "password");

        when(mockUserService.save(user)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.register(user, model);

        assertThat(view).isEqualTo("redirect:/users/register");
        assertThat(model.getAttribute("message"))
                .isEqualTo("Пользователь с таким email уже существует");
    }

    @Test
    void whenRequestLoginPageThenGetIt() {
        assertThat(userController.getLoginPage())
                .isEqualTo("users/login");
    }

    @Test
    void whenUserLogsInHeGoesToFilmSessionsPage() {

        User user = new User(1, "fullName", "email", "password");

        when(mockUserService.findByEmailAndPassword(user.getEmail(),
                        user.getPassword())).thenReturn(Optional.of(user));

        MockHttpServletRequest request = new MockHttpServletRequest();
        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);

        assertThat(view).isEqualTo("redirect:/sessions");

    }

    @Test
    void whenUserFailsToLogIn() {

        User user = new User(1, "fullName", "email", "password");

        when(mockUserService.findByEmailAndPassword(user.getEmail(),
                user.getPassword())).thenReturn(Optional.empty());

        MockHttpServletRequest request = new MockHttpServletRequest();

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);

        assertThat(view).isEqualTo("users/login");
        assertThat(model.getAttribute("error"))
                .isEqualTo("Логин или пароль введены неверно");
    }

    @Test
    void whenUserLogsOut() {
        MockHttpSession mockHttpSession = new MockHttpSession();
        assertThat(userController.logout(mockHttpSession))
                .isEqualTo("redirect:/users/login");
    }
}