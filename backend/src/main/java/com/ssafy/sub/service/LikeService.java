package com.ssafy.sub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Comment;
import com.ssafy.sub.dto.CommentLike;
import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.FeedLike;
import com.ssafy.sub.dto.User;

/**
 * 피드 및 댓글의 좋아요 관리
 * 
 * @author 김순빈
 * @version 1.0, 댓글 좋아요 관련 업데이트
 */

@Service("LikeService")
public interface LikeService {
	
	/**
	 * 유저 pk로 해당 유저가 좋아요 누른 피드 조회
	 * @param int uid - 유저 pk
	 * @return List<Feed> (피드 정보)
	 */
	List<Feed> feedLikeFeedList(int uid);
	
	/**
	 * 피드 pk로  해당 피드 좋아요 누른 유저 조회
	 * @param int fid - 피드 pk
	 * @return List<User>
	 */
	List<User> feedLikeUserList(int fid);
	
	/**
	 * 유저가 해당 피드를 좋아하는지 여부
	 * @param int fid - 피드 pk
	 * @param int uid - 유저 pk
	 * @return boolean (피드 좋아요 여부)
	 */
	boolean isFeedLiked(int fid, int uid);
	
	/**
	 * 피드 좋아요
	 * @param int fid - 피드 pk
	 * @param int uid - 유저 pk
	 * @return FeedLike (피드 좋아요 정보)
	 */
	FeedLike feedLikeInsert(int fid, int uid);

	/**
	 * 피드 좋아요 취소
	 * @param int fid - 피드 pk
	 * @param int uid - 유저 pk
	 * @return int (feedLike pk) #? 확인해보기
	 */
	int feedLikeDelete(int fid, int uid);
	
	/**
	 * 유저 pk로 해당 유저가 좋아요 누른 댓글 조회
	 * @param int uid - 유저 pk
	 * @return List<Comment> (댓글 정보)
	 */
	List<Comment> commentLikeCommentList(int uid);
	
	/**
	 * 댓글 pk로 해당 댓글 좋아요 누른 유저 조회
	 * @param int cid - 댓글 pk
	 * @return List<User> (유저 정보)
	 */
	List<User> commentLikeUserList(int cid);
	
	/**
	 * 유저가 해당 댓글을 좋아하는지 여부
	 * @param int cid - 댓글 pk
	 * @param int uid - 유저 pk
	 * @return boolean (댓글 좋아요 여부)
	 */
	boolean isCommentLiked(int cid, int uid);
	
	/**
	 * 댓글 좋아요
	 * @param int cid - 댓글 pk
	 * @param int uid - 유저 pk
	 * @return CommentLike (댓글 좋아요 정보)
	 */
	CommentLike commentLikeInsert(int cid, int uid);

	/**
	 * 댓글 좋아요 취소
	 * @param int cid - 댓글 pk
	 * @param int uid - 유저 pk
	 * @return int (댓글 좋아요 pk) # ? 확인
	 */
	int commentLikeDelete(int cid, int uid);
	
	/**
	 * 댓글 좋아요 개수
	 * @param int cid - 댓글 pk
	 * @return Long (좋아요 개수)
	 */
	Long countCommentLike(int cid);
	
}
