package com.ssafy.sub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.dto.Relationship;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.service.RelationService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/relations")
public class RelationController {

	@Autowired
	RelationService relationService;
	
		// 1. 팔로우 조회
		@ApiOperation(value = "팔로우 조회", response = Result.class)
		@GetMapping(value="/follower")
		public ResponseEntity<Result> relationFollowerList(Authentication authentication) {
			System.out.println("log - relationFollowerList");
			
			List<Relationship> FollowerList = new ArrayList<Relationship>();
			String id = authentication.getName();
			
			FollowerList = relationService.relationFollowerList(Integer.parseInt(id));

			Result result = new Result(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, FollowerList);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		}
		
		
		// 2. 팔로잉 조회
		@ApiOperation(value = "팔로잉 조회", response = Result.class)
		@GetMapping(value="/following")
		public ResponseEntity<Result> relationFollowingList(Authentication authentication) {
			System.out.println("log - relationFollowingList");
			
			List<Relationship> FollowingList = new ArrayList<Relationship>();
			String id = authentication.getName();
			
			FollowingList = relationService.relationFollowingList(Integer.parseInt(id));

			Result result = new Result(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, FollowingList);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		}
	
}
