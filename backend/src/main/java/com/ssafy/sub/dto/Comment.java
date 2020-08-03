package com.ssafy.sub.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

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
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int fid; // feed id
	
	@Column
	private int pid; // parent id
	
	@Column
	private int uid;
	
	@Column
	private int depth;
	
	@Column
	private String content;
	
	@Column
	private Date regdate;	
	
	@Column
	private Date editdate;
	
	@Transient
    private UserSimple user;
}
