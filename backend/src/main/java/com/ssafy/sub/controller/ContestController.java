package com.ssafy.sub.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ssafy.sub.dto.Contest;
import com.ssafy.sub.dto.ContestFeed;
import com.ssafy.sub.dto.ContestFeedFiles;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.exception.FileStorageException;
import com.ssafy.sub.model.response.ContestFeedAll;
import com.ssafy.sub.model.response.FeedAllResult;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.model.response.UploadFileResponse;
import com.ssafy.sub.service.ContestService;
import com.ssafy.sub.service.FileStorageService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/contests")
public class ContestController {
	
	@Autowired
	ContestService contestService;
	@Autowired
	FileStorageService fileStorageService;
	
	@ApiOperation(value = "모든 콘테스트의 정보를 반환한다", response = Result.class)
	@GetMapping(value="/all")
	public ResponseEntity getContest() {
		
		List<Contest> contestList = contestService.getContest();
		
		return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.READ_ALL_CONTEST, contestList), 
				HttpStatus.OK);
	}
	
	@ApiOperation(value = "최근 콘테스트의 정보를 반환한다", response = Result.class)
	@GetMapping(value="/")
	public ResponseEntity getContestLatest(Authentication authentication) {
		System.out.println("latest contest list");
		
		User LoginUser = (User) authentication.getPrincipal();
		if(LoginUser==null) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), 
					HttpStatus.FORBIDDEN);
		}
		
		List<ContestFeedAll> contestFeedAll = new ArrayList<ContestFeedAll>();
		
		int latestRound = contestService.getContestlatestRound();
		List<ContestFeed> contestFeeds = contestService.findByCidOrderByLike(latestRound);
		boolean islike = false;
		for(ContestFeed cFeed: contestFeeds) {
			islike = contestService.findByContestFeedLike(cFeed.getId(), LoginUser.getId());
			contestFeedAll.add(ContestFeedAll.builder().contestFeed(cFeed).islike(islike).build());
		}
		
		return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, contestFeedAll), 
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
		
		List<ContestFeedAll> contestFeedAll = new ArrayList<ContestFeedAll>();
		List<ContestFeed> contestFeeds = contestService.findByCidOrderByLike(cid);
		boolean islike = false;
		for(ContestFeed cFeed: contestFeeds) {
			islike = contestService.findByContestFeedLike(cFeed.getId(), LoginUser.getId());
			contestFeedAll.add(ContestFeedAll.builder().contestFeed(cFeed).islike(islike).build());
		}
		
		return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, contestFeedAll), 
				HttpStatus.OK);
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
		ContestFeed cFeed = contestService.insertContestFeed(contestFeed);
		
		return new ResponseEntity<Result>(new Result(StatusCode.CREATED, ResponseMessage.CREATE_FEED, cFeed.getId()), 
				HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "콘테스트 피드의 파일을 저장한다.", response = Result.class)
	@PostMapping("/feeds/files")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam int fid) {

		return Arrays.asList(files).stream().map(file -> {
			try {
				return uploadFile(file, fid);
			} catch (FileStorageException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}

	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fid") int fid)
			throws FileStorageException {
		ContestFeedFiles cfFiles = fileStorageService.storeContestFile(file, fid);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(Integer.toString(cfFiles.getId())).toUriString();

		return new UploadFileResponse(cfFiles.getName(), fileDownloadUri, file.getContentType(), file.getSize());
	}
	
	@ApiOperation(value = "fid번 피드를 조회한다.", response = Result.class)
	@GetMapping(value="/feeds/{fid}")
	public ResponseEntity getContestFeed(@PathVariable int fid, Authentication authentication) {
		User LoginUser = (User) authentication.getPrincipal();
		if(LoginUser==null) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null), 
					HttpStatus.FORBIDDEN);
		}
		
		Optional<ContestFeed> contestFeed = contestService.getContestFeed(fid);
		if(!contestFeed.isPresent()) {
			return new ResponseEntity<Result>(new Result(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_FEED, null), 
					HttpStatus.NOT_FOUND);
		}
		
		HashMap<String, Object> statistics = contestService.getContestFeedLike(fid);
		
		boolean islike = false;
		islike = contestService.findByContestFeedLike(fid, LoginUser.getId());
		
		return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.READ_FEED, 
				ContestFeedAll.builder().contestFeed(contestFeed.get()).statistics(statistics).islike(islike).build()), 
				HttpStatus.OK);
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
		if(contestService.insertContestFeedLike(fid, LoginUser.getId())!=null) {
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
		
		contestService.deleteContestFeedLike(fid, LoginUser.getId());
		contestService.updateFeedLikeCount(fid, -1);
		return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.UNLIKE_FEED, null), 
				HttpStatus.OK);
	}
	
}