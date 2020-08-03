package com.ssafy.sub.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FollowResult {
	private String uid;
	private String relationuid;
	private int state;
	private int isFollowing;
}


