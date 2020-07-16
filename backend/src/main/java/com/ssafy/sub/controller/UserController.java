
package com.ssafy.sub.controller;

import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.dto.UserDto;
import com.ssafy.sub.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	// 1. 로그인
	@ApiOperation(value = "로그인 후 user정보를 반환한다.", response = UserDto.class)
	@PostMapping(value = "/login")
	public UserDto login(@RequestBody HashMap<String, String> userData) {
		System.out.println("log - login");
		UserDto userDto = userService.login(userData.get("uid"), userData.get("upw"));
		// System.out.println(userDto.toString());
		return userDto;
	}

	// 2. 회원가입
	@ApiOperation(value = "아이디 중복 체크. 이미 있는 아이디인 경우 fail을 반환한다.", response = String.class)
	@GetMapping(value = "/idcheck/{uid}")
	public String idcheck(@PathVariable String uid) {
		System.out.println("log - idchk");
		String oldId = userService.idcheck(uid);
		if (oldId == null)
			return "success";
		else
			return "fail";
	}

	@ApiOperation(value = "인증 코드를 사용자의 이메일로 보내기. 메일이 전송되면 인증 코드를 반환한다.", response = String.class)
	@PostMapping(value = "/echeck")
	public String createEmailCheck(String userEmail) {
		final String SEND_EMAIL_ID = "ksb940925@gamil.com"; // 관리자 email

		// 이메일 인증
		int random = new Random().nextInt(900000) + 100000;
		String authCode = String.valueOf(random);

		String subject = "회원가입 인증 코드 발급 안내 입니다.";
		StringBuilder sb = new StringBuilder();
		sb.append("귀하의 인증 코드는 " + authCode + "입니다.");

		if (userService.send(subject, sb.toString(), SEND_EMAIL_ID, userEmail, null)) {
			return authCode; // vue에서 authCode로 일치 여부 확인 후 계정생성 버튼 활성화
		} else {
			return "fail";
		}
	}

	@ApiOperation(value = "닉네임 중복 체크. 이미 있는 닉네임인 경우 fail을 반환한다.", response = String.class)
	@GetMapping(value = "/nickcheck/{unick}")
	public String nickcheck(@PathVariable String unick) {

		System.out.println("log - nickchk");
		String oldNick = userService.nickcheck(unick);
		if (oldNick == null)
			return "success";
		else
			return "fail";
	}

	@ApiOperation(value = "회원가입. 성공 시 User정보, 실패 시 null 반환한다.", response = Integer.class)
	@PostMapping(value = "/create")
	public @ResponseBody UserDto create(@RequestBody UserDto dto) {
		System.out.println("log - create");
		if (userService.create(dto) == 1) {
			return dto;
		} else {
			return null;
		}
	}

	// 3. 비밀번호 찾기 (재설정 / 변경)
	// 3-1. 찾기

	// 3-2. 재설정하러가기 ( 재설정하기 위해 현재 비밀번호 확인 )
	@ApiOperation(value = "재설정하기 위해 현재 비밀번호 확인. 성공 시 success, 실패시 fail을 반환한다.", response = String.class)
	@PostMapping(value = "/pwreset")
	public String pwreset(@RequestBody HashMap<String, String> userData) {
		System.out.println("log - pwreset");
		UserDto dto = userService.pwreset(userData.get("uid"), userData.get("upw"));

		if (dto == null)
			return "fail";
		else
			return "success";
	}

	@ApiOperation(value = "비밀번호 변경. 성공 시 success, 실패시 fail을 반환한다.", response = String.class)
	@PostMapping(value = "/pwcheck")
	public String pwcheck(@RequestBody HashMap<String, String> userData) {
		System.out.println("log - pwcheck");
		int ret = userService.pwcheck(userData.get("uid"), userData.get("upw"));

		if (ret == 0)
			return "fail";
		else
			return "success";
	}

}