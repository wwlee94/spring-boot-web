<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="euc-kr" dir="ltr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title> Spring Online Judge </title>

    <!-- Bootstrap core CSS -->
    <link href="/vendor/bootstrap/css/bootstrap.css" rel="stylesheet">


    <!-- Custom fonts for this template -->
    <link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

    <!-- Custom styles for this template -->
    <link href="/css/clean-blog.css" rel="stylesheet">

    <style type="text/css">
        .panel-login {
            border-color: #ccc;
            -webkit-box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.2);
            -moz-box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.2);
            box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.2);
        }

        .panel-login>.panel-heading {
            color: #00415d;
            background-color: #fff;
            border-color: #fff;
            text-align: center;
        }

        .panel-login>.panel-heading a {
            text-decoration: none;
            color: #666;
            font-weight: bold;
            font-size: 15px;
            -webkit-transition: all 0.1s linear;
            -moz-transition: all 0.1s linear;
            transition: all 0.1s linear;
        }

        .panel-login>.panel-heading a.active {
            color: #029f5b;
            font-size: 18px;
        }

        .panel-login>.panel-heading hr {
            margin-top: 10px;
            margin-bottom: 0px;
            clear: both;
            border: 0;
            height: 1px;
            background-image: -webkit-linear-gradient(left, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0));
            background-image: -moz-linear-gradient(left, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0));
            background-image: -ms-linear-gradient(left, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0));
            background-image: -o-linear-gradient(left, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0));
        }

        .panel-login input[type="text"],
        .panel-login input[type="email"],
        .panel-login input[type="password"] {
            height: 45px;
            border: 1px solid #ddd;
            font-size: 16px;
            -webkit-transition: all 0.1s linear;
            -moz-transition: all 0.1s linear;
            transition: all 0.1s linear;
        }

        .panel-login input:hover,
        .panel-login input:focus {
            outline: none;
            -webkit-box-shadow: none;
            -moz-box-shadow: none;
            box-shadow: none;
            border-color: #ccc;
        }

        .btn-login {
            background-color: #59B2E0;
            outline: none;
            color: #fff;
            font-size: 14px;
            height: auto;
            font-weight: normal;
            padding: 14px 0;
            text-transform: uppercase;
            border-color: #59B2E6;
        }

        .btn-login:hover,
        .btn-login:focus {
            color: #fff;
            background-color: #53A3CD;
            border-color: #53A3CD;
        }

        .forgot-password {
            text-decoration: underline;
            color: #888;
        }

        .forgot-password:hover,
        .forgot-password:focus {
            text-decoration: underline;
            color: #666;
        }

        .btn-register {
            background-color: #1CB94E;
            outline: none;
            color: #fff;
            font-size: 14px;
            height: auto;
            font-weight: normal;
            padding: 14px 0;
            text-transform: uppercase;
            border-color: #1CB94A;
        }

        .btn-register:hover,
        .btn-register:focus {
            color: #fff;
            background-color: #1CA347;
            border-color: #1CA347;
        }
    </style>

</head>

<body>
<sec:authorize access="isAuthenticated()">
    <script>location.href='/';</script>
</sec:authorize>

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
                                    <li><a style="color:black;text-decoration:none;" href="security/login"> 로그인</a></li>
                                    <li><a style="color:black;text-decoration:none;" href="security/signUp"> 회원가입</a></li>
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

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6">
                            <a href="#" id="login-form-link">로그인</a>
                        </div>
                        <div class="col-xs-6">
                            <a href="#" class="active" id="register-form-link">회원가입</a>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form id="login-form"  method="post" role="form" style="display: none;">
                                <input type ="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="form-group">
                                    <input type="email" name="username" id="userID" tabindex="1" class="form-control" placeholder="이메일" value="">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="userPassword" tabindex="2" class="form-control" placeholder="비밀번호">
                                </div>
                                <div class="form-group text-center">
                                    <input type="checkbox" tabindex="3" class="" name="remember" id="remember">
                                    <label for="remember"> 아이디 기억하기</label>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="로그인">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="text-center">
                                                <a href="https://phpoll.com/recover" tabindex="5" class="forgot-password">비밀번호를 잊어버리셨나요?</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <form id="register-form" action="/member" method="post" role="form" style="display: block;">
                                <input type ="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="form-group">
                                    <input type="email" name="uemail" id="uid" tabindex="1" class="form-control" placeholder="아이디(이메일 형식)" value="">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="upw" id="password" tabindex="2" class="form-control" placeholder="비밀번호">
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="이름" name="uid" tabindex="1">
                                </div>
                                <div class="form-group" style="text-align:center;">
                                    <div class="btn-group" data-toggle="buttons">
                                        <label class="btn btn-primary active">
                                            <input type="radio" name="userGender" autocomplete="off" value="남자" checked>남자
                                        </label>
                                        <label class="btn btn-primary">
                                            <input type="radio" name="userGender" autocomplete="off" value="여자">여자
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="가입하기">
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <hr>
                            <div class="form-group text-center">
                                <label style="font-weight:bold;"> or </label>
                            </div>
                            <div class="col-sm-7 col-sm-offset-2">
                                <a href="/login/google">
                                    <img class="btn-img" style="width: 300px" src="/img/btn_google2.png"/>
                                </a>
                            </div>
                            <br /><br />
                            <div class="col-sm-7 col-sm-offset-2">
                                <a href="/login/facebook">
                                    <img class="btn-img" style="width: 300px" src="/img/btn_facebook2.png"/>
                                </a>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<br/><br/>

<!-- Footer -->
<hr>
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

<script type="text/javascript">
    $(function() {

        $('#login-form-link').click(function(e) {
            $("#login-form").delay(100).fadeIn(100);
            $("#register-form").fadeOut(100);
            $('#register-form-link').removeClass('active');
            $(this).addClass('active');
            e.preventDefault();
        });
        $('#register-form-link').click(function(e) {
            $("#register-form").delay(100).fadeIn(100);
            $("#login-form").fadeOut(100);
            $('#login-form-link').removeClass('active');
            $(this).addClass('active');
            e.preventDefault();
        });

    });
</script>

</body>

</html>
