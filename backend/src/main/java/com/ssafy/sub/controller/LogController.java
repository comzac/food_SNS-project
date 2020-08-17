package com.ssafy.sub.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sub.dto.Hashtag;
import com.ssafy.sub.dto.Recommand;
import com.ssafy.sub.dto.RecommandKey;
import com.ssafy.sub.dto.User;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.service.LogService;
import com.ssafy.sub.service.UserService;

import lombok.Getter;
import lombok.Setter;

/***
 * 
 * @author 이선수
 * @version 1.0 검색 임시 리스트 (해쉬태그, 유저 닉네임) 통합
 */

@Component
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

	@Autowired
	LogService logService;
	
	private BufferedWriter bw;
	private String textName;
	private PrintWriter pw;

	@Scheduled(fixedDelay = 6 * 60 * 60 * 1000L)	// 서버시작 후 24시간마다 반복
	@GetMapping("/update")
	public ResponseEntity update() throws IOException {
		System.out.println(textName + ": txt");
		String oldTextName = textName;
		
		// 분석
		System.out.println(oldTextName);
		File file = new File(filePath + oldTextName + ".txt");

		if(file.exists()) {
			System.out.println("분석");
			HashMap<String, Integer> recommandMap = new HashMap<String, Integer>();
			HashMap<String, Integer> recommandCnt = new HashMap<String, Integer>();

			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			String[] splitedStr = null;

			while ((line = br.readLine()) != null) {
				splitedStr = null;
				//System.out.println(line);
				splitedStr = line.split(", ");

				String time, uid, gender, age, action, hashtag;
				time = splitedStr[0];
				uid = splitedStr[1];
				gender = splitedStr[2];
				age = splitedStr[3];
				action = splitedStr[4];
				hashtag = splitedStr[5].split(":")[1];

				int score = 0;

				switch (action) {
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

				String key = age + "_" + gender + "_" + hashtag;

				if (recommandMap.get(key) == null) {
					recommandMap.put(key, score);
					recommandCnt.put(key, 1);
				} else {
					int old = recommandMap.get(key);
					recommandMap.put(key, old + score);
					
					int oldCnt = recommandCnt.get(key);
					recommandCnt.put(key, oldCnt+1);
				}
			}
			br.close();
			
			int ageGroup, gender, hid, score, today, cnt, avg;
			
			for (HashMap.Entry<String, Integer> elem : recommandMap.entrySet()) {

				String key = elem.getKey();
				String value = Integer.toString(elem.getValue());
				String [] keyList = key.split("_");
				ageGroup = Integer.parseInt(keyList[0]);
				gender = Integer.parseInt(keyList[1]);
				hid = Integer.parseInt(keyList[2]);
				score = 0;
				today = Integer.parseInt(value);
				cnt = recommandCnt.get(key);
				avg = today/cnt;
				RecommandKey recommnadKey = new RecommandKey(gender, ageGroup, hid);
				Recommand recommand = new Recommand(recommnadKey, score, today, cnt, avg);
//				System.out.println(gender); 
				logService.update(recommand); 
			}
		}
		

		
		// 파일 생성
		textName = LocalDateTime.now().toString().replace(":", "-").substring(0, 19);
//		textName = LocalDate.now().toString();
		bw = new BufferedWriter(new FileWriter(this.filePath+textName+".txt"));
		pw = new PrintWriter(bw,true);
		
		Result result = new Result(StatusCode.OK, ResponseMessage.LOG_UPDATE_SUCCESS, null);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	public void setString(User loginUser, String action, List<Hashtag> hashtagList) {
		// for log
		StringBuilder sbLog = new StringBuilder();
		int loginUserAge = userService.getUserAge(loginUser.getUbirth()); // for log age group
		LocalDateTime now = LocalDateTime.now(); // for log datetime

		for (Hashtag h : hashtagList) {
			sbLog = new StringBuilder();
			sbLog = sbLog.append(now).append(", ").append(loginUser.getId()).append(", ").append(loginUser.getUsex())
					.append(", ").append(loginUserAge).append(", ").append(action).append(", ").append(h.getContent())
					.append(":").append(h.getId());
			write(sbLog.toString());
		}
	}

	public void write(String str) {
		pw.write(str + "\n");
		pw.flush();
	}

}
