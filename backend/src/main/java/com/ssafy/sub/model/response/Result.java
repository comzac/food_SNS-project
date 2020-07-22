package com.ssafy.sub.model.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Result {
	private HttpStatus statusCode;	// 상태 코드
    private String message;			// 메세지
    private Object data;			// 데이터

    public Result setStatusCode(HttpStatus statusCode) {
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
