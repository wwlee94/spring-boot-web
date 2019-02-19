package com.springboot.web.Board.repository;

import com.springboot.web.Board.domain.ReplyLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ReplyLikesRepository extends JpaRepository<ReplyLikes, Integer> {

    //게시판 번호에 해당되는 댓글들의 좋아요 유무 모두 찾으려고
    @Query("select rl from ReplyLikes rl where rl.boardId = :boardId and rl.userId = :userId")
    List<ReplyLikes> findAllByBoardIdAndUserId(@Param("boardId") int boardId, @Param("userId") String userId);

    @Transactional
    int deleteLikesByReplyIdAndUserId(int replyId, String userId);

}
