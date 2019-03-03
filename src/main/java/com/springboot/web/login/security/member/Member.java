package com.springboot.web.login.security.member;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length=50)
    private String uid;

    @Column(nullable = false, length=200)
    private String upw;

    @Column(nullable = false, unique = true, length=50)
    private String uemail;

    @Column(nullable = false)
    private int eamilCheck;

    @CreationTimestamp
    private Date regdate;

    @UpdateTimestamp
    private Date updatedate;

    public String getEmailstr() {
        return emailstr;
    }

    public void setEmailstr(String emailstr) {
        this.emailstr = emailstr;
    }

    @Transient
    private String emailstr;

    @Transient
    private boolean level1;

    @Transient
    private boolean level2;

    public boolean isLevel1() {
        return level1;
    }

    public void setLevel1(boolean level1) {
        this.level1 = level1;
    }

    public boolean isLevel2() {
        return level2;
    }

    public void setLevel2(boolean level2) {
        this.level2 = level2;
    }

    public boolean isLevel3() {
        return level3;
    }

    public void setLevel3(boolean level3) {
        this.level3 = level3;
    }

    @Transient
    private boolean level3;

    //cascade의 경우에는 엔티티들의 영속관계를 한번에 처리하지 못하기 때문에 이에 대한 cascade 설정을 추가하는것
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    //member와 member_role을 둘다 동시에 조회하기 위해서 fetch 설정을 즉시 로딩으로 EAGER 설정을 주어야 에러가 발생하지 않습니다.
    @JoinColumn(name="uid")
    private List<MemberRole> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUpw() {
        return upw;
    }

    public void setUpw(String upw) {
        this.upw = upw;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public List<MemberRole> getRoles() {
        return roles;
    }

    public void setRoles(List<MemberRole> roles) {
        this.roles = roles;
    }

    public int getEamilCheck() {
        return eamilCheck;
    }

    public void setEamilCheck(int eamilCheck) {
        this.eamilCheck = eamilCheck;
    }
}