package com.ssafy.sub.model.response;

import com.ssafy.sub.dto.DBProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Notification {
	private int id;
	private int state;
	private String uid;	// 알림 받는 사람의 아이디
	private String unick;	// 알림 받는 사람의 닉네임
	private int fid;
	private String title;
	private String followid;
	private String likeid;
	private String commentid;
	private String notiUnick;	// 팔로우, 좋아요, 댓글을 단 사람의 닉네임
	private DBProfile notiUprofile; // 팔로우, 좋아요, 댓글을 단 사람의 프로필
}
