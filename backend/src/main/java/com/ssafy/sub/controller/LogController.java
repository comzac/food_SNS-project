package com.ssafy.sub.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;

/***
 * 
 * @author 이선수
 * @version 1.0 검색 임시 리스트 (해쉬태그, 유저 닉네임) 통합
 */

@CrossOrigin(origins = "*")
@RestController
@Getter
@Setter
@RequestMapping("/logs")
public class LogController {
	
	private String filename;
	
	@Value("${logging.recommand.root}") // multipartfile 로컬 저장소 경로 (.yml 내)
	private String filePath;
	
	private BufferedWriter bw;
	private String textName;
	private PrintWriter pw;
	
	
	
	@GetMapping("/update")
	public ResponseEntity update() throws IOException {
		
		// 분석
		
		
		if(bw != null)
			pw.close();
		
		
		
		// 파일 생성
		textName = LocalDate.now().toString();
		bw = new BufferedWriter(new FileWriter(this.filePath+textName+".txt"));
		pw = new PrintWriter(bw,true);
		return null;
	}
	
	public void write(String str) {


		 
		pw.write(str+"\n");
		pw.flush();
		
		
	}
	
}
