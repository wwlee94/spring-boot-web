package com.springboot.web.Board.repository;

import com.springboot.web.Board.domain.Compile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompileRepository extends JpaRepository<Compile,Integer> {

}
