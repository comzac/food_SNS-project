package com.ssafy.sub.repo;

import java.util.List;

import com.ssafy.sub.dto.QFeed;
import com.ssafy.sub.dto.QRelationShip;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.Feed;

@Repository
public class FeedQueryDsl extends QuerydslRepositorySupport {

	public FeedQueryDsl() {
		super(Feed.class);
	}

	public List<Feed> findAllByFollower(int id){
		QFeed feed = QFeed.feed;
		QRelationShip relationShip = QRelationShip.relationShip;
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
	
}
