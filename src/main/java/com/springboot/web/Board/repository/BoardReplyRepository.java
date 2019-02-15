package com.springboot.web.Board.repository;

import com.springboot.web.Board.domain.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardReplyRepository extends JpaRepository<BoardReply,Integer> {

}
