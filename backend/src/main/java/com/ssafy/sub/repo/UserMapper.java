//package com.ssafy.sub.repo;
//
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import com.ssafy.sub.dto.UserDto;
//
//@Mapper
//public interface UserMapper {
//	public UserDto login(@Param("uid") String uid, @Param("upw") String upw);
//	public int create(UserDto dto);
//	public String idcheck(String uid);
//	public String nickcheck(String unick);
//	public UserDto pwcheck(@Param("uid") String uid, @Param("upw") String upw);
//	public int pwreset(@Param("uemail") String uid, @Param("upw") String upw);
//	public String edcheck(@Param("uemail") String uemail);
//}
