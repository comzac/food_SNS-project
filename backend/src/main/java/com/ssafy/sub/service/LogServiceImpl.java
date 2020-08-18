package com.ssafy.sub.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Recommand;
import com.ssafy.sub.repo.LogQueryDsl;
import com.ssafy.sub.repo.LogRepo;

@Service
public class LogServiceImpl implements LogService {
	@Autowired
	LogQueryDsl logQueryDsl;

	@Autowired
	LogRepo logRepo;

	@Override
	@Transactional
	public void update(Recommand newRecommand) {
		Recommand recommand = logQueryDsl.dupCehck(newRecommand);
		if (recommand == null) { // 기록에 없다면,
//			System.out.println("기록 없");
			newRecommand.setAccumulate(newRecommand.getToday());
			logRepo.save(newRecommand);
		} else { // 기록이 있다면,
			int oldScore = recommand.getAccumulate();
			int curScore = newRecommand.getAccumulate();

			int oldCnt = recommand.getCnt();
			int curCnt = newRecommand.getCnt();
			int curToday = newRecommand.getToday();

			
			recommand.setAccumulate(oldScore + curToday);
			recommand.setToday(curToday);
			recommand.setCnt(oldCnt + curCnt);
			recommand.setAvg(recommand.getAccumulate()/recommand.getCnt());
		}
	}

}
