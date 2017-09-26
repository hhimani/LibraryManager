package org.library.issueAndFine.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.library.issueAndFine.model.BookIssueFine;
import org.library.manager.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookIssueFineDaoImpl implements BookIssueFineDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void issueBook(BookIssueFine bookIssueFine) {
		Calendar calendar=Calendar.getInstance();
		Date issueDate=calendar.getTime();
		bookIssueFine.setIssueDate(issueDate);
		Calendar c=new GregorianCalendar();
		c.add(Calendar.DATE, 30);
		Date expectedReturnDate=c.getTime();
		bookIssueFine.setExpectedReturnDate(expectedReturnDate);
		sessionFactory.getCurrentSession().save(bookIssueFine);
	}

	public void returnBook(BookIssueFine bookIssueFine,Date actualReturnDate) {
		bookIssueFine.setActualReturnDate(actualReturnDate);
		int id=bookIssueFine.getId();
		long fine=calculateFine(actualReturnDate,bookIssueFine);
		String hql="Update BookIssueFine set actualReturnDate=:actualReturnDate,fine=:fine where id=:id";
		Query q=sessionFactory.getCurrentSession().createQuery(hql);
		q.setParameter("actualReturnDate", actualReturnDate);
		q.setParameter("fine", fine);
		q.setParameter("id", id);
		q.executeUpdate();
	}
	//fine is calculated corresponding to every row in issue_fine_table
	private static long calculateFine(Date actualReturnDate, BookIssueFine bookIssueFine) {
		Date expectedReturnDate=bookIssueFine.getExpectedReturnDate();
		long difference =  (actualReturnDate.getTime()-expectedReturnDate.getTime())/86400000;
		long fine=0;
		if(difference<0)
			fine=0;
		else if(difference>300)
			fine=500;
		else{
			fine=50*(1+difference/30);
		}
		return fine;
	}
	public List<BookIssueFine> listOfIssuedBooks(String userName){
		String hql="From BookIssueFine where userName=:userName";
		Query q=sessionFactory.getCurrentSession().createQuery(hql);
		q.setParameter("userName",userName);
		List<BookIssueFine> list=q.list();
		return list;
	}

	public BookIssueFine fetchTitle(String userName, String title) {
		String hql="From BookIssueFine where userName=:userName AND title=:title";
		Query q=sessionFactory.getCurrentSession().createQuery(hql);
		q.setParameter("userName",userName);
		q.setParameter("title", title);
		return (BookIssueFine)q.list().get(0);//can be a list when user issues more than one book of same type
	}
	//updating fine,reviewed,actualreturndate
	public void updateBookIssueFine(BookIssueFine book){
		String title=book.getTitle();
		String userName=book.getUserName();
		boolean reviewed=book.isReviewed();
		long fine=book.getFine();
		Date actualReturnDate=book.getActualReturnDate();
		String hql="update BookIssueFine set reviewed=:reviewed,fine=:fine,actualReturnDate=:actualReturnDate WHERE title=:title "
				+ "AND userName=:userName";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("reviewed",reviewed);
		query.setParameter("fine",fine);
		query.setParameter("actualReturnDate",actualReturnDate);
		query.setParameter("title",title);
		query.setParameter("userName", userName);
		query.executeUpdate();
}	

}
