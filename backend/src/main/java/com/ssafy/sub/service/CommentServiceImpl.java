package com.ssafy.sub.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Comment;
import com.ssafy.sub.exception.RestException;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.repo.CommentQueryDsl;
import com.ssafy.sub.repo.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	CommentQueryDsl commentQueryDsl;
	
	@Override
	public List<Comment> commentList(int fid) {
		return commentQueryDsl.findAllByFidByRegdateDesc(fid);
	}
	
	@Override
	public Comment commentInsert(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	@Transactional
	public Comment commentUpdate(Comment comment) {
		Date now = new Date();
		Optional<Comment> updateComment = commentRepository.findById(comment.getId());
		
		if(!updateComment.isPresent()) 
			throw new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMENT, HttpStatus.NOT_FOUND);
		
		updateComment.get().setContent(comment.getContent());
		updateComment.get().setDepth(comment.getDepth());
		updateComment.get().setEditdate(now);
		commentRepository.save(updateComment.get());
		
		return updateComment.get();
	}


	@Override
	public Long commentDelete(int id) {
		return commentRepository.deleteById(id);
	}

	@Override
	public List<Comment> commentListLimit(int fid, int limit) {
		return commentQueryDsl.findLimitByFid(fid, limit);
	}

	@Override
	public Long commentCount(int fid) {
		return commentRepository.countByFid(fid);
	}
	
}
