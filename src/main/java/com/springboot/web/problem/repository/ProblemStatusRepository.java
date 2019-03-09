package com.springboot.web.problem.repository;


import com.springboot.web.problem.domain.ProblemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProblemStatusRepository extends JpaRepository<ProblemStatus,Long> {

    @Query("select ps from ProblemStatus ps order by ps.sNo desc")
    List<ProblemStatus> findAllByDesc();

    @Query("select ps from ProblemStatus ps where ps.sNo=:id")
    ProblemStatus findBySNo(Long id);

    @Query("select ps from ProblemStatus ps where ps.email=:email and ps.proNo=:proNo")
    List<ProblemStatus> findByEmailAndProNo(String email, long proNo);

}
