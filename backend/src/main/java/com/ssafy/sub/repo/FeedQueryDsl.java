package com.ssafy.sub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.QDBFile;
import com.ssafy.sub.dto.QFeed;
import com.ssafy.sub.dto.QRelationship;


@Repository
public class FeedQueryDsl extends QuerydslRepositorySupport {

	public FeedQueryDsl() {
		super(Feed.class);
	}

	public List<Feed> findAllByFollower(int id){
		QFeed feed = QFeed.feed;
		QRelationship relationShip = QRelationship.relationship;
		System.out.println(id + "DSL");
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
				.leftJoin(dbFile)
				.on(feed.id.eq(dbFile.id))
				.where(feed.uid.eq(uid))
				.distinct()
				.fetch();
	}
	
	public List<Feed> findFeedListByFid(int fid) {
		QFeed feed = QFeed.feed;
		QDBFile dbFile = QDBFile.dBFile;
		System.out.println("feed List");
		return from(feed)
				.leftJoin(dbFile)
				.on(feed.id.eq(dbFile.id))
				.where(feed.id.eq(fid))
				.distinct()
				.fetch();
	}
	
}
