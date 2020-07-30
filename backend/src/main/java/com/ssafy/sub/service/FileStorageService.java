package com.ssafy.sub.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sub.dto.DBFile;
import com.ssafy.sub.dto.DBProfile;
import com.ssafy.sub.dto.UserSimple;
import com.ssafy.sub.exception.FileStorageException;
import com.ssafy.sub.exception.MyFileNotFoundException;
import com.ssafy.sub.repo.DBFileRepository;
import com.ssafy.sub.repo.DBProfileRepository;

@Service
public class FileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;
    @Autowired
    private DBProfileRepository dbProfileRepository;
 
    @Transactional
    public DBProfile storeProfile(MultipartFile file, String text, String uid) throws FileStorageException {
       String fileName = StringUtils.cleanPath(file.getOriginalFilename());

       try {
          if (fileName.contains("..")) {
             throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
          }

          DBProfile dbProfile = DBProfile.builder().uid(uid).name(fileName).type(file.getContentType())
                .data(file.getBytes()).text(text).build();

          Optional<DBProfile> updateProfile = dbProfileRepository.findByUid(uid);
          if (!updateProfile.isPresent()) {
             return dbProfileRepository.save(dbProfile);
          } else {
             updateProfile.get().setUid(uid);
             updateProfile.get().setName(dbProfile.getName());
             updateProfile.get().setType(dbProfile.getType());
             updateProfile.get().setData(dbProfile.getData());
             updateProfile.get().setText(dbProfile.getText());
             return updateProfile.get();
          }

       } catch (IOException ex) {
             throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
         }
    }
    
    public boolean hasProfile(String uid) {
    	DBProfile dbProfile = dbProfileRepository.findByUid(uid).get();
    	if(dbProfile.getName()!=null)
    		return true;
    	else
    		return false;
    }
    
    @Transactional
    public DBProfile updateProfile(String text, String uid) throws FileStorageException {
    	DBProfile dbProfile = DBProfile.builder().uid(uid).name("").type("")
    			.data("".getBytes()).text(text).build();
    	
    	Optional<DBProfile> updateProfile = dbProfileRepository.findByUid(uid);
    	if (!updateProfile.isPresent()) {
    		return dbProfileRepository.save(dbProfile);
    	} else {
    		if(updateProfile.get().getName().length()>0) {
    			dbProfile = DBProfile.builder().uid(uid).name("").type("")
    	    			.data("".getBytes()).text(text).build();
    			updateProfile.get().setName(dbProfile.getName());
    			updateProfile.get().setType(dbProfile.getType());
    			updateProfile.get().setData(dbProfile.getData());
    		}
    		updateProfile.get().setText(dbProfile.getText());
    		updateProfile.get().setUid(uid);
    		return updateProfile.get();
    	}
    }
    
    public DBFile storeFile(MultipartFile file, int fid) throws FileStorageException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            } 

            DBFile dbFile = new DBFile(fid, fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public List<DBFile> getFile(int fid) throws MyFileNotFoundException {
        return dbFileRepository.findAllByFid(fid)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fid));
    }
}