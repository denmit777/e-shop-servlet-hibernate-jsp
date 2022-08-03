<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>https://www.online-shop.com</title>
        <link rel="stylesheet" href="css/main.css" type="text/css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
            integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    </head>
    <body class="centered">
        <h2>Welcome to Online Shop!</h2>
        <form method="post" action="e-shop">
            <table>
                <tr>
                    <td>Login</td>
                    <td><input type="text" name="login" required placeholder="Enter your name" value=""/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" required placeholder="Enter your password" value=""/></td>
                </tr>
            </table><br/>
            <p><input name="submit" type="submit" value="Enter"></p>
            <p><input name="submit" type="submit" value="Register"></p>
        </form>
    </body>
</html>


