
package com.ssafy.sub.controller;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.config.security.JwtTokenProvider;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.exception.CUserNotFoundException;
import com.ssafy.sub.model.response.CommonResult;
import com.ssafy.sub.model.response.ListResult;
import com.ssafy.sub.model.response.SingleResult;
import com.ssafy.sub.repo.UserRepository;
import com.ssafy.sub.service.ResponseService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserSecurityController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ResponseService responseService;

    // 회원가입
    @PostMapping("/join/security")
    public String join(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
                .uid(user.get("uid"))
                .uemail(user.get("uemail"))
                .unick(user.get("unick"))
//                .upw((user.get("upw")))
                .upw(passwordEncoder.encode(user.get("upw")))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getUid();
    }

    // 로그인
    @PostMapping("/login/security")
    public ResponseEntity<User> login(@RequestBody Map<String, String> user, HttpServletResponse response) {
    	System.out.println(user.toString());
        User member = userRepository.findByUemail(user.get("uemail"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("upw"), member.getUpw())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
        response.setHeader("X-AUTH-TOKEN", token);
//        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
        return new ResponseEntity<User>(member, HttpStatus.OK);
    }
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
	@GetMapping(value = "/users")
	public ListResult<User> findAllUser() {
	    // 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
	    return responseService.getListResult(userRepository.findAll());
	}
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
	})
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
	        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
	@PutMapping(value = "/user")
	public SingleResult<User> modify(
	        @ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
	        @ApiParam(value = "회원닉네임", required = true) @RequestParam String unick) {
	    User user = User.builder()
	            .uid(uid)
	            .unick(unick)
	            .build();
	    return responseService.getSingleResult(userRepository.save(user));
	}
	@ApiImplicitParams({
	        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
	@DeleteMapping(value = "/user/{uid}")
	public CommonResult delete(
	        @ApiParam(value = "회원아이디", required = true) @RequestParam String uid) {
		Long ref = userRepository.deleteByUid(uid);
		System.out.println(ref);
	    // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
	    return responseService.getSuccessResult();
	}

}