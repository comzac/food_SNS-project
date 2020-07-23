package com.ssafy.sub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.FeedDto;
import com.ssafy.sub.repo.FeedMapper;
@Service
public class FeedServiceImpl implements FeedService {

	
	@Autowired
	FeedMapper feedMapper;
	
	@Override
	public List<FeedDto> feedList() {
		return feedMapper.feedList();
	}

	@Override
	public FeedDto feedDetail(int id) {
		return feedMapper.feedDetail(id);

	}

	@Override
	public int feedInsert() {
		return feedMapper.feedInsert();

	}

	@Override
	public int feedUpdate(int id, FeedDto dto) {
		return feedMapper.feedUpdate(id, dto);

	}

	@Override
	public int feedDelete(int id) {
		return feedMapper.feedDelete(id);
	}

	
	
}
