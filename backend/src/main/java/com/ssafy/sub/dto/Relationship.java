package com.ssafy.sub.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

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

}
