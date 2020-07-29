package com.ssafy.sub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.ssafy.sub.dto.QRelationship;
import com.ssafy.sub.dto.Relationship;

public class RelationQueryDsl extends QuerydslRepositorySupport{

	public RelationQueryDsl() {
		super(Relationship.class);
	}
	
	public List<Relationship> findAllByRelationuid(int id){
		QRelationship relationShip = QRelationship.relationship;
		return from(relationShip)
				.where(relationShip.relationShipkey.relationuid.eq(id))
				.distinct()
				.fetch();
	}
	
	public List<Relationship> findAllByUid(int id){
		QRelationship relationShip = QRelationship.relationship;
		return from(relationShip)
				.where(relationShip.relationShipkey.uid.eq(id))
				.distinct()
				.fetch();
	}
}
