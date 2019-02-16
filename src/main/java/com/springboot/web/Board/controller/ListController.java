package com.springboot.web.Board.controller;

import com.springboot.web.Board.Date.CurrentTime;
import com.springboot.web.Board.Date.TimeDifference;
import com.springboot.web.Board.domain.Board;
import com.springboot.web.Board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/board/*")
public class ListController {

    //Board
    @Autowired
    private BoardRepository repository;

    //작성일 구해주는 객체
    private TimeDifference timeDifference = new TimeDifference();

    //list,GET 요청이 들어오면 보여주기
    @RequestMapping("/list")
    public ModelAndView list() throws ParseException {
        List<Board> boardList = repository.findAllOrderByAsc();

        //Board의 작성일자 구하는 메소드
        //객체 인스턴스라 반환값 안 받아도 적용됨
        timeDifference.getBoardListTimeDifference(boardList);
        System.out.println(boardList.get(0).getTimeDifference());

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
        //현재 시간 추가하기
        CurrentTime currentTime = new CurrentTime();
        String date = currentTime.getStringCurrentTime();
        board.setDateTime(date);
        //email or nickname으로 변경하면 OK
        String userName="wwlee94";
        board.setUserName(userName);

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

}
