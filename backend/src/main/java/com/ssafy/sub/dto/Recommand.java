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
    protected RecommandKey recommandkey;

    @Column(name="score")
    private int score;

	public Recommand(RecommandKey recommandkey, int score) {
		super();
		this.recommandkey = recommandkey;
		this.score = score;
	}

}