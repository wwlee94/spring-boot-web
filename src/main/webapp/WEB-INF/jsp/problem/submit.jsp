<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%
    pageContext.setAttribute("rn", "\n");
    pageContext.setAttribute("br", "<br>");
%>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Clean Blog - Start Bootstrap Theme</title>

    <!-- Bootstrap core CSS-->
    <link href="/vendor/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet'
          type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>

    <!-- Custom styles for this template -->
    <link href="/css/clean-blog.css" rel="stylesheet">
    <!-- 코드미러 -->
    <link href="/css/codemirror/codemirror.css" rel="stylesheet">
    <link href="/css/codemirror/Theme/mbo.css" rel="stylesheet">

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
                                    <li><a style="color:black;text-decoration:none;" href="/admin/list"> 관리자 페이지 </a></li>
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


<div class="container">
    <br>
    <nav class="navbar navbar-expand-md bg-light">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link"  href="#"> ${problem.proNo}번</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">제출</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>
        </ul>
    </nav>
</div>

<!-- Main Content -->
<div class="container">
    <div class="row">
    <div class="page-header">
        <h2>문제</h2>
        <h5>
            No.<span id="board_bno">${problem.proNo}</span>
            &nbsp;&nbsp;
            <span class="h-normal">문제 제목 : ${problem.proName}</span>
        </h5>
    </div>

    <div class = "col-md-12">
        <form action="/problem/compile" method="post" class = "form-horizontal submit-form" id = "submit_form">
            <legend>A+B</legend>
            <input type='hidden' value='${problem.proNo}' name="proNo">
            <div class="form-group">
                <label class="col-md-2 control-label" for="language">언어</label>
                <div class="col-md-10" style="margin-top:7px;">
                    <select id="language" name="language" data-placeholder="언어를 선택해 주세요." class="language-select col-md-2 chosen-select" data-no_results_text = "없는 언어 입니다.">
                        <option value="88" data-mime="text/x-c++src" selected>C++14</option>
                        <option value="3" data-mime="text/x-java" >Java</option>
                        <option value="28" data-mime="text/x-python" >Python 3</option>
                        <option value="75" data-mime="text/x-csrc" >C11</option>
                        <option value="73" data-mime="text/x-python" >PyPy3</option>
                        <option value="0" data-mime="text/x-csrc" >C</option>
                        <option value="1" data-mime="text/x-c++src" >C++</option>
                        <option value="49" data-mime="text/x-c++src" >C++11</option>
                        <option value="84" data-mime="text/x-c++src" >C++17</option>
                        <option value="91" data-mime="text/x-java" >Java (OpenJDK)</option>
                        <option value="93" data-mime="text/x-java" >Java 11</option>
                        <option value="6" data-mime="text/x-python" >Python 2</option>
                        <option value="32" data-mime="text/x-python" >PyPy2</option>
                        <option value="68" data-mime="text/x-ruby" >Ruby 2.5</option>
                        <option value="69" data-mime="text/x-kotlin" >Kotlin (JVM)</option>
                        <option value="92" data-mime="text/x-kotlin" >Kotlin (Native)</option>
                        <option value="74" data-mime="text/x-swift" >Swift</option>
                        <option value="58" data-mime="text/plain" >Text</option>
                        <option value="62" data-mime="text/x-csharp" >C# 6.0</option>
                        <option value="17" data-mime="text/javascript" >node.js</option>
                        <option value="12" data-mime="text/x-go" >Go</option>
                        <option value="29" data-mime="text/x-d" >D</option>
                        <option value="37" data-mime="text/x-fsharp" >F#</option>
                        <option value="7" data-mime="text/x-php" >PHP</option>
                        <option value="44" data-mime="text/plain" >Rust</option>
                        <option value="2" data-mime="text/x-pascal" >Pascal</option>
                        <option value="16" data-mime="text/x-lua" >Lua</option>
                        <option value="8" data-mime="text/x-perl" >Perl</option>
                        <option value="72" data-mime="text/x-rsrc" >R</option>
                        <option value="10" data-mime="text/x-objectivec" >Objective-C</option>
                        <option value="64" data-mime="text/x-objectivec" >Objective-C++</option>
                        <option value="59" data-mime="text/x-csrc" >C (Clang)</option>
                        <option value="60" data-mime="text/x-c++src" >C++ (Clang)</option>
                        <option value="66" data-mime="text/x-c++src" >C++11 (Clang)</option>
                        <option value="67" data-mime="text/x-c++src" >C++14 (Clang)</option>
                        <option value="77" data-mime="text/x-csrc" >C11 (Clang)</option>
                        <option value="85" data-mime="text/x-c++src" >C++17 (Clang)</option>
                        <option value="79" data-mime="text/plain" >Golfscript</option>
                        <option value="27" data-mime="text/plain" >Assembly (32bit)</option>
                        <option value="87" data-mime="text/plain" >Assembly (64bit)</option>
                        <option value="63" data-mime="text/x-vb" >VB.NET 4.0</option>
                        <option value="5" data-mime="text/x-sh" >Bash</option>
                        <option value="13" data-mime="text/x-fortran" >Fortran</option>
                        <option value="14" data-mime="text/x-scheme" >Scheme</option>
                        <option value="19" data-mime="text/plain" >Ada</option>
                        <option value="21" data-mime="text/plain" >awk</option>
                        <option value="22" data-mime="text/x-ocaml" >OCaml</option>
                        <option value="23" data-mime="text/x-brainfuck" >Brainfuck</option>
                        <option value="24" data-mime="text/plain" >Whitespace</option>
                        <option value="26" data-mime="text/x-tcl" >Tcl</option>
                        <option value="34" data-mime="text/javascript" >Rhino</option>
                        <option value="35" data-mime="text/x-cobol" >Cobol</option>
                        <option value="41" data-mime="text/x-c++src" >Pike</option>
                        <option value="43" data-mime="text/plain" >sed</option>
                        <option value="46" data-mime="text/plain" >Boo</option>
                        <option value="47" data-mime="text/plain" >Intercal</option>
                        <option value="48" data-mime="text/plain" >bc</option>
                        <option value="53" data-mime="text/plain" >Nemerle</option>
                        <option value="54" data-mime="text/plain" >Cobra</option>
                        <option value="70" data-mime="text/plain" >Algol 68</option>
                        <option value="71" data-mime="text/plain" >Befunge</option>
                        <option value="81" data-mime="text/x-haxe" >Haxe</option>
                        <option value="82" data-mime="text/plain" >LOLCODE</option>
                        <option value="83" data-mime="text/plain" >아희</option>
                    </select></div></div><div class="form-group">
            <label class="col-md-2 control-label" >소스 코드 공개</label>
            <div class="col-md-10">
                <div class = "radio">
                    <label>
                        <input type = "radio" name = "code_open" id="code_open_open" value="open" checked>공개</label>
                </div>
                <div class = "radio">
                    <label>
                        <input type = "radio" name = "code_open" id="code_open_close" value="close" >비공개</label>
                </div>
                <div class = "radio"><label>
                    <input type = "radio" name = "code_open" id="code_open_accepted" value="onlyaccepted" >맞았을 때만 공개</label>
                </div>
            </div>
        </div>
            <div class="form-group">
                <label class="col-md-2 control-label" for="source">소스 코드</label>
                <div class="col-md-10">
                    <textarea id="contents" class="codemirror-textarea" name="source"></textarea>
                    <%--<textarea id="source" class="codemirror-textarea" data-mime="text/x-c++src" name="source" autofocus="autofocus">--%>
                    <%--</textarea>--%>
                </div>
            </div>
            <input type="hidden" name="csrf_key" value="41c195b0d065fe330f8fe2c4f7b0c1b0">
            <div class = "form-group">
                <div class = "col-md-offset-2 col-md-10">
                    <button id="submit" type="submit" class="btn btn-primary" data-loading-text="제출 중...">제출</button>
                </div>
            </div>
        </form>
    </div>
    </div>
</div>

<hr>
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

<!--코드미러-->
<script src="/js/codemirror/codemirror.js"></script>
<script src="/js/codemirror/mode/clike/clike.js"></script>
<script src="/js/codemirror/codemirror-edit.js"></script>

</body>
</html>