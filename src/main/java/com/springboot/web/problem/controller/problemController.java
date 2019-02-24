package com.springboot.web.problem.controller;

import com.springboot.web.problem.SubmitClient;
import com.springboot.web.problem.domain.Problem;
import com.springboot.web.problem.domain.ProblemStatus;
import com.springboot.web.problem.repository.ProblemRepository;
import com.springboot.web.problem.repository.ProblemStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/problem/*")
public class problemController {

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    ProblemStatusRepository problemStatusRepository;

    @Autowired
    SubmitClient submitClient;

    @GetMapping("/problemset")
    public ModelAndView problemset(){

        List<Problem> problemList = problemRepository.findAllOrderByDesc();

        ModelAndView mv = new ModelAndView();
        mv.setViewName("problem/problemset");
        mv.addObject("problemList", problemList);

        return mv;
    }

    //문제 하나를 클릭했을 때 처리
    //read,GET 요청이 들어오면 자세히 보여주기
    @RequestMapping(value = "/proView/{proNo}", method = RequestMethod.GET)
    public ModelAndView proView(@PathVariable("proNo")long proNo) throws ParseException {

        Optional<Problem> p = problemRepository.findById(proNo);
        Problem problem = p.get();

        //Model 객체를 파라미터로 받으면-> 데이터를 뷰에 넘길수 있음 or
        //ModelAndView -> 데이터와 뷰를 동시에 설정이 가능

        ModelAndView mv = new ModelAndView();
        mv.setViewName("problem/proView");

        mv.addObject("problem", problem);

        return mv;
    }

    //문제 제출을 클릭했을 때 처리
    //read,GET 요청이 들어오면 자세히 보여주기
    @RequestMapping(value = "/submit/{proNo}", method = RequestMethod.GET)
    public ModelAndView submit(@PathVariable("proNo")long proNo) throws ParseException {

        Optional<Problem> p = problemRepository.findById(proNo);
        Problem problem = p.get();

        ModelAndView mv = new ModelAndView();
        mv.setViewName("problem/submit");
        mv.addObject("problem", problem);

        return mv;
    }

    //사용자가 문제를 제출 했을 경우 데이터 베이스에 값을 저장 한후 , 소켓 통신으로 채점 서버에 알려준다.
    @RequestMapping(value = "/statusAction", method=RequestMethod.POST)
    public ModelAndView statusAction(ProblemStatus problemStatus){

        System.out.println(problemStatus.getLanguage());
        System.out.println(problemStatus.getProNo());
        System.out.println(problemStatus.getSource());

        //TODO : security 처리 후 사용자 아이디 받아와서 처리
        problemStatus.setEmail("dndnjs123");

        //DB에게 source 저장 후
        ProblemStatus p =problemStatusRepository.save(problemStatus);

        //cServer에게 알림
        //채점 받은후 리턴 받은 채점 번호.
        long sno = submitClient.sendAndReceive(""+p.getSNo()+"\n");

        //TODO : security 처리 후 사용자 아이디 받아와서 처리
        //사용자의 아이디와 같은 경우 모든 값을 결과view에 출력
        List<ProblemStatus> psList = problemStatusRepository.findByEmail("dndnjs123");

        //채점 번호의 결과를 view 에 출력 해줌 .

        ModelAndView mv = new ModelAndView();
        mv.setViewName("problem/status");
        mv.addObject("psList", psList);

        return mv;

    }
}
