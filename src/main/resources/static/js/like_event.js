var action = '';
var type = '';
var url = '';

var bno = 0;
var rno = 0;
var likeCount = 0;

var like = "좋아요";
var clike = "좋아요 취소";
//input or textarea 값 받아올 때 val()
//그 외엔 text()
$(document).ready(function () {

    //클릭 전 데이터 값 체크
    //data-like -> 속성의 값 가져오는 법
    var element = document.querySelector("#post_like");
    var isLike = element.dataset.like;
    if(isLike==='1'){
        element.text = "좋아요 취소";
    }else{
        element.text= "좋아요";
    }

    //==========================================================
    //post_like 클릭했을 때
    //==========================================================
    $("#post_like").click(function () {

        if(isLike==='0') {
            likeCount = $("#post_likecount").text();
            likeCount++;
            $("#post_likecount").text(likeCount);

            $(this).text(clike);
            isLike="1";
        }
        else{
            likeCount = $("#post_likecount").text();
            likeCount--;
            $("#post_likecount").text(likeCount);

            $(this).text(like);
            isLike="0";
        }

        bno = $("#board_bno").text();
        type = "GET";
        url = "/board/read/like/"+bno;

        var data={
            "likeCount" : likeCount,
            "isLike" : isLike
        };

        $.ajax({
            url:url,
            type:type,
            data:data,
            //complete:function () {location.reload();}
        });

    });

    //==========================================================
    //reply 버튼 이벤트
    //==========================================================
    $("#replyBtn").click(function () {

        //contents -> input reply의 댓글 내용
        var contents = $("#reply_input").val();

        if(contents==="" || contents==null){
            alert("댓글 내용을 다시 입력해주세요.");
        }
        else {
            bno=$("#board_bno").text();
            type="GET";
            url="/board/read/reply/"+bno;

            var data = {
                "contents": contents
            };

            $.ajax({
                url: url,
                type: type,
                data: data,
                complete:function () {location.reload();}
            });
        }
    });

    //==========================================================
    //reply Delete 버튼 이벤트
    //==========================================================
    $("button[name='delete']").click(function () {

        rno=this.value;

        $("#modal-title").text("댓글 삭제");
        $("#infoDelete").modal();
    });

    //modalDelete 버튼 눌렀을 때
    $("#modalDeleteButton").click(function () {

        type="DELETE";
        url="/board/read/reply/"+rno;

        $.ajax({
            url:url,
            type:type,
            complete:function () {location.reload();}
        });
    });
});