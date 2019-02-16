package com.springboot.web.Board.repository;

import com.springboot.web.Board.domain.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReply,Integer> {

    @Query("select br from BoardReply br where br.bno=:bno and br.userName=:userName")
    List<BoardReply> findAllByBnoAndUserName(@Param("bno") int bno,@Param("userName") String userName);
}
