package com.springboot.web.problem.repository;

import com.springboot.web.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProblemRepository extends JpaRepository<Problem,Long> {

    //문제 번호 순서대로 내림차순 출력
    @Query("select p from Problem p order by p.proNo desc ")
    List<Problem> findAllOrderByDesc();


    //    // 게시판 번호 순서대로 오름차순 출력
//    // 테이블 이름 board -> b로 축약한 것임
//    @Query("select b from Board b order by b.bno asc")
//    List<Board> findAllOrderByAsc();
//
//    //bno으로 게시판 글 하나 찾기
//    @Query("select b from Board b where b.bno = :bno")
//    Board findAllBybno(@Param("bno") int bno);
//
//    //게시판 글 수정
//    @Query("update Board b set b.title=:title, b.contents=:contents where b.bno = :bno")
//    Board updateAllByBno(@Param("bno") int bno
//            , @Param("title") String title
//            , @Param("contents") String contents);
}
