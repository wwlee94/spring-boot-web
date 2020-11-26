package com.springboot.web.problem.controller;

import com.springboot.web.board.date.ConverterTime;
import com.springboot.web.board.date.TimeDifference;
import com.springboot.web.login.oauth.user.User;
import com.springboot.web.login.security.SecurityMember;
import com.springboot.web.problem.domain.Problem;
import com.springboot.web.problem.domain.ProblemStatus;
import com.springboot.web.problem.repository.ProblemRepository;
import com.springboot.web.problem.repository.ProblemStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/problem/*")
public class ProblemController {

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    ProblemStatusRepository problemStatusRepository;

    //사용자 정보 가져오기 위한 변수
    private Object object;
    private String email;

    TimeDifference timeDifference = new TimeDifference();

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

    //사용자가 문제를 제출 했을 경우 데이터 베이스에 값을 저장 한후 , 채점 서버에 알려준다.
    @RequestMapping(value = "/problem/compile", method = RequestMethod.POST)
    public String compile(ProblemStatus problemStatus) throws ParseException {

        object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (object.getClass().getName().equals("com.springboot.web.login.OAuth.user.User")) {
            email = ((User) object).getEmail();
        } else if (object.getClass().getName().equals("com.springboot.web.login.security.SecurityMember")) {
            email = ((SecurityMember) object).getUsername();
        }

        //TODO : security 처리 후 사용자 아이디 받아와서 처리
        problemStatus.setEmail(email);
        //현재 시간 추가하기
        ConverterTime converterTime = new ConverterTime();
        String date = converterTime.getStringDateTime();
        problemStatus.setDateTime(date);

        //DB에게 source 저장 후
        problemStatusRepository.save(problemStatus);

        //List<ProblemStatus> psList = problemStatusRepository.findByEmailAndProNo(problemStatus.getEmail(),problemStatus.getProNo());
        //timeDifference.compileRealTimeDifference(psList);
        //ModelAndView mv = new ModelAndView();
        //mv.setViewName("problem/compileList");
        //mv.addObject("compileList", psList);

        return "redirect:/problem/compileList";
    }

    @RequestMapping(value = "/compileList", method = RequestMethod.GET)
    public ModelAndView compileList() throws ParseException {
        List<ProblemStatus> psList = problemStatusRepository.findAllByDesc();

        for (int i = 0; i < psList.size(); i++) {

            int result = psList.get(i).getResult();
            //setResult,setStrResult
            psList.get(i).setResult(result);
        }

        timeDifference.compileRealTimeDifference(psList);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("problem/compileList");
        mv.addObject("compileList", psList);

        return mv;
    }

    //compile/list/realTime -> list 화면의 실시간 시간변경
    //GET으로 하면 안가져와짐 POST로만
    //RequestParam -> 안가져와짐 // ->RequestBody OK
    //int bno = Integer.valueOf(String.valueOf(list.get(i).bno)) 변수 가져다 쓰려면 이렇게해야 오류 안남
    @RequestMapping(value = "/compileList/realTime", method = RequestMethod.POST)
    @ResponseBody
    public List<Map> listRealTime(@RequestBody Map<String, Object> map) throws ParseException {
        List<Map> list = (List) map.get("psList");

        timeDifference.listRealTimeDifference(list);

        for (int i = 0; i < list.size(); i++) {
            int liResult = (int) list.get(i).get("result");
            if (liResult == 0) {
                long sNo = (int) list.get(i).get("sNo");
                System.out.println("sNo = " + sNo);
                //TODO: 처리할 작업이 있으면 1초마다 실행됨..
                ProblemStatus ps = problemStatusRepository.findBySNo(sNo);
                int psResult = ps.getResult();
                if (psResult == -2) {
                    list.get(i).put("result", -2);
                    list.get(i).put("strResult", "컴파일 에러");
                } else if (psResult == -1) {
                    list.get(i).put("result", -1);
                    list.get(i).put("strResult", "틀렸습니다!");
                } else if (psResult == 1) {
                    list.get(i).put("result", 1);
                    list.get(i).put("strResult", "정답입니다!!");
                }
            }
        }
        ///TODO:result 실시간 업데이트 효율적으로 어케..?

        //list 안의 값을 바꾸고 map을 넘겨줘도 바꿔서 넘겨짐
        return list;
    }
}
