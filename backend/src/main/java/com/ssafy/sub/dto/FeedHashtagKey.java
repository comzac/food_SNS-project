package com.ssafy.sub.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FeedHashtagKey implements Serializable {
	
	@Column(name="fid")
	private int fid;
	
	@Column(name="hid")
	private int hid;

//	@ManyToOne
//	@JoinColumn(name="feed_id")
//	private Feed feed;
	
//	@ManyToOne
//	@JoinColumn(name="hashtag_id")
//	private Hashtag hashtag;
}
