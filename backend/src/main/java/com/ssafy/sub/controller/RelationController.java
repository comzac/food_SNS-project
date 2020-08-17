package com.ssafy.sub.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.dto.DBFile;
import com.ssafy.sub.dto.DBProfile;
import com.ssafy.sub.dto.NotificationNonRead;
import com.ssafy.sub.dto.Relationship;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.model.response.FollowResult;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.repo.DBProfileRepository;
import com.ssafy.sub.repo.FeedRepository;
import com.ssafy.sub.repo.RelationRepository;
import com.ssafy.sub.repo.UserRepository;
import com.ssafy.sub.service.NotificationService;
import com.ssafy.sub.service.RelationService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/relations")
public class RelationController {

	@Autowired
	RelationService relationService;

	@Autowired
	RelationRepository relationRepository;
	
    @Autowired
    private DBProfileRepository dbProfileRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	NotificationService notificationService;

	// 1. 팔로우 조회
	@ApiOperation(value = "팔로우 조회", response = Result.class)
	@GetMapping(value = "/follower/{uid}")
	public ResponseEntity<Result> relationFollowerList(Authentication authentication, @PathVariable String uid) {
		System.out.println("log - relationFollowerList");

		String id = authentication.getName(); // 로그인 유저 id

		List<Relationship> FollowerList = new ArrayList<Relationship>();
		
		Optional<User> addUser = userRepository.findByUid(uid); // 피드 주인 id 
		int rid = addUser.get().getId(); // uid가 comzac이라면 8이 나옴
		
		FollowerList = relationService.relationFollowerList(rid);

		for (Relationship relationship : FollowerList) {
			if (relationService.checkRelations(relationship.getRelationShipkey().getUid(), Integer.parseInt(id))) {
				relationship.setIsFollowing(1);
			} else {
				relationship.setIsFollowing(0);
			}
		}

		System.out.println("??");
		List<FollowResult> followList = new ArrayList<FollowResult>();
		for (Relationship relationship : FollowerList) {
			String userId = userRepository.findById(relationship.getRelationShipkey().getUid()).getUid();
			String rUserId = userRepository.findById(relationship.getRelationShipkey().getRelationuid()).getUid();
			String unick = userRepository.findByUid(userId).get().getUnick();
			System.out.println(userId);
			Optional<DBProfile> dbProfile = dbProfileRepository.findByUid(Integer.toString(relationship.getRelationShipkey().getUid()));
			FollowResult followResult;
			if(dbProfile.isPresent()) {
				followResult = new FollowResult(userId, rUserId, unick, relationship.getState(), relationship.getIsFollowing(), dbProfile.get());
			}
			else {
				followResult = new FollowResult(userId, rUserId, unick, relationship.getState(), relationship.getIsFollowing(), null);				
			}
			followList.add(followResult);
		}

		Result result = new Result(StatusCode.OK, ResponseMessage.READ_FOLLOWER, followList);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	// 2. 팔로잉 조회
	@ApiOperation(value = "팔로잉 조회", response = Result.class)
	@GetMapping(value = "/following/{uid}")
	public ResponseEntity<Result> relationFollowingList(Authentication authentication, @PathVariable String uid) {
		System.out.println("log - relationFollowingList");

		String id = authentication.getName(); // 로그인 유저 id
		
		List<Relationship> FollowingList = new ArrayList<Relationship>();

		Optional<User> addUser = userRepository.findByUid(uid); // 피드 주인 id
		int rid = addUser.get().getId(); // uid가 comzac이라면 8이 나옴
		
		FollowingList = relationService.relationFollowingList(rid);

		for (Relationship relationship : FollowingList) {
			if (relationService.checkRelations(relationship.getRelationShipkey().getRelationuid(), Integer.parseInt(id))) {
				relationship.setIsFollowing(1);
			} else {
				relationship.setIsFollowing(0);
			}
		}
		
		List<FollowResult> followList = new ArrayList<FollowResult>();
		for (Relationship relationship : FollowingList) {
			String userId = userRepository.findById(relationship.getRelationShipkey().getUid()).getUid();
			String rUserId = userRepository.findById(relationship.getRelationShipkey().getRelationuid()).getUid();
			String unick = userRepository.findByUid(rUserId).get().getUnick();

			Optional<DBProfile> dbProfile = dbProfileRepository.findByUid(Integer.toString(relationship.getRelationShipkey().getRelationuid()));
			FollowResult followResult;
			if(dbProfile.isPresent()) {
				followResult = new FollowResult(userId, rUserId, unick, relationship.getState(), relationship.getIsFollowing(), dbProfile.get());
			}
			else {
				followResult = new FollowResult(userId, rUserId, unick, relationship.getState(), relationship.getIsFollowing(), null);				
			}
			followList.add(followResult);
		}
		
		Result result = new Result(StatusCode.OK, ResponseMessage.READ_FOLLOWING, followList);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	// 3. 팔로우 추가
	@ApiOperation(value = "팔로우 추가/삭제", response = Result.class)
	@PostMapping(value = "/")
	public ResponseEntity<Result> followInsertAndDelete(Authentication authentication, @RequestParam String uid) {
		System.out.println("log - followInsertAndDelete");
		System.out.println(uid);

		Relationship relationship;
		String id = authentication.getName();
		Optional<User> addUser = userRepository.findByUid(uid);
		System.out.println(addUser.isPresent());
		int rid = addUser.get().getId();

		if (Integer.parseInt(id) == rid) {
			Result result = new Result(StatusCode.OK, ResponseMessage.FAIL_CREATE_FOLLOWER_SAME, null);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		}

		relationship = relationService.followInsertAndDelete(Integer.parseInt(id), rid);

		if (relationship == null) {
			Result result = new Result(StatusCode.OK, ResponseMessage.DELETE_FOLLOWER, null);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		}
		
		String userId = userRepository.findById(relationship.getRelationShipkey().getUid()).getUid();
		String rUserId = userRepository.findById(relationship.getRelationShipkey().getRelationuid()).getUid();
		String unick = userRepository.findByUid(rUserId).get().getUnick();

		FollowResult followResult = new FollowResult(userId, rUserId, unick, relationship.getState(), relationship.getIsFollowing());
	
		// 알림 설정
		notificationService.notificationInsert(NotificationNonRead.builder().state(1)
												.uid(rid).rid(Integer.parseInt(id))
												.actionUid(Integer.parseInt(id)).regdate(new Date()).build());
		
		Result result = new Result(StatusCode.OK, ResponseMessage.CREATE_FOLLOWER, followResult);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

//   // 3. 팔로우 삭제
//   @ApiOperation(value = "팔로우 삭제", response = Result.class)
//   @DeleteMapping(value = "/")
//   public ResponseEntity<Result> followDelete(Authentication authentication,  @RequestParam String rUid) {
//      System.out.println("log - followDelete");
//
//      String id = authentication.getName();
//      Optional<User> addUser = userRepository.findByUid(rUid);
//      int rid = addUser.get().getId();
//      long ret = relationService.followDelete(Integer.parseInt(id), rid);
//
//      Result result = new Result(StatusCode.OK, ResponseMessage.DELETE_FOLLOWER, ret);
//      return new ResponseEntity<Result>(result, HttpStatus.OK);
//   } 
}