package com.ssafy.sub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Contest;
import com.ssafy.sub.dto.ContestFeed;
import com.ssafy.sub.dto.ContestFeedLike;
import com.ssafy.sub.repo.ContestFeedFilesRepository;
import com.ssafy.sub.repo.ContestFeedLikeRepository;
import com.ssafy.sub.repo.ContestFeedRepository;
import com.ssafy.sub.repo.ContestRepository;

@Service
public class ContestServiceImpl implements ContestService {

	@Autowired
	ContestRepository contestRepository;
	@Autowired
	ContestFeedRepository contestFeedRepository;
	@Autowired
	ContestFeedLikeRepository contestFeedLikeRepository;
	@Autowired
	ContestFeedFilesRepository contestFeedFilesRepository;
	
	@Override
	public List<Contest> getContest() {
		return null;
	}

	@Override
	public List<ContestFeed> getContestFeedByIdOrderByLike(int cid) {
		return null;
	}

	@Override
	public ContestFeed insertContestFeed(ContestFeed cFeed) {
		return null;
	}

	@Override
	public ContestFeed getContestFeed(int fid) {
		return null;
	}

	@Override
	public Long deleteContestFeed(int fid) {
		return null;
	}

	@Override
	public ContestFeedLike insertContestFeedLike(int uid, int fid) {
		return null;
	}

	@Override
	public Long deleteContestFeedLike(int uid, int fid) {
		return null;
	}
	
}
