package com.springboot.web.Board.controller;

import com.springboot.web.Board.Date.CurrentTime;
import com.springboot.web.Board.Date.TimeDifference;
import com.springboot.web.Board.domain.Board;
import com.springboot.web.Board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        String userName = "wwlee94";
        board.setUserName(userName);
        //좋아요 개수
        board.setLikeCount(0);

        repository.save(board);
    }

    //list,PUT 요청이 들어오면 수정하기
    //@PUTMapping
    @RequestMapping(value = "/list", method = RequestMethod.PUT)
    public void modify(Board board) {

        //Board board -> bno,title,contents 넘어옴 나머지 설정해주어야함

        //bno에 해당되는 board의 모든 정보 가져옴
        Board getBoard = repository.findAllBybno(board.getBno());

        board.setUserName(getBoard.getUserName());
        board.setLikeCount(getBoard.getLikeCount());
        board.setDateTime(getBoard.getDateTime());

        /*
        //TODO: 수정시간 컬럼을 따로 만든 후에 수정시간만 변경 -> 작성일은 그대로 적용
        //수정했으니 dateTime 현재 시간으로 갱신
        CurrentTime currentTime = new CurrentTime();
        String dateTime = currentTime.getStringCurrentTime();
        board.setDateTime(dateTime);
        */
        repository.save(board);
    }

    //list,DELETE 요청이 들어오면 삭제하기
    //@DELETEMapping
    @RequestMapping(value = "/list/{bno}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("bno") int bno) {
        repository.deleteById(bno);
    }

    //list/realTime -> list 화면의 실시간 시간변경
    //GET으로 하면 안가져와짐 POST로만
    //RequestParam -> 안가져와짐 // ->RequestBody OK
    //int bno = Integer.valueOf(String.valueOf(list.get(i).bno)) 변수 가져다 쓰려면 이렇게해야 오류 안남
    @RequestMapping(value = "/list/realTime", method = RequestMethod.POST)
    @ResponseBody
    public List<Map> listRealTime(@RequestBody Map<String, Object> map) throws ParseException {
        List<Map> list = (List) map.get("boardList");

        timeDifference.listRealTimeDifference(list);

        //list 안의 값을 바꾸고 map을 넘겨줘도 바꿔서 넘겨짐
        return list;
    }

}
