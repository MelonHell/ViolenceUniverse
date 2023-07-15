package ru.melonhell.violenceuniverse.server.user.repository;

import org.jetbrains.annotations.Nullable;
import ru.melonhell.violenceuniverse.server.user.entity.User;

public interface UserRepository {
    boolean existByLogin(String login);

    @Nullable
    User findUserById(String id);

    @Nullable
    User findUserByLogin(String login);

    User createUser(User user);

    void refreshLastLogin(String id);
}
