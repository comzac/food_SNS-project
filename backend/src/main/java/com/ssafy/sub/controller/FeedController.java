package com.ssafy.sub.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sub.dto.Comment;
import com.ssafy.sub.dto.DBProfile;
import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.FeedAll;
import com.ssafy.sub.dto.Hashtag;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.dto.UserPage;
import com.ssafy.sub.dto.UserSimple;
import com.ssafy.sub.exception.FileStorageException;
import com.ssafy.sub.model.response.FeedAllResult;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.model.response.UserFeedResult;
import com.ssafy.sub.model.response.UserPageResult;
import com.ssafy.sub.service.CommentService;
import com.ssafy.sub.service.FeedService;
import com.ssafy.sub.service.FileStorageService;
import com.ssafy.sub.service.LikeService;
import com.ssafy.sub.service.RelationService;
import com.ssafy.sub.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/feeds")
public class FeedController {

	@Autowired
	private FeedService feedService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private RelationService relationService;

	// 1. list 조회
	@ApiOperation(value = "로그인한 유저의 홈 피드를 조회한다", response = Result.class)
	@GetMapping(value = "/page")
	public ResponseEntity feedHomePage(Authentication authentication) {
		System.out.println("log - feedUserHomePage");

		int uid = Integer.parseInt(authentication.getName());
		String loginUserId = authentication.getName();
		List<FeedAll> feedAllList = new ArrayList<FeedAll>();
		List<Feed> feedList = new ArrayList<Feed>();

		feedList = feedService.feedHomePageList();

		User user;
		UserSimple userSimple;
		Feed feed;
		FeedAll feedAll;
		int fid;
		for (int i = 0; i < feedList.size(); i++) {
			feedAll = new FeedAll();

			// feed 넣기
			feed = feedService.feedDetail(feedList.get(i).getId());
			feedAll.setFeed(feed);
			fid = feed.getId();

			// user이름 조회
			user = userService.findById(feed.getUid());
			userSimple = userService.getSimpleUser(user.getUid()); // user 탈퇴하면 어떻게 처리할건지
			feedAll.setUser(userSimple);

			// comment
			List<Comment> commentList = commentService.commentList(fid);
			feedAll.setComment(commentList);

			// hashtag
			List<Hashtag> hashtagList = feedService.findFeedHashtagList(fid);
			feedAll.setHashtag(hashtagList);

			// like
			boolean like = likeService.isFeedLiked(fid, uid);
			feedAll.setLike(like);

			// likeCount
			int likeCount = likeService.feedLikeUserList(fid).size();
			feedAll.setLikeCount(likeCount);

			// 내 피드인지 여부
			boolean mypage = true;
			if (feed.getUid() != Integer.parseInt(loginUserId)) {
				mypage = false;
			}
			feedAll.setMypage(mypage);

			feedAllList.add(feedAll);
		}

		FeedAllResult result = new FeedAllResult(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, feedAllList);
		return new ResponseEntity<FeedAllResult>(result, HttpStatus.OK);
	}

	// 7. list by follower 조회
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
	@ApiOperation(value = "팔로우한 유저들의 피드", response = Result.class)
	@PostMapping(value = "/page/follower")
	public ResponseEntity feedFollowerPage() {
		System.out.println("log - feedFollowerPage");

		String id;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		id = authentication.getName();

		List<Feed> feedList = feedService.findAllByFollower(Integer.parseInt(id)); // follower의 feedList 들고옴
		List<User> userList = new ArrayList<User>();

		User user; // 해당 user의 정보를 들고와서 userList에 넣어줌
		for (int i = 0; i < feedList.size(); i++) {
			user = userService.findById(feedList.get(i).getUid());
			userList.add(user);
		}

		UserFeedResult result = new UserFeedResult(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, feedList, userList,
				false);
		return new ResponseEntity<UserFeedResult>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "유저의 개인 피드 목록을 조회한다", response = UserFeedResult.class)
	@GetMapping(value = "/page/{uid}")
	public ResponseEntity feedUserPage(@PathVariable String uid, Authentication authentication) {
		System.out.println("log - feedUserPage");
		UserPage userPage = new UserPage(); // 넘길 객체

		// user정보
		UserSimple userSimple = userService.getSimpleUser(uid);
		System.out.println(userSimple.toString());

		String user_id = null;
		try {
			user_id = authentication.getName();
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null),
					HttpStatus.FORBIDDEN);
		}

		// url로 들어온 유저의 정보
		User user = userService.findByUid(uid);
		List<Feed> feedList = feedService.feedUserPageList(user.getId());
		for (Feed f : feedList) {
			f.setDbFiles(feedService.feedDetail(f.getId()).getDbFiles());
		}
		int feedCount = feedService.getFeedCount(user.getId());
		int followerCount = relationService.relationFollowerList(user.getId()).size();
		int followingCount = relationService.relationFollowingList(user.getId()).size();

		userPage.setUser(userSimple);
		userPage.setFeed(feedList);
		userPage.setFollowerCount(followerCount);
		userPage.setFollowingCount(followingCount);
		userPage.setFeedCount(feedCount);

		// 로그인한 유저인지 확인
		boolean mypage = true;
		if (Integer.parseInt(user_id) != user.getId()) {
			mypage = false;
		}

		UserPageResult result = new UserPageResult(StatusCode.OK, ResponseMessage.READ_USER_FEEDS, userPage, mypage);
		return new ResponseEntity<UserPageResult>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "유저의 개인 프로필을 수정한다", response = UserFeedResult.class)
	@PostMapping(value = "/page")
//   public ResponseEntity userPageUpdate(@RequestBody UserSimple userSimple, Authentication authentication) throws FileStorageException {
	public ResponseEntity userPageUpdate(@RequestParam(value = "img", required = false) MultipartFile img,
			@RequestParam("text") String text, @RequestParam("unick") String unick,
			@RequestParam("hasImage") boolean hasImage, Authentication authentication) throws FileStorageException {
		System.out.println("log - userPageUpdate");

		String id;
		id = authentication.getName();

//      hasImage: true
//      1. img가 들어오면 바꿔주기
//      2. img가 들어오지 않으면 냅두기
//      hasImage:false
//      1. db에 파일이 있으면: 삭제
//      2. db에 파일이 없으면: 냅두기

		User user = userService.updateNick(Integer.parseInt(id), unick);

		DBProfile dbProfile;
		if (hasImage) {
			if (img != null)
				dbProfile = fileStorageService.storeProfile(img, text, id);
			else
				dbProfile = fileStorageService.updateProfile(text, id, hasImage);
		} else {
			dbProfile = fileStorageService.updateProfile(text, id, hasImage);
		}

		UserSimple res = new UserSimple(user.getUid(), user.getUnick(), dbProfile);
		Result result = new Result(StatusCode.OK, ResponseMessage.UPDATE_USER, res);

		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	// 2. list 검색 ( )
	@ApiOperation(value = "feedList의 내용 중 일부를 검색한다", response = Feed.class)
	@GetMapping(value = "/search/{keyword}")
	public ResponseEntity<FeedAllResult> feedListSearch(Authentication authentication, @PathVariable String keyword) {
		System.out.println("log - feedListSearch");
		System.out.println(keyword);
		int uid = Integer.parseInt(authentication.getName());
		String loginUserId = authentication.getName();
		List<FeedAll> feedAllList = new ArrayList<FeedAll>(); 

		List<Feed> feedList = feedService.searchByHashtag(keyword);
//		switch(state) {
//		case "HASHTAG":
//			feedList = feedService.searchByHashtag(keyword);			
//			break;
//		case "USERID":
//			int findUid = userService.findByUid(keyword).getId();
//			feedList = feedService.searchByUserID(findUid);			
//			break;
//		}

		User user;
		UserSimple userSimple;
		Feed feed;
		FeedAll feedAll;
		int fid;
		for (int i = 0; i < feedList.size(); i++) {
			feedAll = new FeedAll();

			// feed 넣기
			feed = feedService.feedDetail(feedList.get(i).getId());
			feedAll.setFeed(feed);
			fid = feed.getId();

			// user이름 조회
			user = userService.findById(feed.getUid());
			userSimple = userService.getSimpleUser(user.getUid()); // user 탈퇴하면 어떻게 처리할건지
			feedAll.setUser(userSimple);

			// comment
			List<Comment> commentList = commentService.commentList(fid);
			feedAll.setComment(commentList);

			// hashtag
			List<Hashtag> hashtagList = feedService.findFeedHashtagList(fid);
			feedAll.setHashtag(hashtagList);

			// like
			boolean like = likeService.isFeedLiked(fid, uid);
			feedAll.setLike(like);

			// likeCount
			int likeCount = likeService.feedLikeUserList(fid).size();
			feedAll.setLikeCount(likeCount);

			// 내 피드인지 여부
			boolean mypage = true;
			if (feed.getUid() != Integer.parseInt(loginUserId)) {
				mypage = false;
			}
			feedAll.setMypage(mypage);

			feedAllList.add(feedAll);
		}
		
		FeedAllResult result = new FeedAllResult(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, feedAllList);
		return new ResponseEntity<FeedAllResult>(result, HttpStatus.OK);
	}
	
	
	// 2-1. list 검색 ( )
	@ApiOperation(value = "feedList의 내용 중 일부를 검색한다", response = Feed.class)
	@GetMapping(value = "/search/temp/{keyword}/{state}")
	public ResponseEntity<Result> feedTempSearch(Authentication authentication, @PathVariable String keyword, @PathVariable String state) {
		System.out.println(keyword);
		List<HashMap<String, String>> list =new ArrayList<HashMap<String,String>>();
		Result result = null;
		switch(state) {
		case "HASHTAG":
			List<Hashtag> hashtagList = feedService.findHashtagByKeyword(keyword);
			for (Hashtag hashtag : hashtagList) {
				System.out.println(hashtag.getContent());
				String hashContent = hashtag.getContent();
				Long cnt = feedService.countFeedByHashtag(hashtag.getId());
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(hashContent, Long.toString(cnt));
				list.add(map);
			}
			result = new Result(StatusCode.OK, ResponseMessage.READ_SEARCHED_HASHTAG, list);
			break;
		case "USERID":
			List<User> userList = userService.findUserIdByKeyword(keyword);
			for (User user : userList) {
				int user_id = user.getId();
				String user_uid = user.getUid();
				String user_nick = user.getUnick();
				System.out.println(user_id);
				Long cnt = feedService.countFeedByUser(user_id);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("uid", user_uid);
				map.put("cnt", Long.toString(cnt));
				map.put("unick", user_nick);
				list.add(map);
			}
			result = new Result(StatusCode.OK, ResponseMessage.READ_SEARCHED_USERS, list);
			break;
		}
		
		return new ResponseEntity<Result>(result, HttpStatus.CREATED);
	}
	
	
	// 3. list 추가
	@ApiOperation(value = "feedList에 정보를 추가한다", response = Result.class)
	@PostMapping
	public ResponseEntity<Result> feedInsert(@RequestBody FeedAll feedAll, Authentication authentication) {
		System.out.println("log - feedInsert");

		User user = (User) authentication.getPrincipal();

		// user는 token으로 Feed
		Feed feed = feedAll.getFeed();
		System.out.println(feed.toString());
		feed.setUid(user.getId());
		Feed insertedFeed = feedService.feedInsert(feed);
		int fid = insertedFeed.getId();

		// hashtag
		List<Hashtag> hashtagList = feedAll.getHashtag();
		feedService.feedHashtagListInsert(hashtagList);

		Result result = new Result(StatusCode.CREATED, ResponseMessage.CREATE_FEED, fid);
		return new ResponseEntity<Result>(result, HttpStatus.CREATED);
	}

	// 4. list 상세
	@ApiOperation(value = "특정 feed를 조회한다", response = Result.class)
	@GetMapping(value = "/{id}")
	public ResponseEntity feedDetail(@PathVariable int id, Authentication authentication) {
		System.out.println("log - feedDetail");

		String user_id = null;
		try {
			user_id = authentication.getName();
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null),
					HttpStatus.FORBIDDEN);
		}

		List<FeedAll> feedAllList = new ArrayList<FeedAll>();
		FeedAll feedAll = new FeedAll();

		// feed 정보
		Feed feed = feedService.feedDetail(id);
		feedAll.setFeed(feed);

		// user 정보
		User user = userService.findById(feed.getUid());
		UserSimple userSimple = userService.getSimpleUser(user.getUid());
		feedAll.setUser(userSimple);

		// hashtag 정보
		List<Hashtag> hashtag = feedService.findFeedHashtagList(id);
		feedAll.setHashtag(hashtag);

		// 내 피드인지 정보
		boolean mypage = true;
		if (Integer.parseInt(user_id) != feed.getUid()) {
			mypage = false;
		}
		feedAll.setMypage(mypage);

		// like
		boolean like = likeService.isFeedLiked(id, Integer.parseInt(user_id));
		feedAll.setLike(like);

		// likeCount
		int likeCount = likeService.feedLikeUserList(id).size();
		feedAll.setLikeCount(likeCount);

		// Comment 정보
		List<Comment> comment = commentService.commentList(id);
		feedAll.setComment(comment);

		feedAllList.add(feedAll);

		FeedAllResult result = new FeedAllResult(StatusCode.OK, ResponseMessage.READ_FEED, feedAllList);
		return new ResponseEntity<FeedAllResult>(result, HttpStatus.OK);
	}

	// 5. list 수정
	@ApiOperation(value = "feed의 정보를 수정한다", response = Result.class)
	@PutMapping(value = "/{id}")
	public ResponseEntity<Result> feedUpdate(@PathVariable int id, @RequestBody FeedAll feedAll,
			Authentication authentication) {
		System.out.println("log - feedUpdate");

		User user = (User) authentication.getPrincipal();

		// user는 token으로
		Feed feed = feedAll.getFeed();
		Feed updateFeed = feedService.feedUpdate(id, feed);

		// hashtag는 일단 빈칸
		List<Hashtag> hashtagList = feedAll.getHashtag();
		feedService.feedHashtagListUpdate(feed.getId(), hashtagList);

		FeedAll updateFeedAll = new FeedAll();
		updateFeedAll.setFeed(updateFeed);
		updateFeedAll.setUser(userService.getSimpleUser(user.getUid()));
		updateFeedAll.setHashtag(hashtagList);

		Result result = new Result(StatusCode.OK, ResponseMessage.UPDATE_FEED, updateFeedAll);
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
