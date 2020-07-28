package com.ssafy.sub.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	
//	@OneToMany 
//	@JoinColumn(name = "files_id")
//	private Collection<DBFile> dbFiles;
	
//	@OneToMany(mappedBy="feed")
	@Transient
    private List<Hashtag> hashtag = new ArrayList<>();
	
	@Transient
    private List<DBFile> dbFiles = new ArrayList<>();

}
