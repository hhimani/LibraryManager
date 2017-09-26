package org.library.issueAndFine.service;

import java.util.Date;
import java.util.List;

import org.library.issueAndFine.model.BookIssueFine;

public interface BookIssueFineService {

	void issueBook(BookIssueFine bookIssueFine);//save// will save expected return date as well
	void returnBook(BookIssueFine bookIssueFine,Date actualReturnDate);
	List<BookIssueFine> listOfIssuedBooks(String userName);
	BookIssueFine fetchTitle(String userName,String title); 
	void updateBookIssueFine(BookIssueFine book);
}