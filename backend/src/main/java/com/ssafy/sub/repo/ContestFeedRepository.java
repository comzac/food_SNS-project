package com.ssafy.sub.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.ContestFeed;

public interface ContestFeedRepository extends JpaRepository<ContestFeed, Long> {

//	List<ContestFeed> findAllByCidOrderByLikeDesc(int cid);

	Optional<ContestFeed> findById(int fid);

	@Transactional
	Long deleteById(int fid);

	List<ContestFeed> findByCid(int cid);

}
