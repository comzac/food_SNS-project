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

    @Column(name="accumulate")
    private int accumulate;

    @Column(name="today")
    private int today;

    @Column(name="cnt")
    private int cnt;

    @Column(name="avg")
    private int avg;

	public Recommand(RecommandKey recommandkey, int accumulate, int today, int cnt, int avg) {
		super();
		this.recommandkey = recommandkey;
		this.accumulate = accumulate;
		this.today = today;
		this.cnt = cnt;
		this.avg = avg;
	}

}

