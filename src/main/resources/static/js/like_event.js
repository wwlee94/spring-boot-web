var action = '';
var type = '';
var url = '';

var bno = 0;
var rno = 0;

var like = "좋아요";
var clike = "좋아요 취소";

var int = 0;
//input or textarea 값 받아올 때 val()
//그 외엔 text()
$(document).ready(function () {

    //Post 게시글의 좋아요 클릭 전 데이터 세팅
    //data-like -> 속성의 값 가져오는 법
    var post_like = document.querySelector("#post_like");
    var isLike = post_like.dataset.like;
    if (isLike === '1') {
        post_like.text = "좋아요 취소";
    } else {
        post_like.text = "좋아요";
    }

    //Reply 댓글의 좋아요 클릭 전 데이터 세팅(얘는 단일 객체가 아닌 LIST라 read.jsp 에서 JSP로 처리함)
    var reply_isLike;

    //==========================================================
    //post_like 클릭했을 때
    //==========================================================
    $("#post_like").click(function () {

        var likeCount;

        //좋아요 상태 눌렀을 때 그 이후 처리
        if (isLike === '0') {
            likeCount = $("#post_likeCount").text();
            likeCount++;
            $("#post_likeCount").text(likeCount);

            $(this).text(clike);
            isLike = "1";
        }
        //좋아요 취소 상태를 눌렀을 때 그 이후 처리
        else {
            likeCount = $("#post_likeCount").text();
            likeCount--;
            $("#post_likeCount").text(likeCount);

            $(this).text(like);
            isLike = "0";
        }

        bno = $("#board_bno").text();
        type = "GET";
        url = "/board/read/like/" + bno;

        //isLike = 1 이면 필드 추가 , 0이면 삭제
        var data = {
            "likeCount": likeCount,
            "isLike": isLike
        };

        $.ajax({
            url: url,
            type: type,
            data: data
            //complete:function () {location.reload();}
        });

    });
    //==========================================================
    //reply_like 버튼 이벤트
    //==========================================================
    $(".reply_like").click(function () {

        var likeCount;

        rno = $(this).attr("name");
        reply_isLike = $(this).attr("data-like");

        //좋아요 상태 눌렀을 때 그 이후 처리
        //reply_l;ikeCount 를 수정해야함 delete랑
        if (reply_isLike === '0') {
            likeCount = $("#reply_likeCount"+rno).text();
            likeCount++;
            $("#reply_likeCount"+rno).text(likeCount);

            $(this).text(clike);
            $(this).attr("data-like", "1");
        }
        //좋아요 취소 상태를 눌렀을 때 그 이후 처리
        else {
            likeCount = $("#reply_likeCount"+rno).text();
            likeCount--;
            $("#reply_likeCount"+rno).text(likeCount);

            $(this).text(like);
            $(this).attr("data-like", "0");
        }

        type = "GET";
        url = "/board/read/replyLike/" + rno;

        var data = {
            "likeCount": likeCount,
            "isLike": $(this).attr("data-like")
        };

        //isLike = 1 이면 필드 추가 , 0이면 삭제
        $.ajax({
            url: url,
            type: type,
            data: data
            //complete:function () {location.reload();}
        });

    });
    //==========================================================
    //reply 버튼 이벤트
    //==========================================================
    $("#replyBtn").click(function () {

        //contents -> input reply의 댓글 내용
        var contents = $("#reply_input").val();

        if (contents === "" || contents == null) {
            alert("댓글 내용을 다시 입력해주세요.");
        } else {
            bno = $("#board_bno").text();
            type = "GET";
            url = "/board/read/reply/" + bno;

            var data = {
                "contents": contents
            };

            $.ajax({
                url: url,
                type: type,
                data: data,
                complete: function () {
                    location.reload();
                }
            });
        }
    });

    //==========================================================
    //reply Delete 버튼 이벤트
    //==========================================================
    $("button[name='delete']").click(function () {

        rno = this.value;

        $("#modal-deleteTitle").text("댓글 삭제");
        $("#infoDelete").modal();
    });

    //modalDelete 버튼 눌렀을 때
    $("#modalDeleteButton").click(function () {

        type = "DELETE";
        url = "/board/read/reply/" + rno;

        $.ajax({
            url: url,
            type: type,
            complete: function () {
                location.reload();
            }
        });
    });
});