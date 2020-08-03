package com.ssafy.sub.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.DBProfile;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.dto.UserSimple;
import com.ssafy.sub.exception.RestException;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.repo.DBProfileRepository;
import com.ssafy.sub.repo.ProfileRepository;
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
	DBProfileRepository dbProfileRepository;
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;

	// jpaRepository 호출
	public User join(User user) {
		System.out.println("Join-service");
		System.out.println(user.toString());
		return userRepository.save(user);
	}

	// userSimple만들고 반환
	public UserSimple getSimpleUser(String uid) {
//		System.out.println("userSimple "+ uid);
		User user = userRepository.findByUid(uid).
				orElseThrow(() -> new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER));
		
		UserSimple userSimple = new UserSimple();
		userSimple.setUid(user.getUid());
		userSimple.setUnick(user.getUnick());

		if(dbProfileRepository.findByUid(String.valueOf(user.getId())).isPresent()) {
			userSimple.setUprofile(dbProfileRepository.findByUid(String.valueOf(user.getId())).get());
		}
		
		return userSimple;
	}
	
	// custom repository 호출
	// uid -> Optional<user> findByUid()
	public User findByUid(String uid){
		User user = userRepository.findByUid(uid).
				orElseThrow(() -> new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER));
		
		Optional<DBProfile> dbProOptional = dbProfileRepository.findByUid(String.valueOf(user.getId()));
		if(dbProOptional.isPresent()) {
			user.setDBProfile(dbProOptional.get());
		}
		return user;
	}
	
	public User findById(int id) {
		try {
			User user = userRepository.findById(id);
			
			Optional<DBProfile> dbProOptional = dbProfileRepository.findByUid(String.valueOf(user.getId()));
			if(dbProOptional.isPresent()) {
				user.setDBProfile(dbProOptional.get());
			}
			
			return user;
		}catch (Exception e) {
			throw new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
		}
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
	public boolean send(String subject, String text, String to, String filePath) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
		
		return true;
	}

	public User updateNick(int id, String unick) {
		
		int ret = userRepository.updateUnick(id, unick);
		User user = userRepository.findById(id);

		return user;
	}
	
	public int userDelete(String uid) {
		Optional<User> user = userRepository.findByUid(uid);
		if(user.isPresent()) {
			userRepository.delete(user.get());
			return 1;
		}else {
			return 0;
		}
	}
	
	public int userUpdate(User user) {
		userRepository.save(user);
		return 1;
	}
	
}
