package com.ssafy.sub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.ContestFeedFiles;
import com.ssafy.sub.dto.DBProfile;

@Repository
public interface ContestFeedFilesRepository extends JpaRepository<ContestFeedFiles, Long> {

	List<ContestFeedFiles> findAllByCfid(int fid);

}
