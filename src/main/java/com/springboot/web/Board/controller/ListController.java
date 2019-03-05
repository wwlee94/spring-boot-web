package com.springboot.web.Board.controller;

import com.springboot.web.Board.Date.ConverterTime;
import com.springboot.web.Board.Date.TimeDifference;
import com.springboot.web.Board.domain.Board;
import com.springboot.web.Board.paging.Paging;
import com.springboot.web.Board.repository.BoardRepository;
import com.springboot.web.login.security.SecurityMember;
import com.springboot.web.login.OAuth.user.User;
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
public class ListController {

    //Board
    @Autowired
    private BoardRepository repository;

    //작성일 구해주는 객체
    private TimeDifference timeDifference = new TimeDifference();

    //사용자 정보 가져오기 위한 변수
    private Object object;
    private String email;

    private Paging paging = new Paging();

    //list,GET 요청이 들어오면 게시글 리스트 보여주기
    //@RequestParam ->  ?index=1&page=2 이렇게 오는 값을 받을 수 있다.
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1", required = false) int page) throws ParseException {

        int totalCount = repository.findAllForCount();

        paging.init(totalCount, page);
//        System.out.println("전체 게시글: " + totalCount);
//        System.out.println("총 페이지: " + paging.getTotalPage());
//        System.out.println("시작 페이지: " + paging.getStartPage() + " 마지막 페이지: " + paging.getEndPage() + " 현재 페이지: " + paging.getPage());

        int listCount = paging.getListCount();
        //페이지의 값에 따른(몇 개를 읽을건지) 게시글 가져오기
        int pageNum = (page - 1) * listCount;
        List<Board> boardList = repository.findAllOrderByAscAboutPage(pageNum, listCount);

        //Board의 작성일자(게시글 등록한지 얼마나 지났는지) 구하는 메소드
        //객체 인스턴스라 반환값 안 받아도 적용됨
        timeDifference.getBoardListTimeDifference(boardList);

        //Model 객체를 파라미터로 받으면-> 데이터를 뷰에 넘길수 있음 or
        //ModelAndView -> 데이터와 뷰를 동시에 설정이 가능
        ModelAndView mv = new ModelAndView();
        mv.setViewName("board/list");
        mv.addObject("boardList", boardList);
        mv.addObject("paging", paging);
        return mv;
    }

    //list,POST 요청이 들어오면 추가하기
    //@POSTMapping
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void create(Board board) {

        object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (object.getClass().getName().equals("com.springboot.web.login.OAuth.user.User")) {
            email = ((User) object).getEmail();
        } else if (object.getClass().getName().equals("com.springboot.web.login.security.SecurityMember")) {
            email = ((SecurityMember) object).getUsername();
        }

        //현재 시간 추가하기
        ConverterTime converterTime = new ConverterTime();
        String date = converterTime.getStringDateTime();
        board.setDateTime(date);
        //email or nickname으로 변경하면 OK
        String userName = email;
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
