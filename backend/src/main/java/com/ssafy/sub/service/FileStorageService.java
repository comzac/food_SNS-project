package com.ssafy.sub.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;
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

/**
 * 유저의 프로필 및 피드의 multipartfile 업로드 관리
 * 
 * @author 이선수
 * @version 1.0, 파일업로드 형식 변환 및 파일 저장 전 리사이징 처리
 */
@Service
public class FileStorageService {

	@Autowired
	private DBFileRepository dbFileRepository; // 피드 multipartfile 관리 repo
	@Autowired
	private DBProfileRepository dbProfileRepository; // 프로필 multipartfile 관리 repo
	@Autowired
	private ContestFeedFilesRepository contestFeedFilesRepository; // 콘테스트 multipartfile 관리 repo

	@Value("${spring.file.location}") // multipartfile 로컬 저장소 경로 (.yml 내)
	private String filePath;

	/***
	 * 유저프로필의 문구 및 프로필 사진 저장 기능
	 * @param file
	 * @param text
	 * @param uid
	 * @return DBProfile (저장 or 변환된 프로필 정보)
	 * @throws FileStorageException
	 */
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

	/***
	 * uid에 해당하는 프로필 정보에 이미지 데이터 유무 확인
	 * @param uid
	 * @return boolean
	 */
	public boolean hasProfile(String uid) {
		DBProfile dbProfile = dbProfileRepository.findByUid(uid).get();
		if (dbProfile.getName() != null)
			return true;
		else
			return false;
	}

	/***
	 * 유저프로필의 문구 및 프로필 사진 업데이트 기능
	 * @param text
	 * @param uid
	 * @param hasImage
	 * @return DBProfile
	 * @throws FileStorageException
	 */
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

	/***
	 * multipartfile 로컬 저장 기능
	 * @param file
	 * @param fid
	 * @return String (저장된 파일명)
	 * @throws FileStorageException
	 */
	public String storeFile(MultipartFile file, int fid) throws FileStorageException {
	
		String result = null;
		try {
			int pos = file.getOriginalFilename().lastIndexOf(".");
			String format = file.getOriginalFilename().substring(pos);
			result = UUID.randomUUID() + format;

			String extension = file.getContentType().split("/")[1];
			if(extension.equals("jpeg") || extension.equals("png") || extension.equals("tiff")) {
				BufferedImage img = resize(file.getInputStream(), 400, 400);
				File out = new File(filePath+File.separator+ result);
				ImageIO.write(img, extension, out);
			}else {
				Files.copy(file.getInputStream(), Paths.get(filePath).resolve(result));				
			}
			
			dbFileRepository.save(new DBFile(fid, result, file.getContentType()));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		return result;
	}

	/***
	 * 사이즈 지정값(400x400) 이미지 리사이징 기능
	 * @param inputStream
	 * @param width
	 * @param height
	 * @return BufferedImage
	 * @throws IOException
	 */
    public static BufferedImage resize(InputStream inputStream, int width, int height)
            throws IOException {
        BufferedImage inputImage = ImageIO.read(inputStream);

        BufferedImage outputImage =
                new BufferedImage(width, height, inputImage.getType());

        Graphics2D graphics2D = outputImage.createGraphics();
        graphics2D.drawImage(inputImage, 0, 0, width, height, null);
        graphics2D.dispose();

        return outputImage;
    }
    
    /***
     * fid(feed pk)의 DBFile list 정보 호출
     * @param fid
     * @return List<DBFile>
     * @throws MyFileNotFoundException
     */
	public List<DBFile> getFile(int fid) throws MyFileNotFoundException {
		return dbFileRepository.findAllByFid(fid)
				.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fid));
	}

	/***
	 * 콘테스트 피드 multipartfile 저장
	 * @param file
	 * @param fid
	 * @return ContestFeedFiles
	 * @throws FileStorageException
	 */
	public ContestFeedFiles storeContestFile(MultipartFile file, int fid) throws FileStorageException {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			ContestFeedFiles cfFiles = ContestFeedFiles.builder().cfid(fid).name(fileName).type(file.getContentType())
					.data(file.getBytes()).build();

			return contestFeedFilesRepository.save(cfFiles);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	/***
	 * fid(contestfeed pk)의 ContestFeedFiles list 정보 호출
	 * @param fid
	 * @return List<ContestFeedFiles>
	 * @throws MyFileNotFoundException
	 */
	public List<ContestFeedFiles> getContestFiles(int fid) throws MyFileNotFoundException {
		return contestFeedFilesRepository.findAllByCfid(fid);
	}
}