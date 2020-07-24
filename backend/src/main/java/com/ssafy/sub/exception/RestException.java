package com.ssafy.sub.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private int statusCode;
	private String message;
	private HttpStatus status;

	public RestException(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}
