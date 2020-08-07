package com.ssafy.sub.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.ContestFeed;
import com.ssafy.sub.dto.QContestFeedLike;
import com.ssafy.sub.dto.QUser;

@Repository
public class ContestFeedQueryDsl extends QuerydslRepositorySupport{

	public ContestFeedQueryDsl() {
		super(ContestFeed.class);
	}
	
	// 여자 좋아요 수
	public Long findLikeCountFemale(int fid) {
		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
		QUser user = QUser.user;
		
		return from(contestFeedLike)
				.leftJoin(user)
				.on(contestFeedLike.contestFeedLikeKey.uid.eq(user.id))
				.where(user.usex.eq(2))
				.distinct()
				.fetchCount();
	}
	
	// 남자 좋아요 수
	public Long findLikeCountMale(int fid) {
		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
		QUser user = QUser.user;
		
		return from(contestFeedLike)
				.leftJoin(user)
				.on(contestFeedLike.contestFeedLikeKey.uid.eq(user.id))
				.where(user.usex.eq(2))
				.distinct()
				.fetchCount();
	}
	
	// 10대 좋아요 수
	public Long findLikeAge10(int fid) {
		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
		QUser user = QUser.user;
		
		return from(contestFeedLike)
				.leftJoin(user)
				.on(contestFeedLike.contestFeedLikeKey.uid.eq(user.id))
				.where(user.ubirth.after(new Date(2002)))
				.distinct()
				.fetchCount();
	}
	
	// 20대 좋아요 수
	public Long findLikeAge20(int fid) {
		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
		QUser user = QUser.user;
		
		return from(contestFeedLike)
				.leftJoin(user)
				.on(contestFeedLike.contestFeedLikeKey.uid.eq(user.id))
				.where(user.ubirth.after(new Date(1992)).and(user.ubirth.before(new Date(2001))))
				.distinct()
				.fetchCount();
	}
	
	// 30대 좋아요 수
	public Long findLikeAge30(int fid) {
		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
		QUser user = QUser.user;
		Date date = new Date(); 
		date.setYear(2002);
		
		return from(contestFeedLike)
				.leftJoin(user)
				.on(contestFeedLike.contestFeedLikeKey.uid.eq(user.id))
				.where(user.ubirth.after(new Date(1982)).and(user.ubirth.before(new Date(1991))))
				.distinct()
				.fetchCount();
	}
	
	// 40대 좋아요 수
	public Long findLikeAge40(int fid) {
		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
		QUser user = QUser.user;
		
		return from(contestFeedLike)
				.leftJoin(user)
				.on(contestFeedLike.contestFeedLikeKey.uid.eq(user.id))
				.where(user.ubirth.after(new Date(1972)).and(user.ubirth.before(new Date(1981))))
				.distinct()
				.fetchCount();
	}
	
	// 50대 좋아요 수
	public Long findLikeAge50(int fid) {
		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
		QUser user = QUser.user;
		
		return from(contestFeedLike)
				.leftJoin(user)
				.on(contestFeedLike.contestFeedLikeKey.uid.eq(user.id))
				.where(user.ubirth.before(new Date(1971)))
				.distinct()
				.fetchCount();
	}
	
}
