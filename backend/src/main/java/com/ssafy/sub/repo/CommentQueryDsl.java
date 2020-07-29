package com.ssafy.sub.repo;

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
	
}
