package com.ssafy.sub.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.IdClass;

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
@Embeddable
public class FeedLikeKey implements Serializable {
	
	@Column(name = "fid")
	private int fid;
	
	@Column(name = "uid")
	private int uid;
}
