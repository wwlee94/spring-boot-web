package com.springboot.web.board.domain;

import javax.persistence.*;

@Entity
public class ReplyLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, nullable = false)
    private String userId;

    @Column(length = 20, nullable = false)
    private int boardId;

    @Column(length = 20, nullable = false)
    private int replyId;

    //JPA 매핑 제외
    //userId + boardId로 좋아요가 등록되어있는 테이블 검색 후
    //검색 된 필드는 replyId가 설정되어 있고 isLike = 1로 설정한다.
    //따라서 replyId에 해당하는 댓글은 좋아요버튼을 눌렀다는 의미
    @Transient
    private int isLike;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }
}
