package com.ssafy.sub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Relationship;
import com.ssafy.sub.repo.RelationQueryDsl;
import com.ssafy.sub.repo.RelationRepository;

@Service
public class RelationServiceImpl implements RelationService {

	@Autowired
	RelationRepository relationRepository;
	
	@Autowired
	RelationQueryDsl relationQueryDsl;
	
	@Override
	public List<Relationship> relationFollowerList(int relationuid) {
		return relationQueryDsl.findAllByRelationuid(relationuid);
	}

	@Override
	public List<Relationship> relationFollowingList(int uid) {
		return relationQueryDsl.findAllByUid(uid);
	}

	
	
}
