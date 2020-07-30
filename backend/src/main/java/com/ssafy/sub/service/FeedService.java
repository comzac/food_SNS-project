package com.ssafy.sub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.Hashtag;

@Service("FeedService")
public interface FeedService {
	
	public List<Feed> feedHomePageList();
	
	public List<Feed> feedUserPageList(int uid);

	public List<Feed> findAllByFollower(int id);

	// search가 빠짐 (기준 설정이 필요)

	public Feed feedDetail(int id);

	public Feed feedInsert(Feed feed);

	public Feed feedUpdate(int id, Feed feed);

	public Long feedDelete(int id);

	public List<Hashtag> findAllHashtag();
	
	public boolean findByContent(String content);
	
	public Hashtag hashtagInsert(String content);
	
	public Hashtag hashtagUpdate(int hid, String content);
	
	public Hashtag hashtagDetail(int hid);
	
	public int getFeedCount(int uid);
	
	public List<Hashtag> feedHashtagList(int fid);
	
	public int feedHashtagListInsert(List<Hashtag> hashtagList);
	
	public List<Hashtag> feedHashtagListUpdate(int fid, List<Hashtag> hashtagList);
	
	public List<Hashtag> findFeedHashtagList(int fid);

}
