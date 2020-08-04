package com.ssafy.sub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Comment;
import com.ssafy.sub.dto.CommentLike;
import com.ssafy.sub.dto.CommentLikeKey;
import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.FeedLike;
import com.ssafy.sub.dto.FeedLikeKey;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.repo.CommentLikeQueryDsl;
import com.ssafy.sub.repo.CommentLikeRepository;
import com.ssafy.sub.repo.FeedLikeQueryDsl;
import com.ssafy.sub.repo.FeedLikeRepository;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	FeedLikeQueryDsl feedLikeQueryDsl;
	@Autowired
	FeedLikeRepository feedLikeRepository;
	@Autowired
	CommentLikeQueryDsl commentLikeQueryDsl;
	@Autowired
	CommentLikeRepository commentLikeRepository;
	
	@Override
	public List<Feed> feedLikeFeedList(int uid) {
		return feedLikeQueryDsl.findAllByFeedLike(uid);
	}

	@Override
	public List<User> feedLikeUserList(int fid) {
		return feedLikeQueryDsl.findAllByUserLike(fid);
	}

	@Override
	public boolean isFeedLiked(int fid, int uid) {
		if(feedLikeQueryDsl.findFeedLike(fid, uid).size()==0) return false;
		return true;
	}

	@Override
	public FeedLike feedLikeInsert(int fid, int uid) {
		FeedLike feedLike = new FeedLike(new FeedLikeKey(fid, uid));
		return feedLikeRepository.save(feedLike);
	}

	@Override
	public int feedLikeDelete(int fid, int uid) {
		return (int) feedLikeQueryDsl.feedLikeDelete(fid, uid);
	}
	

	@Override
	public List<Comment> commentLikeCommentList(int uid) {
		return commentLikeQueryDsl.findAllByCommentLike(uid);
	}


	@Override
	public List<User> commentLikeUserList(int cid) {
		return commentLikeQueryDsl.findAllByUserLike(cid);
	}

	@Override
	public boolean isCommentLiked(int cid, int uid) {
		if(commentLikeQueryDsl.findCommentLike(cid, uid).size()==0) return false;
		return true;
	}

	@Override
	public CommentLike commentLikeInsert(int cid, int uid) {
		CommentLike commentLike = new CommentLike(new CommentLikeKey(cid, uid));
		return commentLikeRepository.save(commentLike);
	}

	@Override
	public int commentLikeDelete(int cid, int uid) {
		return (int) commentLikeQueryDsl.commentLikeDelete(cid, uid);
	}

	@Override
	public Long countCommentLike(int cid) {
		return commentLikeQueryDsl.countByCid(cid);
	}

}
