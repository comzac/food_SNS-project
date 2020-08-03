package com.ssafy.sub.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class Relationship {

    @EmbeddedId
    protected RelationshipKey relationShipkey;

    @Column(name="state")
    private int state;

    @Column(name="isFollowing")
    private int isFollowing;

	public Relationship(RelationshipKey relationShipkey, int state) {
		super();
		this.relationShipkey = relationShipkey;
		this.state = state;
		this.isFollowing = 0;
	}
    
    
}