package com.ssafy.sub.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserPageResult {
	private int statusCode;	// 상태 코드
    private String message;	// 메세지
    private Object userFeeds;// UserPage
    private boolean mypage;	// mypage 설정
    private boolean isfollow;
    private boolean isblock;
}
