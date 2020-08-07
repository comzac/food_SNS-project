package com.ssafy.sub.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sub.dto.ContestFeedFiles;
import com.ssafy.sub.dto.DBFile;
import com.ssafy.sub.dto.DBProfile;
import com.ssafy.sub.exception.FileStorageException;
import com.ssafy.sub.exception.MyFileNotFoundException;
import com.ssafy.sub.repo.ContestFeedFilesRepository;
import com.ssafy.sub.repo.DBFileRepository;
import com.ssafy.sub.repo.DBProfileRepository;

@Service("FileStorageService")
public class FileStorageService {

	@Autowired
	private DBFileRepository dbFileRepository;
	@Autowired
	private DBProfileRepository dbProfileRepository;
	@Autowired
	private ContestFeedFilesRepository contestFeedFilesRepository;

	@Value("${spring.file.location}")
	private String filePath;

	@Transactional
	public DBProfile storeProfile(MultipartFile file, String text, String uid) throws FileStorageException {

		Optional<DBProfile> updateProfile = dbProfileRepository.findByUid(uid);

		String filename = null;
		
		if (!updateProfile.isPresent()) {

			try {
				int pos = file.getOriginalFilename().lastIndexOf(".");
				String format = file.getOriginalFilename().substring(pos);
				filename = UUID.randomUUID() + format;
				Files.copy(file.getInputStream(), Paths.get(filePath).resolve(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
			DBProfile dbProfile = DBProfile.builder().uid(uid).name(filename).type(file.getContentType()).text(text)
					.build();
			return dbProfileRepository.save(dbProfile);
		} else {
			File localfile = new File(filePath + File.separator+ updateProfile.get().getName());
			System.out.println(localfile.getPath());
			if (localfile.exists()) {
				if (localfile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			} else {
				System.out.println("파일이 존재하지 않습니다.");
			}
			try {
				int pos = file.getOriginalFilename().lastIndexOf(".");
				String format = file.getOriginalFilename().substring(pos);
				filename = UUID.randomUUID() + format;
				Files.copy(file.getInputStream(), Paths.get(filePath).resolve(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
			DBProfile dbProfile = DBProfile.builder().uid(uid).name(filename).type(file.getContentType()).text(text)
					.build();
			updateProfile.get().setUid(uid);
			updateProfile.get().setName(dbProfile.getName());
			updateProfile.get().setType(dbProfile.getType());
			updateProfile.get().setText(dbProfile.getText());

			return updateProfile.get();
		}
	}

	public boolean hasProfile(String uid) {
		DBProfile dbProfile = dbProfileRepository.findByUid(uid).get();
		if (dbProfile.getName() != null)
			return true;
		else
			return false;
	}

	@Transactional
	public DBProfile updateProfile(String text, String uid, boolean hasImage) throws FileStorageException {
		DBProfile dbProfile;
		Optional<DBProfile> updateProfile = dbProfileRepository.findByUid(uid);
		if (!hasImage) {
			dbProfile = DBProfile.builder().uid(uid).name("").type("").text(text).build();
		} else {
			dbProfile = DBProfile.builder().uid(uid).text(text).build();
			updateProfile.get().setText(text);
			return dbProfileRepository.save(updateProfile.get());
		}

		if (!updateProfile.isPresent()) {
			return dbProfileRepository.save(dbProfile);
		} else {
			if (updateProfile.get().getName().length() > 0) {
				dbProfile = DBProfile.builder().uid(uid).name("").type("").text(text).build();
				updateProfile.get().setName(dbProfile.getName());
				updateProfile.get().setType(dbProfile.getType());
			}
			updateProfile.get().setText(dbProfile.getText());
			updateProfile.get().setUid(uid);
			return updateProfile.get();
		}
	}

	public String storeFile(MultipartFile file, int fid) throws FileStorageException {
		// Normalize file name
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//
//        try {
//            // Check if the file's name contains invalid characters
//            if(fileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//            } 
//
//            DBFile dbFile = new DBFile(fid, fileName, file.getContentType(), file.getBytes());
//
//            return dbFileRepository.save(dbFile);
//        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
//        }
		String result = null;
		try {
			int pos = file.getOriginalFilename().lastIndexOf(".");
			String format = file.getOriginalFilename().substring(pos);
			result = UUID.randomUUID() + format;
			Files.copy(file.getInputStream(), Paths.get(filePath).resolve(result));
			dbFileRepository.save(new DBFile(fid, result, file.getContentType()));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		return result;
	}

	public List<DBFile> getFile(int fid) throws MyFileNotFoundException {
		return dbFileRepository.findAllByFid(fid)
				.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fid));
	}

	public ContestFeedFiles storeContestFile(MultipartFile file, int fid) throws FileStorageException {
		// Normalize file name
		
		String result = null;
		ContestFeedFiles cfFiles = null;
		try {
			int pos = file.getOriginalFilename().lastIndexOf(".");
			String format = file.getOriginalFilename().substring(pos);
			result = UUID.randomUUID() + format;
			Files.copy(file.getInputStream(), Paths.get(filePath).resolve(result));
			cfFiles = ContestFeedFiles.builder().cfid(fid).name(result).type(file.getContentType()).build();
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		
		return contestFeedFilesRepository.save(cfFiles);
	}

	public List<ContestFeedFiles> getContestFiles(int fid) throws MyFileNotFoundException {
		return contestFeedFilesRepository.findAllByCfid(fid);
	}
}