package com.ssafy.sub.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByFid(int fid);
	
	Optional<Comment> findById(int id);

	@Transactional
	Long deleteById(int id);
	
	Long countByFid(int fid);
}
