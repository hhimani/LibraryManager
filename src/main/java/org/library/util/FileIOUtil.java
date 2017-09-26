package org.library.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

public final class FileIOUtil {
	
	private FileIOUtil(){
		throw new RuntimeException();
	}
	
	//returns String encodedImage if filepath is given as input
	public static String displayImage(String imagePath){
		try{
			FileInputStream fis=new FileInputStream(imagePath);
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
			return encodedString;
			
		}
		//TODO bad idea to return null in case of exception
		catch(Exception e)
		{
			return null;
		}
		
	}
	//Return the serverfile path so that Db can use it to store the reference
	public static String addImageToDirectory(MultipartFile file){
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
			return serverFile.getAbsolutePath();
			}
			catch(Exception e){
				return null;
			}
		}
		else
			return null;
	}
	
	//delete image from database return true if it is successfully deleted
	public static boolean deleteImage(String imagePath){
		boolean hasDeleted;
		try{
		File file=new File(imagePath);
		hasDeleted=file.delete();
		}
		catch(Exception e){
			return false;
		}
		return hasDeleted;
	}
}