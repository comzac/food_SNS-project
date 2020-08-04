package com.ssafy.sub.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.Comment;
import com.ssafy.sub.dto.CommentLike;
import com.ssafy.sub.dto.QComment;
import com.ssafy.sub.dto.QCommentLike;
import com.ssafy.sub.dto.QUser;
import com.ssafy.sub.dto.User;

@Repository
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
	
	public List<Comment> findAllByCommentLike(int uid){	// 해당 유저가 좋아요한 코멘트
		QComment comment = QComment.comment;
		QCommentLike commentLike = QCommentLike.commentLike;
		System.out.println("find feed who liked "+uid);
		return from(comment)
				.leftJoin(commentLike)
				.on(comment.id.eq(commentLike.commentLikeKey.cid))
				.where(commentLike.commentLikeKey.uid.eq(uid))
				.distinct()
				.fetch();
	}
	
	public List<User> findAllByUserLike(int cid) {
		QUser user = QUser.user;
		QCommentLike commentLike = QCommentLike.commentLike;
//		System.out.println("find feed who liked "+cid);
		return from(user)
				.leftJoin(commentLike)
				.on(user.id.eq(commentLike.commentLikeKey.uid))
				.where(commentLike.commentLikeKey.cid.eq(cid))
				.distinct()
				.fetch();
	}
	
	public Long countByCid(int cid) {
		QCommentLike commentLike = QCommentLike.commentLike;
		return from(commentLike)
				.where(commentLike.commentLikeKey.cid.eq(cid))
				.fetchCount();
	}
	
	public CommentLike findByUidAndCid(int uid, int cid) {
		QCommentLike commentLike = QCommentLike.commentLike;
		return from(commentLike)
				.where(commentLike.commentLikeKey.uid.eq(uid)
						.and(commentLike.commentLikeKey.cid.eq(cid)))
				.distinct()
				.fetchOne();
	}
}
