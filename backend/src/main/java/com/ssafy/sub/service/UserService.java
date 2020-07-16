package com.ssafy.sub.service;

import com.ssafy.sub.dto.UserDto;

public interface UserService {
	public UserDto login(String uid, String upw);
	public int create(UserDto dto);
	public String idcheck(String uid);
	public String nickcheck(String unick);
	public int pwcheck(String uid, String upw);
	public boolean send(String subject, String text, String from, String to, String filePath);
	public UserDto pwreset(String uid, String upw);
	
}
