package com.ssafy.sub.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

	Profile findByUid(int id);

}
