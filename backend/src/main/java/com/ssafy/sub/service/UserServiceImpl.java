package com.ssafy.sub.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.UserDto;
import com.ssafy.sub.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public UserDto login(String uid, String upw) {
		return userMapper.login(uid, upw);
	}
	
	@Override
	public String idcheck(String uid) {
		return userMapper.idcheck(uid);
	}

	@Override
	public String nickcheck(String unick) {
		return userMapper.nickcheck(unick);

	}

	@Override
	public UserDto pwcheck(String uid, String upw) {
		return userMapper.pwcheck(uid, upw);
	}

	@Override
	public int pwreset(String uemail, String upw) {
		return userMapper.pwreset(uemail, upw);
	}

	@Override
	public int create(UserDto dto) {
		return userMapper.create(dto);
	}

	@Override
	public boolean send(String subject, String text, String from, String to, String filePath) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
		
		return true;
	}
	
	@Override
	public String edcheck(String uemail) {
		return userMapper.edcheck(uemail);
	}
	
}
