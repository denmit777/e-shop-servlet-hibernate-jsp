package service.impl;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import model.User;
import service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class.getName());

    private static final String FIELD_IS_EMPTY = "Login or password shouldn't be empty";
    private static final String INVALID_FIELD = "Login or password shouldn't be less than 4 symbols";
    private static final String USER_IS_PRESENT = "User with login {} is already present";

    private final UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
    }

    @Override
    public User save(String login, String password) {
        User user = new User();

        user.setLogin(login);
        user.setPassword(password);

        userDAO.save(user);

        LOGGER.info("New user : {}", user);

        return user;
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        return userDAO.getByLoginAndPassword(login, password);
    }

    @Override
    public boolean isInvalidUser(String login, String password) {
        return login.length() < 4 || password.length() < 4 || isUserPresent(login);
    }

    @Override
    public String invalidUser(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            LOGGER.error(FIELD_IS_EMPTY);

            return FIELD_IS_EMPTY;
        }

        if (isUserPresent(login)) {
            LOGGER.error(USER_IS_PRESENT, login);

            return String.format(USER_IS_PRESENT.replace("{}", "%s"), login);
        }

        LOGGER.error(INVALID_FIELD);

        return INVALID_FIELD;
    }

    private boolean isUserPresent(String login) {
        User user = userDAO.getByLogin(login);

        return user.getLogin().equals(login);
    }
}
