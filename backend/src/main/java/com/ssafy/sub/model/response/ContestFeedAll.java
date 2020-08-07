package com.ssafy.sub.model.response;

import java.util.HashMap;

import com.ssafy.sub.dto.ContestFeed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContestFeedAll {
	private ContestFeed contestFeed;
	private HashMap<String, Object> statistics;
	private boolean islike;
}
