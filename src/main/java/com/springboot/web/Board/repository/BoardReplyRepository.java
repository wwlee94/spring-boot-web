package com.springboot.web.Board.repository;

import com.springboot.web.Board.domain.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReply,Integer> {

    //게시판 번호 + 사용자ID로 댓글 찾는 쿼리문
    @Query("select br from BoardReply br where br.bno=:bno and br.userName=:userName")
    List<BoardReply> findAllByBnoAndUserName(@Param("bno") int bno,@Param("userName") String userName);

    //쿼리 이름만으로 댓글 삭제
    @Transactional
    void deleteBoardReplyByRno(int rno);
}
