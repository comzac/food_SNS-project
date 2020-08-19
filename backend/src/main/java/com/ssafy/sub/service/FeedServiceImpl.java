package com.ssafy.sub.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.FeedHashtag;
import com.ssafy.sub.dto.FeedHashtagKey;
import com.ssafy.sub.dto.Hashtag;
import com.ssafy.sub.exception.RestException;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.repo.DBFileRepository;
import com.ssafy.sub.repo.FeedHashtagQueryDsl;
import com.ssafy.sub.repo.FeedHashtagRepository;
import com.ssafy.sub.repo.FeedQueryDsl;
import com.ssafy.sub.repo.FeedRepository;
import com.ssafy.sub.repo.HashtagQueryDsl;
import com.ssafy.sub.repo.HashtagRepository;
import com.ssafy.sub.repo.LogQueryDsl;

@Service
public class FeedServiceImpl implements FeedService {

	@Autowired
	FeedRepository feedRepository;
	@Autowired
	HashtagRepository hashtagRepository;
	@Autowired
	HashtagQueryDsl hashtagQueryDsl;
	@Autowired
	FeedHashtagRepository feedHashtagRepository;
	@Autowired
	FeedHashtagQueryDsl feedHashtagQueryDsl;
	@Autowired
	DBFileRepository dbFileRepository;
	@Autowired
	FeedQueryDsl feedQueryDsl;
	@Autowired
	LogQueryDsl logQueryDsl;

	@Override
	public List<Feed> feedHomePageList() {
		//return feedRepository.findAll();
		return feedQueryDsl.findFeedList();
	}
	
	@Override
	public List<Hashtag> findFeedHashtagList(int fid) {
		List<Hashtag> hashtagList = feedHashtagQueryDsl.findHashtagById(fid);
		return hashtagList;
	}
	
	@Override
	public List<Feed> feedUserPageList(int uid) {
		List<Feed> feeds = feedQueryDsl.findFeedListByUid(uid);
		return feeds;
	}

	@Override
	public List<Feed> findAllByFollower(int id) {
		List<Feed> test = feedQueryDsl.findAllByFollower(id);
		return test;
	}

	@Override
	public Feed feedDetail(int id) {
		Feed feed = feedRepository.findById(id)
		.orElseThrow(() -> new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_FEED, HttpStatus.NOT_FOUND));

		return feed;
	}

	@Override
	public Feed feedInsert(Feed feed) {
		feed.setRegdate(new Date());
		return feedRepository.save(feed);
	}

	@Override
	@Transactional
	public Feed feedUpdate(int id, Feed feed) {
		Date now = new Date();
		Optional<Feed> updateFeed = feedRepository.findById(id);
		if(!updateFeed.isPresent()) throw new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_FEED, HttpStatus.NOT_FOUND);
		updateFeed.get().setTitle(feed.getTitle());
		updateFeed.get().setContent(feed.getContent());
		updateFeed.get().setEditdate(now);

		return updateFeed.get();
	}

	@Override
	public Long feedDelete(int id) {
		return feedRepository.deleteById(id);
	}

	@Override
	public List<Hashtag> findAllHashtag() {
		return hashtagRepository.findAll();
	}

	@Override
	public Hashtag findByContent(String content) {
		return hashtagRepository.findByContent(content);
	}

	@Override
	public Hashtag hashtagInsert(String content) {
		Hashtag h = new Hashtag();
		h.setContent(content);
		Hashtag hashtag = hashtagRepository.save(h);
		if(hashtag==null)
			throw new RestException(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_CREATE_HASHTAG);
		return hashtag;
	}

	@Override
	public Hashtag hashtagUpdate(int hid, String content) {
		Optional<Hashtag> updateHashtag = hashtagRepository.findById(hid);
		if(!updateHashtag.isPresent())
			throw new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_HASHTAG);
		updateHashtag.get().setContent(content);
		return updateHashtag.get();
	}

	@Override
	public Hashtag hashtagDetail(int hid) {
		return hashtagRepository.findById(hid)
				.orElseThrow(() -> new RestException(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_HASHTAG));
	}
	
	@Override
	public int getFeedCount(int uid) {
		// feed 수
		int feedCount = 0;
		feedCount = feedRepository.findByUid(uid).size();
		return feedCount;
	}

	@Override
	public int feedHashtagListInsert(List<Hashtag> hashtagList, int fid) {
		System.out.println(fid);
		String content;
		int hid;
		FeedHashtag feedHashtag = new FeedHashtag();
		Hashtag hashtag;
		System.out.println(hashtagList.toString());
		for(Hashtag h: hashtagList) {
			content = h.getContent();
			System.out.println();
			
			if(hashtagRepository.findByContent(content)!=null) {
				hid = hashtagRepository.findByContent(content).getId();
			}else {
				Hashtag ht = new Hashtag();
				ht.setContent(content);
				hashtag = hashtagRepository.save(ht);
				hid = hashtag.getId();
			}
			
			feedHashtag.setFeedHashtagkey(new FeedHashtagKey(fid, hid));
			feedHashtagRepository.save(feedHashtag);
		}
		
		return 0;
	}

	@Override
	public List<Hashtag> feedHashtagListUpdate(int fid, List<Hashtag> hashtagList) {
		String content;
		int hid;
		FeedHashtag feedHashtag = new FeedHashtag();
		Hashtag hashtag;
		System.out.println(hashtagList.toString());
		
		feedHashtagQueryDsl.feedHashtagDeleteByFid(fid);
		for(Hashtag h: hashtagList) {
			content = h.getContent();
			
			if(hashtagRepository.findByContent(content)!=null) {
				hid = hashtagRepository.findByContent(content).getId();
			}else {
				Hashtag ht = new Hashtag();
				ht.setContent(content);
				hashtag = hashtagRepository.save(ht);
				hid = hashtag.getId();
			}
			
			System.out.println(feedHashtagQueryDsl.findFeedHashtag(fid, hid).toString());
			if(feedHashtagQueryDsl.findFeedHashtag(fid, hid).size()==0) {
				feedHashtag.setFeedHashtagkey(new FeedHashtagKey(fid, hid));
				feedHashtagRepository.save(feedHashtag);
			}
			
		}
		
		return hashtagList;
	}

	@Override
	public List<Feed> searchByHashtag(String keyword) {
		return feedQueryDsl.searchByHashtag(keyword);
	}

	@Override
	public List<Feed> searchByUserID(int uid) {
		return feedQueryDsl.searchByUserID(uid);
	}

	@Override
	public List<Hashtag> findHashtagByKeyword(String keyword) {
		return hashtagQueryDsl.findHashtagByKeyword(keyword);
	}

	@Override
	public Long countFeedByHashtag(int id) {
		return hashtagQueryDsl.countFeedByHashtag(id);
	}

	@Override
	public Long countFeedByUser(int user_id) {
		return feedQueryDsl.countFeedByUser(user_id);

	}


	@Override
	public List<Feed> feedPagination(Long pageNum, Long fid, int limit) {
		List<Feed> feedList = new ArrayList<Feed>();
		Pageable pageable = PageRequest.of(0, limit, Sort.by("id").descending());
		Page<Feed> feedPageList = feedRepository.findByIdLessThan(fid.intValue(), pageable);
		feedList = feedPageList.getContent();
		
		return feedList;
	}
	
	@Override
	public List<Feed> feedFollowPagination(int uid, Long pageNum, Long fid, int limit) {
		Pageable pageable = PageRequest.of(0, limit, Sort.by("id").descending());
		List<Feed> feedPageList = feedQueryDsl.findByIdLessThanFollower(uid, fid.intValue(), pageable);
		
		return feedPageList;
	}

	@Override
	public List<Feed> getRecommandFeed(int uid, int uageGroup, int usex) {
		//  Accumulate가 높은순으로 해쉬태그 리스트 반환
		List<Integer> hidList = logQueryDsl.findHidOrderByAccumulate(uageGroup, usex);
		List<Feed> recommandFeedList = new ArrayList<Feed>();
		if(!hidList.isEmpty()) {
			// 추천할 피드없으면 해쉬태그 계속 타고 내려가기
			for(int hid: hidList) {
				recommandFeedList.addAll(feedQueryDsl.findByRecommandHid(hid, uid));	
			}
		}
		return recommandFeedList;
	}
	
	@Override
	public Feed getRecommandFeedFetchOne(int uid, int uageGroup, int usex, int lastFidRecommand) {
		//  Accumulate가 높은순으로 해쉬태그 리스트 반환
		List<Integer> hidList = logQueryDsl.findHidOrderByAccumulate(uageGroup, usex);
		List<Feed> recommandFeedList = new ArrayList<Feed>();
		
		Feed recommandFeed = new Feed();
		boolean flag=false;
		if(!hidList.isEmpty()) {
			// 추천할 피드없으면 해쉬태그 계속 타고 내려가기
			for(int hid: hidList) {
				if(flag) lastFidRecommand=Integer.MAX_VALUE;	// 해쉬태그 넘어가면 마지막 피드번호 갱신

//				recommandFeedList = feedQueryDsl.findByRecommandHidFetchOne(hid, uid, lastFidRecommand);	
//				if(!recommandFeedList.isEmpty()) break;
				recommandFeed = feedQueryDsl.findByRecommandHidFetchOne(hid, uid, lastFidRecommand);
				if(recommandFeed!=null) break;
				
				flag=true;
			}
		}
		return recommandFeed;
	}
}
