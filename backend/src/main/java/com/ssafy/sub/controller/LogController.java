package com.ssafy.sub.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.dto.Hashtag;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.service.UserService;

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
	
	@Autowired
	private UserService userService;
	
	private String filename;
	
	@Value("${logging.recommand.root}") // multipartfile 로컬 저장소 경로 (.yml 내)
	private String filePath;
	
	private BufferedWriter bw;
	private String textName;
	private PrintWriter pw;
	

	@GetMapping("/update")
	public ResponseEntity update() throws IOException {
		
		// 분석
		File file = new File("filePath"+textName);
		
		HashMap<String, Integer> recommandMap = new HashMap<String, Integer>();
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		String[] splitedStr = null;
		
		while((line = br.readLine()) != null) {
			splitedStr = null;
			
			splitedStr = line.split(", ");
			 
			String time, uid, gender, ageGroup, action, hid;
			time = splitedStr[0];
			uid = splitedStr[1];
			gender = splitedStr[2];
			ageGroup = splitedStr[3];
			action = splitedStr[4];
			hid = splitedStr[5];
			
			int score = 0;
			switch(action) {
			case "like":
				score = 1;
				break;
			case "search":
				score = 2;
				break;
			case "revisit":
				score = 3;
				break;
			case "insert":
				score = 4;
				break;
			case "unlike":
				score = -1;
				break;
			}
			
			String key = gender + "_" + ageGroup;
			if(recommandMap.get(key) == null) {
				recommandMap.put(key, score);
			}else {
				int old = recommandMap.get(key);
				recommandMap.put(key, old+score);
			}

			//recommandMap.forEach((key, value) -> System.out.println("key: " + key + ", value: " + value));
		}
		
		
		
//		if(bw != null)
//			pw.close();
//		
//		
//		
//		// 파일 생성
//		textName = LocalDate.now().toString();
//		bw = new BufferedWriter(new FileWriter(this.filePath+textName+".txt"));
//		pw = new PrintWriter(bw,true);
		return null;
	}
	
	public void setString(User loginUser, String action, List<Hashtag> hashtagList) {
		// for log
		StringBuilder sbLog = new StringBuilder(); 
		int loginUserAge = userService.getUserAge(loginUser.getUbirth());	// for log age group
		LocalDateTime now = LocalDateTime.now();	// for log datetime
		
		for(Hashtag h: hashtagList) {
			sbLog = new StringBuilder();
			sbLog = sbLog.append(now).append(", ")
					.append(loginUser.getId()).append(", ")
					.append(loginUser.getUsex()).append(", ")
					.append(loginUserAge).append(", ")
					.append(action).append(", ")
					.append(h.getContent()).append(":")
					.append(h.getId());
			write(sbLog.toString());
		}
	}
	
	public void write(String str) {
		pw.write(str+"\n");
		pw.flush();
	}
	
}
