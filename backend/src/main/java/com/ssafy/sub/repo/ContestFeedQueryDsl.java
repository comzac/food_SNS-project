package com.ssafy.sub.repo;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.ssafy.sub.dto.ContestFeed;

public class ContestFeedQueryDsl extends QuerydslRepositorySupport{

	public ContestFeedQueryDsl() {
		super(ContestFeed.class);
	}
	
//	public List<ContestFeed> findByCidOrderByLike(int cid) {
//		QContestFeed contestFeed = QContestFeed.contestFeed;
//		QContestFeedLike contestFeedLike = QContestFeedLike.contestFeedLike;
//		return from(contestFeed)
//				.where(contestFeed.cid.eq(cid))
//				.orderBy()
//				
//	}

}
