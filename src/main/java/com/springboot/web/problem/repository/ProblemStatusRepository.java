package com.springboot.web.problem.repository;


import com.springboot.web.problem.domain.ProblemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProblemStatusRepository extends JpaRepository<ProblemStatus,Long> {

    List<ProblemStatus> findByEmail(String email);

    @Query("select ps from ProblemStatus ps where ps.sNo=:id")
    Optional<ProblemStatus> findBySNo(Long id);

    @Query("select ps from ProblemStatus ps where ps.email=:email and ps.proNo=:proNo")
    List<ProblemStatus> findByEmailAndProNo(String email, long proNo);

}
