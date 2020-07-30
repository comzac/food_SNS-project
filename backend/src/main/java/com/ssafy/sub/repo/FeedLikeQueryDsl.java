package com.ssafy.sub.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.FeedLike;
import com.ssafy.sub.dto.QFeed;
import com.ssafy.sub.dto.QFeedLike;
import com.ssafy.sub.dto.QUser;
import com.ssafy.sub.dto.User;

@Repository
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
	
	
	public List<Feed> findAllByFeedLike(int uid){	// 해당 유저가 좋아요한 피드
		QFeed feed = QFeed.feed;
		QFeedLike feedLike = QFeedLike.feedLike;
//		System.out.println("find feed who liked "+uid);
		return from(feed)
				.leftJoin(feedLike)
				.on(feed.id.eq(feedLike.feedLikekey.fid))
				.where(feedLike.feedLikekey.uid.eq(uid))
				.distinct()
				.fetch();
	}
	
	public List<User> findAllByUserLike(int fid) {
		QUser user = QUser.user;
		QFeedLike feedLike = QFeedLike.feedLike;
//		System.out.println("find users who like "+fid);
		return from(user)
				.leftJoin(feedLike)
				.on(user.id.eq(feedLike.feedLikekey.uid))
				.where(feedLike.feedLikekey.fid.eq(fid))
				.distinct()
				.fetch();
	}
}
