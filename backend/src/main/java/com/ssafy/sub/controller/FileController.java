package com.ssafy.sub.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sub.exception.FileStorageException;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.service.FileStorageService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/files")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileStorageService fileStorageService;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
	@PostMapping("/upload/multipleFiles")
	public ResponseEntity<Result> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
			@RequestParam int fid, @RequestParam("coordi") String[] coordi ) throws FileStorageException, IOException {

		List<String> fileNameList = new ArrayList<String>();
		int len = files.length;

		for(int i = 0 ; i < len ;i++)
		{
			String fileName = uploadFile(files[i], fid, coordi[i]);
			fileNameList.add(fileName);
		}

		Result result = new Result(StatusCode.CREATED, ResponseMessage.CREATE_FILE, fileNameList);
		return new ResponseEntity<Result>(result, HttpStatus.CREATED);
	}

	public String uploadFile(MultipartFile file, int fid, String coordi) throws FileStorageException, IOException {
		String fileName = fileStorageService.storeFile(file, fid, coordi);
		return fileName;
	}

}