package com.ssafy.sub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Comment;
import com.ssafy.sub.dto.CommentLike;
import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.FeedLike;
import com.ssafy.sub.dto.User;

@Service("LikeService")
public interface LikeService {
	
	// user 번호로 관심있는 피드 조회
	List<Feed> feedLikeFeedList(int uid);
	
	// feed 번호로 관심있는 유저 조회
	List<User> feedLikeUserList(int fid);
	
	// user가 해당 피드를 좋아하는지 여부
	boolean isFeedLiked(int fid, int uid);
	
	// 피드 좋아요 누르기
	FeedLike feedLikeInsert(int fid, int uid);

	// 피드 싫어요 누르기
	int feedLikeDelete(int fid, int uid);
	
	// user 번호로 관심있는 코멘트 조회
	List<Comment> commentLikeCommentList(int uid);
	
	// 코멘트 번호로 관심있는 유저 조회
	List<User> commentLikeUserList(int cid);
	
	// user가 해당 코멘트를 좋아하는지 여부
	boolean isCommentLiked(int cid, int uid);
	
	// 코멘트 좋아요 누르기
	CommentLike commentLikeInsert(int cid, int uid);

	// 코멘트 싫어요 누르기
	int commentLikeDelete(int cid, int uid);
	
}
