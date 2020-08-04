package com.ssafy.sub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.sub.dto.Relationship;
import com.ssafy.sub.dto.RelationshipKey;
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

	@Override
	public Relationship followInsertAndDelete(int id, int rid) {
		Relationship relationship = relationQueryDsl.findRelation(id, rid);
		if (relationship == null) { // 등록
			return relationRepository.save(new Relationship(new RelationshipKey(id, rid), 0, 1));
		} else { // 삭제
			relationRepository.delete(relationship);
			return null;
		}
	}

	@Override
	public boolean checkRelations(int rid, int uid) {
		
		Relationship relationship = relationQueryDsl.checkRelations(rid, uid);
		if(relationship == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public Relationship followCheck(int uid, int rid) {
		return relationQueryDsl.findRelationshipFB(uid, rid);
	}

//   @Override
//   public Relationship followInsert(int id, int rid) {
//      List<Relationship> relationship = relationQueryDsl.findRelation(id, rid);
//      if(relationship.size() != 0)
//         return null;
//      return relationRepository.save(new Relationship(new RelationshipKey(id, rid), 1));
//   }
//
//   @Override
//   public long followDelete(int id, int rid) {
//      return relationQueryDsl.followDelete(id, rid);
//
//   }

}