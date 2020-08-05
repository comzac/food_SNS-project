package com.ssafy.sub.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ContestFeedLikeKey implements Serializable {
	
	@Column(name = "cfid")
    private int cfid;
	
	@Column(name = "uid")
    private int uid;
}
