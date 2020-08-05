package com.ssafy.sub.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.ContestFeedLike;
import com.ssafy.sub.dto.ContestFeedLikeKey;

public interface ContestFeedLikeRepository extends JpaRepository<ContestFeedLike, ContestFeedLikeKey> {

	@Transactional
	void delete(ContestFeedLike contestFeedLike);
}
