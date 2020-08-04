package com.ssafy.sub.config.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.ssafy.sub.dto.User;
import com.ssafy.sub.service.CustomUserDetailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	private String secretKey = "webfirewood";
    // 토큰 유효시간 30분
    private long tokenValidTime = 7 * 24 * 60 * 60 * 1000L;
    
    private final UserDetailsService userDetailsService;
    private final CustomUserDetailService cUserDetailsService;

	@Autowired
    RedisTemplate<String, Object> redisTemplate;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(User user, List<String> roles) {
    	System.out.println("? "+ user.getUsername());
        Claims claims = Jwts.claims().setSubject(user.getUsername()); // JWT payload 에 저장되는 정보단위
        claims.put("uid", user.getUid());
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 
                                                                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = cUserDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 아이디 추출
    public String getUserPk(String token) {
//    	System.out.println("userPk: "+Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    
    // 토큰에서 회원 이름 추출
    public String getUName(String token) {
    	Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    	return claims.getBody().get("uid", String.class);
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
        	System.out.println(redisTemplate.opsForValue().get(jwtToken));
        	if(redisTemplate.opsForValue().get(jwtToken) != null) return false;
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    
    // 만료일자 확인
    public Date getExpirationDate(String jwtToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        return claims.getBody().getExpiration();
    }
}
