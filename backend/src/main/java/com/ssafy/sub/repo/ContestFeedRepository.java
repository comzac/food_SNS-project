package com.ssafy.sub.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.ContestFeed;

@Repository
public interface ContestFeedRepository extends JpaRepository<ContestFeed, Long> {

//	List<ContestFeed> findAllByCidOrderByLikeDesc(int cid);

	Optional<ContestFeed> findById(int fid);

	@Transactional
	Long deleteById(int fid);

	List<ContestFeed> findByCid(int cid);

	List<ContestFeed> findByCidOrderByLikeCountDesc(int cid);

	List<ContestFeed> findAllByCidOrderByLikeCountDesc(int cid);


}
