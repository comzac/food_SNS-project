package com.ssafy.sub.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.config.security.JwtTokenProvider;
import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.service.FeedService;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/feeds")
public class FeedController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private FeedService feedService;
	 
	// 1. list 조회
	@ApiOperation(value = "feedList를 조회한다", response = Result.class)
	@GetMapping(value="/home")
	public ResponseEntity<Result> feedHomeList() {
		System.out.println("log - feedHomeList");
		Result result;
		
		List<Feed> feedList = feedService.feedHomeList();
		
		// null값 처리 안하는걸로 그래도 혹시 코드 필요할지 몰라서 남겨둠
//		if(feedList==null) {
//			result = new Result(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_FEED, null);
//			return new ResponseEntity<Result>(result, HttpStatus.NO_CONTENT);
//		}
		
		result = new Result(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, feedList);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "내 피드 목록을 조회한다", response = Result.class)
	@GetMapping(value="/mypage")
	public ResponseEntity<Result> feedMypageList(Authentication authentication) {
		System.out.println("log - feedMypageList");

		Claims claims = (Claims) authentication.getPrincipal();
		int uid = claims.get("id", Integer.class);
		List<Feed> feedList = feedService.feedMypageList(uid);
		
		Result result = new Result(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, feedList);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	// 2. list 검색 ( 기준이 애매해서 일단 비워둠 )
//	@ApiOperation(value = "feedList의 내용 중 일부를 검색한다", response = Feed.class)
//	@GetMapping(value = "/feed/search")
//	public ResponseEntity<List<Feed>> feedListSearch() {
//		System.out.println("log - feedListSearch");
//
//		List<Feed> FeedList = feedService.feedListSearch();
//		
//	return new ResponseEntity<List<Feed>>(FeedList, HttpStatus.OK);
//	}
	
	// 3. list 추가
	@ApiOperation(value = "feedList에 정보를 추가한다", response = String.class)
	@PostMapping
	public ResponseEntity<String> feedInsert(@RequestBody Feed feed, HttpServletRequest request) {
		System.out.println("log - feedInsert");
//		String token = jwtTokenProvider.resolveToken(request);
//		int uid = jwtTokenProvider.getUserId(token);
//		
//		feed.setUid(uid);
		feedService.feedInsert(feed);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	// 4. list 상세
	@ApiOperation(value = "특정 feed를 조회한다", response = Result.class)
	@GetMapping(value = "/{id}")
	public ResponseEntity<Result> feedDetail(@PathVariable int id) {
		System.out.println("log - feedDetail");

		Feed feed = feedService.feedDetail(id);
		
		Result result = new Result(StatusCode.OK, ResponseMessage.READ_FEED, feed);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	// 5. list 수정
	@ApiOperation(value = "feed의 정보를 수정한다", response = Result.class)
	@PutMapping(value = "/{id}")
	public ResponseEntity<Result> feedUpdate(@PathVariable int id, @RequestBody Feed feed) {
		System.out.println("log - feedUpdate");

		Feed updateFeed = feedService.feedUpdate(id, feed);
		
		Result result = new Result(StatusCode.OK, ResponseMessage.UPDATE_FEED, updateFeed);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	// 6. list 삭제
	@ApiOperation(value = "feed의 정보를 삭제한다", response = Result.class)
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Result> feedDelete(@PathVariable int id) {
		System.out.println("log - feedDelete");

		feedService.feedDelete(id);
		
		Result result = new Result(StatusCode.OK, ResponseMessage.DELETE_FEED, null);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
}
