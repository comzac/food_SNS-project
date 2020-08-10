package com.ssafy.sub.service;

import java.util.List;
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
import com.ssafy.sub.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DBProfileRepository dbProfileRepository;
	@Autowired
	private JavaMailSender javaMailSender;

	// jpaRepository 호출
	@Override
	public User join(User user) {
		System.out.println("Join-service");
		System.out.println(user.toString());
		return userRepository.save(user);
	}

	// userSimple만들고 반환
	@Override
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
	@Override
	public User findByUid(String uid) {
		User user = userRepository.findByUid(uid).
				orElseThrow(() -> new RestException(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER));
		
		Optional<DBProfile> dbProOptional = dbProfileRepository.findByUid(String.valueOf(user.getId()));
		if(dbProOptional.isPresent()) {
			user.setDBProfile(dbProOptional.get());
		}
		return user;
	}
	
	@Override
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
	@Override
	public User idCheck(String uid) {
		User user = new User();
		return userRepository.findByUid(uid).
				orElse(user);
	}
	
	// unick -> user findByUnick
	@Override
	public User nickcheck(String unick) {
		return userRepository.findByUnick(unick);
	}

	// uemail -> user findByUemail
	@Override
	public User edcheck(String uemail) {
		return userRepository.findByUemail(uemail);
	}

	@Override
	public int pwreset(String upw, String uid) {
		return userRepository.updateUpw(upw, uid);
	}
	
	// no repository 
	@Override
	public boolean send(String subject, String text, String to, String filePath) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
		
		return true;
	}

	@Override
	public User updateNick(int id, String unick) {
		int ret = userRepository.updateUnick(id, unick);
		User user = userRepository.findById(id);

		return user;
	}
	
	@Override
	public int userDelete(String uid) {
		Optional<User> user = userRepository.findByUid(uid);
		if(user.isPresent()) {
			userRepository.delete(user.get());
			return 1;
		}else {
			return 0;
		}
	}
	
	@Override
	public int userUpdate(User user) {
		userRepository.save(user);
		return 1;
	}

	@Override
	public List<User> findUserNickByKeyword(String keyword) {
		return userRepository.findUserNickByKeyword(keyword);
	}
	
	@Override
	public User findByUemail(String uemail) {
		return userRepository.findByUemail(uemail);
	}

	@Override
	public User findByGoogleUid(String uid) {
		return userRepository.findByUid(uid).orElse(null);
	}
	
	@Override
	public DBProfile getProfile(int uid) {
		Optional<DBProfile> dbProOptional = dbProfileRepository.findByUid(String.valueOf(uid));
		if(dbProOptional.isPresent()) return dbProOptional.get();
		else return null;
	}
	
}
