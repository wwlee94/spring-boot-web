package com.springboot.web.board.repository;

import com.springboot.web.board.domain.Compile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompileRepository extends JpaRepository<Compile, Integer> {
}
