package com.ssafy.sub.repo;

import java.util.List;

import org.hibernate.usertype.UserType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.ssafy.sub.dto.FeedHashtag;
import com.ssafy.sub.dto.Hashtag;
import com.ssafy.sub.dto.QFeedHashtag;
import com.ssafy.sub.dto.QHashtag;
import com.ssafy.sub.dto.QRecommand;
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

	/**
	 * 유저 성별과 연령대에서 누적 점수가 높은 해쉬태그를 가진 피드 들고오기
	 * @param uid - 유저 pk
	 * @param uageGroup - 유저 연령대
	 * @param usex - 유저 성별
	 * @return List<FeedHashtag> (
	 */
	public List<FeedHashtag> findByRecommand(int uageGroup, int usex) {
		// follow와 본인 피드는 추천X
		// hashtag 바탕으로 가져오기
		QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
		QRecommand recommand = QRecommand.recommand;
		
		return from(feedHashtag)
				.leftJoin(recommand)
				.on(feedHashtag.feedHashtagkey.hid.eq(recommand.recommandkey.hashtag))
				.where(recommand.recommandkey.ageGroup.eq(uageGroup)
						.and(recommand.recommandkey.gender.eq(usex)))
				.orderBy(recommand.accumulate.desc())
				.distinct()
				.fetch();
		
	}

}
