package com.ssafy.sub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.Hashtag;
import com.ssafy.sub.dto.QHashtag;

@Repository
public class HashtagQueryDsl extends QuerydslRepositorySupport{
	public HashtagQueryDsl() {
		super(Hashtag.class);
	}
	
	public List<Hashtag> findHashtagByKeyword(String keyword) {
		QHashtag hashtag = QHashtag.hashtag;
		return from(hashtag)
				.where(hashtag.content.contains(keyword))
						.fetch();
	}


}
