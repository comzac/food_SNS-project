package com.ssafy.sub.model.response;

import java.util.List;

import com.ssafy.sub.dto.FeedAll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedAllResult {
	private int statusCode;	// 상태 코드
    private String message;	// 메세지
    private List<FeedAll> feedAll;// feedAll
}
