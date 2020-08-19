package com.ssafy.sub.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sub.dto.Comment;
import com.ssafy.sub.dto.DBProfile;
import com.ssafy.sub.dto.Feed;
import com.ssafy.sub.dto.FeedAll;
import com.ssafy.sub.dto.Hashtag;
import com.ssafy.sub.dto.Relationship;
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
import com.ssafy.sub.service.NotificationService;
import com.ssafy.sub.service.RelationService;
import com.ssafy.sub.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/***
 * 
 * @author 이선수
 * @version 1.0 검색 임시 리스트 (해쉬태그, 유저 닉네임) 통합
 */

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
	@Autowired
	private NotificationService notificationService;

	@Autowired
	LogController logController;

	/***
	 * 페이지네이션 기능을 적용한 홈 피드 리스트 조회
	 * @param lastFid - 조회된 마지막 피드의 pk
	 * @param lastFidRecommand - 조회된 추천피드 마지막 피드의 pk
	 * @param authentication - 로그인한 유저의 권한 정보
	 * @return List<FeedAll>
	 */
	@ApiOperation(value = "로그인한 유저의 홈 피드를 조회한다", response = Result.class)
	@GetMapping(value = "/pages")
	public ResponseEntity feedHomePage(
			@RequestParam(value = "lastFid", required = false) int lastFid, 
			@RequestParam(value = "lastFidRecommand", required = false) int lastFidRecommand,
			Authentication authentication) {
		System.out.println("log - feedUserHomePage");
		
		User loginUser = (User) authentication.getPrincipal();

		List<FeedAll> feedAllList = new ArrayList<FeedAll>();
		List<Feed> feedList = new ArrayList<Feed>();

		int feedLimit = 5;
		feedList.addAll(feedService.feedPagination(0L, lastFid * 1L, feedLimit));
		
		System.out.println("lastfeed id: "+lastFid+" "+lastFidRecommand);
		List<Feed> recommandFeedList = new ArrayList<Feed>();
		recommandFeedList = feedService.getRecommandFeed(
				loginUser.getId(), 
				userService.getUserAge(loginUser.getUbirth()), 
				loginUser.getUsex());

		Feed recommandFeed = new Feed();
		if(!recommandFeedList.isEmpty()) {
			int random = (int) (Math.random()*recommandFeedList.size());	//모든 추천피드 중 무작위로
			if(feedList.size()==feedLimit) {	//feedList가 5개일때만 추천피드 가도록 바꿈
				recommandFeed = recommandFeedList.get(random);
				if(recommandFeed.getId()==lastFidRecommand || recommandFeed.getId()==0) {
					recommandFeed=null;
				}else {
					feedList.add(recommandFeed);
				}
			}
		}

		User user;
		UserSimple userSimple;
		Feed feed;
		FeedAll feedAll;
		int fid;
		for (int i = 0; i < feedList.size(); i++) {
			feedAll = new FeedAll();

			// feed 넣기
			feed = feedList.get(i);
			feedAll.setFeed(feed);
			fid = feed.getId();

			// user이름 조회
			user = userService.findById(feed.getUid());
			userSimple = userService.getSimpleUser(user.getUid()); // user 탈퇴하면 어떻게 처리할건지
			feedAll.setUser(userSimple);

			// commentCount
			Long commentCount = 0L;
			commentCount = commentService.commentCount(fid);
			feedAll.setCommentCount(commentCount);

			// comment
			int limit = 2; // 2개만 불러오기
			List<Comment> commentList = commentService.commentListLimit(fid, limit);
			for (Comment c : commentList) {
				String c_uid = userService.findById(c.getUid()).getUid();
				c.setUser(userService.getSimpleUser(c_uid));
			}
			feedAll.setComment(commentList);

			// hashtag
			List<Hashtag> hashtagList = feedService.findFeedHashtagList(fid);
			feedAll.setHashtag(hashtagList);

			// like
			boolean like = likeService.isFeedLiked(fid, loginUser.getId());
			feedAll.setLike(like);

			// likeCount
			int likeCount = likeService.feedLikeUserList(fid).size();
			feedAll.setLikeCount(likeCount);

			// 내 피드인지 여부
			boolean mypage = true;
			if (feed.getUid() != loginUser.getId()) {
				mypage = false;
			}
			feedAll.setMypage(mypage);
			
			// 추천 피드인지 여부
			boolean recommand = false;
			if(recommandFeed!=null && i==feedLimit) {
				recommand = true;
			}
			feedAll.setRecommand(recommand);

			feedAllList.add(feedAll);
		}

		FeedAllResult result = new FeedAllResult(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, feedAllList);
		return new ResponseEntity<FeedAllResult>(result, HttpStatus.OK);
	}
	
	/***
	 * 로그인한 유저의 팔로우 유저의 피드 조회
	 * 
	 * @param lastFid        - 조회된 마지막 피드의 pk
	 * @param authentication - 로그인한 유저의 권한 정보
	 * @return List<FeedAll>
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
	@ApiOperation(value = "팔로우한 유저들의 피드", response = Result.class)
	@GetMapping(value = "/pages/follower")
	public ResponseEntity feedFollowerPage(
			@RequestParam(value = "lastFid", required = false) int lastFid, 
			@RequestParam(value = "lastFidRecommand", required = false) int lastFidRecommand, 
			Authentication authentication) {
		System.out.println("log - feedFollowerPage");

		User loginUser = (User) authentication.getPrincipal();

		List<FeedAll> feedAllList = new ArrayList<FeedAll>();
		List<Feed> feedList = new ArrayList<Feed>();

		int feedLimit = 5;
		feedList.addAll(feedService.feedFollowPagination(loginUser.getId(), 0L, lastFid * 1L, feedLimit));	// follower의 feedList 들고옴
		
		System.out.println("lastfeed id: "+lastFid+" "+lastFidRecommand);
		List<Feed> recommandFeedList = new ArrayList<Feed>();
		recommandFeedList = feedService.getRecommandFeed(
				loginUser.getId(), 
				userService.getUserAge(loginUser.getUbirth()), 
				loginUser.getUsex());

		Feed recommandFeed = new Feed();
		if(!recommandFeedList.isEmpty()) {
			int random = (int) (Math.random()*recommandFeedList.size());
			if(feedList.size()==feedLimit) {	//feed list가 5개일때
				recommandFeed = recommandFeedList.get(random);
				if(recommandFeed.getId()==lastFidRecommand || recommandFeed.getId()==0) {
					recommandFeed=null;
				}else {
					feedList.add(recommandFeed);
				}
			}
		}
		
		User user;
		UserSimple userSimple;
		Feed feed;
		FeedAll feedAll;
		int fid;
		for (int i = 0; i < feedList.size(); i++) {
			feedAll = new FeedAll();

			// feed 넣기
			feed = feedList.get(i);
			feedAll.setFeed(feed);
			fid = feed.getId();

			// user이름 조회
			user = userService.findById(feed.getUid());
			userSimple = userService.getSimpleUser(user.getUid()); // user 탈퇴하면 어떻게 처리할건지
			feedAll.setUser(userSimple);

			// commentCount
			Long commentCount = 0L;
			commentCount = commentService.commentCount(fid);
			feedAll.setCommentCount(commentCount);

			// comment
			int commentLimit = 2; // 2개만 불러오기
			List<Comment> commentList = commentService.commentListLimit(fid, commentLimit);
			for (Comment c : commentList) {
				String c_uid = userService.findById(c.getUid()).getUid();
				c.setUser(userService.getSimpleUser(c_uid));
			}
			feedAll.setComment(commentList);

			// hashtag
			List<Hashtag> hashtagList = feedService.findFeedHashtagList(fid);
			feedAll.setHashtag(hashtagList);

			// like
			boolean like = likeService.isFeedLiked(fid, loginUser.getId());
			feedAll.setLike(like);

			// likeCount
			int likeCount = likeService.feedLikeUserList(fid).size();
			feedAll.setLikeCount(likeCount);

			// 내 피드인지 여부
			boolean mypage = true;
			if (feed.getUid() != loginUser.getId()) {
				mypage = false;
			}
			feedAll.setMypage(mypage);
			
			// 추천 피드인지 여부
			boolean recommand = false;
			if(recommandFeed!=null && i==feedLimit) {
				recommand = true;
			}
			feedAll.setRecommand(recommand);

			feedAllList.add(feedAll);
		}

		FeedAllResult result = new FeedAllResult(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, feedAllList);
		return new ResponseEntity<FeedAllResult>(result, HttpStatus.OK);
	}

	/***
	 * 로그인한 유저의 개인 피드 조회
	 * 
	 * @param uid            - 유저 id
	 * @param authentication - 로그인한 유저의 권한 정보
	 * @return UserPageResult
	 */
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

		//
		boolean isFollow = false;
		boolean isBlock = false;
		Relationship followRS = relationService.followCheck(Integer.parseInt(user_id), user.getId()); // 로긴한 유저가 해당 피드유저
																										// follow?
		Relationship blockRS = relationService.followCheck(user.getId(), Integer.parseInt(user_id)); // 해당 피드유저가 로긴한 유저
																										// block?
		if (followRS != null && followRS.getState() == 0)
			isFollow = true;
		if (blockRS != null && blockRS.getState() == 1)
			isBlock = true;

		UserPageResult result = new UserPageResult(StatusCode.OK, ResponseMessage.READ_USER_FEEDS, userPage, mypage,
				isFollow, isBlock);
		return new ResponseEntity<UserPageResult>(result, HttpStatus.OK);
	}

	/***
	 * 유저의 프로필 변경
	 * 
	 * @param img            - 프로필 사진
	 * @param text           - 프로필 문구
	 * @param unick          - 유저 닉네임
	 * @param hasImage       - 변경할 프로필 사진 유무 hasImage : true 1. img 변경 2. img 유지
	 *                       hasImage:false 1. db 내 data가 있는 경우, img 삭제 2. db 내
	 *                       data가 없는 경우, 유지
	 * @param authentication - 로그인한 유저의 권한 정보
	 * @return UserSimple
	 * @throws FileStorageException
	 * @throws IOException
	 */
	@ApiOperation(value = "유저의 개인 프로필을 수정한다", response = UserFeedResult.class)
	@PostMapping(value = "/page")
	public ResponseEntity userPageUpdate(@RequestParam(value = "img", required = false) MultipartFile img,
			@RequestParam("coordi") String[] coordi, @RequestParam("text") String text,
			@RequestParam("unick") String unick, @RequestParam("hasImage") boolean hasImage,
			Authentication authentication) throws FileStorageException, IOException {
		System.out.println("log - userPageUpdate");

		String id;
		id = authentication.getName();

		User user = userService.updateNick(Integer.parseInt(id), unick);

		DBProfile dbProfile;
		if (hasImage) {
			if (img != null)
				dbProfile = fileStorageService.storeProfile(img, text, id, coordi[0]);
			else
				dbProfile = fileStorageService.updateProfile(text, id, hasImage);
		} else {
			dbProfile = fileStorageService.updateProfile(text, id, hasImage);
		}

		UserSimple res = UserSimple.builder().id(user.getId()).uid(user.getUid()).unick(user.getUnick())
				.uprofile(dbProfile).ubirth(user.getUbirth()).usex(user.getUsex()).build();
		Result result = new Result(StatusCode.OK, ResponseMessage.UPDATE_USER, res);

		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	/***
	 * 검색 해쉬태그가 포함된 피드 리스트 정보 조회
	 * 
	 * @param authentication - 로그인한 유저의 권한 정보
	 * @param keyword        - 검색한 해쉬태그
	 * @return FeedAllResult
	 */
	@ApiOperation(value = "해쉬태그 기반 feedList를 검색한다", response = Feed.class)
	@GetMapping(value = "/search/{keyword}")
	public ResponseEntity feedListSearch(Authentication authentication, @PathVariable String keyword) {
		System.out.println("log - feedListSearch");

		User loginUser = (User) authentication.getPrincipal();

		// for log
		String action = "search"; // for log action
		List<Hashtag> hashtagListLog = new ArrayList<Hashtag>();
		hashtagListLog.add(feedService.findByContent(keyword));
		logController.setString(loginUser, action, hashtagListLog);

		List<FeedAll> feedAllList = new ArrayList<FeedAll>();
		List<Feed> feedList = feedService.searchByHashtag(keyword);
		
		User user;
		UserSimple userSimple;
		Feed feed;
		FeedAll feedAll;
		int fid;
		for (int i = 0; i < feedList.size(); i++) {
			feedAll = new FeedAll();

			// feed 넣기
			feed = feedList.get(i);
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
			boolean like = likeService.isFeedLiked(fid, loginUser.getId());
			feedAll.setLike(like);

			// likeCount
			int likeCount = likeService.feedLikeUserList(fid).size();
			feedAll.setLikeCount(likeCount);

			// 내 피드인지 여부
			boolean mypage = true;
			if (feed.getUid() != loginUser.getId()) {
				mypage = false;
			}
			feedAll.setMypage(mypage);

			feedAllList.add(feedAll);
		}

		FeedAllResult result = new FeedAllResult(StatusCode.OK, ResponseMessage.READ_ALL_FEEDS, feedAllList);
		return new ResponseEntity<FeedAllResult>(result, HttpStatus.OK);
	}

	/***
	 * 검색 과정 중 현재 키워드에 맞는 목록 조회
	 * 
	 * @param authentication - 로그인한 유저의 권한 정보
	 * @param keyword        - 현재 검색창에 입력한 키워드
	 * @return HashMap<String, List<HashMap<String, String>>>
	 */
	@ApiOperation(value = "feedList의 목록을 검색한다", response = Feed.class)
	@GetMapping(value = "/search/temp/{keyword}")
	public ResponseEntity<Result> feedTempSearch(Authentication authentication, @PathVariable String keyword) {
		System.out.println("log - feedTempSearch");

		User loginUser = (User) authentication.getPrincipal();

		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, List<HashMap<String, String>>> totalList = new HashMap<String, List<HashMap<String, String>>>();
		Result result = null;

		List<Hashtag> hashtagList = feedService.findHashtagByKeyword(keyword);

		for (Hashtag hashtag : hashtagList) {
			System.out.println(hashtag.getContent());
			String hashContent = hashtag.getContent();
			Long cnt = feedService.countFeedByHashtag(hashtag.getId());
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(hashContent, Long.toString(cnt));
			list.add(map);
		}

		totalList.put("hashtag", list);

		list = new ArrayList<HashMap<String, String>>();
		List<User> userList = userService.findUserNickByKeyword(keyword);
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

		totalList.put("unick", list);

		result = new Result(StatusCode.OK, ResponseMessage.READ_SEARCHED_USERS, totalList);

		return new ResponseEntity<Result>(result, HttpStatus.CREATED);
	}

	/***
	 * 새로운 피드 작성
	 * 
	 * @param feedAll        - 새로운 피드의 세부 정보
	 * @param authentication - 로그인한 유저의 권한 정보
	 * @return int (새로 생성한 피드의 fid pk)
	 */
	@ApiOperation(value = "feedList에 정보를 추가한다", response = Result.class)
	@PostMapping
	public ResponseEntity<Result> feedInsert(@RequestBody FeedAll feedAll, Authentication authentication) {
		System.out.println("log - feedInsert");

		User loginUser = (User) authentication.getPrincipal();

		// user는 token으로 Feed
		Feed feed = feedAll.getFeed();
		System.out.println(feed.toString());
		feed.setUid(loginUser.getId());
		Feed insertedFeed = feedService.feedInsert(feed);
		int fid = insertedFeed.getId();

		// hashtag
		List<Hashtag> hashtagList = feedAll.getHashtag();
		feedService.feedHashtagListInsert(hashtagList, fid);

		// for log
		String action = "insert"; // for log action
		List<Hashtag> hashtagListLog = feedService.findFeedHashtagList(fid);
		logController.setString(loginUser, action, hashtagListLog);

		Result result = new Result(StatusCode.CREATED, ResponseMessage.CREATE_FEED, fid);
		return new ResponseEntity<Result>(result, HttpStatus.CREATED);
	}

	/***
	 * 피드의 상세 정보 조회
	 * 
	 * @param id             - 피드 pk
	 * @param authentication - 로그인한 유저의 권한 정보
	 * @return List<FeedAll> ############ 이 부분은 단일인데 리스트로 해둔건가?
	 */
	@ApiOperation(value = "특정 feed를 조회한다", response = Result.class)
	@GetMapping(value = "/{id}")
	public ResponseEntity feedDetail(@PathVariable int id, Authentication authentication) {
		System.out.println("log - feedDetail");

		User loginUser = new User();
		try {
			loginUser = (User) authentication.getPrincipal();
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
		List<Hashtag> hashtagList = feedService.findFeedHashtagList(id);
		feedAll.setHashtag(hashtagList);

		// 내 피드인지 정보
		boolean mypage = true;
		if (loginUser.getId() != feed.getUid()) {
			mypage = false;
		}
		feedAll.setMypage(mypage);

		// like
		boolean like = likeService.isFeedLiked(id, loginUser.getId());
		feedAll.setLike(like);

		// likeCount
		int likeCount = likeService.feedLikeUserList(id).size();
		feedAll.setLikeCount(likeCount);

		// commentCount
		Long commentCount = 0L;
		commentCount = commentService.commentCount(id);
		feedAll.setCommentCount(commentCount);

		// Comment 정보
		int limit = 2; // 2개만 불러오기
		List<Comment> commentList = commentService.commentListLimit(id, limit);
		for (Comment c : commentList) {
			String c_uid = userService.findById(c.getUid()).getUid();
			c.setUser(userService.getSimpleUser(c_uid));
		}
		feedAll.setComment(commentList);

		feedAllList.add(feedAll);

		// for log
		String action = "revisit"; // for log action
		logController.setString(loginUser, action, hashtagList);

		FeedAllResult result = new FeedAllResult(StatusCode.OK, ResponseMessage.READ_FEED, feedAllList);
		return new ResponseEntity<FeedAllResult>(result, HttpStatus.OK);
	}

	/***
	 * 피드 정보 수정
	 * 
	 * @param id             - 피드 pk
	 * @param feedAll        - 수정된 피드의 세부 정보
	 * @param authentication - 로그인한 유저의 권한 정보
	 * @return
	 */
	@ApiOperation(value = "feed의 정보를 수정한다", response = Result.class)
	@PutMapping(value = "/{id}")
	public ResponseEntity feedUpdate(@PathVariable int id, @RequestBody FeedAll feedAll,
			Authentication authentication) {
		System.out.println("log - feedUpdate");

		User user = (User) authentication.getPrincipal();

		// user는 token으로
		Feed feed = feedAll.getFeed();
		Feed updateFeed = feedService.feedUpdate(id, feed);

		// hashtag는 일단 빈칸
		List<Hashtag> hashtagList = feedAll.getHashtag();
		feedService.feedHashtagListUpdate(id, hashtagList);
//		System.out.println(id+" "+feed.getId()+" "+hashtagList.toString());

		FeedAll updateFeedAll = new FeedAll();
		updateFeedAll.setFeed(updateFeed);
		updateFeedAll.setUser(userService.getSimpleUser(user.getUid()));
		updateFeedAll.setHashtag(hashtagList);

		Result result = new Result(StatusCode.OK, ResponseMessage.UPDATE_FEED, updateFeedAll);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	/***
	 * 피드 삭제
	 * 
	 * @param id - 피드 pk
	 * @return null
	 */
	@ApiOperation(value = "feed의 정보를 삭제한다", response = Result.class)
	@DeleteMapping(value = "/{id}")
	public ResponseEntity feedDelete(@PathVariable int id) {
		System.out.println("log - feedDelete");

		feedService.feedDelete(id);
		notificationService.feedDelete(id);

		Result result = new Result(StatusCode.OK, ResponseMessage.DELETE_FEED, null);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

}
