package com.ssafy.sub.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.FeedHashtag;
import com.ssafy.sub.dto.Hashtag;
import com.ssafy.sub.exception.RestException;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.repo.FeedHashtagRepository;
import com.ssafy.sub.repo.FeedQueryDsl;
import com.ssafy.sub.repo.FeedRepository;
import com.ssafy.sub.repo.HashtagRepository;
@Service
public class FeedServiceImpl implements FeedService {

	@Autowired
	FeedRepository feedRepository;
	@Autowired
	HashtagRepository hashtagRepository;
	@Autowired
	FeedHashtagRepository feedHashtagRepository;
	@Autowired
	FeedQueryDsl feedQueryDsl;

	@Override
	public List<Feed> feedHomePageList() {
		return feedRepository.findAll();
	}
	
	@Override
	public List<Feed> feedUserPageList(int uid) {
		List<Feed> feeds = feedRepository.findByUid(uid);
		int fid;
		List<FeedHashtag> feedHashtagList = new ArrayList<FeedHashtag>();
		List<Hashtag> hashtagList = new ArrayList<Hashtag>();
		for(int i=0; i<feeds.size(); i++) {
			fid = feeds.get(i).getId();
			System.out.println(fid);
			feedHashtagList = feedHashtagRepository.findAllByFeedId(fid);
//			for(FeedHashtag fh: feedHashtagList) {
//				System.out.println("Hashtag id: "+fh.getId());
//				System.out.println("next: "+fh.getHashtagId());
//				System.out.println(hashtagRepository.findById(fh.getHashtagId()).get().toString());
//				hashtagList.add(hashtagRepository.findById(fh.getHashtagId()).get());
//			}
//			feeds.get(i).setHashtag(hashtagList);
		}
		
		return feeds;
	}

	@Override
	public List<Feed> findAllByFollower(int id) {
		List<Feed> test = feedQueryDsl.findAllByFollower(id);
		
		for (Feed feed : test) {
			System.out.println(feed.toString());
		}
		
		if(test.size() == 0)
			System.out.println("null z?");
		return test;
	}

	@Override
	public Feed feedDetail(int id) {
		return feedRepository.findById(id)
					.orElseThrow(() -> new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_FEED, HttpStatus.NOT_FOUND));
	}

	@Override
	public Feed feedInsert(Feed feed) {
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
		
		feed.setRegdate(updateFeed.get().getRegdate());
		feed.setEditdate(now);
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
	public boolean findByContent(String content) {
		if(hashtagRepository.findByContent(content)!=null) {
			return false;
		}
		return true;
	}

	@Override
	public Hashtag hashtagInsert(String content) {
		Hashtag hashtag = hashtagRepository.save(content);
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
	
	
}
