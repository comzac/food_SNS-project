package com.ssafy.sub.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.NotificationNonRead;
import com.ssafy.sub.dto.NotificationRead;
import com.ssafy.sub.repo.NotificationNonReadRepository;
import com.ssafy.sub.repo.NotificationReadRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationReadRepository notificationReadRepository;
	@Autowired
	NotificationNonReadRepository notificationNonReadRepository;
	

	@Override
	public List<NotificationNonRead> findNotificationNonReadByUid(int uid) {
		return notificationNonReadRepository.findAllByUidOrderByIdDesc(uid);
	}

	@Override
	public List<NotificationRead> findNotificationReadByUid(int uid) {
		return notificationReadRepository.findAllByUidOrderByIdDesc(uid);
	}
	
	@Override
	public NotificationRead readNotification(int nid) {
		System.out.println("read "+nid);
		NotificationNonRead nonRead = notificationNonReadRepository.findById(nid).get();
		notificationNonReadRepository.deleteById(nid); 

		Date expiredate = nonRead.getRegdate();
		expiredate.setDate(expiredate.getDate()+30);	// 만료 30일 후
		NotificationRead read = notificationReadRepository.save(
									NotificationRead.builder().uid(nonRead.getUid()).fid(nonRead.getFid())
														.rid(nonRead.getRid()).lid(nonRead.getLid()).cid(nonRead.getCid())
														.actionUid(nonRead.getActionUid())
														.expiredate(expiredate).state(nonRead.getState()).build());
		
		return read;
	}

	@Override
	public Long countNotification(int uid) {
		Long totalCount = 0L;
		Long readCount = notificationReadRepository.countByUid(uid);
		Long nonReadCount = notificationNonReadRepository.countByUid(uid);
		totalCount = readCount + nonReadCount;
		return totalCount;
	}
	
	@Override
	public Long countNotificationRead(int uid) {
		return notificationReadRepository.countByUid(uid);
	}
	
	@Override
	public Long countNotificationNonRead(int uid) {
		return notificationNonReadRepository.countByUid(uid);
	}

	@Override
	public NotificationNonRead notificationInsert(NotificationNonRead notificationNonRead) {
		return notificationReadRepository.save(notificationNonRead);
	}

	@Override
	public void feedDelete(int fid) {
		notificationNonReadRepository.deleteAllByFid(fid);
		notificationReadRepository.deleteAllByFid(fid);
	}
	
}
