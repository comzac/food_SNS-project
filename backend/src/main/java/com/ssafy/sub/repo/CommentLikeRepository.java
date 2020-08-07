package com.ssafy.sub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.CommentLike;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

}
