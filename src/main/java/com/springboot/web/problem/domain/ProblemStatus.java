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
    @Column (length = 20, nullable = false)
    private String email;

    //제출한 문제 랭기지
    @Column (length = 20, nullable = false)
    private int language;

    @Column(length = 1500, nullable = false)
    private String source;

    //문제 채점 상태
    @Column(length = 20)
    private int result = 0;

    @Transient
    private String strResult;

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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getStrResult() {
        return strResult;
    }

    public void setStrResult(String strResult) {
        this.strResult = strResult;
    }
}
