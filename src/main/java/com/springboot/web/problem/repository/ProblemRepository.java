package com.springboot.web.problem.repository;

import com.springboot.web.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProblemRepository extends JpaRepository<Problem, Long> {

    //문제 번호 순서대로 내림차순 출력
    @Query("select p from Problem p order by p.proNo desc ")
    List<Problem> findAllOrderByDesc();

}
