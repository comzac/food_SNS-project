package com.ssafy.sub.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FeedAll {
	
	// 기본 feed 정보
	private Feed feed;
	
	// 해시태그
	private List<Hashtag> hashtag;
	
	// 댓글
	private List<Comment> comment;
	
	// 댓글 수
	private Long commentCount;
	
	// 유저 정보
	private UserSimple user;
	
	// 피드 좋아요 수
	private int likeCount;
	
	// 로그인 유저가 눌렀는지 여부
	private boolean like;
	
	// 로그인 유저가 내 피드인지 여부
	private boolean mypage;
	
	// 피드 추천
	private boolean recommand;

}
