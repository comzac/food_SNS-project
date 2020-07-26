package com.ssafy.sub.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RelationshipKey implements Serializable {
    @Column(name = "uid")
    private int uid;

    @Column(name = "relationuid")
    private int relationuid;
}
