package com.ssafy.sub.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.Comment;
import com.ssafy.sub.dto.QComment;

@Repository
public class CommentQueryDsl extends QuerydslRepositorySupport{

	
	public CommentQueryDsl() {
		super(Comment.class);
	}

	@Transactional
	public Long deleteComment(Comment comm) {
		QComment comment = QComment.comment;
				
		return delete(comment)
				.where(comment.fid.eq(comm.getFid())
						.and(comment.pid.eq(comm.getPid()))
						.and(comment.depth.eq(comm.getDepth()))).execute();
	}
	
	public List<Comment> findAllByFidByRegdateDesc(int fid) {
		QComment comment = QComment.comment;
		return from(comment)
				.where(comment.fid.eq(fid))
				.orderBy(comment.regdate.desc())
				.fetch();
	}
	
	public List<Comment> findLimitByFid(int fid, int limit) {
		QComment comment = QComment.comment;
		return from(comment)
				.where(comment.fid.eq(fid))
				.orderBy(comment.regdate.desc())
				.limit(limit)
				.fetch();
	}
}
