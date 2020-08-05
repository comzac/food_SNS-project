package com.ssafy.sub.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.NotificationNonRead;
import com.ssafy.sub.dto.NotificationRead;

@Service("NotificationService")
public interface NotificationService {
	public Map<Long, String> register(final Long userId, final String token);
	
	public List<NotificationNonRead> findNotificationNonReadByUid(int uid);
	
	public List<NotificationRead> findNotificationReadByUid(int uid);

	List<NotificationRead> findNotificationReadByUidLimit(int uid, int limit);

	public NotificationRead readNotification(int nid);

	public Long countNotification(int uid);

	Long countNotificationRead(int uid);

	Long countNotificationNonRead(int uid);

	public NotificationNonRead notificationInsert(NotificationNonRead build);

}
