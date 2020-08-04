package com.ssafy.sub.model.response;

import com.ssafy.sub.dto.DBProfile;

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
	private String unick;
	private int state;
	private int isFollowing;
	private DBProfile dbProfile;

	public FollowResult(String uid, String relationuid, String unick, int state, int isFollowing) {
		super();
		this.uid = uid;
		this.relationuid = relationuid;
		this.unick = unick;
		this.state = state;
		this.isFollowing = isFollowing;
	}

}
