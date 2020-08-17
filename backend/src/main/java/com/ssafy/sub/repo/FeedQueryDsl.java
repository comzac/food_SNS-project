package com.ssafy.sub.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPAExpressions;
import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.FeedHashtag;
import com.ssafy.sub.dto.QDBFile;
import com.ssafy.sub.dto.QFeed;
import com.ssafy.sub.dto.QFeedHashtag;
import com.ssafy.sub.dto.QHashtag;
import com.ssafy.sub.dto.QRecommand;
import com.ssafy.sub.dto.QRecommandKey;
import com.ssafy.sub.dto.QRelationship;


@Repository
public class FeedQueryDsl extends QuerydslRepositorySupport {

	public FeedQueryDsl() {
		super(Feed.class);
	}

	public List<Feed> findAllByFollower(int id){
		QFeed feed = QFeed.feed;
		QRelationship relationShip = QRelationship.relationship;
//		System.out.println(id + "DSL");
		return from(feed)
				.leftJoin(relationShip)
				.on(feed.uid.eq(relationShip.relationShipkey.relationuid))
				.where(relationShip.relationShipkey.uid.eq(id)
						.or(feed.uid.eq(id)))
				.distinct()
				.orderBy(feed.editdate.desc())
				.fetch();
	}
	
	public List<Feed> findFeedListByUid(int uid) {
		QFeed feed = QFeed.feed;
		QDBFile dbFile = QDBFile.dBFile;
		System.out.println("feed List");
		return from(feed)
				.where(feed.uid.eq(uid))
				.orderBy(feed.regdate.desc())
				.distinct()
				.fetch();
	}
	
	public List<Feed> findFeedListByFid(int fid) {
		QFeed feed = QFeed.feed;
		QDBFile dbFile = QDBFile.dBFile;
//		System.out.println("feed List");
		return from(feed)
				.leftJoin(dbFile)
				.on(feed.id.eq(dbFile.id))
				.where(feed.id.eq(fid))
				.distinct()
				.fetch();
	}
	
	public List<Feed> findFeedList() {
		QFeed feed = QFeed.feed;
//		System.out.println("all feed list");
		return from(feed)
				.orderBy(feed.editdate.desc())
				.orderBy(feed.regdate.desc())
				.fetch();
	}

	public List<Feed> searchByHashtag(String keyword) {
		QFeed feed = QFeed.feed;
		QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
		QHashtag hashtag = QHashtag.hashtag;
		
		return from(feed)
				.leftJoin(feedHashtag)
				.on(feed.id.eq(feedHashtag.feedHashtagkey.fid))
				.where(feedHashtag.feedHashtagkey.hid
						.in(JPAExpressions.select(hashtag.id).
								from(hashtag)
								.where(hashtag.content.like(keyword))))
				.fetch();
	}

	public List<Feed> searchByUserID(int uid) {
		QFeed feed = QFeed.feed;
		
		return from(feed)
				.where(feed.uid.eq(uid))
				.orderBy(feed.editdate.desc())
				.orderBy(feed.regdate.desc())
						.fetch();
	}

	public Long countFeedByUser(int user_id) {
		QFeed feed = QFeed.feed;
		return from(feed)
				.where(feed.uid.eq(user_id))
				.fetchCount();
	}

	public List<Feed> findByIdLessThanFollower(int uid, int fid, Pageable pageable) {
		QFeed feed = QFeed.feed;
		QRelationship relationShip = QRelationship.relationship;

		return from(feed)
				.leftJoin(relationShip)
				.on(feed.uid.eq(relationShip.relationShipkey.relationuid))
				.where(relationShip.relationShipkey.uid.eq(uid)
						.or(feed.uid.eq(uid)))
				.where(feed.id.lt(fid))
				.orderBy(feed.id.desc())
				.limit(pageable.getPageSize())
				.distinct()
				.fetch();
	}
	
	public List<Feed> findByRecommandHid(int hid, int uid) {
		QFeed feed = QFeed.feed;
		QRelationship relationShip = QRelationship.relationship;
		QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
		
		return from(feed)
				.leftJoin(feedHashtag)
				.on(feedHashtag.feedHashtagkey.fid.eq(feed.id))
				.where(feedHashtag.feedHashtagkey.hid.eq(hid))
				.where(feed.uid.notIn(
						JPAExpressions.select(relationShip.relationShipkey.relationuid)
							.from(relationShip)
							.where(relationShip.relationShipkey.uid.eq(uid)))
					.and(feed.uid.eq(uid).not()))
				.orderBy(feed.id.desc())
				.distinct()
				.fetch();
	}
	
	public Feed findByRecommandHidFetchOne(int hid, int uid, int lastFidRecommand) {
		QFeed feed = QFeed.feed;
		QRelationship relationShip = QRelationship.relationship;
		QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
		
		return from(feed)
				.leftJoin(feedHashtag)
				.on(feedHashtag.feedHashtagkey.fid.eq(feed.id))
				.where(feedHashtag.feedHashtagkey.hid.eq(hid))
				.where(feed.uid.notIn(
						JPAExpressions.select(relationShip.relationShipkey.relationuid)
							.from(relationShip)
							.where(relationShip.relationShipkey.uid.eq(uid)))
					.and(feed.uid.eq(uid).not()))
				.where(feed.id.lt(lastFidRecommand))
				.orderBy(feed.id.desc())
				.distinct()
				.fetchFirst();
	}

}
