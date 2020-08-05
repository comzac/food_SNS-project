package com.ssafy.sub.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.Contest;

public interface ContestRepository extends JpaRepository<Contest, Long> {

}
