package com.ssafy.sub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Relationship;

/**
 * 관계 (팔로우, 팔로잉) 관리
 * 
 * @author 이선수
 * @version 1.0, 관계 기본 정보  CRUD
 */

@Service("RelationService")
public interface RelationService {

	/**
	 * 유저 pk의 팔로워 목록
	 * @param int relationuid - 유저 pk 
	 * @return List<Relationship> (팔로우 유저 정보)
	 */
	List<Relationship> relationFollowerList(int relationuid);

	/**
	 * 유저 pk의 팔로잉 목록
	 * @param int uid - 유저 pk 
	 * @return List<Relationship> (팔로잉 유저 정보)
	 */
	List<Relationship> relationFollowingList(int uid);

	/**
	 * 팔로우/팔로우 취소
	 * @param int id - 유저 pk 
	 * @param int rid - 팔로잉 유저 pk 
	 * @return Relationship (팔로우 정보)
	 */
	Relationship followInsertAndDelete(int id, int rid);

	/**
	 * 
	 * @param int rid - 팔로잉 유저 pk 
	 * @param int uid - 유저 pk 
	 * @return
	 */
	boolean checkRelations(int rid, int uid);

	/**
	 * 
	 * @param int uid - 유저 pk 
	 * @param int rid - 팔로잉 유저 pk
	 * @return
	 */
	Relationship followCheck(int uid, int rid);
}