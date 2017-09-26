package org.library.upload.service;

import org.library.upload.model.FileUpload;

public interface FileUploadService {

	void save(FileUpload fileUpload);
	String retrieveImagePath(String userName);
	boolean delete(String userName);
	void update(FileUpload fileUpload);
	FileUpload retrieve(String userName);
}
