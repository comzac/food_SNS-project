package com.ssafy.sub.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.dto.Hashtag;
import com.ssafy.sub.dto.NotificationNonRead;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.service.FeedService;
import com.ssafy.sub.service.LikeService;
import com.ssafy.sub.service.NotificationService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/likes")
public class LikeController {

	@Autowired
	private LikeService likeService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private FeedService feedService;
	
	@Autowired
	LogController logController;

	// 1. feed like
	@ApiOperation(value = "피드 좋아요", response = Result.class)
	@PostMapping(value = "/feed/{fid}")
	public ResponseEntity feedLikeInsert(@PathVariable int fid, Authentication authentication) {
		User loginUser = (User) authentication.getPrincipal();
		
		int uid = loginUser.getId();
		likeService.feedLikeInsert(fid, uid);
		
		// 알림 설정
		int notiUid = feedService.feedDetail(fid).getUid();
		if(uid!=notiUid) {
			notificationService.notificationInsert(NotificationNonRead.builder().state(2)
					.uid(notiUid).lid(uid).fid(fid)
					.actionUid(uid).regdate(new Date()).build());
		}

		// for log
		String action = "like";	// for log action
		List<Hashtag> hashtagListLog = feedService.findFeedHashtagList(fid);
		logController.setString(loginUser, action, hashtagListLog);
		
		Result result = new Result(StatusCode.OK, ResponseMessage.LIKE_FEED, null);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	// 2. feed unlike
	@ApiOperation(value = "피드 좋아요 취소", response = Result.class)
	@DeleteMapping(value = "/feed/{fid}")
	public ResponseEntity feedLikeDelete(@PathVariable int fid, Authentication authentication) {
		User loginUser = (User) authentication.getPrincipal();
		
		int uid = loginUser.getId();
		likeService.feedLikeDelete(fid, uid);
		
		// for log
		String action = "unlike";	// for log action
		List<Hashtag> hashtagListLog = feedService.findFeedHashtagList(fid);
		logController.setString(loginUser, action, hashtagListLog);
		
		Result result = new Result(StatusCode.OK, ResponseMessage.UNLIKE_FEED, null);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	// 3. comment like
	@ApiOperation(value = "댓글 좋아요", response = Result.class)
	@PostMapping(value = "/comment/{cid}")
	public ResponseEntity commentLikeInsert(@PathVariable int cid, Authentication authentication) {
		int uid = Integer.parseInt(authentication.getName());
		likeService.commentLikeInsert(cid, uid);
		
		Result result = new Result(StatusCode.OK, ResponseMessage.LIKE_COMMENT, null);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	// 4. comment unlike
	@ApiOperation(value = "댓글 좋아요 취소", response = Result.class)
	@DeleteMapping(value = "/comment/{cid}")
	public ResponseEntity commentLikeDelete(@PathVariable int cid, Authentication authentication) {
		int uid = Integer.parseInt(authentication.getName());
		likeService.commentLikeDelete(cid, uid);
		
		Result result = new Result(StatusCode.OK, ResponseMessage.UNLIKE_COMMENT, null);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
}
