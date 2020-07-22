package com.ssafy.sub.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;				// repository 종류 네 가지 중 나머지 세 개
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.repository.Repository;

import com.ssafy.sub.dto.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUemail(String uemail);
	Optional<User> findByUid(String uid);
	Long deleteByUid(String uid);
}