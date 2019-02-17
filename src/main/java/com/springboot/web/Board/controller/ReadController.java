package com.springboot.web.Board.controller;

import com.springboot.web.Board.Date.CurrentTime;
import com.springboot.web.Board.Date.TimeDifference;
import com.springboot.web.Board.domain.Board;
import com.springboot.web.Board.domain.BoardReply;
import com.springboot.web.Board.domain.Likes;
import com.springboot.web.Board.repository.BoardReplyRepository;
import com.springboot.web.Board.repository.BoardRepository;
import com.springboot.web.Board.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/board/*")
public class ReadController {

    //Board
    @Autowired
    private BoardRepository repository;
    //Likes
    @Autowired
    private LikesRepository likesRepository;
    //reply
    @Autowired
    private BoardReplyRepository replyRepository;

    //isLike 좋아요 유무
    private int isLike = 0;

    //작성일 구해주는 객체
    private TimeDifference timeDifference = new TimeDifference();

    //게시글 하나를 클릭했을 때 처리
    //read,GET 요청이 들어오면 자세히 보여주기
    @RequestMapping(value = "/read/{bno}", method = RequestMethod.GET)
    public ModelAndView read(@PathVariable("bno") int bno) throws ParseException {

        Board board = repository.findAllBybno(bno);

        //Board 단일객체의 작성일자 구하는 메소드
        timeDifference.getBoardTimeDifference(board);

        //좋아요인지 좋아요 취소인지
        //임시 ID
        String userId = "wwlee94";
        Likes likes = likesRepository.findAllByBoardIdAndUserId(bno, userId);
        if (likes != null && !likes.equals("")) {
            System.out.println("게시글 좋아요 눌러진 상태 = 좋아요 취소");
            isLike = 1;
        } else {
            System.out.println("게시글 좋아요 안 눌러진 상태 = 좋아요");
            isLike = 0;
        }

        //게시판 댓글 리스트를 구하는 과정
        List<BoardReply> boardReplyList = replyRepository.findAllByBnoAndUserName(bno,userId);
        timeDifference.getReplyTimeDifference(boardReplyList);

        //Model 객체를 파라미터로 받으면-> 데이터를 뷰에 넘길수 있음 or
        //ModelAndView -> 데이터와 뷰를 동시에 설정이 가능
        ModelAndView mv = new ModelAndView();
        mv.setViewName("board/read");
        mv.addObject("board", board);
        mv.addObject("isLike", isLike);
        mv.addObject("boardReplyList",boardReplyList);
        return mv;
    }

    //read/like,GET 요청이 들어오면 좋아요 처리
    //@PathVariable -> url에 변수 가져올 수 있음
    //@RequestParam -> url 요청시 같이 보낸 데이터 조회 가능
    @RequestMapping(value = "/read/like/{bno}", method = RequestMethod.GET)
    public String like(@PathVariable("bno") int bno
            , @RequestParam("likeCount") int likeCount
            ,@RequestParam("isLike") String like) {

        Board board = repository.findAllBybno(bno);
        board.setLikeCount(likeCount);
        repository.save(board);

        //좋아요 취소 버튼을 누르고 Controller에 요청 -> 삭제
        if(like.equals("0")){
            String userId = "wwlee94";
            likesRepository.deleteLikesByBoardIdAndUserId(bno,userId);
            System.out.println(bno);
            System.out.println("likes delete");
        }
        //좋아요 버튼을 누르고 Controller에 요청 -> 추가
        else{
            //임시 ID
            String userId = "wwlee94";
            Likes likes = new Likes();
            likes.setId(0);
            likes.setBoardId(bno);
            likes.setUserId(userId);
            likesRepository.save(likes);
            System.out.println("likes insert");
        }
        return "board/read";
    }

    //read/reply,GET 요청이 들어오면 댓글 추가 처리
    @RequestMapping(value = "/read/reply/{bno}",method = RequestMethod.GET)
    public String reply(@PathVariable("bno") int bno,@RequestParam("contents") String contents){

        BoardReply boardReply = new BoardReply();
        boardReply.setRno(0); // GeneratedValue
        boardReply.setBno(bno);
        boardReply.setUserName("wwlee94");
        boardReply.setContents(contents);
        boardReply.setLikeCount(0);

        //현재 시간 추가하기
        //현재 시간 추가하기
        CurrentTime currentTime = new CurrentTime();
        String date = currentTime.getStringCurrentTime();
        boardReply.setDateTime(date);

        replyRepository.save(boardReply);

        return "board/read";
    }

    // read/reply,DELETE 요청이 들어오면 댓글 삭제 처리
    @RequestMapping(value = "/read/reply/{rno}",method = RequestMethod.DELETE)
    public String replyDelete(@PathVariable("rno") int rno){
        replyRepository.deleteBoardReplyByRno(rno);
        return "board/read";
    }
}
