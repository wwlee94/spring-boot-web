package com.springboot.web.problem.controller;

import com.springboot.web.problem.domain.Problem;
import com.springboot.web.problem.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/problem/*")
public class problemController {

    @Autowired
    ProblemRepository problemRepository;

    @GetMapping("/problemset")
    public ModelAndView problemset(){

//        Problem p = new Problem();
//        p.setProContents("두 정수 A와 B를 입력받은 다음, A+B를 출력하는 프로그램을 작성하시오");
//        p.setProInput("첫째 줄에 A와 B가 주어진다.");
//        p.setProName("A+B");
//        p.setProOutput("첫째 줄에 A+B를 출력한다.");
//
//        Problem p2 = new Problem();
//        p2.setProContents("두 정수 A와 B를 입력받은 다음, A-B를 출력하는 프로그램을 작성하시오.");
//        p2.setProInput("첫째 줄에 A와 B가 주어진다. (0 < A, B < 10)");
//        p2.setProName("A-B");
//        p2.setProOutput("첫째 줄에 A-B를 출력한다.");
//
//        problemRepository.save(p);
//        problemRepository.save(p2);

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
}
