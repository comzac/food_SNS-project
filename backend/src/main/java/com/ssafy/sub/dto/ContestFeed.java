package com.ssafy.sub.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
//@ToString
public class ContestFeed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int cid;
	
	private int uid;
	
	private String title;
	
	private String content;
	
	private Date regdate;
	
	private int likeCount;
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY,mappedBy="contestFeed", cascade = CascadeType.REMOVE)
	private List<ContestFeedFiles> files = new ArrayList<>();

}
