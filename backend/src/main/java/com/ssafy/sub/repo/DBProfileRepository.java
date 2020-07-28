package com.ssafy.sub.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.DBProfile;

@Repository
public interface DBProfileRepository extends JpaRepository<DBProfile, String> {
	Optional<DBProfile> findByUid(String uid);
}
