package com.ssafy.sub.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.NotificationNonRead;
import com.ssafy.sub.dto.NotificationRead;

/**
 * 유저의 읽은 및 안읽은 알림 관리
 * 
 * @author 김순빈
 * @version 1.0, 알림 서비스 기본 - 읽은 알림, 안읽은 알림 CRUD
 */

@Service("NotificationService")
public interface NotificationService {
	
	/**
	 * 유저 pk로 해당 유저의 안읽은 알림 조회
	 * @param int uid - 유저 pk
	 * @return List<NotificationNonRead> (안읽은 알림 정보)
	 */
	public List<NotificationNonRead> findNotificationNonReadByUid(int uid);
	
	/**
	 * 유저 pk로 해당 유저의 읽은 알림 조회
	 * @param int uid - 유저 pk
	 * @return List<NotificationRead> (읽은 알림 정보)
	 */
	public List<NotificationRead> findNotificationReadByUid(int uid);

	/**
	 * 안읽은 알림pk를 이용해 해당 안읽은 알림을 
	 * non_read_notification_db(안읽은 알림)에서 지운 후 
 	 * read_notification_db(읽은 알림)로 업데이트
	 * @param int nid - 안읽은 알림 pk
	 * @return NotificationRead (읽은 알림 정보)
	 */
	public NotificationRead readNotification(int nid);

	/**
	 * 유저의 알림 총 갯수
	 * @param int uid - 유저 pk
	 * @return Long (알림의 총 갯수: 읽은+안읽은)
	 */
	public Long countNotification(int uid);

	/**
	 * 유저의 읽은 알림 총 갯수
	 * @param int uid - 유저 pk
	 * @return Long (읽은 알림의 총 갯수)
	 */
	Long countNotificationRead(int uid);

	/**
	 * 유저의 안읽은 알림 총 갯수
	 * @param uid
	 * @return Long (안읽은 알림의 총 갯수)
	 */
	Long countNotificationNonRead(int uid);

	/**
	 * 유저 안읽은 알림 저장
	 * @param NotificationNonRead notificationNonRead - 안읽은 알림 정보
	 * @return NotificationNonRead (안읽은 알림 정보)
	 */
	public NotificationNonRead notificationInsert(NotificationNonRead notificationNonRead);

	/**
	 * 피드가 삭제됐을 때 피드 pk로 안읽은 알림, 읽은 알림 삭제
	 * @param fid
	 */
	public void feedDelete(int fid);

}
