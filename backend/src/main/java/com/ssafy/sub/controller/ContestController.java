package com.ssafy.sub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.dto.User;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/contests")
public class ContestController {

	@ApiOperation(value = "모든 콘테스트의 정보를 반환한다", response = Result.class)
	@GetMapping(value="/all")
	public ResponseEntity getContest() {
		
		
		return new ResponseEntity<Result>(new Result(StatusCode.CREATED, ResponseMessage.CREATED_USER, null), 
				HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "모든 콘테스트의 정보를 반환한다", response = Result.class)
	@GetMapping(value="/{cid}")
	public ResponseEntity getContestLatest() {
		
		
		return new ResponseEntity<Result>(new Result(StatusCode.CREATED, ResponseMessage.CREATED_USER, null), 
				HttpStatus.CREATED);
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
	@PostMapping(value="/feeds/")
	public ResponseEntity insertContestFeed(@PathVariable int fid, Authentication authentication) {
		User LoginUser = (User) authentication.getPrincipal();
		if(LoginUser==null) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), 
					HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<Result>(new Result(StatusCode.CREATED, ResponseMessage.CREATED_USER, null), 
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
		
		return new ResponseEntity<Result>(new Result(StatusCode.CREATED, ResponseMessage.CREATED_USER, null), 
				HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "fid번 피드 좋아요를 누른다.", response = Result.class)
	@PostMapping(value="/likes/{fid}")
	public ResponseEntity insertContestFeedLike(@PathVariable int fid, Authentication authentication) {
		User LoginUser = (User) authentication.getPrincipal();
		if(LoginUser==null) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), 
					HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<Result>(new Result(StatusCode.CREATED, ResponseMessage.CREATED_USER, null), 
				HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "fid번 피드 좋아요를 취소한다.", response = Result.class)
	@DeleteMapping(value="/likes/{fid}")
	public ResponseEntity deleteContestFeedLike(@PathVariable int fid, Authentication authentication) {
		User LoginUser = (User) authentication.getPrincipal();
		if(LoginUser==null) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), 
					HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<Result>(new Result(StatusCode.CREATED, ResponseMessage.CREATED_USER, null), 
				HttpStatus.CREATED);
	}
	
}
