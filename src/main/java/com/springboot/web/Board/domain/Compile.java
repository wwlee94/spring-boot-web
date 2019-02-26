package com.springboot.web.Board.domain;

import com.sun.javafx.geom.transform.Identity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Compile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String contents;

    private String error;

    //-2:컴파일 에러 -1: 실패 0: 검사중 1: 성공
    private int result;

    @Transient
    private String strResult;

}
