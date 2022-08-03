<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="service.*, service.impl.*, java.util.*, java.math.BigDecimal;" %>

<html>
    <head>
        <title>https://www.online-shop.com</title>
        <link rel="stylesheet" href="css/main.css" type="text/css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
             integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    </head>
    <body class="centered">
        <h2>Dear <%= (String) session.getAttribute("login") %>,
            <%
                OrderService orderService = new OrderServiceImpl();

                BigDecimal totalPrice = (BigDecimal) session.getAttribute("totalPrice");

                out.println(orderService.orderResult(totalPrice));
            %>
        </h2><br/>
        <div>
            <%
                out.println("<h2><pre>" + (String) session.getAttribute("order") + "</pre></h2>");
            %>
        </div>
        <h2>Total: $ <span> <%= totalPrice %></span></h2><br/>
        <form action="/e-shop" method="get">
            <input name="submit" type="submit" value="Log out">
        </form>
    </body>
</html>









