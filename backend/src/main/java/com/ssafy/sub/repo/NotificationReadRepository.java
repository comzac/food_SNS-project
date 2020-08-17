package com.ssafy.sub.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.NotificationNonRead;
import com.ssafy.sub.dto.NotificationRead;

public interface NotificationReadRepository extends JpaRepository<NotificationRead, Long> {

	List<NotificationRead> findAllByUid(int uid);

	Long countByUid(int uid);

	List<NotificationRead> findByUidOrderByIdDesc(int uid);

	NotificationNonRead save(NotificationNonRead notificationNonRead);

	List<NotificationRead> findAllByUidOrderByIdDesc(int uid);

	@Transactional
	void deleteAllByFid(int fid);
}
