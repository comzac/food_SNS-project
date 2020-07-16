package com.ssafy.sub.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MoveController {

	@RequestMapping("/")
	String index(HttpServletRequest request) {
		return "login";
	}
}