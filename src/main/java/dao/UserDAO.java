package dao;

import model.User;

public interface UserDAO {

    void save(User user);

    User getByLogin(String login);

    User getByLoginAndPassword(String login, String password);
}
