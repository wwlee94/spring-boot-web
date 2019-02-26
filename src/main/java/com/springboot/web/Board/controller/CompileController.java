package com.springboot.web.Board.controller;


import com.springboot.web.Board.domain.Compile;
import com.springboot.web.Board.repository.CompileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CompileController {

    @Autowired
    CompileRepository compileRepository;

    @RequestMapping(value = "/compile",method = RequestMethod.GET)
    public String compile(){
        return "compile";
    }

    @RequestMapping(value = "/compile",method = RequestMethod.POST)
    public String compilePost(@RequestParam("contents") String contents){

        Compile compile = new Compile();
        compile.setContents(contents);
        compile.setId(0);
        compile.setResult(0);
        compile.setError("0");
        compileRepository.save(compile);

        return "compile";
    }
    @RequestMapping(value = "/compile/list",method = RequestMethod.GET)
    public ModelAndView compileList(){


        List<Compile> compileList = compileRepository.findAll();
        for(int i=0;i<compileList.size();i++){

            int result = compileList.get(i).getResult();
            if(result==-1){
                compileList.get(i).setStrResult("틀렸습니다.");
            }
            else if(result==-2){
                compileList.get(i).setStrResult("컴파일 에러입니다.");
            }
            else if(result==0){
                compileList.get(i).setStrResult("채점 대기 중입니다.");
            }
            else if(result == 1){
                compileList.get(i).setStrResult("정답입니다.");
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("compileList");
        modelAndView.addObject("compileList",compileList);

        return modelAndView;
    }
}
