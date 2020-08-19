package com.ssafy.sub.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.ssafy.sub.config.security.JwtTokenProvider;
import com.ssafy.sub.dto.Token;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.dto.UserSimple;
import com.ssafy.sub.exception.RestException;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.repo.UserRepository;
import com.ssafy.sub.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserSecurityController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserService userService;

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String CLIENT_ID;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@Autowired
	LogController logController;
	
	// 회원가입
	@ApiOperation(value = "회원가입. 성공 시 User정보, 실패 시 null 반환한다.", response = Result.class)
	@PostMapping("/join")
	public ResponseEntity<Result> join(@RequestBody Map<String, String> user, HttpServletResponse response) {
		System.out.println("Users - join");

		String s_ubirth = user.get("ubirth");
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		Date ubirth = null;
		try {
			ubirth = fm.parse(s_ubirth);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		User member = User.builder().uid(user.get("uid")).uemail(user.get("uemail")).unick(user.get("unick"))
				.upw(passwordEncoder.encode(user.get("upw"))).uregdate(new Date()).ubirth(ubirth)
				.usex(Integer.parseInt(user.get("usex")))
				.roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 // USER 로 설정
				.build();

		User joinMember = userService.join(member);

		// User 반환 정보
		UserSimple result = userService.getSimpleUser(joinMember.getUid());

		// JWT 생성
		String token = jwtTokenProvider.createToken(member, member.getRoles());
		response.addHeader("token", token);

		return new ResponseEntity<Result>(new Result(StatusCode.CREATED, ResponseMessage.CREATED_USER, result),
				HttpStatus.CREATED);
	}

	// 3개반 반환
	@ApiOperation(value = "user의 정보를 가져온다.", response = UserSimple.class)
	@GetMapping("/simple")
	public ResponseEntity getUserSimple(Authentication authentication) {
		User loginUser = (User) authentication.getPrincipal();
		
		UserSimple userSimple = userService.getSimpleUser(loginUser.getUid());

		return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.READ_USER, userSimple),
				HttpStatus.OK);
	}

	// 로그인
	@ApiOperation(value = "로그인 후 user정보를 반환한다.", response = Result.class)
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody Map<String, String> user, HttpServletResponse response) {
		System.out.println(user.toString());

		User member = userRepository.findByUid(user.get("uid")).orElseThrow(
				() -> new RestException(StatusCode.NOT_FOUND, ResponseMessage.LOGIN_FAIL_ID, HttpStatus.NOT_FOUND));

		if (!passwordEncoder.matches(user.get("upw"), member.getUpw())) {
			throw new RestException(StatusCode.NOT_FOUND, ResponseMessage.LOGIN_FAIL_PW, HttpStatus.NOT_FOUND);
		}

		// JWT 생성
		String token = jwtTokenProvider.createToken(member, member.getRoles());
		response.addHeader("token", token);

		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		Token jsonToken = new Token();
		jsonToken.setUsername(member.getUid());
		jsonToken.setRefreshToken(token);
		vop.set(member.getUid(), jsonToken);
		
		// User 반환 정보
		UserSimple result = userService.getSimpleUser(member.getUid());
		
		System.out.println("Redis 확인: " + redisTemplate.opsForValue().get(member.getUid()));
		return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, result),
				HttpStatus.OK);
	}

	// 소셜 로그인
	@ApiOperation(value = "구글 로그인 후 user정보를 반환한다.", response = Result.class)
	@PostMapping("/google")
	public ResponseEntity<Result> google(@RequestBody Map<String, String> googleToken, HttpServletResponse response) {

		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		HttpTransport httpTransport = null;
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, JSON_FACTORY)
				// Specify the CLIENT_ID of the app that accesses the backend:
				.setAudience(Collections.singletonList(CLIENT_ID))
				// Or, if multiple clients access the backend:
				// .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
				.build();
		System.out.println("google" + googleToken.get("id_token"));
		GoogleIdToken idToken = null;
		try {
			idToken = verifier.verify(googleToken.get("id_token"));
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User googleUser = null;
		Map<String, String> userInfos = new HashMap<String, String>();

		if (idToken != null) {
			Payload payload = idToken.getPayload();

			// Print user identifier
			String userId = payload.getSubject();
			System.out.println("User ID: " + userId);

			// Get profile information from payload
			String email = payload.getEmail();
			boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			String name = (String) payload.get("name");
			String pictureUrl = (String) payload.get("picture");
			// 프로필은 일단 스킵

			int random = new Random().nextInt(900000) + 100000;
			String upw = String.valueOf(random);
			String email_id = email.split("@")[0];
			String email_company = email.split("@")[1].split("\\.")[0];
			System.out.println(email_company);
			String uIdNick = email_id+"_"+email_company; //이메일 아이디+회사 조합으로 아이디와 닉네임 생성
			
			try {
				// 해당 uIdNick 유저 id로 있다면
				userService.findByUid(uIdNick);
				uIdNick=email;
			} catch(Exception e) {
				// 해당 uIdNick 유저 id로는 없는데 유저 nick으로 있다면
				if(userService.nickcheck(uIdNick)!=null) uIdNick=email;
			}
			
			googleUser = User.builder()
					.uemail(email)
					.uid(uIdNick)	
					.upw(passwordEncoder.encode(upw))
					.unick(uIdNick)
					.uregdate(new Date()).roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 User로 설정
					.build();
			
			if (userService.findByUemail(email) == null) { // 가입정보가 없으면,
				googleUser = userRepository.save(googleUser);
			} else { // 가입정보가 있다면,
				googleUser = userService.findByUemail(email);
			}
			userInfos.put("uid", googleUser.getUid());
			userInfos.put("uemail", googleUser.getUemail());
			userInfos.put("unick", googleUser.getUnick());
		} else {
			System.out.println("Invalid ID token.");
			Result result = new Result(StatusCode.OK, ResponseMessage.SOCIAL_LOGIN_FAIL, userInfos);
			return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.SOCIAL_LOGIN_FAIL, result),
					HttpStatus.OK);
		}

		// JWT 생성
		String token = jwtTokenProvider.createToken(googleUser, googleUser.getRoles());
		response.addHeader("token", token);
		response.addHeader("access_token", googleToken.get("access_token"));
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		Token jsonToken = new Token();
		jsonToken.setUsername(googleUser.getUid());
		jsonToken.setRefreshToken(token);
		vop.set(googleUser.getUid(), jsonToken);

		System.out.println(token);
		System.out.println("Redis 확인: " + redisTemplate.opsForValue().get(googleUser.getUid()));
		Result result = new Result(StatusCode.OK, ResponseMessage.SOCIAL_LOGIN_SUCCESS, userInfos);
		return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.SOCIAL_LOGIN_SUCCESS, result),
				HttpStatus.OK);
	}

	// 로그아웃
	@ApiOperation(value = "로그아웃.", response = Result.class)
	@GetMapping("/logout")
	public ResponseEntity<Result> logout(HttpServletRequest request) {
		System.out.println("logout");

		String username = null;
		String token = jwtTokenProvider.resolveToken(request);
		if (jwtTokenProvider.validateToken(token)) {
			username = jwtTokenProvider.getUName(token);

			Date expirationDate = jwtTokenProvider.getExpirationDate(token);
			System.out.println("Redis 확인: " + redisTemplate.opsForValue().get(username));
			redisTemplate.delete(username);
			System.out.println("Redis 확인: " + redisTemplate.opsForValue().get(username));

			String accessToken = token;
			redisTemplate.opsForValue().set(accessToken, new Token(accessToken, null));
			redisTemplate.expire(accessToken, 10 * 6 * 1000, TimeUnit.MILLISECONDS);
			System.out.println("Redis 확인: " + redisTemplate.opsForValue().get(token));

			return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.LOGOUT_SUCCESS, null),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.LOGOUT_FAIL, null),
					HttpStatus.FORBIDDEN);
		}

	}

	// 아이디 중복체크
	@ApiOperation(value = "아이디 중복 체크. 이미 있는 아이디인 경우 fail을 반환한다.", response = String.class)
	@GetMapping("/uid-dup/{uid}")
	public ResponseEntity<String> idCheck(@PathVariable String uid) {
		System.out.println(uid);

		User member = userService.idCheck(uid);

		if (member.getUid() == null)
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);

	}

	// 이메일 인증
	@ApiOperation(value = "인증 코드를 사용자의 이메일로 보내기. 메일이 전송되면 인증 코드를 반환한다.", response = String.class)
	@PostMapping(value = "/e-valid")
	public ResponseEntity<String> createEmailCheck(@RequestBody HashMap<String, String> userEmailData) {
		System.out.println(userEmailData.get("userEmail"));
		String userEmail = userEmailData.get("userEmail");
		// 이메일 인증
		int random = new Random().nextInt(900000) + 100000;
		String authCode = String.valueOf(random);

		String subject = "회원가입 인증 코드 발급 안내 입니다.";
		StringBuilder sb = new StringBuilder();
		sb.append("귀하의 인증 코드는 " + authCode + "입니다.");

		if (userService.send(subject, sb.toString(), userEmail, null)) {
			return new ResponseEntity<String>(authCode, HttpStatus.OK); // vue에서 authCode로 일치 여부 확인 후 계정생성 버튼 활성화
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "닉네임 중복 체크. 이미 있는 닉네임인 경우 fail을 반환한다.", response = String.class)
	@GetMapping(value = "/unick-dup/{unick}")
	public ResponseEntity<String> nickcheck(@PathVariable String unick) {

		System.out.println("log - nickchk");
		User member = userService.nickcheck(unick);

		if (member == null) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "이메일 중복 체크. 이미 있는 이메일인 경우 fail을 반환한다.", response = String.class)
	@PostMapping(value = "/e-dup")
	public ResponseEntity<String> edcheck(@RequestBody HashMap<String, String> userEmailData) {
		System.out.println("log - edcheck");
		String uemail = userEmailData.get("userEmail");
		User memeber = userService.edcheck(uemail);

		if (memeber == null) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "재설정하기 위해 현재 비밀번호 확인. 성공 시 success, 실패시 fail을 반환한다.", response = String.class)
	@PostMapping(value = "/pw-dup")
	public ResponseEntity<String> pwcheck(@RequestBody HashMap<String, String> userData, HttpServletRequest request) {
		System.out.println("log - pwcheck");

		String username = null;
		String token = jwtTokenProvider.resolveToken(request);
		System.out.println("token: " + token);
		if (jwtTokenProvider.validateToken(token)) {
			username = jwtTokenProvider.getUserPk(token);
		}
		System.out.println("username: " + username);
		// User member = userService.findById(Integer.parseInt(id));
		User member = userService.findById(Integer.parseInt(username));
		System.out.println(member.toString());

		if (!passwordEncoder.matches(userData.get("upw"), member.getUpw())) {
			return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "유저 아이디를 이용해 비밀번호 변경. 성공 시 success, 실패시 fail을 반환한다.", response = String.class)
	@PutMapping(value = "/pw-reset")
	public ResponseEntity<String> pwreset(@RequestBody HashMap<String, String> userData) {
		System.out.println("log - pwreset");

		int ret = userService.pwreset(passwordEncoder.encode(userData.get("upw")), userData.get("uid"));

		if (ret == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "회원 아이디 찾기", notes = "유저 이메일로 아이디를 찾는다")
	@PostMapping(value = "/uid-find")
	public ResponseEntity deleteUser(@RequestBody HashMap<String, String> userData) {
		System.out.println("log - uid find");

		User user = userService.findByUemail(userData.get("uemail"));

		if (user != null) {
			return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.READ_USER, user.getUid()),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<Result>(new Result(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null),
					HttpStatus.NOT_FOUND);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
	@GetMapping(value = "/")
	public ResponseEntity findAllUser() {
		// 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
		return new ResponseEntity<Result>(
				new Result(StatusCode.CREATED, ResponseMessage.READ_ALL_USERS, userRepository.findAll()),
				HttpStatus.CREATED);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원  조회", notes = "회원아이디로 회원을 조회한다")
	@GetMapping(value = "/{uid}")
	public ResponseEntity findUserById(@PathVariable String uid) {
		User user = userService.findByUid(uid);

		return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.READ_USER, user), HttpStatus.OK);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
	@PutMapping(value = "/{uid}")
	public ResponseEntity modify(@PathVariable String uid, @RequestBody HashMap<String, String> userUpdate,
			Authentication authentication) {
		// user정보
		User user = (User) authentication.getPrincipal();
		if (!user.getUid().equals(uid)) {
			// 토큰 정보와 일치하는지 확인
			return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.UNAUTHORIZED, null),
					HttpStatus.FORBIDDEN);
		}
		
		// 유저의 생일
		try {
			if(userUpdate.get("ubirth")!=null) {
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
				transFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
				Date ubirth = transFormat.parse(userUpdate.get("ubirth"));
				user.setUbirth(ubirth);
			}
		} catch (ParseException e) {
			throw new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_BIRTH);
		}
		
		// 유저의 성별
		if(userUpdate.get("usex")!=null) {
			user.setUsex(Integer.parseInt(userUpdate.get("usex")));
		}
		
		System.out.println(userUpdate.toString());
		userService.userUpdate(user);
		System.out.println(user.toString());
		return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.UPDATE_USER, user), HttpStatus.OK);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 탈퇴", notes = "유저 아이디로 회원정보를 삭제한다")
	@DeleteMapping(value = "/{uid}")
	public ResponseEntity deleteUser(@PathVariable String uid) {
		// 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
		int ref = userService.userDelete(uid);
		if (ref == 0) {
			return new ResponseEntity<Result>(new Result(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.DELETE_USER, null),
					HttpStatus.OK);
		}
	}

}