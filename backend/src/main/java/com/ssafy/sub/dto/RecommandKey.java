package com.ssafy.sub.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RecommandKey implements Serializable {

    @Column(name = "gender")
    private int gender;

    @Column(name = "ageGroup")
    private int ageGroup;
    
    @Column(name = "hashtag")
    private int hashtag;


}
