package com.ssafy.sub.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.ssafy.sub.dto.CommentLike;
import com.ssafy.sub.dto.QCommentLike;

public class CommentLikeQueryDsl extends QuerydslRepositorySupport {

	public CommentLikeQueryDsl() {
		super(CommentLike.class);
	}
	
	public List<CommentLike> findAllByCid(int cid) {
		QCommentLike commentLike = QCommentLike.commentLike;
		return from(commentLike)
				.where(commentLike.commentLikeKey.cid.eq(cid))
				.distinct()
				.fetch();
	}
	
	public List<CommentLike> findAllByUid(int uid) {
		QCommentLike commentLike = QCommentLike.commentLike;
		return from(commentLike)
				.where(commentLike.commentLikeKey.uid.eq(uid))
				.distinct()
				.fetch();
	}
	
	@Transactional
	public long commentLikeDelete(int cid, int uid) {
		QCommentLike commentLike = QCommentLike.commentLike;
		return delete(commentLike)
				.where(commentLike.commentLikeKey.cid.eq(cid)
						.and(commentLike.commentLikeKey.uid.eq(uid)))
				.execute();
	}
	
	public List<CommentLike> findCommentLike(int cid, int uid) {
		QCommentLike commentLike = QCommentLike.commentLike;
		return from(commentLike)
				.where(commentLike.commentLikeKey.cid.eq(cid)
						.and(commentLike.commentLikeKey.uid.eq(uid)))
				.fetch();
	}
}
