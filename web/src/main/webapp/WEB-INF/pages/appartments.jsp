<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>hotels</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/fonts.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <link rel="stylesheet"
          href="libs/font-awesome-4.2.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="libs/owl-carousel/owl.carousel.css"/>
</head>

<body>
<script src="libs/jquery/jquery-1.11.1.min.js"></script>
<script src="libs/owl-carousel/owl.carousel.min.js"></script>
<script src="js/common.js"></script>
<script src="js/bootstrap.min.js"></script>
<div class="wrapper">

    <header class="top_header">
        <div class="header_top_line">
            <div class="container">
                <div class="col-lg-12">
                    <div class="row">
                        <div class="top_links">

                            <div class="account_icon">
                                <a href="users.html"><i class="fa fa-user-o"
                                                        aria-hidden="true"></i></a>
                            </div>
                            login

                            <a class="logout" href="#">Выход</a>

                            <a href="#">Вход</a>
                            <span class="delimiter"> / </span>
                            <a href="#">Регистрация</a>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <div class="content">

    </div>

    <div class="container">
        АППАРТАМЕНТЫ

        <div class="col-md-12">
            <div class="panel  panel-warning">
                <div class="panel-body">

                    <c:forEach var="app" items="${appartments}">


                        <div class="col-md-6">
                            <div class="panel  panel-warning">
                                <div class="panel-body">${app.name}

                                </div>
                                <div class="panel-footer">

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

    </div>

</div>

<footer>
    <div class="container">
        <div class="col-md-12">
            <div class="row">© Назаренко Николай</div>
        </div>
    </div>
</footer>

</div>
</body>
