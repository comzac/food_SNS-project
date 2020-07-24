package com.ssafy.sub.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	
//	@ManyToOne//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "uid")//, insertable = false, updatable = false)
//	private User user;
	
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
}
