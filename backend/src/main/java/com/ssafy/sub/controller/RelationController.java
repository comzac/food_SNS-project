package com.ssafy.sub.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.dto.Relationship;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.repo.UserRepository;
import com.ssafy.sub.service.RelationService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/relations")
public class RelationController {

	@Autowired
	RelationService relationService;

	@Autowired
	UserRepository userRepository;
	
	// 1. 팔로우 조회
	@ApiOperation(value = "팔로우 조회", response = Result.class)
	@GetMapping(value = "/follower")
	public ResponseEntity<Result> relationFollowerList(Authentication authentication, @RequestParam String rUid) {
		System.out.println("log - relationFollowerList");

		List<Relationship> FollowerList = new ArrayList<Relationship>();

		FollowerList = relationService.relationFollowerList(Integer.parseInt(rUid));

		Result result = new Result(StatusCode.OK, ResponseMessage.READ_FOLLOWER, FollowerList);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	// 2. 팔로잉 조회
	@ApiOperation(value = "팔로잉 조회", response = Result.class)
	@GetMapping(value = "/following")
	public ResponseEntity<Result> relationFollowingList(Authentication authentication, @RequestParam String rUid) {
		System.out.println("log - relationFollowingList");

		List<Relationship> FollowingList = new ArrayList<Relationship>();

		FollowingList = relationService.relationFollowingList(Integer.parseInt(rUid));

		Result result = new Result(StatusCode.OK, ResponseMessage.READ_FOLLOWING, FollowingList);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	// 3. 팔로우 추가
	@ApiOperation(value = "팔로우 추가", response = Result.class)
	@PostMapping(value = "/")
	public ResponseEntity<Result> followInsert(Authentication authentication, @RequestParam String rUid) {
		System.out.println("log - followInsert");

		Relationship relationship;
		String id = authentication.getName();
		Optional<User> addUser = userRepository.findByUid(rUid);
		int rid = addUser.get().getId();
		
		if (Integer.parseInt(id) == rid) {
			Result result = new Result(StatusCode.OK, ResponseMessage.FAIL_CREATE_FOLLOWER_SAME, null);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		}

		relationship = relationService.followInsert(Integer.parseInt(id), rid);
		
		if (relationship == null) {
			Result result = new Result(StatusCode.OK, ResponseMessage.FAIL_CREATE_FOLLOWER_DUP, null);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		}
		Result result = new Result(StatusCode.OK, ResponseMessage.CREATE_FOLLOWER, relationship);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	// 3. 팔로우 삭제
	@ApiOperation(value = "팔로우 삭제", response = Result.class)
	@DeleteMapping(value = "/")
	public ResponseEntity<Result> followDelete(Authentication authentication,  @RequestParam String rUid) {
		System.out.println("log - followDelete");

		String id = authentication.getName();
		Optional<User> addUser = userRepository.findByUid(rUid);
		int rid = addUser.get().getId();
		long ret = relationService.followDelete(Integer.parseInt(id), rid);

		Result result = new Result(StatusCode.OK, ResponseMessage.DELETE_FOLLOWER, ret);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
}
