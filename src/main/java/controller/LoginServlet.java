package controller;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.CartService;
import service.impl.CartServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/e-shop")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class.getName());

    private static final String LOGIN_PAGE = "WEB-INF/view/login.jsp";
    private static final String GOODS_PAGE = "WEB-INF/view/goods.jsp";
    private static final String REGISTER_PAGE = "WEB-INF/view/register.jsp";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String VALIDATIONS_ERRORS = "validationErrors";
    private static final String USER_NOT_FOUND_ERROR = "userNotFoundError";
    private static final String USER_IS_NOT_REGISTERED = "You are unregistered user. Please, register right now";
    private static final String EMPTY_VALUE = "";

    private UserServiceImpl userService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
        cartService = new CartServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcherForward(request, response, LOGIN_PAGE);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        HttpSession session = request.getSession();

        User user = userService.getByLoginAndPassword(login, password);

        LOGGER.info(user);

        cartService.updateData(session);

        clickingActions(request, response, session, user);
    }

    private void clickingActions(HttpServletRequest request, HttpServletResponse response, HttpSession session, User user) throws IOException, ServletException {
        if (request.getParameter("submit").equals("Enter")) {

            eventsWithUser(request, response, session, user);
        } else {
            updateSession(session, EMPTY_VALUE);

            dispatcherForward(request, response, REGISTER_PAGE);
        }
    }

    private void eventsWithUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, User user) throws IOException, ServletException {
        if (user.equals(new User("Unknown"))) {
            updateSession(session, USER_IS_NOT_REGISTERED);

            dispatcherForward(request, response, REGISTER_PAGE);
        } else {
            session.setAttribute(LOGIN, user.getLogin());

            dispatcherForward(request, response, GOODS_PAGE);
        }
    }

    private void updateSession(HttpSession session, String value) {
        session.setAttribute(USER_NOT_FOUND_ERROR, value);
        session.setAttribute(VALIDATIONS_ERRORS, EMPTY_VALUE);
    }

    private void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);

        dispatcher.forward(request, response);
    }
}
