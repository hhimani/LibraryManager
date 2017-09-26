package org.library.upload.dao;

import org.library.upload.model.*;
public interface FileUploadDao {
	
	void save(FileUpload fileUpload);
	String retrieveImagePath(String userName);
	boolean delete(String userName);
	void update(FileUpload fileUpload);
	FileUpload retrieve(String userName);
}
