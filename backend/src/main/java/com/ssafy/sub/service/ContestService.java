package com.ssafy.sub.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Contest;
import com.ssafy.sub.dto.ContestFeed;
import com.ssafy.sub.dto.ContestFeedLike;

@Service("ContestService")
public interface ContestService {
	/**
	 * 모든 회차의 콘테스트 기본 정보 - 셀렉박스 클릭 시
	 * @return List<Contest> (콘테스트 기본 정보)
	 */
	public List<Contest> getContest();
	
	/**
	 * 가장 최근 회차의 콘테스트 기본 정보
	 * @return Contest (콘테스트 기본 정보)
	 */
	public Contest getContestlatestRound();
	
	/**
	 * 해당 회차의 콘테스트 기본 정보
	 * @param int cid - 콘테스트의 기본 id
	 * @return Contest (콘테스트 기본 정보)
	 */
	public Contest getContestById(int cid);

	/**
	 * 콘테스트 피드를 좋아요 순으로 반환
	 * @param int cid - 콘테스트의 기본 id
	 * @return List<ContestFeed> (콘테스트 피드 정보)
	 */
	public List<ContestFeed> findByCidOrderByLike(int cid);
	
	/**
	 * 콘테스트 피드 생성
	 * @param ContestFeed cFeed - 생성하려는 콘테스트 피드 정보
	 * @return ContestFeed (콘테스트 피드 정보)
	 */
	public ContestFeed insertContestFeed(ContestFeed cFeed);

	/**
	 * 콘테스트 피드 수정
	 * @param ContestFeed contestFeed - 수정하려는 콘테스트 피드 정보
	 * @return ContestFeed (콘테스트 피드 정보)
	 */
	public ContestFeed updateContestFeed(ContestFeed updateContestFeed);
	
	/**
	 * 상세 콘테스트 피드 조회
	 * @param int fid - 콘테스트 피드의 기본 id
	 * @return Optional<ContestFeed> (콘테스트 피드 정보)
	 */
	public Optional<ContestFeed> getContestFeed(int fid);
	
	/**
	 * 콘테스트 피드의 성별, 나이대별의 좋아요 수 조회
	 * @param int fid - 콘테스트 피드의 기본 id
	 * @return HashMap<String, Object> (성별, 나이대별의 좋아요 수)
	 */
	public HashMap<String, Object> getContestFeedLike(int fid);
	
	/**
	 * 콘테스트 피드 삭제
	 * @param int fid - 콘테스트 피드의 기본 id
	 * @return Long fid (삭제된 피드의 기본 id)
	 */
	public Long deleteContestFeed(int fid);
	
	/**
	 * 콘테스트 피드 좋아요
	 * @param int fid - 콘테스트 피드의 기본 id
	 * @param int uid - 좋아요를 누른(현재 로그인한) 유저의 기본 id
	 * @return ContestFeedLike (콘테스트 피드 좋아요 정보)
	 */
	public ContestFeedLike insertContestFeedLike(int fid, int uid);
	
	/**
	 * 콘테스트 피드 좋아요 취소
	 * @param int fid - 콘테스트 피드의 기본 id
	 * @param int uid - 좋아요 취소를 누른(현재 로그인한) 유저의 기본 id
	 */
	public void deleteContestFeedLike(int fid, int uid);

	/**
	 * 콘테스트 피드의 좋아요 수 업데이트
	 * @param int fid - 콘테스트 피드의 기본 id
	 * @param int like - 좋아요인 경우 1, 좋아요 취소인 경우 -1 (피드 좋아요에 더해주기 위해)
	 * @return ContestFeed (콘테스트 피드 정보)
	 */
	public ContestFeed updateFeedLikeCount(int fid, int like);

	/**
	 * 로그인한 유저의 콘테스트 피드 좋아요 여부 확인
	 * @param int fid - 콘테스트 피드의 기본 id
	 * @param int uid - 현재 로그인한 유저의 기본 id
	 * @return boolean (좋아요 누른 상태: true)
	 */
	boolean findByContestFeedLike(int fid, int uid);

}
