package com.ssafy.sub.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@ToString
public class Recommand {
	
	@EmbeddedId
    protected RecommandKey recommandKey;
	
	@Column(name = "score")
    private int score;

	public Recommand(RecommandKey recommandKey, int score) {
		super();
		this.recommandKey = recommandKey;
		this.score = score;
	}
	
}
