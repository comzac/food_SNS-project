package com.ssafy.sub.repo;

import java.util.List;

import org.hibernate.usertype.UserType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.ssafy.sub.dto.Hashtag;
import com.ssafy.sub.dto.QFeedHashtag;
import com.ssafy.sub.dto.QHashtag;
import com.ssafy.sub.model.response.Result;

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

	public Long countFeedByHashtag(int id) {
		QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
		
		return from(feedHashtag)
				.where(feedHashtag.feedHashtagkey.hid.eq(id))
				.fetchCount();

	}



}
