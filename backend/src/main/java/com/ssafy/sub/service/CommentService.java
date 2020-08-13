package com.ssafy.sub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Comment;

/**
 * 댓글 관리
 * 
 * @author 김순빈
 * @version 1.0, 댓글 조회 시 limit 개 반환
 */

@Service("CommentService")
public interface CommentService {

	/**
	 * 피드 pk를 이용한 해당 피드의 댓글 조회
	 * @param int fid - 피드 pk
	 * @return List<Comment> (댓글 정보)
	 */
	List<Comment> commentList(int fid);
	
	/**
	 * 피드 pk를 이용한 해당 피드의 댓글 limit개 조회
	 * @param int fid - 피드 pk
	 * @param int limit - 보여질 댓글의 개수
	 * @return List<Comment> (댓글 정보)
	 */
	List<Comment> commentListLimit(int fid, int limit);

	/**
	 * 댓글 업데이트
	 * @param Comment comment - 업데이트할 댓글 정보
	 * @return Comment (댓글 정보)
	 */
	Comment commentUpdate(Comment comment);

	/**
	 * 댓글 저장
	 * @param Comment comment - 저장할 댓글 정보
	 * @return Comment (댓글 정보)
	 */
	Comment commentInsert(Comment comment);

	/**
	 * 댓글 삭제
	 * @param int id - 댓글 pk
	 * @return Long (댓글 pk) # ? 확인해보기
	 */
	Long commentDelete(int id);

	/**
	 * 해당 피드의 댓글 총 갯수
	 * @param int fid - 피드 pk
	 * @return Long (댓글 갯수)
	 */
	Long commentCount(int fid);
}
