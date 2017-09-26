package org.library.review.service;

import java.util.List;

import org.library.review.dao.BookReviewDAO;
import org.library.review.model.BookReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookReviewServiceImpl implements BookReviewService {

	@Autowired
	private BookReviewDAO bookreviewDao;
	
	public BookReviewDAO getBookreviewDao() {
		return bookreviewDao;
	}

	public void setBookreviewDao(BookReviewDAO bookreviewDao) {
		this.bookreviewDao = bookreviewDao;
	}

	@Transactional
	public void addReview(BookReview bookReview) {
		bookreviewDao.addReview(bookReview);
	}

	@Transactional
	public List<BookReview> fetchReviewByUser(String userName) {
		return bookreviewDao.fetchReviewByUser(userName);
	}

	@Transactional
	public List<BookReview> fetchReviewByTitle(String title) {
		return bookreviewDao.fetchReviewByTitle(title);
	}

	@Transactional
	public List<BookReview> fetchReviewByAuthor(String author){
		return bookreviewDao.fetchReviewByAuthor(author);
	}
	@Transactional
	public void deleteReview(BookReview bookReview) {
		bookreviewDao.deleteReview(bookReview);
	}
	@Transactional
	public void addBookReview(BookReview bookReview, String review, int rating) {
		bookreviewDao.addBookReview(bookReview, review, rating);
	}
	@Transactional
	public BookReview fetchBookReview(String userName, String title) {
		return bookreviewDao.fetchBookReview(userName, title);
	}


}
