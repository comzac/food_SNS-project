package com.ssafy.sub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

}
