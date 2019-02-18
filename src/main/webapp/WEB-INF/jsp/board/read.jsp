<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
<header class="masthead" style="background-image: url('/img/home-bg.jpg')">
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
        <h2>Board</h2>
        <h5>
            No.<span id="board_bno">${board.bno}</span>
            &nbsp;&nbsp;
            <span class="h-normal">게시글 제목 : ${board.title}</span>
        </h5>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            ${board.userName}
            &nbsp;&nbsp;<span class="glyphicon glyphicon-thumbs-up"></span>
            <span id="post_likecount">
                ${board.likeCount}
            </span>
            <div style="font-size: medium" class="pull-right">
                ${board.timeDifference} &nbsp;&nbsp;
                <a href="#" onclick="return false" id="post_like" data-like="${isLike}"></a>
            </div>
        </div>
        <!-- DB는 엔터를 줄바꿈으로 인식X 따로 처리 -->
        <div id=post_contents class="panel-body">
            ${fn:replace(board.contents,rn,br)}
        </div>
    </div>

    <!-- 게시글에 따른 댓글 목록 출력 -->
    <c:forEach var="reply" items="${boardReplyList}">

        <h6>
            Re_${reply.rno}
            <jsp:include page="../include/infoDelete.jsp"/>
            <button type="button" name="delete" value="${reply.rno}"
                    class="btn btn-info btn-sm glyphicon glyphicon-remove pull-right"></button>
        </h6>

        <div class="panel panel-default">
            <div class="panel-heading">
                    ${reply.userName}
                &nbsp;&nbsp;<span class="glyphicon glyphicon-thumbs-up"></span>
                <span id="reply_likecount">
                        ${reply.likeCount}
                </span>
                <div style="font-size: medium" class="pull-right">
                        ${reply.timeDifference} &nbsp;&nbsp;
                           <c:set var="state" value="false"/>
                            <c:forEach var="replyLikes" items="${replyLikesList}">
                                <c:choose>
                                    <c:when test="${reply.rno eq replyLikes.replyId}">
                                        <c:set var="state" value="true"/>
                                    </c:when>
                                </c:choose>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${state eq true}">
                                    <a href="#" onclick="return false" class="reply_like" name="${reply.rno}" data-likeCount="${reply.likeCount}" data-like="1">좋아요 취소</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="#" onclick="return false" class="reply_like" name="${reply.rno}" data-likeCount="${reply.likeCount}" data-like="0">좋아요</a>
                                </c:otherwise>
                            </c:choose>
                </div>
            </div>
            <!-- DB는 엔터를 줄바꿈으로 인식X 따로 처리 -->
            <div id="contents" class="panel-body">
                    ${fn:replace(reply.contents,rn,br)}
            </div>
        </div>
    </c:forEach>

    <!-- 댓글 작성란 -->
    <hr/>
    <h5>
        <span class="glyphicon glyphicon-tags"></span>
        &nbsp;댓글 쓰기
    </h5>
    <!-- 댓글 작성란 -->
    <div class="panel panel-default">
        <div class="panel-heading">
            ${board.userName}
        </div>
        <div class="panel-body">
            <input type="text" class="form-control" id="reply_input" placeholder="댓글을 작성해주세요.">
        </div>
    </div>
    <!-- 댓글 쓰기 버튼 -->
    <button id="replyBtn" class="btn btn-info btn-xs right-button">댓글 저장</button>
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

<!-- Like Event -->
<script type="text/javascript" src="/js/like_event.js"></script>

</body>
</html>