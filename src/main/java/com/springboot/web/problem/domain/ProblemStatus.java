package com.springboot.web.problem.domain;

import javax.persistence.*;

@Entity
public class ProblemStatus {

    //채점 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sNo;

    //문제 번호
    @Column(length = 20, nullable = false)
    private long proNo;

    //문제 제출 ID
    @Column(length = 50, nullable = false)
    private String email;

    //제출한 문제 랭기지
    @Column(length = 20, nullable = false)
    private int language;

    @Column(length = 1500, nullable = false)
    private String source;

    //dateTime
    @Column(length = 255, nullable = false)
    private String dateTime;

    //문제 채점 상태
    @Column(length = 20, nullable = false)
    private int result = 0;

    @Transient
    private String strResult;

    //JPA 매핑 제외
    //컴파일 작성일 -> ex) 1시간 전/10개월 전
    @Transient
    private String timeDifference;

    public long getsNo() {
        return sNo;
    }

    public void setsNo(long sNo) {
        this.sNo = sNo;
    }

    public long getProNo() {
        return proNo;
    }

    public void setProNo(long proNo) {
        this.proNo = proNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getResult() { return result; }

    public void setResult(int result) {
        this.result = result;

        if (result == -1) {
            this.setStrResult("틀렸습니다.");
        } else if (result == -2) {
            this.setStrResult("컴파일 에러입니다.");
        } else if (result == 0) {
            this.setStrResult("채점 대기 중입니다.");
        } else if (result == 1) {
            this.setStrResult("정답입니다.");
        }
    }

    public String getStrResult() {
        return strResult;
    }

    public void setStrResult(String strResult) {
        this.strResult = strResult;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }
}
