package com.springboot.web.board.repository;

import com.springboot.web.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//원하는 Repositoey 메소드를 만드려면
//TODO: 1.@Query 2.메소드 네이밍으로 쿼리 생성

public interface BoardRepository extends JpaRepository<Board, Integer> {

    // 게시판 번호 순서대로 오름차순 출력
    // 테이블 이름 board -> b로 축약한 것임
    @Query("select count(b) from Board b")
    int findAllForCount();

    // 페이지에 따른 게시글 리스트 출력
    @Query(nativeQuery = true, value = "select * from Board b order by b.bno desc limit :page, :listCount")
    List<Board> findAllOrderByAscAboutPage(@Param("page") int pageNum, @Param("listCount") int listCount);

    //bno으로 게시판 글 하나 찾기
    @Query("select b from Board b where b.bno = :bno")
    Board findAllBybno(@Param("bno") int bno);

    //게시판 글 수정
    @Query("update Board b set b.title=:title, b.contents=:contents where b.bno = :bno")
    Board updateAllByBno(@Param("bno") int bno
            , @Param("title") String title
            , @Param("contents") String contents);
}
