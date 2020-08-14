package com.ssafy.sub.service;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Recommand;

/**
 * 로그 분석 후 추천 관리
 * 
 * @author 이선수
 * @version 1.0, 로그 분석 저장
 */

@Service("LogService")
public interface LogService {

	/**
	 * 유저 성별,나이대,해쉬태그별 추천 점수 업데이트 및 저장
	 * @param recommand - 추천 정보 (로그)
	 */
	void update(Recommand recommand);
	
}
