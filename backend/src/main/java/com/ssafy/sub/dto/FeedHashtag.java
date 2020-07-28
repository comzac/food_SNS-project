package com.ssafy.sub.dto;

import javax.persistence.Column;
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
@Builder
@Entity
@Table(name="feed_hashtag")
public class FeedHashtag {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(name="feed_id")
	private int feedId;
	@Column(name="hashtag_id")
	private int hashtagId;

//	@ManyToOne
//	@JoinColumn(name="feed_id")
//	private Feed feed;
	
//	@ManyToOne
//	@JoinColumn(name="hashtag_id")
//	private Hashtag hashtag;
}
