package com.ssafy.sub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.Hashtag;

@Service("FeedService")
public interface FeedService {
	
	/**
	 * 모든 피드 리스트 수정일, 등록일순 조회	# 삭제해도 될듯
	 * @return List<Feed> (피드 정보)
	 */
	public List<Feed> feedHomePageList();
	
	/**
	 * 유저의 모든 피드 등록일순 조회
	 * @param int uid - 유저 pk
	 * @return List<Feed> (피드 정보)
	 */
	public List<Feed> feedUserPageList(int uid);

	/**
	 * 유저 본인 및 팔로워들의 모든 피드 등록일순 조회
	 * @param int id - 유저 pk
	 * @return List<Feed> (피드 정보)
	 */
	public List<Feed> findAllByFollower(int id);

	/**
	 * 피드 상세 정보 조회
	 * @param int id - 피드 pk
	 * @return Feed (피드 정보)
	 */
	public Feed feedDetail(int id);

	/**
	 * 피드 생성
	 * @param Feed feed - 생성할 피드 정보
	 * @return Feed (피드 정보)
	 */
	public Feed feedInsert(Feed feed);

	/**
	 * 피드 변경
	 * @param int id - 피드 pk
	 * @param Feed feed - 변경할 피드 정보 
	 * @return Feed (피드 정보)
	 */
	public Feed feedUpdate(int id, Feed feed);

	/**
	 * 피드 삭제
	 * @param int id - 피드 pk
	 * @return Long (삭제된 피드 pk) # - 아마..?확인해보기
	 */
	public Long feedDelete(int id);

	/**
	 * 모든 해쉬태그 조회
	 * @return List<Hashtag> (해쉬태그 정보)
	 */
	public List<Hashtag> findAllHashtag();
	
	/**
	 * 이름을 이용한 해쉬태그 정보 조회
	 * @param String content - 이름
	 * @return Hashtag (해쉬태그 정보)
	 */
	public Hashtag findByContent(String content);
	
	/**
	 * 해쉬태그 생성
	 * @param String content - 이름
	 * @return Hashtag (해쉬태그 정보)
	 */
	public Hashtag hashtagInsert(String content);
	
	/**
	 * 해쉬태그 변경
	 * @param int hid - 해쉬태그 pk
	 * @param String content - 이름
	 * @return Hashtag (해쉬태그 정보)
	 */
	public Hashtag hashtagUpdate(int hid, String content);
	
	/**
	 * 해쉬태그 상세 정보 조회
	 * @param int hid - 해쉬태그 pk
	 * @return Hashtag (해쉬태그 정보)
	 */
	public Hashtag hashtagDetail(int hid);
	
	/**
	 * 유저의 총 피드 수 조회
	 * @param int uid - 유저 pk
	 * @return int (피드 수)
	 */
	public int getFeedCount(int uid);
	
	/**
	 * 피드에 해쉬태그 삽입
	 * @param Hashtag hashtagList - 삽입할 해쉬태그 정보
	 * @param int fid - 피드 pk
	 * @return int # 확인
	 */
	public int feedHashtagListInsert(List<Hashtag> hashtagList, int fid);
	
	/**
	 * 피드의 해쉬태그 변경
	 * @param int fid - 피드 pk
	 * @param Hashtag hashtagList - 변경할 해쉬태그 정보
	 * @return List<Hashtag> (해쉬태그 정보)
	 */
	public List<Hashtag> feedHashtagListUpdate(int fid, List<Hashtag> hashtagList);
	
	/**
	 * 피드의 해쉬태그 정보
	 * @param int fid - 피드 pk
	 * @return List<Hashtag> (해쉬태그 정보)
	 */
	public List<Hashtag> findFeedHashtagList(int fid);

	/**
	 * 검색 키워드를 포함한 해쉬태그가 달린 피드 조회
	 * @param String keyword - 검색 키워드
	 * @return List<Feed> (피드 정보)
	 */
	public List<Feed> searchByHashtag(String keyword);

	/**
	 * 유저의 모든 피드 수정일, 등록일순 조회
	 * @param int uid - 유저 pk
	 * @return List<Feed> (피드 정보)
	 */
	public List<Feed> searchByUserID(int uid);

	/**
	 * 검색 키워드를 포함한 해쉬태그 조회
	 * @param String keyword - 검색 키워드
	 * @return List<Hashtag> (해쉬태그 정보)
	 */
	public List<Hashtag> findHashtagByKeyword(String keyword);

	/**
	 * 피드 pk보다 작은(등록일순) 피드들 조회 (페이지네이션)
	 * @param pageNum
	 * @param Long fid - 피드 pk 
	 * @param int limit - 반환할 피드 수
	 * @return List<Feed> (피드 정보)
	 */
	public List<Feed> feedPagination(Long pageNum, Long fid, int limit);

	/**
	 * 해당 해쉬태그가 달린 피드의 수
	 * @param int id - 해쉬태그 pk
	 * @return Long (해쉬태그 수)
	 */
	public Long countFeedByHashtag(int id);

	/**
	 * 유저의 총 피드 수 조회	# getFeedCount랑 같은지?
	 * @param int user_id - 유저 pk
	 * @return Long (피드 수)
	 */
	public Long countFeedByUser(int user_id);
	
	/**
	 * 유저 본인과 팔로우들의 피드 pk보다 작은(등록일순) 피드들 조회 (페이지네이션)
	 * @param int uid - 유저 pk
	 * @param pageNum
	 * @param Long fid - 피드 pk 
	 * @param int limit - 반환할 피드 수
	 * @return List<Feed> (피드 정보)
	 */
	public List<Feed> feedFollowPagination(int uid, Long pageNum, Long fid, int limit);

	/**
	 * 유저의 성별, 나이대로 추천 피드 리스트 조회
	 * @param int uid - 유저 pk
	 * @param int uageGroup - 유저 나이대
	 * @param int usex - 유저 성별
	 * @return List<Feed> (피드 정보)
	 */
	public List<Feed> getRecommandFeed(int uid, int uageGroup, int usex);

	/**
	 * 유저의 성별, 나이대로 추천 피드 리스트 하나씩 조회
	 * @param int uid - 유저 pk
	 * @param int uageGroup - 유저 나이대
	 * @param int usex - 유저 성별
	 * @param int lastFidRecommand - 마지막 추천 피드 번호
	 * @return Feed (피드 정보)
	 */
	public Feed getRecommandFeedFetchOne(int uid, int uageGroup, int usex, int lastFidRecommand);

}
