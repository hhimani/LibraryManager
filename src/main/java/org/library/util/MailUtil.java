package org.library.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.library.issueAndFine.model.BookIssueFine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//add dependency of spring support for below class
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service
public final class MailUtil {
	//ommitted so that Mail Util can be declared as a Service 
	
	//so that instance cannot be created
//	private MailUtil(){
//		throw new RuntimeException();
//	}
	//method to prepare email to be sent when a user issues a book
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	public boolean sendMailOnIssueBook(String emailTo,BookIssueFine bookIssueFine,String image){
		
		String emailFrom="himani1349@gmail.com";
		String subject="Confirmation on Book Issue of Title" + bookIssueFine.getTitle();
		
		//older way of sending simple text as the message
		String message="You have issue the book "+bookIssueFine.getTitle()+"!";//Add more details once it works
		
		//newer way of sending text via velocity
		Map model = new HashMap();	             
        model.put("bookissuefine", bookIssueFine);
        model.put("image", image);
        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "velocity/bookIssueMailer.vm", "UTF-8", model);
        
		return sendMail(emailTo,emailFrom,subject,text);
	}

	private static boolean sendMail(String to,String from,String subject,String text){
		String host="smtp.gmail.com";
		try{
			MimeMessage message=new MimeMessage(getSession(host));
			message.setFrom(new InternetAddress(from));
			message.addRecipient(RecipientType.TO ,new InternetAddress(to));
			message.setSubject(subject);
			//message.setContent(content,"alternative");
			message.setText(text); //based on type of mail
			Transport.send(message);
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	return true;
	}

	private static Session getSession(String host) {
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.user", "himani1349@gmail.com");  
		properties.put("mail.smtp.password", "him@ni_308"); 
		//Session session=Session.getDefaultInstance(properties,null);
		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("himani1349@gmail.com", "him@ni_308");
			}
		});
		return session;
	}
}
