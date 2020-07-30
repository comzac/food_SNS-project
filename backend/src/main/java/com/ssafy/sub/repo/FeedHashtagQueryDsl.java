package com.ssafy.sub.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.FeedHashtag;
import com.ssafy.sub.dto.Hashtag;
import com.ssafy.sub.dto.QFeedHashtag;
import com.ssafy.sub.dto.QHashtag;

@Repository
public class FeedHashtagQueryDsl extends QuerydslRepositorySupport{

	public FeedHashtagQueryDsl() {
		super(FeedHashtag.class);
	}
	
	public List<FeedHashtag> findAllByFid(int fid) {
		QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
		return from(feedHashtag)
				.where(feedHashtag.feedHashtagkey.fid.eq(fid))
				.distinct()
				.fetch();
	}
	
	public List<FeedHashtag> findAllByHid(int hid) {
		QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
		return from(feedHashtag)
				.where(feedHashtag.feedHashtagkey.hid.eq(hid))
				.distinct()
				.fetch();
	}
	
	@Transactional
	public long feedHashtagDelete(int fid, int hid) {
		QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
		return delete(feedHashtag)
				.where(feedHashtag.feedHashtagkey.fid.eq(fid)
						.and(feedHashtag.feedHashtagkey.hid.eq(hid)))
				.execute();
	}
	
	public List<FeedHashtag> findFeedHashtag(int fid, int hid) {
		QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
		return from(feedHashtag)
				.where(feedHashtag.feedHashtagkey.fid.eq(fid)
						.and(feedHashtag.feedHashtagkey.hid.eq(hid)))
				.fetch();
	}
	
	@Transactional
	public long feedHashtagDeleteByFid(int fid) {
		QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
		return delete(feedHashtag)
				.where(feedHashtag.feedHashtagkey.fid.eq(fid))
				.execute();
	}
	
	public List<Hashtag> findHashtagById(int fid) {
		QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
		QHashtag hashtag = QHashtag.hashtag;
		return from(hashtag)
				.leftJoin(feedHashtag)
				.on(hashtag.id.eq(feedHashtag.feedHashtagkey.hid))
				.where(feedHashtag.feedHashtagkey.fid.eq(fid))
				.distinct()
				.fetch();
	}
}
