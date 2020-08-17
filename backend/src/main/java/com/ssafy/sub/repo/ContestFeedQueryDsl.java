package com.ssafy.sub.repo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
				.where(user.usex.eq(2)
						.and(contestFeedLike.contestFeedLikeKey.cfid.eq(fid)))
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
				.where(user.usex.eq(1)
						.and(contestFeedLike.contestFeedLikeKey.cfid.eq(fid)))
				.distinct()
				.fetchCount();
	}
	
	// 10대 좋아요 수
	public Long findLikeAge10(int fid) throws ParseException {
		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
		QUser user = QUser.user;
		System.out.println(user.ubirth.after(new Date(2002)));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse("2002-01-01");
		
		return from(contestFeedLike)
				.leftJoin(user)
				.on(contestFeedLike.contestFeedLikeKey.uid.eq(user.id))
				.where(user.ubirth.after(date1)
						.and(contestFeedLike.contestFeedLikeKey.cfid.eq(fid)))
				.distinct()
				.fetchCount();
	}
	
	// 20대 좋아요 수
	public Long findLikeAge20(int fid) throws ParseException {
		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
		QUser user = QUser.user;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse("2001-12-31");
		Date date2 = sdf.parse("1992-01-01");
		
		return from(contestFeedLike)
				.leftJoin(user)
				.on(contestFeedLike.contestFeedLikeKey.uid.eq(user.id))
				.where(user.ubirth.after(date2).and(user.ubirth.before(date1))
						.and(contestFeedLike.contestFeedLikeKey.cfid.eq(fid)))
				.distinct()
				.fetchCount();
	}
	
	// 30대 좋아요 수
	public Long findLikeAge30(int fid) throws ParseException {
		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
		QUser user = QUser.user;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse("1991-12-31");
		Date date2 = sdf.parse("1982-01-01");
		
		return from(contestFeedLike)
				.leftJoin(user)
				.on(contestFeedLike.contestFeedLikeKey.uid.eq(user.id))
				.where(user.ubirth.after(date2).and(user.ubirth.before(date1))
						.and(contestFeedLike.contestFeedLikeKey.cfid.eq(fid)))
				.distinct()
				.fetchCount();
	}
	
	// 40대 좋아요 수
	public Long findLikeAge40(int fid) throws ParseException {
		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
		QUser user = QUser.user;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse("1981-12-31");
		Date date2 = sdf.parse("1972-01-01");
		
		return from(contestFeedLike)
				.leftJoin(user)
				.on(contestFeedLike.contestFeedLikeKey.uid.eq(user.id))
				.where(user.ubirth.after(date2).and(user.ubirth.before(date1))
						.and(contestFeedLike.contestFeedLikeKey.cfid.eq(fid)))
				.distinct()
				.fetchCount();
	}
	
	// 50대 좋아요 수
	public Long findLikeAge50(int fid) throws ParseException {
		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
		QUser user = QUser.user;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse("1971-12-31");
		
		return from(contestFeedLike)
				.leftJoin(user)
				.on(contestFeedLike.contestFeedLikeKey.uid.eq(user.id))
				.where(user.ubirth.before(date1)
						.and(contestFeedLike.contestFeedLikeKey.cfid.eq(fid)))
				.distinct()
				.fetchCount();
	}
	
}
