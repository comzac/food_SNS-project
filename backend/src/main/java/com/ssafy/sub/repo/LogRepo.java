package com.ssafy.sub.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.Recommand;

public interface LogRepo extends JpaRepository<Recommand, Integer>{

}
