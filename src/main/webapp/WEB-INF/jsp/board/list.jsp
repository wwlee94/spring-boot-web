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
                                class="btn btn-sm btn-warning btn-padding">수정
                        </button>

                        <jsp:include page="../include/modal/checkDelete.jsp"/>
                        <button style="font-size: small" name="delete" value="${board.bno}"
                                class="btn btn-sm btn-danger btn-padding">삭제
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
<!-- TODO: ajax는 언제 사용하는 것? 1초마다 값 가져와 바꾸는 경우엔 js로만으로도 가능하지만 다른 방법이 있나?
새로고침을 안하고 부분적으로만 리로드해서 보여주려고 ajax쓰는것인디요..
location.realod는 새로고침버튼 누른거랑 동일한 동작입니당..
코드를 그렇게 바꾸시면 아무 의미가 없어요..
자바스크립트 사용하셔서 수정하고싶은 부분을 들어내신 다음에
비동기통신으로 받아온 새로운 값을 채워넣으셔야 하는뎅..
<td>aaa</td> 원래 표시
새로받은 값 bbb
자바스크립트로 aaa 지우고 bbb 채워넣음
<td>bbb</td> 이후 표시
이런식으로..
-->
<script type="text/javascript">

    var diff = 0;
    count = 0;

    onload = function() {
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

        refresh_diff();

        $.ajax({
            url:"/board/woowon",
            type:"GET",
            success:function (data) {
                console.log(data);
            }
        })
    }

    function refresh_diff() {

        <c:forEach var="board" items="${boardList}">

        diff = ${board.diff};

        diff = diff+(count * 1000);

        seconds = Math.floor(diff / 1000 % 60);
        minutes = Math.floor(diff / (60 * 1000) % 60);
        hours = Math.floor(diff / (60 * 60 * 1000) % 24);
        days = Math.floor(diff / (24 * 60 * 60 * 1000));

        month = Math.floor(days / 30);
        year = Math.floor(days / 365);

        if (year <= 0) {
            if (month <= 0) {
                if (days <= 0) {
                    if (hours <= 0) {
                        if (minutes <= 0) {
                            $("#board_diff${board.bno}").text(seconds + "초 전");
                        } else {
                            $("#board_diff${board.bno}").text(minutes + "분 "+seconds+"초 전");
                        }
                    } else {
                        $("#board_diff${board.bno}").text(hours + "시간 "+minutes + "분 전");
                    }
                } else {
                    $("#board_diff${board.bno}").text(days + "일 "+hours+"시간 전");
                }
            } else {
                $("#board_diff${board.bno}").text(month + "개월 "+days%30 + "일 전");
            }
        } else {
            //continue 덕에 위에 조건 성립하면 이 아래 코드는 실행 안함
            $("#board_diff${board.bno}").text(year + "년 "+year%12 + "개월 전");
        }

        </c:forEach>
        count = count+1 ;
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