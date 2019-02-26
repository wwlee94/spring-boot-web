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
    public ModelAndView problemset() {

        List<Problem> problemList = problemRepository.findAllOrderByDesc();

        ModelAndView mv = new ModelAndView();
        mv.setViewName("problem/problemset");
        mv.addObject("problemList", problemList);

        return mv;
    }

    //문제 하나를 클릭했을 때 처리
    //read,GET 요청이 들어오면 자세히 보여주기
    @RequestMapping(value = "/proView/{proNo}", method = RequestMethod.GET)
    public ModelAndView proView(@PathVariable("proNo") long proNo) throws ParseException {

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
    public ModelAndView submit(@PathVariable("proNo") long proNo) throws ParseException {

        Optional<Problem> p = problemRepository.findById(proNo);
        Problem problem = p.get();

        ModelAndView mv = new ModelAndView();
        mv.setViewName("problem/submit");
        mv.addObject("problem", problem);

        return mv;
    }

    //사용자가 문제를 제출 했을 경우 데이터 베이스에 값을 저장 한후 , 소켓 통신으로 채점 서버에 알려준다.
    @RequestMapping(value = "/problem/compile", method = RequestMethod.POST)
    public ModelAndView statusAction(ProblemStatus problemStatus) {

        //TODO : security 처리 후 사용자 아이디 받아와서 처리
        problemStatus.setEmail("dndnjs123");

        //DB에게 source 저장 후
        problemStatusRepository.save(problemStatus);

        List<ProblemStatus> psList = problemStatusRepository.findByEmailAndProNo(problemStatus.getEmail(),problemStatus.getProNo());
        for (int i = 0; i < psList.size(); i++) {

            int result = psList.get(i).getResult();
            if (result == -1) {
                psList.get(i).setStrResult("틀렸습니다.");
            } else if (result == -2) {
                psList.get(i).setStrResult("컴파일 에러입니다.");
            } else if (result == 0) {
                psList.get(i).setStrResult("채점 대기 중입니다.");
            } else if (result == 1) {
                psList.get(i).setStrResult("정답입니다.");
            }
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("problem/compileList");
        mv.addObject("compileList", psList);

        return mv;
    }

    @RequestMapping(value = "/problem/compileList", method = RequestMethod.GET)
    public ModelAndView compileList() {
        List<ProblemStatus> psList = problemStatusRepository.findAll();
        for (int i = 0; i < psList.size(); i++) {

            int result = psList.get(i).getResult();
            if (result == -1) {
                psList.get(i).setStrResult("틀렸습니다.");
            } else if (result == -2) {
                psList.get(i).setStrResult("컴파일 에러입니다.");
            } else if (result == 0) {
                psList.get(i).setStrResult("채점 대기 중입니다.");
            } else if (result == 1) {
                psList.get(i).setStrResult("정답입니다.");
            }
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("problem/compileList");
        mv.addObject("compileList", psList);

        return mv;
    }
}
