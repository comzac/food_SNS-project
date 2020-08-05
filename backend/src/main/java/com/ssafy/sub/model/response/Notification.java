package com.ssafy.sub.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Notification {
	private int id;
	private int state;
	private String uid;
	private int fid;
	private String title;
	private String followid;
	private String likeid;
	private String commentid;
}
