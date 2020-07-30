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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.dto.Comment;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.service.CommentService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	 
	// 1. 댓글 list 조회
	@ApiOperation(value = "댓글 조회", response = Result.class)
	@GetMapping(value="/{fid}")
	public ResponseEntity<Result> commentList(@PathVariable int fid) {
		System.out.println("log - commentList");
		
		List<Comment> commentList = new ArrayList<Comment>();
		
		
		commentList = commentService.commentList(fid);

		// 
		
		Result result = new Result(StatusCode.OK, ResponseMessage.READ_ALL_COMMENTS, commentList);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	   // 2. 댓글 등록
	   @ApiOperation(value = "댓글 등록", response = Result.class)
	   @PostMapping(value="/")
	//   public ResponseEntity<Result> commentInsert(@RequestBody Comment comment, Authentication authentication) {
	   public ResponseEntity<Result> commentInsert(@RequestBody Comment comment, Authentication authentication) {
	      System.out.println("log - commentInsert");
	      
	      String uid = authentication.getName();
	      comment.setUid(Integer.parseInt(uid));
	      comment.setRegdate(new Date());
	      Comment insertedComment = commentService.commentInsert(comment); 
	      
	      Result result = new Result(StatusCode.OK, ResponseMessage.CREATE_COMMENT, insertedComment);
	      return new ResponseEntity<Result>(result, HttpStatus.OK);
	   }
	
	// 3. 댓글 수정
	@ApiOperation(value = "댓글 수정", response = Result.class)
	@PutMapping(value="/")
	public ResponseEntity<Result> commentUpdate(@RequestBody Comment comment) {
//		public ResponseEntity<Result> commentUpdate(@RequestBody Comment comment, Authentication authentication) {
		System.out.println("log - commentUpdate");
		
		Comment updatedComment = commentService.commentUpdate(comment);

		Result result = new Result(StatusCode.OK, ResponseMessage.UPDATE_COMMENT, updatedComment);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	// 4. 댓글 삭제
	@ApiOperation(value = "댓글 삭제", response = Result.class)
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Result> commentDelete(@PathVariable int id) {
//		public ResponseEntity<Result> commentDelete(@RequestBody Comment comment, Authentication authentication) {
		System.out.println("log - commentDelete");
		
		Long ret = commentService.commentDelete(id);

		Result result = new Result(StatusCode.OK, ResponseMessage.DELETE_COMMENT, ret);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
}
