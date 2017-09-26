package org.library.upload.service;

import org.library.upload.dao.FileUploadDao;
import org.library.upload.model.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private FileUploadDao fileuploadDao;
	
	@Transactional
	public void save(FileUpload fileUpload) {
		fileuploadDao.save(fileUpload);
	}
	@Transactional
	public String retrieveImagePath(String userName) {
		return fileuploadDao.retrieveImagePath(userName);
	}
	@Transactional
	public boolean delete(String userName) {
		return fileuploadDao.delete(userName);
	}
	@Transactional
	public void update(FileUpload fileUpload) {
		fileuploadDao.update(fileUpload);
	}
	@Transactional
	public FileUpload retrieve(String userName) {
		return fileuploadDao.retrieve(userName);
	}

}
