package ru.melonhell.violenceuniverse.server.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.melonhell.violenceuniverse.server.user.entity.User;
import ru.melonhell.violenceuniverse.server.user.exceptions.BlankPasswordException;
import ru.melonhell.violenceuniverse.server.user.exceptions.InvalidLoginException;
import ru.melonhell.violenceuniverse.server.user.exceptions.UserAlreadyExistException;
import ru.melonhell.violenceuniverse.server.user.port.PasswordEncoder;
import ru.melonhell.violenceuniverse.server.user.repository.UserRepository;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User signIn(String login, String password) {
        User user = repository.findUserByLogin(login);
        if (user == null)
            throw new InvalidLoginException();

        boolean validPassword = passwordEncoder.verify(user.getPasswordHash(), password);

        if (!validPassword)
            throw new InvalidLoginException();
        repository.refreshLastLogin(user.getId());
        user.setLastLogin(Instant.now());

        return user;
    }

    public User signUp(String login, String password) {
        if (password.isBlank())
            throw new BlankPasswordException();

        boolean exist = repository.existByLogin(login);
        if (exist)
            throw new UserAlreadyExistException();

        User user = new User();
        user.setLogin(login);
        user.setPasswordHash(passwordEncoder.encode(password));

        user = repository.createUser(user);

        return user;
    }
}
