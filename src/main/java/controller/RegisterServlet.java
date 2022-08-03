package controller;

import model.User;

import service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "WEB-INF/view/login.jsp";
    private static final String REGISTER_PAGE = "WEB-INF/view/register.jsp";
    private static final String ERROR_PAGE = "WEB-INF/view/error.jsp";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String VALIDATIONS_ERRORS = "validationErrors";
    private static final String USER_NOT_FOUND_ERROR = "userNotFoundError";
    private static final String EMPTY_VALUE = "";

    private UserServiceImpl userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcherForward(request, response, REGISTER_PAGE);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        HttpSession session = request.getSession();
        session.setAttribute(USER_NOT_FOUND_ERROR, EMPTY_VALUE);

        if (userService.isInvalidUser(login, password)) {
            String errors = userService.invalidUser(login, password);

            session.setAttribute(VALIDATIONS_ERRORS, errors);

            dispatcherForward(request, response, REGISTER_PAGE);
        } else {
            session.setAttribute(VALIDATIONS_ERRORS, EMPTY_VALUE);

            eventsWithCheckbox(request, response, session, login, password);
        }
    }

    private void eventsWithCheckbox(HttpServletRequest request, HttpServletResponse response, HttpSession session, String login, String password) throws IOException, ServletException {
        if (request.getParameter("isUserCheck") != null) {
            User user = userService.save(login, password);

            session.setAttribute(LOGIN, user.getLogin());

            dispatcherForward(request, response, LOGIN_PAGE);
        } else {
            dispatcherForward(request, response, ERROR_PAGE);
        }
    }

    private void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);

        dispatcher.forward(request, response);
    }
}
