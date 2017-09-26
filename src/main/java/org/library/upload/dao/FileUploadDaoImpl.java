package org.library.upload.dao;

import java.io.File;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.library.upload.model.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileUploadDaoImpl implements FileUploadDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void save(FileUpload fileUpload) {
		sessionFactory.getCurrentSession().save(fileUpload);
	}
	//one user will have one imagePath
	public String retrieveImagePath(String userName) {
		String hql="From FileUpload where userName=:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userName", userName);
		FileUpload fu=(FileUpload)query.uniqueResult();
		if(fu!=null)
		return fu.getImagePath();
		else return null;
	}

	//this method is not updating the imagepath to null, we are doing that using update method of DAO in controller
	//this method was earlier performing delete I/O which we are now doing in FILEIOUTIL
	public boolean delete(String userName) {
		String hql="From FileUpload where userName=:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userName", userName);
		FileUpload fu=(FileUpload)query.uniqueResult();
		String imagePath=fu.getImagePath();
		
		boolean hasDeleted;
		//deleted image Path from the database
		//They say update it in the service layer
		//delete file from server
		/*try{
			File file=new File(imagePath);
			hasDeleted=file.delete();
		}
		catch(Exception e){
			return false;
		}*/
		return true;
	}

	public void update(FileUpload fileUpload) {
		String userName=fileUpload.getUserName();
		String imagePath=fileUpload.getImagePath();
		String filePath=fileUpload.getFilePath();
		String hql="Update FileUpload set imagePath=:imagePath,filePath=:filePath where userName=:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("imagePath", imagePath);
		query.setParameter("filePath", filePath);
		query.setParameter("userName", userName);
		query.executeUpdate();
	}
	
	public FileUpload retrieve(String userName) {
		String hql="From FileUpload where userName=:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userName", userName);
		FileUpload fu=(FileUpload)query.uniqueResult();
		return fu;
	}

}
