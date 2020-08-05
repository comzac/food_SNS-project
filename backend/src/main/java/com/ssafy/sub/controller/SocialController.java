
package com.ssafy.sub.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.config.security.JwtTokenProvider;
import com.ssafy.sub.dto.Token;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.repo.UserRepository;
import com.ssafy.sub.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/social")
public class SocialController {
 
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserService userService;
	
	@Autowired
    RedisTemplate<String, Object> redisTemplate;

	// 로그인
		@ApiOperation(value = "로그인 후 user정보를 반환한다.", response = Result.class)
		@GetMapping("/login")
		public ResponseEntity<Result> login(HttpServletRequest request, HttpServletResponse response) {
			System.out.println("==========================================");
			System.out.println("login");
			System.out.println(request.getAttribute("userInfo"));
			Map<String, Object> userInfo = (Map<String, Object>) request.getAttribute("userInfo");
			
			// 회원가입
			String uid = Integer.toString((int) userInfo.get("uid"));
			User member = userService.findByGoogleUid(Integer.toString((int) userInfo.get("uid")));
			
			if(member == null) {
				member = User.builder().uid(uid).uemail((String) userInfo.get("uemail")).unick((String) userInfo.get("unick"))
						.upw(passwordEncoder.encode((CharSequence) userInfo.get("upw"))).uregdate(new Date())
						.roles(Collections.singletonList("ROLE_USER")) // 최초 가입시																											// USER 로 설정
						.build();
				
				userService.join(member);
			}
			

			Map<String, String> result = new HashMap<String, String>();
			result.put("uid", member.getUid());
			result.put("uemail", member.getUemail());
			result.put("unick", member.getUnick());

			// JWT 생성
			String token = jwtTokenProvider.createToken(member, member.getRoles());
			response.addHeader("token", token);
			
			ValueOperations<String, Object> vop = redisTemplate.opsForValue();
			Token jsonToken=new Token();
			jsonToken.setUsername(member.getUid());
			jsonToken.setRefreshToken(token);
			vop.set(member.getUid(), jsonToken);
			
			System.out.println("Redis 확인: "+redisTemplate.opsForValue().get(member.getUid()));
//			System.out.println("Redis 확인: "+redisTemplate.opsForValue().get(member.getU);
			return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, result),
					HttpStatus.OK);
		}
		
//		// 로그아웃
//		@ApiOperation(value = "로그아웃.", response = Result.class)
//		@GetMapping("/logout")
//		public ResponseEntity<Result> logout(HttpServletRequest request) {
//			System.out.println("logout");
//			
//			String username = null;
//			String token = jwtTokenProvider.resolveToken(request);
//			if(jwtTokenProvider.validateToken(token)) {
//				username = jwtTokenProvider.getUName(token);
//				
//				Date expirationDate = jwtTokenProvider.getExpirationDate(token);
//				System.out.println("Redis 확인: "+redisTemplate.opsForValue().get(username));
//				redisTemplate.delete(username);
//				System.out.println("Redis 확인: "+redisTemplate.opsForValue().get(username));
//				
//				String accessToken = token;
//		        redisTemplate.opsForValue().set(accessToken, new Token(accessToken, null));
//		        redisTemplate.expire(accessToken, 10*6*1000, TimeUnit.MILLISECONDS);
//		        System.out.println("Redis 확인: "+redisTemplate.opsForValue().get(token));
//		        
//		        return new ResponseEntity<Result>(new Result(StatusCode.OK, ResponseMessage.LOGOUT_SUCCESS, null), HttpStatus.OK);
//			}else {
//				return new ResponseEntity<Result>(new Result(StatusCode.FORBIDDEN, ResponseMessage.LOGOUT_FAIL, null), HttpStatus.FORBIDDEN);
//			}
//
//		}
	
	

}