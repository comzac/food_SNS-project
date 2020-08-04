package com.ssafy.sub.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.ssafy.sub.dto.QRelationship;
import com.ssafy.sub.dto.Relationship;

@Repository
public class RelationQueryDsl extends QuerydslRepositorySupport{

	public RelationQueryDsl() {
		super(Relationship.class);
	}
	
	public List<Relationship> findAllByRelationuid(int id){
		QRelationship relationShip = QRelationship.relationship;
		return from(relationShip)
				.where(relationShip.relationShipkey.relationuid.eq(id)
						.and(relationShip.state.eq(0)))
				.distinct()
				.fetch();
	}
	
	public List<Relationship> findAllByUid(int id){
		QRelationship relationShip = QRelationship.relationship;
		return from(relationShip)
				.where(relationShip.relationShipkey.uid.eq(id)
						.and(relationShip.state.eq(0)))
				.distinct()
				.fetch();
	}

	@Transactional
	public long followDelete(int id, int rid) {
		QRelationship relationShip = QRelationship.relationship;

		return delete(relationShip)
				.where(relationShip.relationShipkey.uid.eq(id)
						.and(relationShip.relationShipkey.relationuid.eq(rid)))
				.execute();
	}

	public Relationship findRelation(int id, int rid) {
		QRelationship relationShip = QRelationship.relationship;

		return from(relationShip)
				.where(relationShip.relationShipkey.uid.eq(id)
						.and(relationShip.relationShipkey.relationuid.eq(rid))
						.and(relationShip.state.eq(0)))
				.fetchOne();
	}

	public Relationship checkRelations(int rid, int uid) {
		QRelationship relationShip = QRelationship.relationship;

		return from(relationShip)
				.where(relationShip.relationShipkey.uid.eq(uid)
						.and(relationShip.relationShipkey.relationuid.eq(rid))
						.and(relationShip.state.eq(0)))
				.fetchOne();
	}
	
	// 그냥 상태 가져오기
	public Relationship findRelationshipFB(int id, int rid) {
		QRelationship relationShip = QRelationship.relationship;
		return from(relationShip)
				.where(relationShip.relationShipkey.uid.eq(id)
						.and(relationShip.relationShipkey.relationuid.eq(rid)))
				.fetchOne();
	}

}
