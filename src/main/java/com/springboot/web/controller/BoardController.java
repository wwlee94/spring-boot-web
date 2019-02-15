package com.springboot.web.controller;

import com.springboot.web.domain.Board;
import com.springboot.web.domain.Likes;
import com.springboot.web.repository.BoardRepository;
import com.springboot.web.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/board/*")
public class BoardController {

    //Board
    @Autowired
    private BoardRepository repository;
    //Likes
    @Autowired
    private LikesRepository likesRepository;

    //isLike 좋아요 유무
    private int isLike = 0;

    @RequestMapping("/index.html")
    public String index() {
        return "redirect:/index.html";
    }

    @RequestMapping("/about.html")
    public String about() {
        return "redirect:/about.html";
    }

    @RequestMapping("/post.html")
    public String post() {
        return "redirect:/post.html";
    }

    //list,GET 요청이 들어오면 보여주기
    @RequestMapping("/list")
    public ModelAndView list() {
        List<Board> boardList = repository.findAllOrderByAsc();
        //Model 객체를 파라미터로 받으면-> 데이터를 뷰에 넘길수 있음 or
        //ModelAndView -> 데이터와 뷰를 동시에 설정이 가능
        ModelAndView mv = new ModelAndView();
        mv.setViewName("board/list");
        mv.addObject("boardList", boardList);
        return mv;
    }

    //list,POST 요청이 들어오면 추가하기
    //@POSTMapping
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void create(Board board) {
        repository.save(board);
    }

    //list,PUT 요청이 들어오면 수정하기
    //@PUTMapping
    @RequestMapping(value = "/list", method = RequestMethod.PUT)
    public void modify(Board board) {
        repository.save(board);
    }

    //list,DELETE 요청이 들어오면 삭제하기
    //@DELETEMapping
    @RequestMapping(value = "/list/{bno}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("bno") int bno) {
        repository.deleteById(bno);
    }

    //게시글 하나를 클릭했을 때 처리
    //read,GET 요청이 들어오면 자세히 보여주기
    @RequestMapping(value = "/read/{bno}", method = RequestMethod.GET)
    public ModelAndView read(@PathVariable("bno") int bno) {

        Board board = repository.findAllBybno(bno);

        //좋아요인지 좋아요 취소인지
        //임시 ID
        int userId = 1;
        Likes likes = likesRepository.findAllByBoardIdAndUserId(bno, userId);
        if (likes != null && !likes.equals("")) {
            System.out.println("좋아요 취소");
            isLike = 1;
        } else {
            System.out.println("좋아요");
            isLike = 0;
        }
        //Model 객체를 파라미터로 받으면-> 데이터를 뷰에 넘길수 있음 or
        //ModelAndView -> 데이터와 뷰를 동시에 설정이 가능
        ModelAndView mv = new ModelAndView();
        mv.setViewName("board/read");
        mv.addObject("board", board);
        mv.addObject("isLike", isLike);
        return mv;
    }
    //read,POST 요청이 들어오면 like update

    @RequestMapping(value = "/read/like/{bno}", method = RequestMethod.GET)
    public String like(@PathVariable("bno") int bno
            , @RequestParam("likeCount") int likeCount
            ,@RequestParam("isLike") String like) {

        Board board = repository.findAllBybno(bno);
        board.setLikeCount(likeCount);
        repository.save(board);

        //좋아요 취소 버튼을 누르고 Controller에 요청 -> 삭제
        if(like.equals("0")){
            int userId = 1;
            likesRepository.deleteByBoardIdAndUserId(bno,userId);
            System.out.println(bno);
            System.out.println("likes delete");
        }
        //좋아요 버튼을 누르고 Controller에 요청 -> 추가
        else{
            //임시 ID
            int userId = 1;
            Likes likes = new Likes();
            likes.setId(0);
            likes.setBoardId(bno);
            likes.setUserId(userId);
            likesRepository.save(likes);
            System.out.println("likes insert");
        }
        return "board/read";
    }
}
