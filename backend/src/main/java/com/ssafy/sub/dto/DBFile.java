package com.ssafy.sub.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name = "files")
public class DBFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private int fid;
	
    private String name;

    private String type;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="fid", insertable = false, updatable = false)
    private Feed feed;

	public DBFile(int fid, String name, String type) {
		this.fid = fid;
		this.name = name;
		this.type = type;
	}
    
    

}
