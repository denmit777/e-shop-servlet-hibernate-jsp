package controller;

import model.Cart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.CartService;
import service.GoodService;
import service.OrderService;
import service.impl.CartServiceImpl;
import service.impl.GoodServiceImpl;
import service.impl.OrderServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/goods")
public class GoodServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(GoodServlet.class.getName());

    private static final String LOGIN_PAGE = "WEB-INF/view/login.jsp";
    private static final String GOODS_PAGE = "WEB-INF/view/goods.jsp";
    private static final String ORDER_PAGE = "WEB-INF/view/order.jsp";

    private GoodService goodService;
    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        goodService = new GoodServiceImpl();
        cartService = new CartServiceImpl();
        orderService = new OrderServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcherForward(request, response, GOODS_PAGE);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Cart cart = cartService.getCart(session);

        String command = request.getParameter("submit");

        clickingActions(command, request, response, session, cart);
    }

    private void clickingActions(String command, HttpServletRequest request, HttpServletResponse response, HttpSession session, Cart cart) throws IOException, ServletException {
        BigDecimal totalPrice = cartService.getTotalPrice(cart);

        session.setAttribute("cart", cart);
        session.setAttribute("totalPrice", totalPrice);

        String option = goodService.getStringOfNameAndPriceFromOptionMenu(request.getParameter("goodName"));
        String login = (String) session.getAttribute("login");

        switch (command) {
            case "Add Good":
                cartService.addGoodToCart(option, login, cart);

                getChosenGoods(session, cart);

                dispatcherForward(request, response, GOODS_PAGE);

                break;
            case "Remove Good":
                cartService.deleteGoodFromCart(option, cart);

                getChosenGoods(session, cart);

                dispatcherForward(request, response, GOODS_PAGE);

                break;
            case "Submit":
                String order = cartService.printOrder(cart);
                session.setAttribute("order", order);

                orderService.save(login, totalPrice);

                dispatcherForward(request, response, ORDER_PAGE);

                break;
            case "Log out":
                dispatcherForward(request, response, LOGIN_PAGE);

                break;
        }
    }

    private void getChosenGoods(HttpSession session, Cart cart) {
        String chosenGoods = cartService.printChosenGoods(cart);

        LOGGER.info("Chosen goods: {}", chosenGoods);

        session.setAttribute("chosenGoods", chosenGoods);
    }

    private void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);

        dispatcher.forward(request, response);
    }
}
