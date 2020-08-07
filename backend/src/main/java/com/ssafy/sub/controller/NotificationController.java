package com.ssafy.sub.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.dto.DBProfile;
import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.NotificationNonRead;
import com.ssafy.sub.dto.NotificationRead;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.model.response.Notification;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.service.FeedService;
import com.ssafy.sub.service.NotificationService;
import com.ssafy.sub.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
	NotificationService notificationService;
	@Autowired
	UserService userService;
	@Autowired
	FeedService feedService;
	
	@ApiOperation(value = "로그인한 유저의 안읽은 알림과 읽은 알림 보내주기", response = Result.class)
	@GetMapping("/")
	public ResponseEntity getNotification(Authentication authentication) {
		if(authentication==null) {
			return new ResponseEntity<Result>(
					new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), HttpStatus.FORBIDDEN);
		} 
		
		int LoginUid = Integer.parseInt(authentication.getName());
		Long notiNonReadCount = notificationService.countNotificationNonRead(LoginUid);	// 안읽은 알림 수
		
		return new ResponseEntity<Result>(
				new Result(StatusCode.OK, ResponseMessage.READ_NOTIFICATION, notiNonReadCount), HttpStatus.OK);
	}
	
	@ApiOperation(value = "로그인한 유저의 안읽은 알림과 읽은 알림 보내주기", response = Result.class)
	@GetMapping("/all")
	public ResponseEntity getAllNotification(Authentication authentication) {
		if(authentication==null) {
			return new ResponseEntity<Result>(
					new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), HttpStatus.FORBIDDEN);
		}
		
		int LoginUid = Integer.parseInt(authentication.getName());
				
		Long notiNonReadCount = notificationService.countNotificationNonRead(LoginUid);	// 안읽은 알림 수
		List<NotificationNonRead> notificationNonRead = notificationService.findNotificationNonReadByUid(LoginUid);
		Long notiReadCount = notificationService.countNotificationRead(LoginUid); // 읽은 알림 수
		List<NotificationRead> notificationRead = notificationService.findNotificationReadByUid(LoginUid);
		
		// for response
		List<Notification> nonReadResponse = new ArrayList<Notification>();
		List<Notification> readResponse = new ArrayList<Notification>();
		
		User LoginUser = userService.findById(LoginUid);
		for(NotificationNonRead nr: notificationNonRead) {
			switch(nr.getState()) {
				case 1:	// follow
					User follower = userService.findById(nr.getRid());
					DBProfile profile = userService.getProfile(follower.getId());
					nonReadResponse.add(Notification.builder().id(nr.getId()).state(nr.getState())
														.uid(LoginUser.getUid()).unick(LoginUser.getUnick())
														.followid(follower.getUid()).notiUnick(follower.getUnick())
														.notiUprofile(profile)
														.build());
					break;
				case 2:	// like
					Feed feed = feedService.feedDetail(nr.getFid());
					User likeUser = userService.findById(nr.getLid());
					profile = userService.getProfile(likeUser.getId());
					nonReadResponse.add(Notification.builder().id(nr.getId()).state(nr.getState())
														.uid(LoginUser.getUid()).unick(LoginUser.getUnick())
														.likeid(likeUser.getUid()).notiUnick(likeUser.getUnick())
														.notiUprofile(profile)
														.fid(nr.getFid()).title(feed.getTitle())
														.build());	
					break;
				case 3:	// comment
					feed = feedService.feedDetail(nr.getFid());
					User commentUser = userService.findById(nr.getCid());
					profile = userService.getProfile(commentUser.getId());
					nonReadResponse.add(Notification.builder().id(nr.getId()).state(nr.getState())
														.uid(LoginUser.getUid()).unick(LoginUser.getUnick())
														.commentid(commentUser.getUid()).notiUnick(commentUser.getUnick())
														.notiUprofile(profile)
														.fid(nr.getFid()).title(feed.getTitle())
														.build());
					break;
			}
		}
		
		for(NotificationRead r: notificationRead) {
			switch(r.getState()) {
			case 1:	// follow
				User follower = userService.findById(r.getRid());
				DBProfile profile = userService.getProfile(follower.getId());
				readResponse.add(Notification.builder().id(r.getId()).state(r.getState())
													.uid(LoginUser.getUid()).unick(LoginUser.getUnick())
													.followid(follower.getUid()).notiUnick(follower.getUnick())
													.notiUprofile(profile)
													.build());
				break;
			case 2:	// like
				Feed feed = feedService.feedDetail(r.getFid());
				User likeUser = userService.findById(r.getLid());
				profile = userService.getProfile(likeUser.getId());
				readResponse.add(Notification.builder().id(r.getId()).state(r.getState())
													.uid(LoginUser.getUid()).unick(LoginUser.getUnick())
													.likeid(likeUser.getUid()).notiUnick(likeUser.getUnick())
													.notiUprofile(profile)
													.fid(r.getFid()).title(feed.getTitle())
													.build());	
				break;
			case 3:	// comment
				feed = feedService.feedDetail(r.getFid());
				User commentUser = userService.findById(r.getCid());
				profile = userService.getProfile(commentUser.getId());
				readResponse.add(Notification.builder().id(r.getId()).state(r.getState())
													.uid(LoginUser.getUid()).unick(LoginUser.getUnick())
													.commentid(commentUser.getUid()).notiUnick(commentUser.getUnick())
													.notiUprofile(profile)
													.fid(r.getFid()).title(feed.getTitle())
													.build());
				break;
			}
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nonReadCount", notiNonReadCount);
		map.put("nonReadNotification", nonReadResponse);
		map.put("readCount", notiReadCount);
		map.put("readNotification", readResponse);
		return new ResponseEntity<Result>(
				new Result(StatusCode.OK, ResponseMessage.READ_ALL_NOTIFICATION, map), HttpStatus.OK);
	}
	
	@ApiOperation(value = "로그인한 유저가 알림을 읽은 경우", response = Result.class)
	@GetMapping("/{nid}")
	public ResponseEntity readNotification(@PathVariable int nid, Authentication authentication) {
		if(authentication==null) {
			return new ResponseEntity<Result>(
					new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), HttpStatus.FORBIDDEN);
		}
		
		NotificationRead notiRead = notificationService.readNotification(nid);

		if(notiRead != null) {
			return new ResponseEntity<Result>(
					new Result(StatusCode.OK, ResponseMessage.READ_NOTIFICATION, null), HttpStatus.OK);
		}else {
			return new ResponseEntity<Result>(
					new Result(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_READ_NOTIFICATION, null), HttpStatus.BAD_REQUEST);
		}
	}
	
}
