package service;

import model.User;

public interface UserService {

    User save(String login, String password);

    User getByLoginAndPassword(String login, String password);

    boolean isInvalidUser(String login, String password);

    String invalidUser(String login, String password);
}
