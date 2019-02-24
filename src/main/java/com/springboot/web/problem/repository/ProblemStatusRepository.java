package com.springboot.web.problem.repository;


import com.springboot.web.problem.domain.ProblemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemStatusRepository extends JpaRepository<ProblemStatus,Long> {

    List<ProblemStatus> findByEmail(String email);
}
