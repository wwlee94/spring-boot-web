package com.springboot.web.Board.domain;

import javax.persistence.*;

@Entity
public class Board {

    //TODO: generatedValue가 다른 테이블과 겹쳐서 증가되는 것 방지하려면??
    //TODO: 수정일도 따로 추가!?
    @Id
    @GeneratedValue
    //게시판 글 번호
    private int bno;

    //게시판 작성자
    @Column(length = 20, nullable = false)
    private String userName;

    //게시판 글 제목
    @Column(length = 20, nullable = false)
    private String title;

    //게시판 글 내용
    @Column(length = 100, nullable = false)
    private String contents;

    //게시판 등록 시간
    @Column(nullable = false)
    private String dateTime;

    //좋아요 개수
    private int likeCount;

    //JPA 매핑 제외
    //게시판 작성일 (몇 시간 전에 등록했는지)
    @Transient
    private String timeDifference;

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }
}
