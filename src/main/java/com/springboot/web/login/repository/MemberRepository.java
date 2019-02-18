package com.springboot.web.login.repository;

import com.springboot.web.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUemail(String email);
}
