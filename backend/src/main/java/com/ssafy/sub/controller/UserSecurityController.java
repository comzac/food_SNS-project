
package com.ssafy.sub.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.ssafy.sub.config.security.JwtTokenProvider;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.dto.UserDto;
import com.ssafy.sub.exception.CUserNotFoundException;
import com.ssafy.sub.model.response.CommonResult;
import com.ssafy.sub.model.response.ListResult;
import com.ssafy.sub.model.response.SingleResult;
import com.ssafy.sub.repo.UserRepository;
import com.ssafy.sub.service.ResponseService;
import com.ssafy.sub.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
	private final ResponseService responseService;
	private final UserService userService;

	// 회원가입
	@ApiOperation(value = "회원가입. 성공 시 User정보, 실패 시 null 반환한다.", response = UserDto.class)
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody Map<String, String> user) {

		User member = User.builder().uid(user.get("uid")).uemail(user.get("uemail")).unick(user.get("unick"))
				.upw(passwordEncoder.encode(user.get("upw"))).roles(Collections.singletonList("ROLE_USER")) // 최초 가입시
																											// USER 로 설정
				.build();

		String uid = userService.join(member).getUid();

		return new ResponseEntity<String>(uid, HttpStatus.CREATED);
	}

	// 로그인
	@ApiOperation(value = "로그인 후 user정보를 반환한다.", response = UserDto.class)
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody Map<String, String> user, HttpServletResponse response) {
		System.out.println(user.toString());

		User member = userService.findByUid(user.get("uid"));

		if (!passwordEncoder.matches(user.get("upw"), member.getUpw())) {
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}

		String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
		response.setHeader("X-AUTH-TOKEN", token);

		return new ResponseEntity<User>(member, HttpStatus.OK);
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
		final String SEND_EMAIL_ID = "ksb940925@gamil.com"; // 관리자 email
		System.out.println(userEmailData.get("userEmail"));
		String userEmail = userEmailData.get("userEmail");
		// 이메일 인증
		int random = new Random().nextInt(900000) + 100000;
		String authCode = String.valueOf(random);

		String subject = "회원가입 인증 코드 발급 안내 입니다.";
		StringBuilder sb = new StringBuilder();
		sb.append("귀하의 인증 코드는 " + authCode + "입니다.");

		if (userService.send(subject, sb.toString(), SEND_EMAIL_ID, userEmail, null)) {
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

	
	// 이메일 중복 체크
	@ApiOperation(value = "이메일 중복 체크. 이미 있는 이메일인 경우 fail을 반환한다.", response = String.class)
	@PostMapping(value = "/e-dup")
	public ResponseEntity<String> edcheck(@RequestBody HashMap<String, String> userEmailData) {
		System.out.println("log - edcheck");
		String uemail = userEmailData.get("userEmail");
		User memeber = userService.edcheck(uemail);

		if (memeber==null) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// 재설정을 위한 현재 비밀번호 확인
	@ApiOperation(value = "재설정하기 위해 현재 비밀번호 확인. 성공 시 success, 실패시 fail을 반환한다.", response = String.class)
	@PostMapping(value = "/pw-dup")
	public ResponseEntity<String> pwcheck(@RequestBody HashMap<String, String> userData) {
		System.out.println("log - pwcheck");

		User member = userService.findByUid(userData.get("uid"));

		if (!passwordEncoder.matches(userData.get("upw"), member.getUpw())) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
	}

	
	@ApiOperation(value = "유저 이메일을 가지고 비밀번호 변경. 성공 시 success, 실패시 fail을 반환한다.", response = String.class)
	@PutMapping(value = "/pw-reset")
	public ResponseEntity<String> pwreset(@RequestBody HashMap<String, String> userData) {
		System.out.println("log - pwreset");
		
		
		int ret = userService.pwreset(passwordEncoder.encode(userData.get("upw")), userData.get("uemail"));
		
		if (ret == 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
	}
	
	
	
	
	
	
	// ========================================= //
	
	
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
	@GetMapping(value = "/users")
	public ListResult<User> findAllUser() {
		// 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
		return responseService.getListResult(userRepository.findAll());
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 단건 조회", notes = "회원번호(msrl)로 회원을 조회한다")
	@GetMapping(value = "/user")
	public SingleResult<User> findUserById(@ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
		// SecurityContext에서 인증받은 회원의 정보를 얻어온다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getName();
		// 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
		return responseService.getSingleResult(userRepository.findByUid(id).orElseThrow(CUserNotFoundException::new));
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
	@PutMapping(value = "/user")
	public SingleResult<User> modify(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
			@ApiParam(value = "회원닉네임", required = true) @RequestParam String unick) {
		User user = User.builder().uid(uid).unick(unick).build();
		return responseService.getSingleResult(userRepository.save(user));
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
	@DeleteMapping(value = "/user/{uid}")
	public CommonResult delete(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid) {
		Long ref = userRepository.deleteByUid(uid);
		System.out.println(ref);
		// 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
		return responseService.getSuccessResult();
	}

	
	
}