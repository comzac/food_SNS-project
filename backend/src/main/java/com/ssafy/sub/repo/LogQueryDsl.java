package com.ssafy.sub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.sub.dto.QRecommand;
import com.ssafy.sub.dto.Recommand;

@Repository
public class LogQueryDsl extends QuerydslRepositorySupport{

	public LogQueryDsl() {
		super(Recommand.class);
	}

	public Recommand dupCehck(Recommand newRecommand) {
		QRecommand recommand = QRecommand.recommand;
		
		return from(recommand)
				.where(recommand.recommandkey.gender.eq(newRecommand.getRecommandkey().getGender())
						.and(recommand.recommandkey.ageGroup.eq(newRecommand.getRecommandkey().getAgeGroup())
								.and(recommand.recommandkey.hashtag.eq(newRecommand.getRecommandkey().getHashtag())))).fetchOne();
	}

	/**
	 * 유저 성별과 연령대에서 누적 점수가 높은 해쉬태그 조회
	 * @param int uageGroup - 유저 연령대
	 * @param int usex - 유저 성별
	 * @return List<Integer> (해쉬태그 pk)
	 */
	public List<Integer> findHidOrderByAccumulate(int uageGroup, int usex) {
		QRecommand recommand = QRecommand.recommand;
		System.out.println("Rec: "+uageGroup+" "+usex);

		JPAQueryFactory query = new JPAQueryFactory(this.getEntityManager());
		return query
				.select(recommand.recommandkey.hashtag)
				.from(recommand)
				.where(recommand.recommandkey.ageGroup.eq(uageGroup)
						.and(recommand.recommandkey.gender.eq(usex)))
				.orderBy(recommand.accumulate.desc())
				.distinct()
				.fetch();
	}
	
}
