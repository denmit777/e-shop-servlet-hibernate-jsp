<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.*, service.*, service.impl.*, utils.*, java.util.*" %>

<html>
    <head>
        <title>https://www.online-shop.com</title>
        <link rel="stylesheet" href="css/main.css" type="text/css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
            integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    </head>
    <body class="centered">
        <h2>Hello <%= (String) session.getAttribute("login") %>!</h2><br/>
        <div>
            <%
                GoodService goodService = new GoodServiceImpl();

                String chosenGoods = (String) session.getAttribute("chosenGoods");

                out.println("<h2><pre>" + goodService.getChoice(chosenGoods) + "</pre></h2>");
            %>
        </div>
        <form action="goods" method="post">
            <select class="select" name="goodName" id="goodName">
                <%
                    List<Good> goods = goodService.getAll();

                    out.println(goodService.getStringOfOptionsForDroppingMenuFromGoodList(goods));
                %>
            </select>
                <br/><br/>
            <input name="submit" type="submit" value="Add Good">
            <input name="submit" type="submit" value="Submit">
                <br/><br/>
            <input name="submit" type="submit" value="Remove Good">
            <input name="submit" type="submit" value="Log out">
        </form>
    </body>
</html>
