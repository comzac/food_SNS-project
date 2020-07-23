package com.ssafy.sub.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long> {
	Optional<Feed> findById(String id);
//	Feed findById(int uid);
	Long deleteById(int id);
	
//	@Transactional
//	@Modifying
//	@Query("UPDATE ")
	
}
