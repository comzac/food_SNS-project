package com.ssafy.sub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.FeedHashtag;

public interface FeedHashtagRepository extends JpaRepository<FeedHashtag, Integer> {

	FeedHashtag findByFeedId(int feed_id);

	List<FeedHashtag> findAllByFeedId(int feed_id);
	
}
