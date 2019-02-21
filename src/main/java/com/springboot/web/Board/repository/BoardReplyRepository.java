package com.springboot.web.Board.repository;

import com.springboot.web.Board.domain.BoardReply;
import com.springboot.web.Board.domain.ReplyLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Integer> {

    //댓글 리스트 보여주기 위한 쿼리문
    @Query("select br from BoardReply br where br.bno=:bno order by br.rno asc")
    List<BoardReply> findAllByBno(@Param("bno") int bno);


    //게시판 번호 + 사용자ID로 댓글 찾는 쿼리문
    @Query("select br from BoardReply br where br.bno=:bno and br.userName=:userName order by br.rno asc")
    List<BoardReply> findAllByBnoAndUserName(@Param("bno") int bno, @Param("userName") String userName);

    //댓글 번호로 해당 댓글 정보 검색 -> likeCount 업데이트 위함
    @Query("select br from BoardReply br where br.rno=:rno")
    BoardReply findAllByRno(@Param("rno") int rno);

    //쿼리 이름만으로 댓글 삭제
    @Transactional
    void deleteBoardReplyByRno(int rno);
}
