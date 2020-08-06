package com.ssafy.sub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Contest;
import com.ssafy.sub.dto.ContestFeed;
import com.ssafy.sub.dto.ContestFeedLike;

@Service("ContestService")
public interface ContestService {
	// 전 회차의 콘테스트 기본 정보 반환
	public List<Contest> getContest();
	
	// 피드를 좋아요 순으로 반환
	public List<ContestFeed> findByCidOrderByLike(int cid);
	
	// 피드 생성
	public ContestFeed insertContestFeed(ContestFeed cFeed);
	
	// 피드 조회
	public ContestFeed getContestFeed(int fid);
	
	// 피드 삭제
	public Long deleteContestFeed(int fid);
	
	// 피드 좋아요
	public ContestFeedLike insertContestFeedLike(int uid, int fid);
	
	// 피드 좋아요 취소
	public void deleteContestFeedLike(int uid, int fid);

	// 피드 좋아요 수 업데이트
	public ContestFeed updateFeedLikeCount(int fid, int like);
}
