package org.library.upload.Controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.library.login.model.LibUser;
import org.library.upload.model.FileUpload;
import org.library.upload.service.FileUploadService;
import org.library.util.FileIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FileUploadController {
	

	@Autowired
	private FileUploadService fileUploadService;
	
	@ModelAttribute("image")
	public String displayPicIcon(HttpSession session,Model m){
		LibUser user=(LibUser)session.getAttribute("user");
		if(user==null)
			return null;
		String imagePath=fileUploadService.retrieveImagePath(user.getUserName());
		String image=FileIOUtil.displayImage(imagePath);
		return image;
	}
	
	@RequestMapping(value="/uploadPic",method=RequestMethod.POST)
	public String upload(@RequestParam MultipartFile file,Model m,HttpSession session){
		if(!file.isEmpty()){
			String imagePath=FileIOUtil.addImageToDirectory(file);
			//saving reference in DB
			FileUpload fileUpload=new FileUpload();
			fileUpload.setImagePath(imagePath);
			LibUser user=(LibUser)session.getAttribute("user");
			String userName=user.getUserName();
			fileUpload.setUserName(userName);
			if(/*fileUploadService.retrieveImagePath(userName)==null*/
					fileUploadService.retrieve(userName)==null)//logic was incorrect 
				fileUploadService.save(fileUpload);
			else{
				//1. Delete old image 2. Update new image in DB and Server
				String OldImagePath=fileUploadService.retrieveImagePath(userName);
				FileIOUtil.deleteImage(OldImagePath);
				fileUploadService.update(fileUpload);
				
			}
			String encodedString = FileIOUtil.displayImage(imagePath);
			m.addAttribute("image", encodedString);
			m.addAttribute("uploadMsg","Uploaded!!");
		}
		else
			m.addAttribute("uploadMsg", "Failed!");
		return "profile";
	}
	
	//1. Delete file reference from the database 2. Delete file from the server as well
	@RequestMapping(value="/deletePic",method=RequestMethod.POST)
	public String deletePic(String userName/*,HttpSession session*/,Model m){
		/*LibUser user=(LibUser)session.getAttribute("user");
		String userName=user.getUserName();*/
		
		//boolean hasDeleted=fileUploadService.delete(userName);
		String imagePath=fileUploadService.retrieveImagePath(userName);
		//File I/O for delete
		boolean hasDeleted=FileIOUtil.deleteImage(imagePath);
		
		if(hasDeleted==true){
			FileUpload fileUpload=fileUploadService.retrieve(userName);
			fileUpload.setImagePath(null);
			fileUploadService.update(fileUpload);
			m.addAttribute("uploadMsg","Deleted!!");
		}
		else
			m.addAttribute("uploadMsg","Error in deleting,Try Again!!");
		return "profile";
	}
	
/*	@RequestMapping(value="/updatePic",method=RequestMethod.POST)
	public String updatePic(@RequestParam MultipartFile file,Model m,HttpSession session){
		if(!file.isEmpty()){
			try{
			byte[] bytes=file.getBytes();
			String path="/f:/tmpfiles";
			File dir=new File(path);
			if(!dir.exists())
				dir.mkdirs();
			String name=file.getOriginalFilename();
			File serverFile=new File(dir.getAbsolutePath()+File.separator+name);
			
			BufferedOutputStream stream=new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
			//saving reference in DB
			FileUpload fileUpload=new FileUpload();
			fileUpload.setImagePath(serverFile.getAbsolutePath());
			LibUser user=(LibUser)session.getAttribute("user");
			String userName=user.getUserName();
			fileUpload.setUserName(userName);
			if(fileUploadService.retrieveImagePath(userName)==null)
				fileUploadService.save(fileUpload);
			else
				fileUploadService.update(fileUpload);
			//displaying image just after uploading
			FileInputStream fis=new FileInputStream(serverFile.getAbsolutePath());
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			int b;
			byte[] buffer = new byte[1024];
			while((b=fis.read(buffer))!=-1){
			   bos.write(buffer,0,b);
			}
			byte[] fileBytes=bos.toByteArray();
			fis.close();
			bos.close();

			byte[] encoded=Base64.encodeBase64(fileBytes);
			String encodedString = new String(encoded);

			m.addAttribute("image", encodedString);
			
			m.addAttribute("uploadMsg","Uploaded!!");}
		catch(Exception e){
			m.addAttribute("uploadMsg", "Failed!");
		}
		}
		return "profile";
	}*/
}