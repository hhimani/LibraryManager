package org.library.manager.service;

import java.util.List;

import org.library.manager.model.Book;

public interface BookService {
	void save(Book book);
	Book searchByTitle(String title);
	Book searchByAuthor(String author);
	void addNewBook(Book book);//add a new row or 
	void increaseCopies(String title);//increase copies count of a given book
	List<Book> getBooks();
	void updateCopies(String title,int copies);
	void deleteBook(String title);
	void updateBook(Book book);
}
