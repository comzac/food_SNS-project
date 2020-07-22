package com.ssafy.sub.dto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUemail(String uemail);
	Optional<User> findByUid(String uid);
	Long deleteByUid(String uid);
}