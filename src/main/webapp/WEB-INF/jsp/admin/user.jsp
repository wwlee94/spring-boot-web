<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
    <div class="row" style="width: 100%">

        <div class="container">
            <a class="navbar-brand" href="/">Spring Online Judge</a>
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                Menu
                <i class="fas fa-bars"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/user/list">랭킹</a>
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
                    <sec:authorize access="isAuthenticated()">
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
    </div>
</nav>
<!-- Page Header -->
<header class="masthead" style="background-color: #6c757d">
    <div class="overlay"></div>
    <div class="container">
        <div class="row">
            <div class="site-heading" style="padding: 50px">
            </div>
        </div>
    </div>
</header>

<br/><br/>


<!-- Main Content -->
<div class="container">
    <div class="page-header">
        <h2>
            관리자 페이지
            <span id="dpTime" class="pull-right"></span>
        </h2>
    </div>
</div>
<div class="container content">
    <div class="row">
        <div class = "col-md-3">
            <ul class="list-group sidebar-nav-v1">
                <li class = "list-group-item active"><a href = "/admin/user">유저 정보</a></li>
                <li class = "list-group-item"><a href = "/admin/userManage">유저 권한 부여</a></li>
            </ul>
            <br>
            <ul class="list-group sidebar-nav-v1">
                <li class = "list-group-item"><a href = "/admin/problem">문제 만들기</a></li>
                <li class = "list-group-item"><a href = "/admin/problemExam">문제별 예시 만들기</a></li>
            </ul>
        </div>
        <div class = "col-md-9">
            <div class = "row">
                <div class = "col-md-12">
                    <div class="container">
                    <form action="/admin/user" method="get">
                        <input type="text" name="email" tabindex="1"  placeholder="이메일" style="display: inline;">
                        <input type="submit" value="검색하기" style="display: inline;">
                    </form>
                    </div>
                    <table class="table table-hover">
                        <thead>
                        <h3>온라인 저지 회원</h3>
                        <tr>
                            <th>No.</th>
                            <th>&nbsp;이메일</th>
                            <th>&nbsp;이름</th>
                            <th>&nbsp;이메일 확인</th>
                            <th>&nbsp;삭제</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="member" items="${memberList}">
                            <tr>
                                <td>${member.id}</td>
                                <td>${member.uemail}</td>
                                <td>${member.uid}</td>
                                <td>${member.emailstr}</td>
                                <td>
                                    <button style="font-size: small" value="${member.id}"
                                            class="btn btn-sm btn-outline-danger btn-padding deleteUser">
                                        삭제
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <table class="table table-hover">
                        <thead>
                        <h3>소셜 로그인 회원</h3>
                        <tr>
                            <th>No.</th>
                            <th>&nbsp;이메일</th>
                            <th>&nbsp;이름</th>
                            <th>&nbsp;소셜 정보</th>
                            <th>&nbsp;삭제</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.email}</td>
                                <td>${user.nickname}</td>
                                <td>${user.social.provider}</td>
                                <td>
                                    <button style="font-size: small" value="${user.id}"
                                            class="btn btn-sm btn-outline-danger btn-padding deleteOauthUser">
                                        삭제
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
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



<!-- Bootstrap core JavaScript -->
<script src="/vendor/jquery/jquery.min.js"></script>
<script src="/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Custom scripts for this template -->
<script src="/js/clean-blog.min.js"></script>

<!-- Modal.js -->
<script type="text/javascript" src="/js/modal_event.js"></script>

<script type="text/javascript">
    $(".deleteUser").click(function(){
        $.ajax({
            url: '/admin/user/'+this.value,
            method: "DELETE",
        }).always(function(){
            location.reload();
        });
    });


    $(".deleteOauthUser").click(function(){
        $.ajax({
            url: '/admin/Oauth/'+this.value,
            method: "DELETE",
        }).always(function(){
            location.reload();
        });
    });
</script>

</body>
</html>