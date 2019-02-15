var action = '';
var type = '';
var url = '';

var bno = 0;
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
    }

    //post_like를 클릭했을 때
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
        }

        $.ajax({
            url:url,
            type:type,
            data:data,
            //complete:function () {location.reload();}
        });

    });//post_like

});