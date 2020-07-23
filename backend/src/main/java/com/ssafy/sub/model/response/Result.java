package com.ssafy.sub.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Result {
	private int statusCode;	// 상태 코드
    private String message;			// 메세지
    private Object data;			// 데이터

    public Result setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Result setData(Object data) {
    	this.data = data;
    	return this;
    }
}
