package com.ssafy.sub.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
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
import com.ssafy.sub.repo.ContestFeedQueryDsl;
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
	@Autowired
	ContestFeedQueryDsl contestFeedQueryDsl;
	
	@Override
	public List<Contest> getContest() {
		return contestRepository.findAllByOrderByIdDesc();
	}
	
	@Override
	public Contest getContestlatestRound() {
		try {
			Contest contest = contestRepository.findFirstByOrderByIdDesc();
			return contest;
		}catch(NoSuchElementException e) {
			throw new RestException(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_CONTEST);
		}
	}

	@Override
	public Contest getContestById(int cid) {
		try {
			return contestRepository.findById(cid);
		}catch(NoSuchElementException e) {
			throw new RestException(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_CONTEST);
		}
	}

	@Override
	public List<ContestFeed> findByCidOrderByLike(int cid) {
		try {
			return contestFeedRepository.findByCidOrderByLikeCountDesc(cid);
		}catch(NoSuchElementException e) {
			throw new RestException(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_FEED);
		}
	}

	@Override
	public ContestFeed insertContestFeed(ContestFeed cFeed) {
		return contestFeedRepository.save(cFeed);
	}

	@Override
	public Optional<ContestFeed> getContestFeed(int fid) {
		return contestFeedRepository.findById(fid);
	}

	@Override
	public ContestFeed updateContestFeed(ContestFeed updateContestFeed) {
		ContestFeed contestFeed = contestFeedRepository.findById(updateContestFeed.getId()).
				orElseThrow(() -> new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER));
		contestFeed.setTitle(updateContestFeed.getTitle());
		contestFeed.setContent(updateContestFeed.getContent());
		return contestFeedRepository.save(contestFeed);
	}
	
	@Override
	public HashMap<String, Object> getContestFeedLike(int fid) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		HashMap<String, Long> usex = new HashMap<String, Long>();
		Long female = contestFeedQueryDsl.findLikeCountFemale(fid);
		Long male = contestFeedQueryDsl.findLikeCountMale(fid);
		usex.put("female", female);
		usex.put("male", male);
		
		HashMap<String, Long> uage = new HashMap<String, Long>();
		try {
			Long age10 = contestFeedQueryDsl.findLikeAge10(fid);
			Long age20 = contestFeedQueryDsl.findLikeAge20(fid);
			Long age30 = contestFeedQueryDsl.findLikeAge30(fid);
			Long age40 = contestFeedQueryDsl.findLikeAge40(fid);
			Long age50 = contestFeedQueryDsl.findLikeAge50(fid);

			uage.put("age10", age10);
			uage.put("age20", age20);
			uage.put("age30", age30);
			uage.put("age40", age40);
			uage.put("age50", age50);
			
		} catch (ParseException e) {
			throw new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_BIRTH);
		}
		
		map.put("usex", usex);
		map.put("uage", uage);
		
		return map;
	}

	@Override
	public Long deleteContestFeed(int fid) {
		return contestFeedRepository.deleteById(fid);
	}

	@Override
	public ContestFeedLike insertContestFeedLike(int fid, int uid) {
		ContestFeedLike contestFeedLike = new ContestFeedLike(new ContestFeedLikeKey(fid, uid));
		return contestFeedLikeRepository.save(contestFeedLike);
	}

	@Override
	public void deleteContestFeedLike(int fid, int uid) {
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
	
	@Override
	public boolean findByContestFeedLike(int fid, int uid) {
		if(!contestFeedLikeRepository.findById(new ContestFeedLikeKey(fid, uid)).isPresent())
			return false;
		else
			return true;
	}


}
