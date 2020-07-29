package com.ssafy.sub.dto;

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
@ToString
public class UserSimple {
	
	// uid
	private String uid;
	
	// unick
	private String unick;
	
	// uprofile
	private DBProfile uprofile;
	
}
