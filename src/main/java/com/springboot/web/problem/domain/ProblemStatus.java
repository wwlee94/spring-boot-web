package com.springboot.web.problem.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
@Getter
@Setter
public class ProblemStatus {

    //채점 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sNo;

    //문제 번호
    @Column(length = 20, nullable = false)
    private long proNo;

    //문제 채점 상태
    @Column(length = 20)
    private String sContents = "Null";

    //문제 제출 ID
    @Column (length = 20, nullable = false)
    private String email;

    //제출한 문제 랭기지
    @Column (length = 20, nullable = false)
    private int language;

    @Column(length = 1500, nullable = false)
    private String source;

}
