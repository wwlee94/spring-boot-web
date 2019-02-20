<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Clean Blog - Start Bootstrap Theme</title>

    <!-- Bootstrap core CSS -->
    <link href="/vendor/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet'
          type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>

    <!-- Custom styles for this template -->
    <link href="/css/clean-blog.css" rel="stylesheet">

</head>
<body>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
        <a class="navbar-brand" href="/">Start Bootstrap</a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
                data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false"
                aria-label="Toggle navigation">
            Menu
            <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/about.html">About</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/post.html">Sample Post</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/board/list">Board</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Page Header -->
<header class="masthead" style="background-image: url('/img/home-bg.jpg'); background-size: cover">
    <div class="overlay"></div>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
                <div class="site-heading">
                    <h1>Clean Blog</h1>
                    <span class="subheading">A Blog Theme by Start Bootstrap</span>
                </div>
            </div>
        </div>
    </div>
</header>

<!-- Main Content -->
<div class="container">
    <div class="page-header">
        <h2>
            Board
            <span id="dpTime" class="pull-right"></span>
        </h2>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>No.</th>
            <th><span class="glyphicon glyphicon-list"></span>&nbsp;글 제목</th>
            <th><span class="glyphicon glyphicon-user"></span>&nbsp;아이디</th>
            <th><span class="glyphicon glyphicon-thumbs-up"></span>&nbsp;좋아요</th>
            <th><span class="glyphicon glyphicon-time"></span>&nbsp;작성일</th>
            <th>수정 / 삭제</th>
        </tr>
        </thead>
        <tbody>
        <!-- boardList는 DomainController에서 보내준 변수임 -->
        <c:forEach var="board" items="${boardList}">
            <tr>
                <td>${board.bno}</td>
                <td><a href="/board/read/${board.bno}">${board.title}</a></td>
                <td>${board.userName}</td>
                <td>${board.likeCount}</td>
                <td id="board_diff${board.bno}" data-timestamp="${board.diff}">${board.timeDifference}</td>
                <td>
                    <div class="btn-group">
                        <button style="font-size: small" name="modify" value="${board.bno}"
                                class="btn btn-sm btn-outline-success btn-padding">수정
                        </button>

                        <jsp:include page="../include/modal/checkDelete.jsp"/>
                        <button style="font-size: small" name="delete" value="${board.bno}"
                                class="btn btn-sm btn-outline-danger btn-padding">삭제
                        </button>
                    </div>
                </td>
            </tr>
            <input type="hidden" id="ti${board.bno}" value="${board.title}">
            <input type="hidden" id="co${board.bno}" value="${board.contents}">
            <input type="hidden" id="li${board.bno}" value="${board.likeCount}">
        </c:forEach>
        </tbody>
    </table>

    <jsp:include page="../include/modal/Posts.jsp"/>
    <button id="createBtn" class="btn btn-info btn-xs"
            data-toggle="modal">새 글 쓰기
    </button>
    <hr/>
</div>

<!-- Footer -->
<footer>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
                <ul class="list-inline text-center">
                    <li class="list-inline-item">
                        <a href="#">
                  <span class="fa-stack fa-lg">
                    <i class="fas fa-circle fa-stack-2x"></i>
                    <i class="fab fa-twitter fa-stack-1x fa-inverse"></i>
                  </span>
                        </a>
                    </li>
                    <li class="list-inline-item">
                        <a href="#">
                  <span class="fa-stack fa-lg">
                    <i class="fas fa-circle fa-stack-2x"></i>
                    <i class="fab fa-facebook-f fa-stack-1x fa-inverse"></i>
                  </span>
                        </a>
                    </li>
                    <li class="list-inline-item">
                        <a href="#">
                  <span class="fa-stack fa-lg">
                    <i class="fas fa-circle fa-stack-2x"></i>
                    <i class="fab fa-github fa-stack-1x fa-inverse"></i>
                  </span>
                        </a>
                    </li>
                </ul>
                <p class="copyright text-muted">Copyright &copy; Your Website 2018</p>
            </div>
        </div>
    </div>
</footer>

<!-- 실시간 시간 변화 출력 -->
<script type="text/javascript">

    boardList = [];
    board = {};
    i = 0;

    onload = function () {
        dpTime();
    };
    setInterval("dpTime()", 1000);

    function dpTime() {
        var now = new Date();
        hours = now.getHours();
        minutes = now.getMinutes();
        seconds = now.getSeconds();
        if (hours > 12) {
            hours -= 12;
            ampm = "오후 ";
        } else {
            ampm = "오전 ";
        }
        if (hours < 10) {
            hours = "0" + hours;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }
        document.getElementById("dpTime").innerHTML = ampm + hours + ":" + minutes + ":" + seconds;

        realTime();
    }

    function realTime() {
        boardList = [];

        <c:forEach var="board" items="${boardList}">
        board = {};
        board.bno = ${board.bno};
        board.dateTime = "${board.dateTime}";
        board.timeDifference = "";
        boardList.push(board);
        </c:forEach>

        var data = {
            "boardList": boardList
        };

        //Json 형태로 데이터 전달
        $.ajax({
            url: "/board/list/realTime",
            type: "GET",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (response) {
                //성공시 controller로 부터 전달받은 data로 text 변경
                for (i = 0; i < response.length; i++) {
                    var bno = response[i].bno;
                    var timeDifference = response[i].timeDifference;

                    $("#board_diff" + bno).text(timeDifference);
                }
            }
        });
    }
</script>


<!-- Bootstrap core JavaScript -->
<script src="/vendor/jquery/jquery.min.js"></script>
<script src="/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Custom scripts for this template -->
<script src="/js/clean-blog.min.js"></script>

<!-- Modal.js -->
<script type="text/javascript" src="/js/modal_event.js"></script>

</body>
</html>