package com.ssafy.sub.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.DBProfile;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.dto.UserSimple;

/**
 * 
 * @author 김순빈
 * @version 1.0 유저의 연령대 확인
 */

/*
 * @Service 애노테이션은 중요한 역할을 한다.
 * 단순히 @Service만 명시되어 있다면 이것은 그냥 마커(marker)의 역할만 한다.
 * 하지만, 인자를 가지게 되면 이것이 빈 설정의 id로 설정되어 다른 빈에서 그 이름으로 서비스 객체를 주입(injection)받게 된다.
 */
@Service("UserService")
public interface UserService {
	
	/**
	 * 회원가입
	 * @param user - 회원가입할 유저 정보
	 * @return User (회원가입된 유저 정보)
	 */
	public User join(User user);
	
	/**
	 * 유저의 일부 정보 조회
	 * @param String uid - 유저의 아이디
	 * @return UserSimple (유저의 일부 정보 - id, uid, unick, uprofile, ubirth, usex)
	 */
	public UserSimple getSimpleUser(String uid);
	
	/**
	 * 아이디를 이용한 유저 정보 조회
	 * @param String uid - 유저의 아이디
	 * @return User (유저 정보)
	 */
	public User findByUid(String uid);

	/**
	 * 기본 id를 이용한 유저 정보 조회
	 * @param int id - 유저의 기본 id
	 * @return User (유저 정보)
	 */
	public User findById(int id);
	
	/**
	 * 아이디를 이용한 유저 정보 조회
	 * @param String uid - 유저의 아이디
	 * @return User (유저 정보)
	 */
	public User idCheck(String uid);
	
	/**
	 * 닉네임을 이용한 유저 정보 조회
	 * @param String unick - 유저의 닉네임
	 * @return User (유저 정보)
	 */
	public User nickcheck(String unick);
	
	/**
	 * 이메일을 이용한 유저 정보 조회
	 * @param String uemail - 유저의 이메일
	 * @return User (유저 정보)
	 */
	public User edcheck(String uemail);
	
	/**
	 * 유저 비밀번호 변경
	 * @param String upw - 변경할 비밀번호
	 * @param String uid - 유저의 아이디
	 * @return User (유저 정보)
	 */
	public int pwreset(String upw, String uid);
	
	/**
	 * 인증번호 메일 발송
	 * @param String subject - 발신 메일 제목
	 * @param String text - 발신 메일 내용
	 * @param String to - 수신 메일 주소
	 * @param String filePath - 발신 메일에 첨부할 파일의 경로 (안씀)
	 * @return boolean (성공여부)	// 다시 확인
	 */
	public boolean send(String subject, String text, String to, String filePath);
	
	/**
	 * 유저의 닉네임 변경
	 * @param int id - 유저의 기본 id
	 * @param unick - 변경할 닉네임
	 * @return User (유저 정보)
	 */
	public User updateNick(int id, String unick);
	
	/**
	 * 유저 삭제 (회원 탈퇴)
	 * @param String uid - 유저의 아이디
	 * @return int (삭제 성공 여부)
	 */
	public int userDelete(String uid);
	
	/**
	 * 유저 정보 변경
	 * @param User user - 변경할 유저 정보
	 * @return int () 	// 확인
	 */
	public int userUpdate(User user);
	
	/**
	 * 검색 키워드를 포함한 닉네임을 가진 유저들 조회 
	 * @param String keyword - 검색 키워드
	 * @return List<User> (유저들 정보)
	 */
	public List<User> findUserNickByKeyword(String keyword);
	
	/**
	 * 이메일을 이용한 유저 정보 조회
	 * @param String uemail - 유저의 이메일
	 * @return User (유저 정보)
	 */
	public User findByUemail(String uemail);
	
	/**
	 * 아이디를 이용한 유저 정보 조회
	 * @param String uid - 유저의 아이디
	 * @return User (유저 정보)
	 */
	public User findByGoogleUid(String uid);
	
	/**
	 * 기본 id를 이용한 유저 프로필 조회
	 * @param int uid - 유저의 기본 id
	 * @return DBProfile (유저 프로필 정보)
	 */
	public DBProfile getProfile(int uid);
	
	/**
	 * 유저의 생일을 이용한 유저 연령대 조회
	 * @param Date ubirth - 유저의 생일
	 * @return int (유저의 연령대)
	 */
	public int getUserAge(Date ubirth);
}
