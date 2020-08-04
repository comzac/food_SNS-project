package com.ssafy.sub.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long> {
	Optional<Feed> findById(int id);
	
	@Transactional
	Long deleteById(int id);

	List<Feed> findByUid(int uid);
//	List<Feed> findByUser_id(int uid);
	
	Page<Feed> findByIdLessThan(int fid, Pageable pageable);
}
