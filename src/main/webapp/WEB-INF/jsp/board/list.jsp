<%@ page import="com.springboot.web.board.paging.Paging" %>
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

    <title>Spring Online Judge</title>

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
        <a class="navbar-brand" href="/">Spring Online Judge</a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
                data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false"
                aria-label="Toggle navigation">
            Menu
            <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/">랭킹</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/problem/compileList">채점 현황</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/problem/problemset">문제</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/board/list">게시판</a>
                </li>
                <sec:authorize access="isAnonymous()">
                    <ul style="font-size: 20px" class="nav navbar-nav navbar-right">
                        <li class="dropdown nav-item"><a style="text-decoration:none;" href="#" class="dropdown-toggle"
                                                         data-toggle="dropdown" role="button" aria-haspopup="true"
                                                         aria-expanded="false">회원 관리<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a style="color:black;text-decoration:none;" href="/security/login"> 로그인</a></li>
                                <li><a style="color:black;text-decoration:none;" href="/security/signUp"> 회원가입</a></li>
                            </ul></li>
                    </ul>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_BASIC') and isAuthenticated()">
                    <ul style="font-size: 20px" class="nav navbar-nav navbar-right">
                        <li class="dropdown nav-item"><a style="text-decoration:none;" href="#" class="dropdown-toggle"
                                                         data-toggle="dropdown" role="button" aria-haspopup="true"
                                                         aria-expanded="false">회원 관리<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a style="color:black;text-decoration:none;" href="security/#"> 정보수정</a></li>
                                <li><a style="color:black;text-decoration:none;" href="/logout"> 로그아웃</a></li>
                            </ul></li>
                    </ul>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
                    <ul style="font-size: 20px" class="nav navbar-nav navbar-right">
                        <li class="dropdown nav-item"><a style="text-decoration:none;" href="#" class="dropdown-toggle"
                                                         data-toggle="dropdown" role="button" aria-haspopup="true"
                                                         aria-expanded="false">회원 관리<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a style="color:black;text-decoration:none;" href="/admin/user"> 관리자 페이지 </a></li>
                                <li><a style="color:black;text-decoration:none;" href="/security/#"> 정보수정 </a></li>
                                <li><a style="color:black;text-decoration:none;" href="/logout"> 로그아웃 </a></li>
                            </ul></li>
                    </ul>
                </sec:authorize>
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
                    <h1>ONLINE JUDGE !</h1>
                    <span class="subheading">프로그래밍 문제를 풀고 온라인으로 채점받을 수 있는 곳입니다.</span>
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
    <table class="table table-hover table-striped">
        <thead>
        <tr>
            <th><span class="glyphicon glyphicon-list"></span>&nbsp;제목</th>
            <th><span class="glyphicon glyphicon-user"></span>&nbsp;아이디</th>
            <th><span class="glyphicon glyphicon-thumbs-up"></span>&nbsp;</th>
            <th><span class="glyphicon glyphicon-time"></span>&nbsp;작성일</th>
            <th>수정 / 삭제</th>
        </tr>
        </thead>
        <tbody>
        <!-- boardList는 DomainController에서 보내준 변수임 -->
        <c:forEach var="board" items="${boardList}">
            <tr>
                <td><a href="/board/read/${board.bno}">${board.title}</a></td>
                <td>${board.userName}</td>
                <td>${board.likeCount}</td>
                <td id="board_diff${board.bno}">${board.timeDifference}</td>
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

    <br/>
    <!-- pagination -->
    <nav aria-label="Search results pages">
        <ul class="pagination justify-content-center">
            <!-- page==1 이면 이전 페이지네이션 disable로 만듬 -->
            <c:if test="${paging.page==1}">
                <li class="page-item disabled">
                    <a class="page-link" tabindex="-1">
                        <span aria-hidden="true">처음</span>
                    </a>
                </li>
                <li class="page-item disabled">
                    <a class="page-link" tabindex="-1">&lt;</a>
                </li>
            </c:if>
            <c:if test="${paging.page!=1}">
                <li class="page-item">
                    <a class="page-link" href="/board/list?page=1">
                        <span aria-hidden="true">처음</span>
                    </a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="/board/list?page=${paging.page-1}">&lt;</a>
                </li>
            </c:if>
            <!-- 페이지 정보 가져와 화면에 출력
                여기가 NullPointerException 뜨긴 하는 데 별 이상은 X
            -->
            <%
                //JSP로 애트리뷰트 받는 법
                Paging paging = (Paging) request.getAttribute("paging");
                int count = 0;
                //보여줄 페이지 목록은 시작페이지 ~ 마지막 페이지
                for (int i = paging.getStartPage()-1; i < paging.getEndPage(); i++) {
                    if (i == paging.getPage() - 1) {
                        out.print("<li class='page-item active'>");
                    } else {
                        out.print("<li class='page-item'>");
                    }
                    out.print("<a class='page-link' href='/board/list?page=" + (paging.getStartPage() + count) + "'>");
                    out.print((paging.getStartPage() + count));
                    out.print("</a></li>");
                    count++;
                }
            %>
            <!-- page가 마지막페이지이면 다음 페이지네이션 disable로 만듬 -->
            <c:if test="${paging.page==paging.totalPage}">
                <li class="page-item disabled">
                    <a class="page-link" tabindex="-1">&gt;</a>
                </li>
                <li class="page-item disabled">
                    <a class="page-link" tabindex="-1">
                        <span aria-hidden="true">&gt;&gt;</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${paging.page!=paging.totalPage}">
                <li class="page-item">
                    <a class="page-link" href="/board/list?page=${paging.page+1}">&gt;</a>
                </li>
                <!-- 10칸씩 페이지 넘기는 기능에 대해 if문 분류 -->
                <c:if test="${paging.endPage+1 <= paging.totalPage}">
                    <li class="page-item">
                        <a class="page-link" href="/board/list?page=${paging.endPage+1}">
                            <span aria-hidden="true">&gt;&gt;</span>
                        </a>
                    </li>
                </c:if>
                <c:if test="${paging.endPage+1 > paging.totalPage}">
                    <li class="page-item disabled">
                        <a class="page-link" tabindex="-1">
                            <span aria-hidden="true">&gt;&gt;</span>
                        </a>
                    </li>
                </c:if>
            </c:if>
        </ul>
    </nav>
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
    count = 0;

    onload = function () {
        dpTime();
    };

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

        setTimeout("dpTime()", 1000);
        count++;
        if (count > 120) {
            count = 0;
            location.reload();
        }
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
        //data -> 객체안에 배열 , 배열 안에 여러개의 객체로 이루어짐
        $.ajax({
            url: "/board/list/realTime",
            type: "POST",
            cache: false,
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (response) { // 성공했을 때의 처리 콜백함수
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