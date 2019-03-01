package com.springboot.web.login.security.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUemail(String email);

    @Modifying
    @Transactional
    @Query("update Member m set m.eamilCheck = 1 where m.uemail = :uemail")
    void updateEmailCheck(@Param("uemail")String uemail);
}
