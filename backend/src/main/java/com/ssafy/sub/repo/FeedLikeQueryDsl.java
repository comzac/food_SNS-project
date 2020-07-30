package com.ssafy.sub.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.ssafy.sub.dto.FeedLike;
import com.ssafy.sub.dto.QFeedLike;

public class FeedLikeQueryDsl extends QuerydslRepositorySupport {

	public FeedLikeQueryDsl() {
		super(FeedLike.class);
	}
	
	public List<FeedLike> findAllByFid(int fid) {
		QFeedLike feedLike = QFeedLike.feedLike;
		return from(feedLike)
				.where(feedLike.feedLikekey.fid.eq(fid))
				.distinct()
				.fetch();
	}
	
	public List<FeedLike> findAllByUid(int uid) {
		QFeedLike feedLike = QFeedLike.feedLike;
		return from(feedLike)
				.where(feedLike.feedLikekey.uid.eq(uid))
				.distinct()
				.fetch();
	}
	
	@Transactional
	public long feedLikeDelete(int fid, int uid) {
		QFeedLike feedLike = QFeedLike.feedLike;
		return delete(feedLike)
				.where(feedLike.feedLikekey.fid.eq(fid)
						.and(feedLike.feedLikekey.uid.eq(uid)))
				.execute();
	}
	
	public List<FeedLike> findFeedLike(int fid, int uid) {
		QFeedLike feedLike = QFeedLike.feedLike;
		return from(feedLike)
				.where(feedLike.feedLikekey.fid.eq(fid)
						.and(feedLike.feedLikekey.uid.eq(uid)))
				.fetch();
	}
}
