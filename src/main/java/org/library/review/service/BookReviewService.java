package org.library.review.service;

import java.util.List;

import org.library.review.model.BookReview;

public interface BookReviewService {
	void addReview(BookReview bookReview);
	List<BookReview> fetchReviewByUser(String userName);
	List<BookReview> fetchReviewByTitle(String title);
	void deleteReview(BookReview bookReview);
	List<BookReview> fetchReviewByAuthor(String author);
	void addBookReview(BookReview bookReview,String review,int rating);
	BookReview fetchBookReview(String userName,String title);
}
