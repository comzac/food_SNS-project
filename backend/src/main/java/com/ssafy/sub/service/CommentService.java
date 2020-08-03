package com.ssafy.sub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Comment;

@Service("CommentService")
public interface CommentService {

	List<Comment> commentList(int fid);
	
	List<Comment> commentListLimit(int fid, int limit);

	Comment commentUpdate(Comment comment);

	Comment commentInsert(Comment comment);

	Long commentDelete(int id);

}
