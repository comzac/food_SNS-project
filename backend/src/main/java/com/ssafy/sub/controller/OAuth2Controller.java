/*
 * 지울 예정 (테스트용)
 */
package com.ssafy.sub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2Controller {
	@GetMapping({ "", "/" })
	public String getAuthorizationMessage() {
		System.out.println("home");
		return "home";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping({ "/loginSuccess", "/hello" })
	public String loginSuccess() {
		return "hello";
	}

	@GetMapping("/loginFailure")
	public String loginFailure() {
		return "loginFailure";
	}
}
