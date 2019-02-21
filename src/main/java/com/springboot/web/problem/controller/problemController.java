package com.springboot.web.problem.controller;

import com.springboot.web.problem.domain.Problem;
import com.springboot.web.problem.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/problem")
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
        mv.setViewName("/problem/problemset");
        mv.addObject("problemList", problemList);

        return mv;
    }
}
