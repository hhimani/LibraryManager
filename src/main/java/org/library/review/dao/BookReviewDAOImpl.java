package org.library.review.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.library.review.model.BookReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class BookReviewDAOImpl implements BookReviewDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addReview(BookReview bookReview) {
		sessionFactory.getCurrentSession().save(bookReview);
	}

	public List<BookReview> fetchReviewByUser(String userName) {
		String hql="From BookReview where userName=:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userName", userName);
		return query.list();
	}

	public List<BookReview> fetchReviewByTitle(String title) {
		String hql="From BookReview where title=:title";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("title", title);
		return query.list();
	}

	public List<BookReview> fetchReviewByAuthor(String author){
		String hql="From BookReview where author=:author";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("author",author);
		return query.list();
	}
	
	public void deleteReview(BookReview bookReview) {
		String review=bookReview.getReview();
		String userName=bookReview.getUserName();
		String hql="Delete BookReview where review =: review AND userName =:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("review", review);
		query.setParameter("userName",userName);
		query.executeUpdate();
	}

	public void addBookReview(BookReview bookReview,String review,int rating) {
		String userName=bookReview.getUserName();
		String title=bookReview.getTitle();
		String hql="Update BookReview set review=:review,rating=:rating where userName =:userName AND title=:title";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("review", review);
		query.setParameter("rating", rating);
		query.setParameter("userName",userName);
		query.setParameter("title",title);
		query.executeUpdate();
	}

	public BookReview fetchBookReview(String userName, String title) {
		String hql="From BookReview where userName=:userName AND title=:title";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userName",userName);
		query.setParameter("title",title);
		return (BookReview)query.uniqueResult();
	}
}
