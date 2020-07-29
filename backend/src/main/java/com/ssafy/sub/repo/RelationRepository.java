package com.ssafy.sub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.Relationship;

@Repository
public interface RelationRepository extends JpaRepository<Relationship, Long>{

}
