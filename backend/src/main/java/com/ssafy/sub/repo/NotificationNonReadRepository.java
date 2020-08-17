package com.ssafy.sub.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.NotificationNonRead;

public interface NotificationNonReadRepository extends JpaRepository<NotificationNonRead, Long> {

	List<NotificationNonRead> findAllByUid(int uid);
	
	Optional<NotificationNonRead> findById(int id);

	Long countByUid(int uid);

	@Transactional
	Long deleteById(int nid);

	List<NotificationNonRead> findAllByUidOrderByIdDesc(int uid);

	@Transactional
	void deleteAllByFid(int fid);

}
