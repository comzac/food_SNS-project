package com.ssafy.sub.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {
	public Hashtag findByContent(String content);
	public Hashtag save(String content);
}
