package com.ssafy.sub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.User;
import com.ssafy.sub.repo.UserRepository;

@Service("UserService")
/*
 * @Service 애노테이션은 중요한 역할을 한다.
 * 단순히 @Service만 명시되어 있다면 이것은 그냥 마커(marker)의 역할만 한다.
 * 하지만, 인자를 가지게 되면 이것이 빈 설정의 id로 설정되어 다른 빈에서 그 이름으로 서비스 객체를 주입(injection)받게 된다.
 */

public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;

	// jpaRepository 호출
	public User join(User user) {
		System.out.println("Join-service");
		System.out.println(user.toString());
		return userRepository.save(user);
	}

	// custom repository 호출
	
	// uid -> Optional<user> findByUid()
	public User findByUid(String uid){
		return userRepository.findByUid(uid).
				orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
	}

	// uid -> Optional<user> findByUid()
	public User idCheck(String uid) {
		User user = new User();
		return userRepository.findByUid(uid).
				orElse(user);
	}
	
	// unick -> user findByUnick
	public User nickcheck(String unick) {
		return userRepository.findByUnick(unick);
	}

	// uemail -> user findByUemail
	public User edcheck(String uemail) {
		return userRepository.findByUemail(uemail);
	}

	public int pwreset(String upw, String uemail) {
		return userRepository.updateUpw(upw, uemail);
	}
	
	
	// no repository 
	public boolean send(String subject, String text, String from, String to, String filePath) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
		
		return true;
	}











	
}
