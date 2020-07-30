package com.ssafy.sub.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

}
