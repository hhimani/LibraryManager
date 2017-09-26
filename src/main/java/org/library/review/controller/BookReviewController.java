package org.library.review.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.library.issueAndFine.model.BookIssueFine;
import org.library.issueAndFine.service.BookIssueFineService;
import org.library.login.model.LibUser;
import org.library.manager.model.Book;
import org.library.manager.service.BookService;
import org.library.review.model.BookReview;
import org.library.review.service.BookReviewService;
import org.library.upload.service.FileUploadService;
import org.library.util.FileIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookReviewController {

	@Autowired
	private BookReviewService bookreviewService;

	public BookReviewService getBookreviewService() {
		return bookreviewService;
	}

	public void setBookreviewService(BookReviewService bookreviewService) {
		this.bookreviewService = bookreviewService;
	}
		
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookIssueFineService bookissuefineService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	// Display Image icon
	@ModelAttribute("image")
	public String displayPicIcon(HttpSession session, Model m) {
		LibUser user = (LibUser) session.getAttribute("user");
		if (user == null)
			return null;
		String imagePath = fileUploadService.retrieveImagePath(user.getUserName());
		String image = FileIOUtil.displayImage(imagePath);
		return image;
	}
	
	@RequestMapping(value="/reviewPage")
	public String reviewPage(){
		return "review";
	}
	@RequestMapping(value="/reviewTitle", method=RequestMethod.POST )
	public String reviewTitle(String title,Model m){
		List<BookReview> list=bookreviewService.fetchReviewByTitle(title);
		if(list==null){
			m.addAttribute("msg","No Reviews fetched");
			return "review";
		}
		else{
			m.addAttribute("list", list);
			return "review";
		}
	}
	@RequestMapping(value="/reviewAuthor", method=RequestMethod.POST )
	public String reviewAuthor(String author,Model m){
		List<BookReview> list=bookreviewService.fetchReviewByAuthor(author);
		if(list==null){
			m.addAttribute("msg","No Reviews fetched for the Entered Author");
			return "review";
		}
		else{
			m.addAttribute("list", list);
			return "review";
		}
	}
	@RequestMapping(value="/reviewUser", method=RequestMethod.GET )
	public String reviewAuthor(Model m,HttpSession session ){
		LibUser user= (LibUser)session.getAttribute("user");
		String userName=user.getUserName();
		List<BookReview> list=bookreviewService.fetchReviewByUser(userName);
		if(list==null){
			m.addAttribute("msg","No Reviews fetched for the Entered Title");
			return "review";
		}
		else{
			m.addAttribute("list", list);
			return "review";
		}
	}
	
	//when a user adds review, reflect the change in RATING of the book in BOOK DB
	@RequestMapping(value="/addReview",method=RequestMethod.POST)
	public ModelAndView addUserReview(@RequestParam("review") String review, @RequestParam("rating") int rating
			,@RequestParam("userName") String userName,@RequestParam("title") String title,@RequestParam("author") String author){
		BookReview br=new BookReview();
		br.setTitle(title);
		br.setUserName(userName);
		br.setRating(rating);
		br.setReview(review);
		br.setAuthor(author);
		Calendar c=Calendar.getInstance();
		Date reviewDate=c.getTime();
		br.setReviewDate(reviewDate);
		bookreviewService.addReview(br);
		//setting rating for book
		Book book=bookService.searchByTitle(title);
		List<BookReview> listre=bookreviewService.fetchReviewByTitle(title);
		int countReview=listre.size();
		int pr=book.getRating();
		int i=pr*countReview+rating;
		int j=(countReview+1);
		int nr=i/j;
		book.setRating(nr);
		bookService.updateBook(book);
		//**********************//
		//setting reviewed tag for BookIssueFine
		BookIssueFine b=bookissuefineService.fetchTitle(userName, title);
		b.setReviewed(true);
		b.setUserName(userName);
		bookissuefineService.updateBookIssueFine(b);
		//****************************************
		ModelAndView mav=new ModelAndView("review");
		List<BookReview> list=bookreviewService.fetchReviewByUser(userName);
		mav.addObject("list",list);
		return mav;
	}
}
