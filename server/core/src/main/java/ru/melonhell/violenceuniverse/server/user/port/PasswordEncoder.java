package ru.melonhell.violenceuniverse.server.user.port;

public interface PasswordEncoder {
    String encode(String str);

    boolean verify(String hash, String str);
}
