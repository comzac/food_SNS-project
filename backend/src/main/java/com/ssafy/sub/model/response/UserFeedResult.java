package com.ssafy.sub.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserFeedResult {
	private int statusCode;	// 상태 코드
    private String message;	// 메세지
    private Object feed;	// feedList
    private Object user;	// user정보
    private boolean mypage;	// mypage 설정
}
