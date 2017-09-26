package org.library.manager.service;

import java.util.List;

import org.library.manager.dao.BookDAO;
import org.library.manager.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDAO bookDao;
	
	@Transactional
	public void save(Book book) {
		bookDao.save(book);
	}
	@Transactional
	public Book searchByTitle(String title) {
		Book book=bookDao.searchByTitle(title);
		return book;
	}
	@Transactional
	public Book searchByAuthor(String author) {
		Book book=bookDao.searchByAuthor(author);
		return book;
	}
	@Transactional
	public void addNewBook(Book book) {
		bookDao.save(book);
	}
	@Transactional
	public void increaseCopies(String title) {
		bookDao.increaseCopies(title);
	}
	@Transactional
	public List<Book> getBooks() {
		List<Book> list=bookDao.getBooks();
		return list;
	}
	@Transactional
	public void updateCopies(String title,int copies){
		bookDao.updateCopies(title, copies);
	}
	@Transactional
	public void deleteBook(String title){
		bookDao.deleteBook(title);
	}
	@Transactional
	public void updateBook(Book book){
		bookDao.updateBook(book);
	}
}
