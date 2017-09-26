package org.library.issueAndFine.service;

import java.util.Date;
import java.util.List;

import org.library.issueAndFine.dao.BookIssueFineDAO;
import org.library.issueAndFine.model.BookIssueFine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookIssueFineServiceImpl implements BookIssueFineService {

	@Autowired
	private BookIssueFineDAO bookissuefineDao;
	
	public BookIssueFineDAO getBookissuefineDao() {
		return bookissuefineDao;
	}

	public void setBookissuefineDao(BookIssueFineDAO bookissuefineDao) {
		this.bookissuefineDao = bookissuefineDao;
	}
	@Transactional
	public void issueBook(BookIssueFine bookIssueFine) {
		bookissuefineDao.issueBook(bookIssueFine);
	}
	@Transactional
	public void returnBook(BookIssueFine bookIssueFine, Date actualReturnDate) {
		bookissuefineDao.returnBook(bookIssueFine, actualReturnDate);
	}
	@Transactional
	public List<BookIssueFine> listOfIssuedBooks(String userName) {
	return bookissuefineDao.listOfIssuedBooks(userName);
	}

	@Transactional
	public BookIssueFine fetchTitle(String userName, String title) {
		return bookissuefineDao.fetchTitle(userName, title);
	
	}
	@Transactional
	public void updateBookIssueFine(BookIssueFine book) {
		bookissuefineDao.updateBookIssueFine(book);
	}

}
