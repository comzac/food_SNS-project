package com.ssafy.sub.repo;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;				// repository 종류 네 가지 중 나머지 세 개
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.sub.dto.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUid(String uid);
	User findByUnick(String unick);
	User findByUemail(String uemail);
	Long deleteByUid(String uid);

	@Transactional
    @Modifying
    @Query("UPDATE User u SET u.upw = :upw WHERE u.uemail = :uemail")
    int updateUpw(@Param("upw") String upw, @Param("uemail") String uemail);
    
}