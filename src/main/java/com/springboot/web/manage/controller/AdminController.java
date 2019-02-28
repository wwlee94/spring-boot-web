package com.springboot.web.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

    //유저 정보 불러오기
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView user(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("user");

        return mv;
    }

    //문제 정보 불러오기
    @RequestMapping(value = "/problem", method = RequestMethod.GET)
    public ModelAndView problem(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("problem");

        return mv;
    }
}
