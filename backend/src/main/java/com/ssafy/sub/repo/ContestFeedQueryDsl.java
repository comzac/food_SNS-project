package com.ssafy.sub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.jpa.JPAExpressions;
import com.ssafy.sub.dto.ContestFeed;
import com.ssafy.sub.dto.QContestFeed;
import com.ssafy.sub.dto.QContestFeedLike;

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
