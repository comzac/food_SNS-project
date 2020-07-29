package com.ssafy.sub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Relationship;

@Service("RelationService")
public interface RelationService  {

	List<Relationship> relationFollowerList(int relationuid);

	List<Relationship> relationFollowingList(int uid);

	Relationship followInsert(int id, int rid);

	long followDelete(int id, int rid);

}
