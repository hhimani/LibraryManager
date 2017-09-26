package org.library.manager.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.library.issueAndFine.model.BookIssueFine;
import org.library.issueAndFine.service.BookIssueFineService;
import org.library.login.model.LibUser;
import org.library.login.model.LibUserDetails;
import org.library.login.service.UserService;
import org.library.login.service.UserServiceImpl;
import org.library.manager.model.Book;
import org.library.manager.service.BookService;
import org.library.review.model.BookReview;
import org.library.review.service.BookReviewService;
import org.library.upload.service.FileUploadService;
import org.library.util.FileIOUtil;
import org.library.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javassist.bytecode.Descriptor.Iterator;

@Controller
@SessionAttributes("Book")
public class BookController {
	@Autowired
	private BookService bookService;
	private static final Logger logger = Logger.getLogger(BookController.class);

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	@Autowired
	private BookIssueFineService bookissuefineService;// for issue we want to
														// update
														// issue_fine_table as
														// well

	@Autowired
	private UserService userService;

	public BookIssueFineService getBookissuefineService() {
		return bookissuefineService;
	}

	public void setBookissuefineService(BookIssueFineService bookissuefineService) {
		this.bookissuefineService = bookissuefineService;
	}
	@Autowired
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	private BookReviewService bookreviewService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private MailUtil mailUtil;
	
	
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
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexPage(Model m) {
		m.addAttribute("msg", "Welcome to Library Inventory Manager");
		return "index";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Model m) {
		// m.addAttribute("msg", "Welcome to Library Search Manager");
		logger.debug("running the search method");
		return "search";
	}

	// handling get on search results page
	@RequestMapping(value = "/searchresults", method = RequestMethod.GET)
	public String searchResultGet() {
		return "search";
	}

	@RequestMapping(value = "/searchresults", method = RequestMethod.POST)
	public ModelAndView searchResult(@RequestParam("author") String author, @RequestParam("title") String title) {
		if (author == "") {
			Book b = bookService.searchByTitle(title);
			logger.debug("running SearchByTitle in database");
			ModelAndView mav = new ModelAndView("searchresults");
			mav.addObject("himani", "The results of the Search are");
			List<BookReview> list=bookreviewService.fetchReviewByTitle(title);
			mav.addObject("list",list);
			mav.addObject("b", b);
			return mav;
		} else {
			Book b = bookService.searchByAuthor(author);
			logger.debug("running SearchByAuthor in database");
			ModelAndView mav = new ModelAndView("searchresults");
			mav.addObject("himani", "The results of the Search are");
			List<BookReview> list=bookreviewService.fetchReviewByAuthor(author);
			mav.addObject("list",list);
			mav.addObject("b", b);
			return mav;
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update() {
		return "update";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView update(@RequestParam("title") String title, @RequestParam("author") String author,
			@RequestParam("copies") int copies) {
		Book book1=bookService.searchByTitle(title);
		if (book1!=null &&book1.getTitle().equals(title))
			bookService.updateCopies(title, book1.getCopies() + copies);
		else {
			Book book = new Book();
			book.setCopies(copies);
			book.setTitle(title);
			book.setAuthor(author);
			book.setCopiesIssued(0);
			bookService.save(book);
		}
		logger.debug("returning the add page");
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("msg", "Added to the inventory,Choose what to do next");
		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST) // ModelAttribute should// have a// form  jsp
	public ModelAndView deleteupdate(@RequestParam("title") String title, @RequestParam("author") String author,
			@RequestParam("copies") int copies) {

		Book book = bookService.searchByTitle(title);
		if (book.getCopies() - copies <= 0) {
			bookService.deleteBook(title);
		} else {
			int newCopies = book.getCopies() - copies;
			bookService.updateCopies(title, newCopies);
		}
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("msg", "Deleted the inventory,Choose what to do next");
		return mav;
	}

	@RequestMapping(value = "/issue", method = RequestMethod.GET)
	public String issueBook(Model m/* ,HttpSession session */) {
		m.addAttribute("msg", "Choose book to issue");
		return "issue";
	}

	// handling get on issue book page
	@RequestMapping(value = "/issueBook", method = RequestMethod.GET)
	public String issueBookGet() {
		return "issue";
	}

	// issue is allowed only to logged in users
	@RequestMapping(value = "/issueBook", method = RequestMethod.POST)
	public String issueResult(@RequestParam("title") String title, Model m, HttpSession session) {
		Book book = bookService.searchByTitle(title);
		int oldCopiesIssued = book.getCopiesIssued();
		int totalCopies = book.getCopies();
		if (oldCopiesIssued == totalCopies) {
			String s="All copies of " + title + " are issued. Try to issue a new Copy<br>"+ "oldCopies : " + oldCopiesIssued+"Total Copies : " + totalCopies;
			m.addAttribute("msg", s);
			return "issue";
		} else {
			int i = 1 + oldCopiesIssued;
			book.setCopiesIssued(i);
			bookService.updateBook(book);

			LibUser user = (LibUser) session.getAttribute("user");
			String userName = user.getUserName();
			String author = book.getAuthor();
			//Book Issue Fine Object
			BookIssueFine bookIssueFine = new BookIssueFine();
			bookIssueFine.setAuthor(author);
			bookIssueFine.setTitle(title);
			bookIssueFine.setUserName(userName);
			
			bookissuefineService.issueBook(bookIssueFine);
			
			//logic to send Mail
			int id=user.getId();//user fetched from session does not have id of user, needed to be fetched from db
			LibUser dbUser=userService.fetchLibUser(userName);
			LibUserDetails lud=userService.fetchLibUserDetails(dbUser.getId());
			String to=lud.getEmail();	//email of user
			
			//fetching 
			String imagePath=fileUploadService.retrieveImagePath(userName);
			String image=FileIOUtil.displayImage(imagePath);
			if(mailUtil.sendMailOnIssueBook(to,bookIssueFine,image)){
				System.out.println("MAIL SENT TO USER"+userName);
				
			}
			else
				System.out.println("MAIL SENT FAILED"+userName);
			
			m.addAttribute("msg", title + " issued. Choose What to do next");
			return "index";
		}
	}

	@RequestMapping(value = "/inventory", method = RequestMethod.GET)
	public ModelAndView inventory() {
		List<Book> list = bookService.getBooks();
		ModelAndView mav = new ModelAndView("inventory");
		mav.addObject("list", list);

		return mav;
	}

	// fine and issue methods
	@RequestMapping(value = "/listIssuedBooks", method = RequestMethod.GET)
	public ModelAndView listOfIssueBooks(HttpSession session) {
		LibUser user = (LibUser) session.getAttribute("user");
		String userName = user.getUserName();
		List<BookIssueFine> list = bookissuefineService.listOfIssuedBooks(userName);
		ModelAndView mav = new ModelAndView("profileIssueList");
		mav.addObject("list", list);
		return mav;
	}

	@RequestMapping(value = "/return", method = RequestMethod.GET)
	public String return1() {
		return "return";
	}

	// 1.add actual return date
	// 2. calculate fine and add it to the user account
	// 3. decrement copies issued variable in the book DB
	@RequestMapping(value = "/returnBook", method = RequestMethod.POST)
	public String returnBook(HttpSession session, @RequestParam("title") String title) {
		LibUser user = (LibUser) session.getAttribute("user");
		String userName = user.getUserName();
		BookIssueFine bookIssueFine = bookissuefineService.fetchTitle(userName, title);
		Calendar c = Calendar.getInstance();
		Date actualReturnDate = c.getTime();
		bookissuefineService.returnBook(bookIssueFine, actualReturnDate);
		Book book = bookService.searchByTitle(title);
		book.setCopiesIssued(book.getCopiesIssued() - 1);
		bookService.updateBook(book);
		return "profile";
	}

	@RequestMapping(value = "/finedetails", method = RequestMethod.GET)
	public ModelAndView fineDetails(HttpSession session) {
		LibUser user = (LibUser) session.getAttribute("user");
		String userName = user.getUserName();
		List<BookIssueFine> list = bookissuefineService.listOfIssuedBooks(userName);
		ModelAndView mav = new ModelAndView("fineDetails");
		long fine = 0;
		for (int i = 0; i < list.size(); i++) {
			BookIssueFine b = list.get(i);
			fine = fine + b.getFine();
		}
		userService.setFine(user, fine);
		mav.addObject("list", list);
		mav.addObject("fine", fine);
		return mav;
	}
	@RequestMapping(value = "/generateFineCSV", method = RequestMethod.GET)
	public ModelAndView generateCSV(HttpSession session) {
		LibUser user=(LibUser) session.getAttribute("user");
		String userName=user.getUserName();
		List<BookIssueFine> list = bookissuefineService.listOfIssuedBooks(userName);
		ModelAndView mav = new ModelAndView("fineDetails");
		String root="C:/Users/home/Desktop/lib/";
		File file=new File(root +File.separator+userName+".txt");
		try{
		FileWriter bw=new FileWriter(file);
		bw.append("UserName"+",");
		bw.append("Title"+",");
		bw.append("Author"+",");
		bw.append("Issue Date"+",");
		bw.append("Actual Return Date"+",");
		bw.append("Expected Return Date"+",");
		bw.append("Fine");
		bw.append('\n');
		for (int i = 0; i < list.size(); i++) {
			BookIssueFine b = list.get(i);
			bw.append(b.getUserName()+",");
			bw.append(b.getTitle()+",");
			bw.append(b.getAuthor()+",");
			bw.append(b.getIssueDate()+",");
			bw.append(b.getActualReturnDate()+",");
			bw.append(b.getExpectedReturnDate()+",");
			//bw.append(b.getFine());
			bw.append('\n');
		}	
		bw.flush();
		bw.close();
		}
		catch(Exception e){
			mav.addObject("msg", "Error in generating CSV File ");
		}
		mav.addObject("msg", "CSV File Generated on the Desktop");
		return mav;
	}
}

 */