package com.springboot.web.Board.controller;

import com.springboot.web.Board.Date.ConverterTime;
import com.springboot.web.Board.Date.TimeDifference;
import com.springboot.web.Board.domain.Board;
import com.springboot.web.Board.domain.BoardReply;
import com.springboot.web.Board.domain.Likes;
import com.springboot.web.Board.domain.ReplyLikes;
import com.springboot.web.Board.repository.BoardReplyRepository;
import com.springboot.web.Board.repository.BoardRepository;
import com.springboot.web.Board.repository.LikesRepository;
import com.springboot.web.Board.repository.ReplyLikesRepository;
import com.springboot.web.login.SecurityMember;
import com.springboot.web.login.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
    //replyLikes
    @Autowired
    private ReplyLikesRepository replyLikesRepository;

    //isLike 좋아요 유무
    private int isLike = 0;

    //작성일 구해주는 객체
    private TimeDifference timeDifference = new TimeDifference();

    //시간 형 변환
    private ConverterTime converterTime = new ConverterTime();

    //사용자 정보 가져오기 위한 변수
    private Object object;
    private String email;

    //게시글 하나를 클릭했을 때 처리
    //read,GET 요청이 들어오면 자세히 보여주기
    @RequestMapping(value = "/read/{bno}", method = RequestMethod.GET)
    public ModelAndView read(@PathVariable("bno") int bno) throws ParseException {

        Board board = repository.findAllBybno(bno);

        //Board 단일객체의 작성일자 구하는 메소드
        timeDifference.getBoardTimeDifference(board);

        //DateTime -> Date형식 String으로 받아오는 함수
        String boardDate = converterTime.getStringDateByBoard(board);
        board.setDate(boardDate);

        object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (object.getClass().getName().equals("com.springboot.web.login.user.User")) {
            email = ((User) object).getEmail();
        } else if (object.getClass().getName().equals("com.springboot.web.login.SecurityMember")) {
            email = ((SecurityMember) object).getUsername();
        }

        //좋아요인지 좋아요 취소인지
        //임시 ID
        String userId = email;
        Likes likes = likesRepository.findAllByBoardIdAndUserId(bno, userId);
        if (likes != null && !likes.equals("")) {
            System.out.println("게시글 좋아요 눌러진 상태 = 좋아요 취소");
            isLike = 1;
        } else {
            System.out.println("게시글 좋아요 안 눌러진 상태 = 좋아요");
            isLike = 0;
        }

        //게시판 댓글 리스트를 구하는 과정
        List<BoardReply> boardReplyList = replyRepository.findAllByBno(bno);
        timeDifference.getReplyTimeDifference(boardReplyList);

        //DateTime -> Date형식 String으로 받아오는 함수
        converterTime.getStringDateByReply(boardReplyList);

        List<ReplyLikes> replyLikesList = replyLikesRepository.findAllByBoardIdAndUserId(bno, userId);
        for (int i = 0; i < replyLikesList.size(); i++) {
            //가져와진 데이터들은 모두 좋아요가 눌러진 상태인 데이터
            replyLikesList.get(i).setIsLike(1);
            System.out.println("댓글 번호" + replyLikesList.get(i).getReplyId());
            System.out.println("좋아요 세팅" + replyLikesList.get(i).getIsLike());
        }

        //Model 객체를 파라미터로 받으면-> 데이터를 뷰에 넘길수 있음 or
        //ModelAndView -> 데이터와 뷰를 동시에 설정이 가능
        ModelAndView mv = new ModelAndView();
        mv.setViewName("board/read");
        mv.addObject("board", board);
        mv.addObject("isLike", isLike);
        mv.addObject("boardReplyList", boardReplyList);
        mv.addObject("replyLikesList", replyLikesList);
        mv.addObject("email", email);
        return mv;
    }

    //read/like,GET 요청이 들어오면 좋아요 처리
    //@PathVariable -> url에 변수 가져올 수 있음
    //@RequestParam -> url 요청시 같이 보낸 데이터 조회 가능
    @RequestMapping(value = "/read/like/{bno}", method = RequestMethod.GET)
    public String like(@PathVariable("bno") int bno
            , @RequestParam("likeCount") int likeCount
            , @RequestParam("isLike") String isLike) {

        Board board = repository.findAllBybno(bno);
        board.setLikeCount(likeCount);
        repository.save(board);

        object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (object.getClass().getName().equals("com.springboot.web.login.user.User")) {
            email = ((User) object).getEmail();
        } else if (object.getClass().getName().equals("com.springboot.web.login.SecurityMember")) {
            email = ((SecurityMember) object).getUsername();
        }

        String userId = email;

        //좋아요 취소 버튼을 누르고 Controller에 요청 -> 삭제
        if (isLike.equals("0")) {
            likesRepository.deleteLikesByBoardIdAndUserId(bno, userId);
            System.out.println(bno);
            System.out.println("likes delete");
        }
        //좋아요 버튼을 누르고 Controller에 요청 -> 추가
        else {
            //임시 ID
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
    @RequestMapping(value = "/read/reply/{bno}", method = RequestMethod.GET)
    public String reply(@PathVariable("bno") int bno, @RequestParam("contents") String contents) {

        object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (object.getClass().getName().equals("com.springboot.web.login.user.User")) {
            email = ((User) object).getEmail();
        } else if (object.getClass().getName().equals("com.springboot.web.login.SecurityMember")) {
            email = ((SecurityMember) object).getUsername();
        }

        BoardReply boardReply = new BoardReply();
        boardReply.setRno(0); // GeneratedValue
        boardReply.setBno(bno);
        boardReply.setUserName(email);
        boardReply.setContents(contents);
        boardReply.setLikeCount(0);

        //현재 시간 추가하기
        //현재 시간 추가하기
        ConverterTime converterTime = new ConverterTime();
        String date = converterTime.getStringDateTime();
        boardReply.setDateTime(date);

        replyRepository.save(boardReply);

        return "board/read";
    }

    // read/reply,DELETE 요청이 들어오면 댓글 삭제 처리
    @RequestMapping(value = "/read/reply/{rno}", method = RequestMethod.DELETE)
    public String replyDelete(@PathVariable("rno") int rno) {
        replyRepository.deleteBoardReplyByRno(rno);
        return "board/read";
    }

    // read/replyLike ,GET 요청이 들어오면 댓글 좋아요 추가,삭제 처리
    @RequestMapping(value = "/read/replyLike/{rno}", method = RequestMethod.GET)
    public String replyLikes(@PathVariable("rno") int rno
            , @RequestParam("likeCount") int likeCount
            , @RequestParam("isLike") String isLike) {

        object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (object.getClass().getName().equals("com.springboot.web.login.user.User")) {
            email = ((User) object).getEmail();
        } else if (object.getClass().getName().equals("com.springboot.web.login.SecurityMember")) {
            email = ((SecurityMember) object).getUsername();
        }

        //게시판 댓글에 해당되는 likeCount 수정
        //임시 ID
        String userId = email;
        //rno로 댓글 객체 가져옴
        BoardReply boardReply = replyRepository.findAllByRno(rno);
        boardReply.setLikeCount(likeCount);
        replyRepository.save(boardReply);

        System.out.println("rno:" + rno + " likeCount : " + likeCount + " isLike : " + isLike);

        //좋아요 취소 버튼을 누르고 Controller에 요청 -> 삭제
        if (isLike.equals("0")) {
            replyLikesRepository.deleteLikesByReplyIdAndUserId(rno, userId);
            System.out.println(rno);
            System.out.println("ReplyLikes delete");
        }
        //좋아요 버튼을 누르고 Controller에 요청 -> 추가
        else {
            ReplyLikes replyLikes = new ReplyLikes();
            replyLikes.setId(0);
            replyLikes.setBoardId(boardReply.getBno());
            replyLikes.setReplyId(rno);
            replyLikes.setUserId(userId);
            replyLikesRepository.save(replyLikes);
            System.out.println("ReplyLikes  insert");
        }
        return "board/read";
    }

    //read/realTime -> list 화면의 실시간 시간변경
    //GET으로 하면 안가져와짐 POST로만
    //RequestParam -> 안가져와짐 // ->RequestBody OK
    //int bno = Integer.valueOf(String.valueOf(list.get(i).bno)) 변수 가져다 쓰려면 이렇게해야 오류 안남
    @RequestMapping(value = "/read/realTime", method = RequestMethod.POST)
    @ResponseBody
    public List<Map> readRealTime(@RequestBody Map<String, Object> map) throws ParseException {
        List<Map> list = (List) map.get("list");

        timeDifference.readRealTimeDifference(list);

        //list 안의 값을 바꾸고 map을 넘겨줘도 바꿔서 넘겨짐
        return list;
    }


}
