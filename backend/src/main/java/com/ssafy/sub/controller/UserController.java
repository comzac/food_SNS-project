package com.ssafy.sub.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
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

@RestController
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	// 1. 로그인
	@PostMapping(value = "/login")
	public @ResponseBody UserDto login(@RequestBody HashMap<String, String> userData) {
		System.out.println("log - login");
//		System.out.println(uid + "/ " + upw);
		UserDto userDto = userService.login(userData.get("uid"), userData.get("upw"));
		System.out.println(userDto.toString());
		return userDto;
	}
	
	// 2. 회원가입
	// 2-1. id 중복체크
	@GetMapping(value = "/idcheck/{uid}")
	public String idcheck(@PathVariable String uid) {
		System.out.println("log - idchk");
		String oldId = userService.idcheck(uid);
		if(oldId == null)
			return "success";
		else
			return "fail";
	}
	
	// 2-2. email 인증
	@PostMapping(value = "/echeck")
	public String createEmailCheck(String userEmail){
		final String SEND_EMAIL_ID = "ksb940925@gamil.com";	// 관리자 email

		//이메일 인증
		int random = new Random().nextInt(900000) + 100000;
		String authCode = String.valueOf(random);
		
		String subject = "회원가입 인증 코드 발급 안내 입니다.";
		StringBuilder sb = new StringBuilder();
		sb.append("귀하의 인증 코드는 " + authCode + "입니다.");
		
		if(userService.send(subject, sb.toString(), SEND_EMAIL_ID, userEmail, null)) {
			return authCode;	//vue에서 authCode로 일치 여부 확인 후 계정생성 버튼 활성화
		}else {
			return "fail";
		}
	}

	// 2-3. nick 중복체크
	@GetMapping(value = "/nickcheck/{unick}")
	public String nickcheck(@PathVariable String unick) {
		
		System.out.println("log - nickchk");
		String oldNick = userService.nickcheck(unick);
		if(oldNick == null)
			return "success";
		else
			return "fail";
	}
	
	// 2-4. 계정 생성
	@PostMapping(value = "/create")
	public @ResponseBody int create(@RequestBody UserDto dto) {
		System.out.println("log - create");

		return userService.create(dto);
	}
	
	// 3. 비밀번호 찾기 (재설정 / 변경)
	// 3-1. 찾기
	// 2-2) 이메일 인증 활용 (echeck)
	
	// 3-2. 재설정하러가기 ( 재설정하기 위해 현재 비밀번호 확인 )
	@PostMapping(value = "/pwreset")
	public String pwreset(@RequestBody String upw) {
		
		return "fail";
	}
		
	// 3-3. 비밀번호 변경
	@PostMapping(value = "/pwcheck")
	public String pwcheck(@RequestBody UserDto dto) {
		System.out.println("log - pwcheck");
		int ret = userService.pwcheck(dto);
		
		if(ret == 0)
			return "fail";
		else
			return "success";
	}	
	
	
	
	
}
