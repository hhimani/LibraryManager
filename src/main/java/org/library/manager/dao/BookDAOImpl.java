package org.library.manager.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.library.manager.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.library.manager.model.Book;

@Repository
@Transactional
public class BookDAOImpl implements BookDAO {
	@Autowired
	// private HibernateTemplate hibernateTemplate;
	/*
	 * public void save(Book book) { hibernateTemplate.save(book); }
	 */
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(Book book) {
		sessionFactory.getCurrentSession().save(book);
	}

	public Book searchByTitle(String title) {
		String hql="FROM Book WHERE title=:title";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("title",title);
		return (Book) query.uniqueResult();
	}
	
	public Book searchByAuthor(String author) {
		String hql ="FROM Book WHERE author=:author";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("author",author);
		//if(query.list()!=null)
			return (Book) query.list().get(0); //need to implement logic in case list is null
		//else 
		//	return null;
	}

	public void addNewBook(Book book) {
		BookDAOImpl b = new BookDAOImpl();
		b.save(book);
	}

	public void increaseCopies(String title) {
		String hql = "FROM Book WHERE title=:title";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("title", title);
		Book book = (Book) query.list().get(0);
		book.setCopies(book.getCopies() + 1);
	}

	public List<Book> getBooks() {
		String hql="FROM Book";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		List<Book> list=query.list();
		return list;
	}

	public void updateCopies(String title, int copies) {
		String hql = "update Book set copies=:copies where title=:title";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("copies",copies);
		query.setParameter("title",title);
		query.executeUpdate();
	}

	public void deleteBook(String title) {
		String hql = "delete from Book where title=:title";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("title",title);
		query.executeUpdate();
	}
	
	public void updateBook(Book book){
		String title=book.getTitle();
		String author=book.getAuthor();
		int copies=book.getCopies();
		int copiesIssued=book.getCopiesIssued();
		int rating=book.getRating();
		String hql="update Book set author=:author, copies=:copies, copiesIssued=:copiesIssued,rating=:rating WHERE title=:title";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("author",author);
		query.setParameter("copies",copies);
		query.setParameter("copiesIssued",copiesIssued);
		query.setParameter("rating",rating);
		query.setParameter("title",title);
		query.executeUpdate();
}	
}
