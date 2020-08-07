package com.ssafy.sub.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ssafy.sub.dto.DBFile;
import com.ssafy.sub.dto.DBProfile;
import com.ssafy.sub.exception.FileStorageException;
import com.ssafy.sub.exception.MyFileNotFoundException;
import com.ssafy.sub.model.response.ResponseMessage;
import com.ssafy.sub.model.response.Result;
import com.ssafy.sub.model.response.StatusCode;
import com.ssafy.sub.model.response.UploadFileResponse;
import com.ssafy.sub.service.FileStorageService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/files")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileStorageService fileStorageService;

//	// 프로필
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
//	@PostMapping("/upload/profile")
//	public UploadFileResponse uploadProfile(@RequestParam("file") MultipartFile file) throws FileStorageException {
//
//		String id;
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		id = authentication.getName();
//		System.out.println("id : " + id);
//		DBProfile dbProfile = fileStorageService.storeProfile(file, id);
//
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
//				.path(Integer.toString(dbProfile.getId())).toUriString();
//
//		return new UploadFileResponse(dbProfile.getName(), fileDownloadUri, file.getContentType(), file.getSize());
//	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
	@PostMapping("/upload/multipleFiles")
	public ResponseEntity<Result> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
			@RequestParam int fid) throws FileStorageException {

		 
		List<String> fileNameList = new ArrayList<String>();
		for (MultipartFile multipartFile : files) {
			String fileName = uploadFile(multipartFile, fid);
			fileNameList.add(fileName);
		}
//		return Arrays.asList(files).stream().map(file -> {
//			try {
//				return 
//			} catch (FileStorageException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}).collect(Collectors.toList());
		
		Result result = new Result(StatusCode.CREATED, ResponseMessage.CREATE_FILE, fileNameList);
		return new ResponseEntity<Result>(result, HttpStatus.CREATED);
	}

//	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fid") int fid) throws FileStorageException {
	public String uploadFile(MultipartFile file, int fid)
			throws FileStorageException {
//		DBFile dbFile = fileStorageService.storeFile(file, fid);
		System.out.println("fid === ");
		String fileName = fileStorageService.storeFile(file, fid);

//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
//				.path(Integer.toString(dbFile.getId())).toUriString();

//		return new UploadFileResponse(dbFile.getName(), fileDownloadUri, file.getContentType(), file.getSize());
		//Result result = new Result(StatusCode.CREATED, ResponseMessage.CREATE_FILE, fileName);
		return fileName;
	}
	

//	@GetMapping("/download/{fid}/{order}")
//	public ResponseEntity<Resource> downloadFile(@PathVariable int fid, @PathVariable int order)
//			throws MyFileNotFoundException {
//		// Load file from database
//		System.out.println("fid" + fid);
//		System.out.println("ord" + order);
//		List<DBFile> dbFileList = fileStorageService.getFile(fid);
//
//		DBFile choicedFile = dbFileList.get(order);
//		return ResponseEntity.ok().contentType(MediaType.parseMediaType(choicedFile.getType()))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + choicedFile.getName() + "\"")
//				.body(new ByteArrayResource(choicedFile.getData()));
//	}

}