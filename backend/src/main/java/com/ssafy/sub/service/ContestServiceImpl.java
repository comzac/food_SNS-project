package com.ssafy.sub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Contest;
import com.ssafy.sub.dto.ContestFeed;
import com.ssafy.sub.dto.ContestFeedLike;
import com.ssafy.sub.dto.ContestFeedLikeKey;
import com.ssafy.sub.exception.RestException;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.StatusCode;
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
		return contestRepository.findAll();
	}

	@Override
	public List<ContestFeed> findByCidOrderByLike(int cid) {
		return contestFeedRepository.findByCidOrderByLikeCountDesc(cid);
	}

	@Override
	public ContestFeed insertContestFeed(ContestFeed cFeed) {
		return contestFeedRepository.save(cFeed);
	}

	@Override
	public ContestFeed getContestFeed(int fid) {
		return contestFeedRepository.findById(fid).get();
	}

	@Override
	public Long deleteContestFeed(int fid) {
		return contestFeedRepository.deleteById(fid);
	}

	@Override
	public ContestFeedLike insertContestFeedLike(int uid, int fid) {
		ContestFeedLike contestFeedLike = new ContestFeedLike(new ContestFeedLikeKey(fid, uid));
		return contestFeedLikeRepository.save(contestFeedLike);
	}

	@Override
	public void deleteContestFeedLike(int uid, int fid) {
		ContestFeedLike contestFeedLike = new ContestFeedLike(new ContestFeedLikeKey(fid, uid));
		if(!contestFeedLikeRepository.findById(new ContestFeedLikeKey(fid, uid)).isPresent()) {
			throw new RestException(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_FEED_LIKE);
		}
		contestFeedLikeRepository.delete(contestFeedLike);
	}

	@Override
	public ContestFeed updateFeedLikeCount(int fid, int like) {
		Optional<ContestFeed> cFeed = contestFeedRepository.findById(fid);
		if(!cFeed.isPresent()) {
			throw new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_FEED);
		}
		
		int likeCount = cFeed.get().getLikeCount()+like;
		ContestFeed updateCFeed = cFeed.get();
		updateCFeed.setLikeCount(likeCount);
		return contestFeedRepository.save(updateCFeed);
	}
	
}
