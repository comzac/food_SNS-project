package com.ssafy.sub.oauth2.security;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.sub.dto.User;

@Component
public class GoogleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private HttpSession httpSession;
    private ObjectMapper objectMapper;

    public GoogleAuthenticationSuccessHandler(HttpSession httpSession, ObjectMapper objectMapper) {
        this.httpSession = httpSession;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        httpSession.set setAttribute("login_user", getGoogleUser(authentication)); // 간단한 구글계정 정보를 세션에 저장
    	System.out.println("userInfo");
    	Map<String, Object> userInfo = getGoogleUser(authentication);
    	request.setAttribute("userInfo", userInfo);
        request.getRequestDispatcher("/social/login").forward(request, response);
    }

    private Map<String, Object> getGoogleUser(Authentication authentication) { // OAuth 인증정보를 통해 GoogleUser 인스턴스 생성
        
    	OAuth2AuthenticationToken oAuth2Authentication = (OAuth2AuthenticationToken) authentication;
    	String serviceName = oAuth2Authentication.getAuthorizedClientRegistrationId();
    	Map<String, Object> userInfo = new HashMap<String, Object>();
    	Map<String, Object> socialInfo = (oAuth2Authentication.getPrincipal().getAttributes());
    	System.out.println(socialInfo);
    	switch(serviceName) {
    	case "kakao":
    		System.out.println(serviceName);
    		Map<String, Object> properties = (Map<String, Object>) socialInfo.get("properties");
    		Integer uid = (Integer) socialInfo.get("id");
    		userInfo.put("uid", Integer.toString(uid.intValue()));
    		userInfo.put("uemail", "");
    		userInfo.put("unick", properties.get("nickname"));
    		userInfo.put("upw", Integer.toString(uid.intValue()));
    		System.out.println(Integer.toString(uid.intValue()).getClass().getName());
    		userInfo.put("profileImg", properties.get("profile_image"));
    		break;
    	case "google":
    		System.out.println(serviceName);
			String id = socialInfo.get("email").toString().split("@")[0];
    		userInfo.put("uid", id);
    		userInfo.put("uemail", socialInfo.get("email"));
    		userInfo.put("unick", socialInfo.get("name"));    		
    		userInfo.put("upw", socialInfo.get("sub"));
    		System.out.println(socialInfo.get("sub").getClass().getName());
    		userInfo.put("profileImg", socialInfo.get("picture"));	
    		break;
    	}
//    	System.out.println("1");
//    	System.out.println(oAuth2Authentication.getAuthorizedClientRegistrationId());
//    	System.out.println("2");
//    	System.out.println(oAuth2Authentication.getCredentials().toString());
//    	System.out.println("3");
//    	System.out.println(oAuth2Authentication.getDetails().toString());
//    	System.out.println("4");
//    	System.out.println(oAuth2Authentication.getName());
//    	System.out.println("5");
//    	System.out.println(oAuth2Authentication.getPrincipal().getAttributes());
//    	System.out.println("6");
//    	System.out.println(authentication.getName());
        return userInfo;
    }
}