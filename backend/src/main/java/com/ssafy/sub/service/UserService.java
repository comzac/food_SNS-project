package com.ssafy.sub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.DBProfile;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.dto.UserSimple;

/*
 * @Service 애노테이션은 중요한 역할을 한다.
 * 단순히 @Service만 명시되어 있다면 이것은 그냥 마커(marker)의 역할만 한다.
 * 하지만, 인자를 가지게 되면 이것이 빈 설정의 id로 설정되어 다른 빈에서 그 이름으로 서비스 객체를 주입(injection)받게 된다.
 */
@Service("UserService")
public interface UserService {
	public User join(User user);
	
	public UserSimple getSimpleUser(String uid);
	
	public User findByUid(String uid);

	public User findById(int id);
	
	public User idCheck(String uid);
	
	public User nickcheck(String unick);
	
	public User edcheck(String uemail);
	
	public int pwreset(String upw, String uid);
	
	public boolean send(String subject, String text, String to, String filePath);
	
	public User updateNick(int id, String unick);
	
	public int userDelete(String uid);
	
	public int userUpdate(User user);
	
	public List<User> findUserNickByKeyword(String keyword);
	
	public User findByUemail(String uemail);
	
	public User findByGoogleUid(String uid);
	
	public DBProfile getProfile(int uid);
}
