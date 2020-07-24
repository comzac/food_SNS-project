package com.ssafy.sub.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UploadFileResponse {
	private String fileName;	// 상태 코드
    private String fileDownloadUri;			// 메세지
    private String contentType;			// 데이터
    private long size;			// 데이터
	

	
}
