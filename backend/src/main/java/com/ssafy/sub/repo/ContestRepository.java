package com.ssafy.sub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.Contest;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {

	Contest findFirstByOrderByIdDesc();

	List<Contest> findAllByOrderByIdDesc();

	Contest findById(int cid);

}
