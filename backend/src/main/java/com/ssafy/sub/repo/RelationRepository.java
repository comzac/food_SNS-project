package com.ssafy.sub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sub.dto.Relationship;

public interface RelationRepository extends JpaRepository<Relationship, Long>{
}
