package com.ssafy.sub.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.dto.Contest;
import com.ssafy.sub.dto.ContestFeed;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.service.ContestService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/contests")
public class ContestController {
	
	@Autowired
	ContestService contestService;
	
	@ApiOperation(value = "모든 콘테스트의 정보를 반환한다", response = Result.class)
	@GetMapping(value="/all")
	public ResponseEntity getContest() {
		List<Contest> contestList = contestService.getContest();
		
		return new ResponseEntity<Result>(new Result(StatusCode.CREATED, ResponseMessage.CREATED_USER, null), 
				HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "최근 콘테스트의 정보를 반환한다", response = Result.class)
	@GetMapping(value="/")
	public ResponseEntity getContestLatest() {
		System.out.println("latest contest list");
		
		List<ContestFeed> contestFeeds = contestService.findByCidOrderByLike(1);
		System.out.println(contestFeeds.size());
//		ContestFeed contestFeed = contestService.getContestFeed(2);
		
		return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, contestFeeds), 
				HttpStatus.OK);
	}
	
	@ApiOperation(value = "cid에 해당하는 콘테스트의 모든 정보를 좋아요가 많은 순서대로 반환한다.", response = Result.class)
	@GetMapping(value="/{cid}")
	public ResponseEntity getContestById(@PathVariable int cid, Authentication authentication) {
		// 좋아요 순서대로 반환해야됨!
		User LoginUser = (User) authentication.getPrincipal();
		if(LoginUser==null) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), 
					HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<Result>(new Result(StatusCode.CREATED, ResponseMessage.CREATED_USER, null), 
				HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "피드를 생성한다.", response = Result.class)
	@PostMapping(value="/feeds")
	public ResponseEntity insertContestFeed(@RequestBody ContestFeed contestFeed, Authentication authentication) {
		User LoginUser = (User) authentication.getPrincipal();
		if(LoginUser==null) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), 
					HttpStatus.FORBIDDEN);
		}
		
		contestFeed.setUid(LoginUser.getId());
		contestFeed.setRegdate(new Date());
		contestService.insertContestFeed(contestFeed);
		
		return new ResponseEntity<Result>(new Result(StatusCode.CREATED, ResponseMessage.CREATE_FEED, null), 
				HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "fid번 피드를 조회한다.", response = Result.class)
	@GetMapping(value="/feeds/{fid}")
	public ResponseEntity getContestFeed(@PathVariable int fid, Authentication authentication) {
		User LoginUser = (User) authentication.getPrincipal();
		if(LoginUser==null) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), 
					HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<Result>(new Result(StatusCode.CREATED, ResponseMessage.CREATED_USER, null), 
				HttpStatus.CREATED);
	}

	@ApiOperation(value = "fid번 피드를 삭제한다.", response = Result.class)
	@DeleteMapping(value="/feeds/{fid}")
	public ResponseEntity deleteContestFeed(@PathVariable int fid, Authentication authentication) {
		User LoginUser = (User) authentication.getPrincipal();
		if(LoginUser==null) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), 
					HttpStatus.FORBIDDEN);
		}
		
		Long ret = contestService.deleteContestFeed(fid);
		if(ret>0L) {
			return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.DELETE_FEED, null), 
					HttpStatus.OK);
		}else {
			return new ResponseEntity<Result>(new Result(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_DELETE_FEED, null), 
					HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@ApiOperation(value = "fid번 피드 좋아요를 누른다.", response = Result.class)
	@PostMapping(value="/likes/{fid}")
	public ResponseEntity insertContestFeedLike(@PathVariable int fid, Authentication authentication) {
		User LoginUser = (User) authentication.getPrincipal();
		if(LoginUser==null) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), 
					HttpStatus.FORBIDDEN);
		}
		
		contestService.updateFeedLikeCount(fid, 1);
		if(contestService.insertContestFeedLike(LoginUser.getId(), fid)!=null) {
			return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.LIKE_FEED, null), 
					HttpStatus.OK);
		}else {
			return new ResponseEntity<Result>(new Result(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_LIKE_FEED, null), 
					HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@ApiOperation(value = "fid번 피드 좋아요를 취소한다.", response = Result.class)
	@DeleteMapping(value="/likes/{fid}")
	public ResponseEntity deleteContestFeedLike(@PathVariable int fid, Authentication authentication) {
		User LoginUser = (User) authentication.getPrincipal();
		if(LoginUser==null) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), 
					HttpStatus.FORBIDDEN);
		}
		
		contestService.deleteContestFeedLike(LoginUser.getId(), fid);
		contestService.updateFeedLikeCount(fid, -1);
		return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.UNLIKE_FEED, null), 
				HttpStatus.OK);
	}
	
}
