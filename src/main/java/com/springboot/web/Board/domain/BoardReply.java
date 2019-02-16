package com.springboot.web.Board.domain;

import javax.persistence.*;

@Entity
public class BoardReply {

    @Id
    @GeneratedValue
    //댓글의 번호
    private int rno;

    //게시판의 글 번호
    @Column(nullable = false)
    private int bno;

    //댓글 작성자
    @Column(length = 20, nullable = false)
    private String userName;
    //댓글 내용 50자
    @Column(length = 50, nullable = false)
    private String contents;

    //댓글 등록 시간
    @Column(nullable = false)
    private  String dateTime;

    //댓글 좋아요
    private int likeCount;

    //JPA 매핑 제외
    //댓글 작성일
    @Transient
    private String timeDifference;

    public int getRno() {
        return rno;
    }

    public void setRno(int rno) {
        this.rno = rno;
    }

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
