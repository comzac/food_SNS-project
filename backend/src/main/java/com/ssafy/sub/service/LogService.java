package com.ssafy.sub.service;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Recommand;

@Service("LogService")
public interface LogService {

	void update(Recommand recommand);
	
}
