package org.library.review.dao;

import org.library.review.model.BookReview;
import java.util.List;

public interface BookReviewDAO {
	void addReview(BookReview bookReview);
	List<BookReview> fetchReviewByUser(String userName);
	List<BookReview> fetchReviewByTitle(String title);
	List<BookReview> fetchReviewByAuthor(String Author);
	BookReview fetchBookReview(String userName,String title);
	void deleteReview(BookReview bookReview);
	void addBookReview(BookReview bookReview,String review,int rating);
}
