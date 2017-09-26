package org.library.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.library.login.model.LibUser;
import org.library.login.model.LibUserDetails;
import org.library.login.service.*;
import org.library.upload.service.FileUploadService;
import org.library.util.FileIOUtil;

@Controller
public class LibLoginController {
	@Autowired
	private UserService userService;

	@Autowired
	private FileUploadService fileUploadService;
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	//Display Image icon
	@ModelAttribute("image")
	public String displayPicIcon(HttpSession session,Model m){
		LibUser user=(LibUser)session.getAttribute("user");
		if(user==null)
			return null;
		String imagePath=fileUploadService.retrieveImagePath(user.getUserName());
		String image=FileIOUtil.displayImage(imagePath);
		return image;
	}
	
	@RequestMapping(value="/signup",method=RequestMethod.GET)
	public String signUp(Model m){
		m.addAttribute("msg", "Welcome to Account Sign up");
		return "signup";
	}
	
	@RequestMapping(value="/createAccount", method=RequestMethod.POST)
	public String createAccount(@RequestParam String userName,@RequestParam String password,Model m,
			@RequestParam String firstName,@RequestParam String lastName,@RequestParam String address,
			@RequestParam int age, @RequestParam long mobileNo,@RequestParam String email){
		//LibUser user
		LibUser user=new LibUser();
		user.setUserName(userName);
		user.setPassword(password);
		//LibUserDetails
		LibUserDetails userDetails=new LibUserDetails();
		userDetails.setAddress(address);
		userDetails.setAge(age);
		userDetails.setEmail(email);
		userDetails.setFirstName(firstName);
		userDetails.setLastName(lastName);
		userDetails.setMobileNo(mobileNo);
		
		if(userService.createUser(user,userDetails)){
			m.addAttribute("msg","User Account created, Choose what to do next!");
		return "index";
		}
		else{
			m.addAttribute("msg","User Name exists, Choose another user name!");
			return "signup";
		}
	}
	
	@RequestMapping(value="/signin",method=RequestMethod.GET)
	public String loginPage(Model m){
		m.addAttribute("msg","Please enter login credentials");
		return "login";
	}

	@RequestMapping(value="/validateSignIn",method=RequestMethod.POST)// it should first check that it has already logged
	public ModelAndView validateSignIn(@RequestParam String userName,@RequestParam String password,HttpServletRequest request){
	ModelAndView mav;
	LibUser user=new LibUser();
	user.setUserName(userName);
	user.setPassword(password);
	
	if(!userService.validateLogin(user)){
		mav=new ModelAndView("login");
		mav.addObject("msg","Wrong UserName or Password." +"Please enter correct login credentials"+"<br> Or SignUp if New User");
		return mav;
	}
	HttpSession session=request.getSession();
	mav=new ModelAndView("index");
	mav.addObject("msg","Welcome!");
	session.setAttribute("user", user);// need to check if it is allowed // who will feed the JSP the user value??
	session.setMaxInactiveInterval(1*3600);
	mav.addObject("user", user);
	return mav;
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		session.removeAttribute("user");
		return "index";
	}
	//if somebody stays at profile page even after logging out then clicking at profile should return index
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String myProfile(HttpSession session,Model m){
		if(session.getAttribute("user")!=null){
			//add image as well for the user
			LibUser user=(LibUser)session.getAttribute("user");
			String imagePath=fileUploadService.retrieveImagePath(user.getUserName());
			
			//handle a case when user has not uploaded any image yet
			if(imagePath==null){
				m.addAttribute("uploadMsg", "Please Click Upload <br>to Upload an Image");
				return "profile";
			}
				
			//logic to display file
		/*	try{
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
			
			m.addAttribute("image", encodedString);
			}
			catch(Exception E){
			m.addAttribute("uploadMsg", "Error in displaying Image");
			}*/
			String encodedString = FileIOUtil.displayImage(imagePath);
			if(encodedString!=null)
				m.addAttribute("image", encodedString);
			else
				m.addAttribute("uploadMsg", "Error in displaying Image");
			
		return "profile";
		}
		else return "index";
	}
	@RequestMapping(value="/updateAccount", method=RequestMethod.GET)
	public ModelAndView updateAccount(HttpSession session){
		LibUser user=(LibUser)session.getAttribute("user");//only userName is fetched from here
		//We need to fetch the userId and other relevant details
		if(user!=null)
			System.out.println(user.getUserName());
		LibUser dbUser=userService.fetchLibUser(user.getUserName());
		
		LibUserDetails libUserDetails=userService.fetchLibUserDetails(dbUser.getId());
		
		ModelAndView mav= new ModelAndView("updateAccount");
		
		
		if(libUserDetails==null)
			mav.addObject("msg", "No object fetched, User Id is :"+dbUser.getId());
		else
			mav.addObject("msg", "User Details are Fetched,You can also update them");
		
		mav.addObject("libUserDetails",libUserDetails);
		return mav;
	}
	
	@RequestMapping(value="/updated", method=RequestMethod.POST)
	public ModelAndView updatedAccount(Model m,
			@RequestParam String firstName,@RequestParam String lastName,@RequestParam String address,
			@RequestParam int age, @RequestParam long mobileNo,@RequestParam String email,
			/*@ModelAttribute("libUserDetails") LibUserDetails libUserDetails,*/HttpSession session){
		LibUser user=(LibUser)session.getAttribute("user");
		
		LibUser dbUser=userService.fetchLibUser(user.getUserName());
		
		LibUserDetails lud=new LibUserDetails();
		lud.setId(dbUser.getId());
		lud.setFirstName(firstName);
		lud.setLastName(lastName);
		lud.setAge(age);
		lud.setEmail(email);
		lud.setMobileNo(mobileNo);
		lud.setAddress(address);
		
		lud.setLibUser(dbUser);
		
		LibUserDetails libUserDetails=userService.fetchLibUserDetails(dbUser.getId());
		//logic to check if user detail is existing , if not save it and if yes then update it
		
		ModelAndView mav= new ModelAndView("updateAccount");
		mav.addObject("libUserDetails",lud);
		
		if(libUserDetails==null){
			userService.saveLibUserDetails(lud);
			mav.addObject("msg","Account Details Saved!");
		}
		else{
			userService.updateLibUserDetails(lud);
			mav.addObject("msg","Account Details Updated");
		}
		
		return mav;
	}
	
}
