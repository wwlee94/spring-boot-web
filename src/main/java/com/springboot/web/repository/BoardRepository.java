package com.springboot.web.repository;

import com.springboot.web.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//원하는 Repositoey 메소드를 만드려면
//1. @Query 2. 메소드 네이밍으로 쿼리 생성
public interface BoardRepository extends JpaRepository<Board,Integer> {
    // 테이블 이름 board -> b로 축약한 것임
    @Query("select b from Board b order by b.bno asc")
    List<Board> findAllOrderByAsc();

    //bno으로 게시판 글 하나 찾기
    @Query("select b from Board b where b.bno = :bno")
    Board findAllBybno(@Param("bno") int bno);
}
