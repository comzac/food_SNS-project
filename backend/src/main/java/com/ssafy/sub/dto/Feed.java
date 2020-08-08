package com.ssafy.sub.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class Feed {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int uid;
	
	@Column
	private String title;
	
	@Column
	private String content;
	
	@Column
	private Date regdate;
	
	@Column
	private Date editdate;
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY,mappedBy="feed", cascade = CascadeType.REMOVE)
    private List<DBFile> dbFiles = new ArrayList<>();

}
