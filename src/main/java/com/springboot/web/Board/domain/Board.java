package com.springboot.web.Board.domain;

import javax.persistence.*;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //게시판 글 번호
    private int bno;

    //게시판 작성자
    @Column(length = 20, nullable = false)
    private String userName;

    //게시판 글 제목
    @Column(length = 50, nullable = false)
    private String title;

    //게시판 글 내용
    @Column(length = 250, nullable = false)
    private String contents;

    //게시판 등록 시간
    @Column(nullable = false)
    private String dateTime;

    //좋아요 개수
    @Column(nullable = false)
    private int likeCount;

    //TODO: 조회수 추가!?

    //JPA 매핑 제외
    //게시판 작성일 -> ex) 1시간 전/10개월 전
    @Transient
    private String timeDifference;

    //JPA 매핑 제외
    //게시판 작성일 date -> 2017-10-28
    @Transient
    private String date;

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

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}
