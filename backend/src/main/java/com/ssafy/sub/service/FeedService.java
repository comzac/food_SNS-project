package com.ssafy.sub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Feed;

@Service("FeedService")
public interface FeedService {
	
	public List<Feed> feedHomeList();
	
	public List<Feed> feedMypageList(int uid);

	// search가 빠짐 (기준 설정이 필요)

	public Feed feedDetail(int id);

	public Feed feedInsert(Feed feed);

	public Feed feedUpdate(int id, Feed feed);

	public Long feedDelete(int id);
	}
