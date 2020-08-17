package com.ssafy.sub.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

@Service("FileStorageService")
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
	 * 
	 * @param file
	 * @param text
	 * @param uid
	 * @param coordi 
	 * @return DBProfile (저장 or 변환된 프로필 정보)
	 * @throws FileStorageException
	 * @throws IOException 
	 */
	@Transactional
	public DBProfile storeProfile(MultipartFile file, String text, String uid, String coordi) throws FileStorageException, IOException {

		Optional<DBProfile> updateProfile = dbProfileRepository.findByUid(uid);
		String extension = file.getContentType().split("/")[1];

		int x, y, width, height, oldW, oldH;
		x = y = 0;
		width = height = 400;
		boolean isCoordi = false;
		if (extension.equals("jpeg") || extension.equals("png") || extension.equals("tiff")
				|| extension.equals("jfif")) {
			BufferedImage image = ImageIO.read(file.getInputStream());

			width = oldW = image.getWidth();
			height = oldH = image.getHeight();
		}

		if (!coordi.equals("")) { // 크롭 한 경우,
			isCoordi = true;
			String parse;
			parse = coordi.replace("{", "");
			parse = coordi.replace("{", "");
			String[] coordis = parse.split(",");
			List<String> pos = new ArrayList<String>();
			for (String string : coordis) {
				pos.add(string.split(":")[1]);
			}
			x = (int) Float.parseFloat(pos.get(0));
			y = (int) Float.parseFloat(pos.get(1));
			width = (int) Float.parseFloat(pos.get(2));
			height = (int) Float.parseFloat(pos.get(3));
		} else {
			if (width >= height && width > 400) {
				height = (int) (height * (400 / (double) width));
				width = 400;
			} else if (width <= height && height > 400) {
				width = (int) (width * (400 / (double) height));
				height = 400;
			} else if (width < 400 && width >= height) {
				height = (int) (height * (400 / (double) width));
				width = 400;
			} else if (width <= height && height < 400) {
				width = (int) (width * (400 / (double) height));
				height = 400;
			}
		}
		String filename = null;

		if (!updateProfile.isPresent()) {

			///
			int pos = file.getOriginalFilename().lastIndexOf(".");
			String format = file.getOriginalFilename().substring(pos);

			filename = UUID.randomUUID() + format;

			if (extension.equals("jpeg") || extension.equals("png") || extension.equals("tiff")
					|| extension.equals("jfif")) {

				File out = new File(filePath + File.separator + filename);
				if (isCoordi) {
					BufferedImage img = resize(file.getInputStream(), x, y, width, height);
					BufferedImage img2 = resize(img, 400, 400);
					ImageIO.write(img2, extension, out);
				} else {
					System.out.println("no크롭");
					BufferedImage img = resize(file.getInputStream(), width, height);
					ImageIO.write(img, extension, out);
				}

			} else {
				Files.copy(file.getInputStream(), Paths.get(filePath).resolve(filename));
			}

			///
			DBProfile dbProfile = DBProfile.builder().uid(uid).name(filename).type(file.getContentType()).text(text)
					.build();
			return dbProfileRepository.save(dbProfile);
		} else {
			File localfile = new File(filePath + File.separator + updateProfile.get().getName());
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
			///
			int pos = file.getOriginalFilename().lastIndexOf(".");
			String format = file.getOriginalFilename().substring(pos);

			filename = UUID.randomUUID() + format;

			if (extension.equals("jpeg") || extension.equals("png") || extension.equals("tiff")
					|| extension.equals("jfif")) {

				File out = new File(filePath + File.separator + filename);
				if (isCoordi) {
					BufferedImage img = resize(file.getInputStream(), x, y, width, height);
					BufferedImage img2 = resize(img, 400, 400);
					ImageIO.write(img2, extension, out);
				} else {
					System.out.println("no크롭");
					BufferedImage img = resize(file.getInputStream(), width, height);
					ImageIO.write(img, extension, out);
				}

			} else {
				Files.copy(file.getInputStream(), Paths.get(filePath).resolve(filename));
			}

			///
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
	 * 
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
	 * 
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
	 * 
	 * @param file
	 * @param fid
	 * @param coordi
	 * @return String (저장된 파일명)
	 * @throws FileStorageException
	 * @throws IOException
	 */
	public String storeFile(MultipartFile file, int fid, String coordi) throws FileStorageException, IOException {

		String extension = file.getContentType().split("/")[1];

		int x, y, width, height, oldW, oldH;
		x = y = 0;
		width = height = 400;
		boolean isCoordi = false;
		if (extension.equals("jpeg") || extension.equals("png") || extension.equals("tiff")
				|| extension.equals("jfif")) {
			BufferedImage image = ImageIO.read(file.getInputStream());

			width = oldW = image.getWidth();
			height = oldH = image.getHeight();
		}
		if (!coordi.equals("")) { // 크롭 한 경우,
			isCoordi = true;
			String parse;
			parse = coordi.replace("{", "");
			parse = coordi.replace("{", "");
			String[] coordis = parse.split(",");
			List<String> pos = new ArrayList<String>();
			for (String string : coordis) {
				pos.add(string.split(":")[1]);
			}
			x = (int) Float.parseFloat(pos.get(0));
			y = (int) Float.parseFloat(pos.get(1));
			width = (int) Float.parseFloat(pos.get(2));
			height = (int) Float.parseFloat(pos.get(3));
		} else {
			if (width >= height && width > 400) {
				height = (int) (height * (400 / (double) width));
				width = 400;
			} else if (width <= height && height > 400) {
				width = (int) (width * (400 / (double) height));
				height = 400;
			} else if (width < 400 && width >= height) {
				height = (int) (height * (400 / (double) width));
				width = 400;
			} else if (width <= height && height < 400) {
				width = (int) (width * (400 / (double) height));
				height = 400;
			}
		}
		String result = null;
		try {
			int pos = file.getOriginalFilename().lastIndexOf(".");
			String format = file.getOriginalFilename().substring(pos);

			result = UUID.randomUUID() + format;

			if (extension.equals("jpeg") || extension.equals("png") || extension.equals("tiff")
					|| extension.equals("jfif")) {

				File out = new File(filePath + File.separator + result);
				if (isCoordi) {
					BufferedImage img = resize(file.getInputStream(), x, y, width, height);
					BufferedImage img2 = resize(img, 400, 400);
					ImageIO.write(img2, extension, out);
				} else {
					System.out.println("no크롭");
					BufferedImage img = resize(file.getInputStream(), width, height);
					ImageIO.write(img, extension, out);
				}

			} else {
				Files.copy(file.getInputStream(), Paths.get(filePath).resolve(result));
			}

			dbFileRepository.save(new DBFile(fid, result, file.getContentType()));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		return result;
	}

	public static BufferedImage resize(InputStream inputStream, int width, int height) throws IOException {
		BufferedImage inputImage = ImageIO.read(inputStream);
		BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType()); // 사진틀
		Graphics2D graphics2D = outputImage.createGraphics();
		graphics2D.drawImage(inputImage, 0, 0, width, height, null); // 그릴 곳
		graphics2D.dispose();

		return outputImage;
	}

	public static BufferedImage resize(InputStream inputStream, int x, int y, int width, int height)
			throws IOException {
		BufferedImage inputImage = ImageIO.read(inputStream);
		int oldW, oldH;
		oldW = inputImage.getWidth();
		oldH = inputImage.getHeight();
		BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType()); // 사진틀
		Graphics2D graphics2D = outputImage.createGraphics();
		graphics2D.drawImage(inputImage, -x, -y, oldW, oldH, null); // 그릴 곳
		graphics2D.dispose();

		return outputImage;
	}

	/***
	 * 사이즈 지정값(400x400) 이미지 리사이징 기능
	 * 
	 * @param inputStream
	 * @param width
	 * @param height
	 * @return BufferedImage
	 * @throws IOException
	 */
	public static BufferedImage resize(BufferedImage inputImg, int width, int height) throws IOException {
		BufferedImage inputImage = inputImg;
		BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());
		Graphics2D graphics2D = outputImage.createGraphics();
		graphics2D.drawImage(inputImage, 0, 0, width, height, null);
		graphics2D.dispose();

		return outputImage;
	}

	/***
	 * fid(feed pk)의 DBFile list 정보 호출
	 * 
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
	 * 
	 * @param file
	 * @param fid
	 * @return ContestFeedFiles
	 * @throws FileStorageException
	 * @throws IOException 
	 */
	public ContestFeedFiles storeContestFile(MultipartFile file, int fid, String coordi) throws FileStorageException, IOException {


		String extension = file.getContentType().split("/")[1];
		ContestFeedFiles cfFiles = null;

		int x, y, width, height, oldW, oldH;
		x = y = 0;
		width = height = 400;
		boolean isCoordi = false;
		if (extension.equals("jpeg") || extension.equals("png") || extension.equals("tiff")
				|| extension.equals("jfif")) {
			BufferedImage image = ImageIO.read(file.getInputStream());

			width = oldW = image.getWidth();
			height = oldH = image.getHeight();
		}
		if (!coordi.equals("")) { // 크롭 한 경우,
			isCoordi = true;
			String parse;
			parse = coordi.replace("{", "");
			parse = coordi.replace("{", "");
			String[] coordis = parse.split(",");
			List<String> pos = new ArrayList<String>();
			for (String string : coordis) {
				pos.add(string.split(":")[1]);
			}
			x = (int) Float.parseFloat(pos.get(0));
			y = (int) Float.parseFloat(pos.get(1));
			width = (int) Float.parseFloat(pos.get(2));
			height = (int) Float.parseFloat(pos.get(3));
		} else {
			if (width >= height && width > 400) {
				height = (int) (height * (400 / (double) width));
				width = 400;
			} else if (width <= height && height > 400) {
				width = (int) (width * (400 / (double) height));
				height = 400;
			} else if (width < 400 && width >= height) {
				height = (int) (height * (400 / (double) width));
				width = 400;
			} else if (width <= height && height < 400) {
				width = (int) (width * (400 / (double) height));
				height = 400;
			}
		}
		String result = null;
		try {
			int pos = file.getOriginalFilename().lastIndexOf(".");
			String format = file.getOriginalFilename().substring(pos);

			result = UUID.randomUUID() + format;

			if (extension.equals("jpeg") || extension.equals("png") || extension.equals("tiff")
					|| extension.equals("jfif")) {

				File out = new File(filePath + File.separator + result);
				if (isCoordi) {
					BufferedImage img = resize(file.getInputStream(), x, y, width, height);
					BufferedImage img2 = resize(img, 400, 400);
					ImageIO.write(img2, extension, out);
				} else {
					System.out.println("no크롭");
					BufferedImage img = resize(file.getInputStream(), width, height);
					ImageIO.write(img, extension, out);
				}
			} else {
				Files.copy(file.getInputStream(), Paths.get(filePath).resolve(result));
			}
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		cfFiles = ContestFeedFiles.builder().cfid(fid).name(result).type(file.getContentType()).build();
		return contestFeedFilesRepository.save(cfFiles);
		
	}

	/***
	 * fid(contestfeed pk)의 ContestFeedFiles list 정보 호출
	 * 
	 * @param fid
	 * @return List<ContestFeedFiles>
	 * @throws MyFileNotFoundException
	 */
	public List<ContestFeedFiles> getContestFiles(int fid) throws MyFileNotFoundException {
		return contestFeedFilesRepository.findAllByCfid(fid);
	}
}