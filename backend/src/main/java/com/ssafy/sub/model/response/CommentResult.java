package com.ssafy.sub.model.response;

import com.ssafy.sub.dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentResult {

	private Comment comment;
	private Long likeCount;
	private boolean islike;
}
