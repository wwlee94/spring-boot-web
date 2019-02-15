package com.springboot.web.repository;

import com.springboot.web.domain.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardReplyRepository extends JpaRepository<BoardReply,Integer> {

}
