package com.ssafy.sub.repo;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.sub.dto.QRecommand;
import com.ssafy.sub.dto.QRecommandKey;
import com.ssafy.sub.dto.Recommand;

@Repository
public class LogQueryDsl extends QuerydslRepositorySupport{

	public LogQueryDsl() {
		super(Recommand.class);
	}

	public Recommand dupCehck(Recommand newRecommand) {
		QRecommand recommand = QRecommand.recommand;
		QRecommandKey recommandKey = QRecommandKey.recommandKey;
		
		return from(recommand)
				.where(recommand.recommandkey.gender.eq(newRecommand.getRecommandkey().getGender())
						.and(recommand.recommandkey.ageGroup.eq(newRecommand.getRecommandkey().getAgeGroup())
								.and(recommand.recommandkey.hashtag.eq(newRecommand.getRecommandkey().getHashtag())))).fetchOne();
	}
	 
	
}
