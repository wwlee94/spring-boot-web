package com.springboot.web.problem.domain;

import javax.persistence.*;

@Entity
public class Problem {

    //문제 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long proNo;

    //문제 제목
    @Column(length = 20, nullable = false)
    private String proName;

    //문제 내용
    @Column(length = 100, nullable = false)
    private String proContents;

    //문제입력 설명
    @Column(length = 100, nullable = false)
    private String proInput;

    //문제출력 설명
    @Column(length = 100, nullable = false)
    private String proOutput;

    //맞은 사람 수
    private int proSolveCount = 0;

    //문제 제출 수
    private int proSubmitCount = 0;

    //JPA 매핑 제외
    //문제 맞춘 비욜 = 맞은사람 수 / 제출 수
    @Transient
    private float proSolveLate = 0;

    public long getProNo() {
        return proNo;
    }

    public void setProNo(long proNo) {
        this.proNo = proNo;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProContents() {
        return proContents;
    }

    public void setProContents(String proContents) {
        this.proContents = proContents;
    }

    public String getProInput() {
        return proInput;
    }

    public void setProInput(String proInput) {
        this.proInput = proInput;
    }

    public String getProOutput() {
        return proOutput;
    }

    public void setProOutput(String proOutput) {
        this.proOutput = proOutput;
    }

    public int getProSolveCount() {
        return proSolveCount;
    }

    public void setProSolveCount(int proSolveCount) {
        this.proSolveCount = proSolveCount;
    }

    public int getProSubmitCount() {
        return proSubmitCount;
    }

    public void setProSubmitCount(int proSubmitCount) {
        this.proSubmitCount = proSubmitCount;
    }

    public float getProSolveLate() {
        return proSolveLate;
    }

    public void setProSolveLate(float proSolveLate) {
        this.proSolveLate = proSolveLate;
    }
}
