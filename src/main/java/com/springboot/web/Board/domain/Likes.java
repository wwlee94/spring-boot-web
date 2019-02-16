package com.springboot.web.Board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Likes {

    @Id
    @GeneratedValue
    private int id;

    //unique = true -> userId + boardId로 유일하도록
    @Column(length = 20, nullable = false)
    private String userId;

    @Column(length = 20, nullable = false)
    private int boardId;

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
}
