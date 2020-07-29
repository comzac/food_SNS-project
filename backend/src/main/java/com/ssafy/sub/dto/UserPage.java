package com.ssafy.sub.dto;

import java.util.List;

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
public class UserPage {
	
	// user정보
	private UserSimple user;
	
	// 팔로우 수
	private int followerCount;
	
	// 팔로잉 수
	private int followingCount;
	
	// 유저가 쓴 피드
	private List<Feed> feed;
	
	// 유저가 쓴 피드 수
	private int feedCount;

}
