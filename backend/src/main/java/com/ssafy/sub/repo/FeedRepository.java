package com.ssafy.sub.repo;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long> {
	Optional<Feed> findById(int id);
	
	@Transactional
	Long deleteById(int id);
}
