package com.ssafy.sub.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.sub.dto.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {
	public Hashtag findByContent(String content);
	
	public Hashtag save(Hashtag hashtag);
	
}
